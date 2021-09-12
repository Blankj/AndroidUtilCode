## Download

Gradle:
```groovy
implementation 'com.blankj:utilcode:1.30.6'

// if u use AndroidX, use the following
implementation 'com.blankj:utilcodex:1.30.6'
```


## APIs

* ### About Activity -> [ActivityUtils.java][activity.java] -> [Demo][activity.demo]
```
addActivityLifecycleCallbacks
removeActivityLifecycleCallbacks
getAliveActivityByContext
getActivityByContext
isActivityExists
startActivity
startActivityForResult
startActivities
startHomeActivity
getActivityList
getLauncherActivity
getMainActivities
getTopActivity
isActivityAlive
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

* ### About Api -> [ApiUtils.java][api.java] -> [README][api.readme]
```
getApi
```

* ### About App -> [AppUtils.java][app.java] -> [Demo][app.demo]
```
registerAppStatusChangedListener
unregisterAppStatusChangedListener
installApp
uninstallApp
isAppInstalled
isAppRoot
isAppDebug
isAppSystem
isAppForeground
isAppRunning
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
getAppSignatures
getAppSignaturesSHA1
getAppSignaturesSHA256
getAppSignaturesMD5
getAppInfo
getAppsInfo
getApkInfo
```

* ### About Array -> [ArrayUtils.java][array.java] -> [Test][array.test]
```
newArray
newLongArray
newIntArray
newShortArray
newCharArray
newByteArray
newDoubleArray
newFloatArray
newBooleanArray
isEmpty
getLength
isSameLength
get
set
equals
reverse
copy
subArray
add
remove
removeElement
indexOf
lastIndexOf
contains
toPrimitive
toObject
asList
asUnmodifiableList
asArrayList
asLinkedList
sort
forAllDo
toString
```

* ### About Bar -> [BarUtils.java][bar.java] -> [Demo][bar.demo]
```
getStatusBarHeight
setStatusBarVisibility
isStatusBarVisible
setStatusBarLightMode
isStatusBarLightMode
addMarginTopEqualStatusBarHeight
subtractMarginTopEqualStatusBarHeight
setStatusBarColor
setStatusBarColor4Drawer
transparentStatusBar
getActionBarHeight
setNotificationBarVisibility
getNavBarHeight
setNavBarVisibility
isNavBarVisible
setNavBarColor
getNavBarColor
isSupportNavBar
setNavBarLightMode
isNavBarLightMode
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
register
unregister
post
postSticky
removeSticky
toString_
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

* ### About Click -> [ClickUtils.java][click.java] -> [Demo][click.demo]
```
applyPressedViewScale
applyPressedViewAlpha
applyPressedBgAlpha
applyPressedBgDark
applySingleDebouncing
applyGlobalDebouncing
expandClickArea
back2HomeFriendly
ClickUtils#OnDebouncingClickListener
ClickUtils#OnMultiClickListener
```

