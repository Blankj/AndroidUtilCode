var each = require('../utils').each;

/**
 * Insert HTML line breaks for all newlines in a string.
 *
 * @example
 * // foo = "This is nice.\nAnd so are you."
 * {{ foo|nl2br }}
 * // => This is nice.<br>And so are you.
 *
 * @param  {*} input All string values will be modified.
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
    return input.replace(/\n/g, '<br>');
  }

  return input;
};

module.exports.safe = true;
