package com.blankj.utilcode.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.CallSuper;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.annotation.StringRes;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/29
 *     desc  : utils about toast
 * </pre>
 */
public final class ToastUtils {

    @StringDef({MODE.LIGHT, MODE.DARK})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MODE {
        String LIGHT = "light";
        String DARK  = "dark";
    }

    private static final String     TAG_TOAST     = "TAG_TOAST";
    private static final int        COLOR_DEFAULT = 0xFEFFFFFF;
    private static final String     NULL          = "toast null";
    private static final String     NOTHING       = "toast nothing";
    private static final ToastUtils DEFAULT_MAKER = make();

    private static WeakReference<IToast> sWeakToast;

    private String     mMode;
    private int        mGravity            = -1;
    private int        mXOffset            = -1;
    private int        mYOffset            = -1;
    private int        mBgColor            = COLOR_DEFAULT;
    private int        mBgResource         = -1;
    private int        mTextColor          = COLOR_DEFAULT;
    private int        mTextSize           = -1;
    private boolean    isLong              = false;
    private Drawable[] mIcons              = new Drawable[4];
    private boolean    isNotUseSystemToast = false;

    /**
     * Make a toast.
     *
     * @return the single {@link ToastUtils} instance
     */
    @NonNull
    public static ToastUtils make() {
        return new ToastUtils();
    }

    /**
     * @param mode The mode.
     * @return the single {@link ToastUtils} instance
     */
    @NonNull
    public final ToastUtils setMode(@MODE String mode) {
        mMode = mode;
        return this;
    }

    /**
     * Set the gravity.
     *
     * @param gravity The gravity.
     * @param xOffset X-axis offset, in pixel.
     * @param yOffset Y-axis offset, in pixel.
     * @return the single {@link ToastUtils} instance
     */
    @NonNull
    public final ToastUtils setGravity(final int gravity, final int xOffset, final int yOffset) {
        mGravity = gravity;
        mXOffset = xOffset;
        mYOffset = yOffset;
        return this;
    }

    /**
     * Set the color of background.
     *
     * @param backgroundColor The color of background.
     * @return the single {@link ToastUtils} instance
     */
    @NonNull
    public final ToastUtils setBgColor(@ColorInt final int backgroundColor) {
        mBgColor = backgroundColor;
        return this;
    }

    /**
     * Set the resource of background.
     *
     * @param bgResource The resource of background.
     * @return the single {@link ToastUtils} instance
     */
    @NonNull
    public final ToastUtils setBgResource(@DrawableRes final int bgResource) {
        mBgResource = bgResource;
        return this;
    }

    /**
     * Set the text color of toast.
     *
     * @param msgColor The text color of toast.
     * @return the single {@link ToastUtils} instance
     */
    @NonNull
    public final ToastUtils setTextColor(@ColorInt final int msgColor) {
        mTextColor = msgColor;
        return this;
    }

    /**
     * Set the text size of toast.
     *
     * @param textSize The text size of toast.
     * @return the single {@link ToastUtils} instance
     */
    @NonNull
    public final ToastUtils setTextSize(final int textSize) {
        mTextSize = textSize;
        return this;
    }

    /**
     * Set the toast for a long period of time.
     *
     * @return the single {@link ToastUtils} instance
     */
    @NonNull
    public final ToastUtils setDurationIsLong(boolean isLong) {
        this.isLong = isLong;
        return this;
    }

    /**
     * Set the left icon of toast.
     *
     * @param resId The left icon resource identifier.
     * @return the single {@link ToastUtils} instance
     */
    @NonNull
    public final ToastUtils setLeftIcon(@DrawableRes int resId) {
        return setLeftIcon(ContextCompat.getDrawable(Utils.getApp(), resId));
    }

    /**
     * Set the left icon of toast.
     *
     * @param drawable The left icon drawable.
     * @return the single {@link ToastUtils} instance
     */
    @NonNull
    public final ToastUtils setLeftIcon(@Nullable Drawable drawable) {
        mIcons[0] = drawable;
        return this;
    }

