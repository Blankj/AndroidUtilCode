package com.blankj.utilcode.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import java.lang.reflect.Field;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/02
 *     desc  : utils about keyboard
 * </pre>
 */
public final class KeyboardUtils {

    private static int                        sDecorViewInvisibleHeightPre;
    private static OnGlobalLayoutListener     onGlobalLayoutListener;
    private static OnSoftInputChangedListener onSoftInputChangedListener;
    private static int                        sContentViewInvisibleHeightPre5497;

    private KeyboardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Show the soft input.
     *
     * @param activity The activity.
     */
    public static void showSoftInput(final Activity activity) {
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.requestFocus();
        }
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * Show the soft input.
     *
     * @param view The view.
     */
    public static void showSoftInput(final View view) {
        InputMethodManager imm =
                (InputMethodManager) Utils.getApp().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * Show the soft input using toggle.
     *
     * @param activity The activity.
     */
    public static void showSoftInputUsingToggle(final Activity activity) {
        if (isSoftInputVisible(activity)) return;
        toggleSoftInput();
    }

    /**
     * Hide the soft input.
     *
     * @param activity The activity.
     */
    public static void hideSoftInput(final Activity activity) {
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        View view = activity.getCurrentFocus();
        if (view == null) view = new View(activity);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Hide the soft input.
     *
     * @param view The view.
     */
    public static void hideSoftInput(final View view) {
        InputMethodManager imm =
                (InputMethodManager) Utils.getApp().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Hide the soft input.
     *
     * @param activity The activity.
     */
    public static void hideSoftInputUsingToggle(final Activity activity) {
        if (!isSoftInputVisible(activity)) return;
        toggleSoftInput();
    }

    /**
     * Toggle the soft input display or not.
     */
    public static void toggleSoftInput() {
        InputMethodManager imm =
                (InputMethodManager) Utils.getApp().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * Return whether soft input is visible.
     *
     * @param activity The activity.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isSoftInputVisible(final Activity activity) {
        return getDecorViewInvisibleHeight(activity) > 0;
    }

    private static int sDelta = 0;

    private static int getDecorViewInvisibleHeight(final Activity activity) {
        final View decorView = activity.getWindow().getDecorView();
        final Rect outRect = new Rect();
        decorView.getWindowVisibleDisplayFrame(outRect);
        LogUtils.d("KeyboardUtils", "getDecorViewInvisibleHeight: "
                + (decorView.getBottom() - outRect.bottom));
        int delta = Math.abs(decorView.getBottom() - outRect.bottom);
        if (delta <= getNavBarHeight()) {
            sDelta = delta;
            return 0;
        }
        return delta - sDelta;
    }

    /**
     * Register soft input changed listener.
     *
     * @param activity The activity.
     * @param listener The soft input changed listener.
     */
    public static void registerSoftInputChangedListener(final Activity activity,
                                                        final OnSoftInputChangedListener listener) {
        final int flags = activity.getWindow().getAttributes().flags;
        if ((flags & WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS) != 0) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        final FrameLayout contentView = activity.findViewById(android.R.id.content);
        sDecorViewInvisibleHeightPre = getDecorViewInvisibleHeight(activity);
        onSoftInputChangedListener = listener;
        onGlobalLayoutListener = new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (onSoftInputChangedListener != null) {
                    int height = getDecorViewInvisibleHeight(activity);
                    if (sDecorViewInvisibleHeightPre != height) {
                        onSoftInputChangedListener.onSoftInputChanged(height);
                        sDecorViewInvisibleHeightPre = height;
                    }
                }
            }
        };
        contentView.getViewTreeObserver()
                .addOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    /**
     * Unregister soft input changed listener.
     *
     * @param activity The activity.
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void unregisterSoftInputChangedListener(final Activity activity) {
        final View contentView = activity.findViewById(android.R.id.content);
        contentView.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
        onSoftInputChangedListener = null;
        onGlobalLayoutListener = null;
    }

    /**
     * Fix the bug of 5497 in Android.
     *
     * @param activity The activity.
     */
    public static void fixAndroidBug5497(final Activity activity) {
        final FrameLayout contentView = activity.findViewById(android.R.id.content);
        final View contentViewChild = contentView.getChildAt(0);
        final int paddingBottom = contentViewChild.getPaddingBottom();
        sContentViewInvisibleHeightPre5497 = getContentViewInvisibleHeight(activity);
        contentView.getViewTreeObserver()
                .addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int height = getContentViewInvisibleHeight(activity);
                        if (sContentViewInvisibleHeightPre5497 != height) {
                            contentViewChild.setPadding(
                                    contentViewChild.getPaddingLeft(),
                                    contentViewChild.getPaddingTop(),
                                    contentViewChild.getPaddingRight(),
                                    paddingBottom + height
                            );
                            sContentViewInvisibleHeightPre5497 = height;
                        }
                    }
                });
    }

    private static int getContentViewInvisibleHeight(final Activity activity) {
        final FrameLayout contentView = activity.findViewById(android.R.id.content);
        final Rect outRect = new Rect();
        contentView.getWindowVisibleDisplayFrame(outRect);
        LogUtils.d("KeyboardUtils", "getContentViewInvisibleHeight: "
                + (contentView.getBottom() - outRect.bottom));
        int delta = Math.abs(contentView.getBottom() - outRect.bottom);
        if (delta <= getStatusBarHeight()) {
            return 0;
        }
        return delta;
    }

    /**
     * Fix the leaks of soft input.
     * <p>Call the function in {@link Activity#onDestroy()}.</p>
     *
     * @param context The context.
     */
    public static void fixSoftInputLeaks(final Context context) {
        if (context == null) return;
        InputMethodManager imm =
                (InputMethodManager) Utils.getApp().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        String[] strArr = new String[]{"mCurRootView", "mServedView", "mNextServedView", "mLastSrvView"};
        for (int i = 0; i < 4; i++) {
            try {
                Field declaredField = imm.getClass().getDeclaredField(strArr[i]);
                if (declaredField == null) continue;
                if (!declaredField.isAccessible()) {
                    declaredField.setAccessible(true);
                }
                Object obj = declaredField.get(imm);
                if (obj == null || !(obj instanceof View)) continue;
                View view = (View) obj;
                if (view.getContext() == context) {
                    declaredField.set(imm, null);
                } else {
                    return;
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    /**
     * Click blankj area to hide soft input.
     * <p>Copy the following code in ur activity.</p>
     */
    public static void clickBlankArea2HideSoftInput() {
        Log.i("KeyboardUtils", "Please refer to the following code.");
        /*
        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                View v = getCurrentFocus();
                if (isShouldHideKeyboard(v, ev)) {
                    InputMethodManager imm =
                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS
                    );
                }
            }
            return super.dispatchTouchEvent(ev);
        }

        // Return whether touch the view.
        private boolean isShouldHideKeyboard(View v, MotionEvent event) {
            if (v != null && (v instanceof EditText)) {
                int[] l = {0, 0};
                v.getLocationInWindow(l);
                int left = l[0],
                        top = l[1],
                        bottom = top + v.getHeight(),
                        right = left + v.getWidth();
                return !(event.getX() > left && event.getX() < right
                        && event.getY() > top && event.getY() < bottom);
            }
            return false;
        }
        */
    }

    private static int getStatusBarHeight() {
        Resources resources = Resources.getSystem();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    private static int getNavBarHeight() {
        Resources res = Resources.getSystem();
        int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId != 0) {
            return res.getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // interface
    ///////////////////////////////////////////////////////////////////////////

    public interface OnSoftInputChangedListener {
        void onSoftInputChanged(int height);
    }
}
