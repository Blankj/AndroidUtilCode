# browser-fingerprint

> Generate a fingerprint for a browser

Original concept taken from [cuid](https://github.com/ericelliott/cuid).

Fingerprint is based on a hash of mimeTypes count + userAgent string length + global properties count.

## Install

```
npm install browser-fingerprint
```

## Use

```
var fingerprint = require('browser-fingerprint')()
```

---

MIT © Kevin Lanni
