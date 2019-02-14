'use strict';

var EventEmitter = require('events').EventEmitter;
var _ = require('lodash');
var Promise = require('bluebird');
var util = require('./util');
var Document = require('./document');
var Query = require('./query');
var Schema = require('./schema');
var Types = require('./types');
var WarehouseError = require('./error');
var PopulationError = require('./error/population');
var Mutex = require('./mutex');

var parseArgs = util.parseArgs;
var reverse = util.reverse;
var shuffle = _.shuffle;
var getProp = util.getProp;
var setGetter = util.setGetter;
var extend = _.assign;
var isArray = Array.isArray;

/**
 * Model constructor.
 *
 * @class
 * @param {string} name Model name
 * @param {Schema|object} [schema] Schema
 * @extends EventEmitter
 */
function Model(name, schema_) {
  EventEmitter.call(this);

  var schema, i, len, key;

  // Define schema
  if (schema_ instanceof Schema) {
    schema = schema_;
  } else if (typeof schema_ === 'object') {
    schema = new Schema(schema_);
  } else {
    schema = new Schema();
  }

  // Set `_id` path for schema
  if (!schema.path('_id')) {
    schema.path('_id', {type: Types.CUID, required: true});
  }

  this.name = name;
  this.data = {};
  this._mutex = new Mutex();
  this.schema = schema;
  this.length = 0;

  var _Document = this.Document = function(data) {
    Document.call(this, data);

    // Apply getters
    schema._applyGetters(this);
  };

  util.inherits(_Document, Document);
  _Document.prototype._model = this;
  _Document.prototype._schema = schema;

  var _Query = this.Query = function(data) {
    Query.call(this, data);
  };

  util.inherits(_Query, Query);
  _Query.prototype._model = this;
  _Query.prototype._schema = schema;

  // Apply static methods
  var statics = schema.statics;
  var staticKeys = Object.keys(statics);

  for (i = 0, len = staticKeys.length; i < len; i++) {
    key = staticKeys[i];
    this[key] = statics[key];
  }

  // Apply instance methods
  var methods = schema.methods;
  var methodKeys = Object.keys(methods);

  for (i = 0, len = methodKeys.length; i < len; i++) {
    key = methodKeys[i];
    _Document.prototype[key] = methods[key];
  }
}

util.inherits(Model, EventEmitter);

/**
 * Creates a new document.
 *
 * @param {object} data
 * @return {Document}
 */
Model.prototype.new = function(data) {
  return new this.Document(data);
};

/**
 * Finds a document by its identifier.
 *
 * @param {*} id
 * @param {object} options
 *   @param {boolean} [options.lean=false] Returns a plain JavaScript object
 * @return {Document|object}
 */
Model.prototype.findById = function(id, options_) {
  var raw = this.data[id];
  if (!raw) return;

  var options = extend({
    lean: false
  }, options_);

  var data = _.cloneDeep(raw);
  return options.lean ? data : this.new(data);
};

Model.prototype.get = Model.prototype.findById;

/**
 * Checks if the model contains a document with the specified id.
 *
 * @param {*} id
 * @return {boolean}
 */
Model.prototype.has = function(id) {
  return Boolean(this.data[id]);
};

function execHooks(schema, type, event, data) {
  var hooks = schema.hooks[type][event];
  if (!hooks.length) return Promise.resolve(data);

  return Promise.each(hooks, function(hook) {
    return hook(data);
  }).thenReturn(data);
}

/**
 * Acquires write lock.
 *
 * @param {*} id
 * @return {Promise}
 * @private
 */
Model.prototype._acquireWriteLock = function(id) {
  var mutex = this._mutex;

  return new Promise(function(resolve, reject) {
    mutex.lock(resolve);
  }).disposer(function() {
    mutex.unlock();
  });
};

/**
 * Inserts a document.
 *
 * @param {Document|object} data
 * @return {Promise}
 * @private
 */
Model.prototype._insertOne = function(data_) {
  var self = this;
  var schema = this.schema;

  // Apply getters
  var data = data_ instanceof self.Document ? data_ : self.new(data_);
  var id = data._id;

  // Check ID
  if (!id) {
    return Promise.reject(new WarehouseError('ID is not defined', WarehouseError.ID_UNDEFINED));
  }

  if (this.has(id)) {
    return Promise.reject(new WarehouseError('ID `' + id + '` has been used', WarehouseError.ID_EXIST));
  }

  // Apply setters
  var result = data.toObject();
  schema._applySetters(result);

  // Pre-hooks
  return execHooks(schema, 'pre', 'save', data).then(function(data) {
    // Insert data
    self.data[id] = result;
    self.length++;

    self.emit('insert', data);
    return execHooks(schema, 'post', 'save', data);
  });
};

