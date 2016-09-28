package com.blankj.utilcode.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Looper;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/9/27
 *     desc  :
 * </pre>
 */
public class CrashUtils implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private static CrashUtils INSTANCE;
    private Context mContext;

    private CrashUtils() {
        throw new AssertionError();
    }

    public static CrashUtils getInstance() {
        if (INSTANCE == null)
            INSTANCE = new CrashUtils();
        return INSTANCE;
    }

    public void init(Context context) { // 在Application中初始化
        mContext = context;

        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();// 获取系统默认的UncaughtException处理器
        Thread.setDefaultUncaughtExceptionHandler(this);// 设置该CrashHandler为程序的默认处理器
    }
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果自定义的没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        }
    }

    /**
     * 自定义错误处理、收集错误信息、发送错误报告
     *
     * @param ex 异常信息
     * @return true 如果处理了该异常信息;否则返回false.
     */
    public boolean handleException(Throwable ex) {
        if (ex == null || mContext == null)
            return false;
        final String crashReport = getCrashReport(mContext, ex);
        new Thread() {
            public void run() {
                Looper.prepare();
                LogUtils.e("CrashHandler", crashReport); // 打印异常信息，也可保存至文件 方便远程调试
                Looper.loop();
            }

        }.start();
        return true;
    }

    /**
     * 获取APP崩溃异常报告
     *
     * @param ex
     * @return
     */
    private String getCrashReport(Context context, Throwable ex) {
//        PackageInfo pinfo = AppUtils.getPackageInfo(context);
        StringBuffer exceptionStr = new StringBuffer();
//        exceptionStr.append("Version: " + pinfo.versionName + "(" + pinfo.versionCode + ")\n");
        exceptionStr.append("Android: " + android.os.Build.VERSION.RELEASE + "(" + android.os.Build.MODEL + ")\n");
        exceptionStr.append("Exception: " + ex.getMessage() + "\n");
        StackTraceElement[] elements = ex.getStackTrace();
        for (int i = 0; i < elements.length; i++) {
            exceptionStr.append(elements[i].toString() + "\n");
        }
        return exceptionStr.toString();
    }
}
