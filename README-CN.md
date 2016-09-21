## Android开发人员不得不收集的代码([持续更新中][update_log.md])
为方便查找，已进行大致归类，其目录如下所示：  
> - **App相关→[AppUtils.java][app.java]**
>  - 获取安装App(支持6.0)的意图 *getInstallAppIntent*
>  - 获取卸载App的意图 *getUninstallAppIntent*
>  - 获取打开App的意图 *getOpenAppItent*
>  - 获取App信息的意图 *getAppInfoIntent*
>  - 获取App信息分享的意图 *getShareInfoIntent*
>  - 判断App是否安装 *isInstallApp*
>  - 获取当前App信息 *getAppInfo*
>  - 获取所有已安装App信息 *getAllAppsInfo*
>  - 判断当前App处于前台还是后台 *isAppBackground*

> - **常量相关→[ConstUtils.java][const.java]**
>  - 存储相关常量
>  - 时间相关常量
>  - 正则相关常量

> - **转换相关→[ConvertUtils.java][convert.java]→[Test][convert.test]**
>  - byteArr与hexString互转 *bytes2HexString*、*hexString2Bytes*
>  - charArr与byteArr互转 *chars2Bytes*、*bytes2Chars*
>  - 字节数与unit为单位的size互转 *byte2Size*、*size2Byte*
>  - 字节数转合适大小 *byte2FitSize*
>  - inputStream与outputStream互转 *input2OutputStream*、*output2InputStream*
>  - inputStream与byteArr互转 *inputStream2Bytes*、*bytes2InputStream*
>  - outputStream与byteArr互转 *outputStream2Bytes*、*bytes2OutputStream*
>  - inputStream与string按编码互转 *inputStream2String*、*string2InputStream*
>  - outputStream与string按编码互转 *outputStream2String*、*string2OutputStream*
>  - bitmap与byteArr互转 *bitmap2Bytes*、*bytes2Bitmap*
>  - drawable与bitmap互转 *drawable2Bitmap*、*bitmap2Drawable*
>  - drawable与byteArr互转 *drawable2Bytes*、*bytes2Drawable*
>  - dp与px互转 *dp2px*、*px2dp*
>  - sp与px互转 *sp2px*、*px2sp*

> - **设备相关→[DeviceUtils.java][device.java]**
>  - 获取设备MAC地址 *getMacAddress*
>  - 获取设备厂商，如Xiaomi *getManufacturer*
>  - 获取设备型号，如MI2SC *getModel*

> - **编码解码相关→[EncodeUtils.java][encode.java]→[Test][encode.test]**
>  - URL编码 *urlEncode*
>  - URL解码 *urlDecode*
>  - Base64编码 *base64Encode*、*base64Encode2String*
>  - Base64解码 *base64Decode*
>  - Base64URL安全编码 *base64UrlSafeEncode*
>  - Html编码 *htmlEncode*
>  - Html解码 *htmlDecode*

> - **加密解密相关→[EncryptUtils.java][encrypt.java]→[Test][encrypt.test]**
>  - MD2加密 *encryptMD2ToString*、*encryptMD2*
>  - MD5加密 *encryptMD5ToString*、*encryptMD5*
>  - MD5加密文件 *encryptMD5File2String*、*encryptMD5File*
>  - SHA1加密 *encryptSHA1ToString*、*encryptSHA1*
>  - SHA224加密 *encryptSHA224ToString*、*encryptSHA224*
>  - SHA256加密 *encryptSHA256ToString*、*encryptSHA256*
>  - SHA384加密 *encryptSHA384ToString*、*encryptSHA384*
>  - SHA512加密 *encryptSHA512ToString*、*encryptSHA512*
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

