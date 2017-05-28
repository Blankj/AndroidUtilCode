# :fire: Android developers should collect the following utils

[![auc][aucsvg]][auc] [![api][apisvg]][api] [![build][buildsvg]][build] [![License][licensesvg]][license]

## [README of Chinese][readme-cn.md]

## API

* ### About Activity→[ActivityUtils.java][activity.java]→[Demo][activity.demo]
```
isActivityExists
startActivity
getLauncherActivity
getTopActivity
```

* ### About App→[AppUtils.java][app.java]→[Demo][app.demo]
```
isInstallApp
installApp
installAppSilent
uninstallApp
uninstallAppSilent
isAppRoot
launchApp
getAppPackageName
getAppDetailsSettings
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

* ### About Bar→[BarUtils.java][bar.java]
```
setTransparentStatusBar
hideStatusBar
getStatusBarHeight
isStatusBarExists
getActionBarHeight
showNotificationBar
hideNotificationBar
```

* ### About Cache→[CacheUtils.java][cache.java]→[Test][cache.test]
```
getInstance
put
getBytes
getString
getJSONObject
getJSONArray
getBitmap
getDrawable
getParcelable
getSerializable
getCacheSize
getCacheCount
remove
clear
```

* ### About Clean→[CleanUtils.java][clean.java]→[Demo][clean.demo]
```
cleanInternalCache
cleanInternalFiles
cleanInternalDbs
cleanInternalDbByName
cleanInternalSP
cleanExternalCache
cleanCustomCache
```

* ### About Clipboard→[ClipboardUtils.java][clipboard.java]
```
copyText
getText
copyUri
getUri
copyIntent
getIntent
```

* ### About Close→[CloseUtils.java][close.java]
```
closeIO
closeIOQuietly
```

* ### About Convert→[ConvertUtils.java][convert.java]→[Test][convert.test]
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
outputStream2String, string2OutputStream
bitmap2Bytes, bytes2Bitmap
drawable2Bitmap, bitmap2Drawable
drawable2Bytes, bytes2Drawable
view2Bitmap
dp2px, px2dp
sp2px, px2sp
```

* ### About Crash→[CrashUtils.java][crash.java]
```
getInstance
init
```

* ### About Device→[DeviceUtils.java][device.java]→[Demo][device.demo]
```
isDeviceRooted
getSDKVersion
getAndroidID
getMacAddress
getManufacturer
getModel
shutdown
reboot
reboot2Recovery
reboot2Bootloader
```

* ### About Empty→[EmptyUtils.java][empty.java]→[Test][empty.test]
```
isEmpty
isNotEmpty
```

* ### About Encode→[EncodeUtils.java][encode.java]→[Test][encode.test]
```
urlEncode
urlDecode
base64Encode
base64Encode2String
base64Decode
base64UrlSafeEncode
htmlEncode
htmlDecode
```

* ### About Encrypt→[EncryptUtils.java][encrypt.java]→[Test][encrypt.test]
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
encrypt3DES, encrypt3DES2HexString, encrypt3DES2Base64
decrypt3DES, decryptHexString3DES, decryptBase64_3DES
encryptAES, encryptAES2HexString, encryptAES2Base64
decryptAES, decryptHexStringAES, decryptBase64AES
```

* ### About FileIO→[FileIOUtils.java][fileio.java]→[Test][fileio.test]
```
writeFileFromIS
writeFileFromBytesByStream
writeFileFromBytesByChannel
writeFileFromBytesByMap
writeFileFromString
readFile2List
readFile2String
readFile2BytesByStream
readFile2BytesByChannel
readFile2BytesByMap
```

* ### About File→[FileUtils.java][file.java]→[Test][file.test]
```
getFileByPath
isFileExists
rename
isDir
isFile
createOrExistsDir
createOrExistsFile
createFileByDeleteOldFile
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

