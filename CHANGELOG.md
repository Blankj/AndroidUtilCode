* `19/03/08` [add] LogUtils support multi process. Publish v1.23.7.
* `19/03/02` [fix] LogUtils#file.
* `19/02/28` [fix] ImageUtils#calculateInSampleSize. Publish v1.23.6.
* `19/02/26` [fix] UriUtils#uri2File. Publish v1.23.5.
* `19/01/31` [add] HttpUtils.
* `19/01/30` [add] RomUtils. Publish v1.23.4.
* `19/01/29` [fix] LogUtils format json when json not start with '{'. Publish v1.23.3.
* `19/01/28` [fix] KeyboardUtils#fixSoftInputLeaks don't work on the device of HuaWei.
* `19/01/26` [fix] NetworkUtils#getNetworkType.
* `19/01/25` [add] CloneUtils, PermissionUtils support request permission of WRITE_SETTINGS and DRAW_OVERLAYS. Publish v1.23.2.
* `19/01/24` [add] BrightnessUtils and FlashlightUtils.
* `19/01/23` [add] Modify the demo of utilcode use kotlin. Publish v1.23.1.
* `19/01/22` [fix] AppUtils#installApp.
* `19/01/17` [fix] Publish v1.23.0.
* `19/01/16` [fix] BarUtils get Activity from view and delete the function of set status bar alpha.
* `19/01/15` [add] ColorUtils.
* `19/01/04` [add] CacheDiskStaticUtils, CacheDoubleStaticUtils, CacheMemoryStaticUtils.
* `19/01/03` [add] SPStaticUtils.
* `19/01/02` [fix] LogUtils log object. Publish v1.22.10.
* `19/01/01` [add] GsonUtils.
* `18/12/29` [add] AntiShakeUtils and VibrateUtils. Publish v1.22.9.
* `18/12/28` [fix] ToastUtils show behind the dialog when close notification.
* `18/12/27` [fix] LogUtils print StringBuilder failed.
* `18/12/24` [fix] Utils$ActivityLifecycleImpl.consumeOnActivityDestroyedListener ConcurrentModificationException. Publish v1.22.7.
* `18/12/22` [fix] AdaptScreenUtils#pt2px don't work when start webview. Publish v1.22.6.
* `18/12/21` [add] LogUtils support print Map, Collection and Object to String.
* `18/12/19` [fix] AdaptScreenUtils don't work in MIUI on Android 5.1.1. Publish v1.22.5.
* `18/12/18` [fix] ToastUtils multi show crash when run API 25. Publish v1.22.4.
* `18/12/18` [fix] ImageUtils recycle ret equals src. Publish v1.22.3.
* `18/12/17` [fix] Utils$FileProvider4UtilCode not found. Publish v1.22.3.
* `18/12/17` [fix] ToastUtils leak. Publish v1.22.2.
* `18/12/09` [add] Component for the project.
* `18/12/04` [add] BusUtils. Publish v1.22.1.
* `18/11/18` [fix] ToastUtils don't show in the devices grater than API 24 when close the permission of notification. Publish v1.22.0.
* `18/11/17` [fix] AppUtils#isAppInstalled don't work in no launcher app.
* `18/11/16` [fix] ThreadUtils#cancel block the main thread.
* `18/11/15` [add] module of bus-gradle-plugin and change style of gradle.
* `18/11/14` [add] BusUtils.
* `18/11/13` [add] AdaptScreenUtils.
* `18/11/12` [fix] SPUtils bug when use in multi threads.
* `18/10/25` [fix] SpanUtils#setLineHeight bug of multi lines. Publish v1.21.2.
* `18/10/24` [fix] SpanUtils#appendImage on VIVO. Publish v1.21.1.
* `18/10/16` [add] BusUtils, DeviceUtils#isAdbEnabled. Publish v1.21.0.
* `18/09/29` [fix] ToastUtils which causes crash in the some devices of Xiaomi. Publish v1.20.4.
* `18/09/13` 修复 ToastUtils 在小米手机显示 Toast 带有 App 名，发布 1.20.3
* `18/09/12` 修复 KeyBoardUtils#fixAndroidBug5497，完善 ToastUtils，发布 1.20.2
* `18/09/11` 新增 BarUtils#isSupportNavBar，删除 BarUtils#setNavBarImmersive
* `18/09/10` 修复 KeyboardUtils 中 getWindowVisibleDisplayFrame 空指针异常，更新 BarUtils#isNavBarVisible
* `18/09/06` 新增 PathUtils，发布 1.20.1
* `18/09/05` 新增 KeyboardUtils#showSoftInputUsingToggle 和 KeyboardUtils#hideSoftInputUsingToggle
* `18/09/04` 修复 SHA 算法名
* `18/09/03` 新增 MetaDataUtils，发布 1.20.0
* `18/08/30` 修复 PermissionUtils$PermissionActivity 的 window 背景为黑色的问题，发布 1.19.4
* `18/08/28` 新增 RegexUtils#isIDCard18Exact
* `18/08/26` 新增 AppUtils#getAppSignatureSHA256 和 AppUtils#getAppSignatureMD5，发布 1.19.3
* `18/08/24` 新增 ScreenUtils#restoreAdaptScreen，利用 FileProvider4UtilCode 不再需要初始化，发布 1.19.2
* `18/08/23` 修复 适配后 ToastUtils 原生 Toast 尺寸发生改变的问题，修复 KeyboardUtils#fixSoftInputLeaks，发布 1.19.1
* `18/08/10` 修复 ScreenUtils#adaptxx 导致获取状态栏和导航栏尺寸不对问题，发布 1.19.0
* `18/08/09` 新增 IntentUtils#isIntentAvailable，ToastUtils 传入空显示 null，发布 1.18.6
* `18/08/08` 修复 ScreenUtils#adaptxx 在第三方 SDK 会出现的问题，发布 1.18.5
* `18/08/07` 修复 ScreenUtils#adaptxx 在 API 26 以下无效的 bug，发布 1.18.4
* `18/08/06` 修复 ScreenUtils#screenShot 中 decorView.getDrawingCache() 为空的问题，发布 1.18.3
* `18/08/05` 修复 1.18.0 版本删去 `if (activity.getClass() == PermissionUtils.PermissionActivity.class) return;` 造成 PermissionUtils 获取栈顶 Activity 问题，发布 1.18.2
* `18/08/04` 新增 LogUtils#Config#setSaveDays，发布 1.18.1
* `18/08/03` 新增 LogUtils#Config#addFormatter，并新增 Array, Throwable, Bundle, Intent 的格式化输出
* `18/08/02` 修复 TimeUtils 中的 SimpleDateFormat 为 ThreadLocal 实现
* `18/08/01` 删除 标记废弃的 CacheUtils, AppUtils#installApp, TimeUtils#getWeekIndex，发布 1.18.0
* `18/07/30` 替换 ScreenUtils#adaptPortraitScreen 和 ScreenUtils#adaptLandscapeScreen，为 ScreenUtils#adaptScreen4VerticalSlide 和 ScreenUtils#adaptScreen4HorizontalSlide
* `18/07/28` 修复 NetworkUtils#getIPAddress
* `18/07/16` 新增 ScreenUtils#adaptPortraitScreen 和 ScreenUtils#adaptLandscapeScreen，发布 1.17.3
* `18/07/13` 修复 IntentUtils 分享图片判断逻辑，CacheDiskUtils 可放入 byte[0]
* `18/06/29` 修复 FragmentUtils 中 getFragmentManager 空指针错误，发布 1.17.2
* `18/06/27` 新增 UriUtils#uri2File
* `18/06/25` 新增 KeyboardUtils#fixAndroidBug5497，发布 1.17.1 版本
* `18/06/21` 修复 FragmentUtils#add 死循环的 BUG
* `18/06/14` 替换 CacheUtils 为 CacheDiskUtils，CacheUtils 标记 deprecated，发布 1.17.0 版本
* `18/06/13` 新增 CacheMemoryUtils 和 CacheDoubleUtils
* `18/06/12` 完善 FragmentUtils#add 和 replace 新增 tag
* `18/05/30` 完善 DeviceUtils#getMacAddress，发布 1.16.4 版本
* `18/05/30` 修复 ToastUtils 在 targetSdkVersion 为 27 在 api 25 机器快速 show 两次崩溃的异常，发布 1.16.3 版本
* `18/05/29` 完善 TimeUtils 的 timeSpan 带符号位，ToastUtils 去除弱引用，发布 1.16.2 版本
* `18/05/25` 新增 AppUtils#registerAppStatusChangedListener 和 AppUtils#unregisterAppStatusChangedListener，发布 1.16.1 版本
* `18/05/22` 新增 ThreadUtils，发布 1.16.0 版本
* `18/05/15` 新增 MetaDataUtils 和 ActivityUtils#startActivityForResult，发布 1.15.1 版本
* `18/05/08` 新增 ResourceUtils，发布 1.15.0 版本
* `18/05/07` 修复 ZipUtils 漏洞，发布 1.14.4 版本
* `18/05/03` 修复 ToastUtils 默认字体大小问题，发布 1.14.3 版本
* `18/05/02` 修复 PermissionUtils 空异常，发布 1.14.2 版本
* `18/04/28` 新增 FlashlightUtils，发布 1.14.1 版本
* `18/04/26` 修复 KeyboardUtils 全屏 NO_LIMIT 的 bug
* `18/04/25` 修复 多个空异常
* `18/04/24` 修复 多 FileProvider 带来的问题，发布 1.14.0 版本
* `18/04/23` 新增 RSA 加解密，发布 1.13.16 版本
* `18/04/22` 新增 LogUtils 设置栈偏移
* `18/04/21` 新增 AppUtils#relaunchApp、DeviceUtils#getABIs，发布 1.13.15 版本
* `18/04/20` 新增 BarUtils#setNavBarColor、BarUtils#getNavBarColor
* `18/04/19` 新增 Process#isMainProcess、Process#getCurrentProcessName，发布 1.13.14 版本
* `18/04/18` 修复 LogUtils 头部空指针异常，SPUtils、CacheUtils 存储空值异常，发布 1.13.13 版本
* `18/04/17` 修复 ToastUtils 内存泄漏问题，感谢 [LambertCoding](https://github.com/LambertCoding)，发布 1.13.12 版本
* `18/04/16` 完善 AppUtils#installAppSilent 路径包含空格问题，发布 1.13.11 版本
* `18/04/10` 完善 OnCrashListener 回调崩溃信息，发布 1.13.10 版本
* `18/04/09` 修复 静默安装重载错误，发布 1.13.9 版本
* `18/04/08` 修复 获取栈顶 Activity 链表为空的异常，获取栈顶 Activity 放到 Utils 中，发布 1.13.8 版本
* `18/04/06` 新增 GsonUtils 及单元测试
* `18/04/05` 完善 README 文档
* `18/04/03` 修复 LogUtils 在 Android Studio 3.1 版本日志丑陋的问题，发布 1.13.7 版本
* `18/03/29` 兼容 Utils 的初始化传入 application，发布 1.13.6 版本
* `18/03/20` 修复 PermissionUtils 子进程的问题，发布 1.13.5 版本
* `18/03/16` 新增 gradle 插件来格式化 README
* `18/03/14` 修复 KeyboardUtils#getContentViewInvisibleHeight，发布 1.13.4 版本
* `18/03/10` 完善 AppUtils#installAppSilent 和 DeviceUtils#getMacAddress，发布 1.13.3 版本
* `18/03/09` 完善 ActivityUtils#getTopActivity
* `18/03/08` 新增 反射获取栈顶 Activity 的方法，发布 1.13.2 版本
* `18/03/07` 修复 PermissionUtils 请求权限为 0 的 崩溃
* `18/03/05` 修复 Library Source does not match the bytecode for class LogUtils 问题，发布 1.13.1 版本
* `18/03/04` 完善 Javadoc 中文版为英文版，发布 1.13.0 版本
* `18/03/04` 完善 Javadoc 中文版为英文版
* `18/03/03` 完善 Javadoc 中文版为英文版
* `18/03/02` 完善 Javadoc 中文版为英文版
* `18/03/01` 完善 Javadoc 中文版为英文版
* `18/02/28` 完善 Javadoc 中文版为英文版
* `18/02/27` 完善 Javadoc 中文版为英文版
* `18/02/26` 完善 Javadoc 中文版为英文版
* `18/02/25` 完善 Javadoc 中文版为英文版
* `18/02/24` 完善 Javadoc 中文版为英文版
* `18/02/23` 完善 Javadoc 中文版为英文版
* `18/02/22` 完善 Javadoc 中文版为英文版
* `18/02/21` 完善 Javadoc 中文版为英文版
* `18/02/10` 完善 Javadoc 中文版为英文版
* `18/02/09` 完善 非空转换插件 traute 的使用方式
* `18/02/08` 修复 ActivityUtils option 低版本为空的异常
* `18/01/31` 修复 default 相关的逻辑错误，发布 1.12.4，修复 ToastUtils 在 kotlin 中转义失败，发布 1.12.5
* `18/01/28` 修复 ToastUtils 默认样式问题，发布 1.12.2，新增 DeviceUtils#getSDKVersionName，发布 1.12.3
* `18/01/27` 修复 PermissionUtils 某些机型闪烁问题，发布 1.12.1
* `18/01/17` 完善 ReflectUtils 及单元测试，发布 1.12.0 版本
* `18/01/16` 完善 ReflectUtils 及单元测试
* `18/01/15` 完善 ReflectUtils 及单元测试
* `18/01/14` 完善 ReflectUtils 及单元测试
* `18/01/13` 完善 ReflectUtils 及单元测试
* `18/01/12` 完善 ReflectUtils 及单元测试
* `18/01/11` 修复 ImageUtils 的 fastBlur radius 为 1 recycle 的 bug，新增 CrashUtils 初始化崩溃监听事件，发布 1.11.1 版本
* `18/01/10` 完善 PermissionUtils 及 readme，发布 1.11.0 版本
* `18/01/09` 完善 demo 动态权限适配
* `18/01/08` 新增 SPActivity，删除 SPUtils 的单元测试
* `18/01/08` 修复 ToastUtils 在 SDK 为 18 的自定义 toast 崩溃问题
* `18/01/07` 新增 PermissionUtils 的 Demo
* `18/01/06` 修复 权限相关工具类内存泄漏问题
* `18/01/05` 新增 获取 Activity icon 和 logo
* `18/01/04` 完善 6.0 动态权限相关工具类
* `18/01/03` 完善 6.0 动态权限相关工具类
* `18/01/02` 完善 6.0 动态权限相关工具类
* `18/01/01` 新增 6.0 动态权限相关工具类
* `17/12/30` 删除 SpanUtils 中设置图标
* `17/12/29` 完善 SpanUtils 的 appendImage 对齐方式
* `17/12/28` 完善 ScreenUtils 设置全屏的方式，发布 1.10.0
* `17/12/26` 新增 状态栏、导航栏设置是否可见和判断是否可见
* `17/12/22` 新增 注册软键盘改变监听器、注册导航栏改变监听器方法
* `17/12/21` 完善 获取屏幕宽高，修复行宽度大于 100 字符
* `17/12/20` 修复 SpanUtils 图标的 bug，不高于 6.0 的版本不支持居中和底部对齐
* `17/12/19` 修复 SpanUtils 多图的 bug
* `17/12/15` 新增 ReflectUtils
* `17/12/14` 完善 手机号（精确）正则，发布 1.9.12
* `17/12/12` 完善 LogUtils，当最终日志长度为 0 时，输出 log nothing
* `17/12/11` 完善 ActivityUtils 的 finish 系列，发布 1.9.11
* `17/12/04` 完善 LogUtils 边框改为单线清爽型
* `17/11/30` 修复 ToastUtils 背景问题，发布 1.9.10
* `17/11/30` 修复 ToastUtils 获取背景为空，发布 1.9.9
* `17/11/28` 修复 EmptyUtils 对 CharSequence 的判断，感谢 jiezigg
* `17/11/24` 新增 readme 格式化的 gradle 脚本
* `17/11/15` 完善 资源分包位置，使其更合理
* `17/11/10` 完善 LogUtils 新增日志头部，感谢 Kanade
* `17/11/07` 完善 LogUtils 无 tag 的多参数
* `17/11/06` 修复 LogUtils 多参数打印失败的问题
* `17/11/01` 完善 ShellUtil 的 Msg 换行，感谢香脆的大鸡排
* `17/10/30` 完善 README
* `17/10/29` 修复 6.0 内部存储安装失败问题
* `17/10/28` 完善 compile 为 implementation, provided 为 compileOnly
* `17/10/27` 修复 兼容 AS3.0
* `17/10/27` 修复 LogUtils 在 kotlin 中使用的问题
* `17/10/25` 修复 LogUtils 边框，修复 getBitmap 从流获取
* `17/09/30` 完善 FragmentUtils，发布 1.9.2
* `17/09/29` 完善 FragmentUtils 和 isInstallApp
* `17/09/28` 完善 FragmentUtils
* `17/09/27` 完善 FragmentUtils
* `17/09/26` 完善 ActivityUtils 及 Demo，发布 1.9.1
* `17/09/25` 完善 ActivityUtils 及 Demo
* `17/09/24` 完善 ActivityUtils 及 Demo
* `17/09/23` 完善 FragmentUtils
* `17/09/19` 修复 CrashUtils 自定义路径错误
* `17/09/18` 完善 ImageUtils 的 Demo
* `17/09/17` 完善 ImageUtils 的 compress
* `17/09/13` 完善 ImageUtils 的 addBorder
* `17/09/13` 完善 ImageUtils 的 toRound
* `17/09/13` 完善 ImageUtils 和 LogUtils
* `17/09/12` 完善 ImageUtils
* `17/09/10` 完善 单元测试
* `17/09/08` 完善 单元测试
* `17/09/06` 完善 SDCardUtils 获取 SD 卡路径，完善 SPUtils 新增 commit
* `17/09/05` 完善 LogUtils，发布版本 1.9.0
* `17/09/04` 完善 ToastUtils，去除相关 safe 函数，都改为 safe 实现，新增 CustomToast 的 Demo
* `17/09/02` 完善 ToastUtils，去除引入 view 带来的问题，发布版本 1.8.6
* `17/08/30` 修复 ToastUtils 弱引用带来的问题，修复 CacheUtils 异步问题，发布版本 1.8.5
* `17/08/28` 修复 ToastUtils 内存泄露，新增 toast 可根据系统字体显示不同字体，发布版本 1.8.4
* `17/08/20` 新增 监听 Activity 生命周期，退出 App，发布版本 1.8.3
* `17/08/11` 完善 LogUtils 的 Builder 改为 Config，发布版本 1.8.2
* `17/08/10` 完善 FileUtils 的 deleteFilesInDir 和 listFilesInDir
* `17/08/08` 新增 反射工具类 ReflectUtils
* `17/08/06` 完善 为按功能分包，增加 subutil 的 Demo
* `17/07/31` 修复 NetworkUtils 的 isAvailableByPing 循环递归，发布 1.8.1
* `17/07/31` 完善 BarUtils，发布 1.8.0
* `17/07/31` 完善 BarUtils
* `17/07/30` 完善 BarUtils
* `17/07/29` 完善 BarUtils
* `17/07/28` 完善 BarUtils
* `17/07/27` 完善 BarUtils
* `17/07/26` 完善 ActivityUtils
* `17/07/25` 完善 BarUtils，更新布局文件
* `17/07/24` 完善 BarUtils
* `17/07/23` 完善 BarUtils
* `17/07/22` 完善 BarUtils
* `17/07/21` 完善 xml 文件的格式化
* `17/07/17` 完善 NetworkUtils 的 isAvailableByPing 函数新增 ip 参数
* `17/07/14` 修复 FragmentUtils 的 FragmentNode 为 public
* `17/07/11` 完善 将不常用的工具类放在 subutil 中
* `17/07/10` 新增 subutil 库
* `17/07/07` 修复 TimeUtils 中获取当天零点的 bug
* `17/07/02` 完善 BarUtils 的 Demo
* `17/07/01` 完善 BarUtils 的 Demo
* `17/06/30` 完善 BarUtils 的 Demo
* `17/06/29` 新增 README logo
* `17/06/28` 新增 返回键及右划返回
* `17/06/27` 新增 Toolbar
* `17/06/26` 新增 final 参数
* `17/06/23` 完善 Demo 主页
* `17/06/20` 完善 ToastUtil, SnackbarUtils 新增设置底边距
* `17/06/17` 删除 HandlerUtils
* `17/06/16` 新增 insight.io 的 bandage
* `17/06/14` 完善 LogUtils 回退栈，发布 1.7.1 版本
* `17/06/13` 完善 Snackbar 和 Toast 的 Demo
* `17/06/12` 完善 Snackbar 为建造者模式
* `17/06/11` 完善 SpanUtils，发布版本 1.7.0
* `17/06/08` 完善 SpanUtils
* `17/06/07` 完善 SpannableStringUtils 改名为 SpanUtils，即将完工
* `17/06/06` 完善 SpannableStringUtils
* `17/06/05` 完善 SpannableStringUtils
* `17/06/04` 完善 SpannableStringUtils
* `17/06/03` 完善 SpannableStringUtils
* `17/06/02` 完善 SpannableStringUtils
* `17/06/01` 完善 KeyBoardUtils 及 Demo
* `17/05/30` 完善 CrashUtils，发布 1.6.4
* `17/05/28` 修复 CacheUtils 的 bug，发布 1.6.3
* `17/05/27` 修复 CacheUtils 的 bug，发布 1.6.2
* `17/05/26` 完善 CacheUtils，发布 1.6.0 和 1.6.1
* `17/05/25` 完善 FileIOUtils 和 CacheUtils
* `17/05/23` 新增 读取文件到字符数组中两种方式
* `17/05/19` 新增 LogUtils 文件过滤和控制台开关
* `17/05/16` 新增 ActivityUtils 动画
* `17/05/12` 新增 base 系列
* `17/05/11` 修复 SpannableStringUtils 的 setDrawable 的 bug，发布 1.5.1
* `17/05/10` 完善 7.0 安装 App，完善 AppActivity
* `17/05/09` 完善 TimeUtils 单元测试
* `17/05/08` 更新 BarUtils, LogUtils 新增配置文件，TimeUtils 将 pattern 改为 format，发布 1.5.0
* `17/05/04` 新增 签名
* `17/05/03` 修复 对齐头部日期
* `17/05/02` 完善 Demo 的 string 字符串变更，完善 ToastUtils 和 SnackbarUtils
* `17/04/27` 新增 Travis CI，使用 shields，发布 1.4.1
* `17/04/26` 完善 HandlerUtils 使用 Handler#CallBack 的回调接口及 SpannableStringUtils 图片对齐
* `17/04/24` 修复 拼写错误，修复 StringUtils 的 equalsIgnoreCase
* `17/04/23` 完善 README
* `17/04/21` 完善 TimeUtils，发布 1.4.0
* `17/04/20` 新增 SpannableStringUtils 设置字体尺寸
* `17/03/29` 修改 README
* `17/03/27` 更新 LogUtils
* `17/03/26` 更新 LogUtils
* `17/03/25` 更新 LogUtils
* `17/03/24` 完善 StringUtils
* `17/03/20` 修复 链接错误
* `17/03/19` 新增 LogUtils 栈回溯
* `17/03/14` 新增 常量包
* `17/02/14` 完善 FragmentUtils 中，Demo 测试中
* `17/02/13` 完善 FragmentUtils 中
* `17/02/12` 完善 FragmentUtils 中
* `17/02/11` 完善 FragmentUtils 中
* `17/02/10` 完善 FragmentUtils 中，LogUtils 对长度进行分割
* `17/02/09` 完善 FragmentUtils 中
* `17/02/08` 完善 FragmentUtils 中
* `17/02/07` 完善 FragmentUtils 中
* `17/02/06` 完善 FragmentUtils 中，炸断肠
* `17/02/05` 完善 FragmentUtils 中
* `17/02/04` 完善 FragmentUtils 中
* `17/02/03` 完善 FragmentUtils 中
* `17/02/02` 完善 FragmentUtils 中
* `17/02/01` 完善 FragmentUtils 中
* `17/01/24` 完善 并发布版本 1.3.6
* `17/01/16` 新增 LogUtils 打印类名函数名及所在行
* `17/12/26` 新增 阴历相关工具类
* `17/12/21` 完善 SpannableStringUtils
* `16/12/19` 完善 SpannableStringUtils
* `16/12/18` 完善 SpannableStringUtils，采用构造者模式
* `16/12/17` 完善 SpannableStringUtils
* `16/12/16` 完善 拼音工具类
* `16/12/15` 完善 拼音工具类
* `16/12/14` 新增 不低于 7.0 的 Html 解码
* `16/12/13` 新增 获取文件最后修改时间
* `16/12/12` 新增 Utils 来做初始化 context
* `16/12/10` 完善 权限中
* `16/12/09` 新增 6.0 以上权限判断
* `16/12/07` 修复升级到 6.0 bug 中
* `16/12/06` 完善 FlashlightUtils 中
* `16/12/05` 完善 FlashlightUtils 兼容 Api21 之后
* `16/12/04` 新增 FlashlightUtils
* `16/12/03` 完善 时间工具类
* `16/12/02` 新增 获取合适型时间差
* `16/12/01` 新增 获取生肖和星座
* `16/11/30` 新增 获取友好型时间差
* `16/11/23` 完善 LocationUtils 测试，发布 1.3.4
* `16/11/22` 修复 LocationActivity 内存泄漏
* `16/11/21` 完善 README
* `16/11/20` 完善 LocationUtils
* `16/11/19` 完善 SizeUtils
* `16/11/18` 完善 LocationUtils
* `16/11/17` 完善 LocationUtils
* `16/11/16` 新增 拼音工具类，单独拎出来做了整理
* `16/11/15` 完善 正则工具类
* `16/11/14` 新增 启动服务
* `16/11/13` 新增 判断 sim 卡是否准备好
* `16/11/12` 新增 重启到 recovery 和 bootloader，新增获取 launcher activity，最近一直在博客搬家，所以更得有点少
* `16/11/04` 修复 README 的缺少 process 的 bug
* `16/11/03` 修复 SnackbarUtils 中 Snackbar 持有弱引用来消除内存泄漏
* `16/11/02` 修复 内存泄漏中
* `16/11/01` 完善 发布版本 1.3.3 内存泄漏检测中
* `16/10/31` 完善 发布版本 1.3.1 和 1.3.2
* `16/10/30` 修复 获取 IpAddress 对于小米手机的 Bug
* `16/10/29` 新增 文件重命名和完善 root
* `16/10/23` 完善 测试中
* `16/10/22` 完善 测试中
* `16/10/21` 完善 测试中
* `16/10/20` 完善 测试中
* `16/10/19` 修复 判断网络是否可用
* `16/10/18` 完善 是否前台应用，完善网络状态
* `16/10/17` 修复 获取签名，完善是否前台应用，完善网络状态
* `16/10/16` 新增 SnackbarUtils
* `16/10/15` 完善 AppUtils 的 isAppForeground
* `16/10/14` 完善 README-CN 排版（强迫症一定要对齐）
* `16/10/13` 完善 测试
* `16/10/12` 新增 LogUtils 建造者模式，新增获取星期，发布版本 1.3.0，cheer
* `16/10/11` 新增 EncryptUtils 的 Hmac 系列加密
* `16/10/10` 完善 LogUtils
* `16/10/09` 完善 ToastUtils
* `16/10/08` 新增 AppUtils 静默安装和静默卸载
* `16/10/07` 完善 EmptyUtils，新增很多判空
* `16/10/05` 完善 Happy Wedding!
* `16/10/04` 完善 Readme
* `16/10/03` 修复 ConvertUtils
* `16/10/02` 完善 CrashUtils 完毕
* `16/10/01` 完善 Happy National Day!
* `16/09/30` 完善 CrashUtils
* `16/09/29` 完善 CleanUtils 测试完毕
* `16/09/28` 新增 EmptyUtils，完善 AppUtils 完毕
* `16/09/27` 新增 CleanUtils，完善 AppUtils
* `16/09/26` 新增 根据域名获取 ip 地址（在此感谢 jp1017），新增 ClipboardUtils 单元测试，对 ImageUtils 进行了 bug 修复
* `16/09/25` 新增 ClipboardUtils
* `16/09/24` 完善 AppUtils
* `16/09/23` 完善 工具类，新增 ActivityUtils、BarUtils、IntentUtils
* `16/09/22` 完善 LogUtils 中
* `16/09/21` 新增 LogUtils
* `16/09/20` 完善 昨天的单元测试
* `16/09/19` 新增 CameraUtils，新增获取中文首字母
* `16/09/18` 修复 少许代码，发布 1.2.1
* `16/09/15` 完善 Happy Mid-Autumn Festival!
* `16/09/14` 完善 ImageUtils 完毕，完善了 6.0 及以上版本安装 App 的问题，发布版本 1.2.0
* `16/09/13` 新增 英文版 README
* `16/09/12` 完善 ZipUtils 及单元测试完美谢幕（支持空文件夹）
* `16/09/11` 完善 不断更
* `16/09/10` 完善 ZipUtils 和单元测试中
* `16/09/09` 新增 字符串反转，ImageUtils 单元测试卡住中，暂时换为真机测试
* `16/09/08` 修复 NetworkUtils 报空，ImageUtils 单元测试卡住中
* `16/08/31` 完善 ImageUtils 单元测试中，之后 7 天鸡儿岭放假，停更
* `16/08/30` 完善 ImageUtils 单元测试（获取保存图片有问题，卡卡卡住了）
* `16/08/29` 完善 ImageUtils，新增 stack 模糊算法和快速模糊
* `16/08/28` 完善 ImageUtils
* `16/08/27` 完善 ConvertUtils，新增 ZipUtils
* `16/08/26` 完善 ThreadPoolUtils 线程池相关工具类
* `16/08/25` 完善 ConstUtils 时间和存储相关常量新增枚举，传参改为枚举更为友好，新增 ThreadPoolUtils 线程池相关工具类
* `16/08/24` 新增 ConvertUtils 的 InputStream 与 byte[]和 String 相互转换，应用在 FileUtils 中读文件
* `16/08/23` 修复 bug，接下来完善 SDCardUtils 和 ImageUtils
* `16/08/22` 完善 SPUtils 将 commit 改为 apply 提高效率，将 SPUtils 改为构造函数法创建，FileUtils 新增查找函数，规范 JavaDoc
* `16/08/21` 完善 FileUtils 单元测试，修复 FileUtils 的 bug，发布版本 1.1.2
* `16/08/20` 完善 目录、FileUtils 单元测试，发布版本 1.1.1
* `16/08/19` 完善 FileUtils 及单元测试，及其他小修小补（在此感谢 vpop 的三次 Pr）
* `16/08/18` 完善 FileUtils 及单元测试，完善 ImageUtils
* `16/08/17` 完善 FileUtils
* `16/08/16` 新增 StringUtils 及单元测试，完善正则工具类，版本更新 1.1.0
* `16/08/15` 新增 3DES 和 AES 加密及单元检测，加密解密工具类基本完善，目录更新
* `16/08/14` 新增 DES 加密及单元检测
* `16/08/13` 新增 MD2，SHA224，SHA256，SHA384，SHA512 加密及单元测试，正折腾 DES 加密
* `16/08/12` 新增 Base64 和 Html 编码解码及他们的单元测试，新增 TimeUtils 单元测试，更新 md
* `16/08/11` 新增 SDCardUtils, UnitUtils，单元测试慢慢完善中
* `16/08/09` 修复 目录排版，新增 Download, Proguard 和 License
* `16/08/08` 新增 Shell 工具类，已传 jcenter 遇到好多坑，javaDoc 惹的祸，注释一定要规范
* `16/08/07` 新增 6.0 获取 Mac 地址方法，新增对 HTML 转义，新增编码解码工具类，新增 SP 工具类
* `16/08/06` 完善 名包名，新增加密相关的单元测试，MD5 加密新增文件加密重载
* `16/08/05` 新增 MD5 盐加密，完善 NetworkUtils，新增判断状态栏是否存在（在此感谢 tiandawu）
* `16/08/04` 新增 时间工具类（在此感谢 yi520000 给的补充），手机正则分简单和精确（在此感谢 MIkeeJY），新增判断是否锁屏，注释分段落，目录按首字母排序
* `16/08/03` 修复 onCreate 中获取 view 尺寸的 bug, MD5 和 SHA 的 Bug 修复完成（在此感谢 ssyijiu）
* `16/08/02` 修复 wifi 设置界面 bug，注释排版还在修改，获取 mac 地址增加判空，新增 QQ群：74721490，欢迎加入，新增隐藏状态栏，注释更加全面，工具类已封装，写的时候真的是一个一个测试过去的，宝宝心里苦
* `16/08/01` 新增 获取 SD 卡路径，手机和设备进行分类，代码 bug 修改部分，小修排版，正在封装类，新增目录中显示方法名，新增获取当前 App 版本 Code
* `16/07/31` 新增 点击屏幕空白区域隐藏软键盘，未能成功增加本页目录跳转功能（不支持）
