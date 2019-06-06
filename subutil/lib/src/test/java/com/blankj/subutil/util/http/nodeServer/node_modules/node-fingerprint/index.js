'use strict'

let os = require('os')

let pad = (str, size) => ('000000000' + str).slice(-size)

let padding = 2
let pid = pad(process.pid.toString(36), padding)
let hostname = os.hostname()

hostname = hostname.split('')
  .reduce((prev, char) => +prev + char.charCodeAt(0), +hostname.length + 36)
  .toString(36)

let hostId = pad(hostname, padding)

export default () => pid + hostId
