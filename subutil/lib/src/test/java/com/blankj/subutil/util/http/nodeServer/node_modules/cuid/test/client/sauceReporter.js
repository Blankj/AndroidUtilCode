const sauceReporter = (tape) => {
  const results = [];
  const start = new Date().getTime();
  let testStart = start;

  tape.createStream({ objectMode: true }).on('data', function (row) {

    if (row.type === 'assert') {

      const result = {
        name: row.name,
        result: row.ok,
        message: `Actual: ${ JSON.stringify(row.actual) }
          Expected: ${ JSON.stringify(row.expected) }`,
        duration: new Date().getTime() - testStart
      };

      testStart = new Date().getTime();

      results.push(result);
    }

    if (row.name === 'complete') {
      window.global_test_results = {
        tests: results,
        duration: new Date().getTime() - start,
        total: results.length,
        passed: results
          .filter(result => result.result)
          .reduce(previousValue => previousValue + 1, 0),
        failed: results
          .filter(result => !result.result)
          .reduce(previousValue => previousValue + 1, 0)
      };

      console.log(`Tests complete!

        ${ JSON.stringify(window.global_test_results) }`);
    }
  });
};

export default sauceReporter;
