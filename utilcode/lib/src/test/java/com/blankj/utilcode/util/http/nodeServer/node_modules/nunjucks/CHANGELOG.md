Changelog
=========

3.1.7 (Jan 12 2019)
------------------

* Fix bug where exceptions were silently swallowed with synchronous render.
  Fixes [#678](https://github.com/mozilla/nunjucks/issues/678),
  [#1116](https://github.com/mozilla/nunjucks/issues/1116),
  [#1127](https://github.com/mozilla/nunjucks/issues/1127), and
  [#1164](https://github.com/mozilla/nunjucks/issues/1164)
* Removes deprecated postinstall-build package in favor of
  [npm prepare](https://docs.npmjs.com/misc/scripts#prepublish-and-prepare).
  Merge of [#1172](https://github.com/mozilla/nunjucks/pull/1172).
  Fixes [#1167](https://github.com/mozilla/nunjucks/issues/1167).

    - Note: this means that npm@5 or later is required to install nunjucks
      directly from github.

3.1.6 (Dec 13 2018)
-------------------

No code changes; fixed npm packaging issue.

3.1.5 (Dec 13 2018)
-------------------

* Fix engine dependency version for Node versions > 11.1.0;
  Fixes [#1168](https://github.com/mozilla/nunjucks/issues/1168).

3.1.4 (Nov 9 2018)
------------------

* Fix engine version for Node v11.1.0
* Fix "Unexpected token" error for U+2028 unicode newline. Fixes [#126](https://github.com/mozilla/nunjucks/issues/126) and [#736](https://github.com/mozilla/nunjucks/issues/736)

3.1.3 (May 19 2018)
-------------------

* Add `forceescape` filter. Fixes [#782](https://github.com/mozilla/nunjucks/issues/782)

* Fix regression that prevented template errors from reporting line and column number.
  Fixes [#1087](https://github.com/mozilla/nunjucks/issues/1087) and
  [#1095](https://github.com/mozilla/nunjucks/issues/1095).

* Fix "Invalid type: Is" error for `{% if value is defined %}`. Fixes
  [#1110](https://github.com/mozilla/nunjucks/issues/1110)

* Formally drop support for node v4 (the upgrade to babel 7 in 3.1.0 made the
  build process incompatible with node < 6.9.0).

3.1.2 (Feb 23 2018)
-------------------

* Fix regression to make `chokidar` an optional dependency again. Fixes
  [#1073](https://github.com/mozilla/nunjucks/issues/1073)
* Fix issue when running `npm install nunjucks` with the `--no-bin-links` flag
* Fix regression that broke template caching. Fixes
  [#1074](https://github.com/mozilla/nunjucks/issues/1074)

3.1.0 (Feb 19 2018)
-------------------

* Support nunjucks.installJinjaCompat() with slim build. Fixes
  [#1019](https://github.com/mozilla/nunjucks/issues/1019)

* Fix calling render callback twice when a conditional import throws an error.
  Solves [#1029](https://github.com/mozilla/nunjucks/issues/1029)

* Support objects created with Object.create(null). fixes [#468](https://github.com/mozilla/nunjucks/issues/468)

* Support ESNext iterators, using Array.from. Merge of
  [#1058](https://github.com/mozilla/nunjucks/pull/1058)

3.0.1 (May 24 2017)
-------------------

* Fix handling methods and attributes of static arrays, objects and primitives.
  Solves the issue [#937](https://github.com/mozilla/nunjucks/issues/937)

* Add support for python-style array slices with Jinja compat enabled.
  Fixes [#188](https://github.com/mozilla/nunjucks/issues/188); merge of
  [#976](https://github.com/mozilla/nunjucks/pull/976).

* Fix call blocks having access to their parent scope. Fixes
  [#906](https://github.com/mozilla/nunjucks/issues/906); merge of
  [#994](https://github.com/mozilla/nunjucks/pull/994).

* Fix a bug that caused capturing block tags (e.g. set/endset,
  filter/endfilter) to write to the global buffer rather than capturing
  their contents. Fixes
  [#914](https://github.com/mozilla/nunjucks/issues/914) and
  [#972](https://github.com/mozilla/nunjucks/issues/972); merge of
  [#990](https://github.com/mozilla/nunjucks/pull/990). Thanks [Noah
  Lange](@noahlange).


3.0.0 (Nov 5 2016)
----------------

* Allow including many templates without reaching recursion limits. Merge of
  [#787](https://github.com/mozilla/nunjucks/pull/787). Thanks Gleb Khudyakov.

* Allow explicitly setting `null` (aka `none`) as the value of a variable;
  don't ignore that value and look on up the frame stack or context. Fixes
  [#478](https://github.com/mozilla/nunjucks/issues/478). Thanks Jonny Gerig
  Meyer for the report.

* Execute blocks in a child frame that can't write to its parent. This means
  that vars set inside blocks will not leak outside of the block, base
  templates can no longer see vars set in templates that inherit them, and
  `super()` can no longer set vars in its calling scope. Fixes the inheritance
  portion of [#561](https://github.com/mozilla/nunjucks/issues/561), which
  fully closes that issue. Thanks legutierr for the report.

* Prevent macros from seeing or affecting their calling scope. Merge of
  [#667](https://github.com/mozilla/nunjucks/pull/667).

* Fix handling of macro arg with default value which shares a name with another
  macro. Merge of [#791](https://github.com/mozilla/nunjucks/pull/791).

* Add support for the spaces parameter in the dump template filter.
  Merge of [#868](https://github.com/mozilla/nunjucks/pull/868).
  Thanks Jesse Eikema

* Add `verbatim` as an alias of `raw` for compatibility with Twig.
  Merge of [#874](https://github.com/mozilla/nunjucks/pull/874).

* Add new `nl2br` filter. Thanks Marc-Aurèle Darche

* Add support for python's `list.append` with Jinja compat enabled. Thanks
  Conor Flannigan.

* Add variables whitespace control.


2.5.2 (Sep 14 2016)
----------------

* Call `.toString` in safe filter.
  Merge of [#849](https://github.com/mozilla/nunjucks/pull/849).


2.5.1 (Sep 13 2016)
----------------

* Fix `undefined` and `null` behavior in escape and safe filter.
  Merge of [#843](https://github.com/mozilla/nunjucks/pull/843).


2.5.0 (Sep 7 2016)
----------------

* Add `elseif` as an alias of `elif` for parity with Twig. Thanks kswedberg.
  Merge of [#826](https://github.com/mozilla/nunjucks/pull/826).

* Add nunjucks env to express app settings as `nunjucksEnv`.
  Merge of [#829](https://github.com/mozilla/nunjucks/pull/829).

* Add support for finding an object's "length" in length filter.
  Merge of [#813](https://github.com/mozilla/nunjucks/pull/813).

* Ensure that precompiling on Windows still outputs POSIX-style path
  separators. Merge of [#761](https://github.com/mozilla/nunjucks/pull/761).

* Add support for strict type check comparisons (=== and !==). Thanks
  oughter. Merge of [#746](https://github.com/mozilla/nunjucks/pull/746).

* Allow full expressions (incl. filters) in import and from tags. Thanks legutierr.
  Merge of [#710](https://github.com/mozilla/nunjucks/pull/710).

* OS agnostic file paths in precompile. Merge of [#825](https://github.com/mozilla/nunjucks/pull/825).


2.4.3 (Sep 7 2016)
----------------

* Fix potential cast-related XSS vulnerability in autoescape mode, and with `escape` filter.
  Thanks Matt Austin for the report and Thomas Hunkapiller for the fix.
  [#836](https://github.com/mozilla/nunjucks/pull/836)


2.4.2 (Apr 15 2016)
-------------------

* Fix use of `in` operator with strings. Fixes
  [#714](https://github.com/mozilla/nunjucks/issues/714). Thanks Zubrik for the
  report.

* Support ES2015 Map and Set in `length` filter. Merge of
  [#705](https://github.com/mozilla/nunjucks/pull/705). Thanks ricordisamoa.

* Remove truncation of long function names in error messages. Thanks Daniel
  Bendavid. Merge of [#702](https://github.com/mozilla/nunjucks/pull/702).


2.4.1 (Mar 17 2016)
-------------------

* Don't double-escape. Thanks legutierr. Merge of
  [#701](https://github.com/mozilla/nunjucks/pull/701).

* Prevent filter.escape from escaping SafeString. Thanks atian25. Merge of
  [#623](https://github.com/mozilla/nunjucks/pull/623).

* Throw an error if a block is defined multiple times. Refs
  [#696](https://github.com/mozilla/nunjucks/issues/696).

* Officially recommend the `.njk` extension. Thanks David Kebler. Merge of
  [#691](https://github.com/mozilla/nunjucks/pull/691).

* Allow block-set to wrap an inheritance block. Unreported; fixed as a side
  effect of the fix for [#576](https://github.com/mozilla/nunjucks/issues/576).

* Fix `filter` tag with non-trivial contents. Thanks Stefan Cruz and Fabien
  Franzen for report and investigation, Jan Oopkaup for failing tests. Fixes
  [#576](https://github.com/mozilla/nunjucks/issues/576).


2.4.0 (Mar 10 2016)
-------------------

* Allow retrieving boolean-false as a global. Thanks Marius Büscher. Merge of
  [#694](https://github.com/mozilla/nunjucks/pull/694).

* Don't automatically convert any for-loop that has an include statement into
  an async loop. Reverts
  [7d4716f4fd](https://github.com/mozilla/nunjucks/commit/7d4716f4fd), re-opens
  [#372](https://github.com/mozilla/nunjucks/issues/372), fixes
  [#527](https://github.com/mozilla/nunjucks/issues/527). Thanks Tom Delmas for
  the report.

* Switch from Optimist to Yargs for argument-parsing. Thanks Bogdan
  Chadkin. Merge of [#672](https://github.com/mozilla/nunjucks/pull/672).

* Prevent includes from writing to their including scope. Merge of
  [#667](https://github.com/mozilla/nunjucks/pull/667) (only partially
  backported to 2.x; macro var visibility not backported).

* Fix handling of `dev` environment option, to get full tracebacks on errors
  (including nunjucks internals). Thanks Tobias Petry and Chandrasekhar Ambula
  V for the report, Aleksandr Motsjonov for draft patch.

* Support using `in` operator to search in both arrays and objects,
  and it will throw an error for other data types.
  Fix [#659](https://github.com/mozilla/nunjucks/pull/659).
  Thanks Alex Mayfield for report and test, Ouyang Yadong for fix.
  Merge of [#661](https://github.com/mozilla/nunjucks/pull/661).

* Add support for `{% set %}` block assignments as in jinja2. Thanks Daniele
  Rapagnani. Merge of [#656](https://github.com/mozilla/nunjucks/pull/656)

* Fix `{% set %}` scoping within macros.
  Fixes [#577](https://github.com/mozilla/nunjucks/issues/577) and
  the macro portion of [#561](https://github.com/mozilla/nunjucks/issues/561).
  Thanks Ouyang Yadong. Merge of [#653](https://github.com/mozilla/nunjucks/pull/653).

* Add support for named `endblock` (e.g. `{% endblock foo %}`). Thanks
  ricordisamoa. Merge of [#641](https://github.com/mozilla/nunjucks/pull/641).

* Fix `range` global with zero as stop-value. Thanks Thomas Hunkapiller. Merge
  of [#638](https://github.com/mozilla/nunjucks/pull/638).

* Fix a bug in urlize that collapsed whitespace. Thanks Paulo Bu. Merge of
  [#637](https://github.com/mozilla/nunjucks/pull/637).

* Add `sum` filter. Thanks Pablo Matías Lazo. Merge of
  [#629](https://github.com/mozilla/nunjucks/pull/629).

* Don't suppress errors inside {% if %} tags. Thanks Artemy Tregubenko for
  report and test, Ouyang Yadong for fix. Merge of
  [#634](https://github.com/mozilla/nunjucks/pull/634).

* Allow whitespace control on comment blocks, too. Thanks Ouyang Yadong. Merge
  of [#632](https://github.com/mozilla/nunjucks/pull/632).

* Fix whitespace control around nested tags/variables/comments. Thanks Ouyang
  Yadong. Merge of [#631](https://github.com/mozilla/nunjucks/pull/631).


v2.3.0 (Jan 6 2016)
-------------------

* Return `null` from `WebLoader` on missing template instead of throwing an
  error, for consistency with other loaders. This allows `WebLoader` to support
  the new `ignore missing` flag on the `include` tag. If `ignore missing` is
  not set, a generic "template not found" error will still be thrown, just like
  for any other loader. Ajax errors other than 404 will still cause `WebLoader`
  to throw an error directly.

* Add preserve-linebreaks option to `striptags` filter. Thanks Ivan
  Kleshnin. Merge of [#619](https://github.com/mozilla/nunjucks/pull/619).


v2.2.0 (Nov 23 2015)
--------------------

* Add `striptags` filter. Thanks Anthony Giniers. Merge of
  [#589](https://github.com/mozilla/nunjucks/pull/589).
* Allow compiled templates to be imported, included and extended. Thanks Luis
  Gutierrez-Sheris. Merge of
  [#581](https://github.com/mozilla/nunjucks/pull/581).
* Fix issue with different nunjucks environments sharing same globals. Each
  environment is now independent.  Thanks Paul Pechin. Merge of
  [#574](https://github.com/mozilla/nunjucks/pull/574).
* Add negative steps support for range function. Thanks Nikita Mostovoy. Merge
  of [#575](https://github.com/mozilla/nunjucks/pull/575).
* Remove deprecation warning when using the `default` filter without specifying
  a third argument. Merge of
  [#567](https://github.com/mozilla/nunjucks/pull/567).
* Add support for chaining of addGlobal, addFilter, etc. Thanks Rob Graeber. Merge of
  [#537](https://github.com/mozilla/nunjucks/pull/537)
* Fix error propagation. Thanks Tom Delmas. Merge of
  [#534](https://github.com/mozilla/nunjucks/pull/534).
* trimBlocks now also trims windows style line endings. Thanks Magnus Tovslid. Merge of
  [#548](https://github.com/mozilla/nunjucks/pull/548)
* `include` now supports an option to suppress errors if the template does not
  exist. Thanks Mathias Nestler. Merge of
  [#559](https://github.com/mozilla/nunjucks/pull/559)


v2.1.0 (Sep 21 2015)
--------------------

* Fix creating `WebLoader` without `opts`. Merge of
  [#524](https://github.com/mozilla/nunjucks/pull/524).
* Add `hasExtension` and `removeExtension` methods to `Environment`. Merge of
  [#512](https://github.com/mozilla/nunjucks/pull/512).
* Add support for kwargs in `sort` filter. Merge of
  [#510](https://github.com/mozilla/nunjucks/pull/510).
* Add `none` as a lexed constant evaluating to `null`. Merge of
  [#480](https://github.com/mozilla/nunjucks/pull/480).
* Fix rendering of multiple `raw` blocks. Thanks Aaron O'Mullan. Merge of
  [#503](https://github.com/mozilla/nunjucks/pull/503).
* Avoid crashing on async loader error. Thanks Samy Pessé. Merge of
  [#504](https://github.com/mozilla/nunjucks/pull/504).
* Add support for keyword arguments for sort filter. Thanks Andres Pardini. Merge of
  [#510](https://github.com/mozilla/nunjucks/pull/510)


v2.0.0 (Aug 30 2015)
--------------------

Most of the changes can be summed up in the
[issues tagged 2.0](https://github.com/mozilla/nunjucks/issues?q=is%3Aissue+milestone%3A2.0+is%3Aclosed).

Or you can
[see all commits](https://github.com/mozilla/nunjucks/compare/v1.3.4...f8aabccefc31a9ffaccdc6797938b5187e07ea87).

Most important changes:

* **autoescape is now on by default.** You need to explicitly pass `{
  autoescape: false }` in the options to turn it off.
* **watch is off by default.** You need to explicitly pass `{ watch: true }` to
  start the watcher.
* The `default` filter has changed. It will show the default value only if the
  argument is **undefined**. Any other value, even false-y values like `false`
  and `null`, will be returned. You can get back the old behavior by passing
  `true` as a 3rd argument to activate the loose-y behavior: `foo |
  default("bar", true)`. In 2.0 if you don't pass the 3rd argument, a warning
  will be displayed about this change in behavior. In 2.1 this warning will be
  removed.
* [New filter tag](http://mozilla.github.io/nunjucks/templating.html#filter)
* Lots of other bug fixes and small features, view the above issue list!


v1.3.4 (Apr 27 2015)
--------------------

This is an extremely minor release that only adds an .npmignore so that the
bench, tests, and docs folders do not get published to npm. Nunjucks should
download a lot faster now.


v1.3.3 (Apr 3 2015)
-------------------

This is exactly the same as v1.3.1, just fixing a typo in the git version tag.


v1.3.2 (Apr 3 2015)
-------------------

(no notes)


v1.3.1 (Apr 3 2015)
-------------------

We added strict mode to all the files, but that broke running nunjucks in the
browser. Should work now with this small fix.


v1.3.0 (Apr 3 2015)
-------------------

* Relative templates: you can now load a template relatively by starting the
  path with ., like ./foo.html
* FileSystemLoader now takes a noCache option, if true will disable caching
  entirely
* Additional lstripBlocks and trimBlocks available to clean output
  automatically
* New selectattr and rejectattr filters
* Small fixes to the watcher
* Several bug fixes


v1.2.0 (Feb 4 2015)
-------------------

* The special non-line-breaking space is considered whitespace now
* The in operator has a lower precedence now. This is potentially a breaking
  change, thus the minor version bump. See
  [#336](https://github.com/mozilla/nunjucks/pull/336)
* import with context now implemented:
  [#319](https://github.com/mozilla/nunjucks/pull/319)
* async rendering doesn't throw compile errors


v1.1.0 (Sep 30 2014)
--------------------

User visible changes:

* Fix a bug in urlize that would remove periods
* custom tag syntax (like {% and %}) was made Environment-specific
  internally. Previously they were global even though you set them through the
  Environment.
* Remove aggressive optimization that only emitted loop variables when uses. It
  introduced several bugs and didn't really improve perf.
* Support the regular expression syntax like /foo/g.
* The replace filter can take a regex as the first argument
* The call tag was implemented
* for tags can now take an else clause
* The cycler object now exposes the current item as the current property
* The chokidar library was updated and should fix various issues

Dev changes:

* Test coverage now available via istanbul. Will automatically display after
  running tests.


v1.0.7 (Aug 15 2014)
--------------------

Mixed up a few things in the 1.0.6 release, so another small bump. This merges
in one thing:

* The length filter will not throw an error is used on an undefined
  variable. It will return 0 if the variable is undefined.


v1.0.6 (Aug 15 2014)
--------------------

* Added the addGlobal method to the Environment object
* import/extends/include now can take an arbitrary expression
* fix bugs in set
* improve express integration (allows rendering templates without an extension)


v1.0.5 (May 1 2014)
-------------------

* Added support for browserify
* Added option to specify template output path when precompiling templates
* Keep version comment in browser minified files
* Speed up SafeString implementation
* Handle null and non-matching cases for word count filter
* Added support for node-webkit
* Other various minor bugfixes


chokidar repo fix - v1.0.4 (Apr 4 2014)
---------------------------------------

* The chokidar dependency moved repos, and though the git URL should have been
  forwarded some people were having issues. This fixed the repo and
  version.

(v1.0.3 is skipped because it was published with a bad URL, quickly fixed with
another version bump)


Bug fixes - v1.0.2 (Mar 25 2014)
--------------------------------

* Use chokidar for watching file changes. This should fix a lot of problems on
  OS X machines.
* Always use / in paths when precompiling templates
* Fix bug where async filters hang indefinitely inside if statements
* Extensions now can override autoescaping with an autoescape property
* Other various minor bugfixes


v1.0.1 (Dec 16, 2013)
---------------------

(no notes)


We've reached 1.0! Better APIs, asynchronous control, and more (Oct 24, 2013)
-----------------------------------------------------------------------------

* An asynchronous API is now available, and async filters, extensions, and
  loaders is supported. The async API is optional and if you don't do anything
  async (the default), nothing changes for you. You can read more about this
  [here](http://jlongster.github.io/nunjucks/api.html#asynchronous-support). (fixes
  [#41](https://github.com/mozilla/nunjucks/issues/41))
* Much simpler higher-level API for initiating/configuring nunjucks is
  available. Read more
  [here](http://jlongster.github.io/nunjucks/api.html#simple-api).
* An official grunt plugin is available for precompiling templates:
  [grunt-nunjucks](https://github.com/jlongster/grunt-nunjucks)
* **The browser files have been renamed.** nunjucks.js is now the full library
  with compiler, and nunjucks-slim.js is the small version that only works with
  precompiled templates
* urlencode filter has been added
* The express integration has been refactored and isn't a kludge
  anymore. Should avoid some bugs and be more future-proof;
* The order in which variables are lookup up in the context and frame lookup
  has been reversed. It will now look in the frame first, and then the
  context. This means that if a for loop introduces a new var, like {% for name
  in names %}, and if you have name in the context as well, it will properly
  reference name from the for loop inside the loop. (fixes
  [#122](https://github.com/mozilla/nunjucks/pull/122) and
  [#119](https://github.com/mozilla/nunjucks/issues/119))


v0.1.10 (Aug 9 2013)
--------------------

(no notes)


v0.1.9 (May 30 2013)
--------------------

(no notes)


v0.1.8 - whitespace controls, unpacking, better errors, and more! (Feb 6 2013)
------------------------------------------------------------------------------

There are lots of cool new features in this release, as well as many critical
bug fixes.

Full list of changes:

* Whitespace control is implemented. Use {%- and -%} to strip whitespace before/after the block.
* `for` loops implement Python-style array unpacking. This is a really nice
  feature which lets you do this:

    {% for x, y, z in [[2, 2, 2], [3, 3, 3]] %}
      --{{ x }} {{ y }} {{ z }}--
    {% endfor %}

  The above would output: --2 2 2----3 3 3--

  You can pass any number of variable names to for and it will destructure each
  array in the list to the variables.

  This makes the syntax between arrays and objects more
  consistent. Additionally, it allows us to implement the `dictsort` filter
  which sorts an object by keys or values. Technically, it returns an array of
  2-value arrays and the unpacking takes care of it. Example:

    {% for k, v in { b: 2, a: 1 } %}
      --{{ k }}: {{ v }}--
    {% endfor %}

  Output: `--b: 2----a: 1--` (note: the order could actually be anything
  because it uses javascript’s `for k in obj` syntax to iterate, and ordering
  depends on the js implementation)

    {% for k, v in { b: 2, a: 1} | dictsort %}
      --{{ k }}: {{ v }}--
    {% endfor %}

  Output: `--a: 1----b: 2--`

  The above output will always be ordered that way. See the documentation for
  more details.

  Thanks to novocaine for this!

* Much better error handling with at runtime (shows template/line/col information for attempting to call undefined values, etc)
* Fixed a regression which broke the {% raw %} block
* Fix some edge cases with variable lookups
* Fix a regression with loading precompiled templates
* Tweaks to allow usage with YUICompressor
* Use the same error handling as normal when precompiling (shows proper errors)
* Fix template loading on Windows machines
* Fix int/float filters
* Fix regression with super()


v0.1.7 - helpful errors, many bug fixes (Dec 12 2012)
-----------------------------------------------------

The biggest change in v0.1.7 comes from devoidfury (thanks!) which implements
consistent and helpful error messages. The errors are still simply raw text,
and not pretty HTML, but they at least contain all the necessary information to
track down an error, such as template names, line and column numbers, and the
inheritance stack. So if an error happens in a child template, it will print
out all the templates that it inherits. In the future, we will most likely
display the actual line causing an error.

Full list of changes:

* Consistent and helpful error messages
* Expressions are more consistent now. Previously, there were several places
  that wouldn’t accept an arbitrary expression that should. For example, you
  can now do {% include templateNames['foo'] %}, whereas previously you could
  only give it a simply variable name.
* app.locals is fixed with express 2.5
* Method calls on objects now have correct scope for this. Version 0.1.6 broke
  this and this was referencing the global scope.
* A check was added to enforce loading of templates within the correct
  path. Previously you could load a file outside of the template with something
  like ../../crazyPrivateFile.txt

You can
[view all the code changes here](https://github.com/jlongster/nunjucks/compare/v0.1.6...v0.1.7). Please
[file an issue](https://github.com/jlongster/nunjucks/issues?page=1&state=open)
if something breaks!


v0.1.6 - undefined handling, bugfixes (Nov 13, 2012)
----------------------------------------------------

This is mostly a bugfix release, but there are a few small tweaks based on
feedback:

* In some cases, backslashes in the template would not appear in the
  output. This has been fixed.
* An error is thrown if a filter is not found
* Old versions of express are now supported (2.5.11 was tested)
* References on undefined objects are now suppressed. For example, {{ foo }},
  {{ foo.bar }}, {{ foo.bar.baz }} all output nothing if foo is
  undefined. Previously only the first form would be suppressed, and a cryptic
  error thrown for the latter 2 references. Note: I believe this is a departure
  from jinja, which throws errors when referencing undefined objects. I feel
  that this is a good and non-breaking addition though. (thanks to devoidfury)
* A bug in set where you couldn’t not reference other variables is fixed
  (thanks chriso and panta)
* Other various small bugfixes

You can view
[all the code changes here](https://github.com/jlongster/nunjucks/compare/v0.1.5...v0.1.6). As
always, [file an issue](https://github.com/jlongster/nunjucks/issues) if
something breaks!



v0.1.5 - macros, keyword arguments, bugfixes (Oct 11 2012)
----------------------------------------------------------

v0.1.5 has been pushed to npm, and it’s a big one. Please file any issues you
find, and I’ll fix them as soon as possible!

* The node data structure has been completely refactored to reduce redundancy
  and make it easier to add more types in the future.
* Thanks to Brent Hagany, macros now have been implemented. They should act
  exactly the way jinja2 macros do.
* A calling convention which implements keyword arguments now exists. All
    keyword args are converted into a hash and passed as the last
    argument. Macros needed this to implement keyword/default arguments.
* Function and filter calls apply the new keyword argument calling convention
* The “set” block now appropriately only sets a variable for the current scope.
* Many other bugfixes.

I’m watching this release carefully because of the large amount of code that
has changed, so please
[file an issue](https://github.com/jlongster/nunjucks/issues) if you have a
problem with it.
