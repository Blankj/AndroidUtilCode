'use strict';

var vsprintf = require('sprintf-js').vsprintf;

function i18n(options){
  /* jshint validthis: true */
  options = options || {};

  this.data = {};
  this.languages = options.languages || ['default'];

  if (!Array.isArray(this.languages)){
    this.languages = [this.languages];
  }
}

i18n.prototype.get = function(languages){
  var data = this.data;
  var result = {};
  var lang, langData, keys, key, i, leni, j, lenj;

  if (languages){
    if (!Array.isArray(languages)){
      languages = [languages];
    }
  } else {
    languages = this.languages;
  }

  for (i = 0, leni = languages.length; i < leni; i++){
    lang = languages[i];
    langData = data[lang];
    if (!langData) continue;

    keys = Object.keys(langData);

    for (j = 0, lenj = keys.length; j < lenj; j++){
      key = keys[j];
      if (!result.hasOwnProperty(key)) result[key] = langData[key];
    }
  }

  return result;
};

i18n.prototype.set = function(lang, data){
  if (typeof lang !== 'string') throw new TypeError('lang must be a string!');
  if (typeof data !== 'object') throw new TypeError('data is required!');

  this.data[lang] = flattenObject(data);

  return this;
};

i18n.prototype.remove = function(lang){
  if (typeof lang !== 'string') throw new TypeError('lang must be a string!');

  delete this.data[lang];

  return this;
};

i18n.prototype.list = function(){
  return Object.keys(this.data);
};

function flattenObject(data, obj, parent){
  obj = obj || {};
  parent = parent || '';

  var keys = Object.keys(data);
  var key = '';
  var item;

  for (var i = 0, len = keys.length; i < len; i++){
    key = keys[i];
    item = data[key];

    if (typeof item === 'object'){
      flattenObject(item, obj, parent + key + '.');
    } else {
      obj[parent + key] = item;
    }
  }

  return obj;
}

i18n.prototype.__ = function(lang){
  var data = this.get(lang);

  return function(){
    var len = arguments.length;
    if (!len) return '';

    var args = new Array(len);

    for (var i = 0; i < len; i++){
      args[i] = arguments[i];
    }

    var key = args.shift();
    var str = data[key] || key;

    return vsprintf(str, args);
  };
};

i18n.prototype._p = function(lang){
  var data = this.get(lang);

  return function(){
    var len = arguments.length;
    if (!len) return '';

    var args = new Array(len);

    for (var i = 0; i < len; i++){
      args[i] = arguments[i];
    }

    var key = args.shift();
    var number = args.length ? +args[0] : 0;
    var str = key;

    if (!number && data.hasOwnProperty(key + '.zero')){
      str = data[key + '.zero'];
    } else if (number === 1 && data.hasOwnProperty(key + '.one')){
      str = data[key + '.one'];
    } else if (data.hasOwnProperty(key + '.other')){
      str = data[key + '.other'];
    } else if (data.hasOwnProperty(key)){
      str = data[key];
    }

    return vsprintf(str, args);
  };
};

module.exports = i18n;