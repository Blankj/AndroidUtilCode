# Android开发人员不得不收集的代码(持续更新中)
***
为方便查找，已进行大致归类，其目录如下所示：  
> - [App相关][app.md]→[AppUtils.java][app.java]
>    - 安装指定路径下的Apk *installApp*
>    - 卸载指定包名的App *uninstallApp*
>    - 获取当前App信息 *getAppInfo*
>    - 获取所有已安装App信息 *getAllAppsInfo*
>	 - 根据包名判断App是否安装 *isInstallApp*
>    - 打开指定包名的App *openAppByPackageName*
>    - 打开指定包名的App应用信息界面 *openAppInfo*
>    - 可用来做App信息分享 *shareAppInfo*
>    - 判断当前App处于前台还是后台 *isApplicationBackground*

> - [设备相关][device.md]→[DeviceUtils.java][device.java]
>    - 获取设备MAC地址 *getMacAddress*
>    - 获取设备厂商，如Xiaomi *getManufacturer*
>    - 获取设备型号，如MI2SC *getModel*
>    - 获取设备SD卡是否可用 *isSDCardEnable*
>    - 获取设备SD卡路径 *getSDCardPath*

> - [编码解码相关][encode.md]→[EncodeUtils.java][encode.java]
>    - 以UTF-8编码字符串 *encodeUTF8*
>    - 字符编码 *encode*
>    - 以UTF-8解码字符串 *decodeUTF8*
>    - 字符解码 *decode*

> - [加解密相关][encrypt.md]→[EncryptUtils.java][encrypt.java]
>    - MD5加密 *getMD5* *encryptMD5* *getMD5File*
>    - SHA加密 *getSHA* *encryptSHA*

> - [键盘相关][keyboard.md]→[KeyboardUtils.java][keyboard.java]
>    - 避免输入法面板遮挡
>    - 动态隐藏软键盘 *hideSoftInput*
>    - 点击屏幕空白区域隐藏软键盘(注释萌萌哒) *clickBlankArea2HideSoftInput0*
>    - 动态显示软键盘 *showSoftInput*
>    - 切换键盘显示与否状态 *toggleSoftInput*

> - [网络相关][network.md]→[NetworkUtils.java][network.java]
>    - 打开网络设置界面 *openWirelessSettings*
>    - 判断网络是否可用 *isAvailable*
>    - 判断网络是否连接 *isConnected*
>    - 判断网络是否是4G *is4G*
>    - 判断wifi是否连接状态 *isWifiConnected*
>    - 获取移动网络运营商名称 *getNetworkOperatorName*
>    - 获取移动终端类型 *getPhoneType*
>    - 获取当前的网络类型(WIFI,2G,3G,4G) *getNetWorkType* *getNetWorkTypeName*

> - [手机相关][phone.md]→[PhoneUtils.java][phone.java]
>    - 判断设备是否是手机 *isPhone*
>    - 获取手机的IMIE *getDeviceIMEI*
>    - 获取手机状态信息 *getPhoneStatus*
>    - 跳至填充好phoneNumber的拨号界面 *dial*
>    - 拨打phoneNumber *call*
>    - 发送短信 *sendSms*
>    - 获取手机联系人 *getAllContactInfo*
>    - 打开手机联系人界面点击联系人后便获取该号码(注释萌萌哒) *getContantNum*
>    - 获取手机短信并保存到xml中 *getAllSMS*

> - [正则相关][regular.md]→[RegularUtils.java][regular.java]
>    - 正则工具类

> - [屏幕相关][screen.md]→[ScreenUtils.java][screen.java]
>    - 获取手机分辨率 *getDeviceWidth*、*getDeviceHeight*
>    - 设置透明状态栏(api >= 19方可使用) *setTransparentStatusBar*
>    - 隐藏状态栏(注释萌萌哒) *hideStatusBar*
>    - 获取状态栏高度 *getStatusBarHeight*
>    - 判断状态栏是否存在 *isStatusBarExists*
>    - 获取ActionBar高度 *getActionBarHeight*
>	 - 显示通知栏 *showNotificationBar*
>	 - 隐藏通知栏 *hideNotificationBar*
>    - 设置屏幕为横屏(注释萌萌哒) *setLandscape*
>    - 获取屏幕截图 *snapShotWithStatusBar*、*snapShotWithoutStatusBar*
>    - 判断是否锁屏 *isScreenLock*

> - [Shell相关][shell.md]→[ShellUtils.java][shell.java]
>    - 判断设备是否root *isRoot*
>    - 是否是在root下执行命令 *execCmd*

> - [尺寸相关][size.md]→[SizeUtils.java][size.java]
>    - dp与px转换 *dp2px*、*px2dp*
>    - sp与px转换 *sp2px*、*px2sp*
>    - 各种单位转换 *applyDimension*
>    - 在onCreate()即可强行获取View的尺寸 *forceGetViewSize*
>    - ListView中提前测量View尺寸(注释萌萌哒) *measureView*

