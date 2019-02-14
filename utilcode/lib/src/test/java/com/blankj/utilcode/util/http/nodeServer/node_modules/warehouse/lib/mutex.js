'use strict';

function Mutex() {
  this._locked = false;
  this._queue = [];
}

Mutex.prototype.lock = function(fn) {
  if (this._locked) {
    this._queue.push(fn);
    return;
  }

  this._locked = true;
  fn();
};

Mutex.prototype.unlock = function() {
  if (!this._locked) return;

  var next = this._queue.shift();

  if (next) {
    next();
  } else {
    this._locked = false;
  }
};

module.exports = Mutex;
