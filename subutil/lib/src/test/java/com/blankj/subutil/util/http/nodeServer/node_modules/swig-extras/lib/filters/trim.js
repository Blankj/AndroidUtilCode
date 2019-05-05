var each = require('../utils').each;

/**
 * Trim leading and trailing whitespace from input strings.
 *
 * @example
 * // foo = "   this has extra whitespace   ";
 * {{ foo|trim }}
 * // => this has extra whitespace
 *
 * @param  {*} input
 * @return {*}
 */
module.exports = function (input) {
  if (typeof input === 'object') {
    each(input, function (value, key) {
      input[key] = module.exports(value);
    });
    return input;
  }

  if (typeof input === 'string') {
    return input.replace(/^\s*|\s*$/g, '');
  }

  return input;
};
