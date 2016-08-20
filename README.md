## Android开发人员不得不收集的代码(持续更新中)
***
为方便查找，已进行大致归类，其目录如下所示：  
> - **App相关→[AppUtils.java][app.java]**
>  - 安装App *installApp*
>  - 卸载指定包名的App *uninstallApp*
>  - 获取当前App信息 *getAppInfo*
>  - 获取所有已安装App信息 *getAllAppsInfo*
>  - 根据包名判断App是否安装 *isInstallApp*
>  - 打开指定包名的App *openAppByPackageName*
>  - 打开指定包名的App应用信息界面 *openAppInfo*
>  - 可用来做App信息分享 *shareAppInfo*
>  - 判断当前App处于前台还是后台 *isAppBackground*

> - **常量相关→[ConstUtils.java][const.java]**
>  - 存储相关常量
>  - 时间相关常量
>  - 正则相关常量

> - **转换相关→[ConvertUtils.java][convert.java]→[单元测试][convert.test]**
>  - 每1个byte转为2个hex字符 *bytes2HexString*
>  - 每2个hex字符转为1个byte *hexString2Bytes*
>  - charArr转byteArr *chars2Bytes*
>  - byteArr转charArr *bytes2Chars*

> - **设备相关→[DeviceUtils.java][device.java]**
>  - 获取设备MAC地址 *getMacAddress*
>  - 获取设备厂商，如Xiaomi *getManufacturer*
>  - 获取设备型号，如MI2SC *getModel*

> - **编码解码相关→[EncodeUtils.java][encode.java]→[单元测试][encode.test]**
>  - URL编码 *urlEncode*
>  - URL解码 *urlDecode*
>  - Base64编码 *base64Encode* *base64Encode2String*
>  - Base64解码 *base64Decode*
>  - Base64URL安全编码 *base64UrlSafeEncode*
>  - Html编码 *htmlEncode*
>  - Html解码 *htmlDecode*

> - **加密解密相关→[EncryptUtils.java][encrypt.java]→[单元测试][encrypt.test]**
>  - MD2加密 *encryptMD2ToString* *encryptMD2*
>  - MD5加密 *encryptMD5ToString* *encryptMD5*
>  - SHA1加密 *encryptSHA1ToString* *encryptSHA1*
>  - SHA224加密 *encryptSHA224ToString* *encryptSHA224*
>  - SHA256加密 *encryptSHA256ToString* *encryptSHA256*
>  - SHA384加密 *encryptSHA384ToString* *encryptSHA384*
>  - SHA512加密 *encryptSHA512ToString* *encryptSHA512*
>  - 获取文件的MD5校验码 *encryptMD5File2String* *encryptMD5File*
>  - DES加密后转为Base64编码 *encryptDES2Base64*
>  - DES加密后转为16进制 *encryptDES2HexString*
>  - DES加密 *encryptDES*
>  - DES解密Base64编码密文 *decryptBase64DES*
>  - DES解密16进制密文 *decryptHexStringDES*
>  - DES解密 *decryptDES*
>  - 3DES加密后转为Base64编码 *encrypt3DES2Base64*
>  - 3DES加密后转为16进制 *encrypt3DES2HexString*
>  - 3DES加密 *encrypt3DES*
>  - 3DES解密Base64编码密文 *decryptBase64_3DES*
>  - 3DES解密16进制密文 *decryptHexString3DES*
>  - 3DES解密 *decrypt3DES*
>  - AES加密后转为Base64编码 *encryptAES2Base64*
>  - AES加密后转为16进制 *encryptAES2HexString*
>  - AES加密 *encryptAES*
>  - AES解密Base64编码密文 *decryptBase64AES*
>  - AES解密16进制密文 *decryptHexStringAES*
>  - AES解密 *decryptAES*

> - **文件相关→[FileUtils.java][file.java]→[单元测试][file.test]**
>  - 根据文件路径获取文件 *getFileByPath*
>  - 判断文件是否存在 *isFileExists*
>  - 判断是否是目录 *isDir*
>  - 判断是否是文件 *isFile*
>  - 判断目录是否存在，不存在则判断是否创建成功 *createOrExistsDir*
>  - 判断文件是否存在，不存在则判断是否创建成功 *createOrExistsFile*
>  - 判断文件是否存在，存在则在创建之前删除 *createFileByDeleteOldFile*
>  - 复制目录 *copyDir*
>  - 复制文件 *copyFile*
>  - 移动目录 *moveDir*
>  - 移动文件 *moveFile*
>  - 删除目录 *deleteDir*
>  - 删除文件 *deleteFile*
>  - 将输入流写入文件 *writeFileFromIS*
>  - 将字符串写入文件 *writeFileFromString*
>  - 简单获取文件编码格式 *getFileCharsetSimple*

