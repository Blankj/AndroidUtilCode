'use strict';

var yaml = require('js-yaml');
var util = require('util');
var isDate = util.isDate;
var rPrefixSep = /^(-{3,}|;{3,})/;
var rFrontMatter = /^(-{3,}|;{3,})\n([\s\S]+?)\n\1(?:$|\n([\s\S]*)$)/;
var rFrontMatterNew = /^([\s\S]+?)\n(-{3,}|;{3,})(?:$|\n([\s\S]*)$)/;

function split(str) {
  if (typeof str !== 'string') throw new TypeError('str is required!');

  if (rFrontMatter.test(str)) return splitOld(str);
  if (rFrontMatterNew.test(str)) return splitNew(str);

  return {content: str};
}

function splitOld(str) {
  var match = str.match(rFrontMatter);

  return {
    data: match[2],
    content: match[3] || '',
    separator: match[1],
    prefixSeparator: true
  };
}

function splitNew(str) {
  if (rPrefixSep.test(str)) return {content: str};

  var match = str.match(rFrontMatterNew);

  return {
    data: match[1],
    content: match[3] || '',
    separator: match[2],
    prefixSeparator: false
  };
}

function parse(str, options) {
  if (typeof str !== 'string') throw new TypeError('str is required!');

  var splitData = split(str);
  var raw = splitData.data;

  if (!raw) return {_content: str};

  var data;

  if (splitData.separator[0] === ';') {
    data = parseJSON(raw);
  } else {
    data = parseYAML(raw, options);
  }

  if (!data) return {_content: str};

  var keys = Object.keys(data);
  var key = '';
  var item;

  // Convert timezone
  for (var i = 0, len = keys.length; i < len; i++) {
    key = keys[i];
    item = data[key];

    if (isDate(item)) {
      data[key] = new Date(item.getTime() + item.getTimezoneOffset() * 60 * 1000);
    }
  }

  data._content = splitData.content;
  return data;
}

function parseYAML(str, options) {
  var result = yaml.load(escapeYAML(str), options);
  if (typeof result !== 'object') return;

  return result;
}

function parseJSON(str) {
  try {
    return JSON.parse('{' + str + '}');
  } catch (err) {
    return;
  }
}

function escapeYAML(str) {
  if (typeof str !== 'string') throw new TypeError('str is required!');

  return str.replace(/\n(\t+)/g, function(match, tabs) {
    var result = '\n';

    for (var i = 0, len = tabs.length; i < len; i++) {
      result += '  ';
    }

    return result;
  });
}

function stringify(obj, options) {
  if (!obj) throw new TypeError('obj is required!');

  options = options || {};

  var content = obj._content || '';
  delete obj._content;

  if (!Object.keys(obj).length) return content;

  var mode = options.mode;
  var separator = options.separator || (mode === 'json' ? ';;;' : '---');
  var result = '';

  if (options.prefixSeparator) result += separator + '\n';

  if (mode === 'json') {
    result += stringifyJSON(obj);
  } else {
    result += stringifyYAML(obj, options);
  }

  result += separator + '\n' + content;

  return result;
}

function stringifyYAML(obj, options) {
  var keys = Object.keys(obj);
  var data = {};
  var nullKeys = [];
  var dateKeys = [];
  var key, value, i, len;

  for (i = 0, len = keys.length; i < len; i++) {
    key = keys[i];
    value = obj[key];

    if (value == null) {
      nullKeys.push(key);
    } else if (isDate(value)) {
      dateKeys.push(key);
    } else {
      data[key] = value;
    }
  }

  var result = yaml.dump(data, options);

  if (dateKeys.length) {
    for (i = 0, len = dateKeys.length; i < len; i++) {
      key = dateKeys[i];
      result += key + ': ' + formatDate(obj[key]) + '\n';
    }
  }

  if (nullKeys.length) {
    for (i = 0, len = nullKeys.length; i < len; i++) {
      result += nullKeys[i] + ':\n';
    }
  }

  return result;
}

function stringifyJSON(obj) {
  return JSON.stringify(obj, null, '  ')
    // Remove indention
    .replace(/\n {2}/g, function() {
      return '\n';
    })
    // Remove prefixing and trailing braces
    .replace(/^{\n|}$/g, '');
}

function doubleDigit(num) {
  return num < 10 ? '0' + num : num;
}

function formatDate(date) {
  return date.getFullYear() + '-' +
    doubleDigit(date.getMonth() + 1) + '-' +
    doubleDigit(date.getDate()) + ' ' +
    doubleDigit(date.getHours()) + ':' +
    doubleDigit(date.getMinutes()) + ':' +
    doubleDigit(date.getSeconds());
}

exports = module.exports = parse;
exports.parse = parse;
exports.split = split;
exports.escape = escapeYAML;
exports.stringify = stringify;
