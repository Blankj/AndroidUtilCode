'use strict';

var hljs = require('highlight.js/lib/highlight');
var Entities = require('html-entities').XmlEntities;
var entities = new Entities();
var alias = require('../highlight_alias.json');

function highlightUtil(str, options) {
  if (typeof str !== 'string') throw new TypeError('str must be a string!');
  options = options || {};

  var useHljs = options.hasOwnProperty('hljs') ? options.hljs : false;
  var gutter = options.hasOwnProperty('gutter') ? options.gutter : true;
  var wrap = options.hasOwnProperty('wrap') ? options.wrap : true;
  var firstLine = options.hasOwnProperty('firstLine') ? +options.firstLine : 1;
  var caption = options.caption;
  var mark = options.hasOwnProperty('mark') ? options.mark : [];
  var tab = options.tab;

  hljs.configure({ classPrefix: useHljs ? 'hljs-' : ''});

  var data = highlight(str, options);

  if (useHljs && !gutter) wrap = false;

  var before = useHljs ? '<pre><code class="hljs ' + options.lang + '">' : '<pre>';
  var after = useHljs ? '</code></pre>' : '</pre>';

  if (!wrap) return useHljs ? before + data.value + after : data.value;

  var lines = data.value.split('\n');
  var numbers = '';
  var content = '';
  var result = '';
  var line;

  for (var i = 0, len = lines.length; i < len; i++) {
    line = lines[i];
    if (tab) line = replaceTabs(line, tab);
    numbers += '<span class="line">' + (firstLine + i) + '</span><br>';
    content += formatLine(line, firstLine + i, mark, options);
  }

  result += '<figure class="highlight' + (data.language ? ' ' + data.language : '') + '">';

  if (caption) {
    result += '<figcaption>' + caption + '</figcaption>';
  }

  result += '<table><tr>';

  if (gutter) {
    result += '<td class="gutter"><pre>' + numbers + '</pre></td>';
  }

  result += '<td class="code">' + before + content + after + '</td>';
  result += '</tr></table></figure>';

  return result;
}

function formatLine(line, lineno, marked, options) {
  var useHljs = options.hljs || false;
  var res = useHljs ? '' : '<span class="line';
  if (marked.indexOf(lineno) !== -1) {
    // Handle marked lines.
    res += useHljs ? '<mark>' + line + '</mark>' : ' marked">' + line + '</span>';
  } else {
    res += useHljs ? line : '">' + line + '</span>';
  }

  res += '<br>';
  return res;
}

function encodePlainString(str) {
  return entities.encode(str);
}

function replaceTabs(str, tab) {
  return str.replace(/^\t+/, function(match) {
    var result = '';

    for (var i = 0, len = match.length; i < len; i++) {
      result += tab;
    }

    return result;
  });
}

function loadLanguage(lang) {
  hljs.registerLanguage(lang, require('highlight.js/lib/languages/' + lang));
}

function tryLanguage(lang) {
  if (hljs.getLanguage(lang)) return true;
  if (!alias.aliases[lang]) return false;

  loadLanguage(alias.aliases[lang]);
  return true;
}

function loadAllLanguages() {
  alias.languages.filter(function(lang) {
    return !hljs.getLanguage(lang);
  }).forEach(loadLanguage);
}

function highlight(str, options) {
  var lang = options.lang;
  var autoDetect = options.hasOwnProperty('autoDetect') ? options.autoDetect : false;

  if (!lang && autoDetect) {
    loadAllLanguages();
    lang = (function() {
      var result = hljs.highlightAuto(str);
      if (result.relevance > 0 && result.language) return result.language;
      return;
    })();
  }

  if (!lang) {
    lang = 'plain';
  }

  var result = {
    value: encodePlainString(str),
    language: lang.toLowerCase()
  };

  if (result.language === 'plain') {
    return result;
  }

  if (!tryLanguage(result.language)) {
    result.language = 'plain';
    return result;
  }

  if (options.hljs) return hljs.highlight(lang, str);

  return tryHighlight(str, result.language) || result;
}

function tryHighlight(str, lang) {
  try {
    var matching = str.match(/(\r?\n)/);
    var separator = matching ? matching[1] : '';
    var lines = matching ? str.split(separator) : [str];
    var result = hljs.highlight(lang, lines.shift());
    var html = result.value;
    while (lines.length > 0) {
      result = hljs.highlight(lang, lines.shift(), false, result.top);
      html += separator + result.value;
    }

    result.value = html;
    return result;
  } catch (err) {
    return;
  }
}

module.exports = highlightUtil;
