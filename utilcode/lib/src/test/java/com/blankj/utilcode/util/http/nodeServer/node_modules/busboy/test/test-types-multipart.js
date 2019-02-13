var Busboy = require('..');

var path = require('path'),
    inspect = require('util').inspect,
    assert = require('assert');

var EMPTY_FN = function() {};

var t = 0,
    group = path.basename(__filename, '.js') + '/';
var tests = [
  { source: [
      ['-----------------------------paZqsnEHRufoShdX6fh0lUhXBP4k',
       'Content-Disposition: form-data; name="file_name_0"',
       '',
       'super alpha file',
       '-----------------------------paZqsnEHRufoShdX6fh0lUhXBP4k',
       'Content-Disposition: form-data; name="file_name_1"',
       '',
       'super beta file',
       '-----------------------------paZqsnEHRufoShdX6fh0lUhXBP4k',
       'Content-Disposition: form-data; name="upload_file_0"; filename="1k_a.dat"',
       'Content-Type: application/octet-stream',
       '',
       'AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA',
       '-----------------------------paZqsnEHRufoShdX6fh0lUhXBP4k',
       'Content-Disposition: form-data; name="upload_file_1"; filename="1k_b.dat"',
       'Content-Type: application/octet-stream',
       '',
       'BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB',
       '-----------------------------paZqsnEHRufoShdX6fh0lUhXBP4k--'
      ].join('\r\n')
    ],
    boundary: '---------------------------paZqsnEHRufoShdX6fh0lUhXBP4k',
    expected: [
      ['field', 'file_name_0', 'super alpha file', false, false, '7bit', 'text/plain'],
      ['field', 'file_name_1', 'super beta file', false, false, '7bit', 'text/plain'],
      ['file', 'upload_file_0', 1023, 0, '1k_a.dat', '7bit', 'application/octet-stream'],
      ['file', 'upload_file_1', 1023, 0, '1k_b.dat', '7bit', 'application/octet-stream']
    ],
    what: 'Fields and files'
  },
  { source: [
      ['------WebKitFormBoundaryTB2MiQ36fnSJlrhY',
       'Content-Disposition: form-data; name="cont"',
       '',
       'some random content',
       '------WebKitFormBoundaryTB2MiQ36fnSJlrhY',
       'Content-Disposition: form-data; name="pass"',
       '',
       'some random pass',
       '------WebKitFormBoundaryTB2MiQ36fnSJlrhY',
       'Content-Disposition: form-data; name="bit"',
       '',
       '2',
       '------WebKitFormBoundaryTB2MiQ36fnSJlrhY--'
      ].join('\r\n')
    ],
    boundary: '----WebKitFormBoundaryTB2MiQ36fnSJlrhY',
    expected: [
      ['field', 'cont', 'some random content', false, false, '7bit', 'text/plain'],
      ['field', 'pass', 'some random pass', false, false, '7bit', 'text/plain'],
      ['field', 'bit', '2', false, false, '7bit', 'text/plain']
    ],
    what: 'Fields only'
  },
  { source: [
      ''
    ],
    boundary: '----WebKitFormBoundaryTB2MiQ36fnSJlrhY',
    expected: [],
    what: 'No fields and no files'
  },
  { source: [
      ['-----------------------------paZqsnEHRufoShdX6fh0lUhXBP4k',
       'Content-Disposition: form-data; name="file_name_0"',
       '',
       'super alpha file',
       '-----------------------------paZqsnEHRufoShdX6fh0lUhXBP4k',
       'Content-Disposition: form-data; name="upload_file_0"; filename="1k_a.dat"',
       'Content-Type: application/octet-stream',
       '',
       'ABCDEFGHIJKLMNOPQRSTUVWXYZ',
       '-----------------------------paZqsnEHRufoShdX6fh0lUhXBP4k--'
      ].join('\r\n')
    ],
    boundary: '---------------------------paZqsnEHRufoShdX6fh0lUhXBP4k',
    limits: {
      fileSize: 13,
      fieldSize: 5
    },
    expected: [
      ['field', 'file_name_0', 'super', false, true, '7bit', 'text/plain'],
      ['file', 'upload_file_0', 13, 2, '1k_a.dat', '7bit', 'application/octet-stream']
    ],
    what: 'Fields and files (limits)'
  },
  { source: [
      ['-----------------------------paZqsnEHRufoShdX6fh0lUhXBP4k',
       'Content-Disposition: form-data; name="file_name_0"',
       '',
       'super alpha file',
       '-----------------------------paZqsnEHRufoShdX6fh0lUhXBP4k',
       'Content-Disposition: form-data; name="upload_file_0"; filename="1k_a.dat"',
       'Content-Type: application/octet-stream',
       '',
       'ABCDEFGHIJKLMNOPQRSTUVWXYZ',
       '-----------------------------paZqsnEHRufoShdX6fh0lUhXBP4k--'
      ].join('\r\n')
    ],
    boundary: '---------------------------paZqsnEHRufoShdX6fh0lUhXBP4k',
    limits: {
      files: 0
    },
    expected: [
      ['field', 'file_name_0', 'super alpha file', false, false, '7bit', 'text/plain']
    ],
    what: 'Fields and files (limits: 0 files)'
  },
  { source: [
      ['-----------------------------paZqsnEHRufoShdX6fh0lUhXBP4k',
       'Content-Disposition: form-data; name="file_name_0"',
       '',
       'super alpha file',
       '-----------------------------paZqsnEHRufoShdX6fh0lUhXBP4k',
       'Content-Disposition: form-data; name="file_name_1"',
       '',
       'super beta file',
       '-----------------------------paZqsnEHRufoShdX6fh0lUhXBP4k',
       'Content-Disposition: form-data; name="upload_file_0"; filename="1k_a.dat"',
       'Content-Type: application/octet-stream',
       '',
       'AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA',
       '-----------------------------paZqsnEHRufoShdX6fh0lUhXBP4k',
       'Content-Disposition: form-data; name="upload_file_1"; filename="1k_b.dat"',
       'Content-Type: application/octet-stream',
       '',
       'BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB',
       '-----------------------------paZqsnEHRufoShdX6fh0lUhXBP4k--'
      ].join('\r\n')
    ],
    boundary: '---------------------------paZqsnEHRufoShdX6fh0lUhXBP4k',
    expected: [
      ['field', 'file_name_0', 'super alpha file', false, false, '7bit', 'text/plain'],
      ['field', 'file_name_1', 'super beta file', false, false, '7bit', 'text/plain'],
    ],
    events: ['field'],
    what: 'Fields and (ignored) files'
  },
  { source: [
      ['-----------------------------paZqsnEHRufoShdX6fh0lUhXBP4k',
       'Content-Disposition: form-data; name="upload_file_0"; filename="/tmp/1k_a.dat"',
       'Content-Type: application/octet-stream',
       '',
       'ABCDEFGHIJKLMNOPQRSTUVWXYZ',
       '-----------------------------paZqsnEHRufoShdX6fh0lUhXBP4k',
       'Content-Disposition: form-data; name="upload_file_1"; filename="C:\\files\\1k_b.dat"',
       'Content-Type: application/octet-stream',
       '',
       'ABCDEFGHIJKLMNOPQRSTUVWXYZ',
       '-----------------------------paZqsnEHRufoShdX6fh0lUhXBP4k',
       'Content-Disposition: form-data; name="upload_file_2"; filename="relative/1k_c.dat"',
       'Content-Type: application/octet-stream',
       '',
       'ABCDEFGHIJKLMNOPQRSTUVWXYZ',
       '-----------------------------paZqsnEHRufoShdX6fh0lUhXBP4k--'
      ].join('\r\n')
    ],
    boundary: '---------------------------paZqsnEHRufoShdX6fh0lUhXBP4k',
    expected: [
      ['file', 'upload_file_0', 26, 0, '1k_a.dat', '7bit', 'application/octet-stream'],
      ['file', 'upload_file_1', 26, 0, '1k_b.dat', '7bit', 'application/octet-stream'],
      ['file', 'upload_file_2', 26, 0, '1k_c.dat', '7bit', 'application/octet-stream']
    ],
    what: 'Files with filenames containing paths'
  },
  { source: [
      ['-----------------------------paZqsnEHRufoShdX6fh0lUhXBP4k',
       'Content-Disposition: form-data; name="upload_file_0"; filename="/absolute/1k_a.dat"',
       'Content-Type: application/octet-stream',
       '',
       'ABCDEFGHIJKLMNOPQRSTUVWXYZ',
       '-----------------------------paZqsnEHRufoShdX6fh0lUhXBP4k',
       'Content-Disposition: form-data; name="upload_file_1"; filename="C:\\absolute\\1k_b.dat"',
       'Content-Type: application/octet-stream',
       '',
       'ABCDEFGHIJKLMNOPQRSTUVWXYZ',
       '-----------------------------paZqsnEHRufoShdX6fh0lUhXBP4k',
       'Content-Disposition: form-data; name="upload_file_2"; filename="relative/1k_c.dat"',
       'Content-Type: application/octet-stream',
       '',
       'ABCDEFGHIJKLMNOPQRSTUVWXYZ',
       '-----------------------------paZqsnEHRufoShdX6fh0lUhXBP4k--'
      ].join('\r\n')
    ],
    boundary: '---------------------------paZqsnEHRufoShdX6fh0lUhXBP4k',
    preservePath: true,
    expected: [
      ['file', 'upload_file_0', 26, 0, '/absolute/1k_a.dat', '7bit', 'application/octet-stream'],
      ['file', 'upload_file_1', 26, 0, 'C:\\absolute\\1k_b.dat', '7bit', 'application/octet-stream'],
      ['file', 'upload_file_2', 26, 0, 'relative/1k_c.dat', '7bit', 'application/octet-stream']
    ],
    what: 'Paths to be preserved through the preservePath option'
  },
  { source: [
      ['------WebKitFormBoundaryTB2MiQ36fnSJlrhY',
       'Content-Disposition: form-data; name="cont"',
       'Content-Type: ',
       '',
       'some random content',
       '------WebKitFormBoundaryTB2MiQ36fnSJlrhY',
       'Content-Disposition: ',
       '',
       'some random pass',
       '------WebKitFormBoundaryTB2MiQ36fnSJlrhY--'
      ].join('\r\n')
    ],
    boundary: '----WebKitFormBoundaryTB2MiQ36fnSJlrhY',
    expected: [
      ['field', 'cont', 'some random content', false, false, '7bit', 'text/plain']
    ],
    what: 'Empty content-type and empty content-disposition'
  },
  { source: [
      ['--asdasdasdasd\r\n',
       'Content-Type: text/plain\r\n',
       'Content-Disposition: form-data; name="foo"\r\n',
       '\r\n',
       'asd\r\n',
       '--asdasdasdasd--'
      ].join(':)')
    ],
    boundary: 'asdasdasdasd',
    expected: [],
    shouldError: 'Unexpected end of multipart data',
    what: 'Stopped mid-header'
  },
  { source: [
      ['------WebKitFormBoundaryTB2MiQ36fnSJlrhY',
       'Content-Disposition: form-data; name="cont"',
       'Content-Type: application/json',
       '',
       '{}',
       '------WebKitFormBoundaryTB2MiQ36fnSJlrhY--',
      ].join('\r\n')
    ],
    boundary: '----WebKitFormBoundaryTB2MiQ36fnSJlrhY',
    expected: [
      ['field', 'cont', '{}', false, false, '7bit', 'application/json']
    ],
    what: 'content-type for fields'
  },
  { source: [
      '------WebKitFormBoundaryTB2MiQ36fnSJlrhY--\r\n'
    ],
    boundary: '----WebKitFormBoundaryTB2MiQ36fnSJlrhY',
    expected: [],
    what: 'empty form'
  }
];

