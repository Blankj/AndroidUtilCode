## How to use

You should copy the following classes which you want to use in your project.


## APIs

* ### About Brightness -> [BrightnessUtils.java][brightness.java] -> [Demo][brightness.demo]
```
isAutoBrightnessEnabled
setAutoBrightnessEnabled
getBrightness
setBrightness
setWindowBrightness
getWindowBrightness
```

* ### About Clipboard -> [ClipboardUtils.java][clipboard.java] -> [Test][clipboard.test]
```
copyText
getText
copyUri
getUri
copyIntent
getIntent
```

* ### About Location -> [LocationUtils.java][location.java] -> [Demo][location.demo]
```
isGpsEnabled
isLocationEnabled
openGpsSettings
register
unregister
getAddress
getCountryName
getLocality
getStreet
isBetterLocation
isSameProvider
```

* ### About Pinyin -> [PinyinUtils.java][pinyin.java] -> [Demo][pinyin.demo]
```
ccs2Pinyin
ccs2Pinyin
getPinyinFirstLetter
getPinyinFirstLetters
getSurnamePinyin
getSurnameFirstLetter
```

* ### About ThreadPool -> [ThreadPoolUtils.java][thread_pool.java]
```
ThreadPoolUtils
execute
execute
shutDown
shutDownNow
isShutDown
isTerminated
awaitTermination
submit
submit
invokeAll, invokeAny
schedule
schedule
scheduleWithFixedRate
scheduleWithFixedDelay
```



[brightness.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/src/main/java/com/blankj/subutil/util/BrightnessUtils.java
[brightness.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/feature/sub/brightness/BrightnessActivity.java

[clipboard.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/src/main/java/com/blankj/subutil/util/ClipboardUtils.java
[clipboard.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/src/test/java/com/blankj/subutil/util/ClipboardUtilsTest.java

[location.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/src/main/java/com/blankj/subutil/util/LocationUtils.java
[location.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/feature/sub/location/LocationActivity.java

[pinyin.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/src/main/java/com/blankj/subutil/util/PinyinUtils.java
[pinyin.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/feature/sub/pinyin/PinyinActivity.java

[thread_pool.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/src/main/java/com/blankj/subutil/util/ThreadPoolUtils.java
