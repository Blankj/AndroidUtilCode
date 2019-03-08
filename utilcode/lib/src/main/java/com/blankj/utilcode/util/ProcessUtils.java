package com.blankj.utilcode.util;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.Manifest.permission.KILL_BACKGROUND_PROCESSES;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/18
 *     desc  : utils about process
 * </pre>
 */
public final class ProcessUtils {

    private ProcessUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Return the foreground process name.
     * <p>Target APIs greater than 21 must hold
     * {@code <uses-permission android:name="android.permission.PACKAGE_USAGE_STATS" />}</p>
     *
     * @return the foreground process name
     */
    public static String getForegroundProcessName() {
        ActivityManager am =
                (ActivityManager) Utils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        //noinspection ConstantConditions
        List<ActivityManager.RunningAppProcessInfo> pInfo = am.getRunningAppProcesses();
        if (pInfo != null && pInfo.size() > 0) {
            for (ActivityManager.RunningAppProcessInfo aInfo : pInfo) {
                if (aInfo.importance
                        == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return aInfo.processName;
                }
            }
        }
        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.LOLLIPOP) {
            PackageManager pm = Utils.getApp().getPackageManager();
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            List<ResolveInfo> list =
                    pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            Log.i("ProcessUtils", list.toString());
            if (list.size() <= 0) {
                Log.i("ProcessUtils",
                        "getForegroundProcessName: noun of access to usage information.");
                return "";
            }
            try {// Access to usage information.
                ApplicationInfo info =
                        pm.getApplicationInfo(Utils.getApp().getPackageName(), 0);
                AppOpsManager aom =
                        (AppOpsManager) Utils.getApp().getSystemService(Context.APP_OPS_SERVICE);
                //noinspection ConstantConditions
                if (aom.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                        info.uid,
                        info.packageName) != AppOpsManager.MODE_ALLOWED) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Utils.getApp().startActivity(intent);
                }
                if (aom.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                        info.uid,
                        info.packageName) != AppOpsManager.MODE_ALLOWED) {
                    Log.i("ProcessUtils",
                            "getForegroundProcessName: refuse to device usage stats.");
                    return "";
                }
                UsageStatsManager usageStatsManager = (UsageStatsManager) Utils.getApp()
                        .getSystemService(Context.USAGE_STATS_SERVICE);
                List<UsageStats> usageStatsList = null;
                if (usageStatsManager != null) {
                    long endTime = System.currentTimeMillis();
                    long beginTime = endTime - 86400000 * 7;
                    usageStatsList = usageStatsManager
                            .queryUsageStats(UsageStatsManager.INTERVAL_BEST,
                                    beginTime, endTime);
                }
                if (usageStatsList == null || usageStatsList.isEmpty()) return "";
                UsageStats recentStats = null;
                for (UsageStats usageStats : usageStatsList) {
                    if (recentStats == null
                            || usageStats.getLastTimeUsed() > recentStats.getLastTimeUsed()) {
                        recentStats = usageStats;
                    }
                }
                return recentStats == null ? null : recentStats.getPackageName();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * Return all background processes.
     * <p>Must hold {@code <uses-permission android:name="android.permission.KILL_BACKGROUND_PROCESSES" />}</p>
     *
     * @return all background processes
     */
    @RequiresPermission(KILL_BACKGROUND_PROCESSES)
    public static Set<String> getAllBackgroundProcesses() {
        ActivityManager am =
                (ActivityManager) Utils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        //noinspection ConstantConditions
        List<ActivityManager.RunningAppProcessInfo> info = am.getRunningAppProcesses();
        Set<String> set = new HashSet<>();
        if (info != null) {
            for (ActivityManager.RunningAppProcessInfo aInfo : info) {
                Collections.addAll(set, aInfo.pkgList);
            }
        }
        return set;
    }

    /**
     * Kill all background processes.
     * <p>Must hold {@code <uses-permission android:name="android.permission.KILL_BACKGROUND_PROCESSES" />}</p>
     *
     * @return background processes were killed
     */
    @RequiresPermission(KILL_BACKGROUND_PROCESSES)
    public static Set<String> killAllBackgroundProcesses() {
        ActivityManager am =
                (ActivityManager) Utils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        //noinspection ConstantConditions
        List<ActivityManager.RunningAppProcessInfo> info = am.getRunningAppProcesses();
        Set<String> set = new HashSet<>();
        for (ActivityManager.RunningAppProcessInfo aInfo : info) {
            for (String pkg : aInfo.pkgList) {
                am.killBackgroundProcesses(pkg);
                set.add(pkg);
            }
        }
        info = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo aInfo : info) {
            for (String pkg : aInfo.pkgList) {
                set.remove(pkg);
            }
        }
        return set;
    }

    /**
     * Kill background processes.
     * <p>Must hold {@code <uses-permission android:name="android.permission.KILL_BACKGROUND_PROCESSES" />}</p>
     *
     * @param packageName The name of the package.
     * @return {@code true}: success<br>{@code false}: fail
     */
    @RequiresPermission(KILL_BACKGROUND_PROCESSES)
    public static boolean killBackgroundProcesses(@NonNull final String packageName) {
        ActivityManager am =
                (ActivityManager) Utils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        //noinspection ConstantConditions
        List<ActivityManager.RunningAppProcessInfo> info = am.getRunningAppProcesses();
        if (info == null || info.size() == 0) return true;
        for (ActivityManager.RunningAppProcessInfo aInfo : info) {
            if (Arrays.asList(aInfo.pkgList).contains(packageName)) {
                am.killBackgroundProcesses(packageName);
            }
        }
        info = am.getRunningAppProcesses();
        if (info == null || info.size() == 0) return true;
        for (ActivityManager.RunningAppProcessInfo aInfo : info) {
            if (Arrays.asList(aInfo.pkgList).contains(packageName)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return whether app running in the main process.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isMainProcess() {
        return Utils.getApp().getPackageName().equals(getCurrentProcessName());
    }

    /**
     * Return the name of current process.
     * <p>It's faster than ActivityManager.</p>
     *
     * @return the name of current process
     */
    public static String getCurrentProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    public static String getCurrentProcessName() {
//        ActivityManager am = (ActivityManager) Utils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
//        if (am == null) return "";
//        List<ActivityManager.RunningAppProcessInfo> info = am.getRunningAppProcesses();
//        if (info == null || info.size() == 0) return "";
//        int pid = Process.myPid();
//        for (ActivityManager.RunningAppProcessInfo aInfo : info) {
//            if (aInfo.pid == pid) {
//                if (aInfo.processName != null) {
//                    return aInfo.processName;
//                }
//            }
//        }
//        return "";
//    }
}
