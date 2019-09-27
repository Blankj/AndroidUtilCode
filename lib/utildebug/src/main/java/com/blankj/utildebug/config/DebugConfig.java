package com.blankj.utildebug.config;

import android.view.WindowManager;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utildebug.base.view.BaseContentFloatView;

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

    public static void saveFloatViewY(BaseContentFloatView floatView, int y) {
        getSp().put(floatView.getClass().getSimpleName() + ".y", y);
    }

    public static int getFloatViewY(BaseContentFloatView floatView) {
        return getSp().getInt(floatView.getClass().getSimpleName() + ".y", 0);
    }

    public static void saveFloatViewHeight(BaseContentFloatView floatView, int height) {
        getSp().put(floatView.getClass().getSimpleName() + ".height", height);
    }

    public static int getFloatViewHeight(BaseContentFloatView floatView) {
        return getSp().getInt(floatView.getClass().getSimpleName() + ".height", WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public static void saveFloatViewAlpha(BaseContentFloatView floatView, float alpha) {
        getSp().put(floatView.getClass().getSimpleName() + ".alpha", alpha);
    }

    public static float getFloatViewAlpha(BaseContentFloatView floatView) {
        return getSp().getFloat(floatView.getClass().getSimpleName() + ".alpha", 1f);
    }

    private static SPUtils getSp() {
        return SPUtils.getInstance("DebugUtils");
    }
}
