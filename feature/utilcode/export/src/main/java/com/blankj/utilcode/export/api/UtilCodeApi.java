package com.blankj.utilcode.export.api;

import android.content.Context;

import com.blankj.utilcode.util.ApiUtils;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2019/07/01
 *     desc  :
 * </pre>
 */
public abstract class UtilCodeApi extends ApiUtils.BaseApi {

    public abstract void startUtilCodeActivity(Context context);

    public abstract void testCallback(Callback callback);

    public interface Callback {
        void call();
    }

}