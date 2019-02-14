Maintainer's guide
==================


Commit policy
-------------

* Pull requests from outside contributors require a review from a maintainer.

* Maintainers should avoid working on a master branch directly and create branches for everything. A code review from another maintainer is recommended but not required, use your best judgment.



Release process
---------------

Releases happen on a 6-week schedule. Currently due to a long break the date of the next release is not set.

* Update CHANGES.md with everything interesting since the last update.

* Update version numbers using the three-part x.y.z notation everywhere:

  * The header in CHANGES.md (this is where the site looks for the latest version number)
  * ``"version"`` attribute in package.json
  * ``"version"`` attribute in package-lock.json (run `npm install`)
  * Two places in docs/conf.py (``version`` and ``release``)

* Commit the version changes and tag the commit with the plain version number (no "v." or anything like that)

* Push the commit and the tags to master (``git push && git push --tags``)

Pushing the tag triggers the update process which can be monitored at http://highlightjs.org/api/release/

When something didn't work *and* it's fixable in code (version numbers mismatch, last minute patches, etc), simply make another release incrementing the third (revision) part of the version number.
