package com.blankj.utilcode.pkg.feature.span;

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
public class ShadowSpan extends CharacterStyle implements UpdateAppearance {
    private float radius;
    private float dx;
    private float dy;
    private int   shadowColor;

    public ShadowSpan(float radius, float dx, float dy, int shadowColor) {
        this.radius = radius;
        this.dx = dx;
        this.dy = dy;
        this.shadowColor = shadowColor;
    }

    public float getDx() {
        return dx;
    }

    public void setDx(float dx) {
        this.dx = dx;
    }

    public float getDy() {
        return dy;
    }

    public void setDy(float dy) {
        this.dy = dy;
    }

    @Override
    public void updateDrawState(TextPaint tp) {
        tp.setShadowLayer(radius, dx, dy, shadowColor);
    }
}
