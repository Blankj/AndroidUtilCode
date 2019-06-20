
Description
===========

A very fast streaming multipart parser for node.js.

Benchmarks can be found [here](https://github.com/mscdex/dicer/wiki/Benchmarks).


Requirements
============

* [node.js](http://nodejs.org/) -- v0.8.0 or newer


Install
============

    npm install dicer


Examples
========

* Parse an HTTP form upload

```javascript
var inspect = require('util').inspect,
    http = require('http');

var Dicer = require('dicer');

    // quick and dirty way to parse multipart boundary
var RE_BOUNDARY = /^multipart\/.+?(?:; boundary=(?:(?:"(.+)")|(?:([^\s]+))))$/i,
    HTML = new Buffer('<html><head></head><body>\
                       <form method="POST" enctype="multipart/form-data">\
                         <input type="text" name="textfield"><br />\
                         <input type="file" name="filefield"><br />\
                         <input type="submit">\
                       </form>\
                       </body></html>'),
    PORT = 8080;

http.createServer(function(req, res) {
  var m;
  if (req.method === 'POST'
      && req.headers['content-type']
      && (m = RE_BOUNDARY.exec(req.headers['content-type']))) {
    var d = new Dicer({ boundary: m[1] || m[2] });

    d.on('part', function(p) {
      console.log('New part!');
      p.on('header', function(header) {
        for (var h in header) {
          console.log('Part header: k: ' + inspect(h)
                      + ', v: ' + inspect(header[h]));
        }
      });
      p.on('data', function(data) {
        console.log('Part data: ' + inspect(data.toString()));
      });
      p.on('end', function() {
        console.log('End of part\n');
      });
    });
    d.on('finish', function() {
      console.log('End of parts');
      res.writeHead(200);
      res.end('Form submission successful!');
    });
    req.pipe(d);
  } else if (req.method === 'GET' && req.url === '/') {
    res.writeHead(200);
    res.end(HTML);
  } else {
    res.writeHead(404);
    res.end();
  }
}).listen(PORT, function() {
  console.log('Listening for requests on port ' + PORT);
});
```


API
===

_Dicer_ is a _WritableStream_

Dicer (special) events
----------------------

* **finish**() - Emitted when all parts have been parsed and the Dicer instance has been ended.

* **part**(< _PartStream_ >stream) - Emitted when a new part has been found.

* **preamble**(< _PartStream_ >stream) - Emitted for preamble if you should happen to need it (can usually be ignored).

* **trailer**(< _Buffer_ >data) - Emitted when trailing data was found after the terminating boundary (as with the preamble, this can usually be ignored too).


Dicer methods
-------------

* **(constructor)**(< _object_ >config) - Creates and returns a new Dicer instance with the following valid `config` settings:

    * **boundary** - _string_ - This is the boundary used to detect the beginning of a new part.

    * **headerFirst** - _boolean_ - If true, preamble header parsing will be performed first.

    * **maxHeaderPairs** - _integer_ - The maximum number of header key=>value pairs to parse **Default:** 2000 (same as node's http).

* **setBoundary**(< _string_ >boundary) - _(void)_ - Sets the boundary to use for parsing and performs some initialization needed for parsing. You should only need to use this if you set `headerFirst` to true in the constructor and are parsing the boundary from the preamble header.



_PartStream_ is a _ReadableStream_

PartStream (special) events
---------------------------

* **header**(< _object_ >header) - An object containing the header for this particular part. Each property value is an _array_ of one or more string values.
