/**
 * Used to create conditional statements in templates. Accepts most JavaScript valid comparisons.
 *
 * Can be used in conjunction with <a href="#elseif"><code data-language="swig">{% elseif ... %}</code></a> and <a href="#else"><code data-language="swig">{% else %}</code></a> tags.
 *
 * @alias if
 *
 * @example
 * {% if x %}{% endif %}
 * {% if !x %}{% endif %}
 * {% if not x %}{% endif %}
 *
 * @example
 * {% if x and y %}{% endif %}
 * {% if x && y %}{% endif %}
 * {% if x or y %}{% endif %}
 * {% if x || y %}{% endif %}
 * {% if x || (y && z) %}{% endif %}
 *
 * @example
 * {% if x [operator] y %}
 *   Operators: ==, !=, <, <=, >, >=, ===, !==
 * {% endif %}
 *
 * @example
 * {% if x == 'five' %}
 *   The operands can be also be string or number literals
 * {% endif %}
 *
 * @example
 * {% if x|lower === 'tacos' %}
 *   You can use filters on any operand in the statement.
 * {% endif %}
 *
 * @example
 * {% if x in y %}
 *   If x is a value that is present in y, this will return true.
 * {% endif %}
 *
 * @param {...mixed} conditional Conditional statement that returns a truthy or falsy value.
 */
exports.compile = function (
  compiler,
  args,
  content,
  parents,
  options,
  blockName
) {
  return (
    'if (' +
    args.join(' ') +
    ') { \n' +
    compiler(content, parents, options, blockName) +
    '\n' +
    '}'
  )
}

exports.parse = function (str, line, parser, types) {
  if (str === undefined) {
    throw new Error('No conditional statement provided on line ' + line + '.')
  }

  parser.on(types.COMPARATOR, function (token) {
    if (this.isLast) {
      throw new Error(
        'Unexpected logic "' + token.match + '" on line ' + line + '.'
      )
    }
    if (this.prevToken.type === types.NOT) {
      throw new Error(
        'Attempted logic "not ' +
          token.match +
          '" on line ' +
          line +
          '. Use !(foo ' +
          token.match +
          ') instead.'
      )
    }
    this.out.push(token.match)
    this.filterApplyIdx.push(this.out.length)
  })

  parser.on(types.NOT, function (token) {
    if (this.isLast) {
      throw new Error(
        'Unexpected logic "' + token.match + '" on line ' + line + '.'
      )
    }
    this.out.push(token.match)
  })

  parser.on(types.BOOL, function (token) {
    this.out.push(token.match)
  })

  parser.on(types.LOGIC, function (token) {
    if (!this.out.length || this.isLast) {
      throw new Error(
        'Unexpected logic "' + token.match + '" on line ' + line + '.'
      )
    }
    this.out.push(token.match)
    this.filterApplyIdx.pop()
  })

  return true
}

exports.ends = true
