'use strict'

let pad = (str, size) => ('000000000' + str).slice(-size)

let count = (() => {
  let count = 0
  let window = window || global

  for (let i in window) {
    count++
  }

  return count;
}())

let globalCount = () => count

export default () => pad(
  (navigator.mimeTypes.length + navigator.userAgent.length).toString(36) +
    globalCount().toString(36),
  4
)
