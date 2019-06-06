'use strict';

function truncate(str, options) {
  if (typeof str !== 'string') throw new TypeError('str must be a string!');
  options = options || {};

  var length = options.length || 30;
  var omission = options.omission || '...';
  var separator = options.separator;
  var omissionLength = omission.length;

  if (str.length < length) return str;

  if (separator) {
    var words = str.split(separator);
    var word = '';
    var result = '';
    var resultLength = 0;

    for (var i = 0, len = words.length; i < len; i++) {
      word = words[i];

      if (resultLength + word.length + omissionLength < length) {
        result += word + separator;
        resultLength = result.length;
      } else {
        return result.substring(0, resultLength - 1) + omission;
      }
    }
  } else {
    return str.substring(0, length - omissionLength) + omission;
  }
}

module.exports = truncate;
