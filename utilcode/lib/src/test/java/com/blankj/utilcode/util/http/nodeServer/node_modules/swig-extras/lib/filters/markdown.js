var markdown = require('markdown').markdown;

/**
 * Convert a variable's contents from Markdown to HTML.
 *
 * @example
 * {{ foo|markdown }}
 * // => <h1>Markdown</h1>
 *
 * @param  {string} input
 * @return {string}       HTML
 */
module.exports = function (input) {
  return markdown.toHTML(input);
};

module.exports.safe = true;
