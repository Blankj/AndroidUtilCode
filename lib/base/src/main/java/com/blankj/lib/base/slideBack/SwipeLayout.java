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
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/03/22
 *     desc  :
 * </pre>
 */
public class SwipeLayout extends FrameLayout {

    private static final int DIRECTION_LEFT  = 1;
    private static final int DIRECTION_UP    = 2;
    private static final int DIRECTION_RIGHT = 4;
    private static final int DIRECTION_DOWN  = 8;

    private int mEdgeSlop;
    private int mTouchSlop;

    private float mDownX;
    private float mDownY;
    private float mCurrentX;
    private float mCurrentY;

    private boolean mIsLeftStart;
    private boolean mIsUpStart;
    private boolean mIsRightStart;
    private boolean mIsDownStart;
    private boolean mIsEdgeStart;

    private int mMovedDirection;

    private boolean mIsLeftSwipeEnable  = true;
    private boolean mIsRightSwipeEnable = true;
    private boolean mIsUpSwipeEnable    = true;
    private boolean mIsDownSwipeEnable  = true;

    private Paint mPaint;
    private Paint mPaintWhite;
    private Path  mPath;

    private float progress;
    private float preProgress;

    private boolean mAvailable;
    private boolean mDrawBack;

    private int mWidth;
    private int mHeight;

    private float mAlphaLimit = 0.75f;
    private float mStartSpeed;
    private int   mLimit;

    private static final float HALF_SIZE = 400;

    private OnSwipeListener mListener;

    public SwipeLayout(@NonNull Context context) {
        super(context);
    }

    public SwipeLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(final Context context) {
        mEdgeSlop = 2000;
//        mEdgeSlop = ViewConfiguration.get(context).getScaledEdgeSlop();
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(0x90000000);
        mPaint.setStyle(Paint.Style.FILL);

        mPaintWhite = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaintWhite.setColor(Color.WHITE);
        mPaintWhite.setStrokeWidth(4);
        mPaintWhite.setStrokeCap(Paint.Cap.ROUND);
        mPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mLimit = Math.min(mWidth, mHeight) / 3;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawSinLine(canvas);
    }