    /**
     * Set the top icon of toast.
     *
     * @param resId The top icon resource identifier.
     * @return the single {@link ToastUtils} instance
     */
    @NonNull
    public final ToastUtils setTopIcon(@DrawableRes int resId) {
        return setTopIcon(ContextCompat.getDrawable(Utils.getApp(), resId));
    }

    /**
     * Set the top icon of toast.
     *
     * @param drawable The top icon drawable.
     * @return the single {@link ToastUtils} instance
     */
    @NonNull
    public final ToastUtils setTopIcon(@Nullable Drawable drawable) {
        mIcons[1] = drawable;
        return this;
    }

    /**
     * Set the right icon of toast.
     *
     * @param resId The right icon resource identifier.
     * @return the single {@link ToastUtils} instance
     */
    @NonNull
    public final ToastUtils setRightIcon(@DrawableRes int resId) {
        return setRightIcon(ContextCompat.getDrawable(Utils.getApp(), resId));
    }

    /**
     * Set the right icon of toast.
     *
     * @param drawable The right icon drawable.
     * @return the single {@link ToastUtils} instance
     */
    @NonNull
    public final ToastUtils setRightIcon(@Nullable Drawable drawable) {
        mIcons[2] = drawable;
        return this;
    }

    /**
     * Set the left bottom of toast.
     *
     * @param resId The bottom icon resource identifier.
     * @return the single {@link ToastUtils} instance
     */
    @NonNull
    public final ToastUtils setBottomIcon(int resId) {
        return setBottomIcon(ContextCompat.getDrawable(Utils.getApp(), resId));
    }

    /**
     * Set the bottom icon of toast.
     *
     * @param drawable The bottom icon drawable.
     * @return the single {@link ToastUtils} instance
     */
    @NonNull
    public final ToastUtils setBottomIcon(@Nullable Drawable drawable) {
        mIcons[3] = drawable;
        return this;
    }

    /**
     * Set not use system toast.
     *
     * @return the single {@link ToastUtils} instance
     */
    @NonNull
    public final ToastUtils setNotUseSystemToast() {
        isNotUseSystemToast = true;
        return this;
    }

    /**
     * Return the default {@link ToastUtils} instance.
     *
     * @return the default {@link ToastUtils} instance
     */
    @NonNull
    public static ToastUtils getDefaultMaker() {
        return DEFAULT_MAKER;
    }

    /**
     * Show the toast for a short period of time.
     *
     * @param text The text.
     */
    public final void show(@Nullable final CharSequence text) {
        show(text, getDuration(), this);
    }

    /**
     * Show the toast for a short period of time.
     *
     * @param resId The resource id for text.
     */
    public final void show(@StringRes final int resId) {
        show(UtilsBridge.getString(resId), getDuration(), this);
    }

    /**
     * Show the toast for a short period of time.
     *
     * @param resId The resource id for text.
     * @param args  The args.
     */
    public final void show(@StringRes final int resId, final Object... args) {
        show(UtilsBridge.getString(resId, args), getDuration(), this);
    }

    /**
     * Show the toast for a short period of time.
     *
     * @param format The format.
     * @param args   The args.
     */
    public final void show(@Nullable final String format, final Object... args) {
        show(UtilsBridge.format(format, args), getDuration(), this);
    }

    /**
     * Show custom toast.
     */
    public final void show(@NonNull final View view) {
        show(view, getDuration(), this);
    }

    private int getDuration() {
        return isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT;
    }

