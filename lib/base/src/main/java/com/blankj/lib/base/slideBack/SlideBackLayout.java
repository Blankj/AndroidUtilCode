package com.blankj.lib.base.slideBack;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.Scroller;

import com.blankj.utilcode.util.LogUtils;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/03/20
 *     desc  :
 * </pre>
 */
public class SlideBackLayout extends FrameLayout {

    private View mParentView;
    private int  mEdgeSlop;
    private int  mTouchSlop;

    private boolean isConsumeDown;

    private float mDownX;
    private float mDownY;
    private float mTempX;

    private Scroller mScroller;

    private boolean isFinish = false;

    private boolean isSliding = false;

    private int mViewWidth;

    private SlideListener mListener = new SlideListener() {
        @Override
        public void onStart() {
            LogUtils.e("start: ");
        }

        @Override
        public void onChange(float x) {
            LogUtils.e("onChange: " + x);
        }

        @Override
        public void onFinish() {
            LogUtils.e("onFinish: ");
        }
    };

    private VelocityTracker mVelocityTracker;

    public SlideBackLayout(@NonNull Context context) {
        this(context, null);
    }

    public SlideBackLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mEdgeSlop = ViewConfiguration.get(context).getScaledEdgeSlop();
        mScroller = new Scroller(context);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            mParentView = (View) getParent();
            mViewWidth = getWidth();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (super.dispatchTouchEvent(event)) {
                    isConsumeDown = true;
                } else {
                    mDownX = mTempX = event.getRawX();
                    mDownY = event.getRawY();
                }
                isSliding = false;
                return true;
            case MotionEvent.ACTION_MOVE:
                if (!isConsumeDown) {
                    LogUtils.e();
                    if (isSliding) break;
                    if (event.getRawX() - mDownX > mTouchSlop) {
                        isSliding = true;
                        if (mListener != null) {
                            mListener.onStart();
                        }
                        return true;
                    }
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.e(event);
        if (isConsumeDown || !isSliding) return super.onTouchEvent(event);
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
        final int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getRawX();
                if (moveX > mDownX) {
                    mParentView.scrollBy((int) (mTempX - moveX), 0);
                } else if (moveX < mDownX) {
                    //解决连续滑动Activity无法闭合的问题
                    mParentView.scrollTo(0, 0);
                }
                if (mListener != null) {
                    mListener.onChange(mTempX - moveX);
                }
                mTempX = moveX;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                startScroll();
                break;
        }
        return true;
    }

    private void startScroll() {
        final VelocityTracker velocityTracker = mVelocityTracker;
        velocityTracker.computeCurrentVelocity(1000, ViewConfiguration.get(getContext()).getScaledMaximumFlingVelocity());
        int xVelocity = (int) velocityTracker.getXVelocity();
        LogUtils.e(xVelocity);

        if (mParentView.getScrollX() <= -mViewWidth / 2) {
            isFinish = true;
            scrollToRight();
        } else {
            isFinish = false;
            scrollToOrigin();
        }

        if (mListener != null) {
            mListener.onFinish();
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            mParentView.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
            if (mScroller.isFinished() && isFinish && mListener != null) {
                mListener.onFinish();
            }
        }
    }

    private void scrollToOrigin() {
        final int delta = mParentView.getScrollX();
        mScroller.startScroll(mParentView.getScrollX(), 0, -delta, 0, Math.abs(delta));
        postInvalidate();
    }

    private void scrollToRight() {
        final int delta = mViewWidth + mParentView.getScrollX();
        mScroller.startScroll(mParentView.getScrollX(), 0, -delta + 1, 0, Math.abs(delta));
        postInvalidate();
    }

    public interface SlideListener {
        void onStart();

        void onChange(float x);

        void onFinish();
    }
}
