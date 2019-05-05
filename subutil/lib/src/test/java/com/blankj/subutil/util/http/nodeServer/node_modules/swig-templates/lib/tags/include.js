var ignore = 'ignore'
var missing = 'missing'
var only = 'only'

/**
 * Includes a template partial in place. The template is rendered within the current locals variable context.
 *
 * @alias include
 *
 * @example
 * // food = 'burritos';
 * // drink = 'lemonade';
 * {% include "./partial.html" %}
 * // => I like burritos and lemonade.
 *
 * @example
 * // my_obj = { food: 'tacos', drink: 'horchata' };
 * {% include "./partial.html" with my_obj only %}
 * // => I like tacos and horchata.
 *
 * @example
 * {% include "/this/file/does/not/exist" ignore missing %}
 * // => (Nothing! empty string)
 *
 * @param {string|var}  file      The path, relative to the template root, to render into the current context.
 * @param {literal}     [with]    Literally, "with".
 * @param {object}      [context] Local variable key-value object context to provide to the included file.
 * @param {literal}     [only]    Restricts to <strong>only</strong> passing the <code>with context</code> as local variables–the included template will not be aware of any other local variables in the parent template. For best performance, usage of this option is recommended if possible.
 * @param {literal}     [ignore missing] Will output empty string if not found instead of throwing an error.
 */
exports.compile = function (compiler, args) {
  var file = args.shift()
  var onlyIdx = args.indexOf(only)
  var onlyCtx = onlyIdx !== -1 ? args.splice(onlyIdx, 1) : false
  var parentFile = (args.pop() || '').replace(/\\/g, '\\\\')
  var ignore = args[args.length - 1] === missing ? args.pop() : false
  var w = args.join('')

  return (
    (ignore ? '  try {\n' : '') +
    '_output += _swig.compileFile(' +
    file +
    ', {' +
    'resolveFrom: "' +
    parentFile +
    '"' +
    '})(' +
    (onlyCtx && w ? w : !w ? '_ctx' : '_utils.extend({}, _ctx, ' + w + ')') +
    ');\n' +
    (ignore ? '} catch (e) {}\n' : '')
  )
}

exports.parse = function (str, line, parser, types, stack, opts) {
  var file, w
  parser.on(types.STRING, function (token) {
    if (!file) {
      file = token.match
      this.out.push(file)
      return
    }

    return true
  })

  parser.on(types.VAR, function (token) {
    if (!file) {
      file = token.match
      return true
    }

    if (!w && token.match === 'with') {
      w = true
      return
    }

    if (w && token.match === only && this.prevToken.match !== 'with') {
      this.out.push(token.match)
      return
    }

    if (token.match === ignore) {
      return false
    }

    if (token.match === missing) {
      if (this.prevToken.match !== ignore) {
        throw new Error(
          'Unexpected token "' + missing + '" on line ' + line + '.'
        )
      }
      this.out.push(token.match)
      return false
    }

    if (this.prevToken.match === ignore) {
      throw new Error(
        'Expected "' +
          missing +
          '" on line ' +
          line +
          ' but found "' +
          token.match +
          '".'
      )
    }

    return true
  })

  parser.on('end', function () {
    this.out.push(opts.filename || null)
  })

  return true
}
