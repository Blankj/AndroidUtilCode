package com.blankj.base.mvp;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.CallSuper;

import com.blankj.utilcode.util.LogUtils;

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
public abstract class BasePresenter<V extends BaseView> implements LifecycleObserver {

    private V                     mView;
    private Map<Class, BaseModel> mModelMap = new HashMap<>();

    public abstract void onAttachView();

    void bindView(V view) {
        this.mView = view;
        onAttachView();
    }

    public V getView() {
        return mView;
    }

    public <M extends BaseModel> M getModel(Class<M> modelClass) {
        BaseModel baseModel = mModelMap.get(modelClass);
        if (baseModel != null) {
            //noinspection unchecked
            return (M) baseModel;
        }
        try {
            M model = modelClass.newInstance();
            mModelMap.put(modelClass, model);
            model.onCreateModel();
            return model;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroyPresenter() {
        if (mView != null) {
            mView.mPresenterMap.remove(this.getClass());
            mView.onDestroyView();
        }
        for (BaseModel model : mModelMap.values()) {
            if (model != null) {
                model.destroy();
            }
        }
        mModelMap.clear();
        LogUtils.e("onDestroyPresenter");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    public void onLifecycleChanged(LifecycleOwner owner, Lifecycle.Event event) {
        LogUtils.e(event.toString());
    }
}
