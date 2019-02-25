// A special, edited version of the multipart parser from parted is needed here
// because otherwise it attempts to do some things above and beyond just parsing
// -- like saving to disk and whatnot

var assert = require('assert');
var Parser = require('./parted-multipart'),
    boundary = '-----------------------------168072824752491622650073',
    parser = new Parser('boundary=' + boundary),
    mb = 100,
    buffer = createMultipartBuffer(boundary, mb * 1024 * 1024),
    callbacks =
      { partBegin: -1,
        partEnd: -1,
        headerField: -1,
        headerValue: -1,
        partData: -1,
        end: -1,
      };


parser.on('header', function() {
  //callbacks.headerField++;
});

parser.on('data', function() {
  //callbacks.partBegin++;
});

parser.on('part', function() {

});

parser.on('end', function() {
  //callbacks.end++;
});

var start = +new Date(),
    nparsed = parser.write(buffer),
    duration = +new Date - start,
    mbPerSec = (mb / (duration / 1000)).toFixed(2);

console.log(mbPerSec+' mb/sec');

//assert.equal(nparsed, buffer.length);

function createMultipartBuffer(boundary, size) {
  var head =
        '--'+boundary+'\r\n'
      + 'content-disposition: form-data; name="field1"\r\n'
      + '\r\n'
    , tail = '\r\n--'+boundary+'--\r\n'
    , buffer = new Buffer(size);

  buffer.write(head, 'ascii', 0);
  buffer.write(tail, 'ascii', buffer.length - tail.length);
  return buffer;
}

process.on('exit', function() {
  /*for (var k in callbacks) {
    assert.equal(0, callbacks[k], k+' count off by '+callbacks[k]);
  }*/
});
