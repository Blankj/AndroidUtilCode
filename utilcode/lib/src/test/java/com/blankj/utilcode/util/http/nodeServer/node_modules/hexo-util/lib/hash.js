'use strict';

var Stream = require('stream');
var Transform = Stream.Transform;
var crypto = require('crypto');

var ALGORITHM = 'sha1';

function HashStream() {
  Transform.call(this, {
    objectMode: true
  });

  this._hash = crypto.createHash(ALGORITHM);
}

require('util').inherits(HashStream, Transform);

HashStream.prototype._transform = function(chunk, enc, callback) {
  var buffer = chunk instanceof Buffer ? chunk : new Buffer(chunk, enc);

  this._hash.update(buffer);
  callback();
};

HashStream.prototype._flush = function(callback) {
  this.push(this._hash.digest());
  callback();
};

exports.hash = function(content) {
  var hash = crypto.createHash(ALGORITHM);
  hash.update(content);
  return hash.digest();
};

exports.HashStream = HashStream;
