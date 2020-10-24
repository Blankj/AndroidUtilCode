package com.blankj.base.mvp;

import android.util.Log;

import androidx.annotation.CallSuper;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/08/02
 *     desc  :
 * </pre>
 */
public abstract class BaseModel {

    LiveData<Object> mData = new MutableLiveData<>();

    private static final String TAG = BaseView.TAG;

    public abstract void onCreate();

    @CallSuper
    public void onDestroy() {
        Log.i(TAG, "destroy model: " + getClass().getSimpleName());
    }
}
