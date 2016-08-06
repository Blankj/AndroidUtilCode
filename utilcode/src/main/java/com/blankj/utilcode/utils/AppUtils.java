package com.blankj.utilcode.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/*********************************************
 * author: Blankj on 2016/8/2 1:53
 * blog:   http://blankj.com
 * e-mail: blankj@qq.com
 *********************************************/
public class AppUtils {

    private AppUtils() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    /**
     * 安装指定路径下的Apk
     * <p>根据路径名是否符合和文件是否存在判断是否安装成功
     * <p>更好的做法应该是startActivityForResult回调判断是否安装成功比较妥当
     * <p>这里做不了回调，后续自己做处理
     */
    public static boolean installApp(Context context, String filePath) {
        if (filePath != null && filePath.length() > 4
                && filePath.toLowerCase().substring(filePath.length() - 4).equals(".apk")) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            File file = new File(filePath);
            if (file.exists() && file.isFile() && file.length() > 0) {
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                return true;
            }
        }
        return false;
    }

    /**
     * 卸载指定包名的App
     * <p>这里卸载成不成功只判断了packageName是否为空
     * <p>如果要根据是否卸载成功应该用startActivityForResult回调判断是否还存在比较妥当
     * <p>这里做不了回调，后续自己做处理
     */
    public boolean uninstallApp(Context context, String packageName) {
        if (!TextUtils.isEmpty(packageName)) {
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(Uri.parse("package:" + packageName));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        }
        return false;
    }

    /**
     * 封装App信息的Bean类
     */
    public static class AppInfo {

        private String name;
        private Drawable icon;
        private String packagName;
        private String versionName;
        private int versionCode;
        private boolean isSD;
        private boolean isUser;

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(Drawable icon) {
            this.icon = icon;
        }

        public boolean isSD() {
            return isSD;
        }

        public void setSD(boolean SD) {
            isSD = SD;
        }

        public boolean isUser() {
            return isUser;
        }

        public void setUser(boolean user) {
            isUser = user;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPackagName() {
            return packagName;
        }

        public void setPackagName(String packagName) {
            this.packagName = packagName;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        /**
         * @param name        名称
         * @param icon        图标
         * @param packagName  包名
         * @param versionName 版本号
         * @param versionCode 版本Code
         * @param isSD        是否安装在SD卡
         * @param isUser      是否是用户程序
         */
        public AppInfo(String name, Drawable icon, String packagName,
                       String versionName, int versionCode, boolean isSD, boolean isUser) {
            this.setName(name);
            this.setIcon(icon);
            this.setPackagName(packagName);
            this.setVersionName(versionName);
            this.setVersionCode(versionCode);
            this.setSD(isSD);
            this.setUser(isUser);
        }

    /*@Override
    public String toString() {
        return getName() + "\n"
                + getIcon() + "\n"
                + getPackagName() + "\n"
                + getVersionName() + "\n"
                + getVersionCode() + "\n"
                + isSD() + "\n"
                + isUser() + "\n";
    }*/
    }

    /**
     * 获取当前App信息
     * <p>AppInfo（名称，图标，包名，版本号，版本Code，是否安装在SD卡，是否是用户程序）
     */
    public static AppInfo getAppInfo(Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(context.getApplicationContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pi != null ? getBean(pm, pi) : null;
    }

    /**
     * 得到AppInfo的Bean
     */
    private static AppInfo getBean(PackageManager pm, PackageInfo pi) {
        ApplicationInfo ai = pi.applicationInfo;
        String name = ai.loadLabel(pm).toString();
        Drawable icon = ai.loadIcon(pm);
        String packageName = pi.packageName;
        String versionName = pi.versionName;
        int versionCode = pi.versionCode;
        boolean isSD = (ApplicationInfo.FLAG_SYSTEM & ai.flags) != ApplicationInfo.FLAG_SYSTEM;
        boolean isUser = (ApplicationInfo.FLAG_SYSTEM & ai.flags) != ApplicationInfo.FLAG_SYSTEM;
        return new AppInfo(name, icon, packageName, versionName, versionCode, isSD, isUser);
    }

    /**
     * 获取所有已安装App信息
     * <p>AppInfo（名称，图标，包名，版本号，版本Code，是否安装在SD卡，是否是用户程序）
     * <p>依赖上面的getBean方法
     */
    public static List<AppInfo> getAllAppsInfo(Context context) {
        List<AppInfo> list = new ArrayList<>();
        PackageManager pm = context.getPackageManager();
        // 获取系统中安装的所有软件信息
        List<PackageInfo> installedPackages = pm.getInstalledPackages(0);
        for (PackageInfo pi : installedPackages) {
            if (pi != null) {
                list.add(getBean(pm, pi));
            }
        }
        return list;
    }

    /**
     * 打开指定包名的App
     */
    public static boolean openAppByPackageName(Context context, String packageName) {
        if (!TextUtils.isEmpty(packageName)) {
            PackageManager pm = context.getPackageManager();
            Intent launchIntentForPackage = pm.getLaunchIntentForPackage(packageName);
            if (launchIntentForPackage != null) {
                context.startActivity(launchIntentForPackage);
                return true;
            }
        }
        return false;
    }

    /**
     * 打开指定包名的App应用信息界面
     */
    public static boolean openAppInfo(Context context, String packageName) {
        if (!TextUtils.isEmpty(packageName)) {
            Intent intent = new Intent();
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.parse("package:" + packageName));
            context.startActivity(intent);
            return true;
        }
        return false;
    }

    /**
     * 可用来做App信息分享
     */
    public static void shareAppInfo(Context context, String info) {
        if (!TextUtils.isEmpty(info)) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, info);
            context.startActivity(intent);
        }
    }

    /**
     * 判断当前App处于前台还是后台
     * <p>需添加<uses-permission android:name="android.permission.GET_TASKS"/>
     * <p>并且必须是系统应用该方法才有效
     */
    public static boolean isAppBackground(Context context) {
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
}

