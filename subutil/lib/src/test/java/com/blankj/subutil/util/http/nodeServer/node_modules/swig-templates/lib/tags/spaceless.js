/**
 * Attempts to remove whitespace between HTML tags. Use at your own risk.
 *
 * @alias spaceless
 *
 * @example
 * {% spaceless %}
 *   {% for num in foo %}
 *   <li>{{ loop.index }}</li>
 *   {% endfor %}
 * {% endspaceless %}
 * // => <li>1</li><li>2</li><li>3</li>
 *
 */
exports.compile = function (
  compiler,
  args,
  content,
  parents,
  options,
  blockName
) {
  var out = compiler(content, parents, options, blockName)
  out +=
    '_output = _output.replace(/^\\s+/, "")\n' +
    '  .replace(/>\\s+</g, "><")\n' +
    '  .replace(/\\s+$/, "");\n'

  return out
}

exports.parse = function (str, line, parser) {
  parser.on('*', function (token) {
    throw new Error(
      'Unexpected token "' + token.match + '" on line ' + line + '.'
    )
  })

  return true
}

exports.ends = true