* ### About Clipboard -> [ClipboardUtils.java][clipboard.java] -> [Demo][clipboard.demo]
```
copyText
getText
copyUri
getUri
copyIntent
getIntent
addChangedListener
removeChangedListener
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

* ### About Collection -> [CollectionUtils.java][collection.java] -> [Test][collection.test]
```
newUnmodifiableList[NotNull]: 新建只读[非空]链表
newArrayList[NotNull]       : 新建数组型[非空]链表
newLinkedList[NotNull]      : 新建指针型[非空]链表
newHashSet[NotNull]         : 新建哈希[非空]集合
newTreeSet[NotNull]         : 新建有序[非空]集合
newSynchronizedCollection
newUnmodifiableCollection
union
intersection
disjunction
subtract
containsAny
getCardinalityMap
isSubCollection
isProperSubCollection
isEqualCollection
cardinality
find
forAllDo
filter
select
selectRejected
transform
collect
countMatches
exists
addIgnoreNull
addAll
get
size
sizeIsEmpty
isEmpty
isNotEmpty
retainAll
removeAll
toString
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
isLightColor
```

* ### About Convert -> [ConvertUtils.java][convert.java] -> [Test][convert.test]
```
int2HexString, hexString2Int
bytes2Bits, bits2Bytes
bytes2Chars, chars2Bytes
bytes2HexString, hexString2Bytes
bytes2String, string2Bytes
bytes2JSONObject, jsonObject2Bytes
bytes2JSONArray, jsonArray2Bytes
bytes2Parcelable, parcelable2Bytes
bytes2Object, serializable2Bytes
bytes2Bitmap, bitmap2Bytes
memorySize2Byte, byte2MemorySize
byte2FitMemorySize
timeSpan2Millis, millis2TimeSpan
millis2FitTimeSpan
input2OutputStream, output2InputStream
inputStream2Bytes, bytes2InputStream
outputStream2Bytes, bytes2OutputStream
inputStream2String, string2InputStream
outputStream2String, string2OutputStream
inputStream2Lines
drawable2Bitmap, bitmap2Drawable
drawable2Bytes, bytes2Drawable
view2Bitmap
dp2px, px2dp
sp2px, px2sp
```

* ### About Crash -> [CrashUtils.java][crash.java]
```
init
CrashInfo.addExtraHead
CrashInfo.getThrowable
CrashInfo.toString
```

* ### About Debouncing -> [DebouncingUtils.java][debouncing.java]
```
isValid
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
isTablet
isEmulator
isDevelopmentSettingsEnabled
getUniqueDeviceId
isSameDevice
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
binaryEncode
binaryDecode
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
rc4
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
copy
move
delete
deleteAllInDir
deleteFilesInDir
deleteFilesInDirWithFilter
listFilesInDir
listFilesInDirWithFilter
getFileLastModified
getFileCharsetSimple
getFileLines
getSize
getLength
getFileMD5
getFileMD5ToString
getDirName
getFileName
getFileNameNoExtension
getFileExtension
notifySystemToScan
getFsTotalSize
getFsAvailableSize
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
setGsonDelegate
setGson
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
save2Album
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
getShareTextImageIntent
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

* ### About Language -> [LanguageUtils.java][language.java] -> [Demo][language.demo]
```
applySystemLanguage
applyLanguage
isAppliedLanguage
getAppliedLanguage
getContextLanguage
getAppContextLanguage
getSystemLanguage
updateAppContextLanguage
attachBaseContext
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
Config.setFileWriter
Config.setOnConsoleOutputListener
Config.setOnFileOutputListener
Config.addFileExtraHead
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
getCurrentLogFilePath
getLogFiles
```

* ### About Map -> [MapUtils.java][map.java] -> [Test][map.test]
```
newUnmodifiableMap
newHashMap
newLinkedHashMap
newTreeMap
newHashTable
isEmpty
isNotEmpty
size
forAllDo
transform
toString
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
isAvailable[Async]                      : 判断网络是否可用
isAvailableByPing[Async]                : 用 ping 判断网络是否可用
isAvailableByDns[Async]                 : 用 DNS 判断网络是否可用
getMobileDataEnabled
isMobileData
is4G
getWifiEnabled
setWifiEnabled
isWifiConnected
isWifiAvailable[Async]                  : 判断 wifi 数据是否可用
getNetworkOperatorName
getNetworkType
getIPAddress[Async]                     : 获取 IP 地址
getDomainAddress[Async]                 : 获取域名 IP 地址
getIpAddressByWifi
getGatewayByWifi
getNetMaskByWifi
getServerAddressByWifi
registerNetworkStatusChangedListener
isRegisteredNetworkStatusChangedListener
unregisterNetworkStatusChangedListener
getWifiScanResult
addOnWifiChangedConsumer
removeOnWifiChangedConsumer
```

* ### About Notification -> [NotificationUtils.java][notification.java] -> [Demo][notification.demo]
```
areNotificationsEnabled
notify
cancel
cancelAll
setNotificationBarVisibility
```

* ### About Number -> [NumberUtils.java][number.java] -> [Test][number.test]
```
format
float2Double
```

* ### About Object -> [ObjectUtils.java][object.java] -> [Test][object.test]
```
isEmpty
isNotEmpty
equals
compare
requireNonNull(s)
getOrDefault
toString
hashCode(s)
```

