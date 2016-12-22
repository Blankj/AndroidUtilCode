## Android developers should collect the following utils
***

**[README of Chinese][readme-cn.md]**

***
Directory is shown below:

> - **About Activity→[ActivityUtils.java][activity.java]→[Demo][activity.demo]**
 ```
isActivityExists
launchActivity
getLauncherActivity: 获取入口activity
 ```

> - **About App→[AppUtils.java][app.java]→[Demo][app.demo]**
 ```
isInstallApp
installApp
installAppSilent
uninstallApp
uninstallAppSilent
isAppRoot
launchApp
closeApp
getAppPackageName
getAppDetailsSettings: 获取App具体设置
getAppName
getAppIcon
getAppPath
getAppVersionName
getAppVersionCode
isSystemApp
isAppDebug
getAppSignature
getAppSignatureSHA1
isAppForeground
getForegroundApp
getAppInfo
getAppsInfo
cleanAppData
 ```

> - **About Bar→[BarUtils.java][bar.java]**
 ```
setTransparentStatusBar: 设置透明状态栏（api大于19方可使用）
hideStatusBar
getStatusBarHeight
isStatusBarExists
getActionBarHeight
showNotificationBar
hideNotificationBar
 ```

> - **About Clean→[CleanUtils.java][clean.java]→[Demo][clean.demo]**
 ```
cleanInternalCache
cleanInternalFiles
cleanInternalDbs
cleanInternalDbByName: 根据名称清除数据库
cleanInternalSP
cleanExternalCache
cleanCustomCache
 ```

> - **About Clipboard→[ClipboardUtils.java][clipboard.java]**
 ```
copyText
getText
copyUri
getUri
copyIntent: 复制意图到剪贴板
getIntent
 ```

> - **About Close→[CloseUtils.java][close.java]**
 ```
closeIO
closeIOQuietly: 安静关闭IO
 ```

> - **About Const→[ConstUtils.java][const.java]**
 ```
MemoryConst: 存储相关常量
TimeConst
RegexConst
 ```

> - **About Convert→[ConvertUtils.java][convert.java]→[Test][convert.test]**
 ```
bytes2HexString, hexString2Bytes
chars2Bytes, bytes2Chars
memorySize2Byte, byte2MemorySize
byte2FitMemorySize
timeSpan2Millis, millis2TimeSpan
millis2FitTimeSpan
bytes2Bits, bits2Bytes
input2OutputStream, output2InputStream
inputStream2Bytes, bytes2InputStream
outputStream2Bytes, bytes2OutputStream
inputStream2String, string2InputStream
outputStream2String, string2OutputStream: outputStream与string按编码互转
bitmap2Bytes, bytes2Bitmap
drawable2Bitmap, bitmap2Drawable
drawable2Bytes, bytes2Drawable
view2Bitmap
dp2px, px2dp
sp2px, px2sp
 ```

> - **About Crash→[CrashUtils.java][crash.java]**
 ```
getInstance: 获取单例
init
 ```

> - **About Device→[DeviceUtils.java][device.java]→[Demo][device.demo]**
 ```
isDeviceRoot
getSDKVersion
getAndroidID
getMacAddress
getManufacturer
getModel
shutdown
reboot
reboot2Recovery
reboot2Bootloader: 重启到bootloader
 ```

> - **About Empty→[EmptyUtils.java][empty.java]→[Test][empty.test]**
 ```
isEmpty
isNotEmpty: 判断对象是否非空
 ```

> - **About Encode→[EncodeUtils.java][encode.java]→[Test][encode.test]**
 ```
urlEncode
urlDecode
base64Encode
base64Encode2String: Base64编码
base64Decode
base64UrlSafeEncode: Base64URL安全编码
htmlEncode
htmlDecode
 ```

