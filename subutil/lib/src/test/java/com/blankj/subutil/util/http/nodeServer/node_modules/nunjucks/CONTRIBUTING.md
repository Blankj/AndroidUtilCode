# Contributing

Thanks for your interest in contributing! The advice below will help you get your issue fixed / pull request merged.


## Purpose

Nunjucks has the following purpose:

* Aim for templating feature parity with Jinja2.
* Aim for templating feature parity with Twig, but only when not conflicting with Jinja2 parity.
* Works in all node releases that are
  [actively maintained by the Node Foundation](https://github.com/nodejs/Release#release-schedule)
* Works in all modern browsers (with [ES5 support](http://kangax.github.io/compat-table/es5/)).
* Works in IE8 with [es5-shim](https://github.com/es-shims/es5-shim).
* Keep footprint browser files as small as possible (save on bandwidth, download time).
* Keep performance as fast as possible (see benchmarks).
* Keep maintenance as easy as possible (avoid complexity, automate what we can).

Notes:

* We don't aim for parity of all language specific syntax.
* We don't aim for parity of language specific filters like [Twig's PHP date format](http://twig.sensiolabs.org/doc/functions/date.html).

Issues and pull requests contributing to this purpose have the best chance to make it into Nunjucks.


## Questions?

Please DO NOT ask "how do I?" or usage questions via GitHub issues. Instead,
use the [https://groups.google.com/forum/#!forum/nunjucks](mailing list).


## Submitting Issues

Issues are easier to reproduce/resolve when they have:

- A pull request with a failing test demonstrating the issue
- A code example that produces the issue consistently
- A traceback (when applicable)


## Pull Requests

When creating a pull request:

- Write tests (see below).
- Note user-facing changes in the [`CHANGELOG.md`](CHANGELOG.md) file.
- Update the documentation (in [`docs/`](docs/)) as needed.


## Testing

Please add tests for any changes you submit. The tests should fail before your
code changes, and pass with your changes. Existing tests should not break. Test
coverage (output at the end of every test run) should never decrease after your
changes.

To install all the requirements for running the tests:

```bash
npm install
```

To run the tests:

```bash
npm test
```
