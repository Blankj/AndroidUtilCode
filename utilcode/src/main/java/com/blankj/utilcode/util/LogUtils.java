package com.blankj.utilcode.util;

import android.os.Environment;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/21
 *     desc  : Log相关工具类
 * </pre>
 */
public final class LogUtils {

    public static final int V = Log.VERBOSE;
    public static final int D = Log.DEBUG;
    public static final int I = Log.INFO;
    public static final int W = Log.WARN;
    public static final int E = Log.ERROR;
    public static final int A = Log.ASSERT;

    @IntDef({V, D, I, W, E, A})
    @Retention(RetentionPolicy.SOURCE)
    private @interface TYPE {
    }

    private static final char[] T = new char[]{'V', 'D', 'I', 'W', 'E', 'A'};

    private static final int FILE = 0x10;
    private static final int JSON = 0x20;
    private static final int XML  = 0x30;

    private static ExecutorService sExecutor;
    private static String          sDefaultDir;// log默认存储目录
    private static String          sDir;       // log存储目录
    private static String  sFilePrefix        = "util";// log文件前缀
    private static boolean sLogSwitch         = true;  // log总开关，默认开
    private static boolean sLog2ConsoleSwitch = true;  // logcat是否打印，默认打印
    private static String  sGlobalTag         = null;  // log标签
    private static boolean sTagIsSpace        = true;  // log标签是否为空白
    private static boolean sLogHeadSwitch     = true;  // log头部开关，默认开
    private static boolean sLog2FileSwitch    = false; // log写入文件开关，默认关
    private static boolean sLogBorderSwitch   = true;  // log边框开关，默认开
    private static int     sConsoleFilter     = V;     // log控制台过滤器
    private static int     sFileFilter        = V;     // log文件过滤器
    private static int     sStackDeep         = 1;     // log栈深度

    private static final String FILE_SEP      = System.getProperty("file.separator");
    private static final String LINE_SEP      = System.getProperty("line.separator");
    private static final String TOP_BORDER    = "╔═══════════════════════════════════════════════════════════════════════════════════════════════════";
    private static final String SPLIT_BORDER  = "╟───────────────────────────────────────────────────────────────────────────────────────────────────";
    private static final String LEFT_BORDER   = "║ ";
    private static final String BOTTOM_BORDER = "╚═══════════════════════════════════════════════════════════════════════════════════════════════════";
    private static final int    MAX_LEN       = 4000;
    private static final Format FORMAT        = new SimpleDateFormat("MM-dd HH:mm:ss.SSS ", Locale.getDefault());
    private static final String NULL_TIPS     = "Log with null object.";
    private static final String NULL          = "null";
    private static final String ARGS          = "args";
    private static final Config CONFIG        = new Config();

    private LogUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static Config getConfig() {
        return CONFIG;
    }

    public static void v(final Object contents) {
        log(V, sGlobalTag, contents);
    }

    public static void v(final String tag, final Object... contents) {
        log(V, tag, contents);
    }

    public static void d(final Object contents) {
        log(D, sGlobalTag, contents);
    }

    public static void d(final String tag, final Object... contents) {
        log(D, tag, contents);
    }

    public static void i(final Object contents) {
        log(I, sGlobalTag, contents);
    }

    public static void i(final String tag, final Object... contents) {
        log(I, tag, contents);
    }

    public static void w(final Object contents) {
        log(W, sGlobalTag, contents);
    }

    public static void w(final String tag, final Object... contents) {
        log(W, tag, contents);
    }

    public static void e(final Object contents) {
        log(E, sGlobalTag, contents);
    }

    public static void e(final String tag, final Object... contents) {
        log(E, tag, contents);
    }

    public static void a(final Object contents) {
        log(A, sGlobalTag, contents);
    }

    public static void a(final String tag, final Object... contents) {
        log(A, tag, contents);
    }

    public static void file(final Object contents) {
        log(FILE | D, sGlobalTag, contents);
    }

