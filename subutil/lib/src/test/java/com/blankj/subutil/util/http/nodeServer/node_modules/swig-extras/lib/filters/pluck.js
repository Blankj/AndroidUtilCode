var isArray = require('../utils').isArray;

/**
 * Pluck out a key value from a list of objects as a single list.
 *
 * @example
 * // people = [{ age: 30, name: 'Paul' }, { age: 28, name: 'Nicole'}];
 * {{ people|pluck('name') }}
 * // => ['Paul', 'Nicole']
 *
 * @param  {array}  input Array of objects.
 * @param  {string} key   Key index of items to list.
 * @return {array}        List of values for `key`.
 */
module.exports = function (input, key) {
  if (!isArray(input)) {
    return input;
  }

  var i = 0,
    l = input.length,
    o = [];
  for (i; i < l; i += 1) {
    if (input[i].hasOwnProperty(key)) {
      o.push(input[i][key]);
    }
  }

  return o;
};
