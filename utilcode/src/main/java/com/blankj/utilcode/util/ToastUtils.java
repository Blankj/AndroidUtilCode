package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/29
 *     desc  : utils about sToast
 * </pre>
 */
public final class ToastUtils {

    private static final int     COLOR_DEFAULT = 0xFEFFFFFF;
    private static final Handler HANDLER       = new Handler(Looper.getMainLooper());
    private static final String  NULL          = "null";

    private static IToast sToast;
    private static int sGravity     = -1;
    private static int sXOffset     = -1;
    private static int sYOffset     = -1;
    private static int sBgColor     = COLOR_DEFAULT;
    private static int sBgResource  = -1;
    private static int sMsgColor    = COLOR_DEFAULT;
    private static int sMsgTextSize = -1;

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
     * Set the text size of message.
     *
     * @param textSize The text size of message.
     */
    public static void setMsgTextSize(final int textSize) {
        sMsgTextSize = textSize;
    }

    /**
     * Show the sToast for a short period of time.
     *
     * @param text The text.
     */
    public static void showShort(final CharSequence text) {
        show(text == null ? NULL : text, Toast.LENGTH_SHORT);
    }

    /**
     * Show the sToast for a short period of time.
     *
     * @param resId The resource id for text.
     */
    public static void showShort(@StringRes final int resId) {
        show(resId, Toast.LENGTH_SHORT);
    }

    /**
     * Show the sToast for a short period of time.
     *
     * @param resId The resource id for text.
     * @param args  The args.
     */
    public static void showShort(@StringRes final int resId, final Object... args) {
        show(resId, Toast.LENGTH_SHORT, args);
    }

    /**
     * Show the sToast for a short period of time.
     *
     * @param format The format.
     * @param args   The args.
     */
    public static void showShort(final String format, final Object... args) {
        show(format, Toast.LENGTH_SHORT, args);
    }

    /**
     * Show the sToast for a long period of time.
     *
     * @param text The text.
     */
    public static void showLong(final CharSequence text) {
        show(text == null ? NULL : text, Toast.LENGTH_LONG);
    }

    /**
     * Show the sToast for a long period of time.
     *
     * @param resId The resource id for text.
     */
    public static void showLong(@StringRes final int resId) {
        show(resId, Toast.LENGTH_LONG);
    }

    /**
     * Show the sToast for a long period of time.
     *
     * @param resId The resource id for text.
     * @param args  The args.
     */
    public static void showLong(@StringRes final int resId, final Object... args) {
        show(resId, Toast.LENGTH_LONG, args);
    }

    /**
     * Show the sToast for a long period of time.
     *
     * @param format The format.
     * @param args   The args.
     */
    public static void showLong(final String format, final Object... args) {
        show(format, Toast.LENGTH_LONG, args);
    }

    /**
     * Show custom sToast for a short period of time.
     *
     * @param layoutId ID for an XML layout resource to load.
     */
    public static View showCustomShort(@LayoutRes final int layoutId) {
        final View view = getView(layoutId);
        show(view, Toast.LENGTH_SHORT);
        return view;
    }

    /**
     * Show custom sToast for a long period of time.
     *
     * @param layoutId ID for an XML layout resource to load.
     */
    public static View showCustomLong(@LayoutRes final int layoutId) {
        final View view = getView(layoutId);
        show(view, Toast.LENGTH_LONG);
        return view;
    }

    /**
     * Cancel the sToast.
     */
    public static void cancel() {
        if (sToast != null) {
            sToast.cancel();
        }
    }

    private static void show(@StringRes final int resId, final int duration) {
        show(Utils.getApp().getResources().getText(resId).toString(), duration);
    }

    private static void show(@StringRes final int resId, final int duration, final Object... args) {
        show(String.format(Utils.getApp().getResources().getString(resId), args), duration);
    }

    private static void show(final String format, final int duration, final Object... args) {
        String text;
        if (format == null) {
            text = NULL;
        } else {
            text = String.format(format, args);
            if (text == null) {
                text = NULL;
            }
        }
        show(text, duration);
    }

