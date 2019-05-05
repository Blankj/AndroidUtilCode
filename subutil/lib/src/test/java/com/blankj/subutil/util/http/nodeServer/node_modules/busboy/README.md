Description
===========

A node.js module for parsing incoming HTML form data.

If you've found this module to be useful and wish to support it, you may do so by visiting this pledgie campaign:
<a href='https://pledgie.com/campaigns/28774'><img alt='Click here to support busboy' src='https://pledgie.com/campaigns/28774.png?skin_name=chrome' border='0'></a>


Requirements
============

* [node.js](http://nodejs.org/) -- v0.8.0 or newer


Install
=======

    npm install busboy


Examples
========

* Parsing (multipart) with default options:

```javascript
var http = require('http'),
    inspect = require('util').inspect;

var Busboy = require('busboy');

http.createServer(function(req, res) {
  if (req.method === 'POST') {
    var busboy = new Busboy({ headers: req.headers });
    busboy.on('file', function(fieldname, file, filename, encoding, mimetype) {
      console.log('File [' + fieldname + ']: filename: ' + filename + ', encoding: ' + encoding + ', mimetype: ' + mimetype);
      file.on('data', function(data) {
        console.log('File [' + fieldname + '] got ' + data.length + ' bytes');
      });
      file.on('end', function() {
        console.log('File [' + fieldname + '] Finished');
      });
    });
    busboy.on('field', function(fieldname, val, fieldnameTruncated, valTruncated, encoding, mimetype) {
      console.log('Field [' + fieldname + ']: value: ' + inspect(val));
    });
    busboy.on('finish', function() {
      console.log('Done parsing form!');
      res.writeHead(303, { Connection: 'close', Location: '/' });
      res.end();
    });
    req.pipe(busboy);
  } else if (req.method === 'GET') {
    res.writeHead(200, { Connection: 'close' });
    res.end('<html><head></head><body>\
               <form method="POST" enctype="multipart/form-data">\
                <input type="text" name="textfield"><br />\
                <input type="file" name="filefield"><br />\
                <input type="submit">\
              </form>\
            </body></html>');
  }
}).listen(8000, function() {
  console.log('Listening for requests');
});

// Example output, using http://nodejs.org/images/ryan-speaker.jpg as the file:
//
// Listening for requests
// File [filefield]: filename: ryan-speaker.jpg, encoding: binary
// File [filefield] got 11971 bytes
// Field [textfield]: value: 'testing! :-)'
// File [filefield] Finished
// Done parsing form!
```

* Save all incoming files to disk:

```javascript
var http = require('http'),
    path = require('path'),
    os = require('os'),
    fs = require('fs');

var Busboy = require('busboy');

http.createServer(function(req, res) {
  if (req.method === 'POST') {
    var busboy = new Busboy({ headers: req.headers });
    busboy.on('file', function(fieldname, file, filename, encoding, mimetype) {
      var saveTo = path.join(os.tmpDir(), path.basename(fieldname));
      file.pipe(fs.createWriteStream(saveTo));
    });
    busboy.on('finish', function() {
      res.writeHead(200, { 'Connection': 'close' });
      res.end("That's all folks!");
    });
    return req.pipe(busboy);
  }
  res.writeHead(404);
  res.end();
}).listen(8000, function() {
  console.log('Listening for requests');
});
```

* Parsing (urlencoded) with default options:

```javascript
var http = require('http'),
    inspect = require('util').inspect;

var Busboy = require('busboy');

http.createServer(function(req, res) {
  if (req.method === 'POST') {
    var busboy = new Busboy({ headers: req.headers });
    busboy.on('file', function(fieldname, file, filename, encoding, mimetype) {
      console.log('File [' + fieldname + ']: filename: ' + filename);
      file.on('data', function(data) {
        console.log('File [' + fieldname + '] got ' + data.length + ' bytes');
      });
      file.on('end', function() {
        console.log('File [' + fieldname + '] Finished');
      });
    });
    busboy.on('field', function(fieldname, val, fieldnameTruncated, valTruncated) {
      console.log('Field [' + fieldname + ']: value: ' + inspect(val));
    });
    busboy.on('finish', function() {
      console.log('Done parsing form!');
      res.writeHead(303, { Connection: 'close', Location: '/' });
      res.end();
    });
    req.pipe(busboy);
  } else if (req.method === 'GET') {
    res.writeHead(200, { Connection: 'close' });
    res.end('<html><head></head><body>\
               <form method="POST">\
                <input type="text" name="textfield"><br />\
                <select name="selectfield">\
                  <option value="1">1</option>\
                  <option value="10">10</option>\
                  <option value="100">100</option>\
                  <option value="9001">9001</option>\
                </select><br />\
                <input type="checkbox" name="checkfield">Node.js rules!<br />\
                <input type="submit">\
              </form>\
            </body></html>');
  }
}).listen(8000, function() {
  console.log('Listening for requests');
});

// Example output:
//
// Listening for requests
// Field [textfield]: value: 'testing! :-)'
// Field [selectfield]: value: '9001'
// Field [checkfield]: value: 'on'
// Done parsing form!
```


API
===

_Busboy_ is a _Writable_ stream

Busboy (special) events
-----------------------

* **file**(< _string_ >fieldname, < _ReadableStream_ >stream, < _string_ >filename, < _string_ >transferEncoding, < _string_ >mimeType) - Emitted for each new file form field found. `transferEncoding` contains the 'Content-Transfer-Encoding' value for the file stream. `mimeType` contains the 'Content-Type' value for the file stream.
    * Note: if you listen for this event, you should always handle the `stream` no matter if you care about the file contents or not (e.g. you can simply just do `stream.resume();` if you want to discard the contents), otherwise the 'finish' event will never fire on the Busboy instance. However, if you don't care about **any** incoming files, you can simply not listen for the 'file' event at all and any/all files will be automatically and safely discarded (these discarded files do still count towards `files` and `parts` limits).
    * If a configured file size limit was reached, `stream` will both have a boolean property `truncated` (best checked at the end of the stream) and emit a 'limit' event to notify you when this happens.

* **field**(< _string_ >fieldname, < _string_ >value, < _boolean_ >fieldnameTruncated, < _boolean_ >valueTruncated, < _string_ >transferEncoding, < _string_ >mimeType) - Emitted for each new non-file field found.

* **partsLimit**() - Emitted when specified `parts` limit has been reached. No more 'file' or 'field' events will be emitted.

* **filesLimit**() - Emitted when specified `files` limit has been reached. No more 'file' events will be emitted.

* **fieldsLimit**() - Emitted when specified `fields` limit has been reached. No more 'field' events will be emitted.


Busboy methods
--------------

* **(constructor)**(< _object_ >config) - Creates and returns a new Busboy instance.

    * The constructor takes the following valid `config` settings:

        * **headers** - _object_ - These are the HTTP headers of the incoming request, which are used by individual parsers.

        * **highWaterMark** - _integer_ - highWaterMark to use for this Busboy instance (Default: WritableStream default).

        * **fileHwm** - _integer_ - highWaterMark to use for file streams (Default: ReadableStream default).

        * **defCharset** - _string_ - Default character set to use when one isn't defined (Default: 'utf8').

        * **preservePath** - _boolean_ - If paths in the multipart 'filename' field shall be preserved. (Default: false).

        * **limits** - _object_ - Various limits on incoming data. Valid properties are:

            * **fieldNameSize** - _integer_ - Max field name size (in bytes) (Default: 100 bytes).

            * **fieldSize** - _integer_ - Max field value size (in bytes) (Default: 1MB).

            * **fields** - _integer_ - Max number of non-file fields (Default: Infinity).

            * **fileSize** - _integer_ - For multipart forms, the max file size (in bytes) (Default: Infinity).

            * **files** - _integer_ - For multipart forms, the max number of file fields (Default: Infinity).

            * **parts** - _integer_ - For multipart forms, the max number of parts (fields + files) (Default: Infinity).

            * **headerPairs** - _integer_ - For multipart forms, the max number of header key=>value pairs to parse **Default:** 2000 (same as node's http).

    * The constructor can throw errors:

        * **Unsupported content type: $type** - The `Content-Type` isn't one Busboy can parse.

        * **Missing Content-Type** - The provided headers don't include `Content-Type` at all.