* ### About Path -> [PathUtils.java][path.java] -> [Demo][path.demo]
```
join
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
getRootPathExternalFirst
getAppDataPathExternalFirst
getFilesPathExternalFirst
getCachePathExternalFirst
```

* ### About Permission -> [PermissionUtils.java][permission.java] -> [Demo][permission.demo]
```
permission
permissionGroup
permission.explain
permission.rationale
permission.callback
permission.theme
permission.request
getPermissions
isGranted
isGrantedWriteSettings
requestWriteSettings
isGrantedDrawOverlays
requestDrawOverlays
launchAppDetailsSettings
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
dial
call
sendSms
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
RegexConstants.REGEX_DOUBLE_BYTE_CHAR
RegexConstants.REGEX_BLANK_LINE
RegexConstants.REGEX_QQ_NUM
RegexConstants.REGEX_CHINA_POSTAL_CODE
RegexConstants.REGEX_INTEGER
RegexConstants.REGEX_POSITIVE_INTEGER
RegexConstants.REGEX_NEGATIVE_INTEGER
RegexConstants.REGEX_NOT_NEGATIVE_INTEGER
RegexConstants.REGEX_NOT_POSITIVE_INTEGER
RegexConstants.REGEX_FLOAT
RegexConstants.REGEX_POSITIVE_FLOAT
RegexConstants.REGEX_NEGATIVE_FLOAT
RegexConstants.REGEX_NOT_NEGATIVE_FLOAT
RegexConstants.REGEX_NOT_POSITIVE_FLOAT
```

* ### About Resource -> [ResourceUtils.java][resource.java] -> [Demo][resource.demo]
```
getDrawable
getIdByName
getStringIdByName
getColorIdByName
getDimenIdByName
getDrawableIdByName
getMipmapIdByName
getLayoutIdByName
getStyleIdByName
getAnimIdByName
getMenuIdByName
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
getAppScreenWidth
getAppScreenHeight
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
```

* ### About SDCard -> [SDCardUtils.java][sdcard.java] -> [Demo][sdcard.demo]
```
isSDCardEnableByEnvironment
getSDCardPathByEnvironment
getSDCardInfo
getMountedSDCardPath
getExternalTotalSize
getExternalAvailableSize
getInternalTotalSize
getInternalAvailableSize
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

* ### About Shadow -> [ShadowUtils.java][shadow.java] -> [Demo][shadow.demo]
```
apply
```

* ### About Shell -> [ShellUtils.java][shell.java]
```
execCmd[Async]: 执行命令
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
getString
getStringArray
format
```

* ### About Thread -> [ThreadUtils.java][thread.java] -> [Test][thread.test]
```
isMainThread
getMainHandler
runOnUiThread
runOnUiThreadDelayed
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
setDeliver
```

* ### About Time -> [TimeUtils.java][time.java] -> [Test][time.test]
```
getSafeDateFormat
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
isAm
isPm
getValueByCalendarField
getChineseZodiac
getZodiac
```

* ### About Toast -> [ToastUtils.java][toast.java] -> [Demo][toast.demo]
```
make
make.setMode
make.setGravity
make.setBgColor
make.setBgResource
make.setTextColor
make.setTextSize
make.setDurationIsLong
make.setLeftIcon
make.setTopIcon
make.setRightIcon
make.setBottomIcon
make.setNotUseSystemToast
make.show
getDefaultMaker
showShort
showLong
cancel
```

* ### About Touch -> [TouchUtils.java][touch.java]
```
setOnTouchListener
```

* ### About UiMessage -> [UiMessageUtils.java][uiMessage.java] -> [Demo][uiMessage.demo]
```
send
addListener
removeListener
```

* ### About Uri -> [UriUtils.java][uri.java]
```
res2Uri
file2Uri
uri2File
uri2Bytes
```

* ### UtilsTransActivity -> [UtilsTransActivity.java][trans.java]
```
start
```

* ### UtilsTransActivity4MainProcess -> [UtilsTransActivity4MainProcess.java][trans4Main.java]
```
start
```

* ### About Vibrate -> [VibrateUtils.java][vibrate.java] -> [Demo][vibrate.demo]
```
vibrate
cancel
```

* ### About View -> [ViewUtils.java][view.java]
```
setViewEnabled
runOnUiThread
runOnUiThreadDelayed
isLayoutRtl
fixScrollViewTopping
layoutId2View
```

* ### About Volume -> [VolumeUtils.java][volume.java]
```
getVolume
setVolume
getMaxVolume
getMinVolume
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