> - **图片相关→[ImageUtils.java][image.java]**
>  - 完善ing

> - **键盘相关→[KeyboardUtils.java][keyboard.java]**
>  - 避免输入法面板遮挡
>  - 动态隐藏软键盘 *hideSoftInput*
>  - 点击屏幕空白区域隐藏软键盘(注释萌萌哒) *clickBlankArea2HideSoftInput0*
>  - 动态显示软键盘 *showSoftInput*
>  - 切换键盘显示与否状态 *toggleSoftInput*

> - **网络相关→[NetworkUtils.java][network.java]**
>  - 打开网络设置界面 *openWirelessSettings*
>  - 判断网络是否可用 *isAvailable*
>  - 判断网络是否连接 *isConnected*
>  - 判断网络是否是4G *is4G*
>  - 判断wifi是否连接状态 *isWifiConnected*
>  - 获取移动网络运营商名称 *getNetworkOperatorName*
>  - 获取移动终端类型 *getPhoneType*
>  - 获取当前的网络类型(WIFI,2G,3G,4G) *getNetWorkType* *getNetWorkTypeName*

> - **手机相关→[PhoneUtils.java][phone.java]**
>  - 判断设备是否是手机 *isPhone*
>  - 获取手机的IMIE *getPhoneIMEI*
>  - 获取手机状态信息 *getPhoneStatus*
>  - 跳至填充好phoneNumber的拨号界面 *dial*
>  - 拨打phoneNumber *call*
>  - 发送短信 *sendSms*
>  - 获取手机联系人 *getAllContactInfo*
>  - 打开手机联系人界面点击联系人后便获取该号码(注释萌萌哒) *getContantNum*
>  - 获取手机短信并保存到xml中 *getAllSMS*

> - **正则相关→[RegularUtils.java][regular.java]→[单元测试][regular.test]**
>  - 验证手机号（简单） *isMobileSimple*
>  - 验证手机号（精确） *isMobileExact*
>  - 验证电话号码 *isTel*
>  - 验证身份证号码15位 *isIDCard15*
>  - 验证身份证号码18位 *isIDCard18*
>  - 验证邮箱 *isEmail*
>  - 验证URL *isURL*
>  - 验证汉字 *isChz*
>  - 验证用户名 *isUsername*
>  - 验证yyyy-MM-dd格式的日期校验，已考虑平闰年 *isDate*
>  - 验证IP地址 *isIP*
>  - string是否匹配regex *isMatch*

> - **屏幕相关→[ScreenUtils.java][screen.java]**
>  - 获取手机分辨率 *getDeviceWidth*、*getDeviceHeight*
>  - 设置透明状态栏(api大于19方可使用) *setTransparentStatusBar*
>  - 隐藏状态栏(注释萌萌哒) *hideStatusBar*
>  - 获取状态栏高度 *getStatusBarHeight*
>  - 判断状态栏是否存在 *isStatusBarExists*
>  - 获取ActionBar高度 *getActionBarHeight*
>  - 显示通知栏 *showNotificationBar*
>  - 隐藏通知栏 *hideNotificationBar*
>  - 设置屏幕为横屏(注释萌萌哒) *setLandscape*
>  - 获取屏幕截图 *snapShotWithStatusBar*、*snapShotWithoutStatusBar*
>  - 判断是否锁屏 *isScreenLock*

> - **SD卡相关→[SDCardUtils.java][sdcard.java]**
>  - 获取设备SD卡是否可用 *isSDCardEnable*
>  - 获取设备SD卡路径 *getSDCardPath*
>  - 完善ing

> - **Shell相关→[ShellUtils.java][shell.java]**
>  - 判断设备是否root *isRoot*
>  - 是否是在root下执行命令 *execCmd*

> - **尺寸相关→[SizeUtils.java][size.java]**
>  - dp与px转换 *dp2px*、*px2dp*
>  - sp与px转换 *sp2px*、*px2sp*
>  - 各种单位转换 *applyDimension*
>  - 在onCreate()即可强行获取View的尺寸 *forceGetViewSize*
>  - ListView中提前测量View尺寸(注释萌萌哒) *measureView*

