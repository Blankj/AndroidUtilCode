package com.blankj.base.mvp;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
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
public abstract class BasePresenter<V extends BaseView> extends Utils.ActivityLifecycleCallbacks {

    private V                     mView;
    private Map<Class, BaseModel> mModelMap = new HashMap<>();

    public abstract void onAttachView();

    void bindView(V view) {
        this.mView = view;
        onAttachView();
        ActivityUtils.addActivityLifecycleCallbacks(mView.getActivity(), this);
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

    @Override
    public void onLifecycleChanged(@NonNull Activity activity, Lifecycle.Event event) {
        super.onLifecycleChanged(activity, event);
        if (event == Lifecycle.Event.ON_DESTROY) {
            destroyPresenter();
        }
        LogUtils.i("onLifecycleChanged: " + event);
    }

    private void destroyPresenter() {
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
    }
}
