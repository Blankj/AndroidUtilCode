import corejs from 'core-js';
import test from 'tape';
import cuid from '../../source/client';

const { slug } = cuid;
const after = test;
const MAX = 1200000;

const collisionTest = (fn) => {
  let i = 0;
  let ids = {};
  let pass = true;

  while (i < MAX) {
    let id = fn();

    if (!ids[id]) {
      ids[id] = id;
    } else {
      pass = false;
      console.log('Failed at ' + i + ' iterations.');
      break;
    }

    i++;
  }

  return pass;
};

test('cuid()', assert => {
  assert.ok(typeof cuid() === 'string',
    '.cuid() should return a string.');

  assert.ok(collisionTest(cuid),
    'cuids should not collide.');

  assert.ok(collisionTest(slug),
    'slugs should not collide.');

  assert.end();
});

after('complete', (assert) => {
  assert.end();
});
