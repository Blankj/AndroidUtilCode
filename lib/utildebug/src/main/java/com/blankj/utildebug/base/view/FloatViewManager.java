package com.blankj.utildebug.base.view;

import android.view.WindowManager;

import com.blankj.utilcode.util.Utils;
import com.blankj.utildebug.helper.WindowHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/08/30
 *     desc  :
 * </pre>
 */
public class FloatViewManager {

    private final WindowManager       mWM         = WindowHelper.getWindowManager();
    private final List<BaseFloatView> mFloatViews = new ArrayList<>();

    private FloatViewManager() {
    }

    public static FloatViewManager getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static final class LazyHolder {
        private static final FloatViewManager INSTANCE = new FloatViewManager();
    }

    public void show(final BaseFloatView view) {
        Utils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mFloatViews.contains(view)) return;
                view.createFloatView();
                mWM.addView(view, view.getLayoutParams());
                mFloatViews.add(view);
            }
        });
    }

    public void dismiss(final BaseFloatView view) {
        Utils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!mFloatViews.contains(view)) return;
                mWM.removeView(view);
                mFloatViews.remove(view);
            }
        });
    }
}
