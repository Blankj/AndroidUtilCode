'use strict';

var pathFn = require('path');
var fs = require('hexo-fs');

function findPkg(cwd, args) {
  args = args || {};

  if (args.cwd) {
    cwd = pathFn.resolve(cwd, args.cwd);
  }

  return checkPkg(cwd);
}

function checkPkg(path) {
  var pkgPath = pathFn.join(path, 'package.json');

  return fs.readFile(pkgPath).then(function(content) {
    var json = JSON.parse(content);
    if (typeof json.hexo === 'object') return path;
  }).catch(function(err) {
    if (err && err.cause.code === 'ENOENT') {
      var parent = pathFn.dirname(path);

      if (parent === path) return;
      return checkPkg(parent);
    }

    throw err;
  });
}

module.exports = findPkg;
