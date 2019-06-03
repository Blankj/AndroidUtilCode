var utils = require('./utils')
var _tags = require('./tags')
var _filters = require('./filters')
var parser = require('./parser')
var dateformatter = require('./dateformatter')
var loaders = require('./loaders')

/**
 * Swig version number as a string.
 * @example
 * if (swig.version === "1.4.2") { ... }
 *
 * @type {String}
 */
exports.version = '1.4.2'

/**
 * Swig Options Object. This object can be passed to many of the API-level Swig methods to control various aspects of the engine. All keys are optional.
 * @typedef {Object} SwigOpts
 * @property {boolean} autoescape  Controls whether or not variable output will automatically be escaped for safe HTML output. Defaults to <code data-language="js">true</code>. Functions executed in variable statements will not be auto-escaped. Your application/functions should take care of their own auto-escaping.
 * @property {array}   varControls Open and close controls for variables. Defaults to <code data-language="js">['{{', '}}']</code>.
 * @property {array}   tagControls Open and close controls for tags. Defaults to <code data-language="js">['{%', '%}']</code>.
 * @property {array}   cmtControls Open and close controls for comments. Defaults to <code data-language="js">['{#', '#}']</code>.
 * @property {object}  locals      Default variable context to be passed to <strong>all</strong> templates.
 * @property {CacheOptions} cache Cache control for templates. Defaults to saving in <code data-language="js">'memory'</code>. Send <code data-language="js">false</code> to disable. Send an object with <code data-language="js">get</code> and <code data-language="js">set</code> functions to customize.
 * @property {TemplateLoader} loader The method that Swig will use to load templates. Defaults to <var>swig.loaders.fs</var>.
 */
var defaultOptions = {
  autoescape: true,
  varControls: ['{{', '}}'],
  tagControls: ['{%', '%}'],
  cmtControls: ['{#', '#}'],
  locals: {},
  /**
   * Cache control for templates. Defaults to saving all templates into memory.
   * @typedef {boolean|string|object} CacheOptions
   * @example
   * // Default
   * swig.setDefaults({ cache: 'memory' });
   * @example
   * // Disables caching in Swig.
   * swig.setDefaults({ cache: false });
   * @example
   * // Custom cache storage and retrieval
   * swig.setDefaults({
   *   cache: {
   *     get: function (key) { ... },
   *     set: function (key, val) { ... }
   *   }
   * });
   */
  cache: 'memory',
  /**
   * Configure Swig to use either the <var>swig.loaders.fs</var> or <var>swig.loaders.memory</var> template loader. Or, you can write your own!
   * For more information, please see the <a href="../loaders/">Template Loaders documentation</a>.
   * @typedef {class} TemplateLoader
   * @example
   * // Default, FileSystem loader
   * swig.setDefaults({ loader: swig.loaders.fs() });
   * @example
   * // FileSystem loader allowing a base path
   * // With this, you don't use relative URLs in your template references
   * swig.setDefaults({ loader: swig.loaders.fs(__dirname + '/templates') });
   * @example
   * // Memory Loader
   * swig.setDefaults({ loader: swig.loaders.memory({
   *   layout: '{% block foo %}{% endblock %}',
   *   page1: '{% extends "layout" %}{% block foo %}Tacos!{% endblock %}'
   * })});
   */
  loader: loaders.fs()
}
var defaultInstance

/**
 * Empty function, used in templates.
 * @return {string} Empty string
 * @private
 */
function efn () {
  return ''
}

/**
 * Validate the Swig options object.
 * @param  {?SwigOpts} options Swig options object.
 * @return {undefined}      This method will throw errors if anything is wrong.
 * @private
 */
