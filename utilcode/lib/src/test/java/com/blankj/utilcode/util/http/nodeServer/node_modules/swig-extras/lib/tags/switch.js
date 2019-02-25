exports.parse = function (str, line, parser, types, stack, options) {
  parser.on('*', function (token) {
    if (this.out.length) {
      throw new Error('Switch statements may only take one argument.');
    }

    return true;
  });

  return true;
};

exports.compile = function (compiler, args, content, parents, options, blockName) {
  return 'switch (' + args[0] + ') {\n' +
    'default:\n' +
    compiler(content, parents, options, blockName) + '\n' +
    'break;\n' +
    '}\n';
};

exports.ends = true;
