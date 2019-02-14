# hexo-front-matter

[![Build Status](https://travis-ci.org/hexojs/hexo-front-matter.svg?branch=master)](https://travis-ci.org/hexojs/hexo-front-matter)  [![NPM version](https://badge.fury.io/js/hexo-front-matter.svg)](http://badge.fury.io/js/hexo-front-matter) [![Coverage Status](https://coveralls.io/repos/hexojs/hexo-front-matter/badge.svg?branch=master)](https://coveralls.io/r/hexojs/hexo-front-matter?branch=master)

Front-matter parser.

## What is Front-matter?

Front-matter allows you to specify data at the top of a file. Here're two formats:

**YAML front-matter**

```
---
layout: false
title: "Hello world"
---
Lorem ipsum dolor sit amet, consectetur adipiscing elit.
```

**JSON front-matter**

```
;;;
"layout": false,
"title": "Hello world"
;;;
Lorem ipsum dolor sit amet, consectetur adipiscing elit.
```

Prefixing separators are optional.

## API

### parse(str, [options])

Parses front-matter.

### stringify(obj, [options])

Converts an object to a front-matter string.

Option | Description | Default
--- | --- | ---
`mode` | The mode can be either `json` or `yaml`. | `yaml`
`separator` | Separator | `---`
`prefixSeparator` | Add prefixing separator. | `false`

### split(str)

Splits a YAML front-matter string.

### escape(str)

Converts hard tabs to soft tabs.

## License

MIT