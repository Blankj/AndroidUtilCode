# David Gouch's String#toTitleCase() for Node.js

This is a fork of David Gouch's excellent [`String#toTitleCase()`](https://github.com/gouch/to-title-case) method inspired by John Gruber's [post on the topic](http://daringfireball.net/2008/08/title_case_update)

I've simply taken it off the `String` prototype and exported it from a simple module.

```js
var toTitleCase = require('titlecase')

console.log(toTitleCase('foo bar baz'))
```

See [Gouch's page](http://individed.com/code/to-title-case/) with inline converter. Also see this excellent **[table of test case results](http://individed.com/code/to-title-case/tests.html)** for different converters.

In addition, I've added a more comprehensive list of words to not capitalise that includes articles, prepositions and conjunctions (see source files for lists), I'm calling this "lax title case", use it like so:


```js
var toLaxTitleCase = require('titlecase').toLaxTitleCase

console.log(toLaxTitleCase('foo bar baz'))
```

**Using as an executable**

Install with `npm install titlecase -g` and you'll get a `to-title-case` executable that you can run to titlecase strings:

```
$ to-title-case "what is this thing?"
What Is This Thing?
```

*Original README:*

# To Title Case for JavaScript

Instructions: Include the to-title-case.js script and use the new .toTitleCase() method on the string you want converted.

## History
### 2.1 / 2013-11-03
- Acknowledge characters outside of US-ASCII
- Fix bug related to hyphenated small words
- Replace baby's first testing script with the QUnit framework

### 2.0.1 / 2012-01-06

- Fixed IE 7 breakage introduced in 2.0. Don't treat strings like character arrays.

### 2.0 / 2012-01-06

- 15% less code and 35% easier to understand.
- Small words list moved to variable for easy modification.
- Test titles rewritten to focus on a single issue per title.
- More braces to make style guides and JSLint happier.

## License

Copyright © 2008–2013 David Gouch. Licensed under the MIT License.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

