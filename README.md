# Android开发人员不得不收集的代码(不断更新)  
为方便查找，已进行大致归类，其目录如下所示：  
> - [尺寸相关](https://github.com/Blankj/AndroidUtilCode/blob/master/about_size.md)
>    - dp与px转换 *dp2px*、*px2dp*
>    - sp与px转换 *sp2px*、*px2sp*
>    - 各种单位转换 *applyDimension*
>    - 在onCreate()即可获取View的宽高 *getViewMeasure*
>    - ListView中提前测量View尺寸 *measureView*
> - [手机相关](https://github.com/Blankj/AndroidUtilCode/blob/master/about_phone.md)
>    - 判断设备是否是手机 *isPhone*
>    - 获取当前设备的IMIE，需与上面的isPhone一起使用 *getDeviceIMEI*
>    - 获取手机状态信息 *getPhoneStatus*
>    - 是否有SD卡 *haveSDCard*
>    - 获取MAC地址 *getMacAddress*
>    - 获取手机厂商，如Xiaomi *getManufacturer*
>    - 获取手机型号，如MI2SC *getModel*
>    - 拨打电话 *callDial*
>    - 发送短信 *sendSms*
>    - 获取手机联系人 *getAllContactInfo*
>    - 直接打开手机联系人界面，并获取联系人号码
>    - 获取手机短信并保存到xml中 *getAllSMS*
> - [网络相关](https://github.com/Blankj/AndroidUtilCode/blob/master/about_net.md)
>    - 打开网络设置界面 *openSetting*
>    - 判断是否网络连接 *isOnline*
>    - 判断wifi是否连接状态 *isWifi*
>    - 获取移动网络运营商名称，如中国联通、中国移动、中国电信 *getNetworkOperatorName*
>    - 返回移动终端类型 *getPhoneType*
>    - 判断手机连接的网络类型(2G,3G,4G) *getNetWorkClass*
>    - 判断当前手机的网络类型(WIFI还是2,3,4G) *getNetWorkStatus*
> - [App相关](https://github.com/Blankj/AndroidUtilCode/blob/master/about_app.md)
>    - 安装指定路径下的Apk *installApk*
>    - 卸载指定包名的App *uninstallApp*
>    - 获取App名称 *getAppName*
>    - 获取当前App版本号 *getVersonName*
>    - 获取当前App版本Code *getVersionCode*
>    - 打开指定包名的App *openOtherApp*
>    - 打开指定包名的App应用信息界面 *showAppInfo*
>    - 分享Apk信息 *shareApkInfo*
>    - 获取App信息的一个封装类(包名、版本号、应用信息、图标、名称等) *getAppInfos*
>    - 判断当前App处于前台还是后台 *isApplicationBackground*
> - [屏幕相关](https://github.com/Blankj/AndroidUtilCode/blob/master/about_screen.md)
>    - 获取手机分辨率 *getDeviceWidth*、*getDeviceHeight*
>    - 获取状态栏高度 *getStatusBarHeight*
>    - 获取状态栏高度＋标题栏(ActionBar)高度 *getTopBarHeight*
>    - 获取屏幕截图 *snapShotWithStatusBar*、*snapShotWithoutStatusBar*
>    - 设置透明状态栏，需在setContentView之前调用
> - [键盘相关](https://github.com/Blankj/AndroidUtilCode/blob/master/about_keyboard.md)
>    - 避免输入法面板遮挡
>    - 动态隐藏软键盘 *hideSoftInput*
>    - 点击屏幕空白区域隐藏软键盘
>    - 动态显示软键盘 *showSoftInput*
>    - 切换键盘显示与否状态 *toggleSoftInput*
> - [正则相关](https://github.com/Blankj/AndroidUtilCode/blob/master/about_regular.md)
>    - 正则工具类
> - [加解密相关](https://github.com/Blankj/AndroidUtilCode/blob/master/about_encrypt.md)
>    - MD5加密 *encryptMD5*
>    - SHA加密 *encryptSHA*
> - [未归类](https://github.com/Blankj/AndroidUtilCode/blob/master/unclassified.md)
>    - 获取服务是否开启 *isRunningService*
> - [更新Log](https://github.com/Blankj/AndroidUtilCode/blob/master/log.md)

  
**做这份整理只是想把它作为Android的一本小字典，当遇到一些琐碎问题时，不用再面向百度或者谷歌查询API的使用，费时费力，这里有的话，大家尽管撸走。希望它能逐日壮大起来，期待你的Star和完善，用途的话大家想把它们整理成工具类或者什么的话都可以，之后我也会封装工具类并分享之，但本篇只是提供查阅，毕竟看md比看类文件要爽多了，其中好多代码我也是各种搜刮来的，也要谢谢各位的总结，大部分代码已验证过可行，如有错误，请及时告之。**   

  
##[关于Blankj](http://blankj.com/about)