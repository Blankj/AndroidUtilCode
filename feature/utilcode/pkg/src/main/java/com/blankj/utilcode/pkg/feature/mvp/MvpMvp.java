package com.blankj.utilcode.pkg.feature.mvp;

import com.blankj.utilcode.util.Utils;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/11/26
 *     desc  :
 * </pre>
 */
public interface MvpMvp {

    interface View {
        void setLoadingVisible(boolean visible);

        void showMsg(CharSequence msg);
    }

    interface Presenter {
        void updateMsg();
    }

    interface Model {
        void requestUpdateMsg(final Utils.Consumer<String> consumer);
    }
}
