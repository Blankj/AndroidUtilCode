package com.blankj.utilcode.util;

import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.design.widget.Snackbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.lang.ref.WeakReference;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/16
 *     desc  : Snackbar相关工具类
 * </pre>
 */
public final class SnackbarUtils {

    private SnackbarUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static WeakReference<Snackbar> snackbarWeakReference;

    /**
     * 显示短时snackbar
     *
     * @param parent    父视图(CoordinatorLayout或者DecorView)
     * @param text      文本
     * @param textColor 文本颜色
     * @param bgColor   背景色
     */
    public static void showShort(View parent,
                                 CharSequence text,
                                 @ColorInt int textColor,
                                 @ColorInt int bgColor) {
        show(parent,
                text,
                Snackbar.LENGTH_SHORT,
                textColor,
                bgColor,
                null,
                -1,
                null);
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
    public static void showShort(View parent,
                                 CharSequence text,
                                 @ColorInt int textColor,
                                 @ColorInt int bgColor,
                                 CharSequence actionText,
                                 @ColorInt int actionTextColor,
                                 View.OnClickListener listener) {
        show(parent,
                text,
                Snackbar.LENGTH_SHORT,
                textColor,
                bgColor,
                actionText,
                actionTextColor,
                listener);
    }

    /**
     * 显示长时snackbar
     *
     * @param parent    视图(CoordinatorLayout或者DecorView)
     * @param text      文本
     * @param textColor 文本颜色
     * @param bgColor   背景色
     */
    public static void showLong(View parent,
                                CharSequence text,
                                @ColorInt int textColor,
                                @ColorInt int bgColor) {
        show(parent,
                text,
                Snackbar.LENGTH_LONG,
                textColor,
                bgColor,
                null,
                -1,
                null);
    }

    /**
     * 显示长时snackbar
     *
     * @param parent          视图(CoordinatorLayout或者DecorView)
     * @param text            文本
     * @param textColor       文本颜色
     * @param bgColor         背景色
     * @param actionText      事件文本
     * @param actionTextColor 事件文本颜色
     * @param listener        监听器
     */
    public static void showLong(View parent,
                                CharSequence text,
                                @ColorInt int textColor,
                                @ColorInt int bgColor,
                                CharSequence actionText,
                                @ColorInt int actionTextColor,
                                View.OnClickListener listener) {
        show(parent,
                text,
                Snackbar.LENGTH_LONG,
                textColor,
                bgColor,
                actionText,
                actionTextColor,
                listener);
    }

    /**
     * 显示自定义时长snackbar
     *
     * @param parent    父视图(CoordinatorLayout或者DecorView)
     * @param text      文本
     * @param textColor 文本颜色
     * @param bgColor   背景色
     */
    public static void showIndefinite(View parent,
                                      CharSequence text,
                                      @ColorInt int textColor,
                                      @ColorInt int bgColor) {
        show(parent,
                text,
                Snackbar.LENGTH_INDEFINITE,
                textColor,
                bgColor,
                null,
                -1,
                null);
    }

    /**
     * 显示自定义时长snackbar
     *
     * @param parent          父视图(CoordinatorLayout或者DecorView)
     * @param text            文本
     * @param textColor       文本颜色
     * @param bgColor         背景色
     * @param actionText      事件文本
     * @param actionTextColor 事件文本颜色
     * @param listener        监听器
     */
    public static void showIndefinite(View parent,
                                      CharSequence text,
                                      @ColorInt int textColor,
                                      @ColorInt int bgColor,
                                      CharSequence actionText,
                                      @ColorInt int actionTextColor,
                                      View.OnClickListener listener) {
        show(parent,
                text,
                Snackbar.LENGTH_INDEFINITE,
                textColor,
                bgColor,
                actionText,
                actionTextColor,
                listener);
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
    private static void show(View parent,
                             CharSequence text,
                             int duration,
                             @ColorInt int textColor,
                             @ColorInt int bgColor,
                             CharSequence actionText,
                             @ColorInt int actionTextColor,
                             View.OnClickListener listener) {
        SpannableString spannableString = new SpannableString(text);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(textColor);
        spannableString.setSpan(colorSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        snackbarWeakReference = new WeakReference<>(Snackbar.make(parent, spannableString, duration));
        Snackbar snackbar = snackbarWeakReference.get();
        View view = snackbar.getView();
        view.setBackgroundColor(bgColor);
        if (actionText != null && actionText.length() > 0 && listener != null) {
            snackbar.setActionTextColor(actionTextColor);
            snackbar.setAction(actionText, listener);
        }
        snackbar.show();
    }

    /**
     * 为snackbar添加布局
     * <p>在show...Snackbar之后调用</p>
     *
     * @param layoutId 布局文件
     * @param index    位置(the position at which to add the child or -1 to add last)
     */
    public static void addView(@LayoutRes int layoutId, int index) {
        Snackbar snackbar = snackbarWeakReference.get();
        if (snackbar != null) {
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

    /**
     * 为snackbar添加布局
     * <p>在show...Snackbar之后调用</p>
     *
     * @param child  要添加的view
     * @param index  位置(the position at which to add the child or -1 to add last)
     * @param params 布局参数
     */
    public static void addView(View child, int index, ViewGroup.LayoutParams params) {
        Snackbar snackbar = snackbarWeakReference.get();
        if (snackbar != null) {
            View view = snackbar.getView();
            Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) view;
            layout.addView(child, index, params);
        }
    }

    /**
     * 取消snackbar显示
     */
    public static void dismiss() {
        if (snackbarWeakReference != null && snackbarWeakReference.get() != null) {
            snackbarWeakReference.get().dismiss();
            snackbarWeakReference = null;
        }
    }
}
