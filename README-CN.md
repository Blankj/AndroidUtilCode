## Android开发人员不得不收集的代码([持续更新中][update_log.md])
**[README of English][readme.md]→[一键生成英文版README函数][trans]**
***
为方便查找，已进行大致归类，其目录如下所示：
> - **Activity相关→[ActivityUtils.java][activity.java]**
 ```
isActivityExists : 判断是否存在Activity
launchActivity   : 打开Activity
 ```

> - **App相关→[AppUtils.java][app.java]**
 ```
isInstallApp          : 判断App是否安装
installApp            : 安装App(支持6.0)
installAppSilent      : 静默安装App
uninstallApp          : 卸载App
uninstallAppSilent    : 静默卸载App
isAppRoot             : 判断App是否有root权限
launchApp             : 打开App
closeApp              : 关闭App
getAppPackageName     : 获取App包名
getAppDetailsSettings : 获取App具体设置
getAppName            : 获取App名称
getAppIcon            : 获取App图标
getAppPath            : 获取App路径
getAppVersionName     : 获取App版本号
getAppVersionCode     : 获取App版本码
isSystemApp           : 判断App是否是系统应用
isAppDebug            : 判断App是否是Debug版本
getAppSignature       : 获取App签名
getAppSignatureSHA1   : 获取应用签名的的SHA1值
isAppForeground       : 判断App是否处于前台
getForegroundApp      : 获取前台应用包名
getAppInfo            : 获取App信息
getAppsInfo           : 获取所有已安装App信息
cleanAppData          : 清除App所有数据
 ```

> - **栏相关→[BarUtils.java][bar.java]**
 ```
setTransparentStatusBar : 设置透明状态栏(api大于19方可使用)
hideStatusBar           : 隐藏状态栏
getStatusBarHeight      : 获取状态栏高度
isStatusBarExists       : 判断状态栏是否存在
getActionBarHeight      : 获取ActionBar高度
showNotificationBar     : 显示通知栏
hideNotificationBar     : 隐藏通知栏
 ```

> - **清除相关→[CleanUtils.java][clean.java]**
 ```
cleanInternalCache    : 清除内部缓存
cleanInternalFiles    : 清除内部文件
cleanInternalDbs      : 清除内部数据库
cleanInternalDbByName : 根据名称清除数据库
cleanInternalSP       : 清除内部SP
cleanExternalCache    : 清除外部缓存
cleanCustomCache      : 清除自定义目录下的文件
 ```

> - **剪贴板相关→[ClipboardUtils.java][clipboard.java]**
 ```
copyText   : 复制文本到剪贴板
getText    : 获取剪贴板的文本
copyUri    : 复制uri到剪贴板
getUri     : 获取剪贴板的uri
copyIntent : 复制意图到剪贴板
getIntent  : 获取剪贴板的意图
 ```

> - **关闭相关→[CloseUtils.java][close.java]**
 ```
closeIO        : 关闭IO
closeIOQuietly : 安静关闭IO
 ```

> - **常量相关→[ConstUtils.java][const.java]**
 ```
MemoryConst : 存储相关常量
TimeConst   : 时间相关常量
RegexConst  : 正则相关常量
 ```

> - **转换相关→[ConvertUtils.java][convert.java]→[Test][convert.test]**
 ```
bytes2HexString, hexString2Bytes         : byteArr与hexString互转
chars2Bytes, bytes2Chars                 : charArr与byteArr互转
byte2Size, size2Byte                     : 字节数与unit为单位的size互转
byte2FitSize                             : 字节数转合适大小
bytes2Bits, bits2Bytes                   : bytes与bits互转
input2OutputStream, output2InputStream   : inputStream与outputStream互转
inputStream2Bytes, bytes2InputStream     : inputStream与byteArr互转
outputStream2Bytes, bytes2OutputStream   : outputStream与byteArr互转
inputStream2String, string2InputStream   : inputStream与string按编码互转
outputStream2String, string2OutputStream : outputStream与string按编码互转
bitmap2Bytes, bytes2Bitmap               : bitmap与byteArr互转
drawable2Bitmap, bitmap2Drawable         : drawable与bitmap互转
drawable2Bytes, bytes2Drawable           : drawable与byteArr互转
view2Bitmap                              : view转Bitmap
dp2px, px2dp                             : dp与px互转
sp2px, px2sp                             : sp与px互转
 ```

> - **崩溃相关→[CrashUtils.java][crash.java]**
 ```
getInstance : 获取单例
init        : 初始化
 ```