> - **SP相关→[SPUtils.java][sp.java]**
>  - SP中写入String类型value *putString*
>  - SP中读取String *getString*
>  - SP中写入int类型value *putInt*
>  - SP中读取int *getInt*
>  - SP中写入long类型value *putLong*
>  - SP中读取long *getLong*
>  - SP中写入float类型value *putFloat*
>  - SP中读取float *getFloat*
>  - SP中写入boolean类型value *putBoolean*
>  - SP中读取boolean *getBoolean*

> - **字符串相关→[StringUtils.java][string.java]→[单元测试][string.test]**
>  - 判断字符串是否为null或长度为0 *isEmpty*
>  - 判断字符串是否为null或全为空格 *isSpace*
>  - null转为长度为0的字符串 *null2Length0*
>  - 返回字符串长度 *length*
>  - 首字母大写 *upperFirstLetter*
>  - 首字母小写 *lowerFirstLetter*
>  - 转化为半角字符 *toDBC*
>  - 转化为全角字符 *toSBC*

> - **时间相关→[TimeUtils.java][time.java]→[单元测试][time.test]**
>  - 将时间戳转为时间字符串 *milliseconds2String*
>  - 将时间字符串转为时间戳 *string2Milliseconds*
>  - 将时间字符串转为Date类型 *string2Date*
>  - 将Date类型转为时间字符串 *date2String*
>  - 将Date类型转为时间戳 *date2Milliseconds*
>  - 将时间戳转为Date类型 *milliseconds2Date*
>  - 毫秒时间戳单位转换（单位：unit） *milliseconds2Unit*
>  - 获取两个时间差（单位：unit） *getIntervalTime*
>  - 获取当前时间 *getCurTimeMills* *getCurTimeString* *getCurTimeDate*
>  - 获取与当前时间的差（单位：unit） *getIntervalByNow*
>  - 判断闰年 *isLeapYear*

> - **未归类→[UnclassifiedUtils.java][unclassified.java]**
>  - 获取服务是否开启 *isRunningService*

> - **更新Log→[update_log.md][update_log.md]**

***
  
**做这份整理只是想把它作为Android的一本小字典，当遇到一些琐碎问题时，不用再面向百度或者谷歌查询API的使用，费时费力，这里有的话，大家尽管撸走。希望它能逐日壮大起来，期待你的Star和完善，用途的话大家想把它们整理成工具类或者什么的话都可以，之后我也会封装工具类并分享之，但本篇只是提供查阅，毕竟看md比看类文件要爽多了，其中好多代码我也是各种搜刮来的，也要谢谢各位的总结，大部分代码已验证过可行，如有错误，请及时告之，开设QQ群提供讨论，群号：74721490，至于验证问题对大家来说肯定都是小case。**  

### Download
***
Gradle:
``` groovy
compile 'com.blankj:utilcode:1.1.0'
```

### Proguard
***
```
-keep class com.blankj.utilcode.** { *; }
-keepclassmembers class com.blankj.utilcode.** { *; }
-dontwarn com.blankj.utilcode.**
```

### License
***
```
Copyright 2016 Blankj

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

[app.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/AppUtils.java

[const.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ConstUtils.java

[convert.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ConvertUtils.java
[convert.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/ConvertUtilsTest.java

[device.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/DeviceUtils.java

[encode.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/EncodeUtils.java
[encode.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/EncodeUtilsTest.java

[encrypt.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/EncryptUtils.java
[encrypt.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/EncryptUtilsTest.java

[file.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/FileUtils.java
[file.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/FileUtilsTest.java

[image.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ImageUtils.java

[keyboard.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/KeyboardUtils.java

[network.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/NetworkUtils.java

[phone.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/PhonekUtils.java

[regular.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/RegularUtils.java
[regular.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/RegularUtilsTest.java

[screen.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ScreenUtils.java

[sdcard.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/SDCUtils.java

[shell.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ShellUtils.java

[size.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/SizeUtils.java

[sp.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/SPUtils.java

[string.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/StringUtils.java
[string.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/StringUtilsTest.java

[time.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/TimeUtils.java
[time.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/TimeUtilsTest.java

[unclassified.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/UnclassifiedUtils.java

[update_log.md]: https://github.com/Blankj/AndroidUtilCode/blob/master/md/update_log.md
