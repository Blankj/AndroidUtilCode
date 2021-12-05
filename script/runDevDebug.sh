#!/usr/bin/env bash

set -e

# build dev debug apk
./gradlew --daemon installDevDebug

# start main activity
adb shell am start -n "com.blankj.androidutilcode.dev/com.blankj.main.pkg.MainActivity" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER