package com.blankj.mock.api;

import android.content.Context;

import com.blankj.utilcode.export.api.UtilCodeApi;
import com.blankj.utilcode.util.ApiUtils;
import com.blankj.utilcode.util.ToastUtils;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/07/10
 *     desc  :
 * </pre>
 */
@ApiUtils.Api(isDebug = true)
public class UtilCodeMockApi extends UtilCodeApi {

    @Override
    public void startUtilCodeActivity(Context context) {
        ToastUtils.showShort("startUtilCodeActivity");
    }

}
