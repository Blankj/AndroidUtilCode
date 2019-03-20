'use strict';

var Promise = require('bluebird');
var fs = require('graceful-fs');
var pathFn = require('path');
var chokidar = require('chokidar');
var escapeRegExp = require('escape-string-regexp');

var dirname = pathFn.dirname;
var join = pathFn.join;
var rEOL = new RegExp('\\r\\n', 'g');

var statAsync = Promise.promisify(fs.stat);
var readdirAsync = Promise.promisify(fs.readdir);
var unlinkAsync = Promise.promisify(fs.unlink);
var mkdirAsync = Promise.promisify(fs.mkdir);
var writeFileAsync = Promise.promisify(fs.writeFile);
var appendFileAsync = Promise.promisify(fs.appendFile);
var rmdirAsync = Promise.promisify(fs.rmdir);
var readFileAsync = Promise.promisify(fs.readFile);
var createReadStream = fs.createReadStream;
var createWriteStream = fs.createWriteStream;
var accessSync = fs.accessSync;
var accessAsync, exists, existsSync;

if (fs.access) {
  accessAsync = Promise.promisify(fs.access);

  exists = _existsNew;
  existsSync = _existsSyncNew;
} else {
  exists = _existsOld;
  existsSync = fs.existsSync;
}

function _existsOld(path, callback) {
  if (!path) throw new TypeError('path is required!');

  return new Promise(function(resolve, reject) {
    fs.exists(path, function(exist) {
      resolve(exist);
      if (typeof callback === 'function') callback(exist);
    });
  });
}

function _existsNew(path, callback) {
  if (!path) throw new TypeError('path is required!');

  return accessAsync(path).then(function() {
    return true;
  }, function() {

    return false;
  }).then(function(exist) {
    if (typeof callback === 'function') callback(exist);
    return exist;
  });
}

function _existsSyncNew(path) {
  if (!path) throw new TypeError('path is required!');

  try {
    fs.accessSync(path);
  } catch (err) {
    return false;
  }

  return true;
}

function mkdirs(path, callback) {
  if (!path) throw new TypeError('path is required!');

  var parent = dirname(path);

  return exists(parent).then(function(exist) {
    if (!exist) return mkdirs(parent);
  }).then(function() {
    return mkdirAsync(path);
  }).catch(function(err) {
    if (err.cause.code !== 'EEXIST') throw err;
  }).asCallback(callback);
}

function mkdirsSync(path) {
  if (!path) throw new TypeError('path is required!');

  var parent = dirname(path);
  var exist = fs.existsSync(parent);

  if (!exist) mkdirsSync(parent);
  fs.mkdirSync(path);
}

function checkParent(path) {
  if (!path) throw new TypeError('path is required!');

  var parent = dirname(path);

  return exists(parent).then(function(exist) {
    if (!exist) return mkdirs(parent);
  });
}

function checkParentSync(path) {
  if (!path) throw new TypeError('path is required!');

  var parent = dirname(path);
  var exist = fs.existsSync(parent);

  if (exist) return;

  try {
    mkdirsSync(parent);
  } catch (err) {
    if (err.code !== 'EEXIST') throw err;
  }
}

function writeFile(path, data, options, callback) {
  if (!path) throw new TypeError('path is required!');

  if (!callback && typeof options === 'function') {
    callback = options;
    options = {};
  }

  return checkParent(path).then(function() {
    return writeFileAsync(path, data, options);
  }).asCallback(callback);
}

function writeFileSync(path, data, options) {
  if (!path) throw new TypeError('path is required!');

  checkParentSync(path);
  fs.writeFileSync(path, data, options);
}

function appendFile(path, data, options, callback) {
  if (!path) throw new TypeError('path is required!');

  if (!callback && typeof options === 'function') {
    callback = options;
    options = {};
  }

  return checkParent(path).then(function() {
    return appendFileAsync(path, data, options);
  }).asCallback(callback);
}

function appendFileSync(path, data, options) {
  if (!path) throw new TypeError('path is required!');

  checkParentSync(path);
  fs.appendFileSync(path, data, options);
}

const _copyFile = fs.copyFile ? Promise.promisify(fs.copyFile) : function(src, dest) {
  return new Promise(function(resolve, reject) {
    var rs = createReadStream(src);
    var ws = createWriteStream(dest);

    rs.pipe(ws).on('error', reject);

    ws.on('close', resolve).on('error', reject);
  });
};

function copyFile(src, dest, flags, callback) {
  if (!src) throw new TypeError('src is required!');
  if (!dest) throw new TypeError('dest is required!');
  if (typeof flags === 'function') {
    callback = flags;
  }

  return checkParent(dest).then(function() {
    return _copyFile(src, dest, flags);
  }).asCallback(callback);
}

