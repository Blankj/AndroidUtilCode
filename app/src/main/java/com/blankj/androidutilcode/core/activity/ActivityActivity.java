package com.blankj.androidutilcode.core.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.Config;
import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseBackActivity;
import com.blankj.androidutilcode.core.image.ImageActivity;
import com.blankj.utilcode.util.ActivityUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/13
 *     desc  : Activity工具类Demo
 * </pre>
 */
public class ActivityActivity extends BaseBackActivity {

    View viewSharedElement;

    public static void start(Context context) {
        Intent starter = new Intent(context, ActivityActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_activity;
    }


    @Override
    public void initView(Bundle savedInstanceState, View view) {
        getToolBar().setTitle(getString(R.string.demo_activity));
        viewSharedElement = findViewById(R.id.view_shared_element);
        findViewById(R.id.btn_start_image_activity).setOnClickListener(this);
        findViewById(R.id.btn_shared_element).setOnClickListener(this);
        findViewById(R.id.btn_finish_all_activity).setOnClickListener(this);
        TextView tvAboutActivity = (TextView) findViewById(R.id.tv_about_activity);
        tvAboutActivity.setText("Is ImageActivity Exists: " + ActivityUtils.isActivityExists(Config.PKG, ImageActivity.class.getName())
                + "\ngetLauncherActivity: " + ActivityUtils.getLauncherActivity(Config.PKG)
                + "\ngetTopActivity: " + ActivityUtils.getTopActivity()
        );
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start_image_activity:
                ActivityUtils.startActivity(Config.PKG, ImageActivity.class.getName());
                break;
            case R.id.btn_shared_element:
                ActivityUtils.startActivity(this, SharedElementActivityActivity.class, viewSharedElement);
                break;
            case R.id.btn_finish_all_activity:
                ActivityUtils.finishAllActivities();
                break;
        }
    }
}