exports.parse = function (str, line, parser, types, stack) {
  parser.on('*', function (token) {
    if (this.out.length) {
      throw new Error('Switch statements may only take one argument.');
    }

    return true;
  });

  return (stack.length && stack[stack.length - 1].name === 'switch');
};


exports.compile = function (compiler, args, content, parents, options, blockName) {
  return 'break;\n' +
    'case (' + args[0] + '):\n';
};

exports.ends = false;
