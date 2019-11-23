package com.blankj.utilcode.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.util.StateSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2019/06/12
 *     desc  : utils about click
 * </pre>
 */
public class ClickUtils {

    private static final int   PRESSED_VIEW_SCALE_TAG           = -1;
    private static final float PRESSED_VIEW_SCALE_DEFAULT_VALUE = -0.06f;

    private static final int   PRESSED_VIEW_ALPHA_TAG           = -2;
    private static final int   PRESSED_VIEW_ALPHA_SRC_TAG       = -3;
    private static final float PRESSED_VIEW_ALPHA_DEFAULT_VALUE = 0.8f;

    private static final int   PRESSED_BG_ALPHA_STYLE         = 4;
    private static final float PRESSED_BG_ALPHA_DEFAULT_VALUE = 0.9f;

    private static final int   PRESSED_BG_DARK_STYLE         = 5;
    private static final float PRESSED_BG_DARK_DEFAULT_VALUE = 0.9f;

    private static final int  DEBOUNCING_TAG           = -7;
    private static final long DEBOUNCING_DEFAULT_VALUE = 200;

    private ClickUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Apply scale animation for the views' click.
     *
     * @param views The views.
     */
    public static void applyPressedViewScale(final View... views) {
        applyPressedViewScale(views, null);
    }

    /**
     * Apply scale animation for the views' click.
     *
     * @param views        The views.
     * @param scaleFactors The factors of scale for the views.
     */
    public static void applyPressedViewScale(final View[] views, final float[] scaleFactors) {
        if (views == null || views.length == 0) {
            return;
        }
        for (int i = 0; i < views.length; i++) {
            if (scaleFactors == null || i >= scaleFactors.length) {
                applyPressedViewScale(views[i], PRESSED_VIEW_SCALE_DEFAULT_VALUE);
            } else {
                applyPressedViewScale(views[i], scaleFactors[i]);
            }
        }
    }

    /**
     * Apply scale animation for the views' click.
     *
     * @param view        The view.
     * @param scaleFactor The factor of scale for the view.
     */
    public static void applyPressedViewScale(final View view, final float scaleFactor) {
        if (view == null) {
            return;
        }
        view.setTag(PRESSED_VIEW_SCALE_TAG, scaleFactor);
        view.setClickable(true);
        view.setOnTouchListener(OnUtilsTouchListener.getInstance());
    }

    /**
     * Apply alpha for the views' click.
     *
     * @param views The views.
     */
    public static void applyPressedViewAlpha(final View... views) {
        applyPressedViewAlpha(views, null);
    }

    /**
     * Apply alpha for the views' click.
     *
     * @param views  The views.
     * @param alphas The alphas for the views.
     */
    public static void applyPressedViewAlpha(final View[] views, final float[] alphas) {
        if (views == null || views.length == 0) return;
        for (int i = 0; i < views.length; i++) {
            if (alphas == null || i >= alphas.length) {
                applyPressedViewAlpha(views[i], PRESSED_VIEW_ALPHA_DEFAULT_VALUE);
            } else {
                applyPressedViewAlpha(views[i], alphas[i]);
            }
        }
    }


    /**
     * Apply scale animation for the views' click.
     *
     * @param view  The view.
     * @param alpha The alpha for the view.
     */
    public static void applyPressedViewAlpha(final View view, final float alpha) {
        if (view == null) {
            return;
        }
        view.setTag(PRESSED_VIEW_ALPHA_TAG, alpha);
        view.setTag(PRESSED_VIEW_ALPHA_SRC_TAG, view.getAlpha());
        view.setClickable(true);
        view.setOnTouchListener(OnUtilsTouchListener.getInstance());
    }

    /**
     * Apply alpha for the view's background.
     *
     * @param view The views.
     */
    public static void applyPressedBgAlpha(View view) {
        applyPressedBgAlpha(view, PRESSED_BG_ALPHA_DEFAULT_VALUE);
    }

    /**
     * Apply alpha for the view's background.
     *
     * @param view  The views.
     * @param alpha The alpha.
     */
    public static void applyPressedBgAlpha(View view, float alpha) {
        applyPressedBgStyle(view, PRESSED_BG_ALPHA_STYLE, alpha);
    }

    /**
     * Apply alpha of dark for the view's background.
     *
     * @param view The views.
     */
    public static void applyPressedBgDark(View view) {
        applyPressedBgDark(view, PRESSED_BG_DARK_DEFAULT_VALUE);
    }

    /**
     * Apply alpha of dark for the view's background.
     *
     * @param view      The views.
     * @param darkAlpha The alpha of dark.
     */
    public static void applyPressedBgDark(View view, float darkAlpha) {
        applyPressedBgStyle(view, PRESSED_BG_DARK_STYLE, darkAlpha);
    }

