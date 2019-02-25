var filters = require('../filters')

/**
 * Apply a filter to an entire block of template.
 *
 * @alias filter
 *
 * @example
 * {% filter uppercase %}oh hi, {{ name }}{% endfilter %}
 * // => OH HI, PAUL
 *
 * @example
 * {% filter replace(".", "!", "g") %}Hi. My name is Paul.{% endfilter %}
 * // => Hi! My name is Paul!
 *
 * @param {function} filter  The filter that should be applied to the contents of the tag.
 */

exports.compile = function (
  compiler,
  args,
  content,
  parents,
  options,
  blockName
) {
  var filter = args.shift().replace(/\($/, '')
  var val =
    '(function () {\n' +
    '  var _output = "";\n' +
    compiler(content, parents, options, blockName) +
    '  return _output;\n' +
    '})()'

  if (args[args.length - 1] === ')') {
    args.pop()
  }

  args = args.length ? ', ' + args.join('') : ''
  return '_output += _filters["' + filter + '"](' + val + args + ');\n'
}

exports.parse = function (str, line, parser, types) {
  var filter

  function check (filter) {
    if (!filters.hasOwnProperty(filter)) {
      throw new Error(
        'Filter "' + filter + '" does not exist on line ' + line + '.'
      )
    }
  }

  parser.on(types.FUNCTION, function (token) {
    if (!filter) {
      filter = token.match.replace(/\($/, '')
      check(filter)
      this.out.push(token.match)
      this.state.push(token.type)
      return
    }
    return true
  })

  parser.on(types.VAR, function (token) {
    if (!filter) {
      filter = token.match
      check(filter)
      this.out.push(filter)
      return
    }
    return true
  })

  return true
}

exports.ends = true
