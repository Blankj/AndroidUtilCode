# 屏幕相关
### 获取手机分辨率
``` java
/**
* 获取屏幕的宽度px
*/
public static int getDeviceWidth(Context context) {
    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    DisplayMetrics outMetrics = new DisplayMetrics();// 创建了一张白纸
    windowManager.getDefaultDisplay().getMetrics(outMetrics);// 给白纸设置宽高
    return outMetrics.widthPixels;
}

/**
* 获取屏幕的高度px
*/
public static int getDeviceHeight(Context context) {
    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    DisplayMetrics outMetrics = new DisplayMetrics();// 创建了一张白纸
    windowManager.getDefaultDisplay().getMetrics(outMetrics);// 给白纸设置宽高
    return outMetrics.heightPixels;
}
```

### 反射打开、关闭通知栏(api >= 8方可使用)
``` java
/**
 * 打开系统消息中心，api16之后状态栏增加设置一栏
 * @param mContext
 * @param isSettingPanel true,打开设置，false 打开通知
 */
public static void openStatusBar(Context mContext, boolean isSettingPanel) {
    // 判断系统版本号
    String methodName = (Build.VERSION.SDK_INT <= 16) ? "expand"
            : (isSettingPanel ? "expandSettingsPanel" : "expandNotificationsPanel");
    doInStatusBar(mContext, methodName);
}

/**
 * 反射打开通知栏
 * @param mContext
 * @param methodName
 */
private static void doInStatusBar(Context mContext, String methodName) {
    try {
        Object service = mContext.getSystemService("statusbar");
        Class<?> statusBarManager = Class.forName("android.app.StatusBarManager");
        Method expand = statusBarManager.getMethod(methodName);
        expand.invoke(service);
    } catch (Exception e) {
        e.printStackTrace();
    }
}

/**
 * 关闭通知栏
 */
public static void closeStatusBar(Context mContext) {
    // 判断系统版本号
    String methodName = (Build.VERSION.SDK_INT <= 16) ? "collapse" : "collapsePanels";
    doInStatusBar(mContext, methodName);
}
```



### 设置透明状态栏(api >= 19方可使用)
``` java
/**
 * 设置透明状态栏(api >= 19方可使用)
 * <p>可在Activity的onCreat()中调用
 * <p>需在顶部控件布局中加入以下属性让内容出现在状态栏之下
 * <p>android:clipToPadding="true"
 * <p>android:fitsSystemWindows="true"
 */
public static void setTransparentStatusBar(Activity activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        //透明状态栏
        activity.getWindow().addFlags(LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        activity.getWindow().addFlags(LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }
}
```

### 隐藏状态栏
``` java
/**
 * 隐藏状态栏
 * <p>也就是设置全屏，一定要在setContentView之前调用，否则报错
 * <p>此方法Activity可以继承AppCompatActivity
 * <p>启动的时候状态栏会显示一下再隐藏，比如QQ的欢迎界面
 * <p>在配置文件中Activity加属性android:theme="@android:style/Theme.NoTitleBar.Fullscreen"
 * <p>如加了以上配置Activity不能继承AppCompatActivity，会报错
 */
public static void hideStatusBar(Activity activity) {
    activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    activity.getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN,
            LayoutParams.FLAG_FULLSCREEN);
}
```

### 获取状态栏高度
``` java
/**
 * 获取状态栏高度
 */
public static int getStatusBarHeight(Context context) {
    int result = 0;
    int resourceId = context.getResources()
            .getIdentifier("status_bar_height", "dimen", "android");
    if (resourceId > 0) {
        result = context.getResources().getDimensionPixelSize(resourceId);
    }
    return result;
}
```

### 判断状态栏是否存在
``` java
/**
* 判断状态栏是否存在
*
* @param activity
* @return <ul>
* <li>true: 存在 </li>
* <li>false: 不存在</li>
* </ul>
*/
public static boolean isStatusBarExists(Activity activity) {
    WindowManager.LayoutParams params = activity.getWindow().getAttributes();
    return (params.flags & LayoutParams.FLAG_FULLSCREEN) != LayoutParams.FLAG_FULLSCREEN;
}
```

### 获取ActionBar高度
``` java
/**
 * 获取ActionBar高度
 */
public static int getActionBarHeight(Activity activity) {
    TypedValue tv = new TypedValue();
    if (activity.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
        return TypedValue.complexToDimensionPixelSize(tv.data, activity.getResources().getDisplayMetrics());
    }
    return 0;
}
```

### 设置屏幕为横屏
```
/**
 * 设置屏幕为横屏
 * <p>还有一种就是在Activity中加属性android:screenOrientation="landscape"
 * <p>不设置Activity的android:configChanges时，切屏会重新调用各个生命周期，切横屏时会执行一次，切竖屏时会执行两次
 * <p>设置Activity的android:configChanges="orientation"时，切屏还是会重新调用各个生命周期，切横、竖屏时只会执行一次
 * <p>设置Activity的android:configChanges="orientation|keyboardHidden|screenSize"（4.0以上必须带最后一个参数）时
 * 切屏不会重新调用各个生命周期，只会执行onConfigurationChanged方法
 */
public static void setLandscape(Activity activity) {
    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
}
```

### 获取当前屏幕截图
``` java
/**
 * 获取当前屏幕截图，包含状态栏
 */
public static Bitmap captureWithStatusBar(Activity activity) {
    View view = activity.getWindow().getDecorView();
    view.setDrawingCacheEnabled(true);
    view.buildDrawingCache();
    Bitmap bmp = view.getDrawingCache();
    int width = getScreenWidth(activity);
    int height = getScreenHeight(activity);
    Bitmap bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
    view.destroyDrawingCache();
    return bp;
}

/**
 * 获取当前屏幕截图，不包含状态栏
 * <p>需要用到上面获取状态栏高度的方法
 */
public static Bitmap captureWithoutStatusBar(Activity activity) {
    View view = activity.getWindow().getDecorView();
    view.setDrawingCacheEnabled(true);
    view.buildDrawingCache();
    Bitmap bmp = view.getDrawingCache();
    int statusBarHeight = getStatusBarHeight(activity);
    int width = getScreenWidth(activity);
    int height = getScreenHeight(activity);
    Bitmap bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
    view.destroyDrawingCache();
    return bp;
}
```

### 判断是否锁屏
``` java
/**
 * 判断是否锁屏
 */
public static boolean isScreenLock(Context context) {
    KeyguardManager km = (KeyguardManager) context
            .getSystemService(Context.KEYGUARD_SERVICE);
    return km.inKeyguardRestrictedInputMode();
}
```

***
# 尺寸相关
### dp与px转换
``` java
/**
* dp转px
*/
public static int dp2px(Context context, float dpValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
}

/**
* px转dp
*/
public static int px2dp(Context context, float pxValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (pxValue / scale + 0.5f);
}
```
 