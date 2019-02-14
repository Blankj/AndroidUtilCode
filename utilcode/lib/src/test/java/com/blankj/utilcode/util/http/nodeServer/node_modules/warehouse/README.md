# Warehouse

[![Build Status](https://travis-ci.org/tommy351/warehouse.svg?branch=master)](https://travis-ci.org/tommy351/warehouse)  [![NPM version](https://badge.fury.io/js/warehouse.svg)](http://badge.fury.io/js/warehouse) [![Coverage Status](https://coveralls.io/repos/tommy351/warehouse/badge.svg?branch=master)](https://coveralls.io/r/tommy351/warehouse?branch=master)

## Installation

``` bash
$ npm install warehouse
```

## Usage

``` js
var Database = require('warehouse');
var db = new Database();

var Post = db.model('posts', {
  title: String,
  created: {type: Date, default: Date.now}
});

Post.insert({
  title: 'Hello world'
}).then(function(post){
  console.log(post);
});
```

- [API](http://tommy351.github.io/warehouse/)

## Test

``` bash
$ npm test
```
