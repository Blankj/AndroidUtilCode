package com.blankj.utilcode.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.ref.WeakReference;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/9/27
 *     desc  : 崩溃相关工具类
 * </pre>
 */
public class CrashUtils implements Thread.UncaughtExceptionHandler {

    private static CrashUtils mInstance = new CrashUtils();
    private UncaughtExceptionHandler mHandler;
    private boolean mInitialized;
    private static String dir;
    private String versionName;
    private int versionCode;

    private CrashUtils() {
    }

    /**
     * 获取单例
     * <p>在Application中初始化{@code CrashUtils.getInstance().init(this);}</p>
     *
     * @return 单例
     */
    public static CrashUtils getInstance() {
        return mInstance;
    }

    /**
     * 初始化
     *
     * @param context 上下文
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public boolean init(Context context) {
        if (mInitialized) return true;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            dir = context.getExternalCacheDir().getPath();
        } else {
            dir = context.getCacheDir().getPath();
        }
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            versionCode = pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        mHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        return mInitialized = true;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        String fullPath = dir + File.separator + "crash_" + TimeUtils.getCurTimeString() + ".txt";
        if (!FileUtils.createOrExistsFile(fullPath)) return;
        StringBuilder sb = new StringBuilder();
        sb.append(getCrashHead());
        Writer writer = new StringWriter();
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(writer);
            throwable.printStackTrace(pw);
            Throwable cause = throwable.getCause();
            while (cause != null) {
                cause.printStackTrace(pw);
                cause = cause.getCause();
            }
        } finally {
            CloseUtils.closeIO(pw);
        }
        sb.append(writer.toString());
        FileUtils.writeFileFromString(fullPath, sb.toString(), false);
        if (mHandler != null) {
            mHandler.uncaughtException(thread, throwable);
        }
    }

    /**
     * 获取崩溃头
     *
     * @return 崩溃头
     */
    private StringBuilder getCrashHead() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n************* Crash Log Head ****************");
        sb.append("\nDevice Manufacturer: ").append(Build.MANUFACTURER);// 设备厂商
        sb.append("\nDevice Model       : ").append(Build.MODEL);// 设备型号
        sb.append("\nAndroid Version    : ").append(Build.VERSION.RELEASE);// 系统版本
        sb.append("\nAndroid SDK        : ").append(Build.VERSION.SDK_INT);// SDK版本
        sb.append("\nApp VersionName    : ").append(versionName);
        sb.append("\nApp VersionCode    : ").append(versionCode);
        sb.append("\n************* Crash Log Head ****************\n\n");
        return sb;
    }
}
