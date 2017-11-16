package com.blankj.utilcode.util;

import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
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

    public static final int LENGTH_INDEFINITE = -2;
    public static final int LENGTH_SHORT      = -1;
    public static final int LENGTH_LONG       = 0;

    @IntDef({LENGTH_INDEFINITE, LENGTH_SHORT, LENGTH_LONG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

    private static final int COLOR_DEFAULT = 0xFEFFFFFF;
    private static final int COLOR_SUCCESS = 0xFF2BB600;
    private static final int COLOR_WARNING = 0xFFFFC100;
    private static final int COLOR_ERROR   = 0xFFFF0000;
    private static final int COLOR_MESSAGE = 0xFFFFFFFF;

    private static WeakReference<Snackbar> snackbarWeakReference;

    private View                 parent;
    private CharSequence         message;
    private int                  messageColor;
    private int                  bgColor;
    private int                  bgResource;
    private int                  duration;
    private CharSequence         actionText;
    private int                  actionTextColor;
    private View.OnClickListener actionListener;
    private int                  bottomMargin;

    private SnackbarUtils(final View parent) {
        setDefault();
        this.parent = parent;
    }

    private void setDefault() {
        message = "";
        messageColor = COLOR_DEFAULT;
        bgColor = COLOR_DEFAULT;
        bgResource = -1;
        duration = LENGTH_SHORT;
        actionText = "";
        actionTextColor = COLOR_DEFAULT;
        bottomMargin = 0;
    }

    /**
     * 设置snackbar依赖view
     *
     * @param parent 依赖view
     * @return {@link SnackbarUtils}
     */
    public static SnackbarUtils with(@NonNull final View parent) {
        return new SnackbarUtils(parent);
    }

    /**
     * 设置消息
     *
     * @param msg 消息
     * @return {@link SnackbarUtils}
     */
    public SnackbarUtils setMessage(@NonNull final CharSequence msg) {
        this.message = msg;
        return this;
    }

    /**
     * 设置消息颜色
     *
     * @param color 颜色
     * @return {@link SnackbarUtils}
     */
    public SnackbarUtils setMessageColor(@ColorInt final int color) {
        this.messageColor = color;
        return this;
    }

    /**
     * 设置背景色
     *
     * @param color 背景色
     * @return {@link SnackbarUtils}
     */
    public SnackbarUtils setBgColor(@ColorInt final int color) {
        this.bgColor = color;
        return this;
    }

    /**
     * 设置背景资源
     *
     * @param bgResource 背景资源
     * @return {@link SnackbarUtils}
     */
    public SnackbarUtils setBgResource(@DrawableRes final int bgResource) {
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
    public SnackbarUtils setDuration(@Duration final int duration) {
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
    public SnackbarUtils setAction(@NonNull final CharSequence text, @NonNull final View.OnClickListener listener) {
        return setAction(text, COLOR_DEFAULT, listener);
    }

    /**
     * 设置行为
     *
     * @param text     文本
     * @param color    文本颜色
     * @param listener 事件
     * @return {@link SnackbarUtils}
     */

    public SnackbarUtils setAction(@NonNull final CharSequence text, @ColorInt final int color, @NonNull final View.OnClickListener listener) {
        this.actionText = text;
        this.actionTextColor = color;
        this.actionListener = listener;
        return this;
    }

    /**
     * 设置底边距
     *
     * @param bottomMargin 底边距
     */
    public SnackbarUtils setBottomMargin(@IntRange(from = 1) final int bottomMargin) {
        this.bottomMargin = bottomMargin;
        return this;
    }

    /**
     * 显示snackbar
     */
    public void show() {
        final View view = parent;
        if (view == null) return;
        if (messageColor != COLOR_DEFAULT) {
            SpannableString spannableString = new SpannableString(message);
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(messageColor);
            spannableString.setSpan(colorSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            snackbarWeakReference = new WeakReference<>(Snackbar.make(view, spannableString, duration));
        } else {
            snackbarWeakReference = new WeakReference<>(Snackbar.make(view, message, duration));
        }
        final Snackbar snackbar = snackbarWeakReference.get();
        final View snackbarView = snackbar.getView();
        if (bgResource != -1) {
            snackbarView.setBackgroundResource(bgResource);
        } else if (bgColor != COLOR_DEFAULT) {
            snackbarView.setBackgroundColor(bgColor);
        }
        if (bottomMargin != 0) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) snackbarView.getLayoutParams();
            params.bottomMargin = bottomMargin;
        }
        if (actionText.length() > 0 && actionListener != null) {
            if (actionTextColor != COLOR_DEFAULT) {
                snackbar.setActionTextColor(actionTextColor);
            }
            snackbar.setAction(actionText, actionListener);
        }
        snackbar.show();
    }

    /**
     * 显示预设成功的snackbar
     */
    public void showSuccess() {
        bgColor = COLOR_SUCCESS;
        messageColor = COLOR_MESSAGE;
        actionTextColor = COLOR_MESSAGE;
        show();
    }

    /**
     * 显示预设警告的snackbar
     */
    public void showWarning() {
        bgColor = COLOR_WARNING;
        messageColor = COLOR_MESSAGE;
        actionTextColor = COLOR_MESSAGE;
        show();
    }

    /**
     * 显示预设错误的snackbar
     */
    public void showError() {
        bgColor = COLOR_ERROR;
        messageColor = COLOR_MESSAGE;
        actionTextColor = COLOR_MESSAGE;
        show();
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
     * @param params   布局参数
     */
    public static void addView(@LayoutRes final int layoutId, @NonNull final ViewGroup.LayoutParams params) {
        final View view = getView();
        if (view != null) {
            view.setPadding(0, 0, 0, 0);
            Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) view;
            View child = LayoutInflater.from(view.getContext()).inflate(layoutId, null);
            layout.addView(child, -1, params);
        }
    }

    /**
     * 添加snackbar视图
     * <p>在{@link #show()}之后调用</p>
     *
     * @param child  要添加的view
     * @param params 布局参数
     */
    public static void addView(@NonNull final View child, @NonNull final ViewGroup.LayoutParams params) {
        final View view = getView();
        if (view != null) {
            view.setPadding(0, 0, 0, 0);
            Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) view;
            layout.addView(child, params);
        }
    }
}
