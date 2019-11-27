package com.blankj.utilcode.pkg;

import android.content.Context;

import com.blankj.utilcode.export.api.UtilCodeApi;
import com.blankj.utilcode.pkg.feature.CoreUtilActivity;
import com.blankj.utilcode.util.ApiUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2019/07/01
 *     desc  :
 * </pre>
 */
@ApiUtils.Api
public class UtilCodeApiImpl extends UtilCodeApi {

    @Override
    public void startUtilCodeActivity(Context context) {
        CoreUtilActivity.Companion.start(context);
    }

    @Override
    public void testCallback(Callback callback) {
        if (callback != null) {
            callback.call();
        }
    }
}
