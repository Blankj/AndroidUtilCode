
# Pushing a New Version

Nunjucks attempts to adhere to semantic versioning. The API is very stable, so
from here on out it will most likely be point releases.

1. Do a `pull` from github to make sure you have all the latest updates.

2. View all the changes since the last version:

   ```
   $ git log --oneline v1.2.3..master
   ```

Replace `v1.2.3` with whatever the last version was, and you'll see all the
changes going out in this version. Ensure that all significant user-facing
changes (new features and bugfixes) are mentioned in `CHANGELOG.md`. Change the
"master (unreleased)" heading in `CHANGELOG.md` to the new version number and
date.

3. Update the version in `package.json`.

3. Run the command to update the ready-made files for the browser.

   ```
   $ npm run browserfiles
   ```

5. Commit above changes and push to `master` (or a release branch, if using one).

6. Draft a new release on GitHub and copy the changelog to the description. The
   tag and title should both be the version, in the form `v2.3.0`. Publish the
   release.

7. Publish to npm:

   ```
   npm publish
   ```

8. Make sure docs are up-to-date. You need to copy all the `nunjucks*.js` files
   in `browser/` to the docs. This is where the "download" link points to in
   the docs. You also need to copy the tests into the docs, for the online
   browser tests. ``make prod`` in the ``docs/`` dir will handle these tasks
   for you. Push (force push if necessary) the build out _site folder onto the
   `gh-pages` branch of the `nunjucks` repo to get it live. One way to do that
   is the following commands. These commands presume that you have another
   nunjucks git clone inside the (git-ignored) `docs/_site` directory, checked
   out to the `gh-pages` branch (and tracking `origin/gh-pages`). (To set that
   up the first time, `cd docs/_site`, `rm -rf *`, `git clone
   git@github.com:mozilla/nunjucks.git .`, and `git checkout gh-pages`).

   ```
   cd docs && make prod
   cd files
   python -m SimpleHTTPServer
   # load http://localhost:8000/tests/browser/ and verify tests pass in browser
   cd ../_site && git add -A && git commit && git push
   ```

9. Add a new "master (unreleased)" section at the top of `CHANGELOG.md`.

10. Bump the version number in `package.json` to a development pre-release of
    the next anticipated release number (e.g. "2.2.0-dev.1").
