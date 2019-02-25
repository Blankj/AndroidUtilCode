'use strict';

var escapeDiacritic = require('./escape_diacritic');
var escapeRegExp = require('./escape_regexp');
var rControl = /[\u0000-\u001f]/g;
var rSpecial = /[\s~`!@#\$%\^&\*\(\)\-_\+=\[\]\{\}\|\\;:"'<>,\.\?\/]+/g;

function slugize(str, options) {
  if (typeof str !== 'string') throw new TypeError('str must be a string!');
  options = options || {};

  var separator = options.separator || '-';
  var escapedSep = escapeRegExp(separator);

  var result = escapeDiacritic(str)
    // Remove control characters
    .replace(rControl, '')
    // Replace special characters
    .replace(rSpecial, separator)
    // Remove continous separators
    .replace(new RegExp(escapedSep + '{2,}', 'g'), separator)
    // Remove prefixing and trailing separtors
    .replace(new RegExp('^' + escapedSep + '+|' + escapedSep + '+$', 'g'), '');

  switch (options.transform){
    case 1:
      return result.toLowerCase();

    case 2:
      return result.toUpperCase();

    default:
      return result;
  }
}

module.exports = slugize;
