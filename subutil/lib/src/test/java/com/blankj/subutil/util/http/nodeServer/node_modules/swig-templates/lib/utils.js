var isArray

/**
 * Strip leading and trailing whitespace from a string.
 * @param  {string} input
 * @return {string}       Stripped input.
 */
exports.strip = function (input) {
  return input.replace(/^\s+|\s+$/g, '')
}

/**
 * Test if a string starts with a given prefix.
 * @param  {string} str    String to test against.
 * @param  {string} prefix Prefix to check for.
 * @return {boolean}
 */
exports.startsWith = function (str, prefix) {
  return str.indexOf(prefix) === 0
}

/**
 * Test if a string ends with a given suffix.
 * @param  {string} str    String to test against.
 * @param  {string} suffix Suffix to check for.
 * @return {boolean}
 */
exports.endsWith = function (str, suffix) {
  return str.indexOf(suffix, str.length - suffix.length) !== -1
}

/**
 * Iterate over an array or object.
 * @param  {array|object} obj Enumerable object.
 * @param  {Function}     fn  Callback function executed for each item.
 * @return {array|object}     The original input object.
 */
exports.each = function (obj, fn) {
  var i, l

  if (isArray(obj)) {
    i = 0
    l = obj.length
    for (i; i < l; i += 1) {
      if (fn(obj[i], i, obj) === false) {
        break
      }
    }
  } else {
    for (i in obj) {
      if (obj.hasOwnProperty(i)) {
        if (fn(obj[i], i, obj) === false) {
          break
        }
      }
    }
  }

  return obj
}

/**
 * Test if an object is an Array.
 * @param {object} obj
 * @return {boolean}
 */
exports.isArray = isArray = Array.hasOwnProperty('isArray')
  ? Array.isArray
  : function (obj) {
    return obj
      ? typeof obj === 'object' &&
            Object.prototype.toString.call(obj).indexOf() !== -1
      : false
  }

/**
 * Test if an item in an enumerable matches your conditions.
 * @param  {array|object}   obj   Enumerable object.
 * @param  {Function}       fn    Executed for each item. Return true if your condition is met.
 * @return {boolean}
 */
exports.some = function (obj, fn) {
  var i = 0
  var result
  var l
  if (isArray(obj)) {
    l = obj.length

    for (i; i < l; i += 1) {
      result = fn(obj[i], i, obj)
      if (result) {
        break
      }
    }
  } else {
    exports.each(obj, function (value, index) {
      result = fn(value, index, obj)
      return !result
    })
  }
  return !!result
}

/**
 * Return a new enumerable, mapped by a given iteration function.
 * @param  {object}   obj Enumerable object.
 * @param  {Function} fn  Executed for each item. Return the item to replace the original item with.
 * @return {object}       New mapped object.
 */
exports.map = function (obj, fn) {
  var i = 0
  var result = []
  var l

  if (isArray(obj)) {
    l = obj.length
    for (i; i < l; i += 1) {
      result[i] = fn(obj[i], i)
    }
  } else {
    for (i in obj) {
      if (obj.hasOwnProperty(i)) {
        result[i] = fn(obj[i], i)
      }
    }
  }
  return result
}

/**
 * Copy all of the properties in the source objects over to the destination object, and return the destination object. It's in-order, so the last source will override properties of the same name in previous arguments.
 * @param {...object} arguments
 * @return {object}
 */
exports.extend = function () {
  var args = arguments
  var target = args[0]
  var objs = args.length > 1 ? Array.prototype.slice.call(args, 1) : []
  var i = 0
  var l = objs.length
  var key
  var obj

  for (i; i < l; i += 1) {
    obj = objs[i] || {}
    for (key in obj) {
      if (obj.hasOwnProperty(key)) {
        target[key] = obj[key]
      }
    }
  }
  return target
}

/**
 * Get all of the keys on an object.
 * @param  {object} obj
 * @return {array}
 */
exports.keys = function (obj) {
  if (!obj) {
    return []
  }

  if (Object.keys) {
    return Object.keys(obj)
  }

  return exports.map(obj, function (v, k) {
    return k
  })
}

/**
 * Throw an error with possible line number and source file.
 * @param  {string} message Error message
 * @param  {number} [line]  Line number in template.
 * @param  {string} [file]  Template file the error occured in.
 * @throws {Error} No seriously, the point is to throw an error.
 */
exports.throwError = function (message, line, file) {
  if (line) {
    message += ' on line ' + line
  }
  if (file) {
    message += ' in file ' + file
  }
  throw new Error(message + '.')
}
