'use strict';

var Promise = require('bluebird');
var pathFn = require('path');
var chalk = require('chalk');
var fs = require('hexo-fs');
var tildify = require('tildify');
var spawn = require('hexo-util/lib/spawn');
var assign = require('object-assign');
var commandExistsSync = require('command-exists').sync;

var ASSET_DIR = pathFn.join(__dirname, '../../assets');
var GIT_REPO_URL = 'https://github.com/hexojs/hexo-starter.git';

function initConsole(args) {
  args = assign({
    install: true,
    clone: true
  }, args);

  var baseDir = this.base_dir;
  var target = args._[0] ? pathFn.resolve(baseDir, args._[0]) : baseDir;
  var log = this.log;
  var promise;
  var npmCommand;

  if (fs.existsSync(target) && fs.readdirSync(target).length !== 0) {
    log.fatal(chalk.magenta(tildify(target)) +
      ' not empty, please run `hexo init` on an empty folder and then copy your files into it');
    return Promise.reject(new Error('target not empty'));
  }

  log.info('Cloning hexo-starter to', chalk.magenta(tildify(target)));

  if (args.clone) {
    promise = spawn('git', ['clone', '--recursive', GIT_REPO_URL, target], {
      stdio: 'inherit'
    });
  } else {
    promise = copyAsset(target);
  }

  return promise.catch(function() {
    log.warn('git clone failed. Copying data instead');

    return copyAsset(target);
  }).then(function() {
    return Promise.all([
      removeGitDir(target),
      removeGitModules(target)
    ]);
  }).then(function() {
    if (!args.install) return;

    log.info('Install dependencies');

    if (commandExistsSync('yarn')) {
      npmCommand = 'yarn';
    } else {
      npmCommand = 'npm';
    }

    return spawn(npmCommand, ['install', '--production'], {
      cwd: target,
      stdio: 'inherit'
    });
  }).then(function() {
    log.info('Start blogging with Hexo!');
  }).catch(function() {
    log.warn('Failed to install dependencies. Please run \'npm install\' manually!');
  });
}

function copyAsset(target) {
  return fs.copyDir(ASSET_DIR, target, {
    ignoreHidden: false
  });
}

function removeGitDir(target) {
  var gitDir = pathFn.join(target, '.git');

  return fs.stat(gitDir).catch(function(err) {
    if (err.cause && err.cause.code === 'ENOENT') return;
    throw err;
  }).then(function(stats) {
    if (stats) {
      if (stats.isDirectory()) return fs.rmdir(gitDir);
      return fs.unlink(gitDir);
    }
  }).then(function() {
    return fs.readdir(target);
  }).map(function(path) {
    return pathFn.join(target, path);
  }).filter(function(path) {
    return fs.stat(path).then(function(stats) {
      return stats.isDirectory();
    });
  }).each(removeGitDir);
}

function removeGitModules(target) {
  return fs.unlink(pathFn.join(target, '.gitmodules')).catch(function(err) {
    if (err.cause && err.cause.code === 'ENOENT') return;
    throw err;
  });
}

module.exports = initConsole;
