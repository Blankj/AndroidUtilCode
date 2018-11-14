package com.blankj.util;

import org.gradle.api.logging.Logger;

public final class LogUtils {

    private static Logger mLogger = Utils.getProject().getLogger();
    private static String PREFIX = "PLUGIN-BUS >>> ";

    public static void l(Object content) {
        mLogger.lifecycle(PREFIX + content);
    }

    public static void d(Object content) {
        mLogger.debug(PREFIX + content);
    }

    public static void i(Object content) {
        mLogger.info(PREFIX + content);
    }

    public static void w(Object content) {
        mLogger.warn(PREFIX + content);
    }

    public static void e(Object content) {
        mLogger.error(PREFIX + content);
    }
}