    public static void file(@TYPE final int type, final Object contents) {
        log(FILE | type, sGlobalTag, contents);
    }

    public static void file(final String tag, final Object contents) {
        log(FILE | D, tag, contents);
    }

    public static void file(@TYPE final int type, final String tag, final Object contents) {
        log(FILE | type, tag, contents);
    }

    public static void json(final String contents) {
        log(JSON | D, sGlobalTag, contents);
    }

    public static void json(@TYPE final int type, final String contents) {
        log(JSON | type, sGlobalTag, contents);
    }

    public static void json(final String tag, final String contents) {
        log(JSON | D, tag, contents);
    }

    public static void json(@TYPE final int type, final String tag, final String contents) {
        log(JSON | type, tag, contents);
    }

    public static void xml(final String contents) {
        log(XML | D, sGlobalTag, contents);
    }

    public static void xml(@TYPE final int type, final String contents) {
        log(XML | type, sGlobalTag, contents);
    }

    public static void xml(final String tag, final String contents) {
        log(XML | D, tag, contents);
    }

    public static void xml(@TYPE final int type, final String tag, final String contents) {
        log(XML | type, tag, contents);
    }

    private static void log(final int type, final String tag, final Object... contents) {
        if (!sLogSwitch || (!sLog2ConsoleSwitch && !sLog2FileSwitch)) return;
        int type_low = type & 0x0f, type_high = type & 0xf0;
        if (type_low < sConsoleFilter && type_low < sFileFilter) return;
        final TagHead tagHead = processTagAndHead(tag);
        String body = processBody(type_high, contents);
        if (sLog2ConsoleSwitch && type_low >= sConsoleFilter && type_high != FILE) {
            print2Console(type_low, tagHead.tag, tagHead.consoleHead, body);
        }
        if ((sLog2FileSwitch || type_high == FILE) && type_low >= sFileFilter) {
            print2File(type_low, tagHead.tag, tagHead.fileHead + body);
        }
    }

    private static TagHead processTagAndHead(String tag) {
        if (!sTagIsSpace && !sLogHeadSwitch) {
            tag = sGlobalTag;
        } else {
            final StackTraceElement[] stackTrace = new Throwable().getStackTrace();
            StackTraceElement targetElement = stackTrace[3];
            String fileName = targetElement.getFileName();
            String className;
            if (fileName == null) {// 混淆可能会导致获取为空 加-keepattributes SourceFile,LineNumberTable
                className = targetElement.getClassName();
                String[] classNameInfo = className.split("\\.");
                if (classNameInfo.length > 0) {
                    className = classNameInfo[classNameInfo.length - 1];
                }
                int index = className.indexOf('$');
                if (index != -1) {
                    className = className.substring(0, index);
                }
                fileName = className + ".java";
            } else {
                int index = fileName.indexOf('.');// 混淆可能导致文件名被改变从而找不到"."
                className = index == -1 ? fileName : fileName.substring(0, index);
            }
            if (sTagIsSpace) tag = isSpace(tag) ? className : tag;
            if (sLogHeadSwitch) {
                String tName = Thread.currentThread().getName();
                final String head = new Formatter()
                        .format("%s, %s(%s:%d)",
                                tName,
                                targetElement.getMethodName(),
                                fileName,
                                targetElement.getLineNumber())
                        .toString();
                final String fileHead = " [" + head + "]: ";
                if (sStackDeep <= 1) {
                    return new TagHead(tag, new String[]{head}, fileHead);
                } else {
                    final String[] consoleHead = new String[Math.min(sStackDeep, stackTrace.length - 3)];
                    consoleHead[0] = head;
                    int spaceLen = tName.length() + 2;
                    String space = new Formatter().format("%" + spaceLen + "s", "").toString();
                    for (int i = 1, len = consoleHead.length; i < len; ++i) {
                        targetElement = stackTrace[i + 3];
                        consoleHead[i] = new Formatter()
                                .format("%s%s(%s:%d)",
                                        space,
                                        targetElement.getMethodName(),
                                        targetElement.getFileName(),
                                        targetElement.getLineNumber())
                                .toString();
                    }
                    return new TagHead(tag, consoleHead, fileHead);
                }
            }
        }
        return new TagHead(tag, null, ": ");
    }

