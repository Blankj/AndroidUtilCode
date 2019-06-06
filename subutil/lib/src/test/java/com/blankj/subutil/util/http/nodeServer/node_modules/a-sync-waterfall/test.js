"use strict";
const waterfall = require('./index');

var generateSyncTask = function(index) {
	return function (x){
		return function(cb){
			console.log(x);
			cb(null);
		};
	}(index);
};


var generateAsyncTask = function(index) {
	return function (x){
		return function(cb){
			setTimeout(function(){
				console.log(x);
				cb(null);
			}, 0);
		};
	}(index);
};

var generateSyncTasks = function(count){
	var tasks = [];
	for(var i=0; i<count; i++) {
		tasks.push(generateSyncTask(i));
	}
	return tasks;
}

var generateAsyncTasks = function(count){
	var tasks = [];
	for(var i=0; i<count; i++) {
		tasks.push(generateAsyncTask(i));
	}
	return tasks;
}


var generateRandomTasks = function(count){
	var tasks = [];
	for(var i=0; i<count; i++) {
		Math.random() > .5 ? tasks.push(generateAsyncTask(i)) : tasks.push(generateSyncTask(i))
	}
	return tasks;
}

var done = function(){
	console.log('done');
}

var testSync = function(){
	waterfall(generateSyncTasks(10), done);
	console.log('this text should be after waterfall');

};

var testAsync = function(){
	waterfall(generateAsyncTasks(5), done);
	console.log('this text should be before waterfall');
};

var testMixed = function(){
	waterfall(generateRandomTasks(20), done);
};


console.log('testSync:');
testSync();

// console.log('\ntestAsync: ');
// testAsync();

console.log('\ntestMixed: ');
testMixed();