> - **设备相关→[DeviceUtils.java][device.java]**
 ```
isDeviceRoot    : 判断设备是否root
getSDKVersion   : 获取设备系统版本号
getAndroidID    : 获取设备AndroidID
getMacAddress   : 获取设备MAC地址
getManufacturer : 获取设备厂商
getModel        : 获取设备型号
shutdown        : 关机
reboot          : 重启
 ```

> - **判空相关→[EmptyUtils.java][empty.java]→[Test][empty.test]**
 ```
isEmpty    : 判断对象是否为空
isNotEmpty : 判断对象是否非空
 ```

> - **编码解码相关→[EncodeUtils.java][encode.java]→[Test][encode.test]**
 ```
urlEncode                         : URL编码
urlDecode                         : URL解码
base64Encode, base64Encode2String : Base64编码
base64Decode                      : Base64解码
base64UrlSafeEncode               : Base64URL安全编码
htmlEncode                        : Html编码
htmlDecode                        : Html解码
 ```

> - **加密解密相关→[EncryptUtils.java][encrypt.java]→[Test][encrypt.test]**
 ```
encryptMD2, encryptMD2ToString                         : MD2加密
encryptMD5, encryptMD5ToString                         : MD5加密
encryptMD5File, encryptMD5File2String                  : MD5加密文件
encryptSHA1, encryptSHA1ToString                       : SHA1加密
encryptSHA224, encryptSHA224ToString                   : SHA224加密
encryptSHA256, encryptSHA256ToString                   : SHA256加密
encryptSHA384, encryptSHA384ToString                   : SHA384加密
encryptSHA512, encryptSHA512ToString                   : SHA512加密
encryptHmacMD5, encryptHmacMD5ToString                 : HmacMD5加密
encryptHmacSHA1, encryptHmacSHA1ToString               : HmacSHA1加密
encryptHmacSHA224, encryptHmacSHA224ToString           : HmacSHA224加密
encryptHmacSHA256, encryptHmacSHA256ToString           : HmacSHA256加密
encryptHmacSHA384, encryptHmacSHA384ToString           : HmacSHA384加密
encryptHmacSHA512, encryptHmacSHA512ToString           : HmacSHA512加密
encryptDES, encryptDES2HexString, encryptDES2Base64    : DES加密
decryptDES, decryptHexStringDES, decryptBase64DES      : DES解密
encrypt3DES, encrypt3DES2HexString, encrypt3DES2Base64 : 3DES加密
decrypt3DES, decryptHexString3DES, decryptBase64_3DES  : 3DES解密
encryptAES, encryptAES2HexString, encryptAES2Base64    : AES加密
decryptAES, decryptHexStringAES, decryptBase64AES      : AES解密
 ```

> - **文件相关→[FileUtils.java][file.java]→[Test][file.test]**
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
listFilesInDir            : 获取目录下所有文件
listFilesInDir            : 获取目录下所有文件包括子目录
listFilesInDirWithFilter  : 获取目录下所有后缀名为suffix的文件
listFilesInDirWithFilter  : 获取目录下所有后缀名为suffix的文件包括子目录
listFilesInDirWithFilter  : 获取目录下所有符合filter的文件
listFilesInDirWithFilter  : 获取目录下所有符合filter的文件包括子目录
searchFileInDir           : 获取目录下指定文件名的文件包括子目录
writeFileFromIS           : 将输入流写入文件
writeFileFromString       : 将字符串写入文件
getFileCharsetSimple      : 简单获取文件编码格式
getFileLines              : 获取文件行数
readFile2List             : 指定编码按行读取文件到List
readFile2SB               : 指定编码按行读取文件到StringBuilder中
getFileSize               : 获取文件大小
getFileMD5, getFileMD5ToString                : 获取文件的MD5校验码
getDirName                : 根据全路径获取最长目录
getFileName               : 根据全路径获取文件名
getFileNameNoExtension    : 根据全路径获取文件名不带拓展名
getFileExtension          : 根据全路径获取文件拓展名
 ```

> - **图片相关→[ImageUtils.java][image.java]**
 ```
