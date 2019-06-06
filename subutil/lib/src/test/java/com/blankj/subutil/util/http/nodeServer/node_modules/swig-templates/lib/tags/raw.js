// Magic tag, hardcoded into parser

/**
 * Forces the content to not be auto-escaped. All swig instructions will be ignored and the content will be rendered exactly as it was given.
 *
 * @alias raw
 *
 * @example
 * // foobar = '<p>'
 * {% raw %}{{ foobar }}{% endraw %}
 * // => {{ foobar }}
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
  return compiler(content, parents, options, blockName)
}
exports.parse = function (str, line, parser) {
  parser.on('*', function (token) {
    throw new Error(
      'Unexpected token "' + token.match + '" in raw tag on line ' + line + '.'
    )
  })
  return true
}
exports.ends = true
