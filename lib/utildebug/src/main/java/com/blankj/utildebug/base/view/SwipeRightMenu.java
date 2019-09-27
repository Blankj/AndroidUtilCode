package com.blankj.utildebug.base.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SizeUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/09/10
 *     desc  :
 * </pre>
 */
public class SwipeRightMenu extends LinearLayout {

    private static final AccelerateInterpolator OPEN_INTERPOLATOR  = new AccelerateInterpolator(0.5f);
    private static final DecelerateInterpolator CLOSE_INTERPOLATOR = new DecelerateInterpolator(0.5f);

    private static boolean                       isTouching;
    private static WeakReference<SwipeRightMenu> swipeMenuOpened;

    private View mContentView;

    private List<MenuBean> mMenus = new ArrayList<>();

    private int mMenusWidth = 0;

    public SwipeRightMenu(Context context) {
        this(context, null);
    }

    public SwipeRightMenu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
        post(new Runnable() {
            @Override
            public void run() {
                initView();
            }
        });
    }

    private void initView() {
        int childCount = getChildCount();
        if (childCount <= 1) {
            throw new IllegalArgumentException("no menus");
        }
        mContentView = getChildAt(0);

        for (int i = 1; i < childCount; i++) {
            MenuBean bean = new MenuBean(getChildAt(i));
            mMenus.add(bean);
            if (i == 1) {
                bean.setCloseMargin(0);
            } else {
                bean.setCloseMargin(-mMenus.get(i - 2).getWidth());
            }
            mMenusWidth += bean.getWidth();
        }
        for (int i = 0; i < mMenus.size(); i++) {
            mMenus.get(i).setOpenMargin(0);
        }
    }

    private static final int STATE_DOWN = 0;
    private static final int STATE_MOVE = 1;
    private static final int STATE_STOP = 2;

    private static final int SLIDE_INIT       = 0;
    private static final int SLIDE_HORIZONTAL = 1;
    private static final int SLIDE_VERTICAL   = 2;

    private static final int MIN_DISTANCE_MOVE  = SizeUtils.dp2px(4);
    private static final int THRESHOLD_DISTANCE = SizeUtils.dp2px(20);
    private static final int ANIM_TIMING        = 350;

    private int mState;
    private int mDownX;
    private int mDownY;
    private int mLastX;
    private int mLastY;
    private int slideDirection;

    private boolean isTouchPointInView(View view, int x, int y) {
        if (view == null) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        return y >= top && y <= bottom && x >= left && x <= right;
    }

    public boolean isOpen() {
        return swipeMenuOpened != null;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isTouching) {
                    return false;
                }
                isTouching = true;
                close(this);
                mDownX = x;
                mDownY = y;
                mLastX = x;
                mLastY = y;
                mState = STATE_DOWN;
                slideDirection = SLIDE_INIT;
                super.dispatchTouchEvent(event);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mState == STATE_DOWN
                        && Math.abs(x - mDownX) < MIN_DISTANCE_MOVE
                        && Math.abs(y - mDownY) < MIN_DISTANCE_MOVE) {
                    break;
                }
                if (slideDirection == SLIDE_INIT) {
                    if (Math.abs(x - mDownX) > Math.abs(y - mDownY)) {
                        slideDirection = SLIDE_HORIZONTAL;
                        cancelChildViewTouch();
                        requestDisallowInterceptTouchEvent(true);// 让父 view 不要拦截
                    } else {
                        slideDirection = SLIDE_VERTICAL;
                    }
                }
                if (slideDirection == SLIDE_VERTICAL) {
                    return super.dispatchTouchEvent(event);
                }

                scrollTo(Math.max(Math.min(getScrollX() - x + mLastX, mMenusWidth), 0), 0);

                float percent = getScrollX() / (float) mMenusWidth;
                updateLeftMarginByPercent(percent);

                mLastX = x;
                mLastY = y;
                mState = STATE_MOVE;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                try {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (mState == STATE_DOWN) {
                            if (isOpen()) {
                                if (isTouchPointInView(mContentView, x, y)) {
                                    close(true);
                                    final long now = SystemClock.uptimeMillis();
                                    final MotionEvent cancelEvent = MotionEvent.obtain(now, now,
                                            MotionEvent.ACTION_CANCEL, 0.0f, 0.0f, 0);
                                    super.dispatchTouchEvent(cancelEvent);
                                    return true;
                                }
                            }
                            super.dispatchTouchEvent(event);
                            close(true);
                            return true;
                        }
                    } else {
                        super.dispatchTouchEvent(event);
                    }
                    if (swipeMenuOpened != null && swipeMenuOpened.get() == this) {// 如果之前是展开状态
                        if (getScrollX() < mMenusWidth - THRESHOLD_DISTANCE) {// 超过阈值则关闭
                            close(true);
                        } else {// 否则还是打开
                            open(true);
                        }
                    } else {
                        if (getScrollX() > THRESHOLD_DISTANCE) {// 如果是关闭
                            open(true);// 超过阈值则打开
                        } else {
                            close(true);// 否则还是关闭
                        }
                    }
                } finally {
                    isTouching = false;
                    mState = STATE_STOP;
                }
                break;
            default:
                break;
        }
        return true;
    }

    private void updateLeftMarginByPercent(float percent) {
        for (MenuBean menu : mMenus) {
            menu.getParams().leftMargin = (int) (menu.getCloseMargin() + percent * (menu.getOpenMargin() - menu.getCloseMargin()));
            menu.getView().requestLayout();
        }
    }

    private void close(boolean isAnim) {
        swipeMenuOpened = null;
        if (isAnim) {
            ValueAnimator anim = ValueAnimator.ofInt(getScrollX(), 0);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    scrollTo((int) animation.getAnimatedValue(), 0);
                }
            });
            anim.setInterpolator(CLOSE_INTERPOLATOR);
            anim.setDuration(ANIM_TIMING).start();

            for (final MenuBean menu : mMenus) {
                ValueAnimator menuAnim = ValueAnimator.ofInt(menu.getParams().leftMargin, menu.getCloseMargin());
                menuAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        menu.getParams().leftMargin = (int) animation.getAnimatedValue();
                        menu.getView().requestLayout();
                    }
                });
                menuAnim.setInterpolator(CLOSE_INTERPOLATOR);
                menuAnim.setDuration(ANIM_TIMING).start();
            }
        } else {
            scrollTo(0, 0);

            for (final MenuBean menu : mMenus) {
                menu.getParams().leftMargin = menu.getCloseMargin();
                menu.getView().requestLayout();
            }
        }
    }

    private void open(boolean isAnim) {
        swipeMenuOpened = new WeakReference<>(this);
        if (isAnim) {
            ValueAnimator anim = ValueAnimator.ofInt(getScrollX(), mMenusWidth);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    scrollTo((int) animation.getAnimatedValue(), 0);
                }
            });
            anim.setInterpolator(OPEN_INTERPOLATOR);
            anim.setDuration(ANIM_TIMING).start();

            for (final MenuBean menu : mMenus) {
                ValueAnimator menuAnim = ValueAnimator.ofInt(menu.getParams().leftMargin, menu.getOpenMargin());
                menuAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        menu.getParams().leftMargin = (int) animation.getAnimatedValue();
                        menu.getView().requestLayout();
                    }
                });
                menuAnim.setInterpolator(OPEN_INTERPOLATOR);
                menuAnim.setDuration(ANIM_TIMING).start();
            }
        } else {
            scrollTo(mMenusWidth, 0);

            for (final MenuBean menu : mMenus) {
                menu.getParams().leftMargin = menu.getOpenMargin();
                menu.getView().requestLayout();
            }
        }
    }

    public void close(SwipeRightMenu exclude) {
        if (swipeMenuOpened != null) {
            final SwipeRightMenu swipeMenu = swipeMenuOpened.get();
            if (swipeMenu != exclude) {
                swipeMenu.close(true);
            }
        }
    }

    private void cancelChildViewTouch() {
        final long now = SystemClock.uptimeMillis();
        final MotionEvent cancelEvent = MotionEvent.obtain(now, now,
                MotionEvent.ACTION_CANCEL, 0.0f, 0.0f, 0);
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).dispatchTouchEvent(cancelEvent);
        }
        cancelEvent.recycle();
    }

    private static class MenuBean {
        private View                      mView;
        private LinearLayout.LayoutParams mParams;
        private int                       mWidth;
        private int                       mCloseMargin;
        private int                       mOpenMargin;

        public MenuBean(View view) {
            mView = view;
            mParams = (LayoutParams) view.getLayoutParams();
            mWidth = view.getWidth();
        }

        public View getView() {
            return mView;
        }

        public LayoutParams getParams() {
            return mParams;
        }

        public int getWidth() {
            return mWidth;
        }

        public int getCloseMargin() {
            return mCloseMargin;
        }

        public int getOpenMargin() {
            return mOpenMargin;
        }

        public void setCloseMargin(int closeMargin) {
            mCloseMargin = closeMargin;
        }

        public void setOpenMargin(int openMargin) {
            mOpenMargin = openMargin;
        }
    }
}