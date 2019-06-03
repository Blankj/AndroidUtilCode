var each = require('../utils').each;

/**
 * Truncate input strings to the given length;
 *
 * @example
 * // foo = 'This is some text.';
 * {{ foo|truncate(5) }}
 * // => This ...
 *
 * @param  {*}      input
 * @param  {number} len   Number of characters to truncate to.
 * @param  {string} [end="..."]   Text that will be appended if the string was truncated
 * @return {*}
 */
module.exports = function (input, len, end) {
  end = (typeof end === 'undefined') ? '...' : end;

  if (typeof input === 'object') {
    each(input, function (value, key) {
      input[key] = module.exports(value, len, end);
    });
    return input;
  }

  if (typeof input === 'string') {
    return input.substring(0, len) + ((input.length > len) ? end : '');
  }

  return input;
};
