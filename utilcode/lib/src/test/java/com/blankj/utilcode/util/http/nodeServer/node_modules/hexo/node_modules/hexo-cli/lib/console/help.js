'use strict';

var chalk = require('chalk');
var fs = require('hexo-fs');
var pathFn = require('path');
var Promise = require('bluebird');

var COMPLETION_DIR = pathFn.join(__dirname, '../../completion');

function helpConsole(args) {
  if (args.v || args.version) {
    return this.call('version');
  } else if (args.consoleList) {
    return printConsoleList(this.extend.console.list());
  } else if (typeof args.completion === 'string') {
    return printCompletion(args.completion);
  }

  var command = args._[0];

  if (typeof command === 'string' && command !== 'help') {
    var c = this.extend.console.get(command);
    if (c) return printHelpForCommand(this.extend.console.alias[command], c);
  }

  return printAllHelp(this.extend.console.list());
}

function printHelpForCommand(command, data) {
  var options = data.options;

  console.log('Usage: hexo', command, options.usage || '');
  console.log('\nDescription:');
  console.log((options.description || options.desc || data.description || data.desc) + '\n');

  if (options.arguments) printList('Arguments', options.arguments);
  if (options.commands) printList('Commands', options.commands);
  if (options.options) printList('Options', options.options);

  return Promise.resolve();
}

function printAllHelp(list) {
  var keys = Object.keys(list);
  var commands = [];
  var key = '';

  for (var i = 0, len = keys.length; i < len; i++) {
    key = keys[i];

    commands.push({
      name: key,
      desc: list[key].desc
    });
  }

  console.log('Usage: hexo <command>\n');

  printList('Commands', commands);

  printList('Global Options', [
    {name: '--config', desc: 'Specify config file instead of using _config.yml'},
    {name: '--cwd', desc: 'Specify the CWD'},
    {name: '--debug', desc: 'Display all verbose messages in the terminal'},
    {name: '--draft', desc: 'Display draft posts'},
    {name: '--safe', desc: 'Disable all plugins and scripts'},
    {name: '--silent', desc: 'Hide output on console'}
  ]);

  console.log('For more help, you can use \'hexo help [command]\' for the detailed information');
  console.log('or you can check the docs:', chalk.underline('http://hexo.io/docs/'));

  return Promise.resolve();
}

function printList(title, list) {
  var length = 0;
  var str = title + ':\n';

  list.forEach(function(item) {
    length = Math.max(length, item.name.length);
  });

  list.sort(function(a, b) {
    var nameA = a.name;
    var nameB = b.name;

    if (nameA < nameB) return -1;
    else if (nameA > nameB) return 1;

    return 0;
  }).forEach(function(item) {
    var padding = length - item.name.length + 2;
    str += '  ' + chalk.bold(item.name);

    while (padding--) {
      str += ' ';
    }

    str += (item.description || item.desc) + '\n';
  });

  console.log(str);

  return Promise.resolve();
}

function printConsoleList(list) {
  console.log(Object.keys(list).join('\n'));

  return Promise.resolve();
}

function printCompletion(type) {
  return fs.readFile(pathFn.join(COMPLETION_DIR, type)).then(function(content) {
    console.log(content);
  });
}

module.exports = helpConsole;
