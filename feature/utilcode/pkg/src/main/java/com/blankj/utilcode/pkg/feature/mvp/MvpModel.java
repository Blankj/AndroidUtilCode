package com.blankj.utilcode.pkg.feature.mvp;

import com.blankj.base.mvp.BaseModel;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.Utils;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/11/26
 *     desc  :
 * </pre>
 */
public class MvpModel extends BaseModel implements IMvp.Model {

    private int index;

    @Override
    public void onCreateModel() {
        index = 0;
    }

    @Override
    public void onDestroyModel() {

    }

    @Override
    public void requestUpdateMsg(final Utils.Consumer<String> consumer) {
        ThreadUtils.executeByCached(addAutoDestroyTask(new ThreadUtils.SimpleTask<String>() {
            @Override
            public String doInBackground() throws Throwable {
                Thread.sleep(2000);
                return "msg: " + index++;
            }

            @Override
            public void onSuccess(String result) {
                consumer.accept(result);
            }
        }));
    }
}
