'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});
var pad = function pad(str, size) {
  return ('000000000' + str).slice(-size);
};

var count = (function () {
  var count = 0;
  var window = window || global;

  for (var i in window) {
    count++;
  }

  return count;
})();

var globalCount = function globalCount() {
  return count;
};

exports['default'] = function () {
  return pad((navigator.mimeTypes.length + navigator.userAgent.length).toString(36) + globalCount().toString(36), 4);
};

module.exports = exports['default'];

