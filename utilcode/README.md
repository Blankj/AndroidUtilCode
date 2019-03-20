## Download

Gradle:
```groovy
implementation 'com.blankj:utilcode:1.23.7'

// if u use AndroidX, use the following
implementation 'com.blankj:utilcodex:1.23.7'
```


## APIs

* ### About Activity -> [ActivityUtils.java][activity.java] -> [Demo][activity.demo]
```
getActivityByView
isActivityExists
startActivity
startActivityForResult
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

* ### About AdaptScreen -> [AdaptScreenUtils.java][adaptScreen.java] -> [Demo][adaptScreen.demo]
```
adaptWidth
adaptHeight
closeAdapt
pt2Px
px2Pt
```

* ### About AntiShake -> [AntiShakeUtils.java][antiShake.java]
```
isValid
```

* ### About App -> [AppUtils.java][app.java] -> [Demo][app.demo]
```
registerAppStatusChangedListener
unregisterAppStatusChangedListener
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
relaunchApp
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
getAppSignatureSHA256
getAppSignatureMD5
getAppInfo
getAppsInfo
getApkInfo
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
setStatusBarColor4Drawer
getActionBarHeight
setNotificationBarVisibility
getNavBarHeight
setNavBarVisibility
isNavBarVisible
setNavBarColor
getNavBarColor
isSupportNavBar
```

* ### About Brightness -> [BrightnessUtils.java][brightness.java] -> [Demo][brightness.demo]
```
isAutoBrightnessEnabled
setAutoBrightnessEnabled
getBrightness
setBrightness
setWindowBrightness
getWindowBrightness
```

* ### About Bus -> [BusUtils.java][bus.java] -> [README][bus.readme]
```
post
```

* ### About CacheDiskStatic -> [CacheDiskStaticUtils.java][cacheDiskStatic.java] -> [Test][cacheDiskStatic.test]
```
setDefaultCacheDiskUtils
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

* ### About CacheDisk -> [CacheDiskUtils.java][cacheDisk.java] -> [Test][cacheDisk.test]
```
getInstance
Instance.put
Instance.getBytes
Instance.getString
Instance.getJSONObject
Instance.getJSONArray
Instance.getBitmap
Instance.getDrawable
Instance.getParcelable
Instance.getSerializable
Instance.getCacheSize
Instance.getCacheCount
Instance.remove
Instance.clear
```

* ### About CacheDoubleStatic -> [CacheDoubleStaticUtils.java][cacheDoubleStatic.java] -> [Test][cacheDoubleStatic.test]
```
setDefaultCacheDoubleUtils
put
getBytes
getString
getJSONObject
getJSONArray
getBitmap
getDrawable
getParcelable
getSerializable
getCacheDiskSize
getCacheDiskCount
getCacheMemoryCount
remove
clear
```

* ### About CacheDouble -> [CacheDoubleUtils.java][cacheDouble.java] -> [Test][cacheDouble.test]
```
getInstance
Instance.put
Instance.getBytes
Instance.getString
Instance.getJSONObject
Instance.getJSONArray
Instance.getBitmap
Instance.getDrawable
Instance.getParcelable
Instance.getSerializable
Instance.getCacheDiskSize
Instance.getCacheDiskCount
Instance.getCacheMemoryCount
Instance.remove
Instance.clear
```

* ### About CacheMemoryStatic -> [CacheMemoryStaticUtils.java][cacheMemoryStatic.java] -> [Test][cacheMemoryStatic.test]
```
setDefaultCacheMemoryUtils
put
get
getCacheCount
remove
clear
```

