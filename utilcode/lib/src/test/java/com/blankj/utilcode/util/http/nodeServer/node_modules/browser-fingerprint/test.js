'use strict'

  var collision = false;

import fingerprint from './index.js'

let test = require('tape')

let ver = `Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_3`
let platform = `AppleWebKit/537.36 (KHTML, like Gecko)`
let ext = `Chrome/43.0.2357.130 Safari/537.36`

global.navigator = {
  mimeTypes: new Array(8),
  userAgent: `${ver} ${platform} ${ext}`
}

test('cuid()', (t) => {
  t.plan(2)
  t.equal(typeof fingerprint(), 'string', 'fingerprint() should return a string');
  t.equal(fingerprint(), fingerprint(), 'fingerprints should match');
})