/**
 * Inserts a document.
 *
 * @param {object} data
 * @param {function} [callback]
 * @return {Promise}
 */
Model.prototype.insertOne = function(data, callback) {
  var self = this;

  return Promise.using(this._acquireWriteLock(), function() {
    return self._insertOne(data);
  }).asCallback(callback);
};

/**
 * Inserts documents.
 *
 * @param {object|array} data
 * @param {function} [callback]
 * @return {Promise}
 */
Model.prototype.insert = function(data, callback) {
  if (isArray(data)) {
    var self = this;

    return Promise.mapSeries(data, function(item) {
      return self.insertOne(item);
    }).asCallback(callback);
  }

  return this.insertOne(data, callback);
};

/**
 * Inserts the document if it does not exist; otherwise updates it.
 *
 * @param {object} data
 * @param {function} [callback]
 * @return {Promise}
 */
Model.prototype.save = function(data, callback) {
  var id = data._id;
  var self = this;

  if (!id) return this.insertOne(data, callback);

  return Promise.using(this._acquireWriteLock(), function() {
    if (self.has(id)) {
      return self._replaceById(id, data);
    }

    return self._insertOne(data);
  }).asCallback(callback);
};

/**
 * Updates a document with a compiled stack.
 *
 * @param {*} id
 * @param {array} stack
 * @return {Promise}
 * @private
 */
Model.prototype._updateWithStack = function(id, stack) {
  var self = this;
  var schema = self.schema;

  var data = self.data[id];

  if (!data) {
    return Promise.reject(new WarehouseError('ID `' + id + '` does not exist', WarehouseError.ID_NOT_EXIST));
  }

  // Clone data
  var result = _.cloneDeep(data);

  // Update
  for (var i = 0, len = stack.length; i < len; i++) {
    stack[i](result);
  }

  // Apply getters
  var doc = self.new(result);

  // Apply setters
  result = doc.toObject();
  schema._applySetters(result);

  // Pre-hooks
  return execHooks(schema, 'pre', 'save', doc).then(function(data) {
    // Update data
    self.data[id] = result;

    self.emit('update', data);
    return execHooks(schema, 'post', 'save', data);
  });
};

/**
 * Finds a document by its identifier and update it.
 *
 * @param {*} id
 * @param {object} update
 * @param {function} [callback]
 * @return {Promise}
 */
Model.prototype.updateById = function(id, update, callback) {
  var self = this;

  return Promise.using(this._acquireWriteLock(), function() {
    var stack = self.schema._parseUpdate(update);
    return self._updateWithStack(id, stack);
  }).asCallback(callback);
};

/**
 * Updates matching documents.
 *
 * @param {object} query
 * @param {object} data
 * @param {function} [callback]
 * @return {Promise}
 */
Model.prototype.update = function(query, data, callback) {
  return this.find(query).update(data, callback);
};

/**
 * Finds a document by its identifier and replace it.
 *
 * @param {*} id
 * @param  {object} data
 * @return {Promise}
 * @private
 */
Model.prototype._replaceById = function(id, data_) {
  var self = this;
  var schema = this.schema;

  if (!this.has(id)) {
    return Promise.reject(new WarehouseError('ID `' + id + '` does not exist', WarehouseError.ID_NOT_EXIST));
  }

  data_._id = id;

  // Apply getters
  var data = data instanceof self.Document ? data_ : self.new(data_);

  // Apply setters
  var result = data.toObject();
  schema._applySetters(result);

  // Pre-hooks
  return execHooks(schema, 'pre', 'save', data).then(function(data) {
    // Replace data
    self.data[id] = result;

    self.emit('update', data);
    return execHooks(schema, 'post', 'save', data);
  });
};

/**
 * Finds a document by its identifier and replace it.
 *
 * @param {*} id
 * @param {object} data
 * @param {function} [callback]
 * @return {Promise}
 */
Model.prototype.replaceById = function(id, data, callback) {
  var self = this;

  return Promise.using(this._acquireWriteLock(), function() {
    return self._replaceById(id, data);
  }).asCallback(callback);
};

