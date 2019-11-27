package com.blankj.utilcode.util;

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

    public static void apply(View... views) {
        if (views == null) return;
        for (View view : views) {
            apply(view, new Config());
        }
    }

    public static void apply(View view, Config builder) {
        if (view == null || builder == null) return;
        Drawable background = view.getBackground();
        Object tag = view.getTag(SHADOW_TAG);
        if (tag instanceof Drawable) {
            ViewCompat.setBackground(view, (Drawable) tag);
        } else {
            background = builder.apply(background);
            ViewCompat.setBackground(view, background);
            view.setTag(SHADOW_TAG, background);
        }
    }

    public static class Config {

        private static final int SHADOW_COLOR_DEFAULT = 0xb0_000000;
        private static final int SHADOW_SIZE          = dp2px(8);

        private float   mShadowRadius         = -1;
        private float   mShadowSizeNormal     = -1;
        private float   mShadowSizePressed    = -1;
        private float   mShadowMaxSizeNormal  = -1;
        private float   mShadowMaxSizePressed = -1;
        private int     mShadowColorNormal    = SHADOW_COLOR_DEFAULT;
        private int     mShadowColorPressed   = SHADOW_COLOR_DEFAULT;
        private boolean isCircle              = false;

        public Config() {
        }

        public Config setShadowRadius(float radius) {
            this.mShadowRadius = radius;
            if (isCircle) {
                throw new IllegalArgumentException("Set circle needn't set radius.");
            }
            return this;
        }

        public Config setCircle() {
            isCircle = true;
            if (mShadowRadius != -1) {
                throw new IllegalArgumentException("Set circle needn't set radius.");
            }
            return this;
        }

        public Config setShadowSize(int size) {
            return setShadowSize(size, size);
        }

        public Config setShadowSize(int sizeNormal, int sizePressed) {
            this.mShadowSizeNormal = sizeNormal;
            this.mShadowSizePressed = sizePressed;
            return this;
        }

        public Config setShadowMaxSize(int maxSize) {
            return setShadowMaxSize(maxSize, maxSize);
        }

        public Config setShadowMaxSize(int maxSizeNormal, int maxSizePressed) {
            this.mShadowMaxSizeNormal = maxSizeNormal;
            this.mShadowMaxSizePressed = maxSizePressed;
            return this;
        }

        public Config setShadowColor(int color) {
            return setShadowColor(color, color);
        }

        public Config setShadowColor(int colorNormal, int colorPressed) {
            this.mShadowColorNormal = colorNormal;
            this.mShadowColorPressed = colorPressed;
            return this;
        }

        Drawable apply(Drawable src) {
            if (src == null) {
                src = new ColorDrawable(Color.TRANSPARENT);
            }
            StateListDrawable drawable = new StateListDrawable();
            drawable.addState(
                    new int[]{android.R.attr.state_pressed},
                    new ShadowDrawable(src, getShadowRadius(), getShadowSizeNormal(),
                            getShadowMaxSizeNormal(), mShadowColorPressed, isCircle)
            );
            drawable.addState(
                    StateSet.WILD_CARD,
                    new ShadowDrawable(src, getShadowRadius(), getShadowSizePressed(),
                            getShadowMaxSizePressed(), mShadowColorNormal, isCircle)
            );
            return drawable;
        }

        private float getShadowRadius() {
            if (mShadowRadius == -1) {
                mShadowRadius = 0;
            }
            return mShadowRadius;
        }

        private float getShadowSizeNormal() {
            if (mShadowSizeNormal == -1) {
                mShadowSizeNormal = SHADOW_SIZE;
            }
            return mShadowSizeNormal;
        }

        private float getShadowSizePressed() {
            if (mShadowSizePressed == -1) {
                mShadowSizePressed = getShadowSizeNormal();
            }
            return mShadowSizePressed;
        }

        private float getShadowMaxSizeNormal() {
            if (mShadowMaxSizeNormal == -1) {
                mShadowMaxSizeNormal = getShadowSizeNormal();
            }
            return mShadowMaxSizeNormal;
        }

        private float getShadowMaxSizePressed() {
            if (mShadowMaxSizePressed == -1) {
                mShadowMaxSizePressed = getShadowSizePressed();
            }
            return mShadowMaxSizePressed;
        }

        private static int dp2px(final float dpValue) {
            final float scale = Resources.getSystem().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        }
    }

    public static class ShadowDrawable extends DrawableWrapper {
        // used to calculate content padding
        private static final double COS_45 = Math.cos(Math.toRadians(45));

        private float mShadowMultiplier = 1f;

        private float mShadowTopScale    = 1f;
        private float mShadowHorizScale  = 1f;
        private float mShadowBottomScale = 1f;

        private Paint mCornerShadowPaint;
        private Paint mEdgeShadowPaint;

        private RectF mContentBounds;

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

        private boolean isCircle;

        public ShadowDrawable(Drawable content, float radius,
                              float shadowSize, float maxShadowSize, int shadowColor, boolean isCircle) {
            super(content);
            mShadowStartColor = shadowColor;
            mShadowEndColor = 0;
            this.isCircle = isCircle;
            if (isCircle) {
                mShadowMultiplier = 1;
                mShadowTopScale = 1;
                mShadowHorizScale = 1;
                mShadowBottomScale = 1;
            }

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
            mShadowSize = Math.round(shadowSize * mShadowMultiplier);
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

        private float calculateVerticalPadding(float maxShadowSize, float cornerRadius,
                                               boolean addPaddingForCorners) {
            if (addPaddingForCorners) {
                return (float) (maxShadowSize * mShadowMultiplier + (1 - COS_45) * cornerRadius);
            } else {
                return maxShadowSize * mShadowMultiplier;
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
            if (isCircle) {
                int saved = canvas.save();
                canvas.translate(mContentBounds.centerX(), mContentBounds.centerY());
                canvas.drawPath(mCornerShadowPath, mCornerShadowPaint);
                canvas.restoreToCount(saved);
                return;
            }

            final int rotateSaved = canvas.save();
            canvas.rotate(mRotation, mContentBounds.centerX(), mContentBounds.centerY());

            final float edgeShadowTop = -mCornerRadius - mShadowSize;
            final float shadowOffset = mCornerRadius;
            final boolean drawHorizontalEdges = mContentBounds.width() - 2 * shadowOffset > 0;
            final boolean drawVerticalEdges = mContentBounds.height() - 2 * shadowOffset > 0;

            final float shadowOffsetTop = mRawShadowSize - (mRawShadowSize * mShadowTopScale);
            final float shadowOffsetHorizontal = mRawShadowSize - (mRawShadowSize * mShadowHorizScale);
            final float shadowOffsetBottom = mRawShadowSize - (mRawShadowSize * mShadowBottomScale);

            final float shadowScaleHorizontal = shadowOffset == 0 ? 1 : shadowOffset / (shadowOffset + shadowOffsetHorizontal);
            final float shadowScaleTop = shadowOffset == 0 ? 1 : shadowOffset / (shadowOffset + shadowOffsetTop);
            final float shadowScaleBottom = shadowOffset == 0 ? 1 : shadowOffset / (shadowOffset + shadowOffsetBottom);

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
            if (isCircle) {
                float size = mContentBounds.width() / 2 - 1f;
                RectF innerBounds = new RectF(-size, -size, size, size);
                RectF outerBounds = new RectF(innerBounds);
                outerBounds.inset(-mShadowSize, -mShadowSize);

                if (mCornerShadowPath == null) {
                    mCornerShadowPath = new Path();
                } else {
                    mCornerShadowPath.reset();
                }
                mCornerShadowPath.setFillType(Path.FillType.EVEN_ODD);
                mCornerShadowPath.moveTo(-size, 0);
                mCornerShadowPath.rLineTo(-mShadowSize, 0);
                // outer arc
                mCornerShadowPath.arcTo(outerBounds, 180f, 180f, false);
                mCornerShadowPath.arcTo(outerBounds, 360f, 180f, false);
                // inner arc
                mCornerShadowPath.arcTo(innerBounds, 540f, -180f, false);
                mCornerShadowPath.arcTo(innerBounds, 360f, -180f, false);
                mCornerShadowPath.close();

                float shadowRadius = -outerBounds.top;
                if (shadowRadius > 0f) {
                    float startRatio = size / shadowRadius;
                    mCornerShadowPaint.setShader(new RadialGradient(0, 0, shadowRadius,
                            new int[]{0, mShadowStartColor, mShadowEndColor},
                            new float[]{0f, startRatio, 1f},
                            Shader.TileMode.CLAMP));
                }
                return;
            }

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
            // Card is offset mShadowMultiplier * maxShadowSize to account for the shadow shift.
            // We could have different top-bottom offsets to avoid extra gap above but in that case
            // center aligning Views inside the CardView would be problematic.
            if (isCircle) {
                mCornerRadius = bounds.width() / 2;
            }
            final float verticalOffset = mRawMaxShadowSize * mShadowMultiplier;
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
                    + mRawMaxShadowSize * mShadowMultiplier / 2);
            return content + (mRawMaxShadowSize * mShadowMultiplier) * 2;
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