    private View tryApplyUtilsToastView(final CharSequence text) {
        if (!MODE.DARK.equals(mMode) && !MODE.LIGHT.equals(mMode)
                && mIcons[0] == null && mIcons[1] == null && mIcons[2] == null && mIcons[3] == null) {
            return null;
        }

        View toastView = UtilsBridge.layoutId2View(R.layout.utils_toast_view);
        TextView messageTv = toastView.findViewById(android.R.id.message);
        if (MODE.DARK.equals(mMode)) {
            GradientDrawable bg = (GradientDrawable) toastView.getBackground().mutate();
            bg.setColor(Color.parseColor("#BB000000"));
            messageTv.setTextColor(Color.WHITE);
        }
        messageTv.setText(text);
        if (mIcons[0] != null) {
            View leftIconView = toastView.findViewById(R.id.utvLeftIconView);
            ViewCompat.setBackground(leftIconView, mIcons[0]);
            leftIconView.setVisibility(View.VISIBLE);
        }
        if (mIcons[1] != null) {
            View topIconView = toastView.findViewById(R.id.utvTopIconView);
            ViewCompat.setBackground(topIconView, mIcons[1]);
            topIconView.setVisibility(View.VISIBLE);
        }
        if (mIcons[2] != null) {
            View rightIconView = toastView.findViewById(R.id.utvRightIconView);
            ViewCompat.setBackground(rightIconView, mIcons[2]);
            rightIconView.setVisibility(View.VISIBLE);
        }
        if (mIcons[3] != null) {
            View bottomIconView = toastView.findViewById(R.id.utvBottomIconView);
            ViewCompat.setBackground(bottomIconView, mIcons[3]);
            bottomIconView.setVisibility(View.VISIBLE);
        }
        return toastView;
    }


    /**
     * Show the toast for a short period of time.
     *
     * @param text The text.
     */
    public static void showShort(@Nullable final CharSequence text) {
        show(text, Toast.LENGTH_SHORT, DEFAULT_MAKER);
    }

    /**
     * Show the toast for a short period of time.
     *
     * @param resId The resource id for text.
     */
    public static void showShort(@StringRes final int resId) {
        show(UtilsBridge.getString(resId), Toast.LENGTH_SHORT, DEFAULT_MAKER);
    }

    /**
     * Show the toast for a short period of time.
     *
     * @param resId The resource id for text.
     * @param args  The args.
     */
    public static void showShort(@StringRes final int resId, final Object... args) {
        show(UtilsBridge.getString(resId, args), Toast.LENGTH_SHORT, DEFAULT_MAKER);
    }

    /**
     * Show the toast for a short period of time.
     *
     * @param format The format.
     * @param args   The args.
     */
    public static void showShort(@Nullable final String format, final Object... args) {
        show(UtilsBridge.format(format, args), Toast.LENGTH_SHORT, DEFAULT_MAKER);
    }

    /**
     * Show the toast for a long period of time.
     *
     * @param text The text.
     */
    public static void showLong(@Nullable final CharSequence text) {
        show(text, Toast.LENGTH_LONG, DEFAULT_MAKER);
    }

    /**
     * Show the toast for a long period of time.
     *
     * @param resId The resource id for text.
     */
    public static void showLong(@StringRes final int resId) {
        show(UtilsBridge.getString(resId), Toast.LENGTH_LONG, DEFAULT_MAKER);
    }

    /**
     * Show the toast for a long period of time.
     *
     * @param resId The resource id for text.
     * @param args  The args.
     */
    public static void showLong(@StringRes final int resId, final Object... args) {
        show(UtilsBridge.getString(resId), Toast.LENGTH_LONG, DEFAULT_MAKER);
    }

    /**
     * Show the toast for a long period of time.
     *
     * @param format The format.
     * @param args   The args.
     */
    public static void showLong(@Nullable final String format, final Object... args) {
        show(UtilsBridge.format(format, args), Toast.LENGTH_LONG, DEFAULT_MAKER);
    }

