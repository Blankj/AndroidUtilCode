package com.blankj.utilcode.util;

import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2019/06/18
 *     desc  : utils about view
 * </pre>
 */
public class ViewUtils {

    /**
     * Set the enabled state of this view.
     *
     * @param view    The view.
     * @param enabled True to enabled, false otherwise.
     */
    public static void setViewEnabled(View view, boolean enabled) {
        setViewEnabled(view, enabled, (View) null);
    }

    /**
     * Set the enabled state of this view.
     *
     * @param view     The view.
     * @param enabled  True to enabled, false otherwise.
     * @param excludes The excludes.
     */
    public static void setViewEnabled(View view, boolean enabled, View... excludes) {
        if (view == null) return;
        if (excludes != null) {
            for (View exclude : excludes) {
                if (view == exclude) return;
            }
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                setViewEnabled(viewGroup.getChildAt(i), enabled, excludes);
            }
        }
        view.setEnabled(enabled);
    }

    /**
     * @param runnable The runnable
     */
    public static void runOnUiThread(final Runnable runnable) {
        Utils.runOnUiThread(runnable);
    }

    /**
     * @param runnable    The runnable.
     * @param delayMillis The delay (in milliseconds) until the Runnable will be executed.
     */
    public static void runOnUiThreadDelayed(final Runnable runnable, long delayMillis) {
        Utils.runOnUiThreadDelayed(runnable, delayMillis);
    }

    /**
     * Return whether horizontal layout direction of views are from Right to Left.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isLayoutRtl() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Locale primaryLocale;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                primaryLocale = Utils.getApp().getResources().getConfiguration().getLocales().get(0);
            } else {
                primaryLocale = Utils.getApp().getResources().getConfiguration().locale;
            }
            return TextUtils.getLayoutDirectionFromLocale(primaryLocale) == View.LAYOUT_DIRECTION_RTL;
        }
        return false;
    }

    /**
     * Fix the problem of topping the ScrollView nested ListView/GridView/WebView/RecyclerView.
     *
     * @param view The root view inner of ScrollView.
     */
    public static void fixScrollViewTopping(View view) {
        view.setFocusable(false);
        ViewGroup viewGroup = null;
        if (view instanceof ViewGroup) {
            viewGroup = (ViewGroup) view;
        }
        if (viewGroup == null) {
            return;
        }
        for (int i = 0, n = viewGroup.getChildCount(); i < n; i++) {
            View childAt = viewGroup.getChildAt(i);
            childAt.setFocusable(false);
            if (childAt instanceof ViewGroup) {
                fixScrollViewTopping(childAt);
            }
        }
    }
}