function validateOptions (options) {
  if (!options) {
    return
  }

  utils.each(['varControls', 'tagControls', 'cmtControls'], function (key) {
    if (!options.hasOwnProperty(key)) {
      return
    }
    if (!utils.isArray(options[key]) || options[key].length !== 2) {
      throw new Error(
        'Option "' +
          key +
          '" must be an array containing 2 different control strings.'
      )
    }
    if (options[key][0] === options[key][1]) {
      throw new Error(
        'Option "' + key + '" open and close controls must not be the same.'
      )
    }
    utils.each(options[key], function (a, i) {
      if (a.length < 2) {
        throw new Error(
          'Option "' +
            key +
            '" ' +
            (i ? 'open ' : 'close ') +
            'control must be at least 2 characters. Saw "' +
            a +
            '" instead.'
        )
      }
    })
  })

  if (options.hasOwnProperty('cache')) {
    if (options.cache && options.cache !== 'memory') {
      if (!options.cache.get || !options.cache.set) {
        throw new Error(
          'Invalid cache option ' +
            JSON.stringify(options.cache) +
            ' found. Expected "memory" or { get: function (key) { ... }, set: function (key, value) { ... } }.'
        )
      }
    }
  }
  if (options.hasOwnProperty('loader')) {
    if (options.loader) {
      if (!options.loader.load || !options.loader.resolve) {
        throw new Error(
          'Invalid loader option ' +
            JSON.stringify(options.loader) +
            ' found. Expected { load: function (pathname, cb) { ... }, resolve: function (to, from) { ... } }.'
        )
      }
    }
  }
}

/**
 * Set defaults for the base and all new Swig environments.
 *
 * @example
 * swig.setDefaults({ cache: false });
 * // => Disables Cache
 *
 * @example
 * swig.setDefaults({ locals: { now: function () { return new Date(); } }});
 * // => sets a globally accessible method for all template
 * //    contexts, allowing you to print the current date
 * // => {{ now()|date('F jS, Y') }}
 *
 * @param  {SwigOpts} [options={}] Swig options object.
 * @return {undefined}
 */
exports.setDefaults = function (options) {
  validateOptions(options)
  defaultInstance.options = utils.extend(defaultInstance.options, options)
}

/**
 * Set the default TimeZone offset for date formatting via the date filter. This is a global setting and will affect all Swig environments, old or new.
 * @param  {number} offset Offset from GMT, in minutes.
 * @return {undefined}
 */
exports.setDefaultTZOffset = function (offset) {
  dateformatter.tzOffset = offset
}

/**
 * Create a new, separate Swig compile/render environment.
 *
 * @example
 * var swig = require('swig');
 * var myswig = new swig.Swig({varControls: ['<%=', '%>']});
 * myswig.render('Tacos are <%= tacos =>!', { locals: { tacos: 'delicious' }});
 * // => Tacos are delicious!
 * swig.render('Tacos are <%= tacos =>!', { locals: { tacos: 'delicious' }});
 * // => 'Tacos are <%= tacos =>!'
 *
 * @param  {SwigOpts} [opts={}] Swig options object.
 * @return {object}      New Swig environment.
 */
