#!/usr/bin/env bash

set -e

# build dev debug apk
./gradlew --daemon installProductionRelease

# start main activity
adb shell am start -n "com.blankj.androidutilcode/com.blankj.main.pkg.MainActivity" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER