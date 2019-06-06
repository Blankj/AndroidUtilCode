var utils = require('./utils')
var lexer = require('./lexer')

var _t = lexer.types
var _reserved = [
  'break',
  'case',
  'catch',
  'continue',
  'debugger',
  'default',
  'delete',
  'do',
  'else',
  'finally',
  'for',
  'function',
  'if',
  'in',
  'instanceof',
  'new',
  'return',
  'switch',
  'this',
  'throw',
  'try',
  'typeof',
  'var',
  'void',
  'while',
  'with'
]

/**
 * Filters are simply functions that perform transformations on their first input argument.
 * Filters are run at render time, so they may not directly modify the compiled template structure in any way.
 * All of Swig's built-in filters are written in this same way. For more examples, reference the `filters.js` file in Swig's source.
 *
 * To disable auto-escaping on a custom filter, simply add a property to the filter method `safe = true;` and the output from this will not be escaped, no matter what the global settings are for Swig.
 *
 * @typedef {function} Filter
 *
 * @example
 * // This filter will return 'bazbop' if the idx on the input is not 'foobar'
 * swig.setFilter('foobar', function (input, idx) {
 *   return input[idx] === 'foobar' ? input[idx] : 'bazbop';
 * });
 * // myvar = ['foo', 'bar', 'baz', 'bop'];
 * // => {{ myvar|foobar(3) }}
 * // Since myvar[3] !== 'foobar', we render:
 * // => bazbop
 *
 * @example
 * // This filter will disable auto-escaping on its output:
 * function bazbop (input) { return input; }
 * bazbop.safe = true;
 * swig.setFilter('bazbop', bazbop);
 * // => {{ "<p>"|bazbop }}
 * // => <p>
 *
 * @param {*} input Input argument, automatically sent from Swig's built-in parser.
 * @param {...*} [args] All other arguments are defined by the Filter author.
 * @return {*}
 */

/*!
 * Makes a string safe for a regular expression.
 * @param  {string} str
 * @return {string}
 * @private
 */
function escapeRegExp (str) {
  return str.replace(/[-/\\^$*+?.()|[\]{}]/g, '\\$&')
}

/**
 * Parse strings of variables and tags into tokens for future compilation.
 * @class
 * @param {array}   tokens     Pre-split tokens read by the Lexer.
 * @param {object}  filters    Keyed object of filters that may be applied to variables.
 * @param {boolean} autoescape Whether or not this should be autoescaped.
 * @param {number}  line       Beginning line number for the first token.
 * @param {string}  [filename] Name of the file being parsed.
 * @private
 */
function TokenParser (tokens, filters, autoescape, line, filename) {
  this.out = []
  this.state = []
  this.filterApplyIdx = []
  this._parsers = {}
  this.line = line
  this.filename = filename
  this.filters = filters
  this.escape = autoescape

  this.parse = function () {
    var self = this

    if (self._parsers.start) {
      self._parsers.start.call(self)
    }
    utils.each(tokens, function (token, i) {
      var prevToken = tokens[i - 1]
      self.isLast = i === tokens.length - 1
      if (prevToken) {
        while (prevToken.type === _t.WHITESPACE) {
          i -= 1
          prevToken = tokens[i - 1]
        }
      }
      self.prevToken = prevToken
      self.parseToken(token)
    })
    if (self._parsers.end) {
      self._parsers.end.call(self)
    }

    if (self.escape) {
      self.filterApplyIdx = [0]
      if (typeof self.escape === 'string') {
        self.parseToken({ type: _t.FILTER, match: 'e' })
        self.parseToken({ type: _t.COMMA, match: ',' })
        self.parseToken({ type: _t.STRING, match: String(autoescape) })
        self.parseToken({ type: _t.PARENCLOSE, match: ')' })
      } else {
        self.parseToken({ type: _t.FILTEREMPTY, match: 'e' })
      }
    }

    return self.out
  }
}

