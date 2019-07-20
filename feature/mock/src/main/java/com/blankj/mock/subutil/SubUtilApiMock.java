package com.blankj.mock.subutil;

import android.content.Context;

import com.blankj.subutil.export.api.SubUtilApi;
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
public class SubUtilApiMock extends SubUtilApi {

    @Override
    public void startSubUtilActivity(Context context) {
        ToastUtils.showShort("startSubUtilActivity");
    }

}
