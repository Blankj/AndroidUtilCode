'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});
var os = require('os');

var pad = function pad(str, size) {
  return ('000000000' + str).slice(-size);
};

var padding = 2;
var pid = pad(process.pid.toString(36), padding);
var hostname = os.hostname();

hostname = hostname.split('').reduce(function (prev, char) {
  return +prev + char.charCodeAt(0);
}, +hostname.length + 36).toString(36);

var hostId = pad(hostname, padding);

exports['default'] = function () {
  return pid + hostId;
};

module.exports = exports['default'];

