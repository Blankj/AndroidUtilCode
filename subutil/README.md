* ### 剪贴板相关→[ClipboardUtils.java][clipboard.java]→[Test][clipboard.test]
```
copyText  : 复制文本到剪贴板
getText   : 获取剪贴板的文本
copyUri   : 复制uri到剪贴板
getUri    : 获取剪贴板的uri
copyIntent: 复制意图到剪贴板
getIntent : 获取剪贴板的意图
```

* ### 定位相关→[LocationUtils.java][location.java]→[Demo][location.demo]
```
isGpsEnabled     : 判断Gps是否可用
isLocationEnabled: 判断定位是否可用
openGpsSettings  : 打开Gps设置界面
register         : 注册
unregister       : 注销
getAddress       : 根据经纬度获取地理位置
getCountryName   : 根据经纬度获取所在国家
getLocality      : 根据经纬度获取所在地
getStreet        : 根据经纬度获取所在街道
isBetterLocation : 是否更好的位置
isSameProvider   : 是否相同的提供者
```

* ### 拼音相关→[PinyinUtils.java][pinyin.java]→[Demo][pinyin.demo]
```
ccs2Pinyin           : 汉字转拼音
ccs2Pinyin           : 汉字转拼音
getPinyinFirstLetter : 获取第一个汉字首字母
getPinyinFirstLetters: 获取所有汉字的首字母
getSurnamePinyin     : 根据名字获取姓氏的拼音
getSurnameFirstLetter: 根据名字获取姓氏的首字母
```

* ### 线程池相关→[ThreadPoolUtils.java][thread_pool.java]
```
ThreadPoolUtils       : ThreadPoolUtils构造函数
execute               : 在未来某个时间执行给定的命令
execute               : 在未来某个时间执行给定的命令链表
shutDown              : 待以前提交的任务执行完毕后关闭线程池
shutDownNow           : 试图停止所有正在执行的活动任务
isShutDown            : 判断线程池是否已关闭
isTerminated          : 关闭线程池后判断所有任务是否都已完成
awaitTermination      : 请求关闭、发生超时或者当前线程中断
submit                : 提交一个Callable任务用于执行
submit                : 提交一个Runnable任务用于执行
invokeAll, invokeAny  : 执行给定的任务
schedule              : 延迟执行Runnable命令
schedule              : 延迟执行Callable命令
scheduleWithFixedRate : 延迟并循环执行命令
scheduleWithFixedDelay: 延迟并以固定休息时间循环执行命令
```

[clipboard.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/src/main/java/com/blankj/subutil/util/ClipboardUtils.java
[clipboard.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/src/test/java/com/blankj/subutil/util/ClipboardUtilsTest.java


[location.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/src/main/java/com/blankj/subutil/util/LocationUtils.java
[location.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/sub/location/LocationActivity.java

[pinyin.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/src/main/java/com/blankj/subutil/util/PinyinUtils.java
[pinyin.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/sub/pinyin/PinyinActivity.java

[thread_pool.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/subutil/src/main/java/com/blankj/subutil/util/ThreadPoolUtils.java