> - [SP相关][sp.md]→[SPUtils.java][sp.java]
>    - SP中写入String类型value *putString*
>    - SP中读取String *getString*
>    - SP中写入int类型value *putInt*
>	 - SP中读取int *getInt*
>    - SP中写入long类型value *putLong*
>    - SP中读取long *getLong*
>    - SP中写入float类型value *putFloat*
>	 - SP中读取float *getFloat*
>    - SP中写入boolean类型value *putBoolean*
>	 - SP中读取boolean *getBoolean*

> -	[时间相关][time.md]→[TimeUtils.java][time.java]
>	 - 将时间戳转为时间字符串 *milliseconds2String*
>	 - 将时间字符串转为时间戳 *string2Milliseconds*
>	 - 将时间字符串转为Date类型 *string2Date*
>	 - 将Date类型转为时间字符串 *date2String*
>	 - 将Date类型转为时间戳 *date2Milliseconds*
>	 - 将时间戳转为Date类型 *milliseconds2Date*
>	 - 毫秒时间戳单位转换（单位：unit） *milliseconds2Unit*
>	 - 获取两个时间差（单位：unit） *getIntervalTime*
>	 - 获取当前时间 *getCurTimeMills* *getCurTimeString* *getCurTimeDate*
>	 - 获取与当前时间的差（单位：unit） *getIntervalByNow*
>	 - 判断闰年 *isLeapYear*

> - [未归类][unclassified.md]→[UnclassifiedUtils.java][unclassified.java]
>    - 获取服务是否开启 *isRunningService*
> - [更新Log][update_log.md]

***
  
**做这份整理只是想把它作为Android的一本小字典，当遇到一些琐碎问题时，不用再面向百度或者谷歌查询API的使用，费时费力，这里有的话，大家尽管撸走。希望它能逐日壮大起来，期待你的Star和完善，用途的话大家想把它们整理成工具类或者什么的话都可以，之后我也会封装工具类并分享之，但本篇只是提供查阅，毕竟看md比看类文件要爽多了，其中好多代码我也是各种搜刮来的，也要谢谢各位的总结，大部分代码已验证过可行，如有错误，请及时告之，开设QQ群提供讨论，群号：74721490**  

### Download
***
Gradle:(还没过审核)
``` groovy
compile 'com.blankj:utilcode:1.0'
```

### Proguard
***
``` xml
-keep class com.blankj.utilcode.** { *; }
-keepclassmembers class com.blankj.utilcode.** { *; }
-dontwarn com.blankj.utilcode.**
```

### License
***
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



[app.md]: https://github.com/Blankj/AndroidUtilCode/blob/master/md/about_app.md
[app.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/AppUtils.java
[device.md]: https://github.com/Blankj/AndroidUtilCode/blob/master/md/about_device.md
[device.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/DeviceUtils.java
[encode.md]: https://github.com/Blankj/AndroidUtilCode/blob/master/md/about_encode.md
[encode.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/EncodeUtils.java
[encrypt.md]: https://github.com/Blankj/AndroidUtilCode/blob/master/md/about_encrypt.md
[encrypt.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/EncryptUtils.java
[keyboard.md]: https://github.com/Blankj/AndroidUtilCode/blob/master/md/about_keyboard.md
[keyboard.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/KeyboardUtils.java
[network.md]: https://github.com/Blankj/AndroidUtilCode/blob/master/md/about_network.md
[network.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/NetworkUtils.java
[phone.md]: https://github.com/Blankj/AndroidUtilCode/blob/master/md/about_phone.md
[phone.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/PhonekUtils.java
[regular.md]: https://github.com/Blankj/AndroidUtilCode/blob/master/md/about_regular.md
[regular.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/RegularUtils.java
[screen.md]: https://github.com/Blankj/AndroidUtilCode/blob/master/md/about_screen.md
[screen.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ScreenUtils.java
[shell.md]: https://github.com/Blankj/AndroidUtilCode/blob/master/md/about_shell.md
[shell.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ShellUtils.java
[size.md]: https://github.com/Blankj/AndroidUtilCode/blob/master/md/about_size.md
[size.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/SizeUtils.java
[sp.md]: https://github.com/Blankj/AndroidUtilCode/blob/master/md/about_sp.md
[sp.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/SPUtils.java
[time.md]: https://github.com/Blankj/AndroidUtilCode/blob/master/md/about_time.md
[time.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/TimeUtils.java
[unclassified.md]: https://github.com/Blankj/AndroidUtilCode/blob/master/md/unclassified.md
[unclassified.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/UnclassifiedUtils.java
[update_log.md]: https://github.com/Blankj/AndroidUtilCode/blob/master/md/update_log.md

