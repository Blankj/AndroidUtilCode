package com.blankj.util

import org.gradle.api.Project
import org.gradle.api.logging.Logger

final class LogUtils {

    private static Logger sLogger
    private static String PREFIX = "PLUGIN-BUS >>> "

    static void init(Project project) {
        sLogger = project.getLogger()
    }

    static void l(Object content) {
        sLogger.lifecycle(PREFIX + content)
    }

    static void d(Object content) {
        sLogger.debug(PREFIX + content)
    }

    static void i(Object content) {
        sLogger.info(PREFIX + content)
    }

    static void w(Object content) {
        sLogger.warn(PREFIX + content)
    }

    static void e(Object content) {
        sLogger.error(PREFIX + content)
    }
}