'use strict';

// https://github.com/rails/rails/blob/v4.2.0/actionview/lib/action_view/helpers/text_helper.rb#L240
function wordWrap(str, options) {
  if (typeof str !== 'string') throw new TypeError('str must be a string!');
  options = options || {};

  var width = options.width || 80;
  var regex = new RegExp('(.{1,' + width + '})(\\s+|$)', 'g');
  var lines = str.split('\n');
  var line = '';

  for (var i = 0, len = lines.length; i < len; i++) {
    line = lines[i];

    if (line.length > width) {
      lines[i] = line.replace(regex, '$1\n').trim();
    }
  }

  return lines.join('\n');
}

module.exports = wordWrap;