bitmap2Bytes, bytes2Bitmap       : bitmap与byteArr互转
drawable2Bitmap, bitmap2Drawable : drawable与bitmap互转
drawable2Bytes, bytes2Drawable   : drawable与byteArr互转
getBitmap                        : 获取bitmap
scale                            : 缩放图片
clip                             : 裁剪图片
skew                             : 倾斜图片
rotate                           : 旋转图片
getRotateDegree                  : 获取图片旋转角度
toRound                          : 转为圆形图片
toRoundCorner                    : 转为圆角图片
fastBlur                         : 快速模糊
renderScriptBlur                 : renderScript模糊图片
stackBlur                        : stack模糊图片
addFrame                         : 添加颜色边框
addReflection                    : 添加倒影
addTextWatermark                 : 添加文字水印
addImageWatermark                : 添加图片水印
toAlpha                          : 转为alpha位图
toGray                           : 转为灰度图片
save                             : 保存图片
isImage                          : 根据文件名判断文件是否为图片
getImageType                     : 获取图片类型
compressByScale                  : 按缩放压缩
compressByQuality                : 按质量压缩
compressBySampleSize             : 按采样大小压缩
 ```

> - **意图相关→[IntentUtils.java][intent.java]**
 ```
getInstallAppIntent         : 获取安装App(支持6.0)的意图
getUninstallAppIntent       : 获取卸载App的意图
getLaunchAppIntent          : 获取打开App的意图
getAppDetailsSettingsIntent : 获取App具体设置的意图
getShareTextIntent          : 获取分享文本的意图
getShareImageIntent         : 获取分享图片的意图
getComponentIntent          : 获取其他应用组件的意图
getShutdownIntent           : 获取关机的意图
getCaptureIntent            : 获取拍照的意图
 ```

> - **键盘相关→[KeyboardUtils.java][keyboard.java]**
 ```
hideSoftInput                : 动态隐藏软键盘
clickBlankArea2HideSoftInput : 点击屏幕空白区域隐藏软键盘(注释萌萌哒)
showSoftInput                : 动态显示软键盘
toggleSoftInput              : 切换键盘显示与否状态
 ```

> - **日志相关→[LogUtils.java][log.java]→[Test][log.test]**
 ```
init       : 初始化函数
getBuilder : 获取LogUtils建造者
v          : Verbose日志
d          : Debug日志
i          : Info日志
w          : Warn日志
e          : Error日志
 ```

> - **网络相关→[NetworkUtils.java][network.java]**
 ```
openWirelessSettings               : 打开网络设置界面
isConnected                        : 判断网络是否连接
isAvailableByPing                  : 判断网络是否可用
getDataEnabled                     : 判断移动数据是否打开
setDataEnabled                     : 打开或关闭移动数据
is4G                               : 判断网络是否是4G
getWifiEnabled                     : 判断wifi是否打开
setWifiEnabled                     : 打开或关闭wifi
isWifiConnected                    : 判断wifi是否连接状态
isWifiAvailable                    : 判断wifi数据是否可用
getNetworkOperatorName             : 获取移动网络运营商名称
getNetworkType, getNetworkTypeName : 获取当前的网络类型(WIFI, 2G, 3G, 4G)
getIPAddress                       : 获取IP地址
getDomainAddress                   : 获取域名ip地址
 ```

> - **手机相关→[PhoneUtils.java][phone.java]**
 ```
isPhone                                 : 判断设备是否是手机
getIMEI                                 : 获取IMIE码
getIMSI                                 : 获取IMSI码
getPhoneType                            : 获取移动终端类型
getSimOperatorName, getSimOperatorByMnc : 获取Sim卡运营商名称
getPhoneStatus                          : 获取手机状态信息
dial                                    : 跳至拨号界面
call                                    : 拨打phoneNumber
sendSms                                 : 跳至发送短信界面
sendSmsSilent                           : 发送短信
getAllContactInfo                       : 获取手机联系人
getContactNum                           : 打开手机联系人界面点击联系人后便获取该号码(注释萌萌哒)
getAllSMS                               : 获取手机短信并保存到xml中
 ```

> - **进程相关→[ProcessUtils.java][process.java]**
 ```
getForegroundProcessName                             : 获取前台线程包名
cleanAllBackgroundProcesses，cleanBackgroundProcesses : 清理后台服务进程
 ```

> - **正则相关→[RegexUtils.java][regex.java]→[Test][regex.test]**
 ```
isMobileSimple : 验证手机号（简单）
isMobileExact  : 验证手机号（精确）
isTel          : 验证电话号码
isIDCard15     : 验证身份证号码15位
isIDCard18     : 验证身份证号码18位
isEmail        : 验证邮箱
isURL          : 验证URL
isChz          : 验证汉字
isUsername     : 验证用户名
isDate         : 验证yyyy-MM-dd格式的日期校验，已考虑平闰年
isIP           : 验证IP地址
isMatch        : string是否匹配regex
 ```

> - **屏幕相关→[ScreenUtils.java][screen.java]**
 ```
