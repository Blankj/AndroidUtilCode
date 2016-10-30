package com.blankj.utilcode.utils;

import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.R;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/16
 *     desc  : SnackBar相关工具类
 * </pre>
 */

public class SnackbarUtils {

    private static Snackbar snackbar;

    /**
     * 显示短时snackbar
     *
     * @param parent    父视图(CoordinatorLayout或者DecorView)
     * @param text      文本
     * @param textColor 文本颜色
     * @param bgColor   背景色
     */
    public static void showShortSnackbar(View parent, CharSequence text, int textColor, int bgColor) {
        showSnackbar(parent, text, Snackbar.LENGTH_SHORT, textColor, bgColor, null, -1, null);
    }

    /**
     * 显示短时snackbar
     *
     * @param parent          父视图(CoordinatorLayout或者DecorView)
     * @param text            文本
     * @param textColor       文本颜色
     * @param bgColor         背景色
     * @param actionText      事件文本
     * @param actionTextColor 事件文本颜色
     * @param listener        监听器
     */
    public static void showShortSnackbar(View parent, CharSequence text, int textColor, int bgColor,
                                         CharSequence actionText, int actionTextColor, View.OnClickListener listener) {
        showSnackbar(parent, text, Snackbar.LENGTH_SHORT, textColor, bgColor,
                actionText, actionTextColor, listener);
    }

    /**
     * 显示长时snackbar
     *
     * @param parent    视图(CoordinatorLayout或者DecorView)
     * @param text      文本
     * @param textColor 文本颜色
     * @param bgColor   背景色
     */
    public static void showLongSnackbar(View parent, CharSequence text, int textColor, int bgColor) {
        showSnackbar(parent, text, Snackbar.LENGTH_LONG, textColor, bgColor, null, -1, null);
    }

    /**
     * 显示长时snackbar
     *
     * @param parent    视图(CoordinatorLayout或者DecorView)
     * @param text      文本
     * @param textColor 文本颜色
     * @param bgColor   背景色
     */
    public static void showLongSnackbar(View parent, CharSequence text, int textColor, int bgColor,
                                        CharSequence actionText, int actionTextColor, View.OnClickListener listener) {
        showSnackbar(parent, text, Snackbar.LENGTH_LONG, textColor, bgColor,
                actionText, actionTextColor, listener);
    }

    /**
     * 显示自定义时长snackbar
     *
     * @param parent    父视图(CoordinatorLayout或者DecorView)
     * @param text      文本
     * @param duration  自定义时长
     * @param textColor 文本颜色
     * @param bgColor   背景色
     */
    public static void showIndefiniteSnackbar(View parent, CharSequence text, int duration, int textColor, int bgColor) {
        showSnackbar(parent, text, duration, textColor, bgColor, null, -1, null);
    }

    /**
     * 显示自定义时长snackbar
     *
     * @param parent          父视图(CoordinatorLayout或者DecorView)
     * @param text            文本
     * @param duration        自定义时长
     * @param textColor       文本颜色
     * @param bgColor         背景色
     * @param actionText      事件文本
     * @param actionTextColor 事件文本颜色
     * @param listener        监听器
     */
    public static void showIndefiniteSnackbar(View parent, CharSequence text, int duration, int textColor, int bgColor,
                                              CharSequence actionText, int actionTextColor, View.OnClickListener listener) {
        showSnackbar(parent, text, duration, textColor, bgColor,
                actionText, actionTextColor, listener);
    }

    /**
     * 设置snackbar文字和背景颜色
     *
     * @param parent          父视图(CoordinatorLayout或者DecorView)
     * @param text            文本
     * @param duration        显示时长
     * @param textColor       文本颜色
     * @param bgColor         背景色
     * @param actionText      事件文本
     * @param actionTextColor 事件文本颜色
     * @param listener        监听器
     */
    private static void showSnackbar(View parent, CharSequence text, int duration, int textColor, int bgColor,
                                     CharSequence actionText, int actionTextColor, View.OnClickListener listener) {
        switch (duration) {
            default:
            case Snackbar.LENGTH_SHORT:
            case Snackbar.LENGTH_LONG:
                snackbar = Snackbar.make(parent, text, duration);
                break;
            case Snackbar.LENGTH_INDEFINITE:
                snackbar = Snackbar.make(parent, text, Snackbar.LENGTH_INDEFINITE).setDuration(duration);
        }
        View view = snackbar.getView();
        ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(textColor);
        view.setBackgroundColor(bgColor);
        if (actionText != null && actionText.length() > 0 && listener != null) {
            snackbar.setActionTextColor(actionTextColor);
            snackbar.setAction(actionText, listener);
        }
        snackbar.show();
    }

    /**
     * 为SnackBar添加布局
     * <p>在show...Snackbar之后调用</p>
     *
     * @param layoutId 布局文件
     * @param index    位置(the position at which to add the child or -1 to add last)
     */
    public static void addView(int layoutId, int index) {
        View view = snackbar.getView();
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) view;
        View child = LayoutInflater.from(view.getContext()).inflate(layoutId, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        layout.addView(child, index, params);
    }
}