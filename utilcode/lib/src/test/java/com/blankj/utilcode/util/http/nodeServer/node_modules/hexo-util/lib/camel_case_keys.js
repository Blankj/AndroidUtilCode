'use strict';

var camelCase = require('camel-case');

var rPrefixUnderscore = /^(_+)/;

function getter(key) {
  return function() {
    return this[key];
  };
}

function setter(key) {
  return function(value) {
    this[key] = value;
  };
}

function camelCaseKeys(obj) {
  if (typeof obj !== 'object') throw new TypeError('obj must be an object!');

  var keys = Object.keys(obj);
  var result = {};

  for (var i = 0, len = keys.length; i < len; i++) {
    var key = keys[i];
    var value = obj[key];
    var match = key.match(rPrefixUnderscore);
    var newKey;

    if (match) {
      var underscore = match[1];
      newKey = underscore + camelCase(key.substring(underscore.length));
    } else {
      newKey = camelCase(key);
    }

    if (newKey === key) {
      result[key] = value;
    } else {
      result[newKey] = value;
      result.__defineGetter__(key, getter(newKey));
      result.__defineSetter__(key, setter(newKey));
    }
  }

  return result;
}

module.exports = camelCaseKeys;