    private static void show(final CharSequence text, final int duration) {
        HANDLER.post(new Runnable() {
            @SuppressLint("ShowToast")
            @Override
            public void run() {
                cancel();
                if (Utils.isAdaptScreen()) {
                    Utils.cancelAdaptScreen();
                    sToast = ToastFactory.makeToast(Utils.getApp(), text, duration);
                    Utils.restoreAdaptScreen();
                } else {
                    sToast = ToastFactory.makeToast(Utils.getApp(), text, duration);
                }
                final TextView tvMessage = sToast.getView().findViewById(android.R.id.message);
                if (sMsgColor != COLOR_DEFAULT) {
                    tvMessage.setTextColor(sMsgColor);
                }
                if (sMsgTextSize != -1) {
                    tvMessage.setTextSize(sMsgTextSize);
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
                sToast = ToastFactory.newToast(Utils.getApp());
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
        if (sBgResource != -1) {
            final View toastView = sToast.getView();
            toastView.setBackgroundResource(sBgResource);
        } else if (sBgColor != COLOR_DEFAULT) {
            final View toastView = sToast.getView();
            Drawable background = toastView.getBackground();
            if (background != null) {
                background.setColorFilter(
                        new PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN)
                );
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    toastView.setBackground(new ColorDrawable(sBgColor));
                } else {
                    toastView.setBackgroundDrawable(new ColorDrawable(sBgColor));
                }
            }
        }
    }

    private static void setBg(final TextView tvMsg) {
        if (sBgResource != -1) {
            final View toastView = sToast.getView();
            toastView.setBackgroundResource(sBgResource);
            tvMsg.setBackgroundColor(Color.TRANSPARENT);
        } else if (sBgColor != COLOR_DEFAULT) {
            final View toastView = sToast.getView();
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
        //noinspection ConstantConditions
        return inflate.inflate(layoutId, null);
    }

    static class ToastFactory {

        static IToast makeToast(Context context, CharSequence text, int duration) {
            if (NotificationManagerCompat.from(context).areNotificationsEnabled()) {
                return new SystemToast(makeNormalToast(context, text, duration));
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
                return new ToastWithoutNotification(makeNormalToast(context, text, duration));
            }
            Log.e("ToastUtils", "Toast is GG. In fact, next step is useless.");
            return new SystemToast(makeNormalToast(context, text, duration));
        }

        static IToast newToast(Context context) {
            if (NotificationManagerCompat.from(context).areNotificationsEnabled()) {
                return new SystemToast(new Toast(context));
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
                return new ToastWithoutNotification(new Toast(context));
            }
            Log.e("ToastUtils", "Toast is GG. In fact, next step is useless.");
            return new SystemToast(new Toast(context));
        }

        private static Toast makeNormalToast(Context context, CharSequence text, int duration) {
            if ("Xiaomi".equals(Build.MANUFACTURER)) {
                Toast toast = new Toast(context);
                int identifier = Resources.getSystem()
                        .getIdentifier("transient_notification", "layout", "android");
                View view = getView(identifier);
                toast.setView(view);
                toast.setDuration(duration);
                TextView tv = view.findViewById(android.R.id.message);
                tv.setText(text);
                return toast;
            }
            return Toast.makeText(context, text, duration);
        }
    }

    static class SystemToast implements IToast {

        Toast mToast;

        private static Field sField_mTN;
        private static Field sField_TN_Handler;

        SystemToast(@NonNull Toast toast) {
            mToast = toast;
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
                try {
                    //noinspection JavaReflectionMemberAccess
                    sField_mTN = Toast.class.getDeclaredField("mTN");
                    sField_mTN.setAccessible(true);
                    Object mTN = sField_mTN.get(toast);
                    sField_TN_Handler = sField_mTN.getType().getDeclaredField("mHandler");
                    sField_TN_Handler.setAccessible(true);
                    Handler tnHandler = (Handler) sField_TN_Handler.get(mTN);
                    sField_TN_Handler.set(mTN, new SafeHandler(tnHandler));
                } catch (Exception ignored) { /**/ }
            }
        }

        @Override
        public void show() {
            mToast.show();
        }

        @Override
        public void cancel() {
            mToast.cancel();
        }

        @Override
        public void setView(View view) {
            mToast.setView(view);
        }

        @Override
        public View getView() {
            return mToast.getView();
        }

        @Override
        public void setDuration(int duration) {
            mToast.setDuration(duration);
        }

        @Override
        public void setGravity(int gravity, int xOffset, int yOffset) {
            mToast.setGravity(gravity, xOffset, yOffset);
        }

        @Override
        public void setText(int resId) {
            mToast.setText(resId);
        }

        @Override
        public void setText(CharSequence s) {
            mToast.setText(s);
        }

        static class SafeHandler extends Handler {
            private Handler impl;

            SafeHandler(Handler impl) {
                this.impl = impl;
            }

            @Override
            public void handleMessage(Message msg) {
                try {
                    impl.handleMessage(msg);
                } catch (Exception e) {
                    LogUtils.e(e);
                }
            }

            @Override
            public void dispatchMessage(Message msg) {
                impl.dispatchMessage(msg);
            }
        }
    }

    static class ToastWithoutNotification implements IToast {

        private Toast mToast;

        private WindowManager mWM;

        private View mView;

        private WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();

        private Handler mHandler = new Handler(Looper.myLooper());

        ToastWithoutNotification(@NonNull Toast toast) {
            mToast = toast;

            mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            mParams.format = PixelFormat.TRANSLUCENT;
            mParams.windowAnimations = android.R.style.Animation_Toast;
            mParams.type = WindowManager.LayoutParams.TYPE_TOAST;
            mParams.setTitle("ToastWithoutNotification");
            mParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        }

        @Override
        public void show() {
            mView = mToast.getView();
            if (mView == null) return;
            Context context = mToast.getView().getContext();
            mWM = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

            final Configuration config = context.getResources().getConfiguration();
            final int gravity;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                gravity = Gravity.getAbsoluteGravity(mToast.getGravity(), config.getLayoutDirection());
            } else {
                gravity = mToast.getGravity();
            }
            mParams.gravity = gravity;
            if ((gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.FILL_HORIZONTAL) {
                mParams.horizontalWeight = 1.0f;
            }
            if ((gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.FILL_VERTICAL) {
                mParams.verticalWeight = 1.0f;
            }
            mParams.x = mToast.getXOffset();
            mParams.y = mToast.getYOffset();

            mParams.packageName = Utils.getApp().getPackageName();

            try {
                mWM.addView(mView, mParams);
            } catch (Exception ignored) { /**/ }

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    cancel();
                }
            }, mToast.getDuration() == Toast.LENGTH_SHORT ? 2000 : 3500);
        }

        @Override
        public void cancel() {
            try {
                mWM.removeView(mView);
            } catch (IllegalArgumentException ignored) { /**/ }
            mView = null;
            mHandler = null;
            mToast = null;
        }

        @Override
        public void setView(View view) {
            mToast.setView(view);
        }

        @Override
        public View getView() {
            return mToast.getView();
        }

        @Override
        public void setDuration(int duration) {
            mToast.setDuration(duration);
        }

        @Override
        public void setGravity(int gravity, int xOffset, int yOffset) {
            mToast.setGravity(gravity, xOffset, yOffset);
        }

        @Override
        public void setText(int resId) {
            mToast.setText(resId);
        }

        @Override
        public void setText(CharSequence s) {
            mToast.setText(s);
        }
    }

    interface IToast {

        void show();

        void cancel();

        void setView(View view);

        View getView();

        void setDuration(int duration);

        void setGravity(int gravity, int xOffset, int yOffset);

        void setText(@StringRes int resId);

        void setText(CharSequence s);
    }
}