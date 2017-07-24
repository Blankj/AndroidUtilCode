package com.blankj.androidutilcode.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.UtilsApp;
import com.blankj.androidutilcode.base.BaseDrawerActivity;
import com.blankj.utilcode.util.BarUtils;

import java.util.Random;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/05/27
 *     desc  : Bar工具类Demo
 * </pre>
 */
public class StatusBarDrawerActivity extends BaseDrawerActivity {

    private Random mRandom;
    private int    mColor;
    private int    mAlpha;

    private TextView mTvStatusAlpha;
    private SeekBar  sbChangeAlpha;

    public static void start(Context context) {
        Intent starter = new Intent(context, StatusBarDrawerActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(Bundle bundle) {
        mRandom = new Random();
        mColor = ContextCompat.getColor(UtilsApp.getInstance(), R.color.colorPrimary);
        mAlpha = 112;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_status_bar_drawer;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        updateStatusBar();
    }


    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.btn_random_color:
                mColor = 0xff000000 | mRandom.nextInt(0xffffff);
                updateStatusBar();
                break;
            case R.id.btn_set_transparent:
                sbChangeAlpha.setProgress(0);
                break;
        }
    }

    private SeekBar.OnSeekBarChangeListener colorListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            mAlpha = progress;
            mTvStatusAlpha.setText(String.valueOf(mAlpha));
            updateStatusBar();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private void updateStatusBar() {
        BarUtils.setColorForDrawerLayout(StatusBarDrawerActivity.this, (DrawerLayout) findViewById(R.id.drawer_layout), mColor, mAlpha);
    }
}
