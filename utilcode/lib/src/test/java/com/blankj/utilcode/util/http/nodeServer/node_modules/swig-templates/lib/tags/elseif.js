var ifparser = require('./if').parse

/**
 * Like <code data-language="swig">{% else %}</code>, except this tag can take more conditional statements.
 *
 * @alias elseif
 * @alias elif
 *
 * @example
 * {% if false %}
 *   Tacos
 * {% elseif true %}
 *   Burritos
 * {% else %}
 *   Churros
 * {% endif %}
 * // => Burritos
 *
 * @param {...mixed} conditional  Conditional statement that returns a truthy or falsy value.
 */
exports.compile = function (compiler, args) {
  return '} else if (' + args.join(' ') + ') {\n'
}

exports.parse = function (str, line, parser, types, stack) {
  var okay = ifparser(str, line, parser, types, stack)
  return okay && (stack.length && stack[stack.length - 1].name === 'if')
}
