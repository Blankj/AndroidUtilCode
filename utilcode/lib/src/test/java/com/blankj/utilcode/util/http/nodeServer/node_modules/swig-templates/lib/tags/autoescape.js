var utils = require('../utils')
var strings = ['html', 'js']

/**
 * Control auto-escaping of variable output from within your templates.
 *
 * @alias autoescape
 *
 * @example
 * // myvar = '<foo>';
 * {% autoescape true %}{{ myvar }}{% endautoescape %}
 * // => &lt;foo&gt;
 * {% autoescape false %}{{ myvar }}{% endautoescape %}
 * // => <foo>
 *
 * @param {boolean|string} control One of `true`, `false`, `"js"` or `"html"`.
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
exports.parse = function (str, line, parser, types, stack, opts) {
  var matched
  parser.on('*', function (token) {
    if (
      !matched &&
      (token.type === types.BOOL ||
        (token.type === types.STRING && strings.indexOf(token.match) === -1))
    ) {
      this.out.push(token.match)
      matched = true
      return
    }
    utils.throwError(
      'Unexpected token "' + token.match + '" in autoescape tag',
      line,
      opts.filename
    )
  })

  return true
}
exports.ends = true
