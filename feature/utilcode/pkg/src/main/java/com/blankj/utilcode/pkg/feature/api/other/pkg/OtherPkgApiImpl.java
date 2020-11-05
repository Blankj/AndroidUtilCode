package com.blankj.utilcode.pkg.feature.api.other.pkg;

import com.blankj.utilcode.pkg.feature.api.other.export.OtherModuleApi;
import com.blankj.utilcode.util.ApiUtils;
import com.blankj.utilcode.util.ToastUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2019/07/10
 *     desc  : demo about ApiUtils
 * </pre>
 */
@ApiUtils.Api
public class OtherPkgApiImpl extends OtherModuleApi {

    @Override
    public void invokeWithParams(ApiBean bean) {
        ToastUtils.showShort(bean.name);
    }

    @Override
    public ApiBean invokeWithReturnValue() {
        String value = "value";
        return new ApiBean(value);
    }
}
