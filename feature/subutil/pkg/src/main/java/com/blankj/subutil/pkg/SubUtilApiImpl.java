package com.blankj.subutil.pkg;

import android.content.Context;

import com.blankj.subutil.export.api.SubUtilApi;
import com.blankj.subutil.pkg.feature.SubUtilActivity;
import com.blankj.utilcode.util.ApiUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2019/07/02
 *     desc  :
 * </pre>
 */
@ApiUtils.Api
public class SubUtilApiImpl extends SubUtilApi {

    @Override
    public void startSubUtilActivity(Context context) {
        SubUtilActivity.Companion.start(context);
    }

}
