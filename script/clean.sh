#!/usr/bin/env bash
rm -rf .idea
find . -name "*.iml" -type f -exec rm -rf {} \;
find . -name "build" -type d -exec rm -rf {} \;