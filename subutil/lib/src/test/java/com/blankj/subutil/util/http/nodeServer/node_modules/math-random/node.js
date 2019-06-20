var crypto = require('crypto')
var max = Math.pow(2, 32)

module.exports = random
module.exports.cryptographic = true

function random () {
  var buf = crypto
    .randomBytes(4)
    .readUInt32BE(0)

  return buf / max
}
