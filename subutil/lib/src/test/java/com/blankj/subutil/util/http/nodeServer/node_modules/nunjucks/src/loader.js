'use strict';

function _inheritsLoose(subClass, superClass) { subClass.prototype = Object.create(superClass.prototype); subClass.prototype.constructor = subClass; subClass.__proto__ = superClass; }

var path = require('path');

var Obj = require('./object');

module.exports =
/*#__PURE__*/
function (_Obj) {
  _inheritsLoose(Loader, _Obj);

  function Loader() {
    return _Obj.apply(this, arguments) || this;
  }

  var _proto = Loader.prototype;

  _proto.on = function on(name, func) {
    this.listeners = this.listeners || {};
    this.listeners[name] = this.listeners[name] || [];
    this.listeners[name].push(func);
  };

  _proto.emit = function emit(name) {
    for (var _len = arguments.length, args = new Array(_len > 1 ? _len - 1 : 0), _key = 1; _key < _len; _key++) {
      args[_key - 1] = arguments[_key];
    }

    if (this.listeners && this.listeners[name]) {
      this.listeners[name].forEach(function (listener) {
        listener.apply(void 0, args);
      });
    }
  };

  _proto.resolve = function resolve(from, to) {
    return path.resolve(path.dirname(from), to);
  };

  _proto.isRelative = function isRelative(filename) {
    return filename.indexOf('./') === 0 || filename.indexOf('../') === 0;
  };

  return Loader;
}(Obj);