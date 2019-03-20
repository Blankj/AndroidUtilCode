var utils = require('./utils')

var _months = {
  full: [
    'January',
    'February',
    'March',
    'April',
    'May',
    'June',
    'July',
    'August',
    'September',
    'October',
    'November',
    'December'
  ],
  abbr: [
    'Jan',
    'Feb',
    'Mar',
    'Apr',
    'May',
    'Jun',
    'Jul',
    'Aug',
    'Sep',
    'Oct',
    'Nov',
    'Dec'
  ]
}
var _days = {
  full: [
    'Sunday',
    'Monday',
    'Tuesday',
    'Wednesday',
    'Thursday',
    'Friday',
    'Saturday'
  ],
  abbr: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
  alt: { '-1': 'Yesterday', 0: 'Today', 1: 'Tomorrow' }
}

/*
DateZ is licensed under the MIT License:
Copyright (c) 2011 Tomo Universalis (http://tomouniversalis.com)
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
exports.tzOffset = 0
exports.DateZ = function () {
  var members = {
    default: [
      'getUTCDate',
      'getUTCDay',
      'getUTCFullYear',
      'getUTCHours',
      'getUTCMilliseconds',
      'getUTCMinutes',
      'getUTCMonth',
      'getUTCSeconds',
      'toISOString',
      'toGMTString',
      'toUTCString',
      'valueOf',
      'getTime'
    ],
    z: [
      'getDate',
      'getDay',
      'getFullYear',
      'getHours',
      'getMilliseconds',
      'getMinutes',
      'getMonth',
      'getSeconds',
      'getYear',
      'toDateString',
      'toLocaleDateString',
      'toLocaleTimeString'
    ]
  }
  var d = this

  d.date = d.dateZ =
    arguments.length > 1
      ? new Date(
        Date.UTC.apply(Date, arguments) +
            new Date().getTimezoneOffset() * 60000
      )
      : arguments.length === 1 ? new Date(new Date(arguments['0'])) : new Date()

  d.timezoneOffset = d.dateZ.getTimezoneOffset()

  utils.each(members.z, function (name) {
    d[name] = function () {
      return d.dateZ[name]()
    }
  })
  utils.each(members['default'], function (name) {
    d[name] = function () {
      return d.date[name]()
    }
  })

  this.setTimezoneOffset(exports.tzOffset)
}
exports.DateZ.prototype = {
  getTimezoneOffset: function () {
    return this.timezoneOffset
  },
  setTimezoneOffset: function (offset) {
    this.timezoneOffset = offset
    this.dateZ = new Date(
      this.date.getTime() +
        this.date.getTimezoneOffset() * 60000 -
        this.timezoneOffset * 60000
    )
    return this
  }
}

// Day
exports.d = function (input) {
  return (input.getDate() < 10 ? '0' : '') + input.getDate()
}
exports.D = function (input) {
  return _days.abbr[input.getDay()]
}
exports.j = function (input) {
  return input.getDate()
}
exports.l = function (input) {
  return _days.full[input.getDay()]
}
exports.N = function (input) {
  var d = input.getDay()
  return d >= 1 ? d : 7
}
exports.S = function (input) {
  var d = input.getDate()
  return d % 10 === 1 && d !== 11
    ? 'st'
    : d % 10 === 2 && d !== 12 ? 'nd' : d % 10 === 3 && d !== 13 ? 'rd' : 'th'
}
exports.w = function (input) {
  return input.getDay()
}
exports.z = function (input, offset, abbr) {
  var year = input.getFullYear()
  var e = new exports.DateZ(year, input.getMonth(), input.getDate(), 12, 0, 0)
  var d = new exports.DateZ(year, 0, 1, 12, 0, 0)

  e.setTimezoneOffset(offset, abbr)
  d.setTimezoneOffset(offset, abbr)
  return Math.round((e - d) / 86400000)
}

// Week
exports.W = function (input) {
  var target = new Date(input.valueOf())
  var dayNr = (input.getDay() + 6) % 7
  var fThurs

  target.setDate(target.getDate() - dayNr + 3)
  fThurs = target.valueOf()
  target.setMonth(0, 1)
  if (target.getDay() !== 4) {
    target.setMonth(0, 1 + (4 - target.getDay() + 7) % 7)
  }

  return 1 + Math.ceil((fThurs - target) / 604800000)
}

// Month
exports.F = function (input) {
  return _months.full[input.getMonth()]
}
exports.m = function (input) {
  return (input.getMonth() < 9 ? '0' : '') + (input.getMonth() + 1)
}
exports.M = function (input) {
  return _months.abbr[input.getMonth()]
}
exports.n = function (input) {
  return input.getMonth() + 1
}
exports.t = function (input) {
  return 32 - new Date(input.getFullYear(), input.getMonth(), 32).getDate()
}

// Year
exports.L = function (input) {
  return new Date(input.getFullYear(), 1, 29).getDate() === 29
}
exports.o = function (input) {
  var target = new Date(input.valueOf())
  target.setDate(target.getDate() - (input.getDay() + 6) % 7 + 3)
  return target.getFullYear()
}
exports.Y = function (input) {
  return input.getFullYear()
}
exports.y = function (input) {
  return input
    .getFullYear()
    .toString()
    .substr(2)
}

// Time
exports.a = function (input) {
  return input.getHours() < 12 ? 'am' : 'pm'
}
exports.A = function (input) {
  return input.getHours() < 12 ? 'AM' : 'PM'
}
exports.B = function (input) {
  var hours = input.getUTCHours()
  var beats
  hours = hours === 23 ? 0 : hours + 1
  beats = Math.abs(
    ((hours * 60 + input.getUTCMinutes()) * 60 + input.getUTCSeconds()) / 86.4
  ).toFixed(0)
  return '000'.concat(beats).slice(beats.length)
}
exports.g = function (input) {
  var h = input.getHours()
  return h === 0 ? 12 : h > 12 ? h - 12 : h
}
exports.G = function (input) {
  return input.getHours()
}
exports.h = function (input) {
  var h = input.getHours()
  return (h < 10 || (h > 12 && h < 22) ? '0' : '') + (h < 12 ? h : h - 12)
}
exports.H = function (input) {
  var h = input.getHours()
  return (h < 10 ? '0' : '') + h
}
exports.i = function (input) {
  var m = input.getMinutes()
  return (m < 10 ? '0' : '') + m
}
exports.s = function (input) {
  var s = input.getSeconds()
  return (s < 10 ? '0' : '') + s
}
// u = function () { return ''; },

// Timezone
// e = function () { return ''; },
// I = function () { return ''; },
exports.O = function (input) {
  var tz = input.getTimezoneOffset()
  return (
    (tz < 0 ? '-' : '+') + (tz / 60 < 10 ? '0' : '') + Math.abs(tz / 60) + '00'
  )
}
// T = function () { return ''; },
exports.Z = function (input) {
  return input.getTimezoneOffset() * 60
}

// Full Date/Time
exports.c = function (input) {
  return input.toISOString()
}
exports.r = function (input) {
  return input.toUTCString()
}
exports.U = function (input) {
  return input.getTime() / 1000
}
