var target = process.env.WEBPACK_TARGET;

var config = {
  entry: {},
  output: target === 'client' ? {
    library: 'cuid',
    libraryTarget: 'umd',
    path: 'build/',
    filename: target + '-cuid.js'
  } : target === 'test' ? {
    library: 'testCuid',
    libraryTarget: 'umd',
    path: 'test/client/',
    filename: target + '-cuid.js'
  } : {
    path: 'build/',
    filename: target + '-cuid.js'
  },
  module: {
    loaders: [
      {
        test: /\.jsx?$/,
        exclude: /(node_modules|bower_components)/,
        loader: 'babel'
      }
    ]
  }
};

config.entry = target === 'test' ?
  './test/client/index.js' :
  './source/' + target + '/index.js';

module.exports = config;
