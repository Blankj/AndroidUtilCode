package com.blankj.utilcode.util;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.StateSet;
import android.view.View;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/09/13
 *     desc  : utils about shadow
 * </pre>
 */
public class ShadowUtils {

    private static final int SHADOW_TAG = -16;

    private ShadowUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void apply(View... views) {
        if (views == null) return;
        for (View view : views) {
            apply(view, new Builder());
        }
    }

    public static void apply(View view, Builder builder) {
        if (view == null || builder == null) return;
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Drawable background = view.getBackground();
        Object tag = view.getTag(SHADOW_TAG);
        if (tag instanceof Drawable) {
            ViewCompat.setBackground(view, (Drawable) tag);
        } else {
            background = builder.create(background);
            ViewCompat.setBackground(view, background);
            view.setTag(SHADOW_TAG, background);
        }
    }

    public static class Builder {

        private float mDrawableRadius      = -1;
        private float mShadowRadiusNormal  = -1;
        private float mShadowRadiusPressed = -1;
        private int   mShadowColorNormal   = -1;
        private int   mShadowColorPressed  = -1;
        private float mOffsetXNormal       = -1;
        private float mOffsetXPressed      = -1;
        private float mOffsetYNormal       = -1;
        private float mOffsetYPressed      = -1;

        private RectCallback mCallback;

        private boolean isUseDefault = false;

        public Builder() {
        }

        public Builder setDrawableRadius(float radius) {
            this.mDrawableRadius = radius;
            return this;
        }

        public Builder setShadowRadius(float radius) {
            return setShadowRadius(radius, radius);
        }

        public Builder setShadowRadius(float radiusNormal, float radiusPressed) {
            this.mShadowRadiusNormal = radiusNormal;
            this.mShadowRadiusPressed = radiusPressed;
            return this;
        }

        public Builder setShadowColor(int color) {
            return setShadowColor(color, color);
        }

        public Builder setShadowColor(int colorNormal, int colorPressed) {
            this.mShadowColorNormal = colorNormal;
            this.mShadowColorPressed = colorPressed;
            return this;
        }

        public Builder setOffsetX(float offsetX) {
            return setOffsetX(offsetX, offsetX);
        }

        public Builder setOffsetX(float offsetX_Normal, float offsetX_Pressed) {
            this.mOffsetXNormal = offsetX_Normal;
            this.mOffsetXPressed = offsetX_Pressed;
            return this;
        }

        public Builder setOffsetY(float offsetY) {
            return setOffsetY(offsetY, offsetY);
        }

        public Builder setOffsetY(float offsetY_Normal, float offsetY_Pressed) {
            this.mOffsetYNormal = offsetY_Normal;
            this.mOffsetYPressed = offsetY_Pressed;
            return this;
        }

        public Builder setRectCallback(RectCallback callback) {
            this.mCallback = callback;
            return this;
        }

        public Drawable create(Drawable src) {
            StateListDrawable drawable = new StateListDrawable();
            drawable.addState(
                    new int[]{android.R.attr.state_pressed},
                    new ShadowDrawable(src, mDrawableRadius,
                            mShadowRadiusPressed, mShadowColorPressed,
                            mOffsetXPressed, mOffsetYPressed, mCallback
                    )
            );
            drawable.addState(StateSet.WILD_CARD,
                    new ShadowDrawable(src, mDrawableRadius,
                            mShadowRadiusNormal, mShadowColorNormal,
                            mOffsetXNormal, mOffsetYNormal, mCallback
                    )
            );
            return drawable;
        }

        private static int dp2px(final float dpValue) {
            final float scale = Resources.getSystem().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        }
    }

    private static class ShadowDrawable extends Drawable {

        private Drawable mSrc;
        private int      mShadowColor;
        private float    mDrawableRadius;
        private float    mShadowRadius;
        private float    mOffsetX;
        private float    mOffsetY;
        private RectF    mRectF;
        private Paint    mShadowPaint;

        private RectCallback mCallback;

        private ShadowDrawable(Drawable src, float drawableRadius,
                               float shadowRadius, int shadowColor, float offsetX, float offsetY,
                               RectCallback callback) {
            mSrc = src;
            mDrawableRadius = drawableRadius;
            mShadowRadius = shadowRadius;
            mShadowColor = shadowColor;
            mOffsetX = offsetX;
            mOffsetY = offsetY;
            mCallback = callback;

            mShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mShadowPaint.setColor(Color.TRANSPARENT);
            mShadowPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
            mShadowPaint.setShadowLayer(mShadowRadius, mOffsetX, mOffsetY, mShadowColor);
        }

        @Override
        public void setBounds(int left, int top, int right, int bottom) {
            super.setBounds(left, top, right, bottom);
            if (mCallback != null) {
                mRectF = mCallback.callback(left, top, right, bottom, mShadowRadius, mOffsetX, mOffsetY);
            } else {
                float l = left + mShadowRadius - mOffsetX;
                float t = top + mShadowRadius - mOffsetY;
                float r = right - mShadowRadius - mOffsetX;
                float b = bottom - mShadowRadius - mOffsetY;
                mRectF = new RectF(l, t, r, b);
            }
            if (mSrc != null) {
                mSrc.setBounds((int) mRectF.left, (int) mRectF.top, (int) mRectF.right, (int) mRectF.bottom);
            }
        }

        @Override
        public void draw(@NonNull Canvas canvas) {
            canvas.drawRoundRect(mRectF, mDrawableRadius, mDrawableRadius, mShadowPaint);
            if (mSrc != null) {
                mSrc.draw(canvas);
            }
        }

        @Override
        public void setAlpha(int alpha) {
            mShadowPaint.setAlpha(alpha);
        }

        @Override
        public void setColorFilter(@Nullable ColorFilter colorFilter) {
            mShadowPaint.setColorFilter(colorFilter);
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }
    }

    public interface RectCallback {
        RectF callback(int left, int top, int right, int bottom, float radius, float offsetX, float offsetY);
    }
}
