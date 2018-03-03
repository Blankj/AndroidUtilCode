package com.blankj.utilcode.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/29
 *     desc  : utils about toast
 * </pre>
 */
public final class ToastUtils {

    private static final int     COLOR_DEFAULT = 0xFEFFFFFF;
    private static final Handler HANDLER       = new Handler(Looper.getMainLooper());

    private static Toast sToast;
    private static int sGravity    = -1;
    private static int sXOffset    = -1;
    private static int sYOffset    = -1;
    private static int sBgColor    = COLOR_DEFAULT;
    private static int sBgResource = -1;
    private static int sMsgColor   = COLOR_DEFAULT;

    private ToastUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Set the gravity.
     *
     * @param gravity The gravity.
     * @param xOffset X-axis offset, in pixel.
     * @param yOffset Y-axis offset, in pixel.
     */
    public static void setGravity(final int gravity, final int xOffset, final int yOffset) {
        sGravity = gravity;
        sXOffset = xOffset;
        sYOffset = yOffset;
    }

    /**
     * Set the color of background.
     *
     * @param backgroundColor The color of background.
     */
    public static void setBgColor(@ColorInt final int backgroundColor) {
        sBgColor = backgroundColor;
    }

    /**
     * Set the resource of background.
     *
     * @param bgResource The resource of background.
     */
    public static void setBgResource(@DrawableRes final int bgResource) {
        sBgResource = bgResource;
    }

    /**
     * Set the color of message.
     *
     * @param msgColor The color of message.
     */
    public static void setMsgColor(@ColorInt final int msgColor) {
        sMsgColor = msgColor;
    }

    /**
     * Show the toast for a short period of time.
     *
     * @param text The text.
     */
    public static void showShort(@NonNull final CharSequence text) {
        show(text, Toast.LENGTH_SHORT);
    }

    /**
     * Show the toast for a short period of time.
     *
     * @param resId The resource id for text.
     */
    public static void showShort(@StringRes final int resId) {
        show(resId, Toast.LENGTH_SHORT);
    }

    /**
     * Show the toast for a short period of time.
     *
     * @param resId The resource id for text.
     * @param args  The args.
     */
    public static void showShort(@StringRes final int resId, final Object... args) {
        if (args != null && args.length == 0) {
            show(resId, Toast.LENGTH_SHORT);
        } else {
            show(resId, Toast.LENGTH_SHORT, args);
        }
    }

    /**
     * Show the toast for a short period of time.
     *
     * @param format The format.
     * @param args   The args.
     */
    public static void showShort(final String format, final Object... args) {
        if (args != null && args.length == 0) {
            show(format, Toast.LENGTH_SHORT);
        } else {
            show(format, Toast.LENGTH_SHORT, args);
        }
    }

    /**
     * Show the toast for a long period of time.
     *
     * @param text The text.
     */
    public static void showLong(@NonNull final CharSequence text) {
        show(text, Toast.LENGTH_LONG);
    }

    /**
     * Show the toast for a long period of time.
     *
     * @param resId The resource id for text.
     */
    public static void showLong(@StringRes final int resId) {
        show(resId, Toast.LENGTH_LONG);
    }

    /**
     * Show the toast for a long period of time.
     *
     * @param resId The resource id for text.
     * @param args  The args.
     */
    public static void showLong(@StringRes final int resId, final Object... args) {
        if (args != null && args.length == 0) {
            show(resId, Toast.LENGTH_SHORT);
        } else {
            show(resId, Toast.LENGTH_LONG, args);
        }
    }

    /**
     * Show the toast for a long period of time.
     *
     * @param format The format.
     * @param args   The args.
     */
    public static void showLong(final String format, final Object... args) {
        if (args != null && args.length == 0) {
            show(format, Toast.LENGTH_SHORT);
        } else {
            show(format, Toast.LENGTH_LONG, args);
        }
    }

    /**
     * Show custom toast for a short period of time.
     */
    public static View showCustomShort(@LayoutRes final int layoutId) {
        final View view = getView(layoutId);
        show(view, Toast.LENGTH_SHORT);
        return view;
    }

    /**
     * Show custom toast for a long period of time.
     */
    public static View showCustomLong(@LayoutRes final int layoutId) {
        final View view = getView(layoutId);
        show(view, Toast.LENGTH_LONG);
        return view;
    }

    /**
     * Cancel the toast.
     */
    public static void cancel() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }

    private static void show(@StringRes final int resId, final int duration) {
        show(Utils.getApp().getResources().getText(resId).toString(), duration);
    }

    private static void show(@StringRes final int resId, final int duration, final Object... args) {
        show(String.format(Utils.getApp().getResources().getString(resId), args), duration);
    }

    private static void show(final String format, final int duration, final Object... args) {
        show(String.format(format, args), duration);
    }

    private static void show(final CharSequence text, final int duration) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                cancel();
                sToast = Toast.makeText(Utils.getApp(), text, duration);
                TextView tvMessage = sToast.getView().findViewById(android.R.id.message);
                int msgColor = tvMessage.getCurrentTextColor();
                //it solve the font of toast
                TextViewCompat.setTextAppearance(tvMessage, android.R.style.TextAppearance);
                if (sMsgColor != COLOR_DEFAULT) {
                    tvMessage.setTextColor(sMsgColor);
                } else {
                    tvMessage.setTextColor(msgColor);
                }
                if (sGravity != -1 || sXOffset != -1 || sYOffset != -1) {
                    sToast.setGravity(sGravity, sXOffset, sYOffset);
                }
                setBg(tvMessage);
                sToast.show();
            }
        });
    }

    private static void show(final View view, final int duration) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                cancel();
                sToast = new Toast(Utils.getApp());
                sToast.setView(view);
                sToast.setDuration(duration);
                if (sGravity != -1 || sXOffset != -1 || sYOffset != -1) {
                    sToast.setGravity(sGravity, sXOffset, sYOffset);
                }
                setBg();
                sToast.show();
            }
        });
    }

    private static void setBg() {
        View toastView = sToast.getView();
        if (sBgResource != -1) {
            toastView.setBackgroundResource(sBgResource);
        } else if (sBgColor != COLOR_DEFAULT) {
            Drawable background = toastView.getBackground();
            if (background != null) {
                background.setColorFilter(
                        new PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN)
                );
            } else {
                ViewCompat.setBackground(toastView, new ColorDrawable(sBgColor));
            }
        }
    }

    private static void setBg(final TextView tvMsg) {
        View toastView = sToast.getView();
        if (sBgResource != -1) {
            toastView.setBackgroundResource(sBgResource);
            tvMsg.setBackgroundColor(Color.TRANSPARENT);
        } else if (sBgColor != COLOR_DEFAULT) {
            Drawable tvBg = toastView.getBackground();
            Drawable msgBg = tvMsg.getBackground();
            if (tvBg != null && msgBg != null) {
                tvBg.setColorFilter(new PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN));
                tvMsg.setBackgroundColor(Color.TRANSPARENT);
            } else if (tvBg != null) {
                tvBg.setColorFilter(new PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN));
            } else if (msgBg != null) {
                msgBg.setColorFilter(new PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN));
            } else {
                toastView.setBackgroundColor(sBgColor);
            }
        }
    }

    private static View getView(@LayoutRes final int layoutId) {
        LayoutInflater inflate =
                (LayoutInflater) Utils.getApp().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflate != null ? inflate.inflate(layoutId, null) : null;
    }
}
