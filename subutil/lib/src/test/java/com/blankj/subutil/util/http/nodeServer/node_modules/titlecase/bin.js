#!/usr/bin/env node

var toTitleCase = require('./to-title-case')

process.argv.slice(2).forEach(function (a) {
  console.log(toTitleCase(a))
})