* ### About CacheMemory -> [CacheMemoryUtils.java][cacheMemory.java] -> [Test][cacheMemory.test]
```
getInstance
Instance.put
Instance.get
Instance.getCacheCount
Instance.remove
Instance.clear
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

* ### About Clone -> [CloneUtils.java][clone.java] -> [Test][clone.test]
```
deepClone
```

* ### About Close -> [CloseUtils.java][close.java]
```
closeIO
closeIOQuietly
```

* ### About Color -> [ColorUtils.java][color.java]
```
getColor
setAlphaComponent
setRedComponent
setGreenComponent
setBlueComponent
string2Int
int2RgbString
int2ArgbString
getRandomColor
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
isAdbEnabled
getSDKVersionName
getSDKVersionCode
getAndroidID
getMacAddress
getManufacturer
getModel
getABIs
shutdown
reboot
reboot2Recovery
reboot2Bootloader
```

* ### About Flashlight -> [FlashlightUtils.java][flashlight.java] -> [Demo][flashlight.demo]
```
isFlashlightEnable
isFlashlightOn
setFlashlightStatus
destroy
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
encryptRSA, encryptRSA2HexString, encryptRSA2Base64
decryptRSA, decryptHexStringRSA, decryptBase64RSA
```

* ### About FileIO -> [FileIOUtils.java][fileIo.java] -> [Test][fileIo.test]
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
delete
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

* ### About Gson -> [GsonUtils.java][gson.java] -> [Test][gson.test]
```
getGson
toJson
fromJson
getListType
getSetType
getMapType
getArrayType
getType
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
getSize
```

* ### About Intent -> [IntentUtils.java][intent.java]
```
isIntentAvailable
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
unregisterSoftInputChangedListener
fixAndroidBug5497
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
Config.setSingleTagSwitch
Config.setConsoleFilter
Config.setFileFilter
Config.setStackDeep
Config.setStackOffset
Config.setSaveDays
Config.addFormatter
log
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

* ### About MetaData -> [MetaDataUtils.java][metaData.java] -> [Demo][metaData.demo]
```
getMetaDataInApp
getMetaDataInActivity
getMetaDataInService
getMetaDataInReceiver
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
getIpAddressByWifi
getGatewayByWifi
getNetMaskByWifi
getServerAddressByWifi
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

* ### About Path -> [PathUtils.java][path.java] -> [Demo][path.demo]
```
getRootPath
getDataPath
getDownloadCachePath
getInternalAppDataPath
getInternalAppCodeCacheDir
getInternalAppCachePath
getInternalAppDbsPath
getInternalAppDbPath
getInternalAppFilesPath
getInternalAppSpPath
getInternalAppNoBackupFilesPath
getExternalStoragePath
getExternalMusicPath
getExternalPodcastsPath
getExternalRingtonesPath
getExternalAlarmsPath
getExternalNotificationsPath
getExternalPicturesPath
getExternalMoviesPath
getExternalDownloadsPath
getExternalDcimPath
getExternalDocumentsPath
getExternalAppDataPath
getExternalAppCachePath
getExternalAppFilesPath
getExternalAppMusicPath
getExternalAppPodcastsPath
getExternalAppRingtonesPath
getExternalAppAlarmsPath
getExternalAppNotificationsPath
getExternalAppPicturesPath
getExternalAppMoviesPath
getExternalAppDownloadPath
getExternalAppDcimPath
getExternalAppDocumentsPath
getExternalAppObbPath
```

* ### About Permission -> [PermissionUtils.java][permission.java] -> [Demo][permission.demo]
```
getPermissions
isGranted
isGrantedWriteSettings
requestWriteSettings
isGrantedDrawOverlays
requestDrawOverlays
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
getSerial
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
isMainProcess
getCurrentProcessName
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
isIDCard18Exact
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

* ### About Resource -> [ResourceUtils.java][resource.java] -> [Demo][resource.demo]
```
copyFileFromAssets
readAssets2String
readAssets2List
copyFileFromRaw
readRaw2String
readRaw2List
```

* ### About Rom -> [RomUtils.java][rom.java] -> [Demo][rom.demo]
```
isHuawei
isVivo
isXiaomi
isOppo
isLeeco
is360
isZte
isOneplus
isNubia
isCoolpad
isLg
isGoogle
isSamsung
isMeizu
isLenovo
isSmartisan
isHtc
isSony
isGionee
isMotorola
getRomInfo
```

