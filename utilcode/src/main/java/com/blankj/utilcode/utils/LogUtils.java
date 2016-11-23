package com.blankj.utilcode.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/9/21
 *     desc  : 日志相关工具类
 * </pre>
 */
public class LogUtils {

    private LogUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static boolean logSwitch      = true;
    private static boolean log2FileSwitch = false;
    private static char    logFilter      = 'v';
    private static String  tag            = "TAG";
    private static String  dir            = null;

    /**
     * 初始化函数
     * <p>与{@link #getBuilder(Context)}两者选其一</p>
     *
     * @param context        上下文
     * @param logSwitch      日志总开关
     * @param log2FileSwitch 日志写入文件开关
     * @param logFilter      输入日志类型有{@code v, d, i, w, e}<br>v代表输出所有信息，w则只输出警告...
     * @param tag            标签
     */
    public static void init(Context context, boolean logSwitch, boolean log2FileSwitch, char logFilter, String tag) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            dir = context.getExternalCacheDir().getPath() + File.separator;
        } else {
            dir = context.getCacheDir().getPath() + File.separator;
        }
        LogUtils.logSwitch = logSwitch;
        LogUtils.log2FileSwitch = log2FileSwitch;
        LogUtils.logFilter = logFilter;
        LogUtils.tag = tag;
    }

    /**
     * 获取LogUtils建造者
     * <p>与{@link #init(Context, boolean, boolean, char, String)}两者选其一</p>
     *
     * @param context 上下文
     * @return Builder对象
     */
    public static Builder getBuilder(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            dir = context.getExternalCacheDir().getPath() + File.separator + "log" + File.separator;
        } else {
            dir = context.getCacheDir().getPath() + File.separator + "log" + File.separator;
        }
        return new Builder();
    }

    public static class Builder {

        private boolean logSwitch      = true;
        private boolean log2FileSwitch = false;
        private char    logFilter      = 'v';
        private String  tag            = "TAG";

        public Builder setLogSwitch(boolean logSwitch) {
            this.logSwitch = logSwitch;
            return this;
        }

        public Builder setLog2FileSwitch(boolean log2FileSwitch) {
            this.log2FileSwitch = log2FileSwitch;
            return this;
        }

        public Builder setLogFilter(char logFilter) {
            this.logFilter = logFilter;
            return this;
        }

        public Builder setTag(String tag) {
            this.tag = tag;
            return this;
        }

        public void create() {
            LogUtils.logSwitch = logSwitch;
            LogUtils.log2FileSwitch = log2FileSwitch;
            LogUtils.logFilter = logFilter;
            LogUtils.tag = tag;
        }
    }

    /**
     * Verbose日志
     *
     * @param msg 消息
     */
    public static void v(Object msg) {
        v(tag, msg);
    }

    /**
     * Verbose日志
     *
     * @param tag 标签
     * @param msg 消息
     */
    public static void v(String tag, Object msg) {
        v(tag, msg, null);
    }

    /**
     * Verbose日志
     *
     * @param tag 标签
     * @param msg 消息
     * @param tr  异常
     */
    public static void v(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'v');
    }

    /**
     * Debug日志
     *
     * @param msg 消息
     */
    public static void d(Object msg) {
        d(tag, msg);
    }

    /**
     * Debug日志
     *
     * @param tag 标签
     * @param msg 消息
     */
    public static void d(String tag, Object msg) {// 调试信息
        d(tag, msg, null);
    }

    /**
     * Debug日志
     *
     * @param tag 标签
     * @param msg 消息
     * @param tr  异常
     */
    public static void d(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'd');
    }

    /**
     * Info日志
     *
     * @param msg 消息
     */
    public static void i(Object msg) {
        i(tag, msg);
    }

    /**
     * Info日志
     *
     * @param tag 标签
     * @param msg 消息
     */
    public static void i(String tag, Object msg) {
        i(tag, msg, null);
    }

    /**
     * Info日志
     *
     * @param tag 标签
     * @param msg 消息
     * @param tr  异常
     */
    public static void i(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'i');
    }

    /**
     * Warn日志
     *
     * @param msg 消息
     */
    public static void w(Object msg) {
        w(tag, msg);
    }

    /**
     * Warn日志
     *
     * @param tag 标签
     * @param msg 消息
     */
    public static void w(String tag, Object msg) {
        w(tag, msg, null);
    }

    /**
     * Warn日志
     *
     * @param tag 标签
     * @param msg 消息
     * @param tr  异常
     */
    public static void w(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'w');
    }

    /**
     * Error日志
     *
     * @param msg 消息
     */
    public static void e(Object msg) {
        e(tag, msg);
    }

    /**
     * Error日志
     *
     * @param tag 标签
     * @param msg 消息
     */
    public static void e(String tag, Object msg) {
        e(tag, msg, null);
    }

    /**
     * Error日志
     *
     * @param tag 标签
     * @param msg 消息
     * @param tr  异常
     */
    public static void e(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'e');
    }

    /**
     * 根据tag, msg和等级，输出日志
     *
     * @param tag  标签
     * @param msg  消息
     * @param tr   异常
     * @param type 日志类型
     */
    private static void log(String tag, String msg, Throwable tr, char type) {
        if (logSwitch) {
            if ('e' == type && ('e' == logFilter || 'v' == logFilter)) {
                Log.e(tag, msg, tr);
            } else if ('w' == type && ('w' == logFilter || 'v' == logFilter)) {
                Log.w(tag, msg, tr);
            } else if ('d' == type && ('d' == logFilter || 'v' == logFilter)) {
                Log.d(tag, msg, tr);
            } else if ('i' == type && ('d' == logFilter || 'v' == logFilter)) {
                Log.i(tag, msg, tr);
            }
            if (log2FileSwitch) {
                log2File(type, tag, msg + '\n' + Log.getStackTraceString(tr));
            }
        }
    }

    /**
     * 打开日志文件并写入日志
     *
     * @param type    日志类型
     * @param tag     标签
     * @param content 内容
     **/
    private synchronized static void log2File(final char type, final String tag, final String content) {
        if (content == null) return;
        Date now = new Date();
        String date = new SimpleDateFormat("MM-dd", Locale.getDefault()).format(now);
        final String fullPath = dir + date + ".txt";
        if (!FileUtils.createOrExistsFile(fullPath)) return;
        String time = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.getDefault()).format(now);
        final String dateLogContent = time + ":" + type + ":" + tag + ":" + content + '\n';
        new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedWriter bw = null;
                try {
                    bw = new BufferedWriter(new FileWriter(fullPath, true));
                    bw.write(dateLogContent);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    CloseUtils.closeIO(bw);
                }
            }
        }).start();

    }
}
