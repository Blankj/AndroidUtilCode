package com.blankj.androidutilcode.core.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.androidutilcode.Config;
import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseBackActivity;
import com.blankj.androidutilcode.core.image.ImageActivity;
import com.blankj.utilcode.util.ActivityUtils;

import java.util.Random;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/13
 *     desc  : Activity工具类Demo
 * </pre>
 */
public class ActivityActivity extends BaseBackActivity {

    ImageView viewSharedElement;
    Random random = new Random();

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
        viewSharedElement = (ImageView) findViewById(R.id.view_shared_element);
        findViewById(R.id.btn_start_activity_cls).setOnClickListener(this);
        findViewById(R.id.btn_start_activity_cls_opt).setOnClickListener(this);
        findViewById(R.id.btn_start_activity_cls_anim).setOnClickListener(this);
        findViewById(R.id.btn_shared_element).setOnClickListener(this);
        findViewById(R.id.btn_start_home_activity).setOnClickListener(this);
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
            case R.id.btn_start_activity_cls:
                ActivityUtils.startActivity(SharedElementActivityActivity.class);
                break;
            case R.id.btn_start_activity_cls_opt:
                ActivityUtils.startActivity(SharedElementActivityActivity.class,
                        getOption(3));
                break;
            case R.id.btn_start_activity_cls_anim:
                ActivityUtils.startActivity(SharedElementActivityActivity.class,
                        R.anim.fade_in_1000, R.anim.fade_out_1000);
                break;
            case R.id.btn_shared_element:
                ActivityUtils.startActivity(this, SharedElementActivityActivity.class,
                        viewSharedElement);
                break;
            case R.id.btn_start_home_activity:
                ActivityUtils.startHomeActivity();
                break;
            case R.id.btn_finish_all_activity:
                ActivityUtils.finishAllActivities();
                break;
        }
    }

    private Bundle getOption(int type) {
        switch (type) {
            case 3:
                return ActivityOptionsCompat.makeThumbnailScaleUpAnimation(viewSharedElement,
                        viewSharedElement.getDrawingCache(),
                        viewSharedElement.getWidth() / 2,
                        viewSharedElement.getHeight() / 2).toBundle();
            case 2:
                return ActivityOptionsCompat.makeScaleUpAnimation(viewSharedElement,
                        viewSharedElement.getWidth() / 2, viewSharedElement.getHeight() / 2,
                        0, 0).toBundle();
            case 1:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    return ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                            viewSharedElement,
                            viewSharedElement.getTransitionName()).toBundle();
                }
            default:
                return ActivityOptionsCompat.makeCustomAnimation(this,
                        R.anim.slide_in_right_1000,
                        R.anim.slide_out_left_1000).toBundle();
        }
    }
}