package com.blankj.base.mvp;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

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
public class BaseView<V extends BaseView> implements LifecycleObserver {

    public static final String TAG = "UtilsMVP";

    private FragmentActivity                mActivity;
    private Fragment                        mFragment;
    private Lifecycle                       mLifecycle;
    private Map<Class<?>, BasePresenter<V>> mPresenterMap = new HashMap<>();

    public BaseView(Fragment fragment) {
        mFragment = fragment;
        mActivity = fragment.getActivity();
        mLifecycle = mFragment.getLifecycle();
        addLifecycle(this);
    }

    public BaseView(FragmentActivity activity) {
        mActivity = activity;
        mLifecycle = mActivity.getLifecycle();
        addLifecycle(this);
    }

    public BaseView(Lifecycle lifecycle) {
        mLifecycle = lifecycle;
        addLifecycle(this);
    }

    public <T extends FragmentActivity> T getActivity() {
        if (mActivity == null) {
            return null;
        }
        //noinspection unchecked
        return (T) mActivity;
    }

    public <T extends Fragment> T getFragment() {
        if (mFragment == null) {
            return null;
        }
        //noinspection unchecked
        return (T) mFragment;
    }

    public V addPresenter(BasePresenter<V> presenter) {
        if (presenter == null) return (V) this;
        mPresenterMap.put(presenter.getClass(), presenter);
        //noinspection unchecked
        presenter.bindView((V) this);
        return (V) this;
    }

    public <P extends BasePresenter<V>> P getPresenter(Class<P> presenterClass) {
        if (presenterClass == null) {
            throw new IllegalArgumentException("presenterClass is null!");
        }
        BasePresenter<V> basePresenter = mPresenterMap.get(presenterClass);
        if (basePresenter == null) {
            throw new IllegalArgumentException("presenter of <" + presenterClass.getSimpleName() + "> is not added!");
        }
        //noinspection unchecked
        return (P) basePresenter;
    }

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        Log.i(TAG, "destroy view: " + getClass().getSimpleName());
        removeLifecycle(this);
        for (BasePresenter<V> presenter : mPresenterMap.values()) {
            if (presenter != null) {
                presenter.onDestroy();
            }
        }
        mPresenterMap.clear();
    }

    private void addLifecycle(LifecycleObserver observer) {
        if (mLifecycle == null) {
            Log.w(TAG, "addLifecycle: mLifecycle is null");
            return;
        }
        mLifecycle.addObserver(observer);
    }

    private void removeLifecycle(LifecycleObserver observer) {
        if (mLifecycle == null) {
            Log.w(TAG, "removeLifecycle: mLifecycle is null");
            return;
        }
        mLifecycle.removeObserver(observer);
    }
}
