module.exports = function(hljs) {
  function orReValues(ops){
    return ops
    .map(function(op) {
      return op
        .split('')
        .map(function(char) {
          return '\\' + char;
        })
        .join('');
    })
    .join('|');
  }

  var RE_IDENT = '~?[a-z$_][0-9a-zA-Z$_]*';
  var RE_MODULE_IDENT = '`?[A-Z$_][0-9a-zA-Z$_]*';

  var RE_PARAM_TYPEPARAM = '\'?[a-z$_][0-9a-z$_]*';
  var RE_PARAM_TYPE = '\s*:\s*[a-z$_][0-9a-z$_]*(\(\s*(' + RE_PARAM_TYPEPARAM + '\s*(,' + RE_PARAM_TYPEPARAM + ')*)?\s*\))?';
  var RE_PARAM = RE_IDENT + '(' + RE_PARAM_TYPE + ')?(' + RE_PARAM_TYPE + ')?';
  var RE_OPERATOR = "(" + orReValues(['||', '&&', '++', '**', '+.', '*', '/', '*.', '/.', '...', '|>']) + "|==|===)";
  var RE_OPERATOR_SPACED = "\\s+" + RE_OPERATOR + "\\s+";

  var KEYWORDS = {
    keyword:
      'and as asr assert begin class constraint do done downto else end exception external' +
      'for fun function functor if in include inherit initializer' +
      'land lazy let lor lsl lsr lxor match method mod module mutable new nonrec' +
      'object of open or private rec sig struct then to try type val virtual when while with',
    built_in:
      'array bool bytes char exn|5 float int int32 int64 list lazy_t|5 nativeint|5 ref string unit ',
    literal:
      'true false'
  };

  var RE_NUMBER = '\\b(0[xX][a-fA-F0-9_]+[Lln]?|' +
    '0[oO][0-7_]+[Lln]?|' +
    '0[bB][01_]+[Lln]?|' +
    '[0-9][0-9_]*([Lln]|(\\.[0-9_]*)?([eE][-+]?[0-9_]+)?)?)';

  var NUMBER_MODE = {
    className: 'number',
    relevance: 0,
    variants: [
      {
        begin: RE_NUMBER
      },
      {
        begin: '\\(\\-' + RE_NUMBER + '\\)'
      }
    ]
  };

  var OPERATOR_MODE = {
    className: 'operator',
    relevance: 0,
    begin: RE_OPERATOR
  };
  var LIST_CONTENTS_MODES = [
    {
      className: 'identifier',
      relevance: 0,
      begin: RE_IDENT
    },
    OPERATOR_MODE,
    NUMBER_MODE
  ];

  var MODULE_ACCESS_CONTENTS = [
    hljs.QUOTE_STRING_MODE,
    OPERATOR_MODE,
    {
      className: 'module',
      begin: "\\b" + RE_MODULE_IDENT, returnBegin: true,
      end: "\.",
      contains: [
        {
          className: 'identifier',
          begin: RE_MODULE_IDENT,
          relevance: 0
        }
      ]
    }
  ];

  var PARAMS_CONTENTS = [
    {
      className: 'module',
      begin: "\\b" + RE_MODULE_IDENT, returnBegin: true,
      end: "\.",
      relevance: 0,
      contains: [
        {
          className: 'identifier',
          begin: RE_MODULE_IDENT,
          relevance: 0
        }
      ]
    }
  ];

  var PARAMS_MODE = {
    begin: RE_IDENT,
    end: '(,|\\n|\\))',
    relevance: 0,
    contains: [
      OPERATOR_MODE,
      {
        className: 'typing',
        begin: ':',
        end: '(,|\\n)',
        returnBegin: true,
        relevance: 0,
        contains: PARAMS_CONTENTS
      }
    ]
  };

  var FUNCTION_BLOCK_MODE = {
    className: 'function',
    relevance: 0,
    keywords: KEYWORDS,
    variants: [
      {
        begin: '\\s(\\(\\.?.*?\\)|' + RE_IDENT + ')\\s*=>',
        end: '\\s*=>',
        returnBegin: true,
        relevance: 0,
        contains: [
          {
            className: 'params',
            variants: [
              {
                begin: RE_IDENT
              },
              {
                begin: RE_PARAM
              },
              {
                begin: /\(\s*\)/,
              }
            ]
          }
        ]
      },
      {
        begin: '\\s\\(\\.?[^;\\|]*\\)\\s*=>',
        end: '\\s=>',
        returnBegin: true,
        relevance: 0,
        contains: [
          {
            className: 'params',
            relevance: 0,
            variants: [
              PARAMS_MODE
            ]
          }
        ]
      },
      {
        begin: '\\(\\.\\s' + RE_IDENT + '\\)\\s*=>'
      }
    ]
  };
  MODULE_ACCESS_CONTENTS.push(FUNCTION_BLOCK_MODE);

  var CONSTRUCTOR_MODE = {
    className: 'constructor',
    begin: RE_MODULE_IDENT + '\\(',
    end: '\\)',
    illegal: '\\n',
    keywords: KEYWORDS,
    contains: [
      hljs.QUOTE_STRING_MODE,
      OPERATOR_MODE,
      {
        className: 'params',
        begin: '\\b' + RE_IDENT
      }
    ]
  };

  var PATTERN_MATCH_BLOCK_MODE = {
    className: 'pattern-match',
    begin: '\\|',
    returnBegin: true,
    keywords: KEYWORDS,
    end: '=>',
    relevance: 0,
    contains: [
      CONSTRUCTOR_MODE,
      OPERATOR_MODE,
      {
        relevance: 0,
        className: 'constructor',
        begin: RE_MODULE_IDENT
      }
    ]
  };

  var MODULE_ACCESS_MODE = {
    className: 'module-access',
    keywords: KEYWORDS,
    returnBegin: true,
    variants: [
      {
        begin: "\\b(" + RE_MODULE_IDENT + "\\.)+" + RE_IDENT
      },
      {
        begin: "\\b(" + RE_MODULE_IDENT + "\\.)+\\(",
        end: "\\)",
        returnBegin: true,
        contains: [
          FUNCTION_BLOCK_MODE,
          {
            begin: '\\(',
            end: '\\)',
            skip: true
          }
        ].concat(MODULE_ACCESS_CONTENTS)
      },
      {
        begin: "\\b(" + RE_MODULE_IDENT + "\\.)+{",
        end: "}"
      }
    ],
    contains: MODULE_ACCESS_CONTENTS
  };

  PARAMS_CONTENTS.push(MODULE_ACCESS_MODE);

  return {
    aliases: ['re'],
    keywords: KEYWORDS,
    illegal: '(:\\-|:=|\\${|\\+=)',
    contains: [
      hljs.COMMENT('/\\*', '\\*/', { illegal: '^(\\#,\\/\\/)' }),
      {
        className: 'character',
        begin: '\'(\\\\[^\']+|[^\'])\'',
        illegal: '\\n',
        relevance: 0
      },
      hljs.QUOTE_STRING_MODE,
      {
        className: 'literal',
        begin: '\\(\\)',
        relevance: 0
      },
      {
        className: 'literal',
        begin: '\\[\\|',
        end: '\\|\\]',
        relevance:  0,
        contains: LIST_CONTENTS_MODES
      },
      {
        className: 'literal',
        begin: '\\[',
        end: '\\]',
        relevance: 0,
        contains: LIST_CONTENTS_MODES
      },
      CONSTRUCTOR_MODE,
      {
        className: 'operator',
        begin: RE_OPERATOR_SPACED,
        illegal: '\\-\\->',
        relevance: 0
      },
      NUMBER_MODE,
      hljs.C_LINE_COMMENT_MODE,
      PATTERN_MATCH_BLOCK_MODE,
      FUNCTION_BLOCK_MODE,
      {
        className: 'module-def',
        begin: "\\bmodule\\s+" + RE_IDENT + "\\s+" + RE_MODULE_IDENT + "\\s+=\\s+{",
        end: "}",
        returnBegin: true,
        keywords: KEYWORDS,
        relevance: 0,
        contains: [
          {
            className: 'module',
            relevance: 0,
            begin: RE_MODULE_IDENT
          },
          {
            begin: '{',
            end: '}',
            skip: true
          }
        ].concat(MODULE_ACCESS_CONTENTS)
      },
      MODULE_ACCESS_MODE
    ]
  };
};