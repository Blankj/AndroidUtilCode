'use strict';

var SchemaType = require('../schematype');
var util = require('../util');

var setGetter = util.setGetter;

/**
 * Virtual schema type.
 *
 * @class
 * @param {String} name
 * @param {Object} [options]
 * @extends {SchemaType}
 */
function SchemaTypeVirtual(name, options) {
  SchemaType.call(this, name, options);
}

util.inherits(SchemaTypeVirtual, SchemaType);

/**
 * Add a getter.
 *
 * @param {Function} fn
 * @chainable
 */
SchemaTypeVirtual.prototype.get = function(fn) {
  if (typeof fn !== 'function') {
    throw new TypeError('Getter must be a function!');
  }

  this.getter = fn;

  return this;
};

/**
 * Add a setter.
 *
 * @param {Function} fn
 * @chainable
 */
SchemaTypeVirtual.prototype.set = function(fn) {
  if (typeof fn !== 'function') {
    throw new TypeError('Setter must be a function!');
  }

  this.setter = fn;

  return this;
};

/**
 * Applies getters.
 *
 * @param {*} value
 * @param {Object} data
 * @return {*}
 */
SchemaTypeVirtual.prototype.cast = function(value, data) {
  if (typeof this.getter !== 'function') return;

  var getter = this.getter;
  var hasCache = false;
  var cache;

  setGetter(data, this.name, function() {
    if (!hasCache) {
      cache = getter.call(data);
      hasCache = true;
    }

    return cache;
  });
};

/**
 * Applies setters.
 *
 * @param {*} value
 * @param {Object} data
 */
SchemaTypeVirtual.prototype.validate = function(value, data) {
  if (typeof this.setter === 'function') {
    this.setter.call(data, value);
  }
};

module.exports = SchemaTypeVirtual;
