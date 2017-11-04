## Download

Gradle:
``` groovy
compile 'com.blankj:utilcode:1.9.6'
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

* ### Activity相关→[ActivityUtils.java][activity.java]→[Demo][activity.demo]
```
isActivityExists                 : 判断Activity是否存在
startActivity                    : 启动Activity
startActivities                  : 启动多个Activity
startHomeActivity                : 回到桌面
getActivityList                  : 获取Activity栈链表
getLauncherActivity              : 获取启动项Activity
getTopActivity                   : 获取栈顶Activity
isActivityExistsInStack          : 判断Activity是否存在栈中
finishActivity                   : 结束Activity
finishToActivity                 : 结束到指定Activity
finishOtherActivitiesExceptNewest: 结束除最新之外的同类型Activity
finishAllActivities              : 结束所有activity
```

* ### App相关→[AppUtils.java][app.java]→[Demo][app.demo]
```
isInstallApp         : 判断App是否安装
installApp           : 安装App（支持7.0）
installAppSilent     : 静默安装App
uninstallApp         : 卸载App
uninstallAppSilent   : 静默卸载App
isAppRoot            : 判断App是否有root权限
launchApp            : 打开App
exitApp              : 关闭应用
getAppPackageName    : 获取App包名
getAppDetailsSettings: 获取App具体设置
getAppName           : 获取App名称
getAppIcon           : 获取App图标
getAppPath           : 获取App路径
getAppVersionName    : 获取App版本号
getAppVersionCode    : 获取App版本码
isSystemApp          : 判断App是否是系统应用
isAppDebug           : 判断App是否是Debug版本
getAppSignature      : 获取App签名
getAppSignatureSHA1  : 获取应用签名的的SHA1值
isAppForeground      : 判断App是否处于前台
getForegroundApp     : 获取前台应用包名
getAppInfo           : 获取App信息
getAppsInfo          : 获取所有已安装App信息
cleanAppData         : 清除App所有数据
```

* ### 栏相关→[BarUtils.java][bar.java]→[Demo][bar.demo]
```
getStatusBarHeight                   : 获取状态栏高度(px)
addMarginTopEqualStatusBarHeight     : 为view增加MarginTop为状态栏高度
subtractMarginTopEqualStatusBarHeight: 为view减少MarginTop为状态栏高度
setStatusBarColor                    : 设置状态栏颜色
setStatusBarAlpha                    : 设置状态栏透明度
setStatusBarColor4Drawer             : 为DrawerLayout设置状态栏颜色
setStatusBarAlpha4Drawer             : 为DrawerLayout设置状态栏透明度
getActionBarHeight                   : 获取ActionBar高度
showNotificationBar                  : 显示通知栏
hideNotificationBar                  : 隐藏通知栏
getNavBarHeight                      : 获取导航栏高度
hideNavBar                           : 隐藏导航栏
```

* ### 缓存相关→[CacheUtils.java][cache.java]→[Test][cache.test]
```
getInstance    : 获取缓存实例
put            : 缓存中写入数据
getBytes       : 缓存中读取字节数组
getString      : 缓存中读取String
getJSONObject  : 缓存中读取JSONObject
getJSONArray   : 缓存中读取JSONArray
getBitmap      : 缓存中读取Bitmap
getDrawable    : 缓存中读取Drawable
getParcelable  : 缓存中读取Parcelable
getSerializable: 缓存中读取Serializable
getCacheSize   : 获取缓存大小
getCacheCount  : 获取缓存个数
remove         : 根据键值移除缓存
clear          : 清除所有缓存
```

* ### 清除相关→[CleanUtils.java][clean.java]→[Demo][clean.demo]
```
cleanInternalCache   : 清除内部缓存
cleanInternalFiles   : 清除内部文件
cleanInternalDbs     : 清除内部数据库
cleanInternalDbByName: 根据名称清除数据库
cleanInternalSP      : 清除内部SP
cleanExternalCache   : 清除外部缓存
cleanCustomCache     : 清除自定义目录下的文件
```

* ### 关闭相关→[CloseUtils.java][close.java]
```
closeIO       : 关闭IO
closeIOQuietly: 安静关闭IO
```

* ### 转换相关→[ConvertUtils.java][convert.java]→[Test][convert.test]
```
bytes2HexString, hexString2Bytes        : byteArr与hexString互转
chars2Bytes, bytes2Chars                : charArr与byteArr互转
memorySize2Byte, byte2MemorySize        : 以unit为单位的内存大小与字节数互转
byte2FitMemorySize                      : 字节数转合适内存大小
timeSpan2Millis, millis2TimeSpan        : 以unit为单位的时间长度与毫秒时间戳互转
millis2FitTimeSpan                      : 毫秒时间戳转合适时间长度
bytes2Bits, bits2Bytes                  : bytes与bits互转
input2OutputStream, output2InputStream  : inputStream与outputStream互转
inputStream2Bytes, bytes2InputStream    : inputStream与byteArr互转
outputStream2Bytes, bytes2OutputStream  : outputStream与byteArr互转
inputStream2String, string2InputStream  : inputStream与string按编码互转
outputStream2String, string2OutputStream: outputStream与string按编码互转
bitmap2Bytes, bytes2Bitmap              : bitmap与byteArr互转
drawable2Bitmap, bitmap2Drawable        : drawable与bitmap互转
drawable2Bytes, bytes2Drawable          : drawable与byteArr互转
view2Bitmap                             : view转Bitmap
dp2px, px2dp                            : dp与px互转
sp2px, px2sp                            : sp与px互转
```

* ### 崩溃相关→[CrashUtils.java][crash.java]
```
init: 初始化
```

* ### 设备相关→[DeviceUtils.java][device.java]→[Demo][device.demo]
```
isDeviceRooted   : 判断设备是否rooted
getSDKVersion    : 获取设备系统版本号
getAndroidID     : 获取设备AndroidID
getMacAddress    : 获取设备MAC地址
getManufacturer  : 获取设备厂商
getModel         : 获取设备型号
shutdown         : 关机
reboot           : 重启
reboot2Recovery  : 重启到recovery
reboot2Bootloader: 重启到bootloader
```

* ### 判空相关→[EmptyUtils.java][empty.java]→[Test][empty.test]
```
isEmpty   : 判断对象是否为空
isNotEmpty: 判断对象是否非空
```

* ### 编码解码相关→[EncodeUtils.java][encode.java]→[Test][encode.test]
```
urlEncode          : URL编码
urlDecode          : URL解码
base64Encode       : Base64编码
base64Encode2String: Base64编码
base64Decode       : Base64解码
base64UrlSafeEncode: Base64URL安全编码
htmlEncode         : Html编码
htmlDecode         : Html解码
```

* ### 加密解密相关→[EncryptUtils.java][encrypt.java]→[Test][encrypt.test]
```
encryptMD2, encryptMD2ToString                        : MD2加密
encryptMD5, encryptMD5ToString                        : MD5加密
encryptMD5File, encryptMD5File2String                 : MD5加密文件
encryptSHA1, encryptSHA1ToString                      : SHA1加密
encryptSHA224, encryptSHA224ToString                  : SHA224加密
encryptSHA256, encryptSHA256ToString                  : SHA256加密
encryptSHA384, encryptSHA384ToString                  : SHA384加密
encryptSHA512, encryptSHA512ToString                  : SHA512加密
encryptHmacMD5, encryptHmacMD5ToString                : HmacMD5加密
encryptHmacSHA1, encryptHmacSHA1ToString              : HmacSHA1加密
encryptHmacSHA224, encryptHmacSHA224ToString          : HmacSHA224加密
encryptHmacSHA256, encryptHmacSHA256ToString          : HmacSHA256加密
encryptHmacSHA384, encryptHmacSHA384ToString          : HmacSHA384加密
encryptHmacSHA512, encryptHmacSHA512ToString          : HmacSHA512加密
encryptDES, encryptDES2HexString, encryptDES2Base64   : DES加密
decryptDES, decryptHexStringDES, decryptBase64DES     : DES解密
encrypt3DES, encrypt3DES2HexString, encrypt3DES2Base64: 3DES加密
decrypt3DES, decryptHexString3DES, decryptBase64_3DES : 3DES解密
encryptAES, encryptAES2HexString, encryptAES2Base64   : AES加密
decryptAES, decryptHexStringAES, decryptBase64AES     : AES解密
```

* ### 文件相关→[FileIOUtils.java][fileio.java]→[Test][fileio.test]
```
writeFileFromIS            : 将输入流写入文件
writeFileFromBytesByStream : 将字节数组写入文件
writeFileFromBytesByChannel: 将字节数组写入文件
writeFileFromBytesByMap    : 将字节数组写入文件
writeFileFromString        : 将字符串写入文件
readFile2List              : 读取文件到字符串链表中
readFile2String            : 读取文件到字符串中
readFile2BytesByStream     : 读取文件到字节数组中
readFile2BytesByChannel    : 读取文件到字节数组中
readFile2BytesByMap        : 读取文件到字节数组中
setBufferSize              : 设置缓冲区尺寸
```

* ### 文件相关→[FileUtils.java][file.java]→[Test][file.test]
```
getFileByPath             : 根据文件路径获取文件
isFileExists              : 判断文件是否存在
rename                    : 重命名文件
isDir                     : 判断是否是目录
isFile                    : 判断是否是文件
createOrExistsDir         : 判断目录是否存在，不存在则判断是否创建成功
createOrExistsFile        : 判断文件是否存在，不存在则判断是否创建成功
createFileByDeleteOldFile : 判断文件是否存在，存在则在创建之前删除
copyDir                   : 复制目录
copyFile                  : 复制文件
moveDir                   : 移动目录
moveFile                  : 移动文件
deleteDir                 : 删除目录
deleteFile                : 删除文件
deleteAllInDir            : 删除目录下所有东西
deleteFilesInDir          : 删除目录下所有文件
deleteFilesInDirWithFilter: 删除目录下所有过滤的文件
listFilesInDir            : 获取目录下所有文件
listFilesInDirWithFilter  : 获取目录下所有过滤的文件
getFileLastModified       : 获取文件最后修改的毫秒时间戳
getFileCharsetSimple      : 简单获取文件编码格式
getFileLines              : 获取文件行数
getDirSize                : 获取目录大小
getFileSize               : 获取文件大小
getDirLength              : 获取目录长度
getFileLength             : 获取文件长度
getFileMD5                : 获取文件的MD5校验码
getFileMD5ToString        : 获取文件的MD5校验码
getDirName                : 根据全路径获取最长目录
getFileName               : 根据全路径获取文件名
getFileNameNoExtension    : 根据全路径获取文件名不带拓展名
getFileExtension          : 根据全路径获取文件拓展名
```

* ### Fragment相关→[FragmentUtils.java][fragment.java]→[Demo][fragment.demo]
```
add                   : 新增fragment
show                  : 显示fragment
hide                  : 隐藏fragment
showHide              : 先显示后隐藏fragment
replace               : 替换fragment
pop                   : 出栈fragment
popTo                 : 出栈到指定fragment
popAll                : 出栈所有fragment
remove                : 移除fragment
removeTo              : 移除到指定fragment
removeAll             : 移除所有fragment
getTop                : 获取顶部fragment
getTopInStack         : 获取栈中顶部fragment
getTopShow            : 获取顶部可见fragment
getTopShowInStack     : 获取栈中顶部可见fragment
getFragments          : 获取同级别的fragment
getFragmentsInStack   : 获取同级别栈中的fragment
getAllFragments       : 获取所有fragment
getAllFragmentsInStack: 获取栈中所有fragment
findFragment          : 查找fragment
dispatchBackPress     : 处理fragment回退键
setBackgroundColor    : 设置背景色
setBackgroundResource : 设置背景资源
setBackground         : 设置背景
```

* ### 图片相关→[ImageUtils.java][image.java]→[Demo][image.demo]
```
bitmap2Bytes, bytes2Bitmap      : bitmap与byteArr互转
drawable2Bitmap, bitmap2Drawable: drawable与bitmap互转
drawable2Bytes, bytes2Drawable  : drawable与byteArr互转
view2Bitmap                     : view转bitmap
getBitmap                       : 获取bitmap
scale                           : 缩放图片
clip                            : 裁剪图片
skew                            : 倾斜图片
rotate                          : 旋转图片
getRotateDegree                 : 获取图片旋转角度
toRound                         : 转为圆形图片
toRoundCorner                   : 转为圆角图片
addCornerBorder                 : 添加圆角边框
addCircleBorder                 : 添加圆形边框
addReflection                   : 添加倒影
addTextWatermark                : 添加文字水印
addImageWatermark               : 添加图片水印
toAlpha                         : 转为alpha位图
toGray                          : 转为灰度图片
fastBlur                        : 快速模糊
renderScriptBlur                : renderScript模糊图片
stackBlur                       : stack模糊图片
save                            : 保存图片
isImage                         : 根据文件名判断文件是否为图片
getImageType                    : 获取图片类型
compressByScale                 : 按缩放压缩
compressByQuality               : 按质量压缩
compressBySampleSize            : 按采样大小压缩
```

* ### 意图相关→[IntentUtils.java][intent.java]
```
getInstallAppIntent        : 获取安装App（支持6.0）的意图
getUninstallAppIntent      : 获取卸载App的意图
getLaunchAppIntent         : 获取打开App的意图
getAppDetailsSettingsIntent: 获取App具体设置的意图
getShareTextIntent         : 获取分享文本的意图
getShareImageIntent        : 获取分享图片的意图
getComponentIntent         : 获取其他应用组件的意图
getShutdownIntent          : 获取关机的意图
getCaptureIntent           : 获取拍照的意图
```

* ### 键盘相关→[KeyboardUtils.java][keyboard.java]→[Demo][keyboard.demo]
```
showSoftInput               : 动态显示软键盘
hideSoftInput               : 动态隐藏软键盘
toggleSoftInput             : 切换键盘显示与否状态
clickBlankArea2HideSoftInput: 点击屏幕空白区域隐藏软键盘
```

* ### 日志相关→[LogUtils.java][log.java]→[Demo][log.demo]
```
getConfig               : 获取log配置
Config.setLogSwitch     : 设置log总开关
Config.setConsoleSwitch : 设置log控制台开关
Config.setGlobalTag     : 设置log全局tag
Config.setLogHeadSwitch : 设置log头部信息开关
Config.setLog2FileSwitch: 设置log文件开关
Config.setDir           : 设置log文件存储目录
Config.setFilePrefix    : 设置log文件前缀
Config.setBorderSwitch  : 设置log边框开关
Config.setConsoleFilter : 设置log控制台过滤器
Config.setFileFilter    : 设置log文件过滤器
Config.setStackDeep     : 设置log栈深度
v                       : Verbose日志
d                       : Debug日志
i                       : Info日志
w                       : Warn日志
e                       : Error日志
a                       : Assert日志
file                    : log到文件
json                    : log字符串之json
xml                     : log字符串之xml
```

* ### 网络相关→[NetworkUtils.java][network.java]→[Demo][network.demo]
```
openWirelessSettings  : 打开网络设置界面
isConnected           : 判断网络是否连接
isAvailableByPing     : 判断网络是否可用
getDataEnabled        : 判断移动数据是否打开
setDataEnabled        : 打开或关闭移动数据
is4G                  : 判断网络是否是4G
getWifiEnabled        : 判断wifi是否打开
setWifiEnabled        : 打开或关闭wifi
isWifiConnected       : 判断wifi是否连接状态
isWifiAvailable       : 判断wifi数据是否可用
getNetworkOperatorName: 获取移动网络运营商名称
getNetworkType        : 获取当前网络类型
getIPAddress          : 获取IP地址
getDomainAddress      : 获取域名ip地址
```

* ### 手机相关→[PhoneUtils.java][phone.java]→[Demo][phone.demo]
```
isPhone            : 判断设备是否是手机
getIMEI            : 获取IMEI码
getIMSI            : 获取IMSI码
getPhoneType       : 获取移动终端类型
isSimCardReady     : 判断sim卡是否准备好
getSimOperatorName : 获取Sim卡运营商名称
getSimOperatorByMnc: 获取Sim卡运营商名称
getPhoneStatus     : 获取手机状态信息
dial               : 跳至拨号界面
call               : 拨打phoneNumber
sendSms            : 跳至发送短信界面
sendSmsSilent      : 发送短信
getAllContactInfo  : 获取手机联系人
getContactNum      : 打开手机联系人界面点击联系人后便获取该号码
getAllSMS          : 获取手机短信并保存到xml中
```

* ### 进程相关→[ProcessUtils.java][process.java]→[Demo][process.demo]
```
getForegroundProcessName  : 获取前台线程包名
killAllBackgroundProcesses: 杀死所有的后台服务进程
killBackgroundProcesses   : 杀死后台服务进程
```

* ### 正则相关→[RegexUtils.java][regex.java]→[Test][regex.test]
```
isMobileSimple : 验证手机号（简单）
isMobileExact  : 验证手机号（精确）
isTel          : 验证电话号码
isIDCard15     : 验证身份证号码15位
isIDCard18     : 验证身份证号码18位
isEmail        : 验证邮箱
isURL          : 验证URL
isZh           : 验证汉字
isUsername     : 验证用户名
isDate         : 验证yyyy-MM-dd格式的日期校验，已考虑平闰年
isIP           : 验证IP地址
isMatch        : 判断是否匹配正则
getMatches     : 获取正则匹配的部分
getSplits      : 获取正则匹配分组
getReplaceFirst: 替换正则匹配的第一部分
getReplaceAll  : 替换所有正则匹配的部分
```

* ### 屏幕相关→[ScreenUtils.java][screen.java]
```
getScreenWidth     : 获取屏幕的宽度（单位：px）
getScreenHeight    : 获取屏幕的高度（单位：px）
getScreenDensity   : 获取屏幕密度
getScreenDensityDpi: 获取屏幕密度DPI
setFullScreen      : 设置屏幕为全屏
setLandscape       : 设置屏幕为横屏
setPortrait        : 设置屏幕为竖屏
isLandscape        : 判断是否横屏
isPortrait         : 判断是否竖屏
getScreenRotation  : 获取屏幕旋转角度
screenShot         : 截屏
isScreenLock       : 判断是否锁屏
setSleepDuration   : 设置进入休眠时长
getSleepDuration   : 获取进入休眠时长
isTablet           : 判断是否是平板
```

* ### SD卡相关→[SDCardUtils.java][sdcard.java]→[Demo][sdcard.demo]
```
isSDCardEnable: 判断SD卡是否可用
getSDCardPaths: 获取SD卡路径
```

* ### 服务相关→[ServiceUtils.java][service.java]
```
getAllRunningService: 获取所有运行的服务
startService        : 启动服务
stopService         : 停止服务
bindService         : 绑定服务
unbindService       : 解绑服务
isServiceRunning    : 判断服务是否运行
```

* ### Shell相关→[ShellUtils.java][shell.java]
```
execCmd: 是否是在root下执行命令
```

* ### 尺寸相关→[SizeUtils.java][size.java]
```
dp2px, px2dp     : dp与px转换
sp2px, px2sp     : sp与px转换
applyDimension   : 各种单位转换
forceGetViewSize : 在onCreate中获取视图的尺寸
measureView      : 测量视图尺寸
getMeasuredWidth : 获取测量视图宽度
getMeasuredHeight: 获取测量视图高度
```

* ### Snackbar相关→[SnackbarUtils.java][snackbar.java]→[Demo][snackbar.demo]
```
with           : 设置snackbar依赖view
setMessage     : 设置消息
setMessageColor: 设置消息颜色
setBgColor     : 设置背景色
setBgResource  : 设置背景资源
setDuration    : 设置显示时长
setAction      : 设置行为
setBottomMargin: 设置底边距
show           : 显示snackbar
showSuccess    : 显示预设成功的snackbar
showWarning    : 显示预设警告的snackbar
showError      : 显示预设错误的snackbar
dismiss        : 消失snackbar
getView        : 获取snackbar视图
addView        : 添加snackbar视图
```

* ### SpannableString相关→[SpanUtils.java][span.java]→[Demo][span.demo]
```
setFlag           : 设置标识
setForegroundColor: 设置前景色
setBackgroundColor: 设置背景色
setLineHeight     : 设置行高
setQuoteColor     : 设置引用线的颜色
setLeadingMargin  : 设置缩进
setBullet         : 设置列表标记
setIconMargin     : 设置图标
setFontSize       : 设置字体尺寸
setFontProportion : 设置字体比例
setFontXProportion: 设置字体横向比例
setStrikethrough  : 设置删除线
setUnderline      : 设置下划线
setSuperscript    : 设置上标
setSubscript      : 设置下标
setBold           : 设置粗体
setItalic         : 设置斜体
setBoldItalic     : 设置粗斜体
setFontFamily     : 设置字体系列
setTypeface       : 设置字体
setAlign          : 设置对齐
setClickSpan      : 设置点击事件
setUrl            : 设置超链接
setBlur           : 设置模糊
setShader         : 设置着色器
setShadow         : 设置阴影
setSpans          : 设置样式
append            : 追加样式字符串
appendLine        : 追加一行样式字符串
appendImage       : 追加图片
appendSpace       : 追加空白
create            : 创建样式字符串
```

* ### SP相关→[SPUtils.java][sp.java]→[Test][sp.test]
```
getInstance: 获取SP实例
put        : SP中写入数据
getString  : SP中读取String
getInt     : SP中读取int
getLong    : SP中读取long
getFloat   : SP中读取float
getBoolean : SP中读取boolean
getAll     : SP中获取所有键值对
contains   : SP中是否存在该key
remove     : SP中移除该key
clear      : SP中清除所有数据
```

* ### 字符串相关→[StringUtils.java][string.java]→[Test][string.test]
```
isEmpty         : 判断字符串是否为null或长度为0
isTrimEmpty     : 判断字符串是否为null或全为空格
isSpace         : 判断字符串是否为null或全为空白字符
equals          : 判断两字符串是否相等
equalsIgnoreCase: 判断两字符串忽略大小写是否相等
null2Length0    : null转为长度为0的字符串
length          : 返回字符串长度
upperFirstLetter: 首字母大写
lowerFirstLetter: 首字母小写
reverse         : 反转字符串
toDBC           : 转化为半角字符
toSBC           : 转化为全角字符
```

* ### 时间相关→[TimeUtils.java][time.java]→[Test][time.test]
```
millis2String           : 将时间戳转为时间字符串
string2Millis           : 将时间字符串转为时间戳
string2Date             : 将时间字符串转为Date类型
date2String             : 将Date类型转为时间字符串
date2Millis             : 将Date类型转为时间戳
millis2Date             : 将时间戳转为Date类型
getTimeSpan             : 获取两个时间差（单位：unit）
getFitTimeSpan          : 获取合适型两个时间差
getNowMills             : 获取当前毫秒时间戳
getNowString            : 获取当前时间字符串
getNowDate              : 获取当前Date
getTimeSpanByNow        : 获取与当前时间的差（单位：unit）
getFitTimeSpanByNow     : 获取合适型与当前时间的差
getFriendlyTimeSpanByNow: 获取友好型与当前时间的差
getMillis               : 获取与给定时间等于时间差的时间戳
getString               : 获取与给定时间等于时间差的时间字符串
getDate                 : 获取与给定时间等于时间差的Date
getMillisByNow          : 获取与当前时间等于时间差的时间戳
getStringByNow          : 获取与当前时间等于时间差的时间字符串
getDateByNow            : 获取与当前时间等于时间差的Date
isToday                 : 判断是否今天
isLeapYear              : 判断是否闰年
getChineseWeek          : 获取中式星期
getUSWeek               : 获取美式式星期
getWeekIndex            : 获取星期索引
getWeekOfMonth          : 获取月份中的第几周
getWeekOfYear           : 获取年份中的第几周
getChineseZodiac        : 获取生肖
getZodiac               : 获取星座
```

* ### 吐司相关→[ToastUtils.java][toast.java]→[Demo][toast.demo]
```
setGravity     : 设置吐司位置
setBgColor     : 设置背景颜色
setBgResource  : 设置背景资源
setMessageColor: 设置消息颜色
showShort      : 显示短时吐司
showLong       : 显示长时吐司
showCustomShort: 显示短时自定义吐司
showCustomLong : 显示长时自定义吐司
cancel         : 取消吐司显示
```

* ### 压缩相关→[ZipUtils.java][zip.java]→[Test][zip.test]
```
zipFile           : 压缩文件
unzipFile         : 解压文件
unzipFileByKeyword: 解压带有关键字的文件
getFilesPath      : 获取压缩文件中的文件路径链表
getComments       : 获取压缩文件中的注释链表
```



[activity.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/ActivityUtils.java
[activity.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/core/activity/ActivityActivity.java

[app.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/AppUtils.java
[app.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/core/app/AppActivity.java

[bar.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/BarUtils.java
[bar.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/core/bar/BarActivity.java

[cache.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/CacheUtils.java
[cache.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/util/CacheUtilsTest.java

[clean.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/CleanUtils.java
[clean.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/core/clean/CleanActivity.java

[close.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/CloseUtils.java

[convert.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/ConvertUtils.java
[convert.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/util/ConvertUtilsTest.java

[crash.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/CrashUtils.java

[device.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/DeviceUtils.java
[device.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/core/device/DeviceActivity.java

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
[fragment.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/core/fragment/FragmentActivity.java

[image.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/ImageUtils.java
[image.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/core/image/ImageActivity.java

[intent.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/IntentUtils.java

[keyboard.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/KeyboardUtils.java
[keyboard.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/core/keyboard/KeyboardActivity.java

[log.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/LogUtils.java
[log.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/core/log/LogActivity.java

[network.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/NetworkUtils.java
[network.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/core/network/NetworkActivity.java

[phone.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/PhoneUtils.java
[phone.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/core/phone/PhoneActivity.java

[process.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/ProcessUtils.java
[process.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/core/process/ProcessActivity.java

[regex.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/RegexUtils.java
[regex.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/util/RegexUtilsTest.java

[screen.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/ScreenUtils.java

[sdcard.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/SDCardUtils.java
[sdcard.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/core/sdcard/SDCardActivity.java

[service.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/ServiceUtils.java

[shell.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/ShellUtils.java

[size.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/SizeUtils.java

[snackbar.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/SnackbarUtils.java
[snackbar.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/core/snackbar/SnackbarActivity.java

[span.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/SpanUtils.java
[span.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/core/span/SpanActivity.java

[sp.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/SPUtils.java
[sp.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/util/SPUtilsTest.java

[string.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/StringUtils.java
[string.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/util/StringUtilsTest.java

[time.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/TimeUtils.java
[time.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/util/TimeUtilsTest.java

[toast.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/ToastUtils.java
[toast.demo]: https://github.com/Blankj/AndroidUtilCode/blob/master/app/src/main/java/com/blankj/androidutilcode/core/toast/ToastActivity.java

[zip.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/ZipUtils.java
[zip.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/util/ZipUtilsTest.java
