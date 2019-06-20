[1.4.2](https://github.com/paularmstrong/swig/tree/v1.4.2) / 2014-08-04
-----------------------------------------------------------------------

* **Added** Report JS parse errors with template filenames. gh-492
* **Fixed** Ensure block-level tags (`set`, etc) are parsed in correct order. gh-495
* **Fixed** Ensure import tag uses current Swig instance's loader. gh-421, gh-503
* **Fixed** Allow disabling cache on compile/render functions directly. gh-423
* **Fixed** Ensure compilation does not leak global variables. gh-496
* **Fixed** Fix for-loops to run on strings. gh-478, gh-479
* **Fixed** Allow macro output to be assigned using `set` tag. gh-499, gh-502

[1.4.1](https://github.com/paularmstrong/swig/tree/v1.4.1) / 2014-07-03
-----------------------------------------------------------------------

* **Fixed** `macro` argument names colliding with context variable names. gh-457
* **Fixed** filter chaining within tags. gh-441

[1.4.0](https://github.com/paularmstrong/swig/tree/v1.4.0) / 2014-07-03
-----------------------------------------------------------------------

* **Changed** Allow variable tokens to start with `$`. gh-455
* **Changed** `fs` loader should take `cwd` as default base path. gh-419
* **Changed** handle errors which occur at the time of rendering. gh-417
* **Changed** default options in bin (`varControls`, `tagControls`, `cmtControls`). gh-415
* **Changed** `null` should yield empty string when resolving variable. gh-408
* **Added** Escape character for `date` filter argument. gh-427, gh-432
* **Added** Make `if` and `elseif` throw a better error message when a tag body is omitted. gh-425
* **Fixed** don't throw errors on accessing property of `null` object. gh-471
* **Fixed** `loop` variables work correctly in nested loops. gh-433
* **Fixed** Some IE8 compatibility (require es5). gh-428

[1.3.2](https://github.com/paularmstrong/swig/tree/v1.3.2) / 2014-01-27
-----------------------------------------------------------------------

* **Fixed** `for` loop variables on objects. gh-409
* **Fixed** Misc. IE8 fixes. gh-410
* **Fixed** `include` files when loaders have base paths. gh-407

[1.3.0](https://github.com/paularmstrong/swig/tree/v1.3.0) / 2014-01-20
-----------------------------------------------------------------------

* **Changed** Removed official node v0.8.x support
* **Added** Custom template loader support. gh-377, gh-384, gh-382
* **Added** Ability to set root path using template loaders. gh-382, gh-293
* **Added** CLI now accepts custom filter and tag arguments. gh-391
* **Added** Allow `set` tag to set keys on objects with bracket and dot-notation. gh-388
* **Added** `groupBy` filter from swig-extras. gh-383
* **Fixed** `swig.run` `filepath` arg is always optional. gh-402
* **Fixed** Filters on non-empty functions apply correctly. gh-397
* **Fixed** Filters applied to functions w/ & w/o dotkeys. gh-365
* **Fixed** `date` filter `N` option returns correct number. gh-375
* **Fixed** Ensure getting parent template checks cache if on. gh-378

[1.2.2](https://github.com/paularmstrong/swig/tree/v1.2.2) / 2013-12-02
-----------------------------------------------------------------------

* **Fixed** CTX var output in imported macros. gh-363

[1.2.1](https://github.com/paularmstrong/swig/tree/v1.2.1) / 2013-12-02
-----------------------------------------------------------------------

* **Fixed** Scoping for Express. gh-363

[1.2.0](https://github.com/paularmstrong/swig/tree/v1.2.0) / 2013-12-01
-----------------------------------------------------------------------

* **Added** Filepath parameter can be passed to swig.run to allow extends in-browser. gh-349
* **Changed** Use local-context first for var lookups. gh-344, gh-347
* **Changed** Allow DOTKEY after functions/objects/filters. gh-355
* **Changed** Context of for-tags carries into includes. gh-356
* **Changed** When a callback is passed into compileFile, catch all errors thrown by compile and pass the error to callback. gh-340
* **Fixed** Instances of Swig retain their options properly. gh-351
* **Fixed** Fix misc documentation issues. gh-359, gh-358

[1.1.0](https://github.com/paularmstrong/swig/tree/v1.1.0) / 2013-10-02
-----------------------------------------------------------------------

* **Added** Allow logic in default parsing. gh-326
* **Fixed** Error when attempting to wrap spaceless tag around macro/function output. gh-336
* **Fixed** Don't overwrite keys on the locals object. gh-337

[1.0.0](https://github.com/paularmstrong/swig/tree/v1.0.0) / 2013-09-23
-----------------------------------------------------------------------

* **Fixed** Allow parent and other tags to work correctly nested in other tags. gh-331
* **Fixed** Prevent lexer from matching partial logic/words in variables. gh-330

Migrating from v0.x.x? View the [Migration Guide](https://github.com/paularmstrong/swig/wiki/Migrating-from-v0.x.x-to-v1.0.0)

[1.0.0-rc3](https://github.com/paularmstrong/swig/tree/v1.0.0-rc3) / 2013-09-14
-------------------------------------------------------------------------------

* **Fixed** Allow bools in token parser by default. gh-321
* **Fixed** Allow variables as object values. gh-323
* **Fixed** Don't partially match logic words. gh-322
* **Fixed** Parent tag in parent's block with no local block edge case. gh-316

[1.0.0-rc2](https://github.com/paularmstrong/swig/tree/v1.0.0-rc2) / 2013-09-06
-------------------------------------------------------------------------------

* **Changed** Function output from `variable` blocks are no longer auto-escaped. gh-309
* **Fixed** Allow nested macros to work when importing. gh-310
* **Fixed** swig.setDefaultTZOffset. gh-311
* **Changed** `set` tag assigns to the local context, allowing setting within `for` loops, etc. gh-303
* **Fixed** Standardize variable undefined checking. gh-301
* **Fixed** Remove multiple redefinition of block-level tags in compiled templates.
* **Fixed** Performance issue with compile if no default locals are defined.

[1.0.0-rc1](https://github.com/paularmstrong/swig/tree/v1.0.0-rc1) / 2013-08-28
-------------------------------------------------------------------------------

* **Added** `include` tag now accepts `only` (and is preferred, if possible). gh-240
* **Added** `swig.version` and `-v` to cli
* **Changed** Deprecated `raw` filter. Use `safe`.
* **Changed** Allow `import` and `macro` tags to be outside of blocks. gh-299
* **Changed** Don't escape `macro` output. gh-297
* **Changed** (Custom) Filters can be marked as `safe` to disable auto-escaping. gh-294
* **Fixed** `{% for k,v ... %}` tag syntax assigned variables backwards.
* **Fixed** Filters being applied to empty functions throwing errors. gh-296
* **Fixed** `include` paths on windows. gh-295

[1.0.0-pre3](https://github.com/paularmstrong/swig/tree/v1.0.0-pre3) / 2013-08-20
---------------------------------------------------------------------------------

* **Changed** Allow tags at block-level if specified. [gh-289](https://github.com/paularmstrong/swig/issues/289)
* **Fixed** `swig.compileFile` runs callback template is found in cache. [gh-291](https://github.com/paularmstrong/swig/issues/291)
* **Fixed** Accidental modification of Swig Options Object. [gh-287](https://github.com/paularmstrong/swig/issues/287)
* **Fixed** Preserve forward-slashes in text chunks. [gh-285](https://github.com/paularmstrong/swig/issues/285)

[1.0.0-pre2](https://github.com/paularmstrong/swig/tree/v1.0.0-pre2) / 2013-08-18
---------------------------------------------------------------------------------

* **Changed** Binary: Allow --method-name to be a shortcut for --wrap-start var setting.
* **Changed** Make reverse filter an alias for `sort(true)`.
* **Added** Allow asyncronous `compileFile` and `renderFile` operations. [gh-283](https://github.com/paularmstrong/swig/issues/283)
* **Added** Filter: `sort`.
* **Added** Allow {% end[tag] tokens... %}. [gh-278](https://github.com/paularmstrong/swig/issues/278)
* **Added** Built source map for minified browser source.
* **Added** Contextual support for object method calls. [gh-275](https://github.com/paularmstrong/swig/issues/275)
* **Added** `parser.on('start'|'end'...` options. [gh-274](https://github.com/paularmstrong/swig/issues/274)
* **Added** Allow object prototypal inheritance. [gh-273](https://github.com/paularmstrong/swig/issues/273)
* **Fixed** Prevent circular extends. [gh-282](https://github.com/paularmstrong/swig/issues/282)
* **Fixed** Throw an error if reserved word is used as var. [gh-276](https://github.com/paularmstrong/swig/issues/276)
* **Fixed** Add filename to errors if possible. [gh-280](https://github.com/paularmstrong/swig/issues/280)
* **Fixed** Filters work over arrays/objects if possible. [gh-259](https://github.com/paularmstrong/swig/issues/259)
* **Fixed** Allow {% parent %} to work in middle parent templates. [gh-277](https://github.com/paularmstrong/swig/issues/277)
* **Fixed** Allow newlines in tags/vars/comments. [gh-272](https://github.com/paularmstrong/swig/issues/272)

[1.0.0-pre1](https://github.com/paularmstrong/swig/tree/v1.0.0-pre1) / 2013-08-14
---------------------------------------------------------------------------------

* **Changed** Completely rewritting parsing engine supports many more syntaxes and is much easier to extend.
* **Changed** There is no more `swig.init` method.
* **Changed** Custom filters can be added using `swig.addFilter`
* **Changed** Custom tags can be added using `swig.addTag`
* **Changed** Writing custom tags uses an entirely new, simplified format
* **Changed** Removed the underscore/lodash dependency
* **Changed** Template parsing has been completely rewritten
* **Changed** `swig.compileFile` returns a function that renders templates, not an object
* **Changed** Express-compatible using `swig.renderFile`.
* **Changed** `extends`, `import`, and `include` now reference files with relative paths from the current file ([info](https://github.com/paularmstrong/swig/wiki/Migrating-from-v0.x.x-to-v1.0.0#extends-include-import-changes)).
* **Changed** `extends` may no longer accept variables ([info](https://github.com/paularmstrong/swig/wiki/Migrating-from-v0.x.x-to-v1.0.0#extends-include-import-changes)).
* **Changed** `else if` tag is now `elseif` or `elif`.
* **Changed** Removed `only` argument from `include`.
* **Changed** allow `_`, `$` to start var names in templates.
* **Changed** Documentation is auto-generated from jsdoc comments in-files.
* **Added** Ability to set custom var/tag/comment controls (`{{`, `}}`, etc, can be customized).
* **Added** Variable/string concatenation [gh-135](https://github.com/paularmstrong/swig/issues/135).
* **Added** Binary application for `compile`, `run`, and `render` (Lets you pre-compile templates into JS functions for client-side delivery).
* **Fixed** Lots.

[0.14.0](https://github.com/paularmstrong/swig/tree/v0.14.0) / 2013-06-08
-------------------------------------------------------------------------

* **Added** Allow executing functions from within templates [gh-182](https://github.com/paularmstrong/swig/pull/182)
* **Added** New `spaceless` tag [gh-193](https://github.com/paularmstrong/swig/pull/193)
* **Fixed** bug when attempting to loop over nested vars with `for`. [gh-232](https://github.com/paularmstrong/swig/pull/232)

[0.13.5](https://github.com/paularmstrong/swig/tree/v0.13.5) / 2013-01-29
-------------------------------------------------------------------------

* **Fixed** date filter output for 'O' when time-zone offset is negative [gh-185](https://github.com/paularmstrong/swig/pull/185)

[0.13.4](https://github.com/paularmstrong/swig/tree/v0.13.4) / 2012-12-19
-------------------------------------------------------------------------

* **Fixed** Runaway loop on missing template [gh-162](https://github.com/paularmstrong/swig/pull/162) [gh-165](https://github.com/paularmstrong/swig/pull/165)
* **Fixed** Allow variables in if tag conditionals to have filters with arguments [gh-167](https://github.com/paularmstrong/swig/pull/167)

[0.13.3](https://github.com/paularmstrong/swig/tree/v0.13.3) / 2012-12-07
-------------------------------------------------------------------------

* **Added** Support % (modulus) in if tags [gh-155](https://github.com/paularmstrong/swig/pull/155)
* **Added** Support multi-root via array [gh-143](https://github.com/paularmstrong/swig/pull/143)

[0.13.2](https://github.com/paularmstrong/swig/tree/v0.13.2) / 2012-10-28
-------------------------------------------------------------------------

* **Changed** Allow variables, filters, arguments to span lines [gh-122](https://github.com/paularmstrong/swig/issues/122)
* **Changed** Throw Errors when using undefined filters [gh-115](https://github.com/paularmstrong/swig/issues/115)
* **Fixed** compiling files from absolute paths [gh-103](https://github.com/paularmstrong/swig/issues/103)
* **Fixed** Prevent global variables from being used before context variables [gh-117](https://github.com/paularmstrong/swig/issues/117)

[Documentation](https://github.com/paularmstrong/swig/tree/v0.13.2/docs)

[0.13.1](https://github.com/paularmstrong/swig/tree/v0.13.1) / 2012-10-28
-------------------------------------------------------------------------

* **Fixed** Macros should be preserved when using inheritence [gh-132](https://github.com/paularmstrong/swig/issues/132) ([nsaun](https://github.com/nsaun))
* **Fixed** bug in parent tag logic [gh-130](https://github.com/paularmstrong/swig/issues/130)
* **Fixed** Error messaging when parent block failed compilation [gh-129](https://github.com/paularmstrong/swig/issues/129) ([nsaun](https://github.com/nsaun))

[Documentation](https://github.com/paularmstrong/swig/tree/v0.13.1/docs)

[0.13.0](https://github.com/paularmstrong/swig/tree/v0.13.0) / 2012-10-20
-------------------------------------------------------------------------

* **Added** Support for nested blocks! [gh-64](https://github.com/paularmstrong/swig/issues/64) [gh-129](https://github.com/paularmstrong/swig/issues/129) ([nsaun](https://github.com/nsaun))
* **Changed** Removed the `parentBlock` argument from tags.
* **Fixed** Object keys may now contain dots

[Documentation](https://github.com/paularmstrong/swig/tree/v0.13.0/docs)

[0.12.1](https://github.com/paularmstrong/swig/tree/v0.12.1) / 2012-10-05
-------------------------------------------------------------------------

* **Added** More information on some parser errors
* **Added** indent parameter to json_encode filter to support pretty-printing.
* **Added** support for variables as `extends` tag parameters
* **Fixed** Compile errors in Android and other random browsers
* **Fixed** Misc documentation
* **Fixed** Leaking __keys variable into global scope
*
[Documentation](https://github.com/paularmstrong/swig/tree/v0.12.1/docs)

[0.12.0](https://github.com/paularmstrong/swig/tree/v0.12.0) / 2012-07-26
-------------------------------------------------------------------------

* **Fixed** Misc documenation
* **Changed** Support Node.js >=v0.6

[Documentation](https://github.com/paularmstrong/swig/tree/v0.12.0/docs)

[0.11.2](https://github.com/paularmstrong/swig/tree/v0.11.2) / 2012-04-10
-------------------------------------------------------------------------

* **Fixed** Update support for underscore@1.3.3 [gh-70](https://github.com/paularmstrong/swig/issues/70) [gh-71](https://github.com/paularmstrong/swig/issues/71)

[Documentation](https://github.com/paularmstrong/swig/tree/v0.11.2/docs)

[0.11.1](https://github.com/paularmstrong/swig/tree/v0.11.1) / 2012-04-01
-------------------------------------------------------------------------

* **Fixed** Duplicate (string) tokens were being removed when extending a base template. [gh-67](https://github.com/paularmstrong/swig/issues/67)

[Documentation](https://github.com/paularmstrong/swig/tree/v0.11.1/docs)

[0.11.0](https://github.com/paularmstrong/swig/tree/v0.11.0) / 2012-02-27
-------------------------------------------------------------------------

* **Added** Support for Windows style paths [gh-57](https://github.com/paularmstrong/swig/issues/57)
* **Added** `ignore missing` tokens to include tag
* **Changed** include tag `with context` to only work if `context` is an object
* **Changed** `autoescape` tag controls no longer 'yes' or 'no'. Use `true` and `false`
* **Changed** parser is now passed into tags as an argument
* **Changed** don't require passing context object when rendering template
* **Fixed** dateformats `N` and `w` [gh-59](https://github.com/paularmstrong/swig/issues/59)
* **Fixed** number changing to string after add filter or set from variable [gh-53](https://github.com/paularmstrong/swig/issues/53) [gh-58](https://github.com/paularmstrong/swig/issues/58)
* **Fixed** speed decrease caused by loop.cycle fixed
* **Fixed** Ensure set tag bubbles through extends and blocks

[Documentation](https://github.com/paularmstrong/swig/tree/v0.11.0/docs)

[0.10.0](https://github.com/paularmstrong/swig/tree/v0.10.0) / 2012-02-13
-------------------------------------------------------------------------

* **Added** loop.index0, loop.revindex, loop.revindex0, and loop.cycle [gh-48](https://github.com/paularmstrong/swig/issues/48)
* **Added** init config `extensions` for 3rd party extension access in custom tags [gh-44](https://github.com/paularmstrong/swig/issues/44)
* **Added** Whitespace Control [gh-46](https://github.com/paularmstrong/swig/issues/46)
* **Changed** The `empty` tag in `for` loops is now `else` [gh-49](https://github.com/paularmstrong/swig/issues/49)
* **Changed** `forloop` vars to `loop` closes [gh-47](https://github.com/paularmstrong/swig/issues/47)
* **Fixed** `include` tag's `with` and `only` args documentation [gh-50](https://github.com/paularmstrong/swig/issues/50)

[Documentation](https://github.com/paularmstrong/swig/tree/v0.10.0/docs)

[0.9.4](https://github.com/paularmstrong/swig/tree/v0.9.4) / 2012-02-07
-----------------------------------------------------------------------

* **Fixed** `parent` tag would not render when called within tags [gh-41](https://github.com/paularmstrong/swig/issues/41)
* **Fixed** Documentation for forloop.index & forloop.key [gh-42](https://github.com/paularmstrong/swig/issues/42)
* **Fixed** Errors when using `include` inside base template `block` tags [gh-43](https://github.com/paularmstrong/swig/issues/43)
* **Fixed** Allow `set` tag to set values to numbers [gh-45](https://github.com/paularmstrong/swig/issues/45)
* **Fixed** `set` tag for booleans using too many checks

[Documentation](https://github.com/paularmstrong/swig/tree/v0.9.4/docs)

[0.9.3](https://github.com/paularmstrong/swig/tree/v0.9.3) / 2012-01-28
-----------------------------------------------------------------------

* **Fixed** Allow object and array values to be accessed via context variables [gh-40](https://github.com/paularmstrong/swig/issues/40)

[Documentation](https://github.com/paularmstrong/swig/tree/v0.9.3/docs)

[0.9.2](https://github.com/paularmstrong/swig/tree/v0.9.2) / 2012-01-23
-----------------------------------------------------------------------

* **Fixed** Correctly reset autoescape after closing an autoescape tag. [gh-39](https://github.com/paularmstrong/swig/issues/39)

[Documentation](https://github.com/paularmstrong/swig/tree/v0.9.2/docs)

[0.9.1](https://github.com/paularmstrong/swig/tree/v0.9.1) / 2012-01-18
-----------------------------------------------------------------------

* **Fixed** Allow multi-line tags and comments. [gh-30](https://github.com/paularmstrong/swig/issues/30)

[Documentation](https://github.com/paularmstrong/swig/tree/v0.9.1/docs)

[0.9.0](https://github.com/paularmstrong/swig/tree/v0.9.0) / 2011-12-30
-----------------------------------------------------------------------

* **Added** DateZ license to browser header, use link to underscore license.
* **Added** Timezone support in `date` filter [gh-27](https://github.com/paularmstrong/swig/issues/27).
* **Added** New `raw` tag.
* **Changed** Swig is no longer node 0.4 compatible.
* **Fixed** Filter `date('f')` for 10am times.
* **Fixed** Filter `date('r')` returns in UTC date format. This is more correct tospec RFC2822, per [php.net/date](http://php.net/date).
* **Fixed** Filter `add` when adding numbers/numbers+strings together.
* **Fixed** Tests for error messages that changed in node >0.6.0.

[Documentation](https://github.com/paularmstrong/swig/tree/v0.9.0/docs)

[0.8.0](https://github.com/paularmstrong/swig/tree/v0.8.0) / 2011-11-04
-----------------------------------------------------------------------

* **Added** date filter formats `z`, `W`, `t`, `L`, `o`, `B`, and `c`.
* **Added** New `filter` tag.
* **Added** Node.js compatible 0.4.1 - 0.6.X
* **Added** Allow setting cache globally or per-template.
* **Changed** Removed `swig.render` and `swig.fromString`.
* **Changed** `swig.fromFile` is now `swig.compileFile`.
* **Changed** `swig.init()` will clear template cache.
* **Changed** `swig.init()` is now optional for browser mode with no custom settings.
* **Changed** Development dependencies are be more lenient.
* **Fixed** Parser will properly preserver '\' escaping. [gh-24](https://github.com/paularmstrong/swig/issues/24)
* **Fixed** Rewrote tag argument parsing for proper space handling.
* **Fixed** Rewrote filter argument parsing. [gh-23](https://github.com/paularmstrong/swig/issues/23)
* **Fixed** Allow pipe `|` characters in filter arguments. [gh-22](https://github.com/paularmstrong/swig/issues/22)

[Documentation](https://github.com/paularmstrong/swig/tree/v0.8.0/docs)

[0.7.0](https://github.com/paularmstrong/swig/tree/v0.7.0) / 2011-10-05
-----------------------------------------------------------------------

* **Added** `make browser` will build Swig for use in major browsers. [gh-3](https://github.com/paularmstrong/swig/issues/3)
* **Changed** Allow overriding `escape` filters. [gh-19](https://github.com/paularmstrong/swig/issues/19)

[Documentation](https://github.com/paularmstrong/swig/tree/v0.7.0/docs)

[0.6.1](https://github.com/paularmstrong/swig/tree/v0.6.1) / 2011-10-02
-----------------------------------------------------------------------

* **Fixed** chaining filters when the first takes a variable as an argument will not crash parsing.

[Documentation](https://github.com/paularmstrong/swig/tree/v0.6.1/docs)

[0.6.0](https://github.com/paularmstrong/swig/tree/v0.6.0) / 2011-10-02
-----------------------------------------------------------------------

* **Added** `{% import foo as bar %}` tag for importing macros.
* **Added** Allow escaping for js in escape filter and autoescape tag.
* **Added** `raw` filter to force variable to not be escaped.
* **Added** `escape` and `e` filters to force variable to be escaped.
* **Added** Allow filters to accept any JS objects, arrays, strings, and context variables.
* **Changed** `if`, `else`, and `else if` tags support all JS-valid if-syntaxes + extra operators.
* **Fixed** `default` filter for undefined variables. closes gh-18

[Documentation](https://github.com/paularmstrong/swig/tree/v0.6.0/docs)

[0.5.0](https://github.com/paularmstrong/swig/tree/v0.5.0) / 2011-09-27
-----------------------------------------------------------------------

* **Added** More error messaging in some edge cases.
* **Added** Better error messaging including context and line numbers.
* **Changed** Improved compile and render speeds.
* **Changed** `include` tags accept context variables instead of just strings.
* **Changed** Templates can be compiled and rendered from an absolute path outside of the template root.
* **Fixed** Will not double escape output.

[Documentation](https://github.com/paularmstrong/swig/tree/v0.5.0/docs)

[0.4.0](https://github.com/paularmstrong/swig/tree/v0.4.0) / 2011-09-24
-----------------------------------------------------------------------

* **Added** Macro support [docs](docs/tags.md)
* **Changed** Removed requirement to manually specify `locals` for express support.
* **Changed** Increased cache lookup speed by removing crypto dependency.
* **Fixed** `length` filter returns length of objects (number of keys).
* **Fixed** Filters return empty string unless they can apply to the given object.
* **Fixed** Filters will attempt to apply to all values in an object or array.

[Documentation](https://github.com/paularmstrong/swig/tree/v0.4.0/docs)

[0.3.0](https://github.com/paularmstrong/swig/tree/v0.3.0) / 2011-09-17
-----------------------------------------------------------------------

* **Added** Support for `{% set ... %}` tag.

[Documentation](https://github.com/paularmstrong/swig/tree/v0.3.0/docs)

[0.2.3](https://github.com/paularmstrong/swig/tree/v0.2.3) / 2011-09-16
-----------------------------------------------------------------------

* **Fixed** Critical fix for negations in `if` blocks.
* **Added** Support for `forloop.first` in `for` blocks.
* **Added** Support for `forloop.last` in `for` blocks.
* **Added** Support for `forloop.key` in `for` blocks.
* **Added** Support for `{% empty %}` in `for` blocks.

[Documentation](https://github.com/paularmstrong/swig/tree/v0.2.3/docs)

[0.2.2](https://github.com/paularmstrong/swig/tree/v0.2.2) / 2011-09-16
-----------------------------------------------------------------------

* **Added** Support for `else if ...` within `if` blocks.

[Documentation](https://github.com/paularmstrong/swig/tree/v0.2.2/docs)

[0.2.1](https://github.com/paularmstrong/swig/tree/v0.2.1) / 2011-09-13
-----------------------------------------------------------------------

* **Added** Support for `else` within `if` blocks.

[Documentation](https://github.com/paularmstrong/swig/tree/v0.2.1/docs)

[0.2.0](https://github.com/paularmstrong/swig/tree/v0.2.0) / 2011-09-11
-----------------------------------------------------------------------

* **Fixed** `if` statements allow filters applied to operands.
* **Fixed** `for` loops allow filters applied to the object that will be iterated over.

[Documentation](https://github.com/paularmstrong/swig/tree/v0.2.0/docs)

[0.1.9](https://github.com/paularmstrong/swig/tree/v0.1.9) / 2011-09-11
-----------------------------------------------------------------------

* **Added** `allowErrors` flag will allow errors to be thrown and bubbled up. Default to catch errors.
* **Changed** Internal speed improvements.

[Documentation](https://github.com/paularmstrong/swig/tree/v0.1.9/docs)

[0.1.8](https://github.com/paularmstrong/swig/tree/v0.1.8) / 2011-09-10
-----------------------------------------------------------------------

* **Added** `add`, `addslashes`, and `replace` filters.
* **Changed** All tags that 'end' must use named ends like `endblock`, `endif`, `endfor`, etc...

[Documentation](https://github.com/paularmstrong/swig/tree/v0.1.8/docs)

[0.1.7](https://github.com/paularmstrong/swig/tree/v0.1.7) / 2011-09-05
-----------------------------------------------------------------------

* **Added** this History document
* **Fixed** date filter to zero-pad correctly during september when using 'm' format

[Documentation](https://github.com/paularmstrong/swig/tree/v0.1.7/docs)

[0.1.6](https://github.com/paularmstrong/swig/tree/v0.1.6) / 2011-09-04
-----------------------------------------------------------------------

* **Fixed** Template inheritance blocks messing up.

[Documentation](https://github.com/paularmstrong/swig/tree/v0.1.6/docs)

[0.1.5](https://github.com/paularmstrong/swig/tree/v0.1.5) / 2011-09-04
-----------------------------------------------------------------------

* **Added** `first`, `last`, and `uniq` filters
* **Added** ability to specify custom filters
* **Added** ability to specify custom tags
* **Changed** slots removed -- implement using custom tags if desired
* **Fixed** ability to do either dot- or bracket-notation or mixed in variables
* **Fixed** internal parsing helpers

[Documentation](https://github.com/paularmstrong/swig/tree/v0.1.5/docs)

[0.1.3](https://github.com/paularmstrong/swig/tree/v0.1.3) / 2011-09-01
-----------------------------------------------------------------------

* **Fixed** filter parser to work correctly with single-quoted params in filters.

[Documentation](https://github.com/paularmstrong/swig/tree/v0.1.3/docs)

[0.1.2](https://github.com/paularmstrong/swig/tree/v0.1.2) / 2011-09-01
-----------------------------------------------------------------------

* Initial **swig** publish after forking from [node-t](https://github.com/skid/node-t)

[Documentation](https://github.com/paularmstrong/swig/tree/v0.1.2/docs)
