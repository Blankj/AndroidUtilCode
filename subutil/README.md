## How to use

You should copy the following classes which you want to use in your project.


## APIs


* ### About Clipboard -> [ClipboardUtils.java][clipboard.java] -> [Test][clipboard.test]
```
copyText
getText
copyUri
getUri
copyIntent
getIntent
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



[clipboard.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/lib/src/main/java/com/blankj/subutil/util/ClipboardUtils.java
[clipboard.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/lib/src/test/java/com/blankj/subutil/util/ClipboardUtilsTest.java

[coordinate.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/lib/src/main/java/com/blankj/subutil/util/CoordinateUtils.java
[coordinate.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/lib/src/test/java/com/blankj/subutil/util/CoordinateUtilsTest.java

[location.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/lib/src/main/java/com/blankj/subutil/util/LocationUtils.java
[location.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/pkg/src/main/java/com/blankj/subutil/pkg/feature/location/LocationActivity.kt

[pinyin.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/lib/src/main/java/com/blankj/subutil/util/PinyinUtils.java
[pinyin.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/pkg/src/main/java/com/blankj/subutil/pkg/feature/pinyin/PinyinActivity.kt
