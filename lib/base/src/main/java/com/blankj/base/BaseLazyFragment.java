package com.blankj.base;

import android.util.Log;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/03/23
 *     desc  : base about lazy fragment
 * </pre>
 */
public abstract class BaseLazyFragment extends BaseFragment {

    private static final String TAG = "BaseLazyFragment";

    private boolean isDataLoaded;

    public abstract void doLazyBusiness();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.d(TAG, "setUserVisibleHint: " + isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mContentView != null && !isDataLoaded) {
            doLazyBusiness();
            isDataLoaded = true;
        }
    }

    @Override
    public void doBusiness() {
        if (getUserVisibleHint() && !isDataLoaded) {
            doLazyBusiness();
            isDataLoaded = true;
        }
    }
}
