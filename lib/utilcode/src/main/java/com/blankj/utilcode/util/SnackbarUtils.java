package com.blankj.utilcode.util;

import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/16
 *     desc  : utils about snackbar
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

    private static WeakReference<Snackbar> sWeakSnackbar;

    private View                 view;
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
        this.view = parent;
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
     * Set the view to find a parent from.
     *
     * @param view The view to find a parent from.
     * @return the single {@link SnackbarUtils} instance
     */
    public static SnackbarUtils with(@NonNull final View view) {
        return new SnackbarUtils(view);
    }

    /**
     * Set the message.
     *
     * @param msg The message.
     * @return the single {@link SnackbarUtils} instance
     */
    public SnackbarUtils setMessage(@NonNull final CharSequence msg) {
        this.message = msg;
        return this;
    }

    /**
     * Set the color of message.
     *
     * @param color The color of message.
     * @return the single {@link SnackbarUtils} instance
     */
    public SnackbarUtils setMessageColor(@ColorInt final int color) {
        this.messageColor = color;
        return this;
    }

    /**
     * Set the color of background.
     *
     * @param color The color of background.
     * @return the single {@link SnackbarUtils} instance
     */
    public SnackbarUtils setBgColor(@ColorInt final int color) {
        this.bgColor = color;
        return this;
    }

    /**
     * Set the resource of background.
     *
     * @param bgResource The resource of background.
     * @return the single {@link SnackbarUtils} instance
     */
    public SnackbarUtils setBgResource(@DrawableRes final int bgResource) {
        this.bgResource = bgResource;
        return this;
    }

    /**
     * Set the duration.
     *
     * @param duration The duration.
     *                 <ul>
     *                 <li>{@link Duration#LENGTH_INDEFINITE}</li>
     *                 <li>{@link Duration#LENGTH_SHORT     }</li>
     *                 <li>{@link Duration#LENGTH_LONG      }</li>
     *                 </ul>
     * @return the single {@link SnackbarUtils} instance
     */
    public SnackbarUtils setDuration(@Duration final int duration) {
        this.duration = duration;
        return this;
    }

    /**
     * Set the action.
     *
     * @param text     The text.
     * @param listener The click listener.
     * @return the single {@link SnackbarUtils} instance
     */
    public SnackbarUtils setAction(@NonNull final CharSequence text,
                                   @NonNull final View.OnClickListener listener) {
        return setAction(text, COLOR_DEFAULT, listener);
    }

    /**
     * Set the action.
     *
     * @param text     The text.
     * @param color    The color of text.
     * @param listener The click listener.
     * @return the single {@link SnackbarUtils} instance
     */

    public SnackbarUtils setAction(@NonNull final CharSequence text,
                                   @ColorInt final int color,
                                   @NonNull final View.OnClickListener listener) {
        this.actionText = text;
        this.actionTextColor = color;
        this.actionListener = listener;
        return this;
    }

    /**
     * Set the bottom margin.
     *
     * @param bottomMargin The size of bottom margin, in pixel.
     */
    public SnackbarUtils setBottomMargin(@IntRange(from = 1) final int bottomMargin) {
        this.bottomMargin = bottomMargin;
        return this;
    }

    /**
     * Show the snackbar.
     */
    public Snackbar show() {
        return show(false);
    }

    /**
     * Show the snackbar.
     *
     * @param isShowTop True to show the snack bar on the top, false otherwise.
     */
    public Snackbar show(boolean isShowTop) {
        View view = this.view;
        if (view == null) return null;
        if (isShowTop) {
            ViewGroup suitableParent = findSuitableParentCopyFromSnackbar(view);
            View topSnackBarContainer = suitableParent.findViewWithTag("topSnackBarCoordinatorLayout");
            if (topSnackBarContainer == null) {
                CoordinatorLayout topSnackBarCoordinatorLayout = new CoordinatorLayout(view.getContext());
                topSnackBarCoordinatorLayout.setTag("topSnackBarCoordinatorLayout");
                topSnackBarCoordinatorLayout.setRotation(180);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // bring to front
                    topSnackBarCoordinatorLayout.setElevation(100);
                }
                suitableParent.addView(topSnackBarCoordinatorLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                topSnackBarContainer = topSnackBarCoordinatorLayout;
            }
            view = topSnackBarContainer;
        }
        if (messageColor != COLOR_DEFAULT) {
            SpannableString spannableString = new SpannableString(message);
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(messageColor);
            spannableString.setSpan(
                    colorSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
            sWeakSnackbar = new WeakReference<>(Snackbar.make(view, spannableString, duration));
        } else {
            sWeakSnackbar = new WeakReference<>(Snackbar.make(view, message, duration));
        }
        final Snackbar snackbar = sWeakSnackbar.get();
        final Snackbar.SnackbarLayout snackbarView = (Snackbar.SnackbarLayout) snackbar.getView();
        if (isShowTop) {
            for (int i = 0; i < snackbarView.getChildCount(); i++) {
                View child = snackbarView.getChildAt(i);
                child.setRotation(180);
            }
        }
        if (bgResource != -1) {
            snackbarView.setBackgroundResource(bgResource);
        } else if (bgColor != COLOR_DEFAULT) {
            snackbarView.setBackgroundColor(bgColor);
        }
        if (bottomMargin != 0) {
            ViewGroup.MarginLayoutParams params =
                    (ViewGroup.MarginLayoutParams) snackbarView.getLayoutParams();
            params.bottomMargin = bottomMargin;
        }
        if (actionText.length() > 0 && actionListener != null) {
            if (actionTextColor != COLOR_DEFAULT) {
                snackbar.setActionTextColor(actionTextColor);
            }
            snackbar.setAction(actionText, actionListener);
        }
        snackbar.show();
        return snackbar;
    }

    /**
     * Show the snackbar with success style.
     */
    public void showSuccess() {
        showSuccess(false);
    }

    /**
     * Show the snackbar with success style.
     *
     * @param isShowTop True to show the snack bar on the top, false otherwise.
     */
    public void showSuccess(boolean isShowTop) {
        bgColor = COLOR_SUCCESS;
        messageColor = COLOR_MESSAGE;
        actionTextColor = COLOR_MESSAGE;
        show(isShowTop);
    }

    /**
     * Show the snackbar with warning style.
     */
    public void showWarning() {
        showWarning(false);
    }

    /**
     * Show the snackbar with warning style.
     *
     * @param isShowTop True to show the snackbar on the top, false otherwise.
     */
    public void showWarning(boolean isShowTop) {
        bgColor = COLOR_WARNING;
        messageColor = COLOR_MESSAGE;
        actionTextColor = COLOR_MESSAGE;
        show(isShowTop);
    }

    /**
     * Show the snackbar with error style.
     */
    public void showError() {
        showError(false);
    }

    /**
     * Show the snackbar with error style.
     *
     * @param isShowTop True to show the snackbar on the top, false otherwise.
     */
    public void showError(boolean isShowTop) {
        bgColor = COLOR_ERROR;
        messageColor = COLOR_MESSAGE;
        actionTextColor = COLOR_MESSAGE;
        show(isShowTop);
    }

    /**
     * Dismiss the snackbar.
     */
    public static void dismiss() {
        if (sWeakSnackbar != null && sWeakSnackbar.get() != null) {
            sWeakSnackbar.get().dismiss();
            sWeakSnackbar = null;
        }
    }

    /**
     * Return the view of snackbar.
     *
     * @return the view of snackbar
     */
    public static View getView() {
        Snackbar snackbar = sWeakSnackbar.get();
        if (snackbar == null) return null;
        return snackbar.getView();
    }

    /**
     * Add view to the snackbar.
     * <p>Call it after {@link #show()}</p>
     *
     * @param layoutId The id of layout.
     * @param params   The params.
     */
    public static void addView(@LayoutRes final int layoutId,
                               @NonNull final ViewGroup.LayoutParams params) {
        final View view = getView();
        if (view != null) {
            view.setPadding(0, 0, 0, 0);
            Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) view;
            View child = LayoutInflater.from(view.getContext()).inflate(layoutId, null);
            layout.addView(child, -1, params);
        }
    }

    /**
     * Add view to the snackbar.
     * <p>Call it after {@link #show()}</p>
     *
     * @param child  The child view.
     * @param params The params.
     */
    public static void addView(@NonNull final View child,
                               @NonNull final ViewGroup.LayoutParams params) {
        final View view = getView();
        if (view != null) {
            view.setPadding(0, 0, 0, 0);
            Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) view;
            layout.addView(child, params);
        }
    }

    private static ViewGroup findSuitableParentCopyFromSnackbar(View view) {
        ViewGroup fallback = null;

        do {
            if (view instanceof CoordinatorLayout) {
                return (ViewGroup) view;
            }

            if (view instanceof FrameLayout) {
                if (view.getId() == android.R.id.content) {
                    return (ViewGroup) view;
                }

                fallback = (ViewGroup) view;
            }

            if (view != null) {
                ViewParent parent = view.getParent();
                view = parent instanceof View ? (View) parent : null;
            }
        } while (view != null);

        return fallback;
    }
}
