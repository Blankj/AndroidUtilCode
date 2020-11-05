package com.blankj.common.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2020/03/28
 *     desc  :
 * </pre>
 */
public class RotateView extends View {

    private ObjectAnimator headerAnimator;

    public RotateView(Context context) {
        this(context, null);
    }

    public RotateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RotateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (headerAnimator == null) {
            initAnimator();
        }
        if (visibility == VISIBLE) {
            headerAnimator.start();
        } else {
            headerAnimator.end();
        }
    }

    private void initAnimator() {
        headerAnimator = ObjectAnimator.ofFloat(this, "rotation", 0f, 360f);
        headerAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        headerAnimator.setInterpolator(new LinearInterpolator());
        headerAnimator.setRepeatMode(ObjectAnimator.RESTART);
        headerAnimator.setDuration(1000);
        headerAnimator.start();
    }
}
