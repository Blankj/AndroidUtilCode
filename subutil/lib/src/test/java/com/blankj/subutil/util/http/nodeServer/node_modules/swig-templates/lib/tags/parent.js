/**
 * Inject the content from the parent template's block of the same name into the current block.
 *
 * See <a href="#inheritance">Template Inheritance</a> for more information.
 *
 * @alias parent
 *
 * @example
 * {% extends "./foo.html" %}
 * {% block content %}
 *   My content.
 *   {% parent %}
 * {% endblock %}
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
  if (!parents || !parents.length) {
    return ''
  }

  var parentFile = args[0]
  var breaker = true
  var l = parents.length
  var i = 0
  var parent
  var block

  for (i; i < l; i += 1) {
    parent = parents[i]
    if (!parent.blocks || !parent.blocks.hasOwnProperty(blockName)) {
      continue
    }
    if (breaker && parentFile !== parent.name) {
      block = parent.blocks[blockName]
      return (
        block.compile(
          compiler,
          [blockName],
          block.content,
          parents.slice(i + 1),
          options
        ) + '\n'
      )
    }
  }
}

exports.parse = function (str, line, parser, types, stack, opts) {
  parser.on('*', function (token) {
    throw new Error(
      'Unexpected argument "' + token.match + '" on line ' + line + '.'
    )
  })

  parser.on('end', function () {
    this.out.push(opts.filename)
  })

  return true
}
