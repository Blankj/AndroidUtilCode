## How to use

从下面选择拷贝你需要用到的类到你项目中即可。


## APIs


* ### 剪贴板相关 -> [ClipboardUtils.java][clipboard.java] -> [Test][clipboard.test]
```
copyText  : 复制文本到剪贴板
getText   : 获取剪贴板的文本
copyUri   : 复制 uri 到剪贴板
getUri    : 获取剪贴板的 uri
copyIntent: 复制意图到剪贴板
getIntent : 获取剪贴板的意图
```

* ### 坐标转换相关 -> [CoordinateUtils.java][coordinate.java] -> [Test][coordinate.test]
```
bd09ToGcj02 : BD09 坐标转 GCJ02 坐标
gcj02ToBd09 : GCJ02 坐标转 BD09 坐标
gcj02ToWGS84: GCJ02 坐标转 WGS84 坐标
wgs84ToGcj02: WGS84 坐标转 GCJ02 坐标
bd09ToWGS84 : BD09 坐标转 WGS84 坐标
wgs84ToBd09 : WGS84 坐标转 BD09 坐标
```

* ### 定位相关 -> [LocationUtils.java][location.java] -> [Demo][location.demo]
```
isGpsEnabled     : 判断 Gps 是否可用
isLocationEnabled: 判断定位是否可用
openGpsSettings  : 打开 Gps 设置界面
register         : 注册
unregister       : 注销
getAddress       : 根据经纬度获取地理位置
getCountryName   : 根据经纬度获取所在国家
getLocality      : 根据经纬度获取所在地
getStreet        : 根据经纬度获取所在街道
isBetterLocation : 是否更好的位置
isSameProvider   : 是否相同的提供者
```

* ### 拼音相关 -> [PinyinUtils.java][pinyin.java] -> [Demo][pinyin.demo]
```
ccs2Pinyin           : 汉字转拼音
ccs2Pinyin           : 汉字转拼音
getPinyinFirstLetter : 获取第一个汉字首字母
getPinyinFirstLetters: 获取所有汉字的首字母
getSurnamePinyin     : 根据名字获取姓氏的拼音
getSurnameFirstLetter: 根据名字获取姓氏的首字母
```



[clipboard.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/lib/src/main/java/com/blankj/subutil/util/ClipboardUtils.java
[clipboard.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/lib/src/test/java/com/blankj/subutil/util/ClipboardUtilsTest.java

[coordinate.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/lib/src/main/java/com/blankj/subutil/util/CoordinateUtils.java
[coordinate.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/lib/src/test/java/com/blankj/subutil/util/CoordinateUtilsTest.java

[location.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/lib/src/main/java/com/blankj/subutil/util/LocationUtils.java
[location.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/pkg/src/main/java/com/blankj/subutil/pkg/feature/location/LocationActivity.kt

[pinyin.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/lib/src/main/java/com/blankj/subutil/util/PinyinUtils.java
[pinyin.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/pkg/src/main/java/com/blankj/subutil/pkg/feature/pinyin/PinyinActivity.kt