    private void drawSinLine(Canvas canvas) {
        if (!mIsEdgeStart) return;
        if (mDrawBack) {
            if (progress <= 0) return;
            progress -= mStartSpeed;
            mStartSpeed += 0.01;
            postInvalidateDelayed(0);
        }
        mPath.reset();
        if (mMovedDirection == DIRECTION_RIGHT) {
            float p = 5;
            mPath.moveTo(0, mDownY - HALF_SIZE);
            float preX = 0;
            float preY = mDownY - HALF_SIZE;
            float curX = preX;
            float curY = preY + HALF_SIZE / p;
            mPath.quadTo(preX, preY, (preX + curX) / 2, (preY + curY) / 2);

            preX = curX;
            preY = curY;
            curX = preX + HALF_SIZE / p;
            curY = preY + HALF_SIZE / p;
            mPath.quadTo(preX, preY, (preX + curX) / 2, (preY + curY) / 2);
            preX = curX;
            preY = curY;
            curX = HALF_SIZE * 2 / 3;
            curY = mDownY;
            mPath.quadTo(preX, preY, (preX + curX) / 2, (preY + curY) / 2);
            preX = curX;
            preY = curY;
            curX = HALF_SIZE / p;
            curY = mDownY + HALF_SIZE - HALF_SIZE / p;
            mPath.quadTo(preX, preY, (preX + curX) / 2, (preY + curY) / 2);
            preX = curX;
            preY = curY;
            curX = 0;
            curY = mDownY + HALF_SIZE;
            mPath.quadTo(preX, preY, (preX + curX) / 2, (preY + curY) / 2);
            preX = curX;
            preY = curY;
            curX = 0;
            curY = mDownY + HALF_SIZE;
            mPath.quadTo(preX, preY, (preX + curX) / 2, (preY + curY) / 2);



            mPath.close();
            canvas.drawPath(mPath, mPaint);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            mIsLeftStart = mIsLeftSwipeEnable && mDownX <= mEdgeSlop;
            mIsUpStart = mIsUpSwipeEnable && mDownY <= mEdgeSlop;
            mIsRightStart = mIsRightSwipeEnable && mDownX >= getWidth() - mEdgeSlop;
            mIsDownStart = mIsDownSwipeEnable && mDownY >= getHeight() - mEdgeSlop;
            mIsEdgeStart = mIsLeftStart || mIsUpStart || mIsRightStart || mIsDownStart;
            if (mIsEdgeStart) {
                mDownX = ev.getX();
                mDownY = ev.getY();
                mMovedDirection = 0;
                progress = preProgress = -1;

                mDrawBack = false;
                mAvailable = false;
                mStartSpeed = 0.01f;
            }
            return true;
        }
        if (mIsEdgeStart) {
            if (action == MotionEvent.ACTION_MOVE) {
                mCurrentX = ev.getX();
                mCurrentY = ev.getY();
                if (mMovedDirection == 0) {
                    float deltaX = mCurrentX - mDownX;
                    float deltaY = mCurrentY - mDownY;
                    float disX = Math.abs(deltaX);
                    float disY = Math.abs(deltaY);
                    if (disX > mTouchSlop || disY > mTouchSlop) {
                        if (disX >= disY) {
                            if (mIsLeftStart && deltaX > 0) {
                                decideDirection(DIRECTION_RIGHT);
                            } else if (mIsRightStart && deltaX < 0) {
                                decideDirection(DIRECTION_LEFT);
                            }
                        } else {
                            if (mIsUpStart && deltaY > 0) {
                                decideDirection(DIRECTION_DOWN);
                            } else if (mIsDownStart && deltaY < 0) {
                                decideDirection(DIRECTION_UP);
                            }
                        }
                    }
                }
                if (mMovedDirection != 0) {
                    progress = calculateProgress();
                    if (Math.abs(preProgress - progress) > 0.01) {
                        preProgress = progress;
                        postInvalidate();
                    }
                }
            } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                mCurrentX = ev.getX();
                mCurrentY = ev.getY();
                progress = calculateProgress();
                mDrawBack = true;
                if (progress >= 0.95) {
                    mAvailable = true;
                    if (mListener != null) {
                        mListener.onSwipe();
                    }
                }
                postInvalidate();
            }
        }
        return true;
    }

    private void decideDirection(int direction) {
        if (direction == DIRECTION_RIGHT || direction == DIRECTION_LEFT) {
            if (mDownY < HALF_SIZE) {
                mDownY = HALF_SIZE;
            } else if (mDownY >= mHeight - HALF_SIZE) {
                mDownY = mHeight - HALF_SIZE;
            }
        } else {
            if (mDownX < HALF_SIZE) {
                mDownX = HALF_SIZE;
            } else if (mDownX >= mWidth - HALF_SIZE) {
                mDownX = mWidth - HALF_SIZE;
            }
        }
        mMovedDirection = direction;
        cancelChildViewTouch();
        requestDisallowInterceptTouchEvent(true);
    }

    private float calculateProgress() {
        if (mMovedDirection == DIRECTION_RIGHT) {
            float deltaX = mCurrentX - mDownX;
            if (deltaX <= 0) return 0;
            return Math.min(deltaX / mLimit, 1);
        } else if (mMovedDirection == DIRECTION_DOWN) {
            float deltaY = mCurrentY - mDownY;
            if (deltaY <= 0) return 0;
            return Math.min(deltaY / mLimit, 1);
        } else if (mMovedDirection == DIRECTION_LEFT) {
            float deltaX = mCurrentX - mDownX;
            if (deltaX >= 0) return 0;
            return Math.min(-deltaX / mLimit, 1);
        } else {
            float deltaY = mCurrentY - mDownY;
            if (deltaY >= 0) return 0;
            return Math.min(-deltaY / mLimit, 1);
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

    public interface OnSwipeListener {
        void onSwipe();
    }
}
