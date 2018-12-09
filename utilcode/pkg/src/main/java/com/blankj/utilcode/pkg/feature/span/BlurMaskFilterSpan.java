package com.blankj.utilcode.pkg.feature.span;

import android.graphics.BlurMaskFilter;
import android.graphics.MaskFilter;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/27
 *     desc  : demo about SpanUtils
 * </pre>
 */
public class BlurMaskFilterSpan extends CharacterStyle implements UpdateAppearance {

    private float      mRadius;
    private MaskFilter mFilter;

    public BlurMaskFilterSpan(float radius) {
        mRadius = radius;
    }

    public void setRadius(float radius) {
        mRadius = radius;
        mFilter = new BlurMaskFilter(mRadius, BlurMaskFilter.Blur.NORMAL);
    }

    public float getRadius() {
        return mRadius;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setMaskFilter(mFilter);
    }
}
