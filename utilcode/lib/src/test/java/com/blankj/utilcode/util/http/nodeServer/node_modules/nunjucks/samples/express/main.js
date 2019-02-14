/* eslint-disable func-names */

'use strict';

var path = require('path');
var nunjucks = require('../..');
var express = require('express');

var app = express();
nunjucks.configure(path.join(__dirname, 'views'), {
  autoescape: true,
  express: app,
  watch: true
});

// app

app.use(express.static(__dirname));

app.use(function(req, res, next) {
  res.locals.user = 'hello';
  next();
});

app.get('/', function(req, res) {
  res.render('index.html', {
    username: 'James Long <strong>copyright</strong>'
  });
});

app.get('/about', function(req, res) {
  res.render('about.html');
});

app.listen(4000, function() {
  console.log('Express server running on http://localhost:4000');
});
