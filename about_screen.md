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

### 获取状态栏高度
```
/**
* 获取状态栏高度
*/
public int getStatusBarHeight() {
    int result = 0;
    int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
    if (resourceId > 0) {
        result = getResources().getDimensionPixelSize(resourceId);
    }
    return result;
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

### 获取屏幕截图
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
 * 需要用到上面获取状态栏高度的方法
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

### 设置透明状态栏，需在setContentView之前调用
``` java
if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
    //透明状态栏
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    //透明导航栏
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
}

// 需在顶部控件布局中加入以下属性让内容出现在状态栏之下
android:clipToPadding="true" 
android:fitsSystemWindows="true"
```