    private static String processBody(final int type, final Object... contents) {
        String body = NULL_TIPS;
        if (contents != null) {
            if (contents.length == 1) {
                Object object = contents[0];
                body = object == null ? NULL : object.toString();
                if (type == JSON) {
                    body = formatJson(body);
                } else if (type == XML) {
                    body = formatXml(body);
                }
            } else {
                StringBuilder sb = new StringBuilder();
                for (int i = 0, len = contents.length; i < len; ++i) {
                    Object content = contents[i];
                    sb.append(ARGS)
                            .append("[")
                            .append(i)
                            .append("]")
                            .append(" = ")
                            .append(content == null ? NULL : content.toString())
                            .append(LINE_SEP);
                }
                body = sb.toString();
            }
        }
        return body;
    }

    private static String formatJson(String json) {
        try {
            if (json.startsWith("{")) {
                json = new JSONObject(json).toString(4);
            } else if (json.startsWith("[")) {
                json = new JSONArray(json).toString(4);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    private static String formatXml(String xml) {
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(xmlInput, xmlOutput);
            xml = xmlOutput.getWriter().toString().replaceFirst(">", ">" + LINE_SEP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xml;
    }

    private static void print2Console(final int type, final String tag, final String[] head, String msg) {
        printBorder(type, tag, true);
        printHead(type, tag, head);
        printMsg(type, tag, msg);
        printBorder(type, tag, false);
    }

    private static void printBorder(final int type, final String tag, boolean isTop) {
        if (sLogBorderSwitch) {
            Log.println(type, tag, isTop ? TOP_BORDER : BOTTOM_BORDER);
        }
    }

    private static void printHead(final int type, final String tag, final String[] head) {
        if (head != null) {
            for (String aHead : head) {
                Log.println(type, tag, sLogBorderSwitch ? LEFT_BORDER + aHead : aHead);
            }
            Log.println(type, tag, SPLIT_BORDER);
        }
    }

    private static void printMsg(final int type, final String tag, final String msg) {
        int len = msg.length();
        int countOfSub = len / MAX_LEN;
        if (countOfSub > 0) {
            int index = 0;
            for (int i = 0; i < countOfSub; i++) {
                printSubMsg(type, tag, msg.substring(index, index + MAX_LEN));
                index += MAX_LEN;
            }
            if (index != len) {
                printSubMsg(type, tag, msg.substring(index, len));
            }
        } else {
            printSubMsg(type, tag, msg);
        }
    }

    private static void printSubMsg(final int type, final String tag, final String msg) {
        if (!sLogBorderSwitch) {
            Log.println(type, tag, msg);
            return;
        }
        StringBuilder sb = new StringBuilder();
        String[] lines = msg.split(LINE_SEP);
        for (String line : lines) {
            Log.println(type, tag, LEFT_BORDER + line);
        }
    }

    private static void print2File(final int type, final String tag, final String msg) {
        Date now = new Date(System.currentTimeMillis());
        String format = FORMAT.format(now);
        String date = format.substring(0, 5);
        String time = format.substring(6);
        final String fullPath = (sDir == null ? sDefaultDir : sDir) + sFilePrefix + "-" + date + ".txt";
        if (!createOrExistsFile(fullPath)) {
            Log.e(tag, "log to " + fullPath + " failed!");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(time)
                .append(T[type - V])
                .append("/")
                .append(tag)
                .append(msg)
                .append(LINE_SEP);
        final String content = sb.toString();
        if (sExecutor == null) {
            sExecutor = Executors.newSingleThreadExecutor();
        }
        sExecutor.execute(new Runnable() {
            @Override
            public void run() {
                BufferedWriter bw = null;
                try {
                    bw = new BufferedWriter(new FileWriter(fullPath, true));
                    bw.write(content);
                    Log.d(tag, "log to " + fullPath + " success!");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(tag, "log to " + fullPath + " failed!");
                } finally {
                    try {
                        if (bw != null) {
                            bw.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private static boolean createOrExistsFile(final String filePath) {
        File file = new File(filePath);
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static class Config {
        private Config() {
            if (sDefaultDir != null) return;
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    && Utils.getApp().getExternalCacheDir() != null)
                sDefaultDir = Utils.getApp().getExternalCacheDir() + FILE_SEP + "log" + FILE_SEP;
            else {
                sDefaultDir = Utils.getApp().getCacheDir() + FILE_SEP + "log" + FILE_SEP;
            }
        }

        public Config setLogSwitch(final boolean logSwitch) {
            sLogSwitch = logSwitch;
            return this;
        }

        public Config setConsoleSwitch(final boolean consoleSwitch) {
            sLog2ConsoleSwitch = consoleSwitch;
            return this;
        }

        public Config setGlobalTag(final String tag) {
            if (isSpace(tag)) {
                sGlobalTag = "";
                sTagIsSpace = true;
            } else {
                sGlobalTag = tag;
                sTagIsSpace = false;
            }
            return this;
        }

        public Config setLogHeadSwitch(final boolean logHeadSwitch) {
            sLogHeadSwitch = logHeadSwitch;
            return this;
        }

        public Config setLog2FileSwitch(final boolean log2FileSwitch) {
            sLog2FileSwitch = log2FileSwitch;
            return this;
        }

        public Config setDir(final String dir) {
            if (isSpace(dir)) {
                sDir = null;
            } else {
                sDir = dir.endsWith(FILE_SEP) ? dir : dir + FILE_SEP;
            }
            return this;
        }

        public Config setDir(final File dir) {
            sDir = dir == null ? null : dir.getAbsolutePath() + FILE_SEP;
            return this;
        }

        public Config setFilePrefix(final String filePrefix) {
            if (isSpace(filePrefix)) {
                sFilePrefix = "util";
            } else {
                sFilePrefix = filePrefix;
            }
            return this;
        }

        public Config setBorderSwitch(final boolean borderSwitch) {
            sLogBorderSwitch = borderSwitch;
            return this;
        }

        public Config setConsoleFilter(@TYPE final int consoleFilter) {
            sConsoleFilter = consoleFilter;
            return this;
        }

        public Config setFileFilter(@TYPE final int fileFilter) {
            sFileFilter = fileFilter;
            return this;
        }

        public Config setStackDeep(@IntRange(from = 1) final int stackDeep) {
            sStackDeep = stackDeep;
            return this;
        }

        @Override
        public String toString() {
            return "switch: " + sLogSwitch
                    + LINE_SEP + "console: " + sLog2ConsoleSwitch
                    + LINE_SEP + "tag: " + (sTagIsSpace ? "null" : sGlobalTag)
                    + LINE_SEP + "head: " + sLogHeadSwitch
                    + LINE_SEP + "file: " + sLog2FileSwitch
                    + LINE_SEP + "dir: " + (sDir == null ? sDefaultDir : sDir)
                    + LINE_SEP + "filePrefix" + sFilePrefix
                    + LINE_SEP + "border: " + sLogBorderSwitch
                    + LINE_SEP + "consoleFilter: " + T[sConsoleFilter - V]
                    + LINE_SEP + "fileFilter: " + T[sFileFilter - V]
                    + LINE_SEP + "stackDeep: " + sStackDeep;
        }
    }

    private static class TagHead {
        String   tag;
        String[] consoleHead;
        String   fileHead;

        TagHead(String tag, String[] consoleHead, String fileHead) {
            this.tag = tag;
            this.consoleHead = consoleHead;
            this.fileHead = fileHead;
        }
    }
}
