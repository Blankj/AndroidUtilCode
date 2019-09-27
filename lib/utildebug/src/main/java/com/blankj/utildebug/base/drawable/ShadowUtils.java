/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.blankj.utildebug.base.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.util.StateSet;
import android.view.View;

public class ShadowUtils {

    private static final int SHADOW_TAG = -16;

    public static void apply(View... views) {
        if (views == null) return;
        for (View view : views) {
            apply(view, new Builder());
        }
    }

    public static void apply(View view, Builder builder) {
        if (view == null || builder == null) return;
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

        private static final int SHADOW_COLOR_DEFAULT = 0xb0_000000;
        private static final int SHADOW_SIZE          = dp2px(8);

        private float mShadowRadiusNormal   = 0;
        private float mShadowRadiusPressed  = 0;
        private float mShadowSizeNormal     = SHADOW_SIZE;
        private float mShadowSizePressed    = SHADOW_SIZE;
        private float mShadowMaxSizeNormal  = SHADOW_SIZE;
        private float mShadowMaxSizePressed = SHADOW_SIZE;
        private int   mShadowColorNormal    = SHADOW_COLOR_DEFAULT;
        private int   mShadowColorPressed   = SHADOW_COLOR_DEFAULT;

        public Builder() {
        }

        public Builder setShadowRadius(float radius) {
            return setShadowRadius(radius, radius);
        }

        public Builder setShadowRadius(float radiusNormal, float radiusPressed) {
            this.mShadowRadiusNormal = radiusNormal;
            this.mShadowRadiusPressed = radiusPressed;
            return this;
        }

        public Builder setShadowSize(int size) {
            return setShadowSize(size, size);
        }

        public Builder setShadowSize(int sizeNormal, int sizePressed) {
            this.mShadowSizeNormal = sizeNormal;
            this.mShadowSizePressed = sizePressed;
            return this;
        }

        public Builder setShadowMaxSize(int maxSize) {
            return setShadowMaxSize(maxSize, maxSize);
        }

        public Builder setShadowMaxSize(int maxSizeNormal, int maxSizePressed) {
            this.mShadowMaxSizeNormal = maxSizeNormal;
            this.mShadowMaxSizePressed = maxSizePressed;
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

        public Drawable create(Drawable src) {
            if (src == null) {
                src = new ColorDrawable(Color.TRANSPARENT);
            }
            StateListDrawable drawable = new StateListDrawable();
            drawable.addState(
                    new int[]{android.R.attr.state_pressed},
                    new ShadowDrawable(src, mShadowRadiusPressed,
                            mShadowSizeNormal, mShadowMaxSizeNormal, mShadowColorPressed)
            );
            drawable.addState(
                    StateSet.WILD_CARD,
                    new ShadowDrawable(src, mShadowRadiusNormal,
                            mShadowSizePressed, mShadowMaxSizePressed, mShadowColorNormal)
            );
            return drawable;
        }

        private static int dp2px(final float dpValue) {
            final float scale = Resources.getSystem().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        }
    }

    public static class ShadowDrawable extends DrawableWrapper {
        // used to calculate content padding
        private static final double COS_45 = Math.cos(Math.toRadians(45));

        private static final float SHADOW_MULTIPLIER = 1.5f;

        private static final float SHADOW_TOP_SCALE    = 0.25f;
        private static final float SHADOW_HORIZ_SCALE  = 0.5f;
        private static final float SHADOW_BOTTOM_SCALE = 1f;

        private final Paint mCornerShadowPaint;
        private final Paint mEdgeShadowPaint;

        private final RectF mContentBounds;

        private float mCornerRadius;

        private Path mCornerShadowPath;

        // updated value with inset
        private float mMaxShadowSize;
        // actual value set by developer
        private float mRawMaxShadowSize;

        // multiplied value to account for shadow offset
        private float mShadowSize;
        // actual value set by developer
        private float mRawShadowSize;

        private boolean mDirty = true;

        private final int mShadowStartColor;
        private final int mShadowEndColor;

        private boolean mAddPaddingForCorners = false;

        private float mRotation;

        public ShadowDrawable(Drawable content, float radius,
                              float shadowSize, float maxShadowSize, int shadowColor) {
            super(content);
            mShadowStartColor = shadowColor;
            mShadowEndColor = 0;

            mCornerShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
            mCornerShadowPaint.setStyle(Paint.Style.FILL);
            mCornerRadius = Math.round(radius);
            mContentBounds = new RectF();
            mEdgeShadowPaint = new Paint(mCornerShadowPaint);
            mEdgeShadowPaint.setAntiAlias(false);
            setShadowSize(shadowSize, maxShadowSize);
        }

        /**
         * Casts the value to an even integer.
         */
        private static int toEven(float value) {
            int i = Math.round(value);
            return (i % 2 == 1) ? i - 1 : i;
        }

        public void setAddPaddingForCorners(boolean addPaddingForCorners) {
            mAddPaddingForCorners = addPaddingForCorners;
            invalidateSelf();
        }

        @Override
        public void setAlpha(int alpha) {
            super.setAlpha(alpha);
            mCornerShadowPaint.setAlpha(alpha);
            mEdgeShadowPaint.setAlpha(alpha);
        }

        @Override
        protected void onBoundsChange(Rect bounds) {
            mDirty = true;
        }

        void setShadowSize(float shadowSize, float maxShadowSize) {
            if (shadowSize < 0 || maxShadowSize < 0) {
                throw new IllegalArgumentException("invalid shadow size");
            }
            shadowSize = toEven(shadowSize);
            maxShadowSize = toEven(maxShadowSize);
            if (shadowSize > maxShadowSize) {
                shadowSize = maxShadowSize;
            }
            if (mRawShadowSize == shadowSize && mRawMaxShadowSize == maxShadowSize) {
                return;
            }
            mRawShadowSize = shadowSize;
            mRawMaxShadowSize = maxShadowSize;
            mShadowSize = Math.round(shadowSize * SHADOW_MULTIPLIER);
            mMaxShadowSize = maxShadowSize;
            mDirty = true;
            invalidateSelf();
        }

        @Override
        public boolean getPadding(Rect padding) {
            int vOffset = (int) Math.ceil(calculateVerticalPadding(mRawMaxShadowSize, mCornerRadius,
                    mAddPaddingForCorners));
            int hOffset = (int) Math.ceil(calculateHorizontalPadding(mRawMaxShadowSize, mCornerRadius,
                    mAddPaddingForCorners));
            padding.set(hOffset, vOffset, hOffset, vOffset);
            return true;
        }

        private static float calculateVerticalPadding(float maxShadowSize, float cornerRadius,
                                                      boolean addPaddingForCorners) {
            if (addPaddingForCorners) {
                return (float) (maxShadowSize * SHADOW_MULTIPLIER + (1 - COS_45) * cornerRadius);
            } else {
                return maxShadowSize * SHADOW_MULTIPLIER;
            }
        }

        private static float calculateHorizontalPadding(float maxShadowSize, float cornerRadius,
                                                        boolean addPaddingForCorners) {
            if (addPaddingForCorners) {
                return (float) (maxShadowSize + (1 - COS_45) * cornerRadius);
            } else {
                return maxShadowSize;
            }
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }

        public void setCornerRadius(float radius) {
            radius = Math.round(radius);
            if (mCornerRadius == radius) {
                return;
            }
            mCornerRadius = radius;
            mDirty = true;
            invalidateSelf();
        }

        @Override
        public void draw(Canvas canvas) {
            if (mDirty) {
                buildComponents(getBounds());
                mDirty = false;
            }
            drawShadow(canvas);

            super.draw(canvas);
        }

        final void setRotation(float rotation) {
            if (mRotation != rotation) {
                mRotation = rotation;
                invalidateSelf();
            }
        }

        private void drawShadow(Canvas canvas) {
            final int rotateSaved = canvas.save();
            canvas.rotate(mRotation, mContentBounds.centerX(), mContentBounds.centerY());

            final float edgeShadowTop = -mCornerRadius - mShadowSize;
            final float shadowOffset = mCornerRadius;
            final boolean drawHorizontalEdges = mContentBounds.width() - 2 * shadowOffset > 0;
            final boolean drawVerticalEdges = mContentBounds.height() - 2 * shadowOffset > 0;

            final float shadowOffsetTop = mRawShadowSize - (mRawShadowSize * SHADOW_TOP_SCALE);
            final float shadowOffsetHorizontal = mRawShadowSize - (mRawShadowSize * SHADOW_HORIZ_SCALE);
            final float shadowOffsetBottom = mRawShadowSize - (mRawShadowSize * SHADOW_BOTTOM_SCALE);

            final float shadowScaleHorizontal = shadowOffset / (shadowOffset + shadowOffsetHorizontal);
            final float shadowScaleTop = shadowOffset / (shadowOffset + shadowOffsetTop);
            final float shadowScaleBottom = shadowOffset / (shadowOffset + shadowOffsetBottom);

            // LT
            int saved = canvas.save();
            canvas.translate(mContentBounds.left + shadowOffset, mContentBounds.top + shadowOffset);
            canvas.scale(shadowScaleHorizontal, shadowScaleTop);
            canvas.drawPath(mCornerShadowPath, mCornerShadowPaint);
            if (drawHorizontalEdges) {
                // TE
                canvas.scale(1f / shadowScaleHorizontal, 1f);
                canvas.drawRect(0, edgeShadowTop,
                        mContentBounds.width() - 2 * shadowOffset, -mCornerRadius,
                        mEdgeShadowPaint);
            }
            canvas.restoreToCount(saved);
            // RB
            saved = canvas.save();
            canvas.translate(mContentBounds.right - shadowOffset, mContentBounds.bottom - shadowOffset);
            canvas.scale(shadowScaleHorizontal, shadowScaleBottom);
            canvas.rotate(180f);
            canvas.drawPath(mCornerShadowPath, mCornerShadowPaint);
            if (drawHorizontalEdges) {
                // BE
                canvas.scale(1f / shadowScaleHorizontal, 1f);
                canvas.drawRect(0, edgeShadowTop,
                        mContentBounds.width() - 2 * shadowOffset, -mCornerRadius,
                        mEdgeShadowPaint);
            }
            canvas.restoreToCount(saved);
            // LB
            saved = canvas.save();
            canvas.translate(mContentBounds.left + shadowOffset, mContentBounds.bottom - shadowOffset);
            canvas.scale(shadowScaleHorizontal, shadowScaleBottom);
            canvas.rotate(270f);
            canvas.drawPath(mCornerShadowPath, mCornerShadowPaint);
            if (drawVerticalEdges) {
                // LE
                canvas.scale(1f / shadowScaleBottom, 1f);
                canvas.drawRect(0, edgeShadowTop,
                        mContentBounds.height() - 2 * shadowOffset, -mCornerRadius, mEdgeShadowPaint);
            }
            canvas.restoreToCount(saved);
            // RT
            saved = canvas.save();
            canvas.translate(mContentBounds.right - shadowOffset, mContentBounds.top + shadowOffset);
            canvas.scale(shadowScaleHorizontal, shadowScaleTop);
            canvas.rotate(90f);
            canvas.drawPath(mCornerShadowPath, mCornerShadowPaint);
            if (drawVerticalEdges) {
                // RE
                canvas.scale(1f / shadowScaleTop, 1f);
                canvas.drawRect(0, edgeShadowTop,
                        mContentBounds.height() - 2 * shadowOffset, -mCornerRadius, mEdgeShadowPaint);
            }
            canvas.restoreToCount(saved);

            canvas.restoreToCount(rotateSaved);
        }

        private void buildShadowCorners() {
            RectF innerBounds = new RectF(-mCornerRadius, -mCornerRadius, mCornerRadius, mCornerRadius);
            RectF outerBounds = new RectF(innerBounds);
            outerBounds.inset(-mShadowSize, -mShadowSize);

            if (mCornerShadowPath == null) {
                mCornerShadowPath = new Path();
            } else {
                mCornerShadowPath.reset();
            }
            mCornerShadowPath.setFillType(Path.FillType.EVEN_ODD);
            mCornerShadowPath.moveTo(-mCornerRadius, 0);
            mCornerShadowPath.rLineTo(-mShadowSize, 0);
            // outer arc
            mCornerShadowPath.arcTo(outerBounds, 180f, 90f, false);
            // inner arc
            mCornerShadowPath.arcTo(innerBounds, 270f, -90f, false);
            mCornerShadowPath.close();

            float shadowRadius = -outerBounds.top;
            if (shadowRadius > 0f) {
                float startRatio = mCornerRadius / shadowRadius;
                mCornerShadowPaint.setShader(new RadialGradient(0, 0, shadowRadius,
                        new int[]{0, mShadowStartColor, mShadowEndColor},
                        new float[]{0f, startRatio, 1f},
                        Shader.TileMode.CLAMP));
            }

            // we offset the content shadowSize/2 pixels up to make it more realistic.
            // this is why edge shadow shader has some extra space
            // When drawing bottom edge shadow, we use that extra space.
            mEdgeShadowPaint.setShader(new LinearGradient(0, innerBounds.top, 0, outerBounds.top,
                    new int[]{mShadowStartColor, mShadowEndColor},
                    new float[]{0f, 1f}, Shader.TileMode.CLAMP));
            mEdgeShadowPaint.setAntiAlias(false);
        }

        private void buildComponents(Rect bounds) {
            // Card is offset SHADOW_MULTIPLIER * maxShadowSize to account for the shadow shift.
            // We could have different top-bottom offsets to avoid extra gap above but in that case
            // center aligning Views inside the CardView would be problematic.
            final float verticalOffset = mRawMaxShadowSize * SHADOW_MULTIPLIER;
            mContentBounds.set(bounds.left + mRawMaxShadowSize, bounds.top + verticalOffset,
                    bounds.right - mRawMaxShadowSize, bounds.bottom - verticalOffset);

            getWrappedDrawable().setBounds((int) mContentBounds.left, (int) mContentBounds.top,
                    (int) mContentBounds.right, (int) mContentBounds.bottom);

            buildShadowCorners();
        }

        public float getCornerRadius() {
            return mCornerRadius;
        }

        public void setShadowSize(float size) {
            setShadowSize(size, mRawMaxShadowSize);
        }

        public void setMaxShadowSize(float size) {
            setShadowSize(mRawShadowSize, size);
        }

        public float getShadowSize() {
            return mRawShadowSize;
        }

        public float getMaxShadowSize() {
            return mRawMaxShadowSize;
        }

        public float getMinWidth() {
            final float content = 2 *
                    Math.max(mRawMaxShadowSize, mCornerRadius + mRawMaxShadowSize / 2);
            return content + mRawMaxShadowSize * 2;
        }

        public float getMinHeight() {
            final float content = 2 * Math.max(mRawMaxShadowSize, mCornerRadius
                    + mRawMaxShadowSize * SHADOW_MULTIPLIER / 2);
            return content + (mRawMaxShadowSize * SHADOW_MULTIPLIER) * 2;
        }
    }

    static class DrawableWrapper extends Drawable implements Drawable.Callback {
        private Drawable mDrawable;

        public DrawableWrapper(Drawable drawable) {
            this.setWrappedDrawable(drawable);
        }

        public void draw(Canvas canvas) {
            this.mDrawable.draw(canvas);
        }

        protected void onBoundsChange(Rect bounds) {
            this.mDrawable.setBounds(bounds);
        }

        public void setChangingConfigurations(int configs) {
            this.mDrawable.setChangingConfigurations(configs);
        }

        public int getChangingConfigurations() {
            return this.mDrawable.getChangingConfigurations();
        }

        public void setDither(boolean dither) {
            this.mDrawable.setDither(dither);
        }

        public void setFilterBitmap(boolean filter) {
            this.mDrawable.setFilterBitmap(filter);
        }

        public void setAlpha(int alpha) {
            this.mDrawable.setAlpha(alpha);
        }

        public void setColorFilter(ColorFilter cf) {
            this.mDrawable.setColorFilter(cf);
        }

        public boolean isStateful() {
            return this.mDrawable.isStateful();
        }

        public boolean setState(int[] stateSet) {
            return this.mDrawable.setState(stateSet);
        }

        public int[] getState() {
            return this.mDrawable.getState();
        }

        public void jumpToCurrentState() {
            DrawableCompat.jumpToCurrentState(this.mDrawable);
        }

        public Drawable getCurrent() {
            return this.mDrawable.getCurrent();
        }

        public boolean setVisible(boolean visible, boolean restart) {
            return super.setVisible(visible, restart) || this.mDrawable.setVisible(visible, restart);
        }

        public int getOpacity() {
            return this.mDrawable.getOpacity();
        }

        public Region getTransparentRegion() {
            return this.mDrawable.getTransparentRegion();
        }

        public int getIntrinsicWidth() {
            return this.mDrawable.getIntrinsicWidth();
        }

        public int getIntrinsicHeight() {
            return this.mDrawable.getIntrinsicHeight();
        }

        public int getMinimumWidth() {
            return this.mDrawable.getMinimumWidth();
        }

        public int getMinimumHeight() {
            return this.mDrawable.getMinimumHeight();
        }

        public boolean getPadding(Rect padding) {
            return this.mDrawable.getPadding(padding);
        }

        public void invalidateDrawable(Drawable who) {
            this.invalidateSelf();
        }

        public void scheduleDrawable(Drawable who, Runnable what, long when) {
            this.scheduleSelf(what, when);
        }

        public void unscheduleDrawable(Drawable who, Runnable what) {
            this.unscheduleSelf(what);
        }

        protected boolean onLevelChange(int level) {
            return this.mDrawable.setLevel(level);
        }

        public void setAutoMirrored(boolean mirrored) {
            DrawableCompat.setAutoMirrored(this.mDrawable, mirrored);
        }

        public boolean isAutoMirrored() {
            return DrawableCompat.isAutoMirrored(this.mDrawable);
        }

        public void setTint(int tint) {
            DrawableCompat.setTint(this.mDrawable, tint);
        }

        public void setTintList(ColorStateList tint) {
            DrawableCompat.setTintList(this.mDrawable, tint);
        }

        public void setTintMode(Mode tintMode) {
            DrawableCompat.setTintMode(this.mDrawable, tintMode);
        }

        public void setHotspot(float x, float y) {
            DrawableCompat.setHotspot(this.mDrawable, x, y);
        }

        public void setHotspotBounds(int left, int top, int right, int bottom) {
            DrawableCompat.setHotspotBounds(this.mDrawable, left, top, right, bottom);
        }

        public Drawable getWrappedDrawable() {
            return this.mDrawable;
        }

        public void setWrappedDrawable(Drawable drawable) {
            if (this.mDrawable != null) {
                this.mDrawable.setCallback((Callback) null);
            }

            this.mDrawable = drawable;
            if (drawable != null) {
                drawable.setCallback(this);
            }
        }
    }
}
