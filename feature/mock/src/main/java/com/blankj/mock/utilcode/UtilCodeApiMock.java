package com.blankj.mock.utilcode;

import android.content.Context;

import com.blankj.utilcode.export.api.UtilCodeApi;
import com.blankj.utilcode.util.ApiUtils;
import com.blankj.utilcode.util.ToastUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2019/07/10
 *     desc  :
 * </pre>
 */
@ApiUtils.Api(isMock = true)
public class UtilCodeApiMock extends UtilCodeApi {

    @Override
    public void startUtilCodeActivity(Context context) {
        ToastUtils.showShort("startUtilCodeActivity");
    }

    @Override
    public void testCallback(Callback callback) {
        if (callback != null) {
            callback.call();
        }
    }

}
