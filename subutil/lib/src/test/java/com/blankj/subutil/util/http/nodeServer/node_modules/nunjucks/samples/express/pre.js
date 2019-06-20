#!/usr/bin/env node

'use strict';

var precompileString = require('../..').precompileString;
var fs = require('fs');
var path = require('path');


var out = 'window.baseTmpl = ' +
precompileString(
  fs.readFileSync(path.join(__dirname, 'views/base.html'), 'utf-8'), {
    name: 'base.html',
    asFunction: true
  });

out += 'window.aboutTmpl = ' +
precompileString(
  fs.readFileSync(path.join(__dirname, 'views/about.html'), 'utf-8'), {
    name: 'about.html',
    asFunction: true
  });

fs.writeFileSync(path.join(__dirname, 'js/templates.js'), out, 'utf-8');

fs.writeFileSync(path.join(__dirname, 'js/nunjucks.js'),
  fs.readFileSync(path.join(__dirname, '../../browser/nunjucks.js'), 'utf-8'),
  'utf-8');
