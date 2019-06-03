var Decoder = require('../lib/utils').Decoder;

var path = require('path'),
    assert = require('assert');

var group = path.basename(__filename, '.js') + '/';

[
  { source: ['Hello world'],
    expected: 'Hello world',
    what: 'No encoded bytes'
  },
  { source: ['Hello%20world'],
    expected: 'Hello world',
    what: 'One full encoded byte'
  },
  { source: ['Hello%20world%21'],
    expected: 'Hello world!',
    what: 'Two full encoded bytes'
  },
  { source: ['Hello%', '20world'],
    expected: 'Hello world',
    what: 'One full encoded byte split #1'
  },
  { source: ['Hello%2', '0world'],
    expected: 'Hello world',
    what: 'One full encoded byte split #2'
  },
  { source: ['Hello%20', 'world'],
    expected: 'Hello world',
    what: 'One full encoded byte (concat)'
  },
  { source: ['Hello%2Qworld'],
    expected: 'Hello%2Qworld',
    what: 'Malformed encoded byte #1'
  },
  { source: ['Hello%world'],
    expected: 'Hello%world',
    what: 'Malformed encoded byte #2'
  },
  { source: ['Hello+world'],
    expected: 'Hello world',
    what: 'Plus to space'
  },
  { source: ['Hello+world%21'],
    expected: 'Hello world!',
    what: 'Plus and encoded byte'
  },
  { source: ['5%2B5%3D10'],
    expected: '5+5=10',
    what: 'Encoded plus'
  },
  { source: ['5+%2B+5+%3D+10'],
    expected: '5 + 5 = 10',
    what: 'Spaces and encoded plus'
  },
].forEach(function(v) {
  var dec = new Decoder(), result = '';
  v.source.forEach(function(s) {
    result += dec.write(s);
  });
  var msg = '[' + group + v.what + ']: decoded string mismatch.\n'
            + 'Saw: ' + result + '\n'
            + 'Expected: ' + v.expected;
  assert.deepEqual(result, v.expected, msg);
});
