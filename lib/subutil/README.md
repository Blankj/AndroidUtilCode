## How to use

You should copy the following classes which you want to use in your project.


## APIs


* ### About AppStore -> [AppStoreUtils.java][appStore.java] -> [Demo][appStore.demo]
```
getAppStoreIntent
```

* ### About Battery -> [BatteryUtils.java][battery.java] -> [Demo][battery.demo]
```
registerBatteryStatusChangedListener
isRegisteredBatteryStatusChangedListener
unregisterBatteryStatusChangedListener
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

* ### About Country -> [CountryUtils.java][country.java] -> [Demo][country.demo]
```
getCountryCodeBySim
getCountryCodeByLanguage
getCountryBySim
getCountryByLanguage
```

* ### About Dangerous -> [DangerousUtils.java][dangerous.java] -> [Demo][dangerous.demo]
```
installAppSilent
uninstallAppSilent
shutdown
reboot
reboot2Recovery
reboot2Bootloader
setMobileDataEnabled
sendSmsSilent
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



[appStore.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/subutil/src/main/java/com/blankj/subutil/util/AppStoreUtils.java
[appStore.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/subutil/pkg/src/main/java/com/blankj/subutil/pkg/feature/appStore/AppStoreActivity.kt

[battery.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/subutil/src/main/java/com/blankj/subutil/util/BatteryUtils.java
[battery.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/subutil/pkg/src/main/java/com/blankj/subutil/pkg/feature/battery/BatteryActivity.kt

[coordinate.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/subutil/src/main/java/com/blankj/subutil/util/CoordinateUtils.java
[coordinate.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/subutil/src/test/java/com/blankj/subutil/util/CoordinateUtilsTest.java

[country.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/subutil/src/main/java/com/blankj/subutil/util/CountryUtils.java
[country.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/subutil/pkg/src/main/java/com/blankj/subutil/pkg/feature/country/CountryActivity.kt

[dangerous.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/subutil/src/main/java/com/blankj/subutil/util/DangerousUtils.java
[dangerous.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/subutil/pkg/src/main/java/com/blankj/subutil/pkg/feature/dangerous/DangerousActivity.kt

[location.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/subutil/src/main/java/com/blankj/subutil/util/LocationUtils.java
[location.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/subutil/pkg/src/main/java/com/blankj/subutil/pkg/feature/location/LocationActivity.kt

[pinyin.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/subutil/src/main/java/com/blankj/subutil/util/PinyinUtils.java
[pinyin.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/subutil/pkg/src/main/java/com/blankj/subutil/pkg/feature/pinyin/PinyinActivity.kt