TokenParser.prototype = {
  /**
   * Set a custom method to be called when a token type is found.
   *
   * @example
   * parser.on(types.STRING, function (token) {
   *   this.out.push(token.match);
   * });
   * @example
   * parser.on('start', function () {
   *   this.out.push('something at the beginning of your args')
   * });
   * parser.on('end', function () {
   *   this.out.push('something at the end of your args');
   * });
   *
   * @param  {number}   type Token type ID. Found in the Lexer.
   * @param  {Function} fn   Callback function. Return true to continue executing the default parsing function.
   * @return {undefined}
   */
  on: function (type, fn) {
    this._parsers[type] = fn
  },

  /**
   * Parse a single token.
   * @param  {{match: string, type: number, line: number}} token Lexer token object.
   * @return {undefined}
   * @private
   */
  parseToken: function (token) {
    var self = this
    var fn = self._parsers[token.type] || self._parsers['*']
    var match = token.match
    var prevToken = self.prevToken
    var prevTokenType = prevToken ? prevToken.type : null
    var lastState = self.state.length ? self.state[self.state.length - 1] : null
    var temp

    if (fn && typeof fn === 'function') {
      if (!fn.call(this, token)) {
        return
      }
    }

    if (
      lastState &&
      prevToken &&
      lastState === _t.FILTER &&
      prevTokenType === _t.FILTER &&
      token.type !== _t.PARENCLOSE &&
      token.type !== _t.COMMA &&
      token.type !== _t.OPERATOR &&
      token.type !== _t.FILTER &&
      token.type !== _t.FILTEREMPTY
    ) {
      self.out.push(', ')
    }

    if (lastState && lastState === _t.METHODOPEN) {
      self.state.pop()
      if (token.type !== _t.PARENCLOSE) {
        self.out.push(', ')
      }
    }

    switch (token.type) {
      case _t.WHITESPACE:
        break

      case _t.STRING:
        self.filterApplyIdx.push(self.out.length)
        self.out.push(match.replace(/\\/g, '\\\\'))
        break

      case _t.NUMBER:
      case _t.BOOL:
        self.filterApplyIdx.push(self.out.length)
        self.out.push(match)
        break

      case _t.FILTER:
        if (
          !self.filters.hasOwnProperty(match) ||
          typeof self.filters[match] !== 'function'
        ) {
          utils.throwError(
            'Invalid filter "' + match + '"',
            self.line,
            self.filename
          )
        }
        self.escape = self.filters[match].safe ? false : self.escape
        self.out.splice(
          self.filterApplyIdx[self.filterApplyIdx.length - 1],
          0,
          '_filters["' + match + '"]('
        )
        self.state.push(token.type)
        break

      case _t.FILTEREMPTY:
        if (
          !self.filters.hasOwnProperty(match) ||
          typeof self.filters[match] !== 'function'
        ) {
          utils.throwError(
            'Invalid filter "' + match + '"',
            self.line,
            self.filename
          )
        }
        self.escape = self.filters[match].safe ? false : self.escape
        self.out.splice(
          self.filterApplyIdx[self.filterApplyIdx.length - 1],
          0,
          '_filters["' + match + '"]('
        )
        self.out.push(')')
        break

      case _t.FUNCTION:
      case _t.FUNCTIONEMPTY:
        self.out.push(
          '((typeof _ctx.' +
            match +
            ' !== "undefined") ? _ctx.' +
            match +
            ' : ((typeof ' +
            match +
            ' !== "undefined") ? ' +
            match +
            ' : _fn))('
        )
        self.escape = false
        if (token.type === _t.FUNCTIONEMPTY) {
          self.out[self.out.length - 1] = self.out[self.out.length - 1] + ')'
        } else {
          self.state.push(token.type)
        }
        self.filterApplyIdx.push(self.out.length - 1)
        break

      case _t.PARENOPEN:
        self.state.push(token.type)
        if (self.filterApplyIdx.length) {
          self.out.splice(
            self.filterApplyIdx[self.filterApplyIdx.length - 1],
            0,
            '('
          )
          if (prevToken && prevTokenType === _t.VAR) {
            temp = prevToken.match.split('.').slice(0, -1)
            self.out.push(' || _fn).call(' + self.checkMatch(temp))
            self.state.push(_t.METHODOPEN)
            self.escape = false
          } else {
            self.out.push(' || _fn)(')
          }
          self.filterApplyIdx.push(self.out.length - 3)
        } else {
          self.out.push('(')
          self.filterApplyIdx.push(self.out.length - 1)
        }
        break

      case _t.PARENCLOSE:
        temp = self.state.pop()
        if (
          temp !== _t.PARENOPEN &&
          temp !== _t.FUNCTION &&
          temp !== _t.FILTER
        ) {
          utils.throwError('Mismatched nesting state', self.line, self.filename)
        }
        self.out.push(')')
        // Once off the previous entry
        self.filterApplyIdx.pop()
        if (temp !== _t.FILTER) {
          // Once for the open paren
          self.filterApplyIdx.pop()
        }
        break

      case _t.COMMA:
        if (
          lastState !== _t.FUNCTION &&
          lastState !== _t.FILTER &&
          lastState !== _t.ARRAYOPEN &&
          lastState !== _t.CURLYOPEN &&
          lastState !== _t.PARENOPEN &&
          lastState !== _t.COLON
        ) {
          utils.throwError('Unexpected comma', self.line, self.filename)
        }
        if (lastState === _t.COLON) {
          self.state.pop()
        }
        self.out.push(', ')
        self.filterApplyIdx.pop()
        break

      case _t.LOGIC:
      case _t.COMPARATOR:
        if (
          !prevToken ||
          prevTokenType === _t.COMMA ||
          prevTokenType === token.type ||
          prevTokenType === _t.BRACKETOPEN ||
          prevTokenType === _t.CURLYOPEN ||
          prevTokenType === _t.PARENOPEN ||
          prevTokenType === _t.FUNCTION
        ) {
          utils.throwError('Unexpected logic', self.line, self.filename)
        }
        self.out.push(token.match)
        break

      case _t.NOT:
        self.out.push(token.match)
        break

      case _t.VAR:
        self.parseVar(token, match, lastState)
        break

      case _t.BRACKETOPEN:
        if (
          !prevToken ||
          (prevTokenType !== _t.VAR &&
            prevTokenType !== _t.BRACKETCLOSE &&
            prevTokenType !== _t.PARENCLOSE)
        ) {
          self.state.push(_t.ARRAYOPEN)
          self.filterApplyIdx.push(self.out.length)
        } else {
          self.state.push(token.type)
        }
        self.out.push('[')
        break

      case _t.BRACKETCLOSE:
        temp = self.state.pop()
        if (temp !== _t.BRACKETOPEN && temp !== _t.ARRAYOPEN) {
          utils.throwError(
            'Unexpected closing square bracket',
            self.line,
            self.filename
          )
        }
        self.out.push(']')
        self.filterApplyIdx.pop()
        break

      case _t.CURLYOPEN:
        self.state.push(token.type)
        self.out.push('{')
        self.filterApplyIdx.push(self.out.length - 1)
        break

      case _t.COLON:
        if (lastState !== _t.CURLYOPEN) {
          utils.throwError('Unexpected colon', self.line, self.filename)
        }
        self.state.push(token.type)
        self.out.push(':')
        self.filterApplyIdx.pop()
        break

      case _t.CURLYCLOSE:
        if (lastState === _t.COLON) {
          self.state.pop()
        }
        if (self.state.pop() !== _t.CURLYOPEN) {
          utils.throwError(
            'Unexpected closing curly brace',
            self.line,
            self.filename
          )
        }
        self.out.push('}')

        self.filterApplyIdx.pop()
        break

      case _t.DOTKEY:
        if (
          !prevToken ||
          (prevTokenType !== _t.VAR &&
            prevTokenType !== _t.BRACKETCLOSE &&
            prevTokenType !== _t.DOTKEY &&
            prevTokenType !== _t.PARENCLOSE &&
            prevTokenType !== _t.FUNCTIONEMPTY &&
            prevTokenType !== _t.FILTEREMPTY &&
            prevTokenType !== _t.CURLYCLOSE)
        ) {
          utils.throwError(
            'Unexpected key "' + match + '"',
            self.line,
            self.filename
          )
        }
        self.out.push('.' + match)
        break

      case _t.OPERATOR:
        self.out.push(' ' + match + ' ')
        self.filterApplyIdx.pop()
        break
    }
  },

  /**
   * Parse variable token
   * @param  {{match: string, type: number, line: number}} token      Lexer token object.
   * @param  {string} match       Shortcut for token.match
   * @param  {number} lastState   Lexer token type state.
   * @return {undefined}
   * @private
   */
  parseVar: function (token, match, lastState) {
    var self = this

    match = match.split('.')

    if (_reserved.indexOf(match[0]) !== -1) {
      utils.throwError(
        'Reserved keyword "' +
          match[0] +
          '" attempted to be used as a variable',
        self.line,
        self.filename
      )
    }

    self.filterApplyIdx.push(self.out.length)
    if (lastState === _t.CURLYOPEN) {
      if (match.length > 1) {
        utils.throwError('Unexpected dot', self.line, self.filename)
      }
      self.out.push(match[0])
      return
    }

    self.out.push(self.checkMatch(match))
  },

  /**
   * Return contextual dot-check string for a match
   * @param  {string} match       Shortcut for token.match
   * @private
   */
  checkMatch: function (match) {
    var temp = match[0]
    var result

    function checkDot (ctx) {
      var c = ctx + temp
      var m = match
      var build = ''

      build = '(typeof ' + c + ' !== "undefined" && ' + c + ' !== null'
      utils.each(m, function (v, i) {
        if (i === 0) {
          return
        }
        build +=
          ' && ' +
          c +
          '.' +
          v +
          ' !== undefined && ' +
          c +
          '.' +
          v +
          ' !== null'
        c += '.' + v
      })
      build += ')'

      return build
    }

    function buildDot (ctx) {
      return '(' + checkDot(ctx) + ' ? ' + ctx + match.join('.') + ' : "")'
    }
    result =
      '(' +
      checkDot('_ctx.') +
      ' ? ' +
      buildDot('_ctx.') +
      ' : ' +
      buildDot('') +
      ')'
    return '(' + result + ' !== null ? ' + result + ' : ' + '"" )'
  }
}