function trueFn() {
  return true;
}

function ignoreHiddenFiles(ignore) {
  if (!ignore) return trueFn;

  return function(item) {
    return item[0] !== '.';
  };
}

function ignoreFilesRegex(regex) {
  if (!regex) return trueFn;

  return function(item) {
    return !regex.test(item);
  };
}

function ignoreExcludeFiles(arr, parent) {
  if (!arr || !arr.length) return trueFn;

  // Build a map to make it faster.
  var map = {};

  for (var i = 0, len = arr.length; i < len; i++) {
    map[arr[i]] = true;
  }

  return function(item) {
    var path = join(parent, item.path);
    return !map[path];
  };
}

function reduceFiles(result, item) {
  if (Array.isArray(item)) {
    return result.concat(item);
  }

  result.push(item);
  return result;
}

function _readAndFilterDir(path, options) {
  return readdirAsync(path)
    .filter(ignoreHiddenFiles(options.ignoreHidden == null ? true : options.ignoreHidden))
    .filter(ignoreFilesRegex(options.ignorePattern))
    .map(function(item) {
      var fullPath = join(path, item);

      return statAsync(fullPath).then(function(stats) {
        return {
          isDirectory: stats.isDirectory(),
          path: item,
          fullPath: fullPath
        };
      });
    });
}

function _readAndFilterDirSync(path, options) {
  return fs.readdirSync(path)
    .filter(ignoreHiddenFiles(options.ignoreHidden == null ? true : options.ignoreHidden))
    .filter(ignoreFilesRegex(options.ignorePattern))
    .map(function(item) {
      var fullPath = join(path, item);
      var stats = fs.statSync(fullPath);

      return {
        isDirectory: stats.isDirectory(),
        path: item,
        fullPath: fullPath
      };
    });
}

function _copyDir(src, dest, options, parent) {
  options = options || {};
  parent = parent || '';

  return checkParent(dest).then(function() {
    return _readAndFilterDir(src, options);
  })
    .map(function(item) {
      var childSrc = item.fullPath;
      var childDest = join(dest, item.path);

      if (item.isDirectory) {
        return _copyDir(childSrc, childDest, options, join(parent, item.path));
      }

      return copyFile(childSrc, childDest, options)
        .thenReturn(join(parent, item.path));
    }).reduce(reduceFiles, []);
}

function copyDir(src, dest, options, callback) {
  if (!src) throw new TypeError('src is required!');
  if (!dest) throw new TypeError('dest is required!');

  if (!callback && typeof options === 'function') {
    callback = options;
    options = {};
  }

  return _copyDir(src, dest, options).asCallback(callback);
}

function _listDir(path, options, parent) {
  options = options || {};
  parent = parent || '';

  return _readAndFilterDir(path, options).map(function(item) {
    if (item.isDirectory) {
      return _listDir(item.fullPath, options, join(parent, item.path));
    }

    return join(parent, item.path);
  }).reduce(reduceFiles, []);
}

function listDir(path, options, callback) {
  if (!path) throw new TypeError('path is required!');

  if (!callback && typeof options === 'function') {
    callback = options;
    options = {};
  }

  return _listDir(path, options).asCallback(callback);
}

function listDirSync(path, options, parent) {
  if (!path) throw new TypeError('path is required!');

  options = options || {};
  parent = parent || '';

  return _readAndFilterDirSync(path, options).map(function(item) {
    if (item.isDirectory) {
      return listDirSync(item.fullPath, options, join(parent, item.path));
    }

    return join(parent, item.path);
  }).reduce(reduceFiles, []);
}

function escapeEOL(str) {
  return str.replace(rEOL, '\n');
}

function escapeBOM(str) {
  return str.charCodeAt(0) === 0xFEFF ? str.substring(1) : str;
}

function escapeFileContent(content) {
  return escapeBOM(escapeEOL(content));
}

function readFile(path, options, callback) {
  if (!path) throw new TypeError('path is required!');

  if (!callback && typeof options === 'function') {
    callback = options;
    options = {};
  }

  options = options || {};
  if (!options.hasOwnProperty('encoding')) options.encoding = 'utf8';

  return readFileAsync(path, options).then(function(content) {
    if (options.escape == null || options.escape) {
      return escapeFileContent(content);
    }

    return content;
  }).asCallback(callback);
}

function readFileSync(path, options) {
  if (!path) throw new TypeError('path is required!');

  options = options || {};
  if (!options.hasOwnProperty('encoding')) options.encoding = 'utf8';

  var content = fs.readFileSync(path, options);

  if (options.escape == null || options.escape) {
    return escapeFileContent(content);
  }

  return content;
}

