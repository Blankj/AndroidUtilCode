# Changelog for markdown

## v0.5.0 - 2013-07-26

There might be other bug fixes then the ones listed - I've been a bit lax at
updating the changes file, sorry :(

- Fix 'undefined' appearing in output for some cases with blockquotes
- Fix (multiple) global variable leaks. Ooops
- Fix IE8 issues (#68, #74, #97)
- Fix IE8 issue (#86)
- Handle windows line endings (#58)
- Allow spaces in img/link paths (#48)
- Add explicit text of the license to the readme (#74)
- Style tweaks by Xhmikosr (#83, #81, #82)
- Build now tested by TravisCI thanks to sebs (#85)
- Fix 'cuddled' header parsing (#94)
- Fix images inside links mistakenly requiring a title attribute to parse
  correctly (#71)


## v0.4.0 - 2012-06-09

- Fix for anchors enclosed by parenthesis (issue #46)
- `npm test` will now run the entire test suite cleanly. (switch tests over to
  node-tap). (#21)
- Allow inline elements to appear inside link text (#27)
- Improve link parsing when link is inside parenthesis (#38)
- Actually render image references (#36)
- Improve link parsing when multiple on a line (#5)
- Make it work in IE7/8 (#37)
- Fix blockquote merging/implicit conversion between string/String (#44, #24)
- md2html can now process stdin (#43)
- Fix jslint warnings (#42)
- Fix to correctly render self-closing tags (#40, #35, #28)
