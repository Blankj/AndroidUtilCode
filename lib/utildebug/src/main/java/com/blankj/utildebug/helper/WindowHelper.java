package com.blankj.utildebug.helper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.Utils;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/08/29
 *     desc  :
 * </pre>
 */
public class WindowHelper {

    private static WindowManager sWM;

    private WindowHelper() {
    }

    public static void updateViewLayout(final View view, ViewGroup.LayoutParams params) {
        getWindowManager().updateViewLayout(view, params);
    }

    public static int getAppWindowHeight() {
        return ScreenUtils.getAppScreenHeight();
    }

    public static WindowManager getWindowManager() {
        if (sWM == null) {
            sWM = (WindowManager) Utils.getApp().getSystemService(Context.WINDOW_SERVICE);
        }
        return sWM;
    }
}
