var isArray = require('../utils').isArray;

/**
 * Group an array of objects by a common key. If an array is not provided, the input value will be returned untouched.
 *
 * @example
 * // people = [{ age: 23, name: 'Paul' }, { age: 26, name: 'Jane' }, { age: 23, name: 'Jim' }];
 * {% for agegroup in people|groupby('age') %}
 *   <h2>{{ loop.key }}</h2>
 *   <ul>
 *     {% for person in agegroup %}
 *     <li>{{ person.name }}</li>
 *     {% endfor %}
 *   </ul>
 * {% endfor %}
 *
 * @param  {*}      input Input object.
 * @param  {string} key   Key to group by.
 * @return {object}       Grouped arrays by given key.
 */
module.exports = function (input, key) {
  if (!isArray(input)) {
    return input;
  }

  var i = 0,
    l = input.length,
    o = {},
    k;

  for (i; i < l; i += 1) {
    k = input[i];
    if (!k.hasOwnProperty(key)) {
      continue;
    }

    if (!o[k[key]]) {
      o[k[key]] = [];
    }
    o[k[key]].push(k);
  }

  return o;
};