> - **文件相关→[FileUtils.java][file.java]→[Test][file.test]**
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
>  - 获取目录下所有文件 *listFilesInDir*
>  - 获取目录下所有文件包括子目录 *listFilesInDir*
>  - 获取目录下所有后缀名为suffix的文件 *listFilesInDirWithFilter*
>  - 获取目录下所有后缀名为suffix的文件包括子目录 *listFilesInDirWithFilter*
>  - 获取目录下所有符合filter的文件 *listFilesInDirWithFilter*
>  - 获取目录下所有符合filter的文件包括子目录 *listFilesInDirWithFilter*
>  - 获取目录下指定文件名的文件包括子目录 *searchFileInDir*
>  - 将输入流写入文件 *writeFileFromIS*
>  - 将字符串写入文件 *writeFileFromString*
>  - 简单获取文件编码格式 *getFileCharsetSimple*
>  - 获取文件行数 *getFileLines*
>  - 指定编码按行读取文件到List *readFile2List*
>  - 指定编码按行读取文件到StringBuilder中 *readFile2SB*
>  - 获取文件大小 *getFileSize*
>  - 获取文件的MD5校验码 *getFileMD5*
>  - 关闭IO *closeIO*
>  - 根据全路径获取最长目录 *getDirName*
>  - 根据全路径获取文件名 *getFileName*
>  - 根据全路径获取文件名不带拓展名 *getFileNameNoExtension*
>  - 根据全路径获取文件拓展名 *getFileExtension*

> - **图片相关→[ImageUtils.java][image.java]**
>  - bitmap与byteArr互转 *bitmap2Bytes*、*bytes2Bitmap*
>  - drawable与bitmap互转 *drawable2Bitmap*、*bitmap2Drawable*
>  - drawable与byteArr互转 *drawable2Bytes*、*bytes2Drawable*
>  - 获取bitmap *getBitmap*
>  - 缩放图片 *scale*
>  - 裁剪图片 *clip*
>  - 倾斜图片 *skew*
>  - 旋转图片 *rotate*
>  - 获取图片旋转角度 *getRotateDegree*
>  - 转为圆形图片 *toRound*
>  - 转为圆角图片 *toRoundCorner*
>  - 快速模糊 *fastBlur*
>  - renderScript模糊图片 *renderScriptBlur*
>  - stack模糊图片 *stackBlur*
>  - 添加颜色边框 *addFrame*
>  - 添加倒影 *addReflection*
>  - 添加文字水印 *addTextWatermark*
>  - 添加图片水印 *addImageWatermark*
>  - 转为alpha位图 *toAlpha*
>  - 转为灰度图片 *toGray*
>  - 保存图片 *save*
>  - 根据文件名判断文件是否为图片 *isImage*
>  - 获取图片类型 *getImageType*
>  - 按缩放压缩 *compressByScale*
>  - 按质量压缩 *compressByQuality*
>  - 按采样大小压缩 *compressBySampleSize*

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
>  - 获取当前的网络类型(WIFI,2G,3G,4G) *getNetWorkType*、*getNetWorkTypeName*

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

> - **正则相关→[RegexUtils.java][regex.java]→[Test][regex.test]**
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
>  - 判断SD卡是否可用 *isSDCardEnable*
>  - 获取SD卡路径 *getSDCardPath*
>  - 获取SD卡Data路径 *getDataPath*
>  - 计算SD卡的剩余空间 *getFreeSpace*

> - **Shell相关→[ShellUtils.java][shell.java]**
>  - 判断设备是否root *isRoot*
>  - 是否是在root下执行命令 *execCmd*

> - **尺寸相关→[SizeUtils.java][size.java]**
>  - dp与px转换 *dp2px*、*px2dp*
>  - sp与px转换 *sp2px*、*px2sp*
>  - 各种单位转换 *applyDimension*
>  - 在onCreate()即可强行获取View的尺寸 *forceGetViewSize*
>  - ListView中提前测量View尺寸(注释萌萌哒) *measureView*

> - **SP相关→[SPUtils.java][sp.java]→[Test][sp.test]**
>  - SPUtils构造函数 *SPUtils*
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
>  - SP中获取所有键值对 *getAll*
>  - SP中移除该key *remove*
>  - SP中是否存在该key *contains*
>  - SP中清除所有数据 *clear*

> - **字符串相关→[StringUtils.java][string.java]→[Test][string.test]**
>  - 判断字符串是否为null或长度为0 *isEmpty*
>  - 判断字符串是否为null或全为空格 *isSpace*
>  - null转为长度为0的字符串 *null2Length0*
>  - 返回字符串长度 *length*
>  - 首字母大写 *upperFirstLetter*
>  - 首字母小写 *lowerFirstLetter*
>  - 反转字符串 *reverse*
>  - 转化为半角字符 *toDBC*
>  - 转化为全角字符 *toSBC*

