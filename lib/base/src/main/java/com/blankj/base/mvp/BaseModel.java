package com.blankj.base.mvp;

import android.support.annotation.CallSuper;
import android.util.Log;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/08/02
 *     desc  :
 * </pre>
 */
public abstract class BaseModel {

    private static final String TAG = BaseView.TAG;

    public abstract void onCreate();

    @CallSuper
    public void onDestroy() {
        Log.i(TAG, "destroy model: " + getClass().getSimpleName());
    }
}
