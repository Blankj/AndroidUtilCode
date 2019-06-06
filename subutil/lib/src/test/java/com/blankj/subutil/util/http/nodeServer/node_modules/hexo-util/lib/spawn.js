'use strict';

var spawn = require('cross-spawn');
var Promise = require('bluebird');
var CacheStream = require('./cache_stream');

function promiseSpawn(command, args, options) {
  if (!command) throw new TypeError('command is required!');

  if (!options && args && !Array.isArray(args)) {
    options = args;
    args = [];
  }

  args = args || [];
  options = options || {};

  return new Promise(function(resolve, reject) {
    var task = spawn(command, args, options);
    var verbose = options.verbose;
    var encoding = options.hasOwnProperty('encoding') ? options.encoding : 'utf8';
    var stdoutCache = new CacheStream();
    var stderrCache = new CacheStream();

    if (task.stdout) {
      var stdout = task.stdout.pipe(stdoutCache);
      if (verbose) stdout.pipe(process.stdout);
    }

    if (task.stderr) {
      var stderr = task.stderr.pipe(stderrCache);
      if (verbose) stderr.pipe(process.stderr);
    }

    task.on('close', function(code) {
      if (code) {
        var e = new Error(getCache(stderrCache, encoding));
        e.code = code;

        return reject(e);
      }

      resolve(getCache(stdoutCache, encoding));
    });

    task.on('error', reject);

    // Listen to exit events if neither stdout and stderr exist (inherit stdio)
    if (!task.stdout && !task.stderr) {
      task.on('exit', function(code) {
        if (code) {
          var e = new Error('Spawn failed');
          e.code = code;

          return reject(e);
        }

        resolve();
      });
    }
  });
}

function getCache(stream, encoding) {
  var buf = stream.getCache();
  stream.destroy();
  if (!encoding) return buf;

  return buf.toString(encoding);
}

module.exports = promiseSpawn;