> - **线程池相关工具类→[ThreadPoolUtils.java][thread_pool.java]**
>  - ThreadPoolUtils构造函数 *ThreadPoolUtils*
>  - 在未来某个时间执行给定的命令 *execute*
>  - 在未来某个时间执行给定的命令链表 *execute*
>  - 待以前提交的任务执行完毕后关闭线程池 *shutDown*
>  - 试图停止所有正在执行的活动任务 *shutDownNow*
>  - 判断线程池是否已关闭 *isShutDown*
>  - 关闭线程池后判断所有任务是否都已完成 *isTerminated*
>  - 请求关闭、发生超时或者当前线程中断 *awaitTermination*
>  - 提交一个Callable任务用于执行 *submit*
>  - 提交一个Runnable任务用于执行 *submit*
>  - 执行给定的任务 *invokeAll*、*invokeAny*
>  - 延迟执行Runnable命令 *schedule*
>  - 延迟执行Callable命令 *schedule*
>  - 延迟并循环执行命令 *scheduleWithFixedRate*、*scheduleWithFixedDelay*

> - **时间相关→[TimeUtils.java][time.java]→[Test][time.test]**
>  - 将时间戳转为时间字符串 *milliseconds2String*
>  - 将时间字符串转为时间戳 *string2Milliseconds*
>  - 将时间字符串转为Date类型 *string2Date*
>  - 将Date类型转为时间字符串 *date2String*
>  - 将Date类型转为时间戳 *date2Milliseconds*
>  - 将时间戳转为Date类型 *milliseconds2Date*
>  - 毫秒时间戳单位转换（单位：unit） *milliseconds2Unit*
>  - 获取两个时间差（单位：unit） *getIntervalTime*
>  - 获取当前时间 *getCurTimeMills*、*getCurTimeString*、*getCurTimeDate*
>  - 获取与当前时间的差（单位：unit） *getIntervalByNow*
>  - 判断闰年 *isLeapYear*

> - **未归类→[UnclassifiedUtils.java][unclassified.java]**
>  - 获取服务是否开启 *isRunningService*

> - **压缩相关工具类→[ZipUtils.java][zip.java]→[Test][zip.test]**
>  - 批量压缩文件 *zipFiles*
>  - 压缩文件 *zipFile*
>  - 批量解压文件 *unzipFiles*
>  - 解压文件 *unzipFile*
>  - 解压带有关键字的文件 *unzipFileByKeyword*
>  - 获取压缩文件中的文件路径链表 *getFilesPath*
>  - 获取压缩文件中的注释链表 *getComments*
>  - 获取压缩文件中的文件对象 *getEntries*

> - **更新Log→[update_log.md][update_log.md]**

***
  
**做这份整理是想把它作为Android开发的小字典，当遇到一些琐碎问题时，不用再面向百度或者谷歌查询API的使用，费时费力，这里有的话，大家尽管撸走；同时也希望它能逐日壮大起来，期待大家的Star和完善，当然我也会一直更新发布版本和日志，为了方便大家导入，现已上传jcenter；其中很多代码也是汇四方之精华，谢谢前辈们的提供，当然最终还是要通过单元测试的，如有错误，请及时告之；开设QQ群提供讨论，群号：74721490，至于验证问题对大家来说肯定都是小case；最近在玩微博，玩的话向大家求个[关注][weibo]**  

### Download
***
Gradle:
``` groovy
compile 'com.blankj:utilcode:1.2.1'
```

### Proguard
***
```
-keep class com.blankj.utilcode.** { *; }
-keep classmembers class com.blankj.utilcode.** { *; }
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

[phone.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/PhoneUtils.java

[regex.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/RegexUtils.java
[regex.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/RegexUtilsTest.java

[screen.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ScreenUtils.java

[sdcard.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/SDCardUtils.java

[shell.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ShellUtils.java

[size.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/SizeUtils.java

[sp.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/SPUtils.java
[sp.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/SPUtilsTest.java

[string.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/StringUtils.java
[string.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/StringUtilsTest.java

[thread_pool.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ThreadPoolUtils.java

[time.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/TimeUtils.java
[time.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/TimeUtilsTest.java

[unclassified.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/UnclassifiedUtils.java

[zip.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ZipUtils.java
[zip.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/ZipUtilsTest.java

[update_log.md]: https://github.com/Blankj/AndroidUtilCode/blob/master/update_log.md

[weibo]: http://weibo.com/blankcmj