> - **About Encrypt→[EncryptUtils.java][encrypt.java]→[Test][encrypt.test]**
 ```
encryptMD2, encryptMD2ToString
encryptMD5, encryptMD5ToString
encryptMD5File, encryptMD5File2String
encryptSHA1, encryptSHA1ToString
encryptSHA224, encryptSHA224ToString
encryptSHA256, encryptSHA256ToString
encryptSHA384, encryptSHA384ToString
encryptSHA512, encryptSHA512ToString
encryptHmacMD5, encryptHmacMD5ToString
encryptHmacSHA1, encryptHmacSHA1ToString
encryptHmacSHA224, encryptHmacSHA224ToString
encryptHmacSHA256, encryptHmacSHA256ToString
encryptHmacSHA384, encryptHmacSHA384ToString
encryptHmacSHA512, encryptHmacSHA512ToString
encryptDES, encryptDES2HexString, encryptDES2Base64
decryptDES, decryptHexStringDES, decryptBase64DES
encrypt3DES, encrypt3DES2HexString, encrypt3DES2Base64: 3DES加密
decrypt3DES, decryptHexString3DES, decryptBase64_3DES
encryptAES, encryptAES2HexString, encryptAES2Base64
decryptAES, decryptHexStringAES, decryptBase64AES
 ```

> - **About File→[FileUtils.java][file.java]→[Test][file.test]**
 ```
getFileByPath
isFileExists
rename
isDir
isFile
createOrExistsDir
createOrExistsFile
createFileByDeleteOldFile: 判断文件是否存在，存在则在创建之前删除
copyDir
copyFile
moveDir
moveFile
deleteDir
deleteFile
listFilesInDir
listFilesInDir
listFilesInDirWithFilter
listFilesInDirWithFilter
listFilesInDirWithFilter
listFilesInDirWithFilter
searchFileInDir
writeFileFromIS
writeFileFromString
readFile2List
readFile2String
readFile2Bytes
getFileLastModified
getFileCharsetSimple
getFileLines
getDirSize
getFileSize
getDirLength
getFileLength
getFileMD5
getFileMD5ToString
getDirName
getFileName
getFileNameNoExtension
getFileExtension
 ```

> - **About Handler→[HandlerUtils.java][handler.java]→[Demo][handler.demo]**
 ```
HandlerHolder: 使用必读
 ```

> - **About Image→[ImageUtils.java][image.java]→[Demo][image.demo]**
 ```
bitmap2Bytes, bytes2Bitmap
drawable2Bitmap, bitmap2Drawable: drawable与bitmap互转
drawable2Bytes, bytes2Drawable
getBitmap
scale
clip
skew
rotate
getRotateDegree
toRound
toRoundCorner
fastBlur
renderScriptBlur
stackBlur
addFrame
addReflection
addTextWatermark
addImageWatermark
toAlpha
toGray
save
isImage
getImageType
compressByScale
compressByQuality
compressBySampleSize
 ```

> - **About Intent→[IntentUtils.java][intent.java]**
 ```
getInstallAppIntent
getUninstallAppIntent
getLaunchAppIntent
getAppDetailsSettingsIntent: 获取App具体设置的意图
getShareTextIntent
getShareImageIntent
getComponentIntent
getShutdownIntent
getCaptureIntent
 ```

> - **About Keyboard→[KeyboardUtils.java][keyboard.java]→[Demo][keyboard.demo]**
 ```
hideSoftInput
clickBlankArea2HideSoftInput: 点击屏幕空白区域隐藏软键盘
showSoftInput
toggleSoftInput
 ```

> - **About Location→[LocationUtils.java][location.java]→[Demo][location.demo]**
 ```
isGpsEnabled
isLocationEnabled: 判断定位是否可用
openGpsSettings
register
unregister
getAddress
getCountryName
getLocality
getStreet
 ```

> - **About Log→[LogUtils.java][log.java]→[Test][log.test]**
 ```
init
getBuilder: 获取LogUtils建造者
v
d
i
w
e
 ```

> - **About Network→[NetworkUtils.java][network.java]→[Demo][network.demo]**
 ```
openWirelessSettings
isConnected
isAvailableByPing
getDataEnabled
setDataEnabled
is4G
getWifiEnabled
setWifiEnabled
isWifiConnected
isWifiAvailable
getNetworkOperatorName: 获取移动网络运营商名称
getNetworkType
getIPAddress
getDomainAddress
 ```

> - **About Phone→[PhoneUtils.java][phone.java]→[Demo][phone.demo]**
 ```
isPhone
getIMEI
getIMSI
getPhoneType
isSimCardReady
getSimOperatorName
getSimOperatorByMnc: 获取Sim卡运营商名称
getPhoneStatus
dial
call
sendSms
sendSmsSilent
getAllContactInfo
getContactNum
getAllSMS
 ```

