package com.blankj.utilcode.pkg.feature.mvp;

import com.blankj.base.mvp.BasePresenter;
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
        implements IMvp.Presenter {

    @Override
    public void onAttachView() {
    }

    @Override
    public void updateMsg() {
        getView().setLoadingVisible(true);
        getModel(MvpModel.class).requestUpdateMsg(new Utils.Consumer<String>() {
            @Override
            public void accept(String s) {
                getView().showMsg(s);
                getView().setLoadingVisible(false);
            }
        });
    }
}