exports.Swig = function (opts) {
  validateOptions(opts)
  this.options = utils.extend({}, defaultOptions, opts || {})
  this.cache = {}
  this.extensions = {}
  var self = this
  var tags = _tags
  var filters = _filters

  /**
   * Get combined locals context.
   * @param  {?SwigOpts} [options] Swig options object.
   * @return {object}         Locals context.
   * @private
   */
  function getLocals (options) {
    if (!options || !options.locals) {
      return self.options.locals
    }

    return utils.extend({}, self.options.locals, options.locals)
  }

  /**
   * Determine whether caching is enabled via the options provided and/or defaults
   * @param  {SwigOpts} [options={}] Swig Options Object
   * @return {boolean}
   * @private
   */
  function shouldCache (options) {
    options = options || {}
    return (
      (options.hasOwnProperty('cache') && !options.cache) || !self.options.cache
    )
  }

  /**
   * Get compiled template from the cache.
   * @param  {string} key           Name of template.
   * @return {object|undefined}     Template function and tokens.
   * @private
   */
  function cacheGet (key, options) {
    if (shouldCache(options)) {
      return
    }

    if (self.options.cache === 'memory') {
      return self.cache[key]
    }

    return self.options.cache.get(key)
  }

  /**
   * Store a template in the cache.
   * @param  {string} key Name of template.
   * @param  {object} val Template function and tokens.
   * @return {undefined}
   * @private
   */
  function cacheSet (key, options, val) {
    if (shouldCache(options)) {
      return
    }

    if (self.options.cache === 'memory') {
      self.cache[key] = val
      return
    }

    self.options.cache.set(key, val)
  }

  /**
   * Clears the in-memory template cache.
   *
   * @example
   * swig.invalidateCache();
   *
   * @return {undefined}
   */
  this.invalidateCache = function () {
    if (self.options.cache === 'memory') {
      self.cache = {}
    }
  }

  /**
   * Add a custom filter for swig variables.
   *
   * @example
   * function replaceMs(input) { return input.replace(/m/g, 'f'); }
   * swig.setFilter('replaceMs', replaceMs);
   * // => {{ "onomatopoeia"|replaceMs }}
   * // => onofatopeia
   *
   * @param {string}    name    Name of filter, used in templates. <strong>Will</strong> overwrite previously defined filters, if using the same name.
   * @param {function}  method  Function that acts against the input. See <a href="/docs/filters/#custom">Custom Filters</a> for more information.
   * @return {undefined}
   */
  this.setFilter = function (name, method) {
    if (typeof method !== 'function') {
      throw new Error('Filter "' + name + '" is not a valid function.')
    }
    filters[name] = method
  }

  /**
   * Add a custom tag. To expose your own extensions to compiled template code, see <code data-language="js">swig.setExtension</code>.
   *
   * For a more in-depth explanation of writing custom tags, see <a href="../extending/#tags">Custom Tags</a>.
   *
   * @example
   * var tacotag = require('./tacotag');
   * swig.setTag('tacos', tacotag.parse, tacotag.compile, tacotag.ends, tacotag.blockLevel);
   * // => {% tacos %}Make this be tacos.{% endtacos %}
   * // => Tacos tacos tacos tacos.
   *
   * @param  {string} name      Tag name.
   * @param  {function} parse   Method for parsing tokens.
   * @param  {function} compile Method for compiling renderable output.
   * @param  {boolean} [ends=false]     Whether or not this tag requires an <i>end</i> tag.
   * @param  {boolean} [blockLevel=false] If false, this tag will not be compiled outside of <code>block</code> tags when extending a parent template.
   * @return {undefined}
   */
  this.setTag = function (name, parse, compile, ends, blockLevel) {
    if (typeof parse !== 'function') {
      throw new Error(
        'Tag "' + name + '" parse method is not a valid function.'
      )
    }

    if (typeof compile !== 'function') {
      throw new Error(
        'Tag "' + name + '" compile method is not a valid function.'
      )
    }

    tags[name] = {
      parse: parse,
      compile: compile,
      ends: ends || false,
      block: !!blockLevel
    }
  }

  /**
   * Add extensions for custom tags. This allows any custom tag to access a globally available methods via a special globally available object, <var>_ext</var>, in templates.
   *
   * @example
   * swig.setExtension('trans', function (v) { return translate(v); });
   * function compileTrans(compiler, args, content, parent, options) {
   *   return '_output += _ext.trans(' + args[0] + ');'
   * };
   * swig.setTag('trans', parseTrans, compileTrans, true);
   *
   * @param  {string} name   Key name of the extension. Accessed via <code data-language="js">_ext[name]</code>.
   * @param  {*}      object The method, value, or object that should be available via the given name.
   * @return {undefined}
   */
  this.setExtension = function (name, object) {
    self.extensions[name] = object
  }

  /**
   * Parse a given source string into tokens.
   *
   * @param  {string} source  Swig template source.
   * @param  {SwigOpts} [options={}] Swig options object.
   * @return {object} parsed  Template tokens object.
   * @private
   */
  this.parse = function (source, options) {
    validateOptions(options)

    var locals = getLocals(options)
    var opt = {}
    var k

    for (k in options) {
      if (options.hasOwnProperty(k) && k !== 'locals') {
        opt[k] = options[k]
      }
    }

    options = utils.extend({}, self.options, opt)
    options.locals = locals

    return parser.parse(this, source, options, tags, filters)
  }

  /**
   * Parse a given file into tokens.
   *
   * @param  {string} pathname  Full path to file to parse.
   * @param  {SwigOpts} [options={}]   Swig options object.
   * @return {object} parsed    Template tokens object.
   * @private
   */
  this.parseFile = function (pathname, options) {
    var src

    if (!options) {
      options = {}
    }

    pathname = self.options.loader.resolve(pathname, options.resolveFrom)

    src = self.options.loader.load(pathname)

    if (!options.filename) {
      options = utils.extend({ filename: pathname }, options)
    }

    return self.parse(src, options)
  }

  /**
   * Re-Map blocks within a list of tokens to the template's block objects.
   * @param  {array}  tokens   List of tokens for the parent object.
   * @param  {object} template Current template that needs to be mapped to the  parent's block and token list.
   * @return {array}
   * @private
   */
  function remapBlocks (blocks, tokens) {
    return utils.map(tokens, function (token) {
      var args = token.args ? token.args.join('') : ''
      if (token.name === 'block' && blocks[args]) {
        token = blocks[args]
      }
      if (token.content && token.content.length) {
        token.content = remapBlocks(blocks, token.content)
      }
      return token
    })
  }

  /**
   * Import block-level tags to the token list that are not actual block tags.
   * @param  {array} blocks List of block-level tags.
   * @param  {array} tokens List of tokens to render.
   * @return {undefined}
   * @private
   */
  function importNonBlocks (blocks, tokens) {
    var temp = []
    utils.each(blocks, function (block) {
      temp.push(block)
    })
    utils.each(temp.reverse(), function (block) {
      if (block.name !== 'block') {
        tokens.unshift(block)
      }
    })
  }

  /**
   * Recursively compile and get parents of given parsed token object.
   *
   * @param  {object} tokens    Parsed tokens from template.
   * @param  {SwigOpts} [options={}]   Swig options object.
   * @return {object}           Parsed tokens from parent templates.
   * @private
   */
  function getParents (tokens, options) {
    var parentName = tokens.parent
    var parentFiles = []
    var parents = []
    var parentFile
    var parent
    var l

    while (parentName) {
      if (!options || !options.filename) {
        throw new Error(
          'Cannot extend "' +
            parentName +
            '" because current template has no filename.'
        )
      }

      parentFile = parentFile || options.filename
      parentFile = self.options.loader.resolve(parentName, parentFile)
      parent =
        cacheGet(parentFile, options) ||
        self.parseFile(
          parentFile,
          utils.extend({}, options, { filename: parentFile })
        )
      parentName = parent.parent

      if (parentFiles.indexOf(parentFile) !== -1) {
        throw new Error('Illegal circular extends of "' + parentFile + '".')
      }
      parentFiles.push(parentFile)

      parents.push(parent)
    }

    // Remap each parents'(1) blocks onto its own parent(2), receiving the full token list for rendering the original parent(1) on its own.
    l = parents.length
    for (l = parents.length - 2; l >= 0; l -= 1) {
      parents[l].tokens = remapBlocks(parents[l].blocks, parents[l + 1].tokens)
      importNonBlocks(parents[l].blocks, parents[l].tokens)
    }

    return parents
  }

  /**
   * Pre-compile a source string into a cache-able template function.
   *
   * @example
   * swig.precompile('{{ tacos }}');
   * // => {
   * //      tpl: function (_swig, _locals, _filters, _utils, _fn) { ... },
   * //      tokens: {
   * //        name: undefined,
   * //        parent: null,
   * //        tokens: [...],
   * //        blocks: {}
   * //      }
   * //    }
   *
   * In order to render a pre-compiled template, you must have access to filters and utils from Swig. <var>efn</var> is simply an empty function that does nothing.
   *
   * @param  {string} source  Swig template source string.
   * @param  {SwigOpts} [options={}] Swig options object.
   * @return {object}         Renderable function and tokens object.
   */
  this.precompile = function (source, options) {
    var tokens = self.parse(source, options)
    var parents = getParents(tokens, options)
    var tpl

    if (parents.length) {
      // Remap the templates first-parent's tokens using this template's blocks.
      tokens.tokens = remapBlocks(tokens.blocks, parents[0].tokens)
      importNonBlocks(tokens.blocks, tokens.tokens)
    }

    try {
      tpl = new Function( // eslint-disable-line
        '_swig',
        '_ctx',
        '_filters',
        '_utils',
        '_fn',
        '  var _ext = _swig.extensions,\n' +
          '    _output = "";\n' +
          parser.compile(tokens, parents, options) +
          '\n' +
          '  return _output;\n'
      )
    } catch (e) {
      utils.throwError(e, null, options.filename)
    }

    return { tpl: tpl, tokens: tokens }
  }

  /**
   * Compile and render a template string for final output.
   *
   * When rendering a source string, a file path should be specified in the options object in order for <var>extends</var>, <var>include</var>, and <var>import</var> to work properly. Do this by adding <code data-language="js">{ filename: '/absolute/path/to/mytpl.html' }</code> to the options argument.
   *
   * @example
   * swig.render('{{ tacos }}', { locals: { tacos: 'Tacos!!!!' }});
   * // => Tacos!!!!
   *
   * @param  {string} source    Swig template source string.
   * @param  {SwigOpts} [options={}] Swig options object.
   * @return {string}           Rendered output.
   */
  this.render = function (source, options) {
    return self.compile(source, options)()
  }

  /**
   * Compile and render a template file for final output. This is most useful for libraries like Express.js.
   *
   * @example
   * swig.renderFile('./template.html', {}, function (err, output) {
   *   if (err) {
   *     throw err;
   *   }
   *   console.log(output);
   * });
   *
   * @example
   * swig.renderFile('./template.html', {});
   * // => output
   *
   * @param  {string}   pathName    File location.
   * @param  {object}   [locals={}] Template variable context.
   * @param  {Function} [cb] Asyncronous callback function. If not provided, <var>compileFile</var> will run syncronously.
   * @return {string}             Rendered output.
   */
  this.renderFile = function (pathName, locals, cb) {
    if (cb) {
      self.compileFile(pathName, {}, function (err, fn) {
        var result

        if (err) {
          cb(err)
          return
        }

        try {
          result = fn(locals)
        } catch (err2) {
          cb(err2)
          return
        }

        cb(null, result)
      })
      return
    }

    return self.compileFile(pathName)(locals)
  }

  /**
   * Compile string source into a renderable template function.
   *
   * @example
   * var tpl = swig.compile('{{ tacos }}');
   * // => {
   * //      [Function: compiled]
   * //      parent: null,
   * //      tokens: [{ compile: [Function] }],
   * //      blocks: {}
   * //    }
   * tpl({ tacos: 'Tacos!!!!' });
   * // => Tacos!!!!
   *
   * When compiling a source string, a file path should be specified in the options object in order for <var>extends</var>, <var>include</var>, and <var>import</var> to work properly. Do this by adding <code data-language="js">{ filename: '/absolute/path/to/mytpl.html' }</code> to the options argument.
   *
   * @param  {string} source    Swig template source string.
   * @param  {SwigOpts} [options={}] Swig options object.
   * @return {function}         Renderable function with keys for parent, blocks, and tokens.
   */
  this.compile = function (source, options) {
    var key = options ? options.filename : null
    var cached = key ? cacheGet(key, options) : null
    var context
    var contextLength
    var pre

    if (cached) {
      return cached
    }

    context = getLocals(options)
    contextLength = utils.keys(context).length
    pre = self.precompile(source, options)

    function compiled (locals) {
      var lcls
      if (locals && contextLength) {
        lcls = utils.extend({}, context, locals)
      } else if (locals && !contextLength) {
        lcls = locals
      } else if (!locals && contextLength) {
        lcls = context
      } else {
        lcls = {}
      }
      return pre.tpl(self, lcls, filters, utils, efn)
    }

    utils.extend(compiled, pre.tokens)

    if (key) {
      cacheSet(key, options, compiled)
    }

    return compiled
  }

  /**
   * Compile a source file into a renderable template function.
   *
   * @example
   * var tpl = swig.compileFile('./mytpl.html');
   * // => {
   * //      [Function: compiled]
   * //      parent: null,
   * //      tokens: [{ compile: [Function] }],
   * //      blocks: {}
   * //    }
   * tpl({ tacos: 'Tacos!!!!' });
   * // => Tacos!!!!
   *
   * @example
   * swig.compileFile('/myfile.txt', { varControls: ['<%=', '=%>'], tagControls: ['<%', '%>']});
   * // => will compile 'myfile.txt' using the var and tag controls as specified.
   *
   * @param  {string} pathname  File location.
   * @param  {SwigOpts} [options={}] Swig options object.
   * @param  {Function} [cb] Asyncronous callback function. If not provided, <var>compileFile</var> will run syncronously.
   * @return {function}         Renderable function with keys for parent, blocks, and tokens.
   */
  this.compileFile = function (pathname, options, cb) {
    var src, cached

    if (!options) {
      options = {}
    }

    pathname = self.options.loader.resolve(pathname, options.resolveFrom)
    if (!options.filename) {
      options = utils.extend({ filename: pathname }, options)
    }
    cached = cacheGet(pathname, options)

    if (cached) {
      if (cb) {
        cb(null, cached)
        return
      }
      return cached
    }

    if (cb) {
      self.options.loader.load(pathname, function (err, src) {
        if (err) {
          cb(err)
          return
        }
        var compiled

        try {
          compiled = self.compile(src, options)
        } catch (err2) {
          cb(err2)
          return
        }

        cb(err, compiled)
      })
      return
    }

    src = self.options.loader.load(pathname)
    return self.compile(src, options)
  }

  /**
   * Run a pre-compiled template function. This is most useful in the browser when you've pre-compiled your templates with the Swig command-line tool.
   *
   * @example
   * $ swig compile ./mytpl.html --wrap-start="var mytpl = " > mytpl.js
   * @example
   * <script src="mytpl.js"></script>
   * <script>
   *   swig.run(mytpl, {});
   *   // => "rendered template..."
   * </script>
   *
   * @param  {function} tpl       Pre-compiled Swig template function. Use the Swig CLI to compile your templates.
   * @param  {object} [locals={}] Template variable context.
   * @param  {string} [filepath]  Filename used for caching the template.
   * @return {string}             Rendered output.
   */
  this.run = function (tpl, locals, filepath) {
    var context = getLocals({ locals: locals })
    if (filepath) {
      cacheSet(filepath, {}, tpl)
    }
    return tpl(self, context, filters, utils, efn)
  }
}

/*!
 * Export methods publicly
 */
defaultInstance = new exports.Swig()
exports.setFilter = defaultInstance.setFilter
exports.setTag = defaultInstance.setTag
exports.setExtension = defaultInstance.setExtension
exports.parseFile = defaultInstance.parseFile
exports.precompile = defaultInstance.precompile
exports.compile = defaultInstance.compile
exports.compileFile = defaultInstance.compileFile
exports.render = defaultInstance.render
exports.renderFile = defaultInstance.renderFile
exports.run = defaultInstance.run
exports.invalidateCache = defaultInstance.invalidateCache
exports.loaders = loaders