> - **About Pinyin→[PinyinUtils.java][pinyin.java]→[Test][pinyin.test]**
 ```
ccs2Pinyin
ccs2Pinyin
getPinyinFirstLetter
getPinyinFirstLetters: 获取所有汉字的首字母
getSurnamePinyin
getSurnameFirstLetter: 根据名字获取姓氏的首字母
 ```

> - **About Process→[ProcessUtils.java][process.java]→[Demo][process.demo]**
 ```
getForegroundProcessName
killAllBackgroundProcesses: 杀死所有的后台服务进程
killBackgroundProcesses
 ```

> - **About Regex→[RegexUtils.java][regex.java]→[Test][regex.test]**
 ```
isMobileSimple
isMobileExact
isTel
isIDCard15
isIDCard18
isEmail
isURL
isZh
isUsername
isDate
isIP
isMatch
getMatches
getSplits
getReplaceFirst: 替换正则匹配的第一部分
getReplaceAll
 ```

> - **About Screen→[ScreenUtils.java][screen.java]**
 ```
getScreenWidth
getScreenHeight
setLandscape
setPortrait
isLandscape
isPortrait
getScreenRotation
captureWithStatusBar
captureWithoutStatusBar: 获取当前屏幕截图，不包含状态栏
isScreenLock
 ```

> - **About SDCard→[SDCardUtils.java][sdcard.java]→[Demo][sdcard.demo]**
 ```
isSDCardEnable: 判断SD卡是否可用
getSDCardPath
getDataPath
getFreeSpace
getSDCardInfo
 ```

> - **About Service→[ServiceUtils.java][service.java]**
 ```
getAllRunningService: 获取所有运行的服务
startService
stopService
bindService
unbindService
isServiceRunning
 ```

> - **About Shell→[ShellUtils.java][shell.java]**
 ```
execCmd: 是否是在root下执行命令
 ```

> - **About Size→[SizeUtils.java][size.java]**
 ```
dp2px, px2dp
sp2px, px2sp
applyDimension
forceGetViewSize
measureView
getMeasuredWidth
getMeasuredHeight: 获取测量视图高度
 ```

> - **About Snackbar→[SnackbarUtils.java][snackbar.java]→[Demo][snackbar.demo]**
 ```
showShortSnackbar
showLongSnackbar
showIndefiniteSnackbar: 显示自定义时长snackbar
addView
dismissSnackbar
 ```

> - **About SpannableString→[SpannableStringUtils.java][spannable.java]→[Demo][spannable.demo]**
 ```
getBuilder
setFlag
setForegroundColor: 设置前景色
setBackgroundColor: 设置背景色
setQuoteColor
setLeadingMargin
setBullet
setProportion
setXProportion
setStrikethrough
setUnderline
setSuperscript
setSubscript
setBold
setItalic
setBoldItalic
setFontFamily
setAlign
setBitmap
setDrawable
setUri
setResourceId
setClickSpan
setUrl
setBlur
append
create
 ```

> - **About SP→[SPUtils.java][sp.java]→[Test][sp.test]**
 ```
SPUtils
putString
getString
putInt
getInt
putLong
getLong
putFloat
getFloat
putBoolean: SP中写入boolean类型value
getBoolean: SP中读取boolean
getAll
remove
contains
clear
 ```

> - **About String→[StringUtils.java][string.java]→[Test][string.test]**
 ```
isEmpty
isSpace
equals
equalsIgnoreCase: 判断两字符串忽略大小写是否相等
null2Length0
length
upperFirstLetter: 首字母大写
lowerFirstLetter: 首字母小写
reverse
toDBC
toSBC
 ```

> - **About ThreadPool→[ThreadPoolUtils.java][thread_pool.java]**
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
scheduleWithFixedDelay: 延迟并以固定休息时间循环执行命令
 ```

> - **About Time→[TimeUtils.java][time.java]→[Test][time.test]**
 ```
