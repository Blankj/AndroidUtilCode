package com.blankj.utilcode.pkg.feature.fragment;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.TransitionSet;
import android.util.AttributeSet;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 17/02/21
 *     desc  : demo about FragmentUtils
 * </pre>
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
public class DetailTransition extends TransitionSet {
    public DetailTransition() {
        init();
    }

    // 允许资源文件使用
    public DetailTransition(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOrdering(ORDERING_TOGETHER);
        addTransition(new ChangeBounds()).
                addTransition(new ChangeTransform()).
                addTransition(new ChangeImageTransform());
    }
}
