package com.blankj.utildebug.helper;

import android.annotation.SuppressLint;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/08/26
 *     desc  :
 * </pre>
 */
public class TouchHelper {

    private static final int MIN_DISTANCE_MOVE = SizeUtils.dp2px(4);
    private static final int MIN_TAP_TIME      = 1000;

    private static final int STATE_MOVE = 0;
    private static final int STATE_STOP = 1;

    private static int mState = STATE_STOP;
    private static int mLastX = -1;
    private static int mLastY = -1;

    private TouchHelper() {
    }

    public static void applyDrag(final View v, final OnDragListener listener) {
        if (v == null || listener == null) return;
        v.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return onDragEvent(v, event, listener);
            }
        });
    }

    private static boolean onDragEvent(View v, MotionEvent event, OnDragListener listener) {
        if (listener == null) return false;
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                listener._onDown(v, x, y, event);
                return false;
            case MotionEvent.ACTION_MOVE:
                if (mLastX == -1) {
                    mLastX = x;
                    mLastY = y;
                }
                if (mState != STATE_MOVE
                        && Math.abs(x - mLastX) < MIN_DISTANCE_MOVE
                        && Math.abs(y - mLastY) < MIN_DISTANCE_MOVE) {
                    return true;
                }
                listener._onMove(v, x, y, mLastX, mLastY, event);
                mLastX = x;
                mLastY = y;
                mState = STATE_MOVE;
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                listener._onStop(v, x, y, event);
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (mState != STATE_MOVE
                            && event.getEventTime() - event.getDownTime() < MIN_TAP_TIME) {
                        v.performClick();
                    }
                }
                final long now = SystemClock.uptimeMillis();
                final MotionEvent cancelEvent = MotionEvent.obtain(now, now,
                        MotionEvent.ACTION_CANCEL, 0.0f, 0.0f, 0);
                v.onTouchEvent(cancelEvent);
                mState = STATE_STOP;
                return false;
            default:
                break;
        }
        return false;
    }

    public static abstract class OnDragListener {

        public static final int DIRECTION_LEFT       = 1;
        public static final int DIRECTION_TOP        = 2;
        public static final int DIRECTION_RIGHT      = 4;
        public static final int DIRECTION_BOTTOM     = 8;
        public static final int DIRECTION_HORIZONTAL = DIRECTION_LEFT | DIRECTION_RIGHT;
        public static final int DIRECTION_VERTICAL   = DIRECTION_TOP | DIRECTION_BOTTOM;
        public static final int DIRECTION_ALL        = DIRECTION_LEFT | DIRECTION_TOP | DIRECTION_RIGHT | DIRECTION_BOTTOM;

        private   boolean isApplyScale;
        private   boolean isHorizontalSticky;
        private   int     availableDirection;
        private   boolean isFirstMove;
        private   boolean isMoveAvailable;
        protected int     appWidth;
        protected int     appHeight;
        protected int     viewWidth;
        protected int     viewHeight;
        protected int     statusBarHeight;

        public OnDragListener() {
            this(DIRECTION_ALL, false, false);
        }

        public OnDragListener(boolean isApplyScale) {
            this(DIRECTION_ALL, isApplyScale, false);
        }

        public OnDragListener(boolean isApplyScale, boolean isHorizontalSticky) {
            this(DIRECTION_ALL, isApplyScale, isHorizontalSticky);
        }

        public OnDragListener(int availableDirection, boolean isApplyScale, boolean isHorizontalSticky) {
            this.availableDirection = availableDirection;
            this.isApplyScale = isApplyScale;
            this.isHorizontalSticky = isHorizontalSticky;
        }

        public abstract void onDown(View v, int x, int y, MotionEvent event);

        public abstract void onMove(View view, int x, int y, int dx, int dy, MotionEvent event);

        public abstract void onStop(View view, int x, int y, MotionEvent event);

        private void _onDown(View view, int x, int y, MotionEvent event) {
            isFirstMove = false;
            viewWidth = view.getWidth();
            viewHeight = view.getHeight();
            appWidth = ScreenUtils.getAppScreenWidth();
            appHeight = ScreenUtils.getAppScreenHeight();
            statusBarHeight = BarUtils.getStatusBarHeight();
            if (isApplyScale) {
                processScale(view, true);
            }
            onDown(view, x, y, event);
        }

        private void _onMove(View view, int x, int y, int lastX, int lastY, MotionEvent event) {
            if (!isFirstMove) {
                if (availableDirection == DIRECTION_ALL) {
                    isMoveAvailable = true;
                } else {
                    if (Math.abs(x - lastX) >= Math.abs(y - lastY)) {
                        int direction = availableDirection & DIRECTION_HORIZONTAL;
                        if (direction == DIRECTION_HORIZONTAL) {
                            isMoveAvailable = true;
                        } else if (direction == DIRECTION_LEFT && x - lastX < 0) {
                            isMoveAvailable = true;
                        } else if (direction == DIRECTION_RIGHT && x - lastX > 0) {
                            isMoveAvailable = true;
                        }
                    } else {
                        int direction = availableDirection & DIRECTION_VERTICAL;
                        if (direction == DIRECTION_VERTICAL) {
                            isMoveAvailable = true;
                        } else if (direction == DIRECTION_TOP && y - lastY < 0) {
                            isMoveAvailable = true;
                        } else if (direction == DIRECTION_BOTTOM && y - lastY > 0) {
                            isMoveAvailable = true;
                        }
                    }
                }
                isFirstMove = true;
            }
            if (isMoveAvailable) {
                onMove(view, x, y, x - lastX, y - lastY, event);
            }
        }

        private void _onStop(View view, int x, int y, MotionEvent event) {
            if (isHorizontalSticky) {
                float middleX = view.getX() + viewWidth / 2f;
                if (middleX > appWidth / 2f) {
                    view.animate()
                            .setInterpolator(new DecelerateInterpolator())
                            .translationX(appWidth - viewWidth)
                            .setDuration(100)
                            .start();
                } else {
                    view.animate()
                            .setInterpolator(new DecelerateInterpolator())
                            .setDuration(100)
                            .translationX(0)
                            .start();
                }
            }

            if (isApplyScale) {
                processScale(view, false);
            }
            onStop(view, x, y, event);
        }

        private void processScale(final View view, boolean isDown) {
            float value = isDown ? 1 - 0.1f : 1;
            view.animate()
                    .scaleX(value)
                    .scaleY(value)
                    .setDuration(100)
                    .start();
        }
    }

    public interface OnMoveListener {
        boolean onMove(int dx, int dy);
    }
}