millis2String
string2Millis
string2Date
date2String
date2Millis
millis2Date
getTimeSpan
getFitTimeSpan
getNowTimeMills
getNowTimeString
getNowTimeDate
getTimeSpanByNow
getFitTimeSpanByNow
getFriendlyTimeSpanByNow: 获取友好型与当前时间的差
isSameDay
isLeapYear
getWeek, getWeekIndex
getWeekOfMonth
getWeekOfYear
getChineseZodiac
getZodiac
 ```

> - **About Toast→[ToastUtils.java][toast.java]→[Demo][toast.demo]**
 ```
init
showShortToastSafe: 安全地显示短时吐司
showLongToastSafe
showShortToast
showLongToast
cancelToast
 ```

> - **About Zip→[ZipUtils.java][zip.java]→[Test][zip.test]**
 ```
zipFiles
zipFile
unzipFiles
unzipFile
unzipFileByKeyword: 解压带有关键字的文件
getFilesPath
getComments
getEntries
 ```

> - **更新Log→[update_log.md][update_log.md]**

***

### About
***

**I'm so sorry for that the code is annotated with Chinese.**

### Download
***

Gradle:
``` groovy
compile 'com.blankj:utilcode:1.3.5'
```

### How to use
***

```
Utils.init(context);
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

[update_log.md]: https://github.com/Blankj/AndroidUtilCode/blob/master/update_log.md

[readme.md]: https://github.com/Blankj/AndroidUtilCode
[readme-cn.md]: https://github.com/Blankj/AndroidUtilCode/blob/master/README-CN.md

[activity.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ActivityUtils.java
[activity.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/activities/ActivityActivity.java

[app.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/AppUtils.java
[app.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/activities/AppActivity.java

[bar.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/BarUtils.java

[clean.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/CleanUtils.java
[clean.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/activities/CleanActivity.java

[clipboard.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ClipboardUtils.java

[close.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/CloseUtils.java

[const.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ConstUtils.java

[convert.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ConvertUtils.java
[convert.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/ConvertUtilsTest.java

[crash.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/CrashUtils.java

[device.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/DeviceUtils.java
[device.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/activities/DeviceActivity.java

[empty.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/EmptyUtils.java
[empty.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/EmptyUtilsTest.java

[encode.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/EncodeUtils.java
[encode.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/EncodeUtilsTest.java

[encrypt.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/EncryptUtils.java
[encrypt.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/EncryptUtilsTest.java

[file.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/FileUtils.java
[file.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/FileUtilsTest.java

[handler.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/HandlerUtils.java
[handler.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/activities/HandlerActivity.java

[image.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ImageUtils.java
[image.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/activities/ImageActivity.java

[intent.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/IntentUtils.java

[keyboard.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/KeyboardUtils.java
[keyboard.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/activities/KeyboardActivity.java

[location.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/LocationUtils.java
[location.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/activities/LocationActivity.java

[log.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/LogUtils.java
[log.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/LogUtilsTest.java

[network.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/NetworkUtils.java
[network.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/activities/NetworkActivity.java

[phone.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/PhoneUtils.java
[phone.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/activities/PhoneActivity.java

[pinyin.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/PinyinUtils.java
[pinyin.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/PinyinUtilsTest.java

[process.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ProcessUtils.java
[process.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/activities/ProcessActivity.java

[regex.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/RegexUtils.java
[regex.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/RegexUtilsTest.java

[screen.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ScreenUtils.java

[sdcard.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/SDCardUtils.java
[sdcard.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/activities/SDCardActivity.java

[service.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ServiceUtils.java

[shell.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ShellUtils.java

[size.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/SizeUtils.java

[snackbar.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/SnackbarUtils.java
[snackbar.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/activities/SnackbarActivity.java

[spannable.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/SpannableStringUtils.java
[spannable.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/activities/SpannableActivity.java

[sp.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/SPUtils.java
[sp.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/SPUtilsTest.java

[string.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/StringUtils.java
[string.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/StringUtilsTest.java

[thread_pool.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ThreadPoolUtils.java

[time.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/TimeUtils.java
[time.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/TimeUtilsTest.java

[toast.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ToastUtils.java
[toast.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/activities/ToastActivity.java

[zip.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ZipUtils.java
[zip.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/ZipUtilsTest.java

[group]: http://www.jianshu.com/p/8938015df951
[weibo]: http://weibo.com/blankcmj
