package com.blankj.androidutilcode.feature.core.span;

import java.util.ArrayList;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/06/11
 *     desc  :
 * </pre>
 */
public class ForegroundAlphaColorSpanGroup {

    private final float mAlpha;

    private final ArrayList<ForegroundAlphaColorSpan> mSpans;

    public ForegroundAlphaColorSpanGroup(float alpha) {
        mAlpha = alpha;
        mSpans = new ArrayList<>();
    }

    public void addSpan(ForegroundAlphaColorSpan span) {
        span.setAlpha((int) (mAlpha * 255));
        mSpans.add(span);
    }

    public void setAlpha(float alpha) {
        int size = mSpans.size();
        float total = 1.0f * size * alpha;
        for (int index = 0; index < size; index++) {
            ForegroundAlphaColorSpan span = mSpans.get(index);
            if (total >= 1.0f) {
                span.setAlpha(255);
                total -= 1.0f;
            } else {
                span.setAlpha((int) (total * 255));
                total = 0.0f;
            }
        }
    }

    public float getAlpha() {
        return mAlpha;
    }
}
