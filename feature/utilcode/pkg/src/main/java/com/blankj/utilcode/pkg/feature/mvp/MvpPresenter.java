package com.blankj.utilcode.pkg.feature.mvp;

import com.blankj.base.mvp.BasePresenter;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/11/26
 *     desc  :
 * </pre>
 */
public class MvpPresenter extends BasePresenter<MvpView>
        implements MvpMvp.Presenter {

    @Override
    public void onBindView() {
    }

    @Override
    public void updateMsg() {
        getView().setLoadingVisible(true);
        getModel(MvpModel.class).requestUpdateMsg(new Utils.Consumer<String>() {
            @Override
            public void accept(String s) {
                if (isAlive()) {
                    getView().showMsg(s);
                    getView().setLoadingVisible(false);
                } else {
                    LogUtils.iTag(MvpView.TAG, "destroyed");
                }
            }
        });
    }
}
