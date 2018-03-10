## Download

Gradle:
```groovy
compile 'com.blankj:utilcode:1.13.3'
```


## How to use

```
// init it in the function of onCreate in ur Application
Utils.init(application);
```


## Proguard

```
-keep class com.blankj.utilcode.** { *; }
-keepclassmembers class com.blankj.utilcode.** { *; }
-dontwarn com.blankj.utilcode.**
```


## APIs

* ### About Activity -> [ActivityUtils.java][activity.java] -> [Demo][activity.demo]
```
isActivityExists
startActivity
startActivities
startHomeActivity
getActivityList
getLauncherActivity
getTopActivity
isActivityExistsInStack
finishActivity
finishToActivity
finishOtherActivities
finishAllActivities
finishAllActivitiesExceptNewest
```

* ### About App -> [AppUtils.java][app.java] -> [Demo][app.demo]
```
installApp
installAppSilent
uninstallApp
uninstallAppSilent
isAppInstalled
isAppRoot
isAppDebug
isAppSystem
isAppForeground
launchApp
launchAppDetailsSettings
exitApp
getAppIcon
getAppPackageName
getAppName
getAppPath
getAppVersionName
getAppVersionCode
getAppSignature
getAppSignatureSHA1
getAppInfo
getAppsInfo
```

* ### About Bar -> [BarUtils.java][bar.java] -> [Demo][bar.demo]
```
getStatusBarHeight
setStatusBarVisibility
isStatusBarVisible
setStatusBarLightMode
addMarginTopEqualStatusBarHeight
subtractMarginTopEqualStatusBarHeight
setStatusBarColor
setStatusBarAlpha
setStatusBarColor4Drawer
setStatusBarAlpha4Drawer
getActionBarHeight
setNotificationBarVisibility
getNavBarHeight
setNavBarVisibility
setNavBarImmersive
isNavBarVisible
```

* ### About Cache -> [CacheUtils.java][cache.java] -> [Test][cache.test]
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

* ### About Clean -> [CleanUtils.java][clean.java] -> [Demo][clean.demo]
```
cleanInternalCache
cleanInternalFiles
cleanInternalDbs
cleanInternalDbByName
cleanInternalSp
cleanExternalCache
cleanCustomDir
```

* ### About Close -> [CloseUtils.java][close.java]
```
closeIO
closeIOQuietly
```

* ### About Convert -> [ConvertUtils.java][convert.java] -> [Test][convert.test]
```
bytes2Bits, bits2Bytes
bytes2Chars, chars2Bytes
bytes2HexString, hexString2Bytes
memorySize2Byte, byte2MemorySize
byte2FitMemorySize
timeSpan2Millis, millis2TimeSpan
millis2FitTimeSpan
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

* ### About Crash -> [CrashUtils.java][crash.java]
```
init
```

* ### About Device -> [DeviceUtils.java][device.java] -> [Demo][device.demo]
```
isDeviceRooted
getSDKVersionName
getSDKVersionCode
getAndroidID
getMacAddress
getManufacturer
getModel
shutdown
reboot
reboot2Recovery
reboot2Bootloader
```

* ### About Encode -> [EncodeUtils.java][encode.java] -> [Test][encode.test]
```
urlEncode
urlDecode
base64Encode
base64Encode2String
base64Decode
htmlEncode
htmlDecode
```

* ### About Encrypt -> [EncryptUtils.java][encrypt.java] -> [Test][encrypt.test]
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

* ### About FileIO -> [FileIOUtils.java][fileio.java] -> [Test][fileio.test]
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
setBufferSize
```

* ### About File -> [FileUtils.java][file.java] -> [Test][file.test]
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
deleteAllInDir
deleteFilesInDir
deleteFilesInDirWithFilter
listFilesInDir
listFilesInDirWithFilter
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

* ### About Fragment -> [FragmentUtils.java][fragment.java] -> [Demo][fragment.demo]
```
add
show
hide
showHide
replace
pop
popTo
popAll
remove
removeTo
removeAll
getTop
getTopInStack
getTopShow
getTopShowInStack
getFragments
getFragmentsInStack
getAllFragments
getAllFragmentsInStack
findFragment
dispatchBackPress
setBackgroundColor
setBackgroundResource
setBackground
```

* ### About Image -> [ImageUtils.java][image.java] -> [Demo][image.demo]
```
bitmap2Bytes, bytes2Bitmap
drawable2Bitmap, bitmap2Drawable
drawable2Bytes, bytes2Drawable
view2Bitmap
getBitmap
scale
clip
skew
rotate
getRotateDegree
toRound
toRoundCorner
addCornerBorder
addCircleBorder
addReflection
addTextWatermark
addImageWatermark
toAlpha
toGray
fastBlur
renderScriptBlur
stackBlur
save
isImage
getImageType
compressByScale
compressByQuality
compressBySampleSize
```

* ### About Intent -> [IntentUtils.java][intent.java]
```
getInstallAppIntent
getUninstallAppIntent
getLaunchAppIntent
getLaunchAppDetailsSettingsIntent
getShareTextIntent
getShareImageIntent
getComponentIntent
getShutdownIntent
getCaptureIntent
```

* ### About Keyboard -> [KeyboardUtils.java][keyboard.java] -> [Demo][keyboard.demo]
```
showSoftInput
hideSoftInput
toggleSoftInput
isSoftInputVisible
registerSoftInputChangedListener
fixSoftInputLeaks
clickBlankArea2HideSoftInput
```

* ### About Log -> [LogUtils.java][log.java] -> [Demo][log.demo]
```
getConfig
Config.setLogSwitch
Config.setConsoleSwitch
Config.setGlobalTag
Config.setLogHeadSwitch
Config.setLog2FileSwitch
Config.setDir
Config.setFilePrefix
Config.setBorderSwitch
Config.setConsoleFilter
Config.setFileFilter
Config.setStackDeep
v
vTag
d
dTag
i
iTag
w
wTag
e
eTag
a
aTag
file
json
xml
```

* ### About Network -> [NetworkUtils.java][network.java] -> [Demo][network.demo]
```
openWirelessSettings
isConnected
isAvailableByPing
getMobileDataEnabled
setMobileDataEnabled
isMobileData
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

* ### About Object -> [ObjectUtils.java][object.java] -> [Test][object.test]
```
isEmpty
isNotEmpty
equals
requireNonNull
getOrDefault
hashCode
```

* ### About Permission -> [PermissionUtils.java][permission.java] -> [Demo][permission.demo]
```
getPermissions
isGranted
launchAppDetailsSettings
permission
rationale
callback
theme
request
```

* ### About Phone -> [PhoneUtils.java][phone.java] -> [Demo][phone.demo]
```
isPhone
getDeviceId
getIMEI
getMEID
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
```

* ### About Process -> [ProcessUtils.java][process.java] -> [Demo][process.demo]
```
getForegroundProcessName
killAllBackgroundProcesses
killBackgroundProcesses
```

* ### About Reflect -> [ReflectUtils.java][reflect.java] -> [Test][reflect.test]
```
reflect
newInstance
field
method
get
```

* ### About Regex -> [RegexUtils.java][regex.java] -> [Test][regex.test]
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

* ### About Screen -> [ScreenUtils.java][screen.java] -> [Demo][screen.demo]
```
getScreenWidth
getScreenHeight
getScreenDensity
getScreenDensityDpi
setFullScreen
setLandscape
setPortrait
isLandscape
isPortrait
getScreenRotation
screenShot
isScreenLock
setSleepDuration
getSleepDuration
isTablet
```

* ### About SDCard -> [SDCardUtils.java][sdcard.java] -> [Demo][sdcard.demo]
```
isSDCardEnable
getSDCardPaths
```

* ### About Service -> [ServiceUtils.java][service.java]
```
getAllRunningServices
startService
stopService
bindService
unbindService
isServiceRunning
```

* ### About Shell -> [ShellUtils.java][shell.java]
```
execCmd
```

* ### About Size -> [SizeUtils.java][size.java]
```
dp2px, px2dp
sp2px, px2sp
applyDimension
forceGetViewSize
measureView
getMeasuredWidth
getMeasuredHeight
```

* ### About Snackbar -> [SnackbarUtils.java][snackbar.java] -> [Demo][snackbar.demo]
```
with
setMessage
setMessageColor
setBgColor
setBgResource
setDuration
setAction
setBottomMargin
show
showSuccess
showWarning
showError
dismiss
getView
addView
```

* ### About Span -> [SpanUtils.java][span.java] -> [Demo][span.demo]
```
setFlag
setForegroundColor
setBackgroundColor
setLineHeight
setQuoteColor
setLeadingMargin
setBullet
setIconMargin
setFontSize
setFontProportion
setFontXProportion
setStrikethrough
setUnderline
setSuperscript
setSubscript
setBold
setItalic
setBoldItalic
setFontFamily
setTypeface
setAlign
setClickSpan
setUrl
setBlur
setShader
setShadow
setSpans
append
appendLine
appendImage
appendSpace
create
```

* ### About SP -> [SPUtils.java][sp.java] -> [Demo][sp.demo]
```
getInstance
put
getString
getInt
getLong
getFloat
getBoolean
getAll
contains
remove
clear
```

* ### About String -> [StringUtils.java][string.java] -> [Test][string.test]
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

* ### About Time -> [TimeUtils.java][time.java] -> [Test][time.test]
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

* ### About Toast -> [ToastUtils.java][toast.java] -> [Demo][toast.demo]
```
setGravity
setBgColor
setBgResource
setMessageColor
showShort
showLong
showCustomShort
showCustomLong
cancel
```

* ### About Zip -> [ZipUtils.java][zip.java] -> [Test][zip.test]
```
zipFiles
zipFile
unzipFile
unzipFileByKeyword
getFilesPath
getComments
```



[activity.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/ActivityUtils.java
[activity.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/feature/core/activity/ActivityActivity.java

[app.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/AppUtils.java
[app.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/feature/core/app/AppActivity.java

[bar.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/BarUtils.java
[bar.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/feature/core/bar/BarActivity.java

[cache.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/CacheUtils.java
[cache.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/util/CacheUtilsTest.java

[clean.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/CleanUtils.java
[clean.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/feature/core/clean/CleanActivity.java

[close.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/CloseUtils.java

[convert.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/ConvertUtils.java
[convert.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/util/ConvertUtilsTest.java

[crash.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/CrashUtils.java

[device.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/DeviceUtils.java
[device.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/feature/core/device/DeviceActivity.java

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
[fragment.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/feature/core/fragment/FragmentActivity.java

[image.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/ImageUtils.java
[image.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/feature/core/image/ImageActivity.java

[intent.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/IntentUtils.java

[keyboard.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/KeyboardUtils.java
[keyboard.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/feature/core/keyboard/KeyboardActivity.java

[log.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/LogUtils.java
[log.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/feature/core/log/LogActivity.java

[network.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/NetworkUtils.java
[network.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/feature/core/network/NetworkActivity.java

[object.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/ObjectUtils.java
[object.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/util/ObjectUtilsTest.java

[permission.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/PermissionUtils.java
[permission.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/feature/core/permission/PermissionActivity.java

[phone.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/PhoneUtils.java
[phone.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/feature/core/phone/PhoneActivity.java

[process.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/ProcessUtils.java
[process.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/feature/core/process/ProcessActivity.java

[reflect.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/ReflectUtils.java
[reflect.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/util/reflect/ReflectUtilsTest.java

[regex.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/RegexUtils.java
[regex.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/util/RegexUtilsTest.java

[screen.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/ScreenUtils.java
[screen.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/feature/core/screen/ScreenActivity.java

[sdcard.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/SDCardUtils.java
[sdcard.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/feature/core/sdcard/SDCardActivity.java

[service.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/ServiceUtils.java

[shell.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/ShellUtils.java

[size.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/SizeUtils.java

[snackbar.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/SnackbarUtils.java
[snackbar.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/feature/core/snackbar/SnackbarActivity.java

[span.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/SpanUtils.java
[span.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/feature/core/span/SpanActivity.java

[sp.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/SPUtils.java
[sp.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/feature/core/sp/SPActivity.java

[string.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/StringUtils.java
[string.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/util/StringUtilsTest.java

[time.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/TimeUtils.java
[time.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/util/TimeUtilsTest.java

[toast.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/ToastUtils.java
[toast.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/feature/core/toast/ToastActivity.java

[zip.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/ZipUtils.java
[zip.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/util/ZipUtilsTest.java
