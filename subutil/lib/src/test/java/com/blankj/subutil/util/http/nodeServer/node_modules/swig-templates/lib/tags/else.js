/**
 * Used within an <code data-language="swig">{% if %}</code> tag, the code block following this tag up until <code data-language="swig">{% endif %}</code> will be rendered if the <i>if</i> statement returns false.
 *
 * @alias else
 *
 * @example
 * {% if false %}
 *   statement1
 * {% else %}
 *   statement2
 * {% endif %}
 * // => statement2
 *
 */
exports.compile = function () {
  return '} else {\n'
}

exports.parse = function (str, line, parser, types, stack) {
  parser.on('*', function (token) {
    throw new Error(
      '"else" tag does not accept any tokens. Found "' +
        token.match +
        '" on line ' +
        line +
        '.'
    )
  })

  return stack.length && stack[stack.length - 1].name === 'if'
}
