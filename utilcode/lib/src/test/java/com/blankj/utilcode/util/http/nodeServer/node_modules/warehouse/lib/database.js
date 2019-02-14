'use strict';

var JSONStream = require('JSONStream');
var Promise = require('bluebird');
var fs = require('graceful-fs');
var _ = require('lodash');
var Model = require('./model');
var Schema = require('./schema');
var SchemaType = require('./schematype');
var util = require('./util');
var WarehouseError = require('./error');
var pkg = require('../package.json');
var extend = _.assign;

/**
 * Database constructor.
 *
 * @class
 * @param {object} [options]
 *   @param {number} [options.version=0] Database version
 *   @param {string} [options.path] Database path
 *   @param {function} [options.onUpgrade] Triggered when the database is upgraded
 *   @param {function} [options.onDowngrade] Triggered when the database is downgraded
 */
function Database(options) {
  this.options = extend({
    version: 0,
    onUpgrade: function() {},

    onDowngrade: function() {}
  }, options);

  this._models = {};

  var _Model = this.Model = function(name, schema) {
    Model.call(this, name, schema);
  };

  util.inherits(_Model, Model);
  _Model.prototype._database = this;
}

/**
 * Creates a new model.
 *
 * @param {string} name
 * @param {Schema|object} [schema]
 * @return {Model}
 */
Database.prototype.model = function(name, schema) {
  if (this._models[name]) {
    return this._models[name];
  }

  var model = this._models[name] = new this.Model(name, schema);
  return model;
};

/**
 * Loads database.
 *
 * @param {function} [callback]
 * @return {Promise}
 */
Database.prototype.load = function(callback) {
  var path = this.options.path;
  var self = this;

  if (!path) throw new WarehouseError('options.path is required');

  return new Promise(function(resolve, reject) {
    var src = fs.createReadStream(path, {encoding: 'utf8'});
    var oldVersion = 0;

    var stream = JSONStream.parse([true, true], function(value, keys) {
      switch (keys.shift()){
        case 'meta':
          if (keys.shift() === 'version') {
            oldVersion = value;
          }

          break;

        case 'models':
          self.model(keys.shift())._import(value);
          break;
      }
    });

    src
      .pipe(stream)
      .on('error', reject)
      .on('end', function() {
        resolve(oldVersion);
      });
  }).then(function(oldVersion) {
    var newVersion = self.options.version;

    if (newVersion > oldVersion) {
      return self.options.onUpgrade(oldVersion, newVersion);
    } else if (newVersion < oldVersion) {
      return self.options.onDowngrade(oldVersion, newVersion);
    }
  }).asCallback(callback);
};

/**
 * Saves database.
 *
 * @param {function} [callback]
 * @return {Promise}
 */
Database.prototype.save = function(callback) {
  var path = this.options.path;
  var self = this;

  if (!path) throw new WarehouseError('options.path is required');

  return new Promise(function(resolve, reject) {
    var stream = fs.createWriteStream(path);

    // Start
    stream.write('{');

    // Meta
    stream.write('"meta":' + JSON.stringify({
      version: self.options.version,
      warehouse: pkg.version
    }) + ',');

    // Export models
    var models = self._models;
    var keys = Object.keys(models);
    var model, key;

    stream.write('"models":{');

    for (var i = 0, len = keys.length; i < len; i++) {
      key = keys[i];
      model = models[key];

      if (!model) continue;

      if (i) stream.write(',');
      stream.write('"' + key + '":' + model._export());
    }

    stream.write('}');

    // End
    stream.end('}');

    stream.on('error', reject)
      .on('finish', resolve);
  }).asCallback(callback);
};

Database.Schema = Database.prototype.Schema = Schema;
Database.SchemaType = Database.prototype.SchemaType = SchemaType;
Database.version = pkg.version;

module.exports = Database;
