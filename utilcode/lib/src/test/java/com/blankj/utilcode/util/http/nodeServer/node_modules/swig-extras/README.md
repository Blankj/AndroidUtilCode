Swig Extras
===========

A collection of handy tags, filters, and extensions for [Swig](http://paularmstrong.github.io/swig/), the most awesome template engine for node.js.

Usage
-----

Use a filter:

```js
var swig = require('swig'),
  extras = require('swig-extras');
extras.useFilter(swig, 'markdown');
```

Use a tag:

```js
var swig = require('swig'),
  extras = require('swig-extras'),
  mySwig = new swig.Swig();
extras.useTag(mySwig, 'markdown');
```

Available Filters
-----------------

* batch
* groupby
* markdown
* nl2br
* pluck
* split
* trim
* truncate

Available Tags
--------------

* markdown
* switch/case

License
-------

Copyright (c) 2013 Paul Armstrong

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the 'Software'), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
