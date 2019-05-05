# hexo-i18n

[![Build Status](https://travis-ci.org/hexojs/hexo-i18n.svg?branch=master)](https://travis-ci.org/hexojs/hexo-i18n)  [![NPM version](https://badge.fury.io/js/hexo-i18n.svg)](http://badge.fury.io/js/hexo-i18n) [![Coverage Status](https://img.shields.io/coveralls/hexojs/hexo-i18n.svg)](https://coveralls.io/r/hexojs/hexo-i18n?branch=master)

i18n module for [Hexo].

## Installation

``` bash
$ npm install hexo-i18n --save
```

## Usage

### Example

``` js
var i18n = new require('hexo-i18n')({
  languages: ['zh-TW', 'en']
});

i18n.set('en', {
  ok: 'OK',
  name: 'My name is %1$s %2$s.',
  index: {
    title: 'Home'
  },
  video: {
    zero: 'No videos',
    one: 'A video',
    other: '%d videos'
  }
});

i18n.set('zh-TW', {
  name: '我的名字是 %2$s %1$s。',
  index: {
    title: '首頁'
  },
  video: {
    zero: '沒有影片',
    one: '一部影片',
    other: '%d 部影片'
  }
});

var __ = i18n.__();
var _p = i18n._p();

__('ok') // OK
__('index.title') // 首頁
__('name', '大呆', '王') // 我的名字是王大呆
_p('video', 0) // 沒有影片
_p('video', 1) // 一部影片
_p('video', 10) // 10 部影片
```

### new i18n([options])

Creates a new i18n instance.

Option | Description | Default
--- | --- | ---
`languages` | Default languages. It can be an array or a string | `default`

### i18n.get([lang]) → Object

Returns a set of localization data. `lang` can be an array or a string, or the default language defined in constructor if not set. This method will build the data in order of languages.

### i18n.set(lang, data)

Loads localization data.

### i18n.remove(lang)

Unloads localization data.

### i18n.list()

Lists loaded languages.

### i18n.__() → Function(key, arg...)

Returns a function for localization.

### i18n._p() → Function(key, count, ...)

This method is similar to `i18n.__`, but it returns pluralized string based on the second parameter. For example:

``` js
_p('video', 0) = __('video.zero', 0)
_p('video', 1) = __('video.one', 1)
_p('video', 10) = __('video.other', 10)
```

## License

MIT

[Hexo]: http://hexo.io/