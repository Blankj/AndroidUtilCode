package com.blankj.base.mvp;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/08/02
 *     desc  :
 * </pre>
 */
public abstract class BaseView<V extends BaseView> {

    private FragmentActivity mActivity;
    private Fragment         mFragment;
    Map<Class, BasePresenter<V>> mPresenterMap = new HashMap<>();

    public abstract void onDestroyView();

    public BaseView(FragmentActivity activity) {
        mActivity = activity;
    }

    public BaseView(Fragment fragment) {
        mFragment = fragment;
        mActivity = fragment.getActivity();
    }

    public <T extends FragmentActivity> T getActivity() {
        //noinspection unchecked
        return (T) mActivity;
    }

    public <T extends Fragment> T getFragment() {
        //noinspection unchecked
        return (T) mFragment;
    }

    public void addPresenter(BasePresenter<V> presenter) {
        mPresenterMap.put(presenter.getClass(), presenter);
        //noinspection unchecked
        presenter.bindView((V) this);
    }

    public <P extends BasePresenter<V>> P getPresenter(Class<P> presenterClass) {
        BasePresenter<V> basePresenter = mPresenterMap.get(presenterClass);
        if (basePresenter != null) {
            //noinspection unchecked
            return (P) basePresenter;
        }
        return null;
    }
}
