package com.blankj.base_transform.util

import org.gradle.api.Project
import org.gradle.api.logging.Logger

final class LogUtils {

    private static Logger sLogger

    static void init(Project project) {
        sLogger = project.getLogger()
    }

    static void l(Object content) {
        l("", content)
    }

    static void d(Object content) {
        d("", content)
    }

    static void i(Object content) {
        i("", content)
    }

    static void w(Object content) {
        w("", content)
    }

    static void e(Object content) {
        e("", content)
    }

    static void l(String tag, Object content) {
        sLogger.lifecycle(getTag(tag) + content)
    }

    static void d(String tag, Object content) {
        sLogger.debug(getTag(tag) + content)
    }

    static void i(String tag, Object content) {
        sLogger.info(getTag(tag) + content)
    }

    static void w(String tag, Object content) {
        sLogger.warn(getTag(tag) + content)
    }

    static void e(String tag, Object content) {
        sLogger.error(getTag(tag) + content)
    }

    private static String getTag(String tag) {
        if (tag == null || tag.isEmpty()) {
            return "LogUtils >>> "
        }
        return tag + " >>> "
    }
}