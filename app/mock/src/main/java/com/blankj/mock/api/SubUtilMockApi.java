package com.blankj.mock.api;

import android.content.Context;

import com.blankj.subutil.export.api.SubUtilApi;
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
public class SubUtilMockApi extends SubUtilApi {

    @Override
    public void startSubUtilActivity(Context context) {
        ToastUtils.showShort("startSubUtilActivity");
    }

}
