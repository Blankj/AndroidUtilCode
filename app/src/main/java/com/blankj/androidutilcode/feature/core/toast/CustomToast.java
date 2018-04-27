package com.blankj.androidutilcode.feature.core.toast;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.UtilsApp;
import com.blankj.utilcode.util.ToastUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/08/31
 *     desc  : 自定义 Toast
 * </pre>
 */
public class CustomToast {

    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    public static void showShort(@NonNull final CharSequence text) {
        show(text, Toast.LENGTH_SHORT);
    }

    public static void showShort(@StringRes final int resId) {
        show(resId, Toast.LENGTH_SHORT);
    }

    public static void showShort(@StringRes final int resId, final Object... args) {
        show(resId, Toast.LENGTH_SHORT, args);
    }

    public static void showShort(final String format, final Object... args) {
        show(format, Toast.LENGTH_SHORT, args);
    }

    public static void showLong(@NonNull final CharSequence text) {
        show(text, Toast.LENGTH_LONG);
    }

    public static void showLong(@StringRes final int resId) {
        show(resId, Toast.LENGTH_LONG);
    }

    public static void showLong(@StringRes final int resId, final Object... args) {
        show(resId, Toast.LENGTH_LONG, args);
    }

    public static void showLong(final String format, final Object... args) {
        show(format, Toast.LENGTH_LONG, args);
    }

    private static void show(@StringRes final int resId, final int duration) {
        show(UtilsApp.getInstance().getResources().getString(resId), duration);
    }

    private static void show(@StringRes final int resId, final int duration, final Object... args) {
        show(String.format(UtilsApp.getInstance().getResources().getString(resId), args), duration);
    }

    private static void show(final String format, final int duration, final Object... args) {
        show(String.format(format, args), duration);
    }

    private static void show(final CharSequence text, final int duration) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                TextView toastView;
                if (duration == Toast.LENGTH_SHORT) {
                    toastView = (TextView) ToastUtils.showCustomShort(R.layout.toast_custom);
                } else {
                    toastView = (TextView) ToastUtils.showCustomLong(R.layout.toast_custom);
                }
                toastView.setText(text);
            }
        });
    }

    public static void cancel() {
        ToastUtils.cancel();
    }
}
