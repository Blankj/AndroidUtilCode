package com.blankj.utilcode.utils;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *     author: onlylemi
 *     blog  : https://github.com/onlylemi
 *     time  : 2016/10/14
 *     desc  : 前台进程信息获取相关
 * </pre>
 */
public class ProcessUtils {

    private static UsageStats recentStats;
    private static String result;

    private ProcessUtils() {
    }

    /**
     * 判断应用是否处于前台
     *
     * @param context
     * @return
     */
    public static boolean isAppForeground(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfos = manager.getRunningAppProcesses();
        if (runningAppProcessInfos == null || runningAppProcessInfos.size() == 0) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo ra : runningAppProcessInfos) {
            if (ra.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                    && Arrays.asList(ra.pkgList).contains(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断某个应用是否处于前台（系统应用调用）
     * <p>API < 21，需要添加 {@code <uses-permission android:name="android.permission.GET_TASKS"/>} 权限</p>
     * <p>API >= 22，需要添加　{@code <uses-permission android:name="android.permission.PACKAGE_USAGE_STATS"/>} 权限</p>
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppForeground(Context context, String packageName) {
        return TextUtils.equals(packageName, getForegroundPackage(context));
    }

    /**
     * 获取前台应用包名（系统应用调用）
     * <p>API < 21，需要添加 {@code <uses-permission android:name="android.permission.GET_TASKS"/>} 权限</p>
     * <p>API >= 22，需要添加　{@code <uses-permission android:name="android.permission.PACKAGE_USAGE_STATS"/>} 权限</p>
     *
     * @param context
     * @return
     */
    public static String getForegroundPackage(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return getForegroundPackage1(context);
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
            return getForegroundPackage2(context);
        } else {
            return getForegroundPackage3(context);
        }
    }


    /**
     * 获取前台应用包名（API < 21，已被遗弃，不能使用）
     * <p>需要添加　{@code <uses-permission android:name="android.permission.GET_TASKS"/>} 权限</p>
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static String getForegroundPackage1(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);
        if (runningTaskInfos == null || runningTaskInfos.size() == 0
                || runningTaskInfos.get(0) == null) {
            return null;
        }

        return runningTaskInfos.get(0).topActivity.getPackageName();
    }

    /**
     * 获取前台应用包名（在 API 22 开始仅可以获取自己的应用，其他应用位于前台时获取到为 null，但可以通过此方式判断自己的应用是否处于前台）
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    private static String getForegroundPackage2(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfos = manager.getRunningAppProcesses();
        if (runningAppProcessInfos == null || runningAppProcessInfos.size() == 0) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo ra : runningAppProcessInfos) {
            if (ra.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return ra.processName;
            }
        }
        return null;
    }

    /**
     * 获取前台应用包名（API >= 22）
     * <p>需要添加　{@code <uses-permission android:name="android.permission.PACKAGE_USAGE_STATS"/>} 权限</p>
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    public static String getForegroundPackage3(Context context) {
        UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        long endTime = calendar.getTimeInMillis();// 结束时间
        calendar.add(Calendar.DAY_OF_MONTH, -1);// 时间间隔为一个月
        long beginTime = calendar.getTimeInMillis();// 开始时间
        // 获取一个月内的信息
        List<UsageStats> usageStatses = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_MONTHLY, beginTime, endTime);
        if (usageStatses == null || usageStatses.size() == 0) {
            return null;
        }
        for (UsageStats usageStats : usageStatses) {
            if (recentStats == null || usageStats.getLastTimeUsed() > recentStats.getLastTimeUsed()) {
                recentStats = usageStats;
            }
        }

        return recentStats.getPackageName();
    }

    /**
     * 获取前台应用包名（API >= 22）
     * <p>需要添加　{@code <uses-permission android:name="android.permission.PACKAGE_USAGE_STATS"/>} 权限</p>
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    private static String getForegroundPackage4(Context context) {
        UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);

        long endTime = System.currentTimeMillis();
        long beginTime = endTime - 10 * 1000;
        UsageEvents.Event event = new UsageEvents.Event();
        UsageEvents usageEvents = usageStatsManager.queryEvents(beginTime, endTime);
        while (usageEvents.hasNextEvent()) {
            usageEvents.getNextEvent(event);
            if (event.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                result = event.getPackageName();
            }
        }

        return result;
    }
}