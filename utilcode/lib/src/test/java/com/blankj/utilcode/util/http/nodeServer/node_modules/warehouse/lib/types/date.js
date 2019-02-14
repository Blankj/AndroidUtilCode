'use strict';

var SchemaType = require('../schematype');
var util = require('../util');
var ValidationError = require('../error/validation');

/**
 * Date schema type.
 *
 * @class
 * @param {String} name
 * @param {Object} [options]
 *   @param {Boolean} [options.required=false]
 *   @param {Date|Number|Function} [options.default]
 * @extends {SchemaType}
 */
function SchemaTypeDate(name, options) {
  SchemaType.call(this, name, options);
}

util.inherits(SchemaTypeDate, SchemaType);

/**
 * Casts data.
 *
 * @param {*} value
 * @param {Object} data
 * @return {Date}
 */
SchemaTypeDate.prototype.cast = function(value_, data) {
  var value = SchemaType.prototype.cast.call(this, value_, data);

  if (value == null || util.isDate(value)) return value;

  return new Date(value);
};

/**
 * Validates data.
 *
 * @param {*} value
 * @param {Object} data
 * @return {Date|Error}
 */
SchemaTypeDate.prototype.validate = function(value_, data) {
  var value = SchemaType.prototype.validate.call(this, value_, data);

  if (value != null && (!util.isDate(value) || isNaN(value.getTime()))) {
    throw new ValidationError('`' + value + '` is not a valid date!');
  }

  return value;
};

/**
 * Checks the equality of data.
 *
 * @param {Date} value
 * @param {Date} query
 * @param {Object} data
 * @return {Boolean}
 */
SchemaTypeDate.prototype.match = function(value, query, data) {
  if (!value || !query) {
    return value === query;
  }

  return value.getTime() === query.getTime();
};

/**
 * Compares between two dates.
 *
 * @param {Date} a
 * @param {Date} b
 * @return {Number}
 */
SchemaTypeDate.prototype.compare = function(a, b) {
  if (a) {
    return b ? a - b : 1;
  }

  return b ? -1 : 0;
};

/**
 * Parses data and transforms it into a date object.
 *
 * @param {*} value
 * @param {Object} data
 * @return {Date}
 */
SchemaTypeDate.prototype.parse = function(value, data) {
  if (value) return new Date(value);
};

/**
 * Transforms a date object to a string.
 *
 * @param {Date} value
 * @param {Object} data
 * @return {String}
 */
SchemaTypeDate.prototype.value = function(value, data) {
  return value ? value.toISOString() : value;
};

/**
 * Finds data by its date.
 *
 * @param {Date} value
 * @param {Number} query
 * @param {Object} data
 * @return {Boolean}
 */
SchemaTypeDate.prototype.q$day = function(value, query, data) {
  return value ? value.getDate() === query : false;
};

/**
 * Finds data by its month. (Start from 0)
 *
 * @param {Date} value
 * @param {Number} query
 * @param {Object} data
 * @return {Boolean}
 */
SchemaTypeDate.prototype.q$month = function(value, query, data) {
  return value ? value.getMonth() === query : false;
};

/**
 * Finds data by its year. (4-digit)
 *
 * @param {Date} value
 * @param {Number} query
 * @param {Object} data
 * @return {Boolean}
 */
SchemaTypeDate.prototype.q$year = function(value, query, data) {
  return value ? value.getFullYear() === query : false;
};

/**
 * Adds milliseconds to date.
 *
 * @param {Date} value
 * @param {Number} update
 * @param {Object} data
 * @return {Date}
 */
SchemaTypeDate.prototype.u$inc = function(value, update, data) {
  if (value) return new Date(value.getTime() + update);
};

/**
 * Subtracts milliseconds from date.
 *
 * @param {Date} value
 * @param {Number} update
 * @param {Object} data
 * @return {Date}
 */
SchemaTypeDate.prototype.u$dec = function(value, update, data) {
  if (value) return new Date(value.getTime() - update);
};

module.exports = SchemaTypeDate;
