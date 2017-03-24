package com.blankj.utilcode.util;

import android.os.Environment;
import android.support.annotation.IntDef;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/9/21
 *     desc  : 日志相关工具类
 * </pre>
 */
public final class LogUtils {

    private LogUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static final int V = 0x01;
    public static final int D = 0x02;
    public static final int I = 0x04;
    public static final int W = 0x08;
    public static final int E = 0x10;
    public static final int A = 0x20;

    @IntDef({V, D, I, W, E, A})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TYPE {
    }

    private static final int FILE = 0xF1;
    private static final int JSON = 0xF2;
    private static final int XML  = 0xF4;

    private static String dir;                      // log存储目录
    private static String  sGlobalTag       = null; // log标签
    private static boolean sTagIsSpace      = true; // log标签是否为空白
    private static boolean sLogSwitch       = true; // log总开关
    private static boolean sLog2FileSwitch  = false;// log写入文件开关
    private static boolean sLogBorderSwitch = false;// log边框开关
    private static int     sLogFilter       = V;    // log过滤器

    private static       int    stackIndex = 0;
    private static final int    MAX_LEN    = 4000;
    private static final String NULL_TIPS  = "Log with null object.";
    private static final String NULL       = "null";
    private static final String PARAM      = "Param";

    public static class Builder {

        public Builder() {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                dir = Utils.getContext().getExternalCacheDir().getPath() + File.separator + "log" + File.separator;
            } else {
                dir = Utils.getContext().getCacheDir().getPath() + File.separator + "log" + File.separator;
            }
        }

        public Builder setGlobalTag(String tag) {
            if (!isSpace(tag)) {
                LogUtils.sGlobalTag = tag;
                sTagIsSpace = false;
            } else {
                LogUtils.sGlobalTag = "";
                sTagIsSpace = true;
            }
            return this;
        }

        public Builder setLogSwitch(boolean logSwitch) {
            LogUtils.sLogSwitch = logSwitch;
            return this;
        }

        public Builder setLog2FileSwitch(boolean log2FileSwitch) {
            LogUtils.sLog2FileSwitch = log2FileSwitch;
            return this;
        }

        public Builder setBorderSwitch(boolean borderSwitch) {
            LogUtils.sLogBorderSwitch = borderSwitch;
            return this;
        }

