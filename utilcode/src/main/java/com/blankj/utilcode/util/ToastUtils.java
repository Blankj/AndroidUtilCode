package com.blankj.utilcode.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.widget.TextViewCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/29
 *     desc  : 吐司相关工具类
 * </pre>
 */
public final class ToastUtils {

    private static final int     DEFAULT_COLOR = 0xFEFFFFFF;
    private static final Handler HANDLER       = new Handler(Looper.getMainLooper());
    private static Toast               sToast;
    private static WeakReference<View> sViewWeakReference;
    private static int gravity         = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
    private static int xOffset         = 0;
    private static int yOffset         = (int) (64 * Utils.getApp().getResources().getDisplayMetrics().density + 0.5);
    private static int backgroundColor = DEFAULT_COLOR;
    private static int bgResource      = -1;
    private static int messageColor    = DEFAULT_COLOR;

    private ToastUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 设置吐司位置
     *
     * @param gravity 位置
     * @param xOffset x偏移
     * @param yOffset y偏移
     */
    public static void setGravity(final int gravity, final int xOffset, final int yOffset) {
        ToastUtils.gravity = gravity;
        ToastUtils.xOffset = xOffset;
        ToastUtils.yOffset = yOffset;
    }

    /**
     * 设置吐司view
     *
     * @param layoutId 视图
     */
    public static View setView(@LayoutRes final int layoutId) {
        LayoutInflater inflate = (LayoutInflater) Utils.getApp().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View toastView = inflate.inflate(layoutId, null);
        sViewWeakReference = new WeakReference<>(toastView);
        return toastView;
    }

    /**
     * 设置吐司view
     *
     * @param view 视图
     */
    public static View setView(final View view) {
        sViewWeakReference = view == null ? null : new WeakReference<>(view);
        return view;
    }

    /**
     * 获取吐司view
     *
     * @return view
     */
    public static View getView() {
        final View view = getViewFromWR();
        if (view != null) {
            return view;
        }
        if (sToast != null) {
            return sToast.getView();
        }
        return null;
    }

    /**
     * 设置背景颜色
     *
     * @param backgroundColor 背景色
     */
    public static void setBgColor(@ColorInt final int backgroundColor) {
        ToastUtils.backgroundColor = backgroundColor;
    }

    /**
     * 设置背景资源
     *
     * @param bgResource 背景资源
     */
    public static void setBgResource(@DrawableRes final int bgResource) {
        ToastUtils.bgResource = bgResource;
    }

    /**
     * 设置消息颜色
     *
     * @param messageColor 颜色
     */
    public static void setMessageColor(@ColorInt final int messageColor) {
        ToastUtils.messageColor = messageColor;
    }

