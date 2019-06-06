/**
 * cuid.js
 * Collision-resistant UID generator for browsers and node.
 * Sequential for fast db lookups and recency sorting.
 * Safe for element IDs and server-side lookups.
 *
 * Extracted from CLCTR
 *
 * Copyright (c) Eric Elliott 2012
 * MIT License
 */

let c = 0;
const blockSize = 4;
const base = 36;
const discreteValues = Math.pow(base, blockSize);

const pad = (str, size) => ('000000000' + str).slice(-size);

const randomBlock = function randomBlock () {
  return pad((Math.random() * discreteValues << 0).toString(base), blockSize);
};

const safeCounter = function () {
  c = c < discreteValues ? c : 0;
  return c++;
};

const createCuid = (fingerprint) => {
  const cuid = () => {
    // Starting with a lowercase letter makes
    // it HTML element ID friendly.
    const letter = 'c'; // hard-coded allows for sequential access

    // timestamp
    // warning: this exposes the exact date and time
    // that the uid was created.
    const timestamp = (new Date().getTime()).toString(base);

    // Grab some more chars from Math.random()
    const random = randomBlock() + randomBlock();

    // Prevent same-machine collisions.
    const counter = pad(safeCounter().toString(base), blockSize);

    return letter + timestamp + counter + fingerprint + random;
  };

  const slug = () => {
    const date = new Date().getTime().toString(36);
    const print = fingerprint.slice(0, 1) + fingerprint.slice(-1);
    const random = randomBlock().slice(-2);
    const counter = safeCounter().toString(36).slice(-4);

    return date.slice(-2) + counter + print + random;
  };

  return { cuid, slug };
};

export default createCuid;
