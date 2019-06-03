'use strict';

var util = require('./util');

/**
 * WarehouseError constructor
 *
 * @class
 * @param {String} msg
 * @param {String} code
 * @extends Error
 */
function WarehouseError(msg, code) {
  Error.call(this);

  if (Error.captureStackTrace) {
    Error.captureStackTrace(this);
  } else {
    this.stack = new Error().stack;
  }

  this.name = 'WarehouseError';
  this.message = msg;
  this.code = code;
}

util.inherits(WarehouseError, Error);

WarehouseError.ID_EXIST = 'ID_EXIST';
WarehouseError.ID_NOT_EXIST = 'ID_NOT_EXIST';
WarehouseError.ID_UNDEFINED = 'ID_UNDEFINED';

module.exports = WarehouseError;