* ### About Fragment→[FragmentUtils.java][fragment.java]→[Demo][fragment.demo]
```
addFragment
hideAddFragment
addFragments
removeFragment
removeToFragment
removeFragments
removeAllFragments
replaceFragment
popFragment
popToFragment
popFragments
popAllFragments
popAddFragment
hideFragment
hideFragments
showFragment
hideShowFragment
getLastAddFragment
getLastAddFragmentInStack
getTopShowFragment
getTopShowFragmentInStack
getFragments
getFragmentsInStack
getAllFragments
getAllFragmentsInStack
getPreFragment
findFragment
dispatchBackPress
setBackgroundColor
setBackgroundResource
setBackground
```

* ### About Handler→[HandlerUtils.java][handler.java]→[Demo][handler.demo]
```
HandlerHolder
```

* ### About Image→[ImageUtils.java][image.java]→[Demo][image.demo]
```
bitmap2Bytes, bytes2Bitmap
drawable2Bitmap, bitmap2Drawable
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

* ### About Intent→[IntentUtils.java][intent.java]
```
getInstallAppIntent
getUninstallAppIntent
getLaunchAppIntent
getAppDetailsSettingsIntent
getShareTextIntent
getShareImageIntent
getComponentIntent
getShutdownIntent
getCaptureIntent
```

* ### About Keyboard→[KeyboardUtils.java][keyboard.java]→[Demo][keyboard.demo]
```
hideSoftInput
clickBlankArea2HideSoftInput
showSoftInput
toggleSoftInput
```

* ### About Location→[LocationUtils.java][location.java]→[Demo][location.demo]
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

* ### About Log→[LogUtils.java][log.java]→[Demo][log.demo]
```
Builder.setLogSwitch
Builder.setConsoleSwitch
Builder.setGlobalTag
Builder.setLogHeadSwitch
Builder.setLog2FileSwitch
Builder.setDir
Builder.setBorderSwitch
Builder.setConsoleFilter
Builder.setFileFilter
v
d
i
w
e
a
file
json
xml
```

* ### About Network→[NetworkUtils.java][network.java]→[Demo][network.demo]
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
getNetworkOperatorName
getNetworkType
getIPAddress
getDomainAddress
```

* ### About Phone→[PhoneUtils.java][phone.java]→[Demo][phone.demo]
```
isPhone
getIMEI
getIMSI
getPhoneType
isSimCardReady
getSimOperatorName
getSimOperatorByMnc
getPhoneStatus
dial
call
sendSms
sendSmsSilent
getAllContactInfo
getContactNum
getAllSMS
```

* ### About Pinyin→[PinyinUtils.java][pinyin.java]→[Test][pinyin.test]
```
ccs2Pinyin
ccs2Pinyin
getPinyinFirstLetter
getPinyinFirstLetters
getSurnamePinyin
getSurnameFirstLetter
```

* ### About Process→[ProcessUtils.java][process.java]→[Demo][process.demo]
```
getForegroundProcessName
killAllBackgroundProcesses
killBackgroundProcesses
```

* ### About Regex→[RegexUtils.java][regex.java]→[Test][regex.test]
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
getReplaceFirst
getReplaceAll
```

* ### About Screen→[ScreenUtils.java][screen.java]
```
getScreenWidth
getScreenHeight
setLandscape
setPortrait
isLandscape
isPortrait
getScreenRotation
captureWithStatusBar
captureWithoutStatusBar
isScreenLock
```

* ### About SDCard→[SDCardUtils.java][sdcard.java]→[Demo][sdcard.demo]
```
isSDCardEnable
getSDCardPath
getDataPath
getFreeSpace
getSDCardInfo
```

* ### About Service→[ServiceUtils.java][service.java]
```
getAllRunningService
startService
stopService
bindService
unbindService
isServiceRunning
```

* ### About Shell→[ShellUtils.java][shell.java]
```
execCmd
```

* ### About Size→[SizeUtils.java][size.java]
```
dp2px, px2dp
sp2px, px2sp
applyDimension
forceGetViewSize
measureView
getMeasuredWidth
getMeasuredHeight
```

* ### About Snackbar→[SnackbarUtils.java][snackbar.java]→[Demo][snackbar.demo]
```
showShort
showLong
showIndefinite
addView
dismiss
```

* ### About SpannableString→[SpannableStringUtils.java][spannable.java]→[Demo][spannable.demo]
```
Builder.setFlag
Builder.setForegroundColor
Builder.setBackgroundColor
Builder.setQuoteColor
Builder.setLeadingMargin
Builder.setMargin
Builder.setBullet
Builder.setFontSize
Builder.setFontProportion
Builder.setFontXProportion
Builder.setStrikethrough
Builder.setUnderline
Builder.setSuperscript
Builder.setSubscript
Builder.setBold
Builder.setItalic
Builder.setBoldItalic
Builder.setFontFamily
Builder.setTypeface
Builder.setAlign
Builder.setBitmap
Builder.setDrawable
Builder.setUri
Builder.setResourceId
Builder.setClickSpan
Builder.setUrl
Builder.setBlur
Builder.append
Builder.create
```

* ### About SP→[SPUtils.java][sp.java]→[Test][sp.test]
```
getInstance
put
getString
getInt
getLong
getFloat
getBoolean
getAll
remove
contains
clear
```

* ### About String→[StringUtils.java][string.java]→[Test][string.test]
```
isEmpty
isTrimEmpty
isSpace
equals
equalsIgnoreCase
null2Length0
length
upperFirstLetter
lowerFirstLetter
reverse
toDBC
toSBC
```

* ### About ThreadPool→[ThreadPoolUtils.java][thread_pool.java]
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

* ### About Time→[TimeUtils.java][time.java]→[Test][time.test]
```
millis2String
string2Millis
string2Date
date2String
date2Millis
millis2Date
getTimeSpan
getFitTimeSpan
getNowMills
getNowString
getNowDate
getTimeSpanByNow
getFitTimeSpanByNow
getFriendlyTimeSpanByNow
getMillis
getString
getDate
getMillisByNow
getStringByNow
getDateByNow
isToday
isLeapYear
getChineseWeek
getUSWeek
getWeekIndex
getWeekOfMonth
getWeekOfYear
getChineseZodiac
getZodiac
```

* ### About Toast→[ToastUtils.java][toast.java]→[Demo][toast.demo]
```
setGravity
setView
getView
showShortSafe
showLongSafe
showShort
showLong
cancel
```

* ### About Zip→[ZipUtils.java][zip.java]→[Test][zip.test]
```
zipFiles
zipFile
unzipFiles
unzipFile
unzipFileByKeyword
getFilesPath
getComments
getEntries
```

* ### About Log→[update_log.md][update_log.md]

***

## About

* [![jianshu][jianshusvg]][jianshu] [![weibo][weibosvg]][weibo]  [![Blog][blogsvg]][blog] [![QQ0Group][qq0groupsvg]][qq0group] [![QQ1Group][qq1groupsvg]][qq1group]

* **I'm so sorry for that the code is annotated with Chinese.**


## Download

Gradle:
``` groovy
compile 'com.blankj:utilcode:1.6.2'
```


## How to use

```
// init it in the function of onCreate in ur Application
Utils.init(context);
```


## Proguard

```
-keep class com.blankj.utilcode.** { *; }
-keepclassmembers class com.blankj.utilcode.** { *; }
-dontwarn com.blankj.utilcode.**
```



[aucsvg]: https://img.shields.io/badge/AndroidUtilCode-v1.6.2-brightgreen.svg
[auc]: https://github.com/Blankj/AndroidUtilCode

[apisvg]: https://img.shields.io/badge/API-15+-brightgreen.svg
[api]: https://android-arsenal.com/api?level=15

[buildsvg]: https://travis-ci.org/Blankj/AndroidUtilCode.svg?branch=master
[build]: https://travis-ci.org/Blankj/AndroidUtilCode

[licensesvg]: https://img.shields.io/badge/License-Apache--2.0-brightgreen.svg
[license]: https://github.com/Blankj/AndroidUtilCode/blob/master/LICENSE

[jianshusvg]: https://img.shields.io/badge/简书-Blankj-brightgreen.svg
[jianshu]: http://www.jianshu.com/u/46702d5c6978

[weibosvg]: https://img.shields.io/badge/weibo-__Blankj-brightgreen.svg
[weibo]: http://weibo.com/3076228982

[blogsvg]: https://img.shields.io/badge/Blog-Blankj-brightgreen.svg
[blog]: http://blankj.com

[qq0groupsvg]: https://img.shields.io/badge/QQ0群(满)-74721490-fba7f9.svg
[qq0group]: https://shang.qq.com/wpa/qunwpa?idkey=62baf2c3ec6b0863155b0c7a10c71bba2608cb0b6532fc18515835e54c69bdd3

[qq1groupsvg]: https://img.shields.io/badge/QQ1群-25206533-fba7f9.svg
[qq1group]: https://shang.qq.com/wpa/qunwpa?idkey=d906789f84484465e2736f7b524366b4c23afeda38733d5c7b10fc3f6e406e9b

[readme.md]: https://github.com/Blankj/AndroidUtilCode
[readme-cn.md]: https://github.com/Blankj/AndroidUtilCode/blob/master/README-CN.md

[activity.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/ActivityUtils.java
[activity.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/activity/ActivityActivity.java

[app.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/AppUtils.java
[app.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/activity/AppActivity.java

[bar.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/BarUtils.java

[cache.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/CacheUtils.java
[cache.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/util/CacheUtilsTest.java

[clean.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/CleanUtils.java
[clean.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/activity/CleanActivity.java

[clipboard.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/ClipboardUtils.java

[close.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/CloseUtils.java

[convert.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/ConvertUtils.java
[convert.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/util/ConvertUtilsTest.java

[crash.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/CrashUtils.java

[device.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/DeviceUtils.java
[device.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/activity/DeviceActivity.java

[empty.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/EmptyUtils.java
[empty.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/util/EmptyUtilsTest.java

[encode.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/EncodeUtils.java
[encode.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/util/EncodeUtilsTest.java

[encrypt.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/EncryptUtils.java
[encrypt.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/util/EncryptUtilsTest.java

[fileio.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/FileIOUtils.java
[fileio.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/util/FileIOUtilsTest.java

[file.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/FileUtils.java
[file.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/util/FileUtilsTest.java

[fragment.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/FragmentUtils.java
[fragment.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/activity/FragmentActivity.java

[handler.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/HandlerUtils.java
[handler.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/activity/HandlerActivity.java

[image.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/ImageUtils.java
[image.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/activity/ImageActivity.java

[intent.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/IntentUtils.java

[keyboard.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/KeyboardUtils.java
[keyboard.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/activity/KeyboardActivity.java

[location.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/LocationUtils.java
[location.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/activity/LocationActivity.java

[log.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/LogUtils.java
[log.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/activity/LogActivity.java

[network.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/NetworkUtils.java
[network.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/activity/NetworkActivity.java

[phone.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/PhoneUtils.java
[phone.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/activity/PhoneActivity.java

[pinyin.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/PinyinUtils.java
[pinyin.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/util/PinyinUtilsTest.java

[process.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/ProcessUtils.java
[process.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/activity/ProcessActivity.java

[regex.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/RegexUtils.java
[regex.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/util/RegexUtilsTest.java

[screen.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/ScreenUtils.java

[sdcard.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/SDCardUtils.java
[sdcard.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/activity/SDCardActivity.java

[service.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/ServiceUtils.java

[shell.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/ShellUtils.java

[size.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/SizeUtils.java

[snackbar.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/SnackbarUtils.java
[snackbar.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/activity/SnackbarActivity.java

[spannable.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/SpannableStringUtils.java
[spannable.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/activity/SpannableActivity.java

[sp.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/SPUtils.java
[sp.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/util/SPUtilsTest.java

[string.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/StringUtils.java
[string.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/util/StringUtilsTest.java

[thread_pool.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/ThreadPoolUtils.java

[time.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/TimeUtils.java
[time.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/util/TimeUtilsTest.java

[toast.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/ToastUtils.java
[toast.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/activity/ToastActivity.java

[zip.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/ZipUtils.java
[zip.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/util/ZipUtilsTest.java

[update_log.md]: https://github.com/Blankj/AndroidUtilCode/blob/master/update_log.md

[group]: http://www.jianshu.com/p/8938015df951
[weibo]: http://weibo.com/blankcmj
