package com.blankj.utilcode.pkg.feature.bar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;

import com.blankj.utilcode.pkg.R;
import com.blankj.lib.base.BaseBackActivity;
import com.blankj.utilcode.util.BarUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/13
 *     desc  : demo about BarUtils
 * </pre>
 */
public class BarNotificationActivity extends BaseBackActivity {

    private Handler mHandler = new Handler();

    public static void start(Context context) {
        Intent starter = new Intent(context, BarNotificationActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_bar_notification;
    }


    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        setTitle(R.string.demo_bar);

        findViewById(R.id.btn_show_notification).setOnClickListener(this);
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_show_notification) {
            BarUtils.setNotificationBarVisibility(true);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    BarUtils.setNotificationBarVisibility(false);
                }
            }, 2000);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
