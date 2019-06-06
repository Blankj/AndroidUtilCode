import createCuid from '../index.js';
const fingerprint = require('browser-fingerprint')();

const { cuid, slug } = createCuid(fingerprint);
cuid.slug = slug;

export default cuid;
