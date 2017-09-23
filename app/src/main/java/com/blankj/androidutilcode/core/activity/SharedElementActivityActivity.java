package com.blankj.androidutilcode.core.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.Window;

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
        return R.layout.activity_activity_shared_element;
    }


    @Override
    public void initView(Bundle savedInstanceState, View view) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Explode explode = (Explode) TransitionInflater.from(this).inflateTransition(R.transition.explode_1000);
            Fade fade = (Fade) TransitionInflater.from(this).inflateTransition(R.transition.fade_1000);
            getWindow().setEnterTransition(fade);
            getWindow().setExitTransition(explode);
        }
        findViewById(android.R.id.content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedElementActivityActivity.start(SharedElementActivityActivity.this);
            }
        });
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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        }
    }
}