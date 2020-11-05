package com.blankj.utilcode.util;

import android.support.annotation.IntDef;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2019/08/26
 *     desc  : utils about touch
 * </pre>
 */
public class TouchUtils {

    public static final int UNKNOWN = 0;
    public static final int LEFT    = 1;
    public static final int UP      = 2;
    public static final int RIGHT   = 4;
    public static final int DOWN    = 8;

    @IntDef({LEFT, UP, RIGHT, DOWN})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Direction {
    }

    private TouchUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void setOnTouchListener(final View v, final OnTouchUtilsListener listener) {
        if (v == null || listener == null) {
            return;
        }
        v.setOnTouchListener(listener);
    }

    public static abstract class OnTouchUtilsListener implements View.OnTouchListener {

        private static final int STATE_DOWN = 0;
        private static final int STATE_MOVE = 1;
        private static final int STATE_STOP = 2;

        private static final int MIN_TAP_TIME = 1000;

        private int touchSlop;
        private int downX, downY, lastX, lastY;
        private int             state;
        private int             direction;
        private VelocityTracker velocityTracker;
        private int             maximumFlingVelocity;
        private int             minimumFlingVelocity;

        public OnTouchUtilsListener() {
            resetTouch(-1, -1);
        }

        private void resetTouch(int x, int y) {
            downX = x;
            downY = y;
            lastX = x;
            lastY = y;
            state = STATE_DOWN;
            direction = UNKNOWN;
            if (velocityTracker != null) {
                velocityTracker.clear();
            }
        }

        public abstract boolean onDown(View view, int x, int y, MotionEvent event);

        public abstract boolean onMove(View view,
                                       @Direction int direction,
                                       int x,
                                       int y,
                                       int dx,
                                       int dy,
                                       int totalX,
                                       int totalY,
                                       MotionEvent event);

        public abstract boolean onStop(View view,
                                       @Direction int direction,
                                       int x,
                                       int y,
                                       int totalX,
                                       int totalY,
                                       int vx,
                                       int vy,
                                       MotionEvent event);

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (touchSlop == 0) {
                touchSlop = ViewConfiguration.get(v.getContext()).getScaledTouchSlop();
            }
            if (maximumFlingVelocity == 0) {
                maximumFlingVelocity =
                        ViewConfiguration.get(v.getContext()).getScaledMaximumFlingVelocity();
            }
            if (minimumFlingVelocity == 0) {
                minimumFlingVelocity =
                        ViewConfiguration.get(v.getContext()).getScaledMinimumFlingVelocity();
            }
            if (velocityTracker == null) {
                velocityTracker = VelocityTracker.obtain();
            }
            velocityTracker.addMovement(event);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    return onUtilsDown(v, event);
                case MotionEvent.ACTION_MOVE:
                    return onUtilsMove(v, event);
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    return onUtilsStop(v, event);
                default:
                    break;
            }
            return false;
        }

        public boolean onUtilsDown(View view, MotionEvent event) {
            int x = (int) event.getRawX();
            int y = (int) event.getRawY();

            resetTouch(x, y);
            view.setPressed(true);
            return onDown(view, x, y, event);
        }

        public boolean onUtilsMove(View view, MotionEvent event) {
            int x = (int) event.getRawX();
            int y = (int) event.getRawY();

            if (downX == -1) {
                // not receive down should reset
                resetTouch(x, y);
                view.setPressed(true);
            }

            if (state != STATE_MOVE) {
                if (Math.abs(x - lastX) < touchSlop && Math.abs(y - lastY) < touchSlop) {
                    return true;
                }
                state = STATE_MOVE;
                if (Math.abs(x - lastX) >= Math.abs(y - lastY)) {
                    if (x - lastX < 0) {
                        direction = LEFT;
                    } else {
                        direction = RIGHT;
                    }
                } else {
                    if (y - lastY < 0) {
                        direction = UP;
                    } else {
                        direction = DOWN;
                    }
                }
            }

            boolean consumeMove =
                    onMove(view, direction, x, y, x - lastX, y - lastY, x - downX, y - downY, event);
            lastX = x;
            lastY = y;
            return consumeMove;
        }

        public boolean onUtilsStop(View view, MotionEvent event) {
            int x = (int) event.getRawX();
            int y = (int) event.getRawY();

            int vx = 0, vy = 0;

            if (velocityTracker != null) {
                velocityTracker.computeCurrentVelocity(1000, maximumFlingVelocity);
                vx = (int) velocityTracker.getXVelocity();
                vy = (int) velocityTracker.getYVelocity();
                velocityTracker.recycle();
                if (Math.abs(vx) < minimumFlingVelocity) {
                    vx = 0;
                }
                if (Math.abs(vy) < minimumFlingVelocity) {
                    vy = 0;
                }
                velocityTracker = null;
            }

            view.setPressed(false);
            boolean consumeStop = onStop(view, direction, x, y, x - downX, y - downY, vx, vy, event);

            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (state == STATE_DOWN) {
                    if (event.getEventTime() - event.getDownTime() <= MIN_TAP_TIME) {
                        view.performClick();
                    } else {
                        view.performLongClick();
                    }
                }
            }

            resetTouch(-1, -1);

            return consumeStop;
        }
    }
}