/**
 * Replaces matching documents.
 *
 * @param {object} query
 * @param {object} data
 * @param {function} [callback]
 * @return {Promise}
 */
Model.prototype.replace = function(query, data, callback) {
  return this.find(query).replace(data, callback);
};

/**
 * Finds a document by its identifier and remove it.
 *
 * @param {*} id
 * @param {function} [callback]
 * @return {Promise}
 * @private
 */
Model.prototype._removeById = function(id) {
  var self = this;
  var schema = this.schema;

  var data = this.data[id];

  if (!data) {
    return Promise.reject(new WarehouseError('ID `' + id + '` does not exist', WarehouseError.ID_NOT_EXIST));
  }

  // Pre-hooks
  return execHooks(schema, 'pre', 'remove', data).then(function(data) {
    // Remove data
    self.data[id] = null;
    self.length--;

    self.emit('remove', data);
    return execHooks(schema, 'post', 'remove', data);
  });
};

/**
 * Finds a document by its identifier and remove it.
 *
 * @param {*} id
 * @param {function} [callback]
 * @return {Promise}
 */
Model.prototype.removeById = function(id, callback) {
  var self = this;

  return Promise.using(this._acquireWriteLock(), function() {
    return self._removeById(id);
  }).asCallback(callback);
};

/**
 * Removes matching documents.
 *
 * @param {object} query
 * @param {object} [callback]
 * @return {Promise}
 */
Model.prototype.remove = function(query, callback) {
  return this.find(query).remove(callback);
};

/**
 * Deletes a model.
 */
Model.prototype.destroy = function() {
  this._database._models[this.name] = null;
};

/**
 * Returns the number of elements.
 *
 * @return {number}
 */
Model.prototype.count = function() {
  return this.length;
};

Model.prototype.size = Model.prototype.count;

/**
 * Iterates over all documents.
 *
 * @param {function} iterator
 * @param {object} [options] See {@link Model#findById}.
 */
Model.prototype.forEach = function(iterator, options) {
  var keys = Object.keys(this.data);
  var num = 0;
  var data;

  for (var i = 0, len = keys.length; i < len; i++) {
    data = this.findById(keys[i], options);
    if (data) iterator(data, num++);
  }
};

Model.prototype.each = Model.prototype.forEach;

/**
 * Returns an array containing all documents.
 *
 * @param {Object} [options] See {@link Model#findById}.
 * @return {Array}
 */
Model.prototype.toArray = function(options) {
  var result = new Array(this.length);

  this.forEach(function(item, i) {
    result[i] = item;
  }, options);

  return result;
};

/**
 * Finds matching documents.
 *
 * @param {Object} query
 * @param {Object} [options]
 *   @param {Number} [options.limit=0] Limits the number of documents returned.
 *   @param {Number} [options.skip=0] Skips the first elements.
 *   @param {Boolean} [options.lean=false] Returns a plain JavaScript object.
 * @return {Query|Array}
 */
Model.prototype.find = function(query, options_) {
  var options = options_ || {};
  var filter = this.schema._execQuery(query);
  var keys = Object.keys(this.data);
  var len = keys.length;
  var limit = options.limit || this.length;
  var skip = options.skip;
  var data = this.data;
  var arr = [];
  var key, item;

  for (var i = 0; limit && i < len; i++) {
    key = keys[i];
    item = data[key];

    if (item && filter(item)) {
      if (skip) {
        skip--;
      } else {
        arr.push(this.findById(key, options));
        limit--;
      }
    }
  }

  return options.lean ? arr : new this.Query(arr);
};

/**
 * Finds the first matching documents.
 *
 * @param {Object} query
 * @param {Object} [options]
 *   @param {Number} [options.skip=0] Skips the first elements.
 *   @param {Boolean} [options.lean=false] Returns a plain JavaScript object.
 * @return {Document|Object}
 */
Model.prototype.findOne = function(query, options_) {
  var options = options_ || {};
  options.limit = 1;

  var result = this.find(query, options);
  return options.lean ? result[0] : result.data[0];
};

/**
 * Sorts documents. See {@link Query#sort}.
 *
 * @param {String|Object} orderby
 * @param {String|Number} [order]
 * @return {Query}
 */
Model.prototype.sort = function(orderby, order) {
  var sort = parseArgs(orderby, order);
  var fn = this.schema._execSort(sort);

  return new this.Query(this.toArray().sort(fn));
};

