package com.blankj.androidutilcode.core.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.Window;
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
    private Bitmap bitmap;

    public static void start(Context context) {
        Intent starter = new Intent(context, ActivityActivity.class);
        ContextCompat.startActivity(context, starter,
                ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context).toBundle());
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        return R.layout.activity_activity;
    }


    @Override
    public void initView(Bundle savedInstanceState, View view) {
        getToolBar().setTitle(getString(R.string.demo_activity));
        viewSharedElement = (ImageView) findViewById(R.id.view_shared_element);
        findViewById(R.id.btn_cls).setOnClickListener(this);
        findViewById(R.id.btn_cls_opt_anim).setOnClickListener(this);
        findViewById(R.id.btn_cls_opt_shared).setOnClickListener(this);
        findViewById(R.id.btn_cls_opt_scale_up).setOnClickListener(this);
        findViewById(R.id.btn_cls_opt_thumbnail_scale_up).setOnClickListener(this);
        findViewById(R.id.btn_cls_opt_clip_reveal).setOnClickListener(this);
        findViewById(R.id.btn_cls_anim).setOnClickListener(this);
        findViewById(R.id.btn_shared_element).setOnClickListener(this);
        findViewById(R.id.btn_start_home_activity).setOnClickListener(this);
        findViewById(R.id.btn_finish_all_activity).setOnClickListener(this);
        TextView tvAboutActivity = (TextView) findViewById(R.id.tv_about_activity);
        tvAboutActivity.setText("Is ImageActivity Exists: " + ActivityUtils.isActivityExists(Config.PKG, ImageActivity.class.getName())
                + "\ngetLauncherActivity: " + ActivityUtils.getLauncherActivity(Config.PKG)
                + "\ngetTopActivity: " + ActivityUtils.getTopActivity()
        );
        bitmap = ((BitmapDrawable) viewSharedElement.getDrawable()).getBitmap();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Explode explode = (Explode) TransitionInflater.from(this).inflateTransition(R.transition.explode_1000);
            Fade fade = (Fade) TransitionInflater.from(this).inflateTransition(R.transition.fade_1000);
            getWindow().setEnterTransition(explode);
            getWindow().setReturnTransition(fade);
        }
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cls:
                ActivityUtils.startActivity(SharedElementActivityActivity.class);
                break;
            case R.id.btn_cls_opt_anim:
                ActivityUtils.startActivity(SharedElementActivityActivity.class,
                        getOption(0));
                break;
            case R.id.btn_cls_opt_shared:
                ActivityUtils.startActivity(SharedElementActivityActivity.class,
                        getOption(1));
                break;
            case R.id.btn_cls_opt_scale_up:
                ActivityUtils.startActivity(SharedElementActivityActivity.class,
                        getOption(2));
                break;
            case R.id.btn_cls_opt_thumbnail_scale_up:
                ActivityUtils.startActivity(SharedElementActivityActivity.class,
                        getOption(3));
                break;
            case R.id.btn_cls_opt_clip_reveal:
                ActivityUtils.startActivity(SharedElementActivityActivity.class,
                        getOption(4));
                break;
            case R.id.btn_cls_anim:
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
            default:
            case 0:
                return ActivityOptionsCompat.makeCustomAnimation(this,
                        R.anim.slide_in_right_1000,
                        R.anim.slide_out_left_1000)
                        .toBundle();
            case 1:
                return ActivityOptionsCompat.makeSceneTransitionAnimation(this
//                        ,
//                        viewSharedElement,
//                        getString(R.string.activity_shared_element)
                )
                        .toBundle();
            case 2:
                return ActivityOptionsCompat.makeScaleUpAnimation(viewSharedElement,
                        viewSharedElement.getWidth() / 2,
                        viewSharedElement.getHeight() / 2,
                        0, 0)
                        .toBundle();
            case 3:
                return ActivityOptionsCompat.makeThumbnailScaleUpAnimation(viewSharedElement,
                        bitmap,
                        0, 0)
                        .toBundle();
            case 4:
                return ActivityOptionsCompat.makeClipRevealAnimation(viewSharedElement,
                        viewSharedElement.getWidth() / 2,
                        viewSharedElement.getHeight() / 2,
                        0, 0)
                        .toBundle();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        }
    }
}