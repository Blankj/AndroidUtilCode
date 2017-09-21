package com.blankj.androidutilcode.core.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseActivity;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/13
 *     desc  : Activity工具类Demo
 * </pre>
 */
public class SharedElementActivityActivity extends BaseActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, SharedElementActivityActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_activity_shared_element;
    }


    @Override
    public void initView(Bundle savedInstanceState, View view) {

    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCompat.finishAfterTransition(this);
    }
}