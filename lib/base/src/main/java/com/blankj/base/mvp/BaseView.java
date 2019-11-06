package com.blankj.base.mvp;

import android.arch.lifecycle.Lifecycle;
import android.support.v4.app.FragmentActivity;

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

    private Lifecycle        mLifecycle;
    private FragmentActivity mActivity;
    Map<Class, BasePresenter<V>> mPresenterMap = new HashMap<>();

    public V bindActivity(FragmentActivity activity) {
        mLifecycle = activity.getLifecycle();
        for (BasePresenter<V> presenter : mPresenterMap.values()) {
            mLifecycle.addObserver(presenter);
        }
        onCreateView();
        //noinspection unchecked
        return (V) this;
    }

    public <T extends FragmentActivity> T getActivity() {
        //noinspection unchecked
        return (T) mActivity;
    }

    public V addPresenter(BasePresenter<V> presenter) {
        mPresenterMap.put(presenter.getClass(), presenter);
        //noinspection unchecked
        presenter.setView((V) this);
        if (mLifecycle != null) {
            mLifecycle.addObserver(presenter);
        }
        //noinspection unchecked
        return (V) this;
    }

    public <P extends BasePresenter<V>> P getPresenter(Class<P> presenterClass) {
        BasePresenter<V> basePresenter = mPresenterMap.get(presenterClass);
        if (basePresenter != null) {
            //noinspection unchecked
            return (P) basePresenter;
        }
        return null;
    }

    public abstract void onCreateView();

    public abstract void onDestroyView();
}
