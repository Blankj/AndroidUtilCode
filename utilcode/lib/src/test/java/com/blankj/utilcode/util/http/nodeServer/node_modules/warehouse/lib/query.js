'use strict';

var _ = require('lodash');
var Promise = require('bluebird');
var util = require('./util');

var reverse = util.reverse;
var shuffle = _.shuffle;
var parseArgs = util.parseArgs;

/**
 * Query constructor.
 *
 * @class
 * @param {Array} data
 */
function Query(data) {
  this.data = data;
  this.length = data.length;
}

/**
 * Returns the number of elements.
 *
 * @return Number
 */
Query.prototype.count = function() {
  return this.length;
};

Query.prototype.size = Query.prototype.count;

/**
 * Iterates over all documents.
 *
 * @param {Function} iterator
 */
Query.prototype.forEach = function(iterator) {
  var data = this.data;

  for (var i = 0, len = this.length; i < len; i++) {
    iterator(data[i], i);
  }
};

Query.prototype.each = Query.prototype.forEach;

/**
 * Returns an array containing all documents.
 *
 * @return {Array}
 */
Query.prototype.toArray = function() {
  return this.data;
};

/**
 * Returns the document at the specified index. `num` can be a positive or
 * negative number.
 *
 * @param {Number} i
 * @return {Document|Object}
 */
Query.prototype.eq = function(i) {
  var index = i < 0 ? this.length + i : i;
  return this.data[index];
};

/**
 * Returns the first document.
 *
 * @return {Document|Object}
 */
Query.prototype.first = function() {
  return this.eq(0);
};

/**
 * Returns the last document.
 *
 * @return {Document|Object}
 */
Query.prototype.last = function() {
  return this.eq(-1);
};

/**
 * Returns the specified range of documents.
 *
 * @param {Number} start
 * @param {Number} [end]
 * @return {Query}
 */
Query.prototype.slice = function(start, end) {
  return new this.constructor(this.data.slice(start, end));
};

/**
 * Limits the number of documents returned.
 *
 * @param {Number} i
 * @return {Query}
 */
Query.prototype.limit = function(i) {
  return this.slice(0, i);
};

/**
 * Specifies the number of items to skip.
 *
 * @param {Number} i
 * @return {Query}
 */
Query.prototype.skip = function(i) {
  return this.slice(i);
};

/**
 * Returns documents in a reversed order.
 *
 * @return {Query}
 */
Query.prototype.reverse = function() {
  return new this.constructor(reverse(this.data.slice()));
};

/**
 * Returns documents in random order.
 *
 * @return {Query}
 */
Query.prototype.shuffle = function() {
  return new this.constructor(shuffle(this.data.slice()));
};

Query.prototype.random = Query.prototype.shuffle;

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
Query.prototype.find = function(query, options_) {
  var options = options_ || {};
  var filter = this._schema._execQuery(query);
  var data = this.data;
  var i = 0;
  var len = this.length;
  var limit = options.limit || len;
  var skip = options.skip;
  var arr = [];
  var item;

  for (; limit && i < len; i++) {
    item = data[i];

    if (filter(item)) {
      if (skip) {
        skip--;
      } else {
        arr.push(item);
        limit--;
      }
    }
  }

  return options.lean ? arr : new this.constructor(arr);
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
Query.prototype.findOne = function(query, options_) {
  var options = options_ || {};
  options.limit = 1;

  var result = this.find(query, options);
  return options.lean ? result[0] : result.data[0];
};

/**
 * Sorts documents.
 *
 * Example:
 *
 * ``` js
 * query.sort('date', -1);
 * query.sort({date: -1, title: 1});
 * query.sort('-date title');
 * ```
 *
 * If the `order` equals to `-1`, `desc` or `descending`, the data will be
 * returned in reversed order.
 *
 * @param {String|Object} orderby
 * @param {String|Number} [order]
 * @return {Query}
 */
Query.prototype.sort = function(orderby, order) {
  var sort = parseArgs(orderby, order);
  var fn = this._schema._execSort(sort);

  return new this.constructor(this.data.slice().sort(fn));
};

/**
 * Creates an array of values by iterating each element in the collection.
 *
 * @param {Function} iterator
 * @return {Array}
 */
Query.prototype.map = function(iterator) {
  var len = this.length;
  var result = new Array(len);
  var data = this.data;

  for (var i = 0; i < len; i++) {
    result[i] = iterator(data[i], i);
  }

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
Query.prototype.reduce = function(iterator, initial) {
  var len = this.length;
  var data = this.data;
  var result, i;

  if (initial === undefined) {
    i = 1;
    result = data[0];
  } else {
    i = 0;
    result = initial;
  }

  for (; i < len; i++) {
    result = iterator(result, data[i], i);
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
Query.prototype.reduceRight = function(iterator, initial) {
  var len = this.length;
  var data = this.data;
  var result, i;

  if (initial === undefined) {
    i = len - 2;
    result = data[len - 1];
  } else {
    i = len - 1;
    result = initial;
  }

  for (; i >= 0; i--) {
    result = iterator(result, data[i], i);
  }

  return result;
};

/**
 * Creates a new array with all documents that pass the test implemented by the
 * provided function.
 *
 * @param {Function} iterator
 * @return {Query}
 */
Query.prototype.filter = function(iterator) {
  var data = this.data;
  var arr = [];
  var item;

  for (var i = 0, len = this.length; i < len; i++) {
    item = data[i];
    if (iterator(item, i)) arr.push(item);
  }

  return new this.constructor(arr);
};

/**
 * Tests whether all documents pass the test implemented by the provided
 * function.
 *
 * @param {Function} iterator
 * @return {Boolean}
 */
Query.prototype.every = function(iterator) {
  var data = this.data;

  for (var i = 0, len = data.length; i < len; i++) {
    if (!iterator(data[i], i)) return false;
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
Query.prototype.some = function(iterator) {
  var data = this.data;

  for (var i = 0, len = data.length; i < len; i++) {
    if (iterator(data[i], i)) return true;
  }

  return false;
};

/**
 * Update all documents.
 *
 * @param {Object} data
 * @param {Function} [callback]
 * @return {Promise}
 */
Query.prototype.update = function(data, callback) {
  var model = this._model;
  var stack = this._schema._parseUpdate(data);

  return Promise.mapSeries(this.data, function(item) {
    return model._updateWithStack(item._id, stack);
  }).asCallback(callback);
};

/**
 * Replace all documents.
 *
 * @param {Object} data
 * @param {Function} [callback]
 * @return {Promise}
 */
Query.prototype.replace = function(data, callback) {
  var model = this._model;

  return Promise.map(this.data, function(item) {
    return model.replaceById(item._id, data);
  }).asCallback(callback);
};

/**
 * Remove all documents.
 *
 * @param {Function} [callback]
 * @return {Promise}
 */
Query.prototype.remove = function(callback) {
  var model = this._model;

  return Promise.mapSeries(this.data, function(item) {
    return model.removeById(item._id);
  }).asCallback(callback);
};

/**
 * Populates document references.
 *
 * @param {String|Object} expr
 * @return {Query}
 */
Query.prototype.populate = function(expr) {
  var stack = this._schema._parsePopulate(expr);
  var data = this.data;
  var model = this._model;

  for (var i = 0, len = this.length; i < len; i++) {
    data[i] = model._populate(data[i], stack);
  }

  return this;
};

module.exports = Query;