function next() {
  if (t === tests.length)
    return;

  var v = tests[t];

  var busboy = new Busboy({
        limits: v.limits,
        preservePath: v.preservePath,
        headers: {
          'content-type': 'multipart/form-data; boundary=' + v.boundary
        }
      }),
      finishes = 0,
      results = [];

  if (v.events === undefined || v.events.indexOf('field') > -1) {
    busboy.on('field', function(key, val, keyTrunc, valTrunc, encoding, contype) {
      results.push(['field', key, val, keyTrunc, valTrunc, encoding, contype]);
    });
  }
  if (v.events === undefined || v.events.indexOf('file') > -1) {
    busboy.on('file', function(fieldname, stream, filename, encoding, mimeType) {
      var nb = 0,
          info = ['file',
                  fieldname,
                  nb,
                  0,
                  filename,
                  encoding,
                  mimeType];
      results.push(info);
      stream.on('data', function(d) {
        nb += d.length;
      }).on('limit', function() {
        ++info[3];
      }).on('end', function() {
        info[2] = nb;
        if (stream.truncated)
          ++info[3];
      });
    });
  }
  busboy.on('finish', function() {
    assert(finishes++ === 0, makeMsg(v.what, 'finish emitted multiple times'));
    assert.deepEqual(results.length,
                     v.expected.length,
                     makeMsg(v.what, 'Parsed result count mismatch. Saw '
                                     + results.length
                                     + '. Expected: ' + v.expected.length));

    results.forEach(function(result, i) {
      assert.deepEqual(result,
                       v.expected[i],
                       makeMsg(v.what,
                               'Result mismatch:\nParsed: ' + inspect(result)
                               + '\nExpected: ' + inspect(v.expected[i]))
                      );
    });
    ++t;
    next();
  }).on('error', function(err) {
    if (!v.shouldError || v.shouldError !== err.message)
      assert(false, makeMsg(v.what, 'Unexpected error: ' + err));
  });

  v.source.forEach(function(s) {
    busboy.write(new Buffer(s, 'utf8'), EMPTY_FN);
  });
  busboy.end();
}
next();

function makeMsg(what, msg) {
  return '[' + group + what + ']: ' + msg;
}

process.on('exit', function() {
  assert(t === tests.length,
         makeMsg('_exit',
                 'Only finished ' + t + '/' + tests.length + ' tests'));
});
