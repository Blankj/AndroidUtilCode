'use strict';

var util = require('util');

exports.inherits = util.inherits;
exports.isDate = util.isDate;

function extractPropKey(key) {
  return key.split('.');
}

exports.getProp = function(obj, key) {
  if (typeof obj !== 'object') throw new TypeError('obj must be an object!');
  if (!key) throw new TypeError('key is required!');

  var token = extractPropKey(key);
  var result = obj[token.shift()];
  var len = token.length;

  if (!len) return result;

  for (var i = 0; i < len; i++) {
    result = result[token[i]];
  }

  return result;
};

exports.setProp = function(obj, key, value) {
  if (typeof obj !== 'object') throw new TypeError('obj must be an object!');
  if (!key) throw new TypeError('key is required!');

  var token = extractPropKey(key);
  var lastKey = token.pop();
  var len = token.length;

  if (!len) {
    obj[lastKey] = value;
    return;
  }

  var cursor = obj;
  var name;

  for (var i = 0; i < len; i++) {
    name = token[i];
    cursor = cursor[name] = cursor[name] || {};
  }

  cursor[lastKey] = value;
};

exports.delProp = function(obj, key) {
  if (typeof obj !== 'object') throw new TypeError('obj must be an object!');
  if (!key) throw new TypeError('key is required!');

  var token = extractPropKey(key);
  var lastKey = token.pop();
  var len = token.length;

  if (!len) {
    delete obj[lastKey];
    return;
  }

  var cursor = obj;
  var name;

  for (var i = 0; i < len; i++) {
    name = token[i];

    if (cursor[name]) {
      cursor = cursor[name];
    } else {
      return;
    }
  }

  delete cursor[lastKey];
};

exports.setGetter = function(obj, key, fn) {
  if (typeof obj !== 'object') throw new TypeError('obj must be an object!');
  if (!key) throw new TypeError('key is required!');
  if (typeof fn !== 'function') throw new TypeError('fn must be a function!');

  var token = extractPropKey(key);
  var lastKey = token.pop();
  var len = token.length;

  if (!len) {
    obj.__defineGetter__(lastKey, fn);
    return;
  }

  var cursor = obj;
  var name;

  for (var i = 0; i < len; i++) {
    name = token[i];
    cursor = cursor[name] = cursor[name] || {};
  }

  cursor.__defineGetter__(lastKey, fn);
};

exports.arr2obj = function(arr, value) {
  if (!Array.isArray(arr)) throw new TypeError('arr must be an array!');

  var obj = {};
  var i = arr.length;

  while (i--) {
    obj[arr[i]] = value;
  }

  return obj;
};

exports.reverse = function(arr) {
  if (!Array.isArray(arr)) throw new TypeError('arr must be an array!');

  var len = arr.length;
  var tmp;

  if (!len) return arr;

  for (var left = 0, right = len - 1; left < right; left++, right--) {
    tmp = arr[left];
    arr[left] = arr[right];
    arr[right] = tmp;
  }

  return arr;
};

function parseArgs(args) {
  if (typeof args !== 'string') return args;

  var arr = args.split(' ');
  var result = {};
  var key;

  for (var i = 0, len = arr.length; i < len; i++) {
    key = arr[i];

    switch (key[0]){
      case '+':
        result[key.slice(1)] = 1;
        break;

      case '-':
        result[key.slice(1)] = -1;
        break;

      default:
        result[key] = 1;
    }
  }

  return result;
}

exports.parseArgs = function(orderby, order) {
  var result;

  if (order) {
    result = {};
    result[orderby] = order;
  } else if (typeof orderby === 'string') {
    result = parseArgs(orderby);
  } else {
    result = orderby;
  }

  return result;
};