    /**
     * Cancel the toast.
     */
    public static void cancel() {
        UtilsBridge.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (sWeakToast != null) {
                    final IToast iToast = ToastUtils.sWeakToast.get();
                    if (iToast != null) {
                        iToast.cancel();
                    }
                    sWeakToast = null;
                }
            }
        });
    }

    private static void show(@Nullable final CharSequence text, final int duration, final ToastUtils utils) {
        show(null, getToastFriendlyText(text), duration, utils);
    }

    private static void show(@NonNull final View view, final int duration, final ToastUtils utils) {
        show(view, null, duration, utils);
    }

    private static void show(@Nullable final View view,
                             @Nullable final CharSequence text,
                             final int duration,
                             @NonNull final ToastUtils utils) {
        UtilsBridge.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                cancel();
                IToast iToast = newToast(utils);
                ToastUtils.sWeakToast = new WeakReference<>(iToast);
                if (view != null) {
                    iToast.setToastView(view);
                } else {
                    iToast.setToastView(text);
                }
                iToast.show(duration);
            }
        });
    }

    private static CharSequence getToastFriendlyText(CharSequence src) {
        CharSequence text = src;
        if (text == null) {
            text = NULL;
        } else if (text.length() == 0) {
            text = NOTHING;
        }
        return text;
    }

    private static IToast newToast(ToastUtils toastUtils) {
        if (!toastUtils.isNotUseSystemToast) {
            if (NotificationManagerCompat.from(Utils.getApp()).areNotificationsEnabled()) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    return new SystemToast(toastUtils);
                }
                if (!UtilsBridge.isGrantedDrawOverlays()) {
                    return new SystemToast(toastUtils);
                }
            }
        }

        // not use system or notification disable
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
            return new WindowManagerToast(toastUtils, WindowManager.LayoutParams.TYPE_TOAST);
        } else if (UtilsBridge.isGrantedDrawOverlays()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return new WindowManagerToast(toastUtils, WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
            } else {
                return new WindowManagerToast(toastUtils, WindowManager.LayoutParams.TYPE_PHONE);
            }
        }
        return new ActivityToast(toastUtils);
    }

    static final class SystemToast extends AbsToast {

        SystemToast(ToastUtils toastUtils) {
            super(toastUtils);
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
                try {
                    //noinspection JavaReflectionMemberAccess
                    Field mTNField = Toast.class.getDeclaredField("mTN");
                    mTNField.setAccessible(true);
                    Object mTN = mTNField.get(mToast);
                    Field mTNmHandlerField = mTNField.getType().getDeclaredField("mHandler");
                    mTNmHandlerField.setAccessible(true);
                    Handler tnHandler = (Handler) mTNmHandlerField.get(mTN);
                    mTNmHandlerField.set(mTN, new SafeHandler(tnHandler));
                } catch (Exception ignored) {/**/}
            }
        }

        @Override
        public void show(int duration) {
            if (mToast == null) return;
            mToast.setDuration(duration);
            mToast.show();
        }

        static class SafeHandler extends Handler {
            private Handler impl;

            SafeHandler(Handler impl) {
                this.impl = impl;
            }

            @Override
            public void handleMessage(@NonNull Message msg) {
                impl.handleMessage(msg);
            }

            @Override
            public void dispatchMessage(@NonNull Message msg) {
                try {
                    impl.dispatchMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static final class WindowManagerToast extends AbsToast {

        private WindowManager mWM;

        private WindowManager.LayoutParams mParams;

        WindowManagerToast(ToastUtils toastUtils, int type) {
            super(toastUtils);
            mParams = new WindowManager.LayoutParams();
            mWM = (WindowManager) Utils.getApp().getSystemService(Context.WINDOW_SERVICE);
            mParams.type = type;
        }

        WindowManagerToast(ToastUtils toastUtils, WindowManager wm, int type) {
            super(toastUtils);
            mParams = new WindowManager.LayoutParams();
            mWM = wm;
            mParams.type = type;
        }

        @Override
        public void show(final int duration) {
            if (mToast == null) return;
            mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            mParams.format = PixelFormat.TRANSLUCENT;
            mParams.windowAnimations = android.R.style.Animation_Toast;
            mParams.setTitle("ToastWithoutNotification");
            mParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
            mParams.packageName = Utils.getApp().getPackageName();

            mParams.gravity = mToast.getGravity();
            if ((mParams.gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.FILL_HORIZONTAL) {
                mParams.horizontalWeight = 1.0f;
            }
            if ((mParams.gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.FILL_VERTICAL) {
                mParams.verticalWeight = 1.0f;
            }

            mParams.x = mToast.getXOffset();
            mParams.y = mToast.getYOffset();
            mParams.horizontalMargin = mToast.getHorizontalMargin();
            mParams.verticalMargin = mToast.getVerticalMargin();

            try {
                if (mWM != null) {
                    mWM.addView(mToastView, mParams);
                }
            } catch (Exception ignored) {/**/}

            UtilsBridge.runOnUiThreadDelayed(new Runnable() {
                @Override
                public void run() {
                    cancel();
                }
            }, duration == Toast.LENGTH_SHORT ? 2000 : 3500);
        }

        @Override
        public void cancel() {
            try {
                if (mWM != null) {
                    mWM.removeViewImmediate(mToastView);
                    mWM = null;
                }
            } catch (Exception ignored) {/**/}
            super.cancel();
        }
    }

    static final class ActivityToast extends AbsToast {

        private static int sShowingIndex = 0;

        private Utils.ActivityLifecycleCallbacks mActivityLifecycleCallbacks;
        private IToast                           iToast;

        ActivityToast(ToastUtils toastUtils) {
            super(toastUtils);
        }

        @Override
        public void show(int duration) {
            if (mToast == null) return;
            if (!UtilsBridge.isAppForeground()) {
                // try to use system toast
                iToast = showSystemToast(duration);
                return;
            }
            boolean hasAliveActivity = false;
            for (final Activity activity : UtilsBridge.getActivityList()) {
                if (!UtilsBridge.isActivityAlive(activity)) {
                    continue;
                }
                if (!hasAliveActivity) {
                    hasAliveActivity = true;
                    iToast = showWithActivityWindow(activity, duration);
                } else {
                    showWithActivityView(activity, sShowingIndex, true);
                }
            }
            if (hasAliveActivity) {
                registerLifecycleCallback();
                UtilsBridge.runOnUiThreadDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cancel();
                    }
                }, duration == Toast.LENGTH_SHORT ? 2000 : 3500);

                ++sShowingIndex;
            } else {
                // try to use system toast
                iToast = showSystemToast(duration);
            }
        }

        @Override
        public void cancel() {
            if (isShowing()) {
                unregisterLifecycleCallback();
                for (Activity activity : UtilsBridge.getActivityList()) {
                    if (!UtilsBridge.isActivityAlive(activity)) {
                        continue;
                    }
                    final Window window = activity.getWindow();
                    if (window != null) {
                        ViewGroup decorView = (ViewGroup) window.getDecorView();
                        View toastView = decorView.findViewWithTag(TAG_TOAST + (sShowingIndex - 1));
                        if (toastView != null) {
                            try {
                                decorView.removeView(toastView);
                            } catch (Exception ignored) {/**/}
                        }
                    }
                }
            }
            if (iToast != null) {
                iToast.cancel();
                iToast = null;
            }
            super.cancel();
        }

        private IToast showSystemToast(int duration) {
            SystemToast systemToast = new SystemToast(mToastUtils);
            systemToast.mToast = mToast;
            systemToast.show(duration);
            return systemToast;
        }

        private IToast showWithActivityWindow(Activity activity, int duration) {
            WindowManagerToast wmToast = new WindowManagerToast(mToastUtils, activity.getWindowManager(), WindowManager.LayoutParams.LAST_APPLICATION_WINDOW);
            wmToast.mToastView = getToastViewSnapshot(-1);
            wmToast.mToast = mToast;
            wmToast.show(duration);
            return wmToast;
        }

        private void showWithActivityView(final Activity activity, final int index, boolean useAnim) {
            final Window window = activity.getWindow();
            if (window != null) {
                final ViewGroup decorView = (ViewGroup) window.getDecorView();
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                );
                lp.gravity = mToast.getGravity();
                lp.bottomMargin = mToast.getYOffset() + UtilsBridge.getNavBarHeight();
                lp.topMargin = mToast.getYOffset() + UtilsBridge.getStatusBarHeight();
                lp.leftMargin = mToast.getXOffset();
                View toastViewSnapshot = getToastViewSnapshot(index);
                if (useAnim) {
                    toastViewSnapshot.setAlpha(0);
                    toastViewSnapshot.animate().alpha(1).setDuration(200).start();
                }
                decorView.addView(toastViewSnapshot, lp);
            }
        }

        private void registerLifecycleCallback() {
            final int index = sShowingIndex;
            mActivityLifecycleCallbacks = new Utils.ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(@NonNull Activity activity) {
                    if (isShowing()) {
                        showWithActivityView(activity, index, false);
                    }
                }
            };
            UtilsBridge.addActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
        }

        private void unregisterLifecycleCallback() {
            UtilsBridge.removeActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
            mActivityLifecycleCallbacks = null;
        }

        private boolean isShowing() {
            return mActivityLifecycleCallbacks != null;
        }
    }

    static abstract class AbsToast implements IToast {

        protected Toast      mToast;
        protected ToastUtils mToastUtils;
        protected View       mToastView;

        AbsToast(ToastUtils toastUtils) {
            mToast = new Toast(Utils.getApp());
            mToastUtils = toastUtils;

            if (mToastUtils.mGravity != -1 || mToastUtils.mXOffset != -1 || mToastUtils.mYOffset != -1) {
                mToast.setGravity(mToastUtils.mGravity, mToastUtils.mXOffset, mToastUtils.mYOffset);
            }
        }

        @Override
        public void setToastView(View view) {
            mToastView = view;
            mToast.setView(mToastView);
        }

        @Override
        public void setToastView(CharSequence text) {
            View utilsToastView = mToastUtils.tryApplyUtilsToastView(text);
            if (utilsToastView != null) {
                setToastView(utilsToastView);
                processRtlIfNeed();
                return;
            }

            mToastView = mToast.getView();
            if (mToastView == null || mToastView.findViewById(android.R.id.message) == null) {
                setToastView(UtilsBridge.layoutId2View(R.layout.utils_toast_view));
            }

            TextView messageTv = mToastView.findViewById(android.R.id.message);
            messageTv.setText(text);
            if (mToastUtils.mTextColor != COLOR_DEFAULT) {
                messageTv.setTextColor(mToastUtils.mTextColor);
            }
            if (mToastUtils.mTextSize != -1) {
                messageTv.setTextSize(mToastUtils.mTextSize);
            }
            setBg(messageTv);
            processRtlIfNeed();
        }

        private void processRtlIfNeed() {
            if (UtilsBridge.isLayoutRtl()) {
                setToastView(getToastViewSnapshot(-1));
            }
        }

        private void setBg(final TextView msgTv) {
            if (mToastUtils.mBgResource != -1) {
                mToastView.setBackgroundResource(mToastUtils.mBgResource);
                msgTv.setBackgroundColor(Color.TRANSPARENT);
            } else if (mToastUtils.mBgColor != COLOR_DEFAULT) {
                Drawable toastBg = mToastView.getBackground();
                Drawable msgBg = msgTv.getBackground();
                if (toastBg != null && msgBg != null) {
                    toastBg.mutate().setColorFilter(new PorterDuffColorFilter(mToastUtils.mBgColor, PorterDuff.Mode.SRC_IN));
                    msgTv.setBackgroundColor(Color.TRANSPARENT);
                } else if (toastBg != null) {
                    toastBg.mutate().setColorFilter(new PorterDuffColorFilter(mToastUtils.mBgColor, PorterDuff.Mode.SRC_IN));
                } else if (msgBg != null) {
                    msgBg.mutate().setColorFilter(new PorterDuffColorFilter(mToastUtils.mBgColor, PorterDuff.Mode.SRC_IN));
                } else {
                    mToastView.setBackgroundColor(mToastUtils.mBgColor);
                }
            }
        }

        @Override
        @CallSuper
        public void cancel() {
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = null;
            mToastView = null;
        }

        View getToastViewSnapshot(final int index) {
            Bitmap bitmap = UtilsBridge.view2Bitmap(mToastView);
            ImageView toastIv = new ImageView(Utils.getApp());
            toastIv.setTag(TAG_TOAST + index);
            toastIv.setImageBitmap(bitmap);
            return toastIv;
        }
    }

    interface IToast {

        void setToastView(View view);

        void setToastView(CharSequence text);

        void show(int duration);

        void cancel();
    }

    public static final class UtilsMaxWidthRelativeLayout extends RelativeLayout {

        private static final int SPACING = UtilsBridge.dp2px(80);

        public UtilsMaxWidthRelativeLayout(Context context) {
            super(context);
        }

        public UtilsMaxWidthRelativeLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public UtilsMaxWidthRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int widthMaxSpec = MeasureSpec.makeMeasureSpec(UtilsBridge.getAppScreenWidth() - SPACING, MeasureSpec.AT_MOST);
            super.onMeasure(widthMaxSpec, heightMeasureSpec);
        }
    }
}