function _emptyDir(path, options, parent) {
  options = options || {};
  parent = parent || '';

  return _readAndFilterDir(path, options)
    .filter(ignoreExcludeFiles(options.exclude, parent))
    .map(function(item) {
      var fullPath = item.fullPath;

      if (item.isDirectory) {
        return _emptyDir(fullPath, options, join(parent, item.path)).then(function(removed) {
          return readdirAsync(fullPath).then(function(files) {
            if (!files.length) return rmdirAsync(fullPath);
          }).thenReturn(removed);
        });
      }

      return unlinkAsync(fullPath).thenReturn(join(parent, item.path));
    }).reduce(reduceFiles, []);
}

function emptyDir(path, options, callback) {
  if (!path) throw new TypeError('path is required!');

  if (!callback && typeof options === 'function') {
    callback = options;
    options = {};
  }

  return _emptyDir(path, options).asCallback(callback);
}

function emptyDirSync(path, options, parent) {
  if (!path) throw new TypeError('path is required!');

  options = options || {};
  parent = parent || '';

  return _readAndFilterDirSync(path, options)
    .filter(ignoreExcludeFiles(options.exclude, parent))
    .map(function(item) {
      var childPath = item.fullPath;

      if (item.isDirectory) {
        var removed = emptyDirSync(childPath, options, join(parent, item.path));

        if (!fs.readdirSync(childPath).length) {
          rmdirSync(childPath);
        }

        return removed;
      }

      fs.unlinkSync(childPath);
      return join(parent, item.path);
    }).reduce(reduceFiles, []);
}

function rmdir(path, callback) {
  if (!path) throw new TypeError('path is required!');

  return readdirAsync(path).map(function(item) {
    var childPath = join(path, item);

    return statAsync(childPath).then(function(stats) {
      if (stats.isDirectory()) {
        return rmdir(childPath);
      }

      return unlinkAsync(childPath);
    });
  }).then(function() {
    return rmdirAsync(path);
  }).asCallback(callback);
}

function rmdirSync(path) {
  if (!path) throw new TypeError('path is required!');

  var files = fs.readdirSync(path);
  var childPath, stats;

  for (var i = 0, len = files.length; i < len; i++) {
    childPath = join(path, files[i]);
    stats = fs.statSync(childPath);

    if (stats.isDirectory()) {
      rmdirSync(childPath);
    } else {
      fs.unlinkSync(childPath);
    }
  }

  fs.rmdirSync(path);
}

function watch(path, options, callback) {
  if (!path) throw new TypeError('path is required!');

  if (!callback && typeof options === 'function') {
    callback = options;
    options = {};
  }

  return new Promise(function(resolve, reject) {
    var watcher = chokidar.watch(path, options);

    watcher.on('ready', function() {
      resolve(watcher);
    });

    watcher.on('error', reject);
  }).asCallback(callback);
}

function _findUnusedPath(path, files) {
  var extname = pathFn.extname(path);
  var basename = pathFn.basename(path, extname);
  var regex = new RegExp('^' + escapeRegExp(basename) + '(?:-(\\d+))?' + escapeRegExp(extname) + '$');
  var item = '';
  var num = -1;
  var match, matchNum;

  for (var i = 0, len = files.length; i < len; i++) {
    item = files[i];
    if (!regex.test(item)) continue;

    match = item.match(regex);
    matchNum = match[1] ? parseInt(match[1], 10) : 0;

    if (matchNum > num) {
      num = matchNum;
    }
  }

  return join(dirname(path), basename + '-' + (num + 1) + extname);
}

function ensurePath(path, callback) {
  if (!path) throw new TypeError('path is required!');

  return exists(path).then(function(exist) {
    if (!exist) return path;

    return readdirAsync(dirname(path)).then(function(files) {
      return _findUnusedPath(path, files);
    });
  }).asCallback(callback);
}

function ensurePathSync(path) {
  if (!path) throw new TypeError('path is required!');
  if (!fs.existsSync(path)) return path;

  var files = fs.readdirSync(dirname(path));

  return _findUnusedPath(path, files);
}

function ensureWriteStream(path, options, callback) {
  if (!path) throw new TypeError('path is required!');

  if (!callback && typeof options === 'function') {
    callback = options;
    options = {};
  }

  return checkParent(path).then(function() {
    return fs.createWriteStream(path, options);
  }).asCallback(callback);
}

function ensureWriteStreamSync(path, options) {
  if (!path) throw new TypeError('path is required!');

  checkParentSync(path);
  return fs.createWriteStream(path, options);
}

