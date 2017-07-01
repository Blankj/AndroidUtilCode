package com.blankj.androidutilcode.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseBackActivity;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/05/27
 *     desc  : Bar工具类Demo
 * </pre>
 */
public class BarActivity extends BaseBackActivity {

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_bar;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        getSupportActionBar().setTitle(getString(R.string.demo_bar));

        findViewById(R.id.btn_status_bar_color).setOnClickListener(this);
        findViewById(R.id.btn_status_bar_alpha).setOnClickListener(this);
        findViewById(R.id.btn_status_bar_image_view).setOnClickListener(this);
        findViewById(R.id.btn_status_bar_fragment).setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.btn_status_bar_color:
                startActivity(new Intent(this, StatusBarColorActivity.class));
                break;
            case R.id.btn_status_bar_alpha:
                startActivity(new Intent(this, StatusBarAlphaActivity.class));
                break;
            case R.id.btn_status_bar_image_view:
                startActivity(new Intent(this, StatusBarImageViewActivity.class));
                break;
            case R.id.btn_status_bar_fragment:
                startActivity(new Intent(this, StatusBarFragmentActivity.class));
                break;
        }
    }
}
