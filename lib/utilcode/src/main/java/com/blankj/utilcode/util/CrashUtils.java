package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.NonNull;

import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/27
 *     desc  : utils about crash
 * </pre>
 */
public final class CrashUtils {

    private static final String FILE_SEP = System.getProperty("file.separator");

    private static final UncaughtExceptionHandler DEFAULT_UNCAUGHT_EXCEPTION_HANDLER = Thread.getDefaultUncaughtExceptionHandler();

    private CrashUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Initialization.
     */
    @SuppressLint("MissingPermission")
    public static void init() {
        init("");
    }

    /**
     * Initialization
     *
     * @param crashDir The directory of saving crash information.
     */
    public static void init(@NonNull final File crashDir) {
        init(crashDir.getAbsolutePath(), null);
    }

    /**
     * Initialization
     *
     * @param crashDirPath The directory's path of saving crash information.
     */
    public static void init(final String crashDirPath) {
        init(crashDirPath, null);
    }

    /**
     * Initialization
     *
     * @param onCrashListener The crash listener.
     */
    public static void init(final OnCrashListener onCrashListener) {
        init("", onCrashListener);
    }

    /**
     * Initialization
     *
     * @param crashDir        The directory of saving crash information.
     * @param onCrashListener The crash listener.
     */
    public static void init(@NonNull final File crashDir, final OnCrashListener onCrashListener) {
        init(crashDir.getAbsolutePath(), onCrashListener);
    }

    /**
     * Initialization
     *
     * @param crashDirPath    The directory's path of saving crash information.
     * @param onCrashListener The crash listener.
     */
    public static void init(final String crashDirPath, final OnCrashListener onCrashListener) {
        String dirPath;
        if (UtilsBridge.isSpace(crashDirPath)) {
            if (UtilsBridge.isSDCardEnableByEnvironment()
                    && Utils.getApp().getExternalFilesDir(null) != null)
                dirPath = Utils.getApp().getExternalFilesDir(null) + FILE_SEP + "crash" + FILE_SEP;
            else {
                dirPath = Utils.getApp().getFilesDir() + FILE_SEP + "crash" + FILE_SEP;
            }
        } else {
            dirPath = crashDirPath.endsWith(FILE_SEP) ? crashDirPath : crashDirPath + FILE_SEP;
        }
        Thread.setDefaultUncaughtExceptionHandler(getUncaughtExceptionHandler(dirPath, onCrashListener));
    }

    private static UncaughtExceptionHandler getUncaughtExceptionHandler(final String dirPath,
                                                                        final OnCrashListener onCrashListener) {
        return new UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(@NonNull final Thread t, @NonNull final Throwable e) {
                final String time = new SimpleDateFormat("yyyy_MM_dd-HH_mm_ss").format(new Date());
                final StringBuilder sb = new StringBuilder();
                final String head = "************* Log Head ****************" +
                        "\nTime Of Crash      : " + time +
                        "\nDevice Manufacturer: " + Build.MANUFACTURER +
                        "\nDevice Model       : " + Build.MODEL +
                        "\nAndroid Version    : " + Build.VERSION.RELEASE +
                        "\nAndroid SDK        : " + Build.VERSION.SDK_INT +
                        "\nApp VersionName    : " + UtilsBridge.getAppVersionName() +
                        "\nApp VersionCode    : " + UtilsBridge.getAppVersionCode() +
                        "\n************* Log Head ****************\n\n";
                sb.append(head).append(UtilsBridge.getFullStackTrace(e));
                final String crashInfo = sb.toString();
                final String crashFile = dirPath + time + ".txt";
                UtilsBridge.writeFileFromString(crashFile, crashInfo, true);

                if (onCrashListener != null) {
                    onCrashListener.onCrash(crashInfo, e);
                }

                if (DEFAULT_UNCAUGHT_EXCEPTION_HANDLER != null) {
                    DEFAULT_UNCAUGHT_EXCEPTION_HANDLER.uncaughtException(t, e);
                }
            }
        };
    }

    ///////////////////////////////////////////////////////////////////////////
    // interface
    ///////////////////////////////////////////////////////////////////////////

    public interface OnCrashListener {
        void onCrash(String crashInfo, Throwable e);
    }
}
