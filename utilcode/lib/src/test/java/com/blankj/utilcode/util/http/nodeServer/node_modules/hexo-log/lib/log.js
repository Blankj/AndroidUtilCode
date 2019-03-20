'use strict';

var bunyan = require('hexo-bunyan');
var chalk = require('chalk');
var Writable = require('stream').Writable;

var levelNames = {
  10: 'TRACE',
  20: 'DEBUG',
  30: 'INFO ',
  40: 'WARN ',
  50: 'ERROR',
  60: 'FATAL'
};

var levelColors = {
  10: 'gray',
  20: 'gray',
  30: 'green',
  40: 'bgYellow',
  50: 'bgRed',
  60: 'bgRed'
};

function ConsoleStream(env) {
  Writable.call(this, {objectMode: true});

  this.debug = env.debug;
}

require('util').inherits(ConsoleStream, Writable);

ConsoleStream.prototype._write = function(data, enc, callback) {
  var level = data.level;
  var msg = '';

  // Time
  if (this.debug) {
    msg += chalk.gray(formatTime(data.time)) + ' ';
  }

  // Level
  msg += chalk[levelColors[level]](levelNames[level]) + ' ';

  // Message
  msg += data.msg + '\n';

  // Error
  if (data.err) {
    var err = data.err.stack || data.err.message;
    if (err) msg += chalk.yellow(err) + '\n';
  }

  if (level >= 40) {
    process.stderr.write(msg);
  } else {
    process.stdout.write(msg);
  }

  callback();
};

function formatTime(date) {
  return date.toISOString().substring(11, 23);
}

function createLogger(options) {
  options = options || {};

  var streams = [];

  if (!options.silent) {
    streams.push({
      type: 'raw',
      level: options.debug ? 'trace' : 'info',
      stream: new ConsoleStream(options)
    });
  }

  if (options.debug) {
    streams.push({
      level: 'trace',
      path: 'debug.log'
    });
  }

  var logger = bunyan.createLogger({
    name: options.name || 'hexo',
    streams: streams,
    serializers: {
      err: bunyan.stdSerializers.err
    }
  });

  // Alias for logger levels
  logger.d = logger.debug;
  logger.i = logger.info;
  logger.w = logger.warn;
  logger.e = logger.error;
  logger.log = logger.info;

  return logger;
}

module.exports = createLogger;