/**
 * Parse a source string into tokens that are ready for compilation.
 *
 * @example
 * exports.parse('{{ tacos }}', {}, tags, filters);
 * // => [{ compile: [Function], ... }]
 *
 * @params {object} swig    The current Swig instance
 * @param  {string} source  Swig template source.
 * @param  {object} opts    Swig options object.
 * @param  {object} tags    Keyed object of tags that can be parsed and compiled.
 * @param  {object} filters Keyed object of filters that may be applied to variables.
 * @return {array}          List of tokens ready for compilation.
 */
exports.parse = function (swig, source, opts, tags, filters) {
  source = source.replace(/\r\n/g, '\n')
  var escape = opts.autoescape
  var tagOpen = opts.tagControls[0]
  var tagClose = opts.tagControls[1]
  var varOpen = opts.varControls[0]
  var varClose = opts.varControls[1]
  var escapedTagOpen = escapeRegExp(tagOpen)
  var escapedTagClose = escapeRegExp(tagClose)
  var escapedVarOpen = escapeRegExp(varOpen)
  var escapedVarClose = escapeRegExp(varClose)
  var tagStrip = new RegExp(
    '^' + escapedTagOpen + '-?\\s*-?|-?\\s*-?' + escapedTagClose + '$',
    'g'
  )
  var tagStripBefore = new RegExp('^' + escapedTagOpen + '-')
  var tagStripAfter = new RegExp('-' + escapedTagClose + '$')
  var varStrip = new RegExp(
    '^' + escapedVarOpen + '-?\\s*-?|-?\\s*-?' + escapedVarClose + '$',
    'g'
  )
  var varStripBefore = new RegExp('^' + escapedVarOpen + '-')
  var varStripAfter = new RegExp('-' + escapedVarClose + '$')
  var cmtOpen = opts.cmtControls[0]
  var cmtClose = opts.cmtControls[1]
  var anyChar = '[\\s\\S]*?'
  // Split the template source based on variable, tag, and comment blocks
  // /(\{%[\s\S]*?%\}|\{\{[\s\S]*?\}\}|\{#[\s\S]*?#\})/
  var splitter = new RegExp(
    '(' +
      escapedTagOpen +
      anyChar +
      escapedTagClose +
      '|' +
      escapedVarOpen +
      anyChar +
      escapedVarClose +
      '|' +
      escapeRegExp(cmtOpen) +
      anyChar +
      escapeRegExp(cmtClose) +
      ')'
  )
  var line = 1
  var stack = []
  var parent = null
  var tokens = []
  var blocks = {}
  var inRaw = false
  var stripNext

  /**
   * Parse a variable.
   * @param  {string} str  String contents of the variable, between <i>{{</i> and <i>}}</i>
   * @param  {number} line The line number that this variable starts on.
   * @return {VarToken}      Parsed variable token object.
   * @private
   */
  function parseVariable (str, line) {
    var lexedTokens = lexer.read(utils.strip(str))
    var parser
    var out

    parser = new TokenParser(lexedTokens, filters, escape, line, opts.filename)
    out = parser.parse().join('')

    if (parser.state.length) {
      utils.throwError('Unable to parse "' + str + '"', line, opts.filename)
    }

    /**
     * A parsed variable token.
     * @typedef {object} VarToken
     * @property {function} compile Method for compiling this token.
     */
    return {
      compile: function () {
        return '_output += ' + out + ';\n'
      }
    }
  }
  exports.parseVariable = parseVariable

  /**
   * Parse a tag.
   * @param  {string} str  String contents of the tag, between <i>{%</i> and <i>%}</i>
   * @param  {number} line The line number that this tag starts on.
   * @return {TagToken}      Parsed token object.
   * @private
   */
  function parseTag (str, line) {
    var lexedTokens, parser, chunks, tagName, tag, args, last

    if (utils.startsWith(str, 'end')) {
      last = stack[stack.length - 1]
      if (
        last &&
        last.name === str.split(/\s+/)[0].replace(/^end/, '') &&
        last.ends
      ) {
        switch (last.name) {
          case 'autoescape':
            escape = opts.autoescape
            break
          case 'raw':
            inRaw = false
            break
        }
        stack.pop()
        return
      }

      if (!inRaw) {
        utils.throwError(
          'Unexpected end of tag "' + str.replace(/^end/, '') + '"',
          line,
          opts.filename
        )
      }
    }

    if (inRaw) {
      return
    }

    chunks = str.split(/\s+(.+)?/)
    tagName = chunks.shift()

    if (!tags.hasOwnProperty(tagName)) {
      utils.throwError('Unexpected tag "' + str + '"', line, opts.filename)
    }

    lexedTokens = lexer.read(utils.strip(chunks.join(' ')))
    parser = new TokenParser(lexedTokens, filters, false, line, opts.filename)
    tag = tags[tagName]

    /**
     * Define custom parsing methods for your tag.
     * @callback parse
     *
     * @example
     * exports.parse = function (str, line, parser, types, options, swig) {
     *   parser.on('start', function () {
     *     // ...
     *   });
     *   parser.on(types.STRING, function (token) {
     *     // ...
     *   });
     * };
     *
     * @param {string} str The full token string of the tag.
     * @param {number} line The line number that this tag appears on.
     * @param {TokenParser} parser A TokenParser instance.
     * @param {TYPES} types Lexer token type enum.
     * @param {TagToken[]} stack The current stack of open tags.
     * @param {SwigOpts} options Swig Options Object.
     * @param {object} swig The Swig instance (gives acces to loaders, parsers, etc)
     */
    if (!tag.parse(chunks[1], line, parser, _t, stack, opts, swig)) {
      utils.throwError('Unexpected tag "' + tagName + '"', line, opts.filename)
    }

    parser.parse()
    args = parser.out

    switch (tagName) {
      case 'autoescape':
        escape = args[0] !== 'false' ? args[0] : false
        break
      case 'raw':
        inRaw = true
        break
    }

    /**
     * A parsed tag token.
     * @typedef {Object} TagToken
     * @property {compile} [compile] Method for compiling this token.
     * @property {array} [args] Array of arguments for the tag.
     * @property {Token[]} [content=[]] An array of tokens that are children of this Token.
     * @property {boolean} [ends] Whether or not this tag requires an end tag.
     * @property {string} name The name of this tag.
     */
    return {
      block: !!tags[tagName].block,
      compile: tag.compile,
      args: args,
      content: [],
      ends: tag.ends,
      name: tagName
    }
  }

  /**
   * Strip the whitespace from the previous token, if it is a string.
   * @param  {object} token Parsed token.
   * @return {object}       If the token was a string, trailing whitespace will be stripped.
   */
  function stripPrevToken (token) {
    if (typeof token === 'string') {
      token = token.replace(/\s*$/, '')
    }
    return token
  }

  /*!
   * Loop over the source, split via the tag/var/comment regular expression splitter.
   * Send each chunk to the appropriate parser.
   */
  utils.each(source.split(splitter), function (chunk) {
    var token, lines, stripPrev, prevToken, prevChildToken

    if (!chunk) {
      return
    }

    // Is a variable?
    if (
      !inRaw &&
      utils.startsWith(chunk, varOpen) &&
      utils.endsWith(chunk, varClose)
    ) {
      stripPrev = varStripBefore.test(chunk)
      stripNext = varStripAfter.test(chunk)
      token = parseVariable(chunk.replace(varStrip, ''), line)
      // Is a tag?
    } else if (
      utils.startsWith(chunk, tagOpen) &&
      utils.endsWith(chunk, tagClose)
    ) {
      stripPrev = tagStripBefore.test(chunk)
      stripNext = tagStripAfter.test(chunk)
      token = parseTag(chunk.replace(tagStrip, ''), line)
      if (token) {
        if (token.name === 'extends') {
          parent = token.args
            .join('')
            .replace(/^'|'$/g, '')
            .replace(/^"|"$/g, '')
        } else if (token.block && !stack.length) {
          blocks[token.args.join('')] = token
        }
      }
      if (inRaw && !token) {
        token = chunk
      }
      // Is a content string?
    } else if (
      inRaw ||
      (!utils.startsWith(chunk, cmtOpen) && !utils.endsWith(chunk, cmtClose))
    ) {
      token = stripNext ? chunk.replace(/^\s*/, '') : chunk
      stripNext = false
    } else if (
      utils.startsWith(chunk, cmtOpen) &&
      utils.endsWith(chunk, cmtClose)
    ) {
      return
    }

    // Did this tag ask to strip previous whitespace? <code>{%- ... %}</code> or <code>{{- ... }}</code>
    if (stripPrev && tokens.length) {
      prevToken = tokens.pop()
      if (typeof prevToken === 'string') {
        prevToken = stripPrevToken(prevToken)
      } else if (prevToken.content && prevToken.content.length) {
        prevChildToken = stripPrevToken(prevToken.content.pop())
        prevToken.content.push(prevChildToken)
      }
      tokens.push(prevToken)
    }

    // This was a comment, so let's just keep going.
    if (!token) {
      return
    }

    // If there's an open item in the stack, add this to its content.
    if (stack.length) {
      stack[stack.length - 1].content.push(token)
    } else {
      tokens.push(token)
    }

    // If the token is a tag that requires an end tag, open it on the stack.
    if (token.name && token.ends) {
      stack.push(token)
    }

    lines = chunk.match(/\n/g)
    line += lines ? lines.length : 0
  })

  return {
    name: opts.filename,
    parent: parent,
    tokens: tokens,
    blocks: blocks
  }
}

