package com.blankj.androidutilcode.feature.core.span;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

public class ForegroundAlphaColorSpan extends CharacterStyle
        implements UpdateAppearance {

    private int mColor;

    public ForegroundAlphaColorSpan(@ColorInt int color) {
        mColor = color;
    }

    public void setAlpha(int alpha) {
        mColor = Color.argb(alpha, Color.red(mColor), Color.green(mColor), Color.blue(mColor));
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(mColor);
    }
}
