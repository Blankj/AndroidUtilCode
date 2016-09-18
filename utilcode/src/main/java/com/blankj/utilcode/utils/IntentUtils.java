package com.blankj.utilcode.utils;

public class IntentUtils {

    /**
     * 跳转至系统设置界面
     *
     * @param mContext 上下文
     */
    public static void toSettingActivity(Context mContext) {
        Intent settingsIntent = new Intent(Settings.ACTION_SETTINGS);
        mContext.startActivity(settingsIntent);
    }

    /**
     * 跳转至WIFI设置界面
     *
     * @param mContext 上下文
     */
    public static void toWIFISettingActivity(Context mContext) {
        Intent wifiSettingsIntent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        mContext.startActivity(wifiSettingsIntent);
    }


    /**
     * 判断是否安装指定包名的APP
     *
     * @param mContext    上下文
     * @param packageName 包路径
     * @return
     */
    public static boolean isInstalledApp(Context mContext, String packageName) {
        if (packageName == null || "".equals(packageName)) {
            return false;
        }

        try {
            ApplicationInfo info =
                    mContext.getPackageManager().getApplicationInfo(packageName,
                            PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    /**
     * 判断是否存在指定的Activity
     *
     * @param mContext    上下文
     * @param packageName 包名
     * @param className   activity全路径类名
     * @return
     */
    public static boolean isExistActivity(Context mContext, String packageName, String className) {

        Boolean result = true;
        Intent intent = new Intent();
        intent.setClassName(packageName, className);

        if (mContext.getPackageManager().resolveActivity(intent, 0) == null) {
            result = false;
        } else if (intent.resolveActivity(mContext.getPackageManager()) == null) {
            result = false;
        } else {
            List<ResolveInfo> list = mContext.getPackageManager().queryIntentActivities(intent, 0);
            if (list.size() == 0) {
                result = false;
            }
        }

        return result;
    }

    /**
     * 打开某App的指定Activity
     *
     * @param context      上下文
     * @param packageName  包名
     * @param activityName 全类名
     */
    public static void launchAppAct(Context context, String packageName, String activityName) {
        launchAppAct(context, packageName, activityName, null);
    }

    public static void launchAppAct(Context context, String packageName, String activityName, Bundle b) {
        Intent intent = new Intent();
        if (b != null)
            intent.putExtras(b);

        ComponentName comp = new ComponentName(packageName, activityName);
        intent.setComponent(comp);
        intent.setAction("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }



    /**
     * 通过包名打开某个App
     *
     * @param context     上下文
     * @param packageName 包名
     */
    public static void launchApp(Context context, String packageName) {
        try {
            PackageManager packageManager = context.getPackageManager();
            Intent intent = packageManager.getLaunchIntentForPackage(packageName);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 创建可远程启动的Intent
     * @param packageName 包名
     * @param activityName 全类名
     * @return Intent
     */
    public static Intent createComIntent(String packageName, String activityName){
        ComponentName comp = new ComponentName(packageName, activityName);
        Intent intent = new Intent();
        intent.setComponent(comp);
        intent.setAction("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }
}