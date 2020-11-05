package com.blankj.utilcode.pkg.feature.api.other.export;

import com.blankj.utilcode.util.ApiUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2019/07/10
 *     desc  : demo about ApiUtils
 * </pre>
 */
public abstract class OtherModuleApi extends ApiUtils.BaseApi {

    public abstract void invokeWithParams(ApiBean bean);

    public abstract ApiBean invokeWithReturnValue();

    public static class ApiBean {

        public String name;

        public ApiBean(String name) {
            this.name = name;
        }
    }
}
