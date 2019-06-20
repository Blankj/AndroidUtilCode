package com.blankj.subutil.pkg.impl;

import android.content.Context;

import com.blankj.subutil.export.api.SubUtilApi;
import com.blankj.subutil.pkg.feature.SubUtilActivity;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/06/09
 *     desc  :
 * </pre>
 */
public class SubUtilApiImpl implements SubUtilApi {

    @Override
    public void startSubUtilActivity(Context context) {
        SubUtilActivity.Companion.start(context);
    }

}