* ### About Screen -> [ScreenUtils.java][screen.java] -> [Demo][screen.demo]
```
getScreenWidth
getScreenHeight
getScreenDensity
getScreenDensityDpi
setFullScreen
setNonFullScreen
toggleFullScreen
isFullScreen
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
isSDCardEnableByEnvironment
getSDCardPathByEnvironment
getSDCardInfo
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
with
setFlag
setForegroundColor
setBackgroundColor
setLineHeight
setQuoteColor
setLeadingMargin
setBullet
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

* ### About SPStatic -> [SPStaticUtils.java][spStatic.java] -> [Demo][spStatic.demo]
```
setDefaultSPUtils
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

* ### About SP -> [SPUtils.java][sp.java]
```
getInstance
Instance.put
Instance.getString
Instance.getInt
Instance.getLong
Instance.getFloat
Instance.getBoolean
Instance.getAll
Instance.contains
Instance.remove
Instance.clear
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

* ### About Thread -> [ThreadUtils.java][thread.java] -> [Test][thread.test]
```
isMainThread
getFixedPool
getSinglePool
getCachedPool
getIoPool
getCpuPool
executeByFixed
executeByFixedWithDelay
executeByFixedAtFixRate
executeBySingle
executeBySingleWithDelay
executeBySingleAtFixRate
executeByCached
executeByCachedWithDelay
executeByCachedAtFixRate
executeByIo
executeByIoWithDelay
executeByIoAtFixRate
executeByCpu
executeByCpuWithDelay
executeByCpuAtFixRate
executeByCustom
executeByCustomWithDelay
executeByCustomAtFixRate
cancel
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
getValueByCalendarField
getChineseZodiac
getZodiac
```

* ### About Toast -> [ToastUtils.java][toast.java] -> [Demo][toast.demo]
```
setGravity
setBgColor
setBgResource
setMsgColor
setMsgTextSize
showShort
showLong
showCustomShort
showCustomLong
cancel
```

* ### About Uri -> [UriUtils.java][uri.java]
```
file2Uri
uri2File
```

* ### About Vibrate -> [VibrateUtils.java][vibrate.java] -> [Demo][demo.demo]
```
vibrate
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



