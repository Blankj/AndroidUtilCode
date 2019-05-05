var utils = require('../utils');

/**
 * Batches items by returning a list of lists with the given number of items.
 *
 * @example
 * // items = ['a', 'b', 'c', 'd', 'e', 'f', 'g'];
 * <table>
 * {% for row in items|batch(3, 'No item') %}
 *   <tr>
 *   {% for column in row %}
 *     <td>{{ column }}</td>
 *   {% endfor %}
 *   </tr>
 * {% endfor %}
 * </table>
 * // => <table>
 * //      <tr>
 * //          <td>a</td>
 * //          <td>b</td>
 * //          <td>c</td>
 * //        </tr>
 * //      <tr>
 * //          <td>d</td>
 * //          <td>e</td>
 * //          <td>f</td>
 * //        </tr>
 * //      <tr>
 * //          <td>g</td>
 * //          <td>No item</td>
 * //          <td>No item</td>
 * //        </tr>
 * //    </table>
 *
 * @param  {array}  input
 * @param  {number} num   Number of items to batch into lists.
 * @param  {*}      fill  Item to fill in if there are blanks when batching.
 * @return {array}
 */
module.exports = function (input, num, fill) {
  if (!utils.isArray(input)) {
    return input;
  }

  var o = [],
    l = (input.length % num !== 0) ? num - (input.length % num) : 0;
  utils.each(input, function (val, index) {
    if (index % num === 0) {
      o.push([]);
    }
    o[o.length - 1].push(val);
  });

  if (typeof fill !== 'undefined' && l) {
    while (l) {
      o[o.length - 1].push(fill);
      l -= 1;
    }
  }

  return o;
};
