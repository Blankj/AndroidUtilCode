// TODO:
//  * support 1 nested multipart level
//    (see second multipart example here:
//     http://www.w3.org/TR/html401/interact/forms.html#didx-multipartform-data)
//  * support limits.fieldNameSize
//     -- this will require modifications to utils.parseParams

var ReadableStream = require('stream').Readable || require('readable-stream'),
    inherits = require('util').inherits;

var Dicer = require('dicer');

var parseParams = require('../utils').parseParams,
    decodeText = require('../utils').decodeText,
    basename = require('../utils').basename;

var RE_BOUNDARY = /^boundary$/i,
    RE_FIELD = /^form-data$/i,
    RE_CHARSET = /^charset$/i,
    RE_FILENAME = /^filename$/i,
    RE_NAME = /^name$/i;

Multipart.detect = /^multipart\/form-data/i;
function Multipart(boy, cfg) {
  if (!(this instanceof Multipart))
    return new Multipart(boy, cfg);
  var i,
      len,
      self = this,
      boundary,
      limits = cfg.limits,
      parsedConType = cfg.parsedConType || [],
      defCharset = cfg.defCharset || 'utf8',
      preservePath = cfg.preservePath,
      fileopts = (typeof cfg.fileHwm === 'number'
                  ? { highWaterMark: cfg.fileHwm }
                  : {});

  for (i = 0, len = parsedConType.length; i < len; ++i) {
    if (Array.isArray(parsedConType[i])
        && RE_BOUNDARY.test(parsedConType[i][0])) {
      boundary = parsedConType[i][1];
      break;
    }
  }

  function checkFinished() {
    if (nends === 0 && finished && !boy._done) {
      finished = false;
      process.nextTick(function() {
        boy._done = true;
        boy.emit('finish');
      });
    }
  }

  if (typeof boundary !== 'string')
    throw new Error('Multipart: Boundary not found');

  var fieldSizeLimit = (limits && typeof limits.fieldSize === 'number'
                        ? limits.fieldSize
                        : 1 * 1024 * 1024),
      fileSizeLimit = (limits && typeof limits.fileSize === 'number'
                       ? limits.fileSize
                       : Infinity),
      filesLimit = (limits && typeof limits.files === 'number'
                    ? limits.files
                    : Infinity),
      fieldsLimit = (limits && typeof limits.fields === 'number'
                     ? limits.fields
                     : Infinity),
      partsLimit = (limits && typeof limits.parts === 'number'
                    ? limits.parts
                    : Infinity);

  var nfiles = 0,
      nfields = 0,
      nends = 0,
      curFile,
      curField,
      finished = false;

  this._needDrain = false;
  this._pause = false;
  this._cb = undefined;
  this._nparts = 0;
  this._boy = boy;

  var parserCfg = {
    boundary: boundary,
    maxHeaderPairs: (limits && limits.headerPairs)
  };
  if (fileopts.highWaterMark)
    parserCfg.partHwm = fileopts.highWaterMark;
  if (cfg.highWaterMark)
    parserCfg.highWaterMark = cfg.highWaterMark;

  this.parser = new Dicer(parserCfg);
  this.parser.on('drain', function() {
    self._needDrain = false;
    if (self._cb && !self._pause) {
      var cb = self._cb;
      self._cb = undefined;
      cb();
    }
  }).on('part', function onPart(part) {
    if (++self._nparts > partsLimit) {
      self.parser.removeListener('part', onPart);
      self.parser.on('part', skipPart);
      boy.hitPartsLimit = true;
      boy.emit('partsLimit');
      return skipPart(part);
    }

    // hack because streams2 _always_ doesn't emit 'end' until nextTick, so let
    // us emit 'end' early since we know the part has ended if we are already
    // seeing the next part
    if (curField) {
      var field = curField;
      field.emit('end');
      field.removeAllListeners('end');
    }

    part.on('header', function(header) {
      var contype,
          fieldname,
          parsed,
          charset,
          encoding,
          filename,
          nsize = 0;

      if (header['content-type']) {
        parsed = parseParams(header['content-type'][0]);
        if (parsed[0]) {
          contype = parsed[0].toLowerCase();
          for (i = 0, len = parsed.length; i < len; ++i) {
            if (RE_CHARSET.test(parsed[i][0])) {
              charset = parsed[i][1].toLowerCase();
              break;
            }
          }
        }
      }

      if (contype === undefined)
        contype = 'text/plain';
      if (charset === undefined)
        charset = defCharset;

      if (header['content-disposition']) {
        parsed = parseParams(header['content-disposition'][0]);
        if (!RE_FIELD.test(parsed[0]))
          return skipPart(part);
        for (i = 0, len = parsed.length; i < len; ++i) {
          if (RE_NAME.test(parsed[i][0])) {
            fieldname = decodeText(parsed[i][1], 'binary', 'utf8');
          } else if (RE_FILENAME.test(parsed[i][0])) {
            filename = decodeText(parsed[i][1], 'binary', 'utf8');
            if (!preservePath)
              filename = basename(filename);
          }
        }
      } else
        return skipPart(part);

      if (header['content-transfer-encoding'])
        encoding = header['content-transfer-encoding'][0].toLowerCase();
      else
        encoding = '7bit';

      var onData,
          onEnd;
      if (contype === 'application/octet-stream' || filename !== undefined) {
        // file/binary field
        if (nfiles === filesLimit) {
          if (!boy.hitFilesLimit) {
            boy.hitFilesLimit = true;
            boy.emit('filesLimit');
          }
          return skipPart(part);
        }

        ++nfiles;

        if (!boy._events.file) {
          self.parser._ignore();
          return;
        }

        ++nends;
        var file = new FileStream(fileopts);
        curFile = file;
        file.on('end', function() {
          --nends;
          checkFinished();
          if (self._cb && !self._needDrain) {
            var cb = self._cb;
            self._cb = undefined;
            cb();
          }
        });
        file._read = function(n) {
          if (!self._pause)
            return;
          self._pause = false;
          if (self._cb && !self._needDrain) {
            var cb = self._cb;
            self._cb = undefined;
            cb();
          }
        };
        boy.emit('file', fieldname, file, filename, encoding, contype);

        onData = function(data) {
          if ((nsize += data.length) > fileSizeLimit) {
            var extralen = (fileSizeLimit - (nsize - data.length));
            if (extralen > 0)
              file.push(data.slice(0, extralen));
            file.emit('limit');
            file.truncated = true;
            part.removeAllListeners('data');
          } else if (!file.push(data))
            self._pause = true;
        };

        onEnd = function() {
          curFile = undefined;
          file.push(null);
        };
      } else {
        // non-file field
        if (nfields === fieldsLimit) {
          if (!boy.hitFieldsLimit) {
            boy.hitFieldsLimit = true;
            boy.emit('fieldsLimit');
          }
          return skipPart(part);
        }

        ++nfields;
        ++nends;
        var buffer = '',
            truncated = false;
        curField = part;

        onData = function(data) {
          if ((nsize += data.length) > fieldSizeLimit) {
            var extralen = (fieldSizeLimit - (nsize - data.length));
            buffer += data.toString('binary', 0, extralen);
            truncated = true;
            part.removeAllListeners('data');
          } else
            buffer += data.toString('binary');
        };

        onEnd = function() {
          curField = undefined;
          if (buffer.length)
            buffer = decodeText(buffer, 'binary', charset);
          boy.emit('field', fieldname, buffer, false, truncated, encoding, contype);
          --nends;
          checkFinished();
        };
      }

      /* As of node@2efe4ab761666 (v0.10.29+/v0.11.14+), busboy had become
         broken. Streams2/streams3 is a huge black box of confusion, but
         somehow overriding the sync state seems to fix things again (and still
         seems to work for previous node versions).
      */
      part._readableState.sync = false;

      part.on('data', onData);
      part.on('end', onEnd);
    }).on('error', function(err) {
      if (curFile)
        curFile.emit('error', err);
    });
  }).on('error', function(err) {
    boy.emit('error', err);
  }).on('finish', function() {
    finished = true;
    checkFinished();
  });
}

Multipart.prototype.write = function(chunk, cb) {
  var r;
  if ((r = this.parser.write(chunk)) && !this._pause)
    cb();
  else {
    this._needDrain = !r;
    this._cb = cb;
  }
};

Multipart.prototype.end = function() {
  var self = this;
  if (this._nparts === 0 && !self._boy._done) {
    process.nextTick(function() {
      self._boy._done = true;
      self._boy.emit('finish');
    });
  } else if (this.parser.writable)
    this.parser.end();
};

function skipPart(part) {
  part.resume();
}

function FileStream(opts) {
  if (!(this instanceof FileStream))
    return new FileStream(opts);
  ReadableStream.call(this, opts);

  this.truncated = false;
}
inherits(FileStream, ReadableStream);

FileStream.prototype._read = function(n) {};

module.exports = Multipart;