/**
 * Returns the document at the specified index. `num` can be a positive or
 * negative number.
 *
 * @param {Number} i
 * @param {Object} [options] See {@link Model#findById}.
 * @return {Document|Object}
 */
Model.prototype.eq = function(i_, options) {
  var index = i_ < 0 ? this.length + i_ : i_;
  var data = this.data;
  var keys = Object.keys(data);
  var key, item;

  for (var i = 0, len = keys.length; i < len; i++) {
    key = keys[i];
    item = data[key];

    if (!item) continue;

    if (index) {
      index--;
    } else {
      return this.findById(key, options);
    }
  }
};

/**
 * Returns the first document.
 *
 * @param {Object} [options] See {@link Model#findById}.
 * @return {Document|Object}
 */
Model.prototype.first = function(options) {
  return this.eq(0, options);
};

/**
 * Returns the last document.
 *
 * @param {Object} [options] See {@link Model#findById}.
 * @return {Document|Object}
 */
Model.prototype.last = function(options) {
  return this.eq(-1, options);
};

/**
 * Returns the specified range of documents.
 *
 * @param {Number} start
 * @param {Number} [end]
 * @return {Query}
 */
Model.prototype.slice = function(start_, end_) {
  var total = this.length;

  var start = start_ | 0;
  if (start < 0) start += total;
  if (start > total - 1) return new this.Query([]);

  var end = end_ | 0 || total;
  if (end < 0) end += total;

  var len = start > end ? 0 : end - start;
  if (len > total) len = total - start;
  if (!len) return new this.Query([]);

  var arr = new Array(len);
  var keys = Object.keys(this.data);
  var keysLen = keys.length;
  var num = 0;
  var data;

  for (var i = 0; num < len && i < keysLen; i++) {
    data = this.findById(keys[i]);
    if (!data) continue;

    if (start) {
      start--;
    } else {
      arr[num++] = data;
    }
  }

  return new this.Query(arr);
};

/**
 * Limits the number of documents returned.
 *
 * @param {Number} i
 * @return {Query}
 */
Model.prototype.limit = function(i) {
  return this.slice(0, i);
};

/**
 * Specifies the number of items to skip.
 *
 * @param {Number} i
 * @return {Query}
 */
Model.prototype.skip = function(i) {
  return this.slice(i);
};

/**
 * Returns documents in a reversed order.
 *
 * @return {Query}
 */
Model.prototype.reverse = function() {
  return new this.Query(reverse(this.toArray()));
};

/**
 * Returns documents in random order.
 *
 * @return {Query}
 */
Model.prototype.shuffle = function() {
  return new this.Query(shuffle(this.toArray()));
};

Model.prototype.random = Model.prototype.shuffle;

/**
 * Creates an array of values by iterating each element in the collection.
 *
 * @param {Function} iterator
 * @param {Object} [options]
 * @return {Array}
 */
Model.prototype.map = function(iterator, options) {
  var result = new Array(this.length);

  this.forEach(function(item, i) {
    result[i] = iterator(item, i);
  }, options);

  return result;
};

/**
 * Reduces a collection to a value which is the accumulated result of iterating
 * each element in the collection.
 *
 * @param {Function} iterator
 * @param {*} [initial] By default, the initial value is the first document.
 * @return {*}
 */
Model.prototype.reduce = function(iterator, initial) {
  var arr = this.toArray();
  var len = this.length;
  var i, result;

  if (initial === undefined) {
    i = 1;
    result = arr[0];
  } else {
    i = 0;
    result = initial;
  }

  for (; i < len; i++) {
    result = iterator(result, arr[i], i);
  }

  return result;
};

/**
 * Reduces a collection to a value which is the accumulated result of iterating
 * each element in the collection from right to left.
 *
 * @param {Function} iterator
 * @param {*} [initial] By default, the initial value is the last document.
 * @return {*}
 */
Model.prototype.reduceRight = function(iterator, initial) {
  var arr = this.toArray();
  var len = this.length;
  var i, result;

  if (initial === undefined) {
    i = len - 2;
    result = arr[len - 1];
  } else {
    i = len - 1;
    result = initial;
  }

  for (; i >= 0; i--) {
    result = iterator(result, arr[i], i);
  }

  return result;
};

/**
 * Creates a new array with all documents that pass the test implemented by the
 * provided function.
 *
 * @param {Function} iterator
 * @param {Object} [options]
 * @return {Query}
 */
Model.prototype.filter = function(iterator, options) {
  var arr = [];

  this.forEach(function(item, i) {
    if (iterator(item, i)) arr.push(item);
  }, options);

  return new this.Query(arr);
};

/**
 * Tests whether all documents pass the test implemented by the provided
 * function.
 *
 * @param {Function} iterator
 * @return {Boolean}
 */
Model.prototype.every = function(iterator) {
  var keys = Object.keys(this.data);
  var len = keys.length;
  var num = 0;
  var data;

  if (!len) return true;

  for (var i = 0; i < len; i++) {
    data = this.findById(keys[i]);

    if (data) {
      if (!iterator(data, num++)) return false;
    }
  }

  return true;
};

/**
 * Tests whether some documents pass the test implemented by the provided
 * function.
 *
 * @param {Function} iterator
 * @return {Boolean}
 */
Model.prototype.some = function(iterator) {
  var keys = Object.keys(this.data);
  var len = keys.length;
  var num = 0;
  var data;

  if (!len) return false;

  for (var i = 0; i < len; i++) {
    data = this.findById(keys[i]);

    if (data) {
      if (iterator(data, num++)) return true;
    }
  }

  return false;
};

/**
 * Returns a getter function for normal population.
 *
 * @param {Object} data
 * @param {Model} model
 * @param {Object} options
 * @return {Function}
 * @private
 */
Model.prototype._populateGetter = function(data, model, options) {
  var hasCache = false;
  var cache;

  return function() {
    if (!hasCache) {
      cache = model.findById(data);
      hasCache = true;
    }

    return cache;
  };
};

/**
 * Returns a getter function for array population.
 *
 * @param {Object} data
 * @param {Model} model
 * @param {Object} options
 * @return {Function}
 * @private
 */
Model.prototype._populateGetterArray = function(data, model, options) {
  var Query = model.Query;
  var hasCache = false;
  var cache;

  return function() {
    if (!hasCache) {
      var arr = [];

      for (var i = 0, len = data.length; i < len; i++) {
        arr.push(model.findById(data[i]));
      }

      if (options.match) {
        cache = new Query(arr).find(options.match, options);
      } else if (options.skip) {
        if (options.limit) {
          arr = arr.slice(options.skip, options.skip + options.limit);
        } else {
          arr = arr.slice(options.skip);
        }

        cache = new Query(arr);
      } else if (options.limit) {
        cache = new Query(arr.slice(0, options.limit));
      } else {
        cache = new Query(arr);
      }

      if (options.sort) {
        cache = cache.sort(options.sort);
      }

      hasCache = true;
    }

    return cache;
  };
};

/**
 * Populates document references with a compiled stack.
 *
 * @param {Object} data
 * @param {Array} stack
 * @return {Object}
 * @private
 */
Model.prototype._populate = function(data, stack) {
  var models = this._database._models;
  var item, model, path, prop;

  for (var i = 0, len = stack.length; i < len; i++) {
    item = stack[i];
    model = models[item.model];

    if (!model) {
      throw new PopulationError('Model `' + item.model + '` does not exist');
    }

    path = item.path;
    prop = getProp(data, path);

    if (isArray(prop)) {
      setGetter(data, path, this._populateGetterArray(prop, model, item));
    } else {
      setGetter(data, path, this._populateGetter(prop, model, item));
    }
  }

  return data;
};

/**
 * Populates document references.
 *
 * @param {String|Object} path
 * @return {Query}
 */
Model.prototype.populate = function(path) {
  if (!path) throw new TypeError('path is required');

  var stack = this.schema._parsePopulate(path);
  var arr = new Array(this.length);
  var self = this;

  this.forEach(function(item, i) {
    arr[i] = self._populate(item, stack);
  });

  return new Query(arr);
};

/**
 * Imports data.
 *
 * @param {Array} arr
 * @private
 */
Model.prototype._import = function(arr) {
  var len = arr.length;
  var data = this.data;
  var schema = this.schema;
  var item;

  for (var i = 0; i < len; i++) {
    item = arr[i];
    data[item._id] = schema._parseDatabase(item);
  }

  this.length = len;
};

/**
 * Exports data.
 *
 * @return {String}
 * @private
 */
Model.prototype._export = function() {
  var arr = new Array(this.length);
  var schema = this.schema;

  this.forEach(function(item, i) {
    arr[i] = schema._exportDatabase(item);
  }, {lean: true});

  return JSON.stringify(arr);
};

module.exports = Model;
