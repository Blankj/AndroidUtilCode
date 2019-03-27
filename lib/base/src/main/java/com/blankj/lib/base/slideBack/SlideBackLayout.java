package com.blankj.lib.base.slideBack;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.widget.FrameLayout;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/03/20
 *     desc  :
 * </pre>
 */
public class SlideBackLayout extends FrameLayout {

    private static final String TAG = "SlideBackLayout";

    private       float               mStartX;
    private       float               mStartY;
    private       float               mCurrentX;
    private       boolean             mHasJudged          = false;
    private       float               progress;
    private       Paint               mPaint;
    private       Paint               mPaintWhite;
    private       Path                mPath;
    private       int                 mHalfScreenWidth;
    private       boolean             mDrawBack           = false;
    private       boolean             mIsAccept           = false;
    private       boolean             mIsLeftStart        = false;
    private       boolean             mIsRightStart       = false;
    private       OnSwipeBackListener mSwipeBackListener;
    private       boolean             mIsRightSwipeEnable = true;
    private       boolean             mIsLeftSwipeEnable  = true;
    private final float               mSwipeMaximum       = 0.6665f;
    private       Context             mContext;
    private       int                 mStartMarginDP      = 100;//dp
    private       int                 mAmplitudeDP        = 90;

    private float mAmplitude;
    private float mHeight;
    private float mSineWidth;
    private float mSineIndex;
    private float mSineTheta;
    private float valueSineStart;
    private float sineLineStartY;

    public SlideBackLayout(@NonNull Context context) {
        this(context, null);
    }

