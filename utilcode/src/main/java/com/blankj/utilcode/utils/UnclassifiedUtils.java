package com.blankj.utilcode.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;

import java.util.List;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/2
 *     desc  : 未归类工具类
 * </pre>
 */
public class UnclassifiedUtils {

    private UnclassifiedUtils() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    /**
     * 获取服务是否开启
     *
     * @param context   上下文
     * @param className 完整包名的服务类名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isRunningService(Context context, String className) {
        // 进程的管理者,活动的管理者
        ActivityManager activityManager = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);
        // 获取正在运行的服务，最多获取1000个
        List<RunningServiceInfo> runningServices = activityManager.getRunningServices(1000);
        // 遍历集合
        for (RunningServiceInfo runningServiceInfo : runningServices) {
            ComponentName service = runningServiceInfo.service;
            if (className.equals(service.getClassName())) {
                return true;
            }
        }
        return false;
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
}