        public Builder setLogFilter(@TYPE int logFilter) {
            LogUtils.sLogFilter = logFilter;
            return this;
        }
    }

    public static void v(Object... contents) {
        log(V, sGlobalTag, contents);
    }

    public static void v(String tag, Object... contents) {
        log(V, tag, contents);
    }

    public static void d(Object... contents) {
        log(D, sGlobalTag, contents);
    }

    public static void d(String tag, Object... contents) {
        log(D, tag, contents);
    }

    public static void i(Object... contents) {
        log(I, sGlobalTag, contents);
    }

    public static void i(String tag, Object... contents) {
        log(I, tag, contents);
    }

    public static void w(Object... contents) {
        log(W, sGlobalTag, contents);
    }

    public static void w(String tag, Object... contents) {
        log(W, tag, contents);
    }

    public static void e(Object... contents) {
        log(E, sGlobalTag, contents);
    }

    public static void e(String tag, Object... contents) {
        log(E, tag, contents);
    }

    public static void a(Object... contents) {
        log(A, sGlobalTag, contents);
    }

    public static void a(String tag, Object... contents) {
        log(A, tag, contents);
    }

    public static void file(Object... contents) {
        log(FILE, sGlobalTag, contents);
    }

    public static void file(String tag, Object... contents) {
        log(FILE, tag, contents);
    }

    public static void json(Object... contents) {
        log(JSON, sGlobalTag, contents);
    }

    public static void json(String tag, Object... contents) {
        log(JSON, tag, contents);
    }

    public static void xml(Object... contents) {
        log(XML, sGlobalTag, contents);
    }

    public static void xml(String tag, Object... contents) {
        log(XML, tag, contents);
    }

    private static void log(int type, String tag, Object... contents) {
        if (!sLogSwitch) return;
        String[] processContents = processContents(tag, contents);
        tag = processContents[0];
        String msg = processContents[1];
        switch (type) {
            case V:
            case D:
            case I:
            case W:
            case E:
            case A:
                if (V == sLogFilter || type == sLogFilter) {
                    printLog(type, tag, msg);
                }
                break;
            case FILE:
//                printFile();
                break;
            case JSON:
//                printJson();
                break;
            case XML:
//                printXml();
                break;
        }

    }

    private static final String LEFT_BORDER = "║ ";

    // Length: 100.
    private static final String TOP_BORDER =
            "╔═════════════════════════════════════════════════" +
                    "══════════════════════════════════════════════════";

    // Length: 100.
    private static final String BOTTOM_BORDER =
            "╚═════════════════════════════════════════════════" +
                    "══════════════════════════════════════════════════";

    private static String[] processContents(String tag, Object... contents) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackIndex == 0) {
            while (!stackTrace[stackIndex].getMethodName().equals("processContents")) {
                ++stackIndex;
            }
            stackIndex += 3;
        }
        StackTraceElement targetElement = stackTrace[stackIndex];
        String className = targetElement.getClassName();
        String[] classNameInfo = className.split("\\.");
        if (classNameInfo.length > 0) {
            className = classNameInfo[classNameInfo.length - 1];
        }
        if (className.contains("$")) {
            className = className.split("\\$")[0];
        }
        if (!sTagIsSpace) {// 如果全局tag不为空，那就用全局tag
            tag = sGlobalTag;
        } else {// 全局tag为空时，如果传入的tag为空那就显示类名，否则显示tag
            tag = isSpace(tag) ? className : tag;
        }

        String header = new Formatter()
                .format("Thread: %s at %s(%s.java:%d)\n",
                        Thread.currentThread().getName(),
                        targetElement.getMethodName(),
                        className,
                        targetElement.getLineNumber())
                .toString();
//        StringBuilder result = new StringBuilder();
//        if (sLogBorderSwitch) {
//            result.append(TOP_BORDER);
//        } else {
//
//        }

        String body;

        if (contents == null) {
            body = NULL_TIPS;
        } else {
            if (contents.length == 1) {
                Object object = contents[0];
                body = object == null ? NULL : object.toString();
            } else {
                StringBuilder sb = new StringBuilder();
                for (int i = 0, len = contents.length; i < len; ++i) {
                    Object content = contents[i];
                    sb.append(PARAM)
                            .append("[")
                            .append(i)
                            .append("]")
                            .append(" = ")
                            .append(content == null ? NULL : content.toString())
                            .append("\n");
                }
                body = sb.toString();
            }
        }
        return new String[]{tag, header + body};
    }

    private static void printLog(int type, String tag, String msg) {
        int len = msg.length();
        int countOfSub = len / MAX_LEN;
        if (countOfSub > 0) {
            int index = 0;
            for (int i = 0; i < countOfSub; i++) {
                String sub = msg.substring(index, index + MAX_LEN);
                printSubLog(type, tag, sub);
                index += MAX_LEN;
            }
            printSubLog(type, tag, msg.substring(index, len));
        } else {
            printSubLog(type, tag, msg);
        }
    }

    private static void printSubLog(int type, String tag, String msg) {
        switch (type) {
            case V:
                Log.v(tag, msg);
                break;
            case D:
                Log.d(tag, msg);
                break;
            case I:
                Log.i(tag, msg);
                break;
            case W:
                Log.w(tag, msg);
                break;
            case E:
                Log.e(tag, msg);
                break;
            case A:
                Log.wtf(tag, msg);
                break;
        }
    }

    private synchronized static void log2File(final char type, final String tag, final String msg) {
        Date now = new Date();
        String date = new SimpleDateFormat("MM-dd", Locale.getDefault()).format(now);
        final String fullPath = dir + date + ".txt";
        if (!FileUtils.createOrExistsFile(fullPath)) return;
        String time = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.getDefault()).format(now);
        final String dateLogContent = time + ":" + type + ":" + tag + ":" + msg + '\n';
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

    private static boolean isSpace(String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}