// access
if (fs.access) {
  ['F_OK', 'R_OK', 'W_OK', 'X_OK'].forEach(function(key) {
    Object.defineProperty(exports, key, {
      enumerable: true,
      value: (fs.constants || fs)[key],
      writable: false
    });
  });

  exports.access = accessAsync;
  exports.accessSync = accessSync;
}

// appendFile
exports.appendFile = appendFile;
exports.appendFileSync = appendFileSync;

// chmod
exports.chmod = Promise.promisify(fs.chmod);
exports.chmodSync = fs.chmodSync;
exports.fchmod = Promise.promisify(fs.fchmod);
exports.fchmodSync = fs.fchmodSync;
exports.lchmod = Promise.promisify(fs.lchmod);
exports.lchmodSync = fs.lchmodSync;

// chown
exports.chown = Promise.promisify(fs.chown);
exports.chownSync = fs.chownSync;
exports.fchown = Promise.promisify(fs.fchown);
exports.fchownSync = fs.fchownSync;
exports.lchown = Promise.promisify(fs.lchown);
exports.lchownSync = fs.lchownSync;

// close
exports.close = Promise.promisify(fs.close);
exports.closeSync = fs.closeSync;

// copy
exports.copyDir = copyDir;
exports.copyFile = copyFile;

// createStream
exports.createReadStream = createReadStream;
exports.createWriteStream = createWriteStream;

// emptyDir
exports.emptyDir = emptyDir;
exports.emptyDirSync = emptyDirSync;

// ensurePath
exports.ensurePath = ensurePath;
exports.ensurePathSync = ensurePathSync;

// ensureWriteStream
exports.ensureWriteStream = ensureWriteStream;
exports.ensureWriteStreamSync = ensureWriteStreamSync;

// exists
exports.exists = exists;
exports.existsSync = existsSync;

// fsync
exports.fsync = Promise.promisify(fs.fsync);
exports.fsyncSync = fs.fsyncSync;

// link
exports.link = Promise.promisify(fs.link);
exports.linkSync = fs.linkSync;

// listDir
exports.listDir = listDir;
exports.listDirSync = listDirSync;

// mkdir
exports.mkdir = mkdirAsync;
exports.mkdirSync = fs.mkdirSync;

// mkdirs
exports.mkdirs = mkdirs;
exports.mkdirsSync = mkdirsSync;

// open
exports.open = Promise.promisify(fs.open);
exports.openSync = fs.openSync;

// symlink
exports.symlink = Promise.promisify(fs.symlink);
exports.symlinkSync = fs.symlinkSync;

// read
exports.read = Promise.promisify(fs.read);
exports.readSync = fs.readSync;

// readdir
exports.readdir = readdirAsync;
exports.readdirSync = fs.readdirSync;

// readFile
exports.readFile = readFile;
exports.readFileSync = readFileSync;

// readlink
exports.readlink = Promise.promisify(fs.readlink);
exports.readlinkSync = fs.readlinkSync;

// realpath
exports.realpath = Promise.promisify(fs.realpath);
exports.realpathSync = fs.realpathSync;

// rename
exports.rename = Promise.promisify(fs.rename);
exports.renameSync = fs.renameSync;

// rmdir
exports.rmdir = rmdir;
exports.rmdirSync = rmdirSync;

// stat
exports.stat = statAsync;
exports.statSync = fs.statSync;
exports.fstat = Promise.promisify(fs.fstat);
exports.fstatSync = fs.fstatSync;
exports.lstat = Promise.promisify(fs.lstat);
exports.lstatSync = fs.lstatSync;

// truncate
exports.truncate = Promise.promisify(fs.truncate);
exports.truncateSync = fs.truncateSync;
exports.ftruncate = Promise.promisify(fs.ftruncate);
exports.ftruncateSync = fs.ftruncateSync;

// unlink
exports.unlink = unlinkAsync;
exports.unlinkSync = fs.unlinkSync;

// utimes
exports.utimes = Promise.promisify(fs.utimes);
exports.utimesSync = fs.utimesSync;
exports.futimes = Promise.promisify(fs.futimes);
exports.futimesSync = fs.futimesSync;

// watch
exports.watch = watch;
exports.watchFile = fs.watchFile;
exports.unwatchFile = fs.unwatchFile;

// write
exports.write = Promise.promisify(fs.write);
exports.writeSync = fs.writeSync;

// writeFile
exports.writeFile = writeFile;
exports.writeFileSync = writeFileSync;

// Static classes
exports.Stats = fs.Stats;
exports.ReadStream = fs.ReadStream;
exports.WriteStream = fs.WriteStream;
exports.FileReadStream = fs.FileReadStream;
exports.FileWriteStream = fs.FileWriteStream;

// util
exports.escapeBOM = escapeBOM;
exports.escapeEOL = escapeEOL;
