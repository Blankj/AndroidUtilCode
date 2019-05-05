/**
 * Parted (https://github.com/chjj/parted)
 * A streaming multipart state parser.
 * Copyright (c) 2011, Christopher Jeffrey. (MIT Licensed)
 */

var fs = require('fs')
  , path = require('path')
  , EventEmitter = require('events').EventEmitter
  , StringDecoder = require('string_decoder').StringDecoder
  , set = require('qs').set
  , each = Array.prototype.forEach;

/**
 * Character Constants
 */

var DASH = '-'.charCodeAt(0)
  , CR = '\r'.charCodeAt(0)
  , LF = '\n'.charCodeAt(0)
  , COLON = ':'.charCodeAt(0)
  , SPACE = ' '.charCodeAt(0);

/**
 * Parser
 */

var Parser = function(type, options) {
  if (!(this instanceof Parser)) {
    return new Parser(type, options);
  }

  EventEmitter.call(this);

  this.writable = true;
  this.readable = true;

  this.options = options || {};

  var key = grab(type, 'boundary');
  if (!key) {
    return this._error('No boundary key found.');
  }

  this.key = new Buffer('\r\n--' + key);

  this._key = {};
  each.call(this.key, function(ch) {
    this._key[ch] = true;
  }, this);

  this.state = 'start';
  this.pending = 0;
  this.written = 0;
  this.writtenDisk = 0;
  this.buff = new Buffer(200);

  this.preamble = true;
  this.epilogue = false;

  this._reset();
};

Parser.prototype.__proto__ = EventEmitter.prototype;

/**
 * Parsing
 */

Parser.prototype.write = function(data) {
  if (!this.writable
      || this.epilogue) return;

  try {
    this._parse(data);
  } catch (e) {
    this._error(e);
  }

  return true;
};

Parser.prototype.end = function(data) {
  if (!this.writable) return;

  if (data) this.write(data);

  if (!this.epilogue) {
    return this._error('Message underflow.');
  }

  return true;
};

Parser.prototype._parse = function(data) {
  var i = 0
    , len = data.length
    , buff = this.buff
    , key = this.key
    , ch
    , val
    , j;

  for (; i < len; i++) {
    if (this.pos >= 200) {
      return this._error('Potential buffer overflow.');
    }

    ch = data[i];

    switch (this.state) {
      case 'start':
        switch (ch) {
          case DASH:
            this.pos = 3;
            this.state = 'key';
            break;
          default:
            break;
        }
        break;
      case 'key':
        if (this.pos === key.length) {
          this.state = 'key_end';
          i--;
        } else if (ch !== key[this.pos]) {
          if (this.preamble) {
            this.state = 'start';
            i--;
          } else {
            this.state = 'body';
            val = this.pos - i;
            if (val > 0) {
              this._write(key.slice(0, val));
            }
            i--;
          }
        } else {
          this.pos++;
        }
        break;
      case 'key_end':
        switch (ch) {
          case CR:
            this.state = 'key_line_end';
            break;
          case DASH:
            this.state = 'key_dash_end';
            break;
          default:
            return this._error('Expected CR or DASH.');
        }
        break;
      case 'key_line_end':
        switch (ch) {
          case LF:
            if (this.preamble) {
              this.preamble = false;
            } else {
              this._finish();
            }
            this.state = 'header_name';
            this.pos = 0;
            break;
          default:
            return this._error('Expected CR.');
        }
        break;
      case 'key_dash_end':
        switch (ch) {
          case DASH:
            this.epilogue = true;
            this._finish();
            return;
          default:
            return this._error('Expected DASH.');
        }
        break;
      case 'header_name':
        switch (ch) {
          case COLON:
            this.header = buff.toString('ascii', 0, this.pos);
            this.pos = 0;
            this.state = 'header_val';
            break;
          default:
            buff[this.pos++] = ch | 32;
            break;
        }
        break;
      case 'header_val':
        switch (ch) {
          case CR:
            this.state = 'header_val_end';
            break;
          case SPACE:
            if (this.pos === 0) {
              break;
            }
            ; // FALL-THROUGH
          default:
            buff[this.pos++] = ch;
            break;
        }
        break;
      case 'header_val_end':
        switch (ch) {
          case LF:
            val = buff.toString('ascii', 0, this.pos);
            this._header(this.header, val);
            this.pos = 0;
            this.state = 'header_end';
            break;
          default:
            return this._error('Expected LF.');
        }
        break;
      case 'header_end':
        switch (ch) {
          case CR:
            this.state = 'head_end';
            break;
          default:
            this.state = 'header_name';
            i--;
            break;
        }
        break;
      case 'head_end':
        switch (ch) {
          case LF:
            this.state = 'body';
            i++;
            if (i >= len) return;
            data = data.slice(i);
            i = -1;
            len = data.length;
            break;
          default:
            return this._error('Expected LF.');
        }
        break;
      case 'body':
        switch (ch) {
          case CR:
            if (i > 0) {
              this._write(data.slice(0, i));
            }
            this.pos = 1;
            this.state = 'key';
            data = data.slice(i);
            i = 0;
            len = data.length;
            break;
          default:
            // boyer-moore-like algorithm
            // at felixge's suggestion
            while ((j = i + key.length - 1) < len) {
              if (this._key[data[j]]) break;
              i = j;
            }
            break;
        }
        break;
    }
  }

  if (this.state === 'body') {
    this._write(data);
  }
};

Parser.prototype._header = function(name, val) {
  /*if (name === 'content-disposition') {
    this.field = grab(val, 'name');
    this.file = grab(val, 'filename');

    if (this.file) {
      this.data = stream(this.file, this.options.path);
    } else {
      this.decode = new StringDecoder('utf8');
      this.data = '';
    }
  }*/

  return this.emit('header', name, val);
};

Parser.prototype._write = function(data) {
  /*if (this.data == null) {
    return this._error('No disposition.');
  }

  if (this.file) {
    this.data.write(data);
    this.writtenDisk += data.length;
  } else {
    this.data += this.decode.write(data);
    this.written += data.length;
  }*/

  this.emit('data', data);
};

Parser.prototype._reset = function() {
  this.pos = 0;
  this.decode = null;
  this.field = null;
  this.data = null;
  this.file = null;
  this.header = null;
};

Parser.prototype._error = function(err) {
  this.destroy();
  this.emit('error', typeof err === 'string'
    ? new Error(err)
    : err);
};

Parser.prototype.destroy = function(err) {
  this.writable = false;
  this.readable = false;
  this._reset();
};

Parser.prototype._finish = function() {
  var self = this
    , field = this.field
    , data = this.data
    , file = this.file
    , part;

  this.pending++;

  this._reset();

  if (data && data.path) {
    part = data.path;
    data.end(next);
  } else {
    part = data;
    next();
  }

  function next() {
    if (!self.readable) return;

    self.pending--;

    self.emit('part', field, part);

    if (data && data.path) {
      self.emit('file', field, part, file);
    }

    if (self.epilogue && !self.pending) {
      self.emit('end');
      self.destroy();
    }
  }
};

/**
 * Uploads
 */

Parser.root = process.platform === 'win32'
  ? 'C:/Temp'
  : '/tmp';

/**
 * Middleware
 */

Parser.middleware = function(options) {
  options = options || {};
  return function(req, res, next) {
    if (options.ensureBody) {
      req.body = {};
    }

    if (req.method === 'GET'
        || req.method === 'HEAD'
        || req._multipart) return next();

    req._multipart = true;

    var type = req.headers['content-type'];

    if (type) type = type.split(';')[0].trim().toLowerCase();

    if (type === 'multipart/form-data') {
      Parser.handle(req, res, next, options);
    } else {
      next();
    }
  };
};

/**
 * Handler
 */

Parser.handle = function(req, res, next, options) {
  var parser = new Parser(req.headers['content-type'], options)
    , diskLimit = options.diskLimit
    , limit = options.limit
    , parts = {}
    , files = {};

  parser.on('error', function(err) {
    req.destroy();
    next(err);
  });

  parser.on('part', function(field, part) {
    set(parts, field, part);
  });

  parser.on('file', function(field, path, name) {
    set(files, field, {
      path: path,
      name: name,
      toString: function() {
        return path;
      }
    });
  });

  parser.on('data', function() {
    if (this.writtenDisk > diskLimit || this.written > limit) {
      this.emit('error', new Error('Overflow.'));
      this.destroy();
    }
  });

  parser.on('end', next);

  req.body = parts;
  req.files = files;
  req.pipe(parser);
};

/**
 * Helpers
 */

var isWindows = process.platform === 'win32';

var stream = function(name, dir) {
  var ext = path.extname(name) || ''
    , name = path.basename(name, ext) || ''
    , dir = dir || Parser.root
    , tag;

  tag = Math.random().toString(36).substring(2);

  name = name.substring(0, 200) + '.' + tag;
  name = path.join(dir, name) + ext.substring(0, 6);
  name = name.replace(/\0/g, '');

  if (isWindows) {
    name = name.replace(/[:*<>|"?]/g, '');
  }

  return fs.createWriteStream(name);
};

var grab = function(str, name) {
  if (!str) return;

  var rx = new RegExp('\\b' + name + '\\s*=\\s*("[^"]+"|\'[^\']+\'|[^;,]+)', 'i')
    , cap = rx.exec(str);

  if (cap) {
    return cap[1].trim().replace(/^['"]|['"]$/g, '');
  }
};

/**
 * Expose
 */

module.exports = Parser;