var assert = console.assert
var unique = require('array-unique')
var random = require('./')

var iterations = 10000
var number, cache = []

for (var i = 0; i < iterations; i++) {
  number = random()
  if (number < 0) {
    assert(false, 'Random numbers should be greater than or equal to zero')
    break
  }
  if (number >= 1) {
    assert(false, 'Random numbers should be less than one')
    break
  }
  cache.push(number)
}

assert(unique(cache).length === iterations, 'Random numbers should be unique')
