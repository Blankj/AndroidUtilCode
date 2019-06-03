'use strict';

var hljs = require('highlight.js');
var languages = hljs.listLanguages();

var result = {
  languages: languages,
  aliases: {}
};

languages.forEach(function(lang) {
  result.aliases[lang] = lang;

  var def = require('highlight.js/lib/languages/' + lang)(hljs);
  var aliases = def.aliases;

  if (aliases) {
    aliases.forEach(function(alias) {
      result.aliases[alias] = lang;
    });
  }
});

console.log(JSON.stringify(result));
