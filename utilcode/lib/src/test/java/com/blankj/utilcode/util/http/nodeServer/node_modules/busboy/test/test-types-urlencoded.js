var Busboy = require('..');

var path = require('path'),
    inspect = require('util').inspect,
    assert = require('assert');

var EMPTY_FN = function() {};

var t = 0,
    group = path.basename(__filename, '.js') + '/';

var tests = [
  { source: ['foo'],
    expected: [['foo', '', false, false]],
    what: 'Unassigned value'
  },
  { source: ['foo=bar'],
    expected: [['foo', 'bar', false, false]],
    what: 'Assigned value'
  },
  { source: ['foo&bar=baz'],
    expected: [['foo', '', false, false],
               ['bar', 'baz', false, false]],
    what: 'Unassigned and assigned value'
  },
  { source: ['foo=bar&baz'],
    expected: [['foo', 'bar', false, false],
               ['baz', '', false, false]],
    what: 'Assigned and unassigned value'
  },
  { source: ['foo=bar&baz=bla'],
    expected: [['foo', 'bar', false, false],
               ['baz', 'bla', false, false]],
    what: 'Two assigned values'
  },
  { source: ['foo&bar'],
    expected: [['foo', '', false, false],
               ['bar', '', false, false]],
    what: 'Two unassigned values'
  },
  { source: ['foo&bar&'],
    expected: [['foo', '', false, false],
               ['bar', '', false, false]],
    what: 'Two unassigned values and ampersand'
  },
  { source: ['foo=bar+baz%2Bquux'],
    expected: [['foo', 'bar baz+quux', false, false]],
    what: 'Assigned value with (plus) space'
  },
  { source: ['foo=bar%20baz%21'],
    expected: [['foo', 'bar baz!', false, false]],
    what: 'Assigned value with encoded bytes'
  },
  { source: ['foo%20bar=baz%20bla%21'],
    expected: [['foo bar', 'baz bla!', false, false]],
    what: 'Assigned value with encoded bytes #2'
  },
  { source: ['foo=bar%20baz%21&num=1000'],
    expected: [['foo', 'bar baz!', false, false],
               ['num', '1000', false, false]],
    what: 'Two assigned values, one with encoded bytes'
  },
  { source: ['foo=bar&baz=bla'],
    expected: [],
    what: 'Limits: zero fields',
    limits: { fields: 0 }
  },
  { source: ['foo=bar&baz=bla'],
    expected: [['foo', 'bar', false, false]],
    what: 'Limits: one field',
    limits: { fields: 1 }
  },
  { source: ['foo=bar&baz=bla'],
    expected: [['foo', 'bar', false, false],
               ['baz', 'bla', false, false]],
    what: 'Limits: field part lengths match limits',
    limits: { fieldNameSize: 3, fieldSize: 3 }
  },
  { source: ['foo=bar&baz=bla'],
    expected: [['fo', 'bar', true, false],
               ['ba', 'bla', true, false]],
    what: 'Limits: truncated field name',
    limits: { fieldNameSize: 2 }
  },
  { source: ['foo=bar&baz=bla'],
    expected: [['foo', 'ba', false, true],
               ['baz', 'bl', false, true]],
    what: 'Limits: truncated field value',
    limits: { fieldSize: 2 }
  },
  { source: ['foo=bar&baz=bla'],
    expected: [['fo', 'ba', true, true],
               ['ba', 'bl', true, true]],
    what: 'Limits: truncated field name and value',
    limits: { fieldNameSize: 2, fieldSize: 2 }
  },
  { source: ['foo=bar&baz=bla'],
    expected: [['fo', '', true, true],
               ['ba', '', true, true]],
    what: 'Limits: truncated field name and zero value limit',
    limits: { fieldNameSize: 2, fieldSize: 0 }
  },
  { source: ['foo=bar&baz=bla'],
    expected: [['', '', true, true],
               ['', '', true, true]],
    what: 'Limits: truncated zero field name and zero value limit',
    limits: { fieldNameSize: 0, fieldSize: 0 }
  },
  { source: ['&'],
    expected: [],
    what: 'Ampersand'
  },
  { source: ['&&&&&'],
    expected: [],
    what: 'Many ampersands'
  },
  { source: ['='],
    expected: [['', '', false, false]],
    what: 'Assigned value, empty name and value'
  },
  { source: [''],
    expected: [],
    what: 'Nothing'
  },
];

function next() {
  if (t === tests.length)
    return;

  var v = tests[t];

  var busboy = new Busboy({
        limits: v.limits,
        headers: {
          'content-type': 'application/x-www-form-urlencoded; charset=utf-8'
        }
      }),
      finishes = 0,
      results = [];

  busboy.on('field', function(key, val, keyTrunc, valTrunc) {
    results.push([key, val, keyTrunc, valTrunc]);
  });
  busboy.on('file', function() {
    throw new Error(makeMsg(v.what, 'Unexpected file'));
  });
  busboy.on('finish', function() {
    assert(finishes++ === 0, makeMsg(v.what, 'finish emitted multiple times'));
    assert.deepEqual(results.length,
                     v.expected.length,
                     makeMsg(v.what, 'Parsed result count mismatch. Saw '
                                     + results.length
                                     + '. Expected: ' + v.expected.length));

    var i = 0;
    results.forEach(function(result) {
      assert.deepEqual(result,
                       v.expected[i],
                       makeMsg(v.what,
                               'Result mismatch:\nParsed: ' + inspect(result)
                               + '\nExpected: ' + inspect(v.expected[i]))
                      );
      ++i;
    });
    ++t;
    next();
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
  assert(t === tests.length, makeMsg('_exit', 'Only finished ' + t + '/' + tests.length + ' tests'));
});
