# swig

[![semantic-release](https://img.shields.io/badge/%20%20%F0%9F%93%A6%F0%9F%9A%80-semantic--release-e10079.svg)](https://github.com/semantic-release/semantic-release)
 [![Build Status](http://img.shields.io/travis/node-swig/swig-templates/master.svg?style=flat)](http://travis-ci.org/node-swig/swig-templates) [![NPM version](http://img.shields.io/npm/v/swig-templates.svg?style=flat)](https://www.npmjs.org/package/swig-templates) [![NPM Downloads](http://img.shields.io/npm/dm/swig-templates.svg?style=flat)](https://www.npmjs.org/package/swig-templates) [![JavaScript Style Guide](https://img.shields.io/badge/code_style-standard-brightgreen.svg)](https://standardjs.com)


[Swig](http://node-swig.github.io/swig-templates/) is an awesome, Django/Jinja-like template engine for node.js.

#### seeking maintainers
Paul Armstrong has [stepped down](https://web.archive.org/web/20160311170700/https://github.com/paularmstrong/swig/issues/628) as the primary swig maintainer.  Swig is a phenominal project and a template engine that quitely, but strongly, stands tall against the others in a domain full of template engines.  If you are interested in being a collaborator, check out the issues page, and let's discuss how to proceed.  Don't forget to thank Paul and previous swig collabs for their hard and excellent work!

Features
--------

* Available for node.js **and** major web browsers!
* [Express](http://expressjs.com/) compatible.
* Object-Oriented template inheritance.
* Apply filters and transformations to output in your templates.
* Automatically escapes all output for safe HTML rendering.
* Lots of iteration and conditionals supported.
* Robust without the bloat.
* Extendable and customizable. See [Swig-Extras](https://github.com/paularmstrong/swig-extras) for some examples.
* Great [code coverage](http://node-swig.github.io/swig-templates/coverage.html).

Need Help? Have Questions? Comments?
------------------------------------

* [Mailing List/Google Group](http://groups.google.com/forum/#!forum/swig-templates)
* [StackOverflow](http://stackoverflow.com/questions/tagged/swig-template)
* [Migration Guide](https://github.com/node-swig/swig-templates/wiki/Migrating-from-v0.x.x-to-v1.0.0)

Installation
------------

    npm install swig-templates

Documentation
-------------

All documentation can be viewed online on the [Swig Website](http://node-swig.github.io/swig-templates/).

Basic Example
-------------

### Template code

```html
<h1>{{ pagename|title }}</h1>
<ul>
{% for author in authors %}
    <li{% if loop.first %} class="first"{% endif %}>{{ author }}</li>
{% endfor %}
</ul>
```

### node.js code

```js
var swig  = require('swig-templates');
var template = swig.compileFile('/absolute/path/to/template.html');
var output = template({
    pagename: 'awesome people',
    authors: ['Paul', 'Jim', 'Jane']
});
```

### Output

```html
<h1>Awesome People</h1>
<ul>
    <li class="first">Paul</li>
    <li>Jim</li>
    <li>Jane</li>
</ul>
```

For working example see [examples/basic](https://github.com/node-swig/swig-templates/tree/master/examples/basic)

How it works
------------

Swig reads template files and translates them into cached javascript functions. When we later render a template we call the evaluated function, passing a context object as an argument.

License
-------

Copyright (c) 2010-2013 Paul Armstrong

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the 'Software'), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
