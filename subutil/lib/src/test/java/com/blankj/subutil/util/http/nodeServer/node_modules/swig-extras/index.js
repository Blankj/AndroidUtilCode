/**
 * Raw tag objects.
 * @type {object}
 */
exports.tags = require('./lib/tags');

/**
 * Raw filter methods.
 * @type {object}
 */
exports.filters = require('./lib/filters');

/**
 * Add an Extras filter to your swig instance.
 *
 * @example
 * var swig = require('swig'),
 *   extras = require('swig-extras');
 * extras.useFilter(swig, 'markdown');
 *
 * @param  {object} swig   Swig instance.
 * @param  {string} filter Extras filter name.
 * @return {undefined}
 * @throws {Error} If Extras does not have a filter with the given name.
 */
exports.useFilter = function (swig, filter) {
  var f = exports.filters[filter];
  if (!f) {
    throw new Error('Filter "' + filter + '" does not exist.');
  }
  swig.setFilter(filter, f);
};

/**
 * Add an Extras tag to your swig instance.
 *
 * @example
 * var swig = require('swig'),
 *   extras = require('swig-extras'),
 *   mySwig = new swig.Swig();
 * extras.useTag(mySwig, 'markdown');
 *
 * @param  {object} swig   Swig instance.
 * @param  {string} tag    Extras tag name.
 * @return {undefined}
 * @throws {Error} If Extras does not have a tag with the given name.
 */
exports.useTag = function (swig, tag) {
  var t = exports.tags[tag];
  if (!t) {
    throw new Error('Tag "' + tag + '" does not exist.');
  }
  swig.setTag(tag, t.parse, t.compile, t.ends, t.blockLevel);
  if (t.ext) {
    swig.setExtension(t.ext.name, t.ext.obj);
  }
};
