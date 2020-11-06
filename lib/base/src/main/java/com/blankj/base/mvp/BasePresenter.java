package com.blankj.base.mvp;

import android.support.annotation.CallSuper;
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
public abstract class BasePresenter<V extends BaseView> {

    private static final String TAG = BaseView.TAG;

    private V                                          mView;
    private Map<Class<? extends BaseModel>, BaseModel> mModelMap = new HashMap<>();
    private boolean                                    isAlive   = true;

    public abstract void onBindView();

    void bindView(V view) {
        this.mView = view;
        onBindView();
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
            model.onCreate();
            return model;
        } catch (IllegalAccessException e) {
            Log.e("BasePresenter", "getModel", e);
        } catch (InstantiationException e) {
            Log.e("BasePresenter", "getModel", e);
        }
        return null;
    }

    @CallSuper
    public void onDestroy() {
        Log.i(TAG, "destroy presenter: " + getClass().getSimpleName());
        isAlive = false;
        for (BaseModel model : mModelMap.values()) {
            if (model != null) {
                model.onDestroy();
            }
        }
        mModelMap.clear();
    }

    public boolean isAlive() {
        return isAlive;
    }
}
