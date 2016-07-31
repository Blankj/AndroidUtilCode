# App相关
### 安装指定路径下的Apk
``` java
/**
* 安装指定路径下的Apk
*/
public void installApk(String filePath) {
    Intent intent = new Intent();
    intent.setAction("android.intent.action.VIEW");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.setDataAndType(Uri.fromFile(new File(filePath)), "application/vnd.android.package-archive");
    startActivityForResult(intent, 0);
}
```

### 卸载指定包名的App
``` java
/**
* 卸载指定包名的App
*/
public void uninstallApp(String packageName) {
    Intent intent = new Intent();
    intent.setAction("android.intent.action.DELETE");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.setData(Uri.parse("package:" + packageName));
    startActivityForResult(intent, 0);
}
```

### 获取App名称
```
/**
* 获取App名称
*/
public static String getAppName(Context context) {
    try {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageInfo(
                context.getPackageName(), 0);
        int labelRes = packageInfo.applicationInfo.labelRes;
        return context.getResources().getString(labelRes);
    } catch (NameNotFoundException e) {
        e.printStackTrace();
    }
    return null;
}
```


### 获取当前App版本号
``` java
/**
* 获取当前App版本号
*/
public static String getVersonName(Context context) {
    String versionName = null;
    PackageManager pm = context.getPackageManager();
    PackageInfo info = null;
    try {
        info = pm.getPackageInfo(context.getApplicationContext().getPackageName(), 0);
    } catch (NameNotFoundException e) {
        e.printStackTrace();
    }
    if (info != null) {
        versionName = info.versionName;
    }
    return versionName;
}
```

### 打开指定包名的App
```
/**
* 打开指定包名的App
*/
public void openOtherApp(String packageName){
    PackageManager manager = getPackageManager();
    Intent launchIntentForPackage = manager.getLaunchIntentForPackage(packageName);
    if (launchIntentForPackage != null) {
        startActivity(launchIntentForPackage);
    }
}
```

### 打开指定包名的App应用信息界面
``` java
/**
* 打开指定包名的App应用信息界面
*/
public void showAppInfo(String packageName) {
    Intent intent = new Intent();
    intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
    intent.setData(Uri.parse("package:" + packageName));
    startActivity(intent);
}
```

### 分享Apk信息
``` java
/**
* 分享Apk信息
*/
public void shareApkInfo(String info) {
    Intent intent = new Intent();
    intent.setAction("android.intent.action.SEND");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.setType("text/plain");
    intent.putExtra(Intent.EXTRA_TEXT, info);
    startActivity(intent);
}
```

### 获取App信息的一个封装类(包名、版本号、应用信息、图标、名称等)
``` java
/**
* 获取App信息的一个封装类(包名、版本号、应用信息、图标、名称等)
*/
public class AppEnging {
    public static List<AppInfo> getAppInfos(Context context) {
        List<AppInfo> list = new ArrayList<AppInfo>();
        //获取应用程序信息
        //包的管理者
        PackageManager pm = context.getPackageManager();
        //获取系统中安装到所有软件信息
        List<PackageInfo> installedPackages = pm.getInstalledPackages(0);
        for (PackageInfo packageInfo : installedPackages) {
            //获取包名
            String packageName = packageInfo.packageName;
            //获取版本号
            String versionName = packageInfo.versionName;
            //获取application
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            int uid = applicationInfo.uid;
            //获取应用程序的图标
            Drawable icon = applicationInfo.loadIcon(pm);
            //获取应用程序的名称
            String name = applicationInfo.loadLabel(pm).toString();
            //是否是用户程序
            //获取应用程序中相关信息,是否是系统程序和是否安装到SD卡
            boolean isUser;
            int flags = applicationInfo.flags;
            if ((applicationInfo.FLAG_SYSTEM & flags) == applicationInfo.FLAG_SYSTEM) {
                //系统程序
                isUser = false;
            } else {
                //用户程序
                isUser = true;
            }
            //是否安装到SD卡
            boolean isSD;
            if ((applicationInfo.FLAG_EXTERNAL_STORAGE & flags) == applicationInfo.FLAG_EXTERNAL_STORAGE) {
                //安装到了SD卡
                isSD = true;
            } else {
                //安装到手机中
                isSD = false;
            }
            //添加到bean中
            AppInfo appInfo = new AppInfo(name, icon, packageName, versionName, isSD, isUser);
            //将bean存放到list集合
            list.add(appInfo);
        }
        return list;
    }
}
 
// 封装软件信息的bean类
class AppInfo {
    //名称
    private String name;
    //图标
    private Drawable icon;
    //包名
    private String packagName;
    //版本号
    private String versionName;
    //是否安装到SD卡
    private boolean isSD;
    //是否是用户程序
    private boolean isUser;
 
    public AppInfo() {
        super();
    }
 
    public AppInfo(String name, Drawable icon, String packagName,
                   String versionName, boolean isSD, boolean isUser) {
        super();
        this.name = name;
        this.icon = icon;
        this.packagName = packagName;
        this.versionName = versionName;
        this.isSD = isSD;
        this.isUser = isUser;
    }
}
```

### 判断当前App处于前台还是后台
``` java
// 需添加<uses-permission android:name="android.permission.GET_TASKS"/>
// 并且必须是系统应用该方法才有效
/**
* 判断当前App处于前台还是后台
*/
public static boolean isApplicationBackground(final Context context) {
    ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    @SuppressWarnings("deprecation")
    List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
    if (!tasks.isEmpty()) {
        ComponentName topActivity = tasks.get(0).topActivity;
        if (!topActivity.getPackageName().equals(context.getPackageName())) {
            return true;
        }
    }
    return false;
}
```