/**
 * Compile an array of tokens.
 * @param  {Token[]} template     An array of template tokens.
 * @param  {Templates[]} parents  Array of parent templates.
 * @param  {SwigOpts} [options]   Swig options object.
 * @param  {string} [blockName]   Name of the current block context.
 * @return {string}               Partial for a compiled JavaScript method that will output a rendered template.
 */
exports.compile = function (template, parents, options, blockName) {
  var out = ''
  var tokens = utils.isArray(template) ? template : template.tokens

  utils.each(tokens, function (token) {
    var o
    if (typeof token === 'string') {
      out +=
        '_output += "' +
        token
          .replace(/\\/g, '\\\\')
          .replace(/\n|\r/g, '\\n')
          .replace(/"/g, '\\"') +
        '";\n'
      return
    }

    /**
     * Compile callback for VarToken and TagToken objects.
     * @callback compile
     *
     * @example
     * exports.compile = function (compiler, args, content, parents, options, blockName) {
     *   if (args[0] === 'foo') {
     *     return compiler(content, parents, options, blockName) + '\n';
     *   }
     *   return '_output += "fallback";\n';
     * };
     *
     * @param {parserCompiler} compiler
     * @param {array} [args] Array of parsed arguments on the for the token.
     * @param {array} [content] Array of content within the token.
     * @param {array} [parents] Array of parent templates for the current template context.
     * @param {SwigOpts} [options] Swig Options Object
     * @param {string} [blockName] Name of the direct block parent, if any.
     */
    o = token.compile(
      exports.compile,
      token.args ? token.args.slice(0) : [],
      token.content ? token.content.slice(0) : [],
      parents,
      options,
      blockName
    )
    out += o || ''
  })

  return out
}
