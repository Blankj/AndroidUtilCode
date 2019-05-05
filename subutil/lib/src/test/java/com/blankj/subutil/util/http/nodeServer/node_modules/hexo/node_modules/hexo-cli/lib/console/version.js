'use strict';

var os = require('os');
var pkg = require('../../package.json');
var Promise = require('bluebird');

function versionConsole(args) {
  var versions = process.versions;
  var keys = Object.keys(versions);
  var key = '';

  if (this.version) {
    console.log('hexo:', this.version);
  }

  console.log('hexo-cli:', pkg.version);
  console.log('os:', os.type(), os.release(), os.platform(), os.arch());

  for (var i = 0, len = keys.length; i < len; i++) {
    key = keys[i];
    console.log('%s: %s', key, versions[key]);
  }

  return Promise.resolve();
}

module.exports = versionConsole;
