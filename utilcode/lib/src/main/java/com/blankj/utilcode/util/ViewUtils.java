package com.blankj.utilcode.util;

import android.view.View;
import android.view.ViewGroup;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/06/18
 *     desc  : utils about view
 * </pre>
 */
public class ViewUtils {

    public static void setViewEnabled(View view, boolean enabled) {
        setViewEnabled(view, enabled, (View) null);
    }

    public static void setViewEnabled(View view, boolean enabled, View... excludes) {
        if (view == null) return;
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                setViewEnabled(viewGroup.getChildAt(i), enabled, excludes);
            }
        }
        if (excludes != null) {
            for (View exclude : excludes) {
                if (view == exclude) return;
            }
        }
        view.setEnabled(enabled);
    }
}