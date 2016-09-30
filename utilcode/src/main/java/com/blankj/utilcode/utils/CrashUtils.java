package com.blankj.utilcode.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

    private UncaughtExceptionHandler mHandler;
    private String mAndroidVersion;
    private String mModel;
    private String mManufacturer;
    public static String sVERSION = "Unknown";
    private static CrashBuilder sBuilder;
    private boolean isAppend;
    private boolean isSimple;

    private CrashUtils() {
        mHandler = Thread.currentThread().getUncaughtExceptionHandler();
        Thread.currentThread().setUncaughtExceptionHandler(this);
        mAndroidVersion = Build.VERSION.RELEASE;//安卓系统版本
        mModel = Build.MODEL;// 设备型号
        mManufacturer = Build.MANUFACTURER;// 设备厂商
    }

    /**
     * @param isAppend 是否为日志追加模式
     */
    public CrashUtils setAppend(boolean isAppend) {
        this.isAppend = isAppend;
        return this;
    }

    /**
     * @param isSimple 是否为简单的日志记录模式
     */
    public CrashUtils setSimple(boolean isSimple) {
        this.isSimple = isSimple;
        return this;
    }


    public static CrashUtils init(Context context, String dirName) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            sVERSION = info.versionName + info.versionCode;
            sBuilder = CrashBuilder.build(context, dirName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new CrashUtils();
    }

//    public static String formatNumber(int value) {
//        return new DecimalFormat("00").format(value);
//    }
//
//    public static String getCurrentDate() {
//        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
//        return calendar.get(Calendar.YEAR) + "-" + formatNumber((calendar.get(Calendar.MONTH) + 1)) + "-"
//                + formatNumber(calendar.get(Calendar.DAY_OF_MONTH)) + "  "
//                + formatNumber(calendar.get(Calendar.HOUR_OF_DAY)) + ":" + formatNumber(calendar.get(Calendar
// .MINUTE));
//    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        if (!isAppend) {
            FileUtils.createFileByDeleteOldFile(sBuilder.getCarsh_log());
        } else {
            FileUtils.createOrExistsFile(sBuilder.getCarsh_log());
        }
        File file = new File(sBuilder.getCarsh_log());
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(file, true));
            pw.write("\n*************---------Crash  Log  Head ------------****************\n\n");
            pw.write("Happened Time: " + TimeUtils.getCurTimeString() + "\n");
            pw.write("Android Version: " + mAndroidVersion + "\n");
            pw.write("Device Model: " + mModel + "\n");
            pw.write("Device Manufacturer: " + mManufacturer + "\n");
            pw.write("App Version: v" + sVERSION + "\n\n");
            pw.write("*************---------Crash  Log  Head ------------****************\n\n");
            if (!isSimple)
                throwable.printStackTrace(pw);
            else {
                pw.write(throwable.getLocalizedMessage() + "\n");
            }
        } catch (IOException e) {
            return;
        } finally {
            FileUtils.closeIO(pw);
        }
        FileUtils.createFileByDeleteOldFile(sBuilder.getCrash_tag());
        if (mHandler != null) {
            mHandler.uncaughtException(thread, throwable);
        }
    }

    public static class CrashBuilder {
        private String carsh_dir;
        public String getCrash_dir() {
            return carsh_dir;
        }
        public String getCarsh_log() {
            return getCrash_dir() + File.separator + "carshRecord.log";
        }
        public String getCrash_tag() {
            return getCrash_dir() + File.separator + ".carshed";
        }
        public CrashBuilder(Context context, String dirName) {
            if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                this.carsh_dir = context.getCacheDir().getPath() + File.separator + dirName;
            } else {
                this.carsh_dir = context.getExternalCacheDir().getPath() + File.separator + dirName;
            }
        }
        public static CrashBuilder build(Context context, String dirName) {
            return new CrashBuilder(context, dirName);
        }

        @Override
        public String toString() {
            return "CarshBuilder [dir path: " + getCrash_dir() + "-- log path:" + getCarsh_log() + "-- tag path:" +
                    getCrash_tag() + "]";
        }
    }

    /**
     * 获取log 日志路径
     */
    public static String getLogFilePath() {
        if (sBuilder == null)
            return "Unknown";
        else
            return sBuilder.getCarsh_log();
    }

    /**
     * 获取 LOG 记录的内容
     */
    public static String getLogContent() {
        return FileUtils.readFile2String(getLogFilePath(), "UTF-8");
    }
}
