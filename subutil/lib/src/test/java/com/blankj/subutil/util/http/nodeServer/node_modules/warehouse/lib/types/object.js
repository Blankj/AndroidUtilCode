'use strict';

var _ = require('lodash');
var SchemaType = require('../schematype');
var util = require('../util');

var extend = _.assign;

/**
 * Object schema type.
 *
 * @class
 * @param {String} name
 * @param {Object} [options]
 *   @param {Boolean} [options.required=false]
 *   @param {Object|Function} [options.default={}]
 * @extends {SchemaType}
 */
function SchemaTypeObject(name, options) {
  SchemaType.call(this, name, extend({
    default: {}
  }, options));
}

util.inherits(SchemaTypeObject, SchemaType);

module.exports = SchemaTypeObject;
