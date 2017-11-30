package com.blankj.androidutilcode.core.span;

import android.graphics.BlurMaskFilter;
import android.graphics.MaskFilter;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

public class BlurMaskFilterSpan extends CharacterStyle implements UpdateAppearance {

    private static final String TAG = "MutableBlurMaskFilterSpan";
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
