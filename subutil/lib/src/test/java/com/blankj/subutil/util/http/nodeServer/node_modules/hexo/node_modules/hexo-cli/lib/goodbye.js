'use strict';

var byeWords = [
  'Good bye',
  'See you again',
  'Farewell',
  'Have a nice day',
  'Bye!',
  'Catch you later'
];

module.exports = function() {
  return byeWords[(Math.random() * byeWords.length) | 0];
};
