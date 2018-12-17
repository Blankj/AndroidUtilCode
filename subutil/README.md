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

* ### About Clone -> [CloneUtils.java][clone.java] -> [Test][clone.test]
```
deepClone
```

* ### About Coordinate -> [CoordinateUtils.java][coordinate.java] -> [Test][coordinate.test]
```
bd09ToGcj02
gcj02ToBd09
gcj02ToWGS84
wgs84ToGcj02
bd09ToWGS84
wgs84ToBd09
```

* ### About Flashlight -> [FlashlightUtils.java][flashlight.java] -> [Demo][flashlight.demo]
```
getInstance
Instance.register
Instance.unregister
Instance.setFlashlightOn
Instance.setFlashlightOff
Instance.isFlashlightOn
isFlashlightEnable
```

* ### About Gson -> [GsonUtils.java][gson.java] -> [Test][gson.test]
```
getGson
toJson
fromJson
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



[brightness.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/lib/src/main/java/com/blankj/subutil/util/BrightnessUtils.java
[brightness.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/pkg/src/main/java/com/blankj/subutil/pkg/feature/brightness/BrightnessActivity.java
[brightness.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/pkg/src/main/java/com/blankj/subutil/pkg/feature/brightness/BrightnessActivity.java

[clipboard.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/lib/src/main/java/com/blankj/subutil/util/ClipboardUtils.java
[clipboard.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/lib/src/test/java/com/blankj/subutil/util/ClipboardUtilsTest.java

[clone.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/lib/src/main/java/com/blankj/subutil/util/CloneUtils.java
[clone.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/lib/src/test/java/com/blankj/subutil/util/CloneUtilsTest.java

[coordinate.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/lib/src/main/java/com/blankj/subutil/util/CoordinateUtils.java
[coordinate.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/lib/src/test/java/com/blankj/subutil/util/CoordinateUtilsTest.java

[flashlight.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/lib/src/main/java/com/blankj/subutil/util/FlashlightUtils.java
[flashlight.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/pkg/src/main/java/com/blankj/subutil/pkg/feature/flashlight/FlashlightActivity.java

[gson.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/lib/src/main/java/com/blankj/subutil/util/GsonUtils.java
[gson.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/lib/src/test/java/com/blankj/subutil/util/GsonUtilsTest.java

[location.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/lib/src/main/java/com/blankj/subutil/util/LocationUtils.java
[location.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/pkg/src/main/java/com/blankj/subutil/pkg/feature/location/LocationActivity.java

[pinyin.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/lib/src/main/java/com/blankj/subutil/util/PinyinUtils.java
[pinyin.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/pkg/src/main/java/com/blankj/subutil/pkg/feature/pinyin/PinyinActivity.java