[activity.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/ActivityUtils.java
[activity.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/activity/ActivityActivity.kt

[adaptScreen.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/AdaptScreenUtils.java
[adaptScreen.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/adaptScreen/AdaptScreenActivity.kt

[antiShake.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/AntiShakeUtils.java

[app.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/AppUtils.java
[app.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/app/AppActivity.kt

[bar.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/BarUtils.java
[bar.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/bar/BarActivity.kt

[brightness.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/BrightnessUtils.java
[brightness.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/brightness/BrightnessActivity.kt

[bus.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/BusUtils.java
[bus.readme]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/README-STATIC-BUS.md

[cacheDiskStatic.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/CacheDiskStaticUtils.java
[cacheDiskStatic.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/test/java/com/blankj/utilcode/util/CacheDiskStaticUtilsTest.java

[cacheDisk.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/CacheDiskUtils.java
[cacheDisk.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/test/java/com/blankj/utilcode/util/CacheDiskUtilsTest.java

[cacheDoubleStatic.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/CacheDoubleStaticUtils.java
[cacheDoubleStatic.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/test/java/com/blankj/utilcode/util/CacheDoubleStaticUtilsTest.java

[cacheDouble.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/CacheDoubleUtils.java
[cacheDouble.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/test/java/com/blankj/utilcode/util/CacheDoubleUtilsTest.java

[cacheMemoryStatic.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/CacheMemoryStaticUtils.java
[cacheMemoryStatic.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/test/java/com/blankj/utilcode/util/CacheMemoryStaticUtilsTest.java

[cacheMemory.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/CacheMemoryUtils.java
[cacheMemory.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/test/java/com/blankj/utilcode/util/CacheMemoryUtilsTest.java

[clean.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/CleanUtils.java
[clean.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/clean/CleanActivity.kt

[clone.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/CloneUtils.java
[clone.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/test/java/com/blankj/utilcode/util/CloneUtilsTest.java

[close.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/CloseUtils.java

[color.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/ColorUtils.java
[color.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/test/java/com/blankj/utilcode/util/ColorUtilsTest.java

[convert.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/ConvertUtils.java
[convert.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/test/java/com/blankj/utilcode/util/ConvertUtilsTest.java

[crash.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/CrashUtils.java

[device.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/DeviceUtils.java
[device.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/device/DeviceActivity.kt

[empty.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/EmptyUtils.java
[empty.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/test/java/com/blankj/utilcode/util/EmptyUtilsTest.java

[encode.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/EncodeUtils.java
[encode.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/test/java/com/blankj/utilcode/util/EncodeUtilsTest.java

[encrypt.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/EncryptUtils.java
[encrypt.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/test/java/com/blankj/utilcode/util/EncryptUtilsTest.java

[fileIo.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/FileIOUtils.java
[fileIo.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/test/java/com/blankj/utilcode/util/FileIOUtilsTest.java

[file.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/FileUtils.java
[file.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/test/java/com/blankj/utilcode/util/FileUtilsTest.java

[flashlight.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/FlashlightUtils.java
[flashlight.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/flashlight/FlashlightActivity.kt

[fragment.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/FragmentUtils.java
[fragment.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/fragment/FragmentActivity.kt

[gson.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/GsonUtils.java
[gson.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/test/java/com/blankj/utilcode/util/GsonUtilsTest.java

[image.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/ImageUtils.java
[image.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/image/ImageActivity.kt

[intent.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/IntentUtils.java

[keyboard.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/KeyboardUtils.java
[keyboard.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/keyboard/KeyboardActivity.kt

[log.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/LogUtils.java
[log.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/log/LogActivity.kt

[metaData.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/MetaDataUtils.java
[metaData.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/metaData/MetaDataActivity.kt

[network.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/NetworkUtils.java
[network.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/network/NetworkActivity.kt

[object.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/ObjectUtils.java
[object.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/test/java/com/blankj/utilcode/util/ObjectUtilsTest.java

[path.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/PathUtils.java
[path.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/path/PathActivity.kt

[permission.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/PermissionUtils.java
[permission.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/permission/PermissionActivity.kt

[phone.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/PhoneUtils.java
[phone.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/phone/PhoneActivity.kt

[process.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/ProcessUtils.java
[process.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/process/ProcessActivity.kt

[reflect.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/ReflectUtils.java
[reflect.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/test/java/com/blankj/utilcode/util/reflect/ReflectUtilsTest.java

[regex.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/RegexUtils.java
[regex.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/test/java/com/blankj/utilcode/util/RegexUtilsTest.java

[resource.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/ResourceUtils.java
[resource.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/resource/ResourceActivity.kt

[rom.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/RomUtils.java
[rom.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/rom/RomActivity.kt

[screen.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/ScreenUtils.java
[screen.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/screen/ScreenActivity.kt

[sdcard.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/SDCardUtils.java
[sdcard.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/sdcard/SDCardActivity.kt

[service.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/ServiceUtils.java

[shell.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/ShellUtils.java

[size.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/SizeUtils.java

[snackbar.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/SnackbarUtils.java
[snackbar.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/snackbar/SnackbarActivity.kt

[span.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/SpanUtils.java
[span.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/span/SpanActivity.kt

[spStatic.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/SPStaticUtils.java
[spStatic.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/spStatic/SPStaticActivity.kt

[sp.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/SPUtils.java

[string.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/StringUtils.java
[string.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/test/java/com/blankj/utilcode/util/StringUtilsTest.java

[thread.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/ThreadUtils.java
[thread.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/test/java/com/blankj/utilcode/util/ThreadUtilsTest.java

[time.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/TimeUtils.java
[time.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/test/java/com/blankj/utilcode/util/TimeUtilsTest.java

[toast.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/ToastUtils.java
[toast.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/toast/ToastActivity.kt

[uri.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/UriUtils.java

[vibrate.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/VibrateUtils.java
[vibrate.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/vibrate/VibrateActivity.kt

[zip.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/ZipUtils.java
[zip.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/test/java/com/blankj/utilcode/util/ZipUtilsTest.java
