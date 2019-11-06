package com.blankj.utildebug.config;

import android.view.View;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/09/02
 *     desc  :
 * </pre>
 */
public class DebugConfig {

    private static final String DEBUG_ICON_X     = "DEBUG_ICON_X";
    private static final String DEBUG_ICON_Y     = "DEBUG_ICON_Y";
    private static final String NO_MORE_REMINDER = "NO_MORE_REMINDER";

    public static void saveDebugIconX(float x) {
        getSp().put(DEBUG_ICON_X, x);
    }

    public static float getDebugIconX() {
        return getSp().getFloat(DEBUG_ICON_X);
    }

    public static void saveDebugIconY(float y) {
        getSp().put(DEBUG_ICON_Y, y);
    }

    public static float getDebugIconY() {
        return getSp().getFloat(DEBUG_ICON_Y, ScreenUtils.getAppScreenHeight() / 3);
    }

    public static void saveNoMoreReminder() {
        getSp().put(NO_MORE_REMINDER, true);
    }

    public static boolean isNoMoreReminder() {
        return getSp().getBoolean(NO_MORE_REMINDER, false);
    }

    public static void saveViewY(View view, int y) {
        if (ScreenUtils.isPortrait()) {
            getSp().put(view.getClass().getSimpleName() + ".yP", y);
        } else {
            getSp().put(view.getClass().getSimpleName() + ".yL", y);
        }
    }

    public static int getViewY(View view) {
        return getViewY(view, 0);
    }

    public static int getViewY(View view, int defaultVal) {
        if (ScreenUtils.isPortrait()) {
            return getSp().getInt(view.getClass().getSimpleName() + ".yP", defaultVal);
        } else {
            return getSp().getInt(view.getClass().getSimpleName() + ".yL", defaultVal);
        }
    }

    public static void saveViewX(View view, int x) {
        if (ScreenUtils.isPortrait()) {
            getSp().put(view.getClass().getSimpleName() + ".xP", x);
        } else {
            getSp().put(view.getClass().getSimpleName() + ".xL", x);
        }
    }

    public static int getViewX(View view) {
        if (ScreenUtils.isPortrait()) {
            return getSp().getInt(view.getClass().getSimpleName() + ".xP");
        } else {
            return getSp().getInt(view.getClass().getSimpleName() + ".xL");
        }
    }

    public static void saveViewHeight(View view, int height) {
        if (ScreenUtils.isPortrait()) {
            getSp().put(view.getClass().getSimpleName() + ".heightP", height);
        } else {
            getSp().put(view.getClass().getSimpleName() + ".heightL", height);
        }
    }

    public static int getViewHeight(View view, int height) {
        if (ScreenUtils.isPortrait()) {
            return getSp().getInt(view.getClass().getSimpleName() + ".heightP", height);
        } else {
            return getSp().getInt(view.getClass().getSimpleName() + ".heightL", height);
        }
    }

    public static void saveViewAlpha(View view, float alpha) {
        getSp().put(view.getClass().getSimpleName() + ".alpha", alpha);
    }

    public static float getViewAlpha(View view) {
        return getSp().getFloat(view.getClass().getSimpleName() + ".alpha", 1f);
    }

    private static SPUtils getSp() {
        return SPUtils.getInstance("DebugUtils");
    }
}
