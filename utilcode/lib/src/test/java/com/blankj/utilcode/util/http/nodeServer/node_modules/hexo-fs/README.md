# hexo-fs

[![Build Status](https://travis-ci.org/hexojs/hexo-fs.svg?branch=master)](https://travis-ci.org/hexojs/hexo-fs)  [![NPM version](https://badge.fury.io/js/hexo-fs.svg)](http://badge.fury.io/js/hexo-fs) [![Coverage Status](https://img.shields.io/coveralls/hexojs/hexo-fs.svg)](https://coveralls.io/r/hexojs/hexo-fs?branch=master) [![Build status](https://ci.appveyor.com/api/projects/status/wift3c57kei3ylq4/branch/master?svg=true)](https://ci.appveyor.com/project/tommy351/hexo-fs/branch/master) [![dependencies Status](https://david-dm.org/hexojs/hexo-fs/status.svg)](https://david-dm.org/hexojs/hexo-fs) [![devDependencies Status](https://david-dm.org/hexojs/hexo-fs/dev-status.svg)](https://david-dm.org/hexojs/hexo-fs?type=dev)

File system module for [Hexo].

## Features

- Support for both Promise and callback interface.
- Use [graceful-fs] to avoid EMFILE error and various improvements.
- Use [chokidar] for consistent file watching.

## Installation

``` bash
$ npm install hexo-fs --save
```

## Usage

``` js
var fs = require('hexo-fs');
```

> Some methods in the original fs module are not listed below, but they're available in hexo-fs.

### exists(path, [callback])

Test whether or not the given `path` exists by checking with the file system.

### existsSync(path)

Synchronous version of `fs.exists`.

### mkdirs(path, [callback])

Creates a directory and its parent directories if they does not exist.

### mkdirsSync(path)

Synchronous version of `fs.mkdirs`.

### writeFile(path, data, [options], [callback])

Writes data to a file.

Option | Description | Default
--- | --- | ---
`encoding` | File encoding | utf8
`mode` | Mode | 438 (0666 in octal)
`flag` | Flag | w

### writeFileSync(path, data, [options])

Synchronous version of `fs.writeFile`.

### appendFile(path, data, [options], [callback])

Appends data to a file.

Option | Description | Default
--- | --- | ---
`encoding` | File encoding | utf8
`mode` | Mode | 438 (0666 in octal)
`flag` | Flag | w

### appendFileSync(path, data, [options])

Synchronous version of `fs.appendFile`.

### copyFile(src, dest, [callback])

Copies a file from `src` to `dest`.

### copyDir(src, dest, [options], [callback])

Copies a directory from `src` to `dest`. It returns an array of copied files.

Option | Description | Default
--- | --- | ---
`ignoreHidden` | Ignore hidden files | true
`ignorePattern` | Ignore files which pass the regular expression |

### listDir(path, [options], [callback])

Lists files in a directory.

Option | Description | Default
--- | --- | ---
`ignoreHidden` | Ignore hidden files | true
`ignorePattern` | Ignore files which pass the regular expression |

### listDirSync(path, [options])

Synchronous version of `fs.listDir`.

### readFile(path, [options], [callback])

Reads the entire contents of a file.

Option | Description | Default
--- | --- | ---
`encoding` | File encoding | utf8
`flag` | Flag | r
`escape` | Escape UTF BOM and line ending in the content | true

### readFileSync(path, [options])

Synchronous version of `fs.readFile`.

### emptyDir(path, [options], [callback])

Deletes all files in a directory. It returns an array of deleted files.

Option | Description | Default
--- | --- | ---
`ignoreHidden` | Ignore hidden files | true
`ignorePattern` | Ignore files which pass the regular expression |
`exclude` | Ignore files in the array |

### emptyDirSync(path, [options])

Synchronous version of `fs.emptyDir`.

### rmdir(path, [callback])

Removes a directory and all files in it.

### rmdirSync(path)

Synchronous version of `fs.rmdir`.

### watch(path, [options], [callback])

Watches changes of a file or a directory.

See [Chokidar API](https://github.com/paulmillr/chokidar#api) for more info.

### ensurePath(path, [callback])

Ensures the given path is available to use or appends a number to the path.

### ensurePathSync(path)

Synchronous version of `fs.ensurePath`.

### ensureWriteStream(path, [options], [callback])

Creates the parent directories if they does not exist and returns a writable stream.

### ensureWriteStream(path, [options])

Synchronous version of `fs.ensureWriteStream`.

## License

MIT

[graceful-fs]: https://github.com/isaacs/node-graceful-fs
[Hexo]: http://hexo.io/
[chokidar]: https://github.com/paulmillr/chokidar