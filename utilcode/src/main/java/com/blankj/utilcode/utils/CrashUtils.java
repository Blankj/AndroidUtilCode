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

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/9/27
 *     desc  : 崩溃相关工具类
 * </pre>
 */
public class CrashUtils implements Thread.UncaughtExceptionHandler {

    private volatile static CrashUtils mInstance;
    private Context mContext;
    private UncaughtExceptionHandler mHandler;
    private boolean mInitialized;

    private CrashUtils() {
    }

    /**
     * 获取单例
     * <p>在Application中初始化{@code CrashUtils.getInstance().getBuilder(this);}</p>
     *
     * @return 单例
     */
    public static CrashUtils getInstance() {
        synchronized (CrashUtils.class) {
            if (null == mInstance) {
                mInstance = new CrashUtils();
            }
        }
        return mInstance;
    }

    /**
     * 初始化
     *
     * @param context 上下文
     */
    public void init(Context context) {
        if (mInitialized) return;
        mInitialized = true;
        mContext = context;
        mHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        String dir;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            dir = mContext.getExternalCacheDir().getPath();
        } else {
            dir = mContext.getCacheDir().getPath();
        }
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
        try {
            PackageInfo pi = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            sb.append("\n************* Crash Log Head ****************");
            sb.append("\nDevice Manufacturer: ").append(Build.MANUFACTURER);// 设备厂商
            sb.append("\nDevice Model       : ").append(Build.MODEL);// 设备型号
            sb.append("\nAndroid Version    : ").append(Build.VERSION.RELEASE);// 系统版本
            sb.append("\nAndroid SDK        : ").append(Build.VERSION.SDK_INT);// SDK版本
            sb.append("\nApp VersionName    : ").append(pi.versionName);
            sb.append("\nApp VersionCode    : ").append(pi.versionCode);
            sb.append("\n************* Crash Log Head ****************\n\n");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return sb;
    }
}