    public SlideBackLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideBackLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initDrawTool();
    }

    private void initDrawTool() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(8);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaintWhite = new Paint();
        mPaintWhite.setColor(Color.WHITE);
        mPaintWhite.setStrokeWidth(4);
        mPaintWhite.setAntiAlias(true);
        mPaintWhite.setStrokeCap(Paint.Cap.ROUND);
        mPath = new Path();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mHasJudged = false;
                mStartX = ev.getX();
                mStartY = ev.getY();
                mIsLeftStart = isLeftStart(mStartX);
                mIsRightStart = isRightStart(mStartX);
                return true;
            }
        }
        if (mIsLeftStart || mIsRightStart) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_MOVE: {
                    mCurrentX = ev.getX();
                    if (!mHasJudged) {
                        float distanceX = Math.abs(mCurrentX - mStartX);
                        if (distanceX > 30) {
//                        allowParentsInterceptTouchEvent(getParent());
                            mHasJudged = true;
                        }
                    }
                    // 大于30开始画图
                    if (mHasJudged) {
                        cancelChildViewTouch();
                        postInvalidateDelayed(0);
                    }
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    progress = calculateProgress();
                    if (progress > 0.9 * 0.665 && (mIsLeftStart || mIsRightStart)) {
                        Log.d(TAG, "onTouchEvent: action up");
                        mIsAccept = true;
                        mSwipeBackListener.completeSwipeBack();
                    }
                    mDrawBack = true;
                    postInvalidateDelayed(0);
                    break;
                }
            }
        }
        if (mHasJudged) {
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    void cancelChildViewTouch() {
        // Cancel child touches
        final long now = SystemClock.uptimeMillis();
        final MotionEvent cancelEvent = MotionEvent.obtain(now, now,
                MotionEvent.ACTION_CANCEL, 0.0f, 0.0f, 0);
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).dispatchTouchEvent(cancelEvent);
        }
        cancelEvent.recycle();
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return mIsLeftStart || mIsRightStart || super.onInterceptTouchEvent(ev);
//    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
////        disallowParentsInterceptTouchEvent(getParent());
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_MOVE: {
//                mCurrentX = event.getX();
//                if (!mHasJudged) {
//                    float distanceX = Math.abs(mCurrentX - mStartX);
//                    if (distanceX > 30) {
////                        allowParentsInterceptTouchEvent(getParent());
//                        mHasJudged = true;
//                    }
//                }
//                // 大于30开始画图
//                if (mHasJudged) {
//                    postInvalidateDelayed(0);
//                }
//                break;
//            }
//            case MotionEvent.ACTION_UP: {
//                progress = calculateProgress();
//                if (progress > 0.9 * 0.665 && (mIsLeftStart || mIsRightStart)) {
//                    Log.d(TAG, "onTouchEvent: action up");
//                    mIsAccept = true;
//                    mSwipeBackListener.completeSwipeBack();
//                }
//                mDrawBack = true;
//                postInvalidateDelayed(0);
//                break;
//            }
//        }
//        return true;
//    }


    private boolean isLeftStart(float startX) {
        return mIsLeftSwipeEnable && startX < dip2px(mStartMarginDP);
    }

    private boolean isRightStart(float startX) {
        return mIsRightSwipeEnable && startX > getWidth() - dip2px(mStartMarginDP);
    }

    private void disallowParentsInterceptTouchEvent(ViewParent parent) {
        if (parent == null) {
            return;
        }
        parent.requestDisallowInterceptTouchEvent(true);
        disallowParentsInterceptTouchEvent(parent.getParent());
    }

    private void allowParentsInterceptTouchEvent(ViewParent parent) {
        if (parent == null) {
            return;
        }
        parent.requestDisallowInterceptTouchEvent(false);
        allowParentsInterceptTouchEvent(parent.getParent());
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawSinLine(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHalfScreenWidth = getMeasuredWidth() / 2;
    }

    private void drawSinLine(Canvas canvas) {
        if (!mIsRightStart && !mIsLeftStart) {
            resetAll();
            return;
        }
        if (!mDrawBack) {
            progress = calculateProgress();
        } else if (mIsAccept) {
            resetAll();
        } else {
            progress -= 0.1;
            if (progress < 0) {
                resetAll();
            }
        }
        int initAmplitude = dip2px(12);
        mAmplitude = initAmplitude * progress * 1.5f;
        mHeight = initAmplitude * 2 * progress * 1.5f;
        mSineWidth = dip2px(135);
        mSineIndex = 0;
        mSineTheta = 30;
        valueSineStart = 0;
        sineLineStartY = mStartY - 1.9f / 3f * mSineWidth;
        if (mIsRightStart) {
            valueSineStart += getWidth();
        }
        mPath.reset();
        mPath.moveTo(valueSineStart, sineLineStartY);
        float valueSine;
        while (mSineIndex <= mSineWidth * 4 / 3) {
            valueSine = (float) (Math.sin(mSineIndex / mSineWidth * 1.5f * Math.PI + mSineTheta)
                    * mAmplitude + mHeight - mAmplitude);
            if (mIsRightStart) {
                valueSine *= -1;
                valueSine += getWidth();
            }
            mPath.lineTo(valueSine, sineLineStartY + mSineIndex);
            mSineIndex++;
        }
        mPath.lineTo(valueSineStart, sineLineStartY);
        mPath.close();
        mPaint.setAlpha((int) (190 * progress * 1.5f));
        canvas.drawPath(mPath, mPaint);

        float midBackX = mAmplitude * 1.25f;
        float midBackY = mStartY;

        mPaintWhite.setAlpha((int) (255 * progress * 1.5f));
        float lineLength = dip2px(5) * progress * 1.5f;

        if (mIsRightStart) {
            midBackX *= -1;
            midBackX += getWidth() + lineLength;
            canvas.drawLine(midBackX - lineLength, midBackY, midBackX, midBackY - lineLength, mPaintWhite);
            canvas.drawLine(midBackX - lineLength, midBackY, midBackX, midBackY + lineLength, mPaintWhite);
        } else {
            canvas.drawLine(midBackX - lineLength, midBackY, midBackX, midBackY + lineLength, mPaintWhite);
            canvas.drawLine(midBackX - lineLength, midBackY, midBackX, midBackY - lineLength, mPaintWhite);
        }

        // 慢回弹
        if (mDrawBack) {
            postInvalidateDelayed(0);
        }
    }

    private void resetAll() {
        mCurrentX = 0;
        mHasJudged = false;
        mStartY = 0;
        mStartX = 0;
        mDrawBack = false;
        progress = 0;
        allowParentsInterceptTouchEvent(getParent());
    }

    private float calculateProgress() {
        float distance = Math.abs(mCurrentX - mStartX);
        if (distance > mHalfScreenWidth) {
            return mSwipeMaximum;
        }
        float temp = distance / mHalfScreenWidth;
        if (temp > mSwipeMaximum) {
            return mSwipeMaximum;
        }
        return temp;
    }

    private int dip2px(float dipValue) {
        float scale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (dipValue * scale + 0.5f);
    }

    public void setRightSwipeEnable(boolean enable) {
        this.mIsRightSwipeEnable = enable;
    }

    public void setLeftSwipeEnable(boolean enable) {
        this.mIsLeftSwipeEnable = enable;
    }

    public void setSwipeBackListener(OnSwipeBackListener onSwipeBackListener) {
        this.mSwipeBackListener = onSwipeBackListener;
    }

    public interface OnSwipeBackListener {
        void completeSwipeBack();
    }
}