getDeviceWidth, getDeviceHeight                 : 获取手机分辨率
setTransparentStatusBar                         : 设置透明状态栏(api大于19方可使用)
hideStatusBar                                   : 隐藏状态栏(注释萌萌哒)
getStatusBarHeight                              : 获取状态栏高度
isStatusBarExists                               : 判断状态栏是否存在
getActionBarHeight                              : 获取ActionBar高度
showNotificationBar                             : 显示通知栏
hideNotificationBar                             : 隐藏通知栏
setLandscape                                    : 设置屏幕为横屏(注释萌萌哒)
snapShotWithStatusBar, snapShotWithoutStatusBar : 获取屏幕截图
isScreenLock                                    : 判断是否锁屏
 ```

> - **SD卡相关→[SDCardUtils.java][sdcard.java]**
 ```
isSDCardEnable : 判断SD卡是否可用
getSDCardPath  : 获取SD卡路径
getDataPath    : 获取SD卡Data路径
getFreeSpace   : 计算SD卡的剩余空间
getSDCardInfo  : 获取SD卡信息
 ```

> - **服务相关→[ServiceUtils.java][service.java]**
 ```
isServiceRunning : 判断服务是否运行
stopService      : 停止服务
 ```

> - **Shell相关→[ShellUtils.java][shell.java]**
 ```
execCmd : 是否是在root下执行命令
 ```

> - **尺寸相关→[SizeUtils.java][size.java]**
 ```
dp2px, px2dp     : dp与px转换
sp2px, px2sp     : sp与px转换
applyDimension   : 各种单位转换
forceGetViewSize : 在onCreate()即可强行获取View的尺寸
measureView      : ListView中提前测量View尺寸(注释萌萌哒)
 ```

> - **Snackbar相关→[SnackbarUtils.java][snackbar.java]**
 ```
showShortSnackbar      : 显示短时snackbar
showLongSnackbar       : 显示长时snackbar
showIndefiniteSnackbar : 显示自定义时长snackbar
addView                : 为SnackBar添加布局
 ```

> - **SP相关→[SPUtils.java][sp.java]→[Test][sp.test]**
 ```
SPUtils    : SPUtils构造函数
putString  : SP中写入String类型value
getString  : SP中读取String
putInt     : SP中写入int类型value
getInt     : SP中读取int
putLong    : SP中写入long类型value
getLong    : SP中读取long
putFloat   : SP中写入float类型value
getFloat   : SP中读取float
putBoolean : SP中写入boolean类型value
getBoolean : SP中读取boolean
getAll     : SP中获取所有键值对
remove     : SP中移除该key
contains   : SP中是否存在该key
clear      : SP中清除所有数据
 ```

> - **字符串相关→[StringUtils.java][string.java]→[Test][string.test]**
 ```
isEmpty          : 判断字符串是否为null或长度为0
isSpace          : 判断字符串是否为null或全为空格
equals           : 判断两字符串是否相等
null2Length0     : null转为长度为0的字符串
length           : 返回字符串长度
upperFirstLetter : 首字母大写
lowerFirstLetter : 首字母小写
reverse          : 反转字符串
toDBC            : 转化为半角字符
toSBC            : 转化为全角字符
getPYFirstLetter : 获得第一个汉字首字母
cn2PY            : 中文转拼音
 ```

> - **线程池相关→[ThreadPoolUtils.java][thread_pool.java]**
 ```
ThreadPoolUtils                               : ThreadPoolUtils构造函数
execute                                       : 在未来某个时间执行给定的命令
execute                                       : 在未来某个时间执行给定的命令链表
shutDown                                      : 待以前提交的任务执行完毕后关闭线程池
shutDownNow                                   : 试图停止所有正在执行的活动任务
isShutDown                                    : 判断线程池是否已关闭
isTerminated                                  : 关闭线程池后判断所有任务是否都已完成
awaitTermination                              : 请求关闭、发生超时或者当前线程中断
submit                                        : 提交一个Callable任务用于执行
submit                                        : 提交一个Runnable任务用于执行
invokeAll, invokeAny                          : 执行给定的任务
schedule                                      : 延迟执行Runnable命令
schedule                                      : 延迟执行Callable命令
scheduleWithFixedRate, scheduleWithFixedDelay : 延迟并循环执行命令
 ```

> - **时间相关→[TimeUtils.java][time.java]→[Test][time.test]**
 ```
milliseconds2String                               : 将时间戳转为时间字符串
string2Milliseconds                               : 将时间字符串转为时间戳
string2Date                                       : 将时间字符串转为Date类型
date2String                                       : 将Date类型转为时间字符串
date2Milliseconds                                 : 将Date类型转为时间戳
milliseconds2Date                                 : 将时间戳转为Date类型
milliseconds2Unit                                 : 毫秒时间戳单位转换（单位：unit）
getIntervalTime                                   : 获取两个时间差（单位：unit）
getCurTimeMills, getCurTimeString, getCurTimeDate : 获取当前时间
getIntervalByNow                                  : 获取与当前时间的差（单位：unit）
isLeapYear                                        : 判断闰年
getWeek, getWeekIndex                             : 获取星期
getWeekOfMonth                                    : 获取月份中的第几周
getWeekOfYear                                     : 获取年份中的第几周
 ```

> - **吐司相关→[ToastUtils.java][toast.java]**
 ```
init               : 吐司初始化
showShortToastSafe : 安全地显示短时吐司
showLongToastSafe  : 安全地显示长时吐司
showShortToast     : 显示短时吐司
showLongToast      : 显示长时吐司
cancelToast        : 取消吐司显示
 ```

> - **压缩相关→[ZipUtils.java][zip.java]→[Test][zip.test]**
 ```
zipFiles           : 批量压缩文件
zipFile            : 压缩文件
unzipFiles         : 批量解压文件
unzipFile          : 解压文件
unzipFileByKeyword : 解压带有关键字的文件
getFilesPath       : 获取压缩文件中的文件路径链表
getComments        : 获取压缩文件中的注释链表
getEntries         : 获取压缩文件中的文件对象
 ```

> - **更新Log→[update_log.md][update_log.md]**

***

**做这份整理是想把它作为Android开发的小字典，当遇到一些琐碎问题时，不用再面向百度或者谷歌查询API的使用，费时费力，这里有的话，大家尽管撸走；同时也希望它能逐日壮大起来，期待大家的Star和完善，当然我也会一直更新发布版本和日志，为了方便大家导入，现已上传jcenter；其中很多代码也是汇四方之精华，谢谢前辈们的提供，当然最终还是要通过单元测试的，如有错误，请及时告之；开设QQ群提供讨论，群号：74721490，至于验证问题对大家来说肯定都是小case；最近在玩微博，玩的话向大家求个[关注][weibo]**

### Download
***
Gradle:
``` groovy
compile 'com.blankj:utilcode:1.3.1'
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

[update_log.md]: https://github.com/Blankj/AndroidUtilCode/blob/master/update_log.md

[readme.md]: https://github.com/Blankj/AndroidUtilCode
[readme-cn.md]: https://github.com/Blankj/AndroidUtilCode/blob/master/README-CN.md

[trans]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/TestUtils.java

[activity.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ActivityUtils.java

[app.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/AppUtils.java

[bar.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/BarUtils.java

[clean.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/CleanUtils.java

[clipboard.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ClipboardUtils.java

[close.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/CloseUtils.java

[const.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ConstUtils.java

[convert.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ConvertUtils.java
[convert.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/ConvertUtilsTest.java

[crash.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/CrashUtils.java

[device.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/DeviceUtils.java

[empty.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/EmptyUtils.java
[empty.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/EmptyUtilsTest.java

[encode.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/EncodeUtils.java
[encode.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/EncodeUtilsTest.java

[encrypt.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/EncryptUtils.java
[encrypt.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/EncryptUtilsTest.java

[file.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/FileUtils.java
[file.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/FileUtilsTest.java

[image.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ImageUtils.java

[intent.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/IntentUtils.java

[keyboard.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/KeyboardUtils.java

[log.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/LogUtils.java
[log.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/LogUtilsTest.java

[network.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/NetworkUtils.java

[phone.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/PhoneUtils.java

[regex.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/RegexUtils.java
[regex.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/RegexUtilsTest.java

[screen.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ScreenUtils.java

[sdcard.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/SDCardUtils.java

[service.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ServiceUtils.java

[shell.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ShellUtils.java

[size.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/SizeUtils.java

[snackbar.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/SnackbarUtils.java

[sp.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/SPUtils.java
[sp.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/SPUtilsTest.java

[string.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/StringUtils.java
[string.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/StringUtilsTest.java

[thread_pool.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ThreadPoolUtils.java

[time.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/TimeUtils.java
[time.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/TimeUtilsTest.java

[toast.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ToastUtils.java

[zip.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ZipUtils.java
[zip.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/ZipUtilsTest.java

[weibo]: http://weibo.com/blankcmj