    private static void applyPressedBgStyle(View view, int style, float value) {
        if (view == null) return;
        Drawable background = view.getBackground();
        Object tag = view.getTag(-style);
        if (tag instanceof Drawable) {
            ViewCompat.setBackground(view, (Drawable) tag);
        } else {
            background = createStyleDrawable(background, style, value);
            ViewCompat.setBackground(view, background);
            view.setTag(-style, background);
        }
    }

    private static Drawable createStyleDrawable(Drawable src, int style, float value) {
        if (src == null) {
            src = new ColorDrawable(0);
        }
        if (src.getConstantState() == null) return src;

        Drawable pressed = src.getConstantState().newDrawable().mutate();
        if (style == PRESSED_BG_ALPHA_STYLE) {
            pressed = createAlphaDrawable(pressed, value);
        } else if (style == PRESSED_BG_DARK_STYLE) {
            pressed = createDarkDrawable(pressed, value);
        }

        Drawable disable = src.getConstantState().newDrawable().mutate();
        disable = createAlphaDrawable(pressed, 0.5f);

        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed}, pressed);
        drawable.addState(new int[]{-android.R.attr.state_enabled}, disable);
        drawable.addState(StateSet.WILD_CARD, src);
        return drawable;
    }

    private static Drawable createAlphaDrawable(Drawable drawable, float alpha) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT
                && !(drawable instanceof ColorDrawable)) {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas myCanvas = new Canvas(bitmap);
            drawable.setAlpha((int) (alpha * 255));
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            drawable.draw(myCanvas);
            return new BitmapDrawable(Resources.getSystem(), bitmap);
        }
        drawable.setAlpha((int) (alpha * 255));
        return drawable;
    }

    private static Drawable createDarkDrawable(Drawable drawable, float alpha) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT && !(drawable instanceof ColorDrawable)) {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas myCanvas = new Canvas(bitmap);
            drawable.setColorFilter(getDarkColorFilter(alpha));
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            drawable.draw(myCanvas);
            return new BitmapDrawable(Resources.getSystem(), bitmap);
        }
        drawable.setColorFilter(getDarkColorFilter(alpha));
        return drawable;
    }

    private static ColorMatrixColorFilter getDarkColorFilter(float darkAlpha) {
        return new ColorMatrixColorFilter(new ColorMatrix(new float[]{
                darkAlpha, 0, 0, 0, 0,
                0, darkAlpha, 0, 0, 0,
                0, 0, darkAlpha, 0, 0,
                0, 0, 0, 2, 0
        }));
    }

    /**
     * Apply single debouncing for the view's click.
     *
     * @param view     The view.
     * @param listener The listener.
     */
    public static void applySingleDebouncing(final View view, final View.OnClickListener listener) {
        applySingleDebouncing(new View[]{view}, listener);
    }

    /**
     * Apply single debouncing for the view's click.
     *
     * @param view     The view.
     * @param duration The duration of debouncing.
     * @param listener The listener.
     */
    public static void applySingleDebouncing(final View view, @IntRange(from = 0) long duration,
                                             final View.OnClickListener listener) {
        applySingleDebouncing(new View[]{view}, duration, listener);
    }

    /**
     * Apply single debouncing for the views' click.
     *
     * @param views    The views.
     * @param listener The listener.
     */
    public static void applySingleDebouncing(final View[] views, final View.OnClickListener listener) {
        applySingleDebouncing(views, DEBOUNCING_DEFAULT_VALUE, listener);
    }

    /**
     * Apply single debouncing for the views' click.
     *
     * @param views    The views.
     * @param duration The duration of debouncing.
     * @param listener The listener.
     */
    public static void applySingleDebouncing(final View[] views,
                                             @IntRange(from = 0) long duration,
                                             final View.OnClickListener listener) {
        applyDebouncing(views, false, duration, listener);
    }

    /**
     * Apply global debouncing for the view's click.
     *
     * @param view     The view.
     * @param listener The listener.
     */
    public static void applyGlobalDebouncing(final View view, final View.OnClickListener listener) {
        applyGlobalDebouncing(new View[]{view}, listener);
    }

    /**
     * Apply global debouncing for the view's click.
     *
     * @param view     The view.
     * @param duration The duration of debouncing.
     * @param listener The listener.
     */
    public static void applyGlobalDebouncing(final View view, @IntRange(from = 0) long duration,
                                             final View.OnClickListener listener) {
        applyGlobalDebouncing(new View[]{view}, duration, listener);
    }


    /**
     * Apply global debouncing for the views' click.
     *
     * @param views    The views.
     * @param listener The listener.
     */
    public static void applyGlobalDebouncing(final View[] views, final View.OnClickListener listener) {
        applyGlobalDebouncing(views, DEBOUNCING_DEFAULT_VALUE, listener);
    }

    /**
     * Apply global debouncing for the views' click.
     *
     * @param views    The views.
     * @param duration The duration of debouncing.
     * @param listener The listener.
     */
    public static void applyGlobalDebouncing(final View[] views,
                                             @IntRange(from = 0) long duration,
                                             final View.OnClickListener listener) {
        applyDebouncing(views, true, duration, listener);
    }

    private static void applyDebouncing(final View[] views,
                                        final boolean isGlobal,
                                        @IntRange(from = 0) long duration,
                                        final View.OnClickListener listener) {
        if (views == null || views.length == 0 || listener == null) return;
        for (View view : views) {
            if (view == null) continue;
            view.setOnClickListener(new OnDebouncingClickListener(isGlobal, duration) {
                @Override
                public void onDebouncingClick(View v) {
                    listener.onClick(v);
                }
            });
        }
    }

    private static int dp2px(final float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static abstract class OnDebouncingClickListener implements View.OnClickListener {

        private static boolean mEnabled = true;

        private static final Runnable ENABLE_AGAIN = new Runnable() {
            @Override
            public void run() {
                mEnabled = true;
            }
        };

        private static boolean isValid(@NonNull final View view, final long duration) {
            long curTime = System.currentTimeMillis();
            Object tag = view.getTag(DEBOUNCING_TAG);
            if (!(tag instanceof Long)) {
                view.setTag(DEBOUNCING_TAG, curTime);
                return true;
            }
            long preTime = (Long) tag;
            if (curTime - preTime <= duration) return false;
            view.setTag(DEBOUNCING_TAG, curTime);
            return true;
        }

        private long    mDuration;
        private boolean mIsGlobal;

        public OnDebouncingClickListener() {
            this(true, DEBOUNCING_DEFAULT_VALUE);
        }

        public OnDebouncingClickListener(final boolean isGlobal) {
            this(isGlobal, DEBOUNCING_DEFAULT_VALUE);
        }

        public OnDebouncingClickListener(final long duration) {
            this(true, duration);
        }

        public OnDebouncingClickListener(final boolean isGlobal, final long duration) {
            mIsGlobal = isGlobal;
            mDuration = duration;
        }

        public abstract void onDebouncingClick(View v);

        @Override
        public final void onClick(View v) {
            if (mIsGlobal) {
                if (mEnabled) {
                    mEnabled = false;
                    v.postDelayed(ENABLE_AGAIN, mDuration);
                    onDebouncingClick(v);
                }
            } else {
                if (isValid(v, mDuration)) {
                    onDebouncingClick(v);
                }
            }
        }
    }

    public static abstract class OnMultiClickListener implements View.OnClickListener {

        private static final long INTERVAL_DEFAULT_VALUE = 666;

        private final int  mTriggerClickCount;
        private final long mClickInterval;

        private long mLastClickTime;
        private int  mClickCount;

        public OnMultiClickListener(int triggerClickCount) {
            this(triggerClickCount, INTERVAL_DEFAULT_VALUE);
        }

        public OnMultiClickListener(int triggerClickCount, long clickInterval) {
            this.mTriggerClickCount = triggerClickCount;
            this.mClickInterval = clickInterval;
        }

        public abstract void onTriggerClick(View v);

        public abstract void onBeforeTriggerClick(View v, int count);

        @Override
        public void onClick(View v) {
            if (mTriggerClickCount <= 1) {
                onTriggerClick(v);
                return;
            }
            long curTime = System.currentTimeMillis();

            if (curTime - mLastClickTime < mClickInterval) {
                mClickCount++;
                if (mClickCount == mTriggerClickCount) {
                    onTriggerClick(v);
                } else if (mClickCount < mTriggerClickCount) {
                    onBeforeTriggerClick(v, mClickCount);
                } else {
                    mClickCount = 1;
                    onBeforeTriggerClick(v, mClickCount);
                }
            } else {
                mClickCount = 1;
                onBeforeTriggerClick(v, mClickCount);
            }
            mLastClickTime = curTime;
        }
    }

    private static class OnUtilsTouchListener implements View.OnTouchListener {

        public static OnUtilsTouchListener getInstance() {
            return LazyHolder.INSTANCE;
        }

        private OnUtilsTouchListener() {/**/}

        @Override
        public boolean onTouch(final View v, MotionEvent event) {
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                processScale(v, true);
                processAlpha(v, true);
            } else if (action == MotionEvent.ACTION_UP
                    || action == MotionEvent.ACTION_CANCEL) {
                processScale(v, false);
                processAlpha(v, false);
            }
            return false;
        }

        private void processScale(final View view, boolean isDown) {
            Object tag = view.getTag(PRESSED_VIEW_SCALE_TAG);
            if (!(tag instanceof Float)) return;
            float value = isDown ? 1 + (Float) tag : 1;
            view.animate()
                    .scaleX(value)
                    .scaleY(value)
                    .setDuration(200)
                    .start();
        }

        private void processAlpha(final View view, boolean isDown) {
            Object tag = view.getTag(isDown ? PRESSED_VIEW_ALPHA_TAG : PRESSED_VIEW_ALPHA_SRC_TAG);
            if (!(tag instanceof Float)) return;
            view.setAlpha((Float) tag);
        }

        private static class LazyHolder {
            private static final OnUtilsTouchListener INSTANCE = new OnUtilsTouchListener();
        }
    }
}
