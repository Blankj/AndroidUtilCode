'use strict';

var Transform = require('stream').Transform;

function CacheStream() {
  Transform.call(this);

  this._cache = [];
}

require('util').inherits(CacheStream, Transform);

CacheStream.prototype._transform = function(chunk, enc, callback) {
  var buf = chunk instanceof Buffer ? chunk : new Buffer(chunk, enc);

  this._cache.push(buf);
  this.push(buf);
  callback();
};

CacheStream.prototype.destroy = function() {
  this._cache.length = 0;
};

CacheStream.prototype.getCache = function() {
  return Buffer.concat(this._cache);
};

module.exports = CacheStream;
