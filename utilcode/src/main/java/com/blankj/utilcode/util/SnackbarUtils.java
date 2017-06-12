package com.blankj.utilcode.util;

import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
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

    private static final int DEFAULT_COLOR = 0x12000000;

    public static final int LENGTH_INDEFINITE = BaseTransientBottomBar.LENGTH_INDEFINITE;

    public static final int LENGTH_SHORT = BaseTransientBottomBar.LENGTH_SHORT;

    public static final int LENGTH_LONG = BaseTransientBottomBar.LENGTH_LONG;

    @IntDef({LENGTH_INDEFINITE, LENGTH_SHORT, LENGTH_LONG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

    private static WeakReference<Snackbar> snackbarWeakReference;

    private WeakReference<View> parent;

    private CharSequence         message;
    private int                  messageColor;
    private int                  bgColor;
    private int                  bgResource;
    private int                  duration;
    private CharSequence         actionText;
    private int                  actionTextColor;
    private View.OnClickListener actionListener;

    private SnackbarUtils(View parent) {
        setDefault();
        this.parent = new WeakReference<>(parent);
    }

    private void setDefault() {
        message = "";
        messageColor = DEFAULT_COLOR;
        bgColor = DEFAULT_COLOR;
        bgResource = -1;
        duration = LENGTH_SHORT;
        actionText = "";
        actionTextColor = DEFAULT_COLOR;
    }

    /**
     * 设置snackbar依赖view
     *
     * @param parent 依赖view
     * @return {@link SnackbarUtils}
     */
    public static SnackbarUtils with(@Nullable View parent) {
        return new SnackbarUtils(parent);
    }

    /**
     * 设置消息
     *
     * @param msg 消息
     * @return {@link SnackbarUtils}
     */
    public SnackbarUtils setMessage(@Nullable CharSequence msg) {
        this.message = msg;
        return this;
    }

    /**
     * 设置消息颜色
     *
     * @param color 颜色
     * @return {@link SnackbarUtils}
     */
    public SnackbarUtils setMessageColor(@ColorInt int color) {
        this.messageColor = color;
        return this;
    }

    /**
     * 设置背景色
     *
     * @param color 背景色
     * @return {@link SnackbarUtils}
     */
    public SnackbarUtils setBgColor(@ColorInt int color) {
        this.bgColor = color;
        return this;
    }

    /**
     * 设置背景资源
     *
     * @param bgResource 背景资源
     * @return {@link SnackbarUtils}
     */
    public SnackbarUtils setBgResource(@DrawableRes int bgResource) {
        this.bgResource = bgResource;
        return this;
    }

    /**
     * 设置显示时长
     *
     * @param duration 时长
     *                 <ul>
     *                 <li>{@link Duration#LENGTH_INDEFINITE}永久</li>
     *                 <li>{@link Duration#LENGTH_SHORT}短时</li>
     *                 <li>{@link Duration#LENGTH_LONG}长时</li>
     *                 </ul>
     * @return {@link SnackbarUtils}
     */
    public SnackbarUtils setDuration(@Duration int duration) {
        this.duration = duration;
        return this;
    }

    /**
     * 设置行为
     *
     * @param text     文本
     * @param listener 事件
     * @return {@link SnackbarUtils}
     */
    public SnackbarUtils setAction(@Nullable CharSequence text, @Nullable final View.OnClickListener listener) {
        return setAction(text, DEFAULT_COLOR, listener);
    }

    /**
     * 设置行为
     *
     * @param text     文本
     * @param color    文本颜色
     * @param listener 事件
     * @return {@link SnackbarUtils}
     */

    public SnackbarUtils setAction(@Nullable CharSequence text, @ColorInt int color, @Nullable final View.OnClickListener listener) {
        this.actionText = text;
        this.actionTextColor = color;
        this.actionListener = listener;
        return this;
    }

    /**
     * 显示snackbar
     */
    public void show() {
        final View view = parent.get();
        if (view == null) return;
        if (messageColor != DEFAULT_COLOR) {
            SpannableString spannableString = new SpannableString(message);
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(messageColor);
            spannableString.setSpan(colorSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            snackbarWeakReference = new WeakReference<>(Snackbar.make(view, spannableString, duration));
        } else {
            snackbarWeakReference = new WeakReference<>(Snackbar.make(view, message, duration));
        }
        final Snackbar snackbar = snackbarWeakReference.get();
        final View snackbarView = snackbar.getView();
        if (bgColor != DEFAULT_COLOR) {
            snackbarView.setBackgroundColor(bgColor);
        } else if (bgResource != -1) {
            snackbarView.setBackgroundResource(bgResource);
        }
        if (actionText.length() > 0 && actionListener != null) {
            if (actionTextColor != DEFAULT_COLOR) {
                snackbar.setActionTextColor(actionTextColor);
            }
            snackbar.setAction(actionText, actionListener);
        }
        snackbar.show();
    }

    /**
     * 消失snackbar
     */
    public static void dismiss() {
        if (snackbarWeakReference != null && snackbarWeakReference.get() != null) {
            snackbarWeakReference.get().dismiss();
            snackbarWeakReference = null;
        }
    }

    /**
     * 获取snackbar视图
     *
     * @return snackbar视图
     */
    public static View getView() {
        Snackbar snackbar = snackbarWeakReference.get();
        if (snackbar == null) return null;
        return snackbar.getView();
    }

    /**
     * 添加snackbar视图
     * <p>在{@link #show()}之后调用</p>
     *
     * @param layoutId 布局文件
     * @param index    位置(the position at which to add the child or -1 to add last)
     * @param params   布局参数
     */
    public static void addView(@LayoutRes int layoutId, int index, ViewGroup.LayoutParams params) {
        final View view = getView();
        if (view != null) {
            Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) view;
            View child = LayoutInflater.from(view.getContext()).inflate(layoutId, null);
            layout.addView(child, index, params);
        }
    }

    /**
     * 添加snackbar视图
     * <p>在{@link #show()}之后调用</p>
     *
     * @param child  要添加的view
     * @param index  位置(the position at which to add the child or -1 to add last)
     * @param params 布局参数
     */
    public static void addView(View child, int index, ViewGroup.LayoutParams params) {
        final View view = getView();
        if (view != null) {
            Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) view;
            layout.addView(child, index, params);
        }
    }
}
