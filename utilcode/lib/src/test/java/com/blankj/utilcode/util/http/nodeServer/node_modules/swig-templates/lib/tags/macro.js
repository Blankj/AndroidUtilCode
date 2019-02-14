/**
 * Create custom, reusable snippets within your templates.
 * Can be imported from one template to another using the <a href="#import"><code data-language="swig">{% import ... %}</code></a> tag.
 *
 * @alias macro
 *
 * @example
 * {% macro input(type, name, id, label, value, error) %}
 *   <label for="{{ name }}">{{ label }}</label>
 *   <input type="{{ type }}" name="{{ name }}" id="{{ id }}" value="{{ value }}"{% if error %} class="error"{% endif %}>
 * {% endmacro %}
 *
 * {{ input("text", "fname", "fname", "First Name", fname.value, fname.errors) }}
 * // => <label for="fname">First Name</label>
 * //    <input type="text" name="fname" id="fname" value="">
 *
 * @param {...arguments} arguments  User-defined arguments.
 */
exports.compile = function (
  compiler,
  args,
  content,
  parents,
  options,
  blockName
) {
  var fnName = args.shift()

  return (
    '_ctx.' +
    fnName +
    ' = function (' +
    args.join('') +
    ') {\n' +
    '  var _output = "",\n' +
    '    __ctx = _utils.extend({}, _ctx);\n' +
    '  _utils.each(_ctx, function (v, k) {\n' +
    '    if (["' +
    args.join('","') +
    '"].indexOf(k) !== -1) { delete _ctx[k]; }\n' +
    '  });\n' +
    compiler(content, parents, options, blockName) +
    '\n' +
    ' _ctx = _utils.extend(_ctx, __ctx);\n' +
    '  return _output;\n' +
    '};\n' +
    '_ctx.' +
    fnName +
    '.safe = true;\n'
  )
}

exports.parse = function (str, line, parser, types) {
  var name

  parser.on(types.VAR, function (token) {
    if (token.match.indexOf('.') !== -1) {
      throw new Error(
        'Unexpected dot in macro argument "' +
          token.match +
          '" on line ' +
          line +
          '.'
      )
    }
    this.out.push(token.match)
  })

  parser.on(types.FUNCTION, function (token) {
    if (!name) {
      name = token.match
      this.out.push(name)
      this.state.push(types.FUNCTION)
    }
  })

  parser.on(types.FUNCTIONEMPTY, function (token) {
    if (!name) {
      name = token.match
      this.out.push(name)
    }
  })

  parser.on(types.PARENCLOSE, function () {
    if (this.isLast) {
      return
    }
    throw new Error('Unexpected parenthesis close on line ' + line + '.')
  })

  parser.on(types.COMMA, function () {
    return true
  })

  parser.on('*', function () {})

  return true
}

exports.ends = true
exports.block = true