[activity.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/ActivityUtils.java
[activity.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/activity/ActivityActivity.kt

[adaptScreen.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/AdaptScreenUtils.java
[adaptScreen.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/adaptScreen/AdaptScreenActivity.kt

[api.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/ApiUtils.java
[api.readme]: https://github.com/Blankj/AndroidUtilCode/blob/master/plugin/api-gradle-plugin

[app.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/AppUtils.java
[app.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/app/AppActivity.kt

[array.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/ArrayUtils.java
[array.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/test/java/com/blankj/utilcode/util/ArrayUtilsTest.java

[bar.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/BarUtils.java
[bar.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/bar/BarActivity.kt

[brightness.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/BrightnessUtils.java
[brightness.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/brightness/BrightnessActivity.kt

[bus.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/BusUtils.java
[bus.readme]: https://github.com/Blankj/AndroidUtilCode/blob/master/plugin/bus-gradle-plugin

[cacheDiskStatic.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/CacheDiskStaticUtils.java
[cacheDiskStatic.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/test/java/com/blankj/utilcode/util/CacheDiskStaticUtilsTest.java

[cacheDisk.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/CacheDiskUtils.java
[cacheDisk.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/test/java/com/blankj/utilcode/util/CacheDiskUtilsTest.java

[cacheDoubleStatic.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/CacheDoubleStaticUtils.java
[cacheDoubleStatic.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/test/java/com/blankj/utilcode/util/CacheDoubleStaticUtilsTest.java

[cacheDouble.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/CacheDoubleUtils.java
[cacheDouble.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/test/java/com/blankj/utilcode/util/CacheDoubleUtilsTest.java

[cacheMemoryStatic.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/CacheMemoryStaticUtils.java
[cacheMemoryStatic.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/test/java/com/blankj/utilcode/util/CacheMemoryStaticUtilsTest.java

[cacheMemory.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/CacheMemoryUtils.java
[cacheMemory.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/test/java/com/blankj/utilcode/util/CacheMemoryUtilsTest.java

[clean.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/CleanUtils.java
[clean.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/clean/CleanActivity.kt

[click.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/ClickUtils.java
[click.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/click/ClickActivity.kt

[clipboard.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/ClipboardUtils.java
[clipboard.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/clipboard/ClipboardActivity.kt

[clone.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/CloneUtils.java
[clone.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/test/java/com/blankj/utilcode/util/CloneUtilsTest.java

[close.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/CloseUtils.java

[collection.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/CollectionUtils.java
[collection.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/test/java/com/blankj/utilcode/util/CollectionUtilsTest.java

[color.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/ColorUtils.java
[color.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/test/java/com/blankj/utilcode/util/ColorUtilsTest.java

[convert.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/ConvertUtils.java
[convert.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/test/java/com/blankj/utilcode/util/ConvertUtilsTest.java

[crash.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/CrashUtils.java

[debouncing.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/DebouncingUtils.java

[device.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/DeviceUtils.java
[device.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/device/DeviceActivity.kt

[empty.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/EmptyUtils.java
[empty.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/test/java/com/blankj/utilcode/util/EmptyUtilsTest.java

[encode.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/EncodeUtils.java
[encode.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/test/java/com/blankj/utilcode/util/EncodeUtilsTest.java

[encrypt.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/EncryptUtils.java
[encrypt.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/test/java/com/blankj/utilcode/util/EncryptUtilsTest.java

[fileIo.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/FileIOUtils.java
[fileIo.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/test/java/com/blankj/utilcode/util/FileIOUtilsTest.java

[file.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/FileUtils.java
[file.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/test/java/com/blankj/utilcode/util/FileUtilsTest.java

[flashlight.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/FlashlightUtils.java
[flashlight.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/flashlight/FlashlightActivity.kt

[fragment.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/FragmentUtils.java
[fragment.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/fragment/FragmentActivity.kt

[gson.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/GsonUtils.java
[gson.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/test/java/com/blankj/utilcode/util/GsonUtilsTest.java

[image.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/ImageUtils.java
[image.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/image/ImageActivity.kt

[intent.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/IntentUtils.java

[keyboard.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/KeyboardUtils.java
[keyboard.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/keyboard/KeyboardActivity.kt

[language.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/LanguageUtils.java
[language.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/language/LanguageActivity.kt

[log.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/LogUtils.java
[log.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/log/LogActivity.kt

[map.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/MapUtils.java
[map.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/test/java/com/blankj/utilcode/util/MapUtilsTest.java

[metaData.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/MetaDataUtils.java
[metaData.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/metaData/MetaDataActivity.kt

[network.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/NetworkUtils.java
[network.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/network/NetworkActivity.kt

[notification.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/NotificationUtils.java
[notification.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/notification/NotificationActivity.kt

[number.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/NumberUtils.java
[number.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/test/java/com/blankj/utilcode/util/NumberUtilsTest.java

[object.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/ObjectUtils.java
[object.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/test/java/com/blankj/utilcode/util/ObjectUtilsTest.java

[path.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/PathUtils.java
[path.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/path/PathActivity.kt

[permission.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/PermissionUtils.java
[permission.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/permission/PermissionActivity.kt

[phone.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/PhoneUtils.java
[phone.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/phone/PhoneActivity.kt

[process.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/ProcessUtils.java
[process.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/process/ProcessActivity.kt

[reflect.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/ReflectUtils.java
[reflect.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/test/java/com/blankj/utilcode/util/reflect/ReflectUtilsTest.java

[regex.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/RegexUtils.java
[regex.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/test/java/com/blankj/utilcode/util/RegexUtilsTest.java

[resource.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/ResourceUtils.java
[resource.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/resource/ResourceActivity.kt

[rom.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/RomUtils.java
[rom.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/rom/RomActivity.kt

[screen.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/ScreenUtils.java
[screen.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/screen/ScreenActivity.kt

[sdcard.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/SDCardUtils.java
[sdcard.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/sdcard/SDCardActivity.kt

[service.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/ServiceUtils.java

[shadow.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/ShadowUtils.java
[shadow.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/shadow/ShadowActivity.kt

[shell.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/ShellUtils.java

[size.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/SizeUtils.java

[snackbar.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/SnackbarUtils.java
[snackbar.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/snackbar/SnackbarActivity.kt

[span.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/SpanUtils.java
[span.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/span/SpanActivity.kt

[spStatic.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/SPStaticUtils.java
[spStatic.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/spStatic/SPStaticActivity.kt

[sp.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/SPUtils.java

[string.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/StringUtils.java
[string.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/test/java/com/blankj/utilcode/util/StringUtilsTest.java

[thread.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/ThreadUtils.java
[thread.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/test/java/com/blankj/utilcode/util/ThreadUtilsTest.java

[time.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/TimeUtils.java
[time.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/test/java/com/blankj/utilcode/util/TimeUtilsTest.java

[toast.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/ToastUtils.java
[toast.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/toast/ToastActivity.kt

[touch.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/TouchUtils.java

[uiMessage.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/UiMessageUtils.java
[uiMessage.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/uiMessage/UiMessageActivity.kt

[uri.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/UriUtils.java

[trans.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/UtilsTransActivity.java

[trans4Main.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/UtilsTransActivity4MainProcess.java

[vibrate.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/VibrateUtils.java
[vibrate.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/vibrate/VibrateActivity.kt

[view.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/ViewUtils.java

[volume.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/VolumeUtils.java
[volume.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/feature/utilcode/pkg/src/main/java/com/blankj/utilcode/pkg/feature/volume/VolumeActivity.kt

[zip.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/ZipUtils.java
[zip.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/test/java/com/blankj/utilcode/util/ZipUtilsTest.java