    /**
     * 安全地显示短时吐司
     *
     * @param text 文本
     */
    public static void showShortSafe(@NonNull final CharSequence text) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                show(text, Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 安全地显示短时吐司
     *
     * @param resId 资源Id
     */
    public static void showShortSafe(@StringRes final int resId) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                show(resId, Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 安全地显示短时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public static void showShortSafe(@StringRes final int resId, final Object... args) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                show(resId, Toast.LENGTH_SHORT, args);
            }
        });
    }

    /**
     * 安全地显示短时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showShortSafe(final String format, final Object... args) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                show(format, Toast.LENGTH_SHORT, args);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param text 文本
     */
    public static void showLongSafe(@NonNull final CharSequence text) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                show(text, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param resId 资源Id
     */
    public static void showLongSafe(@StringRes final int resId) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                show(resId, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public static void showLongSafe(@StringRes final int resId, final Object... args) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                show(resId, Toast.LENGTH_LONG, args);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showLongSafe(final String format, final Object... args) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                show(format, Toast.LENGTH_LONG, args);
            }
        });
    }

    /**
     * 显示短时吐司
     *
     * @param text 文本
     */
    public static void showShort(@NonNull final CharSequence text) {
        show(text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示短时吐司
     *
     * @param resId 资源Id
     */
    public static void showShort(@StringRes final int resId) {
        show(resId, Toast.LENGTH_SHORT);
    }

    /**
     * 显示短时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public static void showShort(@StringRes final int resId, final Object... args) {
        show(resId, Toast.LENGTH_SHORT, args);
    }

    /**
     * 显示短时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showShort(final String format, final Object... args) {
        show(format, Toast.LENGTH_SHORT, args);
    }

    /**
     * 显示长时吐司
     *
     * @param text 文本
     */
    public static void showLong(@NonNull final CharSequence text) {
        show(text, Toast.LENGTH_LONG);
    }

    /**
     * 显示长时吐司
     *
     * @param resId 资源Id
     */
    public static void showLong(@StringRes final int resId) {
        show(resId, Toast.LENGTH_LONG);
    }

    /**
     * 显示长时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public static void showLong(@StringRes final int resId, final Object... args) {
        show(resId, Toast.LENGTH_LONG, args);
    }

    /**
     * 显示长时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showLong(final String format, final Object... args) {
        show(format, Toast.LENGTH_LONG, args);
    }

    /**
     * 安全地显示短时自定义吐司
     */
    public static View showCustomShortSafe(@LayoutRes final int layoutId) {
        final View view = setView(layoutId);
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                show("", Toast.LENGTH_SHORT);
            }
        });
        return view;
    }

    /**
     * 安全地显示长时自定义吐司
     */
    public static View showCustomLongSafe(@LayoutRes final int layoutId) {
        final View view = setView(layoutId);
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                show("", Toast.LENGTH_LONG);
            }
        });
        return view;
    }

    /**
     * 显示短时自定义吐司
     */
    public static View showCustomShort(@LayoutRes final int layoutId) {
        final View view = setView(layoutId);
        show("", Toast.LENGTH_SHORT);
        return view;
    }

    /**
     * 显示长时自定义吐司
     */
    public static View showCustomLong(@LayoutRes final int layoutId) {
        final View view = setView(layoutId);
        show("", Toast.LENGTH_LONG);
        return view;
    }

    /**
     * 安全地显示短时自定义吐司
     */
    public static View showCustomShortSafe(@NonNull final View view) {
        setView(view);
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                show("", Toast.LENGTH_SHORT);
            }
        });
        return view;
    }

    /**
     * 安全地显示长时自定义吐司
     */
    public static View showCustomLongSafe(@NonNull final View view) {
        setView(view);
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                setView(view);
                show("", Toast.LENGTH_LONG);
            }
        });
        return view;
    }

    /**
     * 显示短时自定义吐司
     */
    public static View showCustomShort(@NonNull final View view) {
        setView(view);
        show("", Toast.LENGTH_SHORT);
        return view;
    }

    /**
     * 显示长时自定义吐司
     */
    public static View showCustomLong(@NonNull final View view) {
        setView(view);
        show("", Toast.LENGTH_LONG);
        return view;
    }

    /**
     * 显示吐司
     *
     * @param resId    资源Id
     * @param duration 显示时长
     */
    private static void show(@StringRes final int resId, final int duration) {
        show(Utils.getApp().getResources().getText(resId).toString(), duration);
    }

    /**
     * 显示吐司
     *
     * @param resId    资源Id
     * @param duration 显示时长
     * @param args     参数
     */
    private static void show(@StringRes final int resId, final int duration, final Object... args) {
        show(String.format(Utils.getApp().getResources().getString(resId), args), duration);
    }

    /**
     * 显示吐司
     *
     * @param format   格式
     * @param duration 显示时长
     * @param args     参数
     */
    private static void show(final String format, final int duration, final Object... args) {
        show(String.format(format, args), duration);
    }

    /**
     * 显示吐司
     *
     * @param text     文本
     * @param duration 显示时长
     */
    private static void show(final CharSequence text, final int duration) {
        cancel();
        final View view = getViewFromWR();
        if (view != null) {
            sToast = new Toast(Utils.getApp());
            sToast.setView(view);
            sToast.setDuration(duration);
        } else {
            sToast = Toast.makeText(Utils.getApp(), text, duration);
            // solve the font of toast
            TextView tvMessage = (TextView) sToast.getView().findViewById(android.R.id.message);
            TextViewCompat.setTextAppearance(tvMessage, android.R.style.TextAppearance);
            tvMessage.setTextColor(messageColor);
        }
        View toastView = sToast.getView();
        if (bgResource != -1) {
            toastView.setBackgroundResource(bgResource);
        } else if (backgroundColor != DEFAULT_COLOR) {
            toastView.setBackgroundColor(backgroundColor);
        }
        sToast.setGravity(gravity, xOffset, yOffset);
        sToast.show();
    }

    /**
     * 取消吐司显示
     */
    public static void cancel() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }

    /**
     * 如果自定义View的Context为Activity级别，需要调用releaseView，否则会内存泄露
     */
    static void releaseView() {
        if (sToast != null) {
            sToast.setView(null);
        }
    }

    private static View getViewFromWR() {
        if (sViewWeakReference != null) {
            final View view = sViewWeakReference.get();
            if (view != null) {
                return view;
            }
        }
        return null;
    }
}