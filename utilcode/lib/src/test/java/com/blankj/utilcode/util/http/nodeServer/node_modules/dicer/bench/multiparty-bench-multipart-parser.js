var assert = require('assert'),
    Form = require('multiparty').Form,
    boundary = '-----------------------------168072824752491622650073',
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

var form = new Form({ boundary: boundary });

hijack('onParseHeaderField', function() {
  callbacks.headerField++;
});

hijack('onParseHeaderValue', function() {
  callbacks.headerValue++;
});

hijack('onParsePartBegin', function() {
  callbacks.partBegin++;
});

hijack('onParsePartData', function() {
  callbacks.partData++;
});

hijack('onParsePartEnd', function() {
  callbacks.partEnd++;
});

form.on('finish', function() {
  callbacks.end++;
});

var start = new Date();
form.write(buffer, function(err) {
  var duration = new Date() - start;
  assert.ifError(err);
  var mbPerSec = (mb / (duration / 1000)).toFixed(2);
  console.log(mbPerSec+' mb/sec');
});

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

function hijack(name, fn) {
  var oldFn = form[name];
  form[name] = function() {
    fn();
    return oldFn.apply(this, arguments);
  };
}
