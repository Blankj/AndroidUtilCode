'use strict';

var escapeRegExp = require('./escape_regexp');

var rParam = /:(\w+)/g;

function Permalink(rule, options) {
  if (!rule) throw new TypeError('rule is required!');
  options = options || {};

  var segments = options.segments || {};
  var params = [];

  var regex = escapeRegExp(rule)
    .replace(rParam, function(match, name) {
      params.push(name);

      if (segments.hasOwnProperty(name)) {
        var segment = segments[name];

        if (segment instanceof RegExp) {
          return segment.source;
        }

        return segment;
      }

      return '(.+?)';
    });

  this.rule = rule;
  this.regex = new RegExp('^' + regex + '$');
  this.params = params;
}

Permalink.prototype.test = function(str) {
  return this.regex.test(str);
};

Permalink.prototype.parse = function(str) {
  var match = str.match(this.regex);
  var params = this.params;
  var result = {};

  if (!match) return;

  for (var i = 1, len = match.length; i < len; i++) {
    result[params[i - 1]] = match[i];
  }

  return result;
};

Permalink.prototype.stringify = function(data) {
  return this.rule.replace(rParam, function(match, name) {
    return data[name];
  });
};

module.exports = Permalink;
