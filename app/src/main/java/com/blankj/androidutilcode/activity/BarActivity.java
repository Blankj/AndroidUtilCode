package com.blankj.androidutilcode.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseDrawerActivity;
import com.blankj.utilcode.util.BarUtils;

import java.util.Random;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/05/27
 *     desc  :
 * </pre>
 */
public class BarActivity extends BaseDrawerActivity {

    private int      mColor;
    private int      mAlpha;
    private Random   mRandom;
    private TextView mTvStatusAlpha;

    @Override
    public void initData(Bundle bundle) {
        mRandom = new Random();
        mColor = ContextCompat.getColor(this, R.color.colorPrimary);
        mAlpha = 112;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_bar;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        findViewById(R.id.btn_set_color).setOnClickListener(this);
        mTvStatusAlpha = (TextView) findViewById(R.id.tv_status_alpha);
        SeekBar sbChangeAlpha = (SeekBar) findViewById(R.id.sb_change_alpha);

        sbChangeAlpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mAlpha = progress;
                BarUtils.setColor(BarActivity.this, mColor, mAlpha);
                mTvStatusAlpha.setText(String.valueOf(mAlpha));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sbChangeAlpha.setProgress(mAlpha);
        updateStatusBar();
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.btn_set_color:
                mColor = 0xff000000 | mRandom.nextInt(0xffffff);
                updateStatusBar();
                break;
        }
    }

    private void updateStatusBar() {
        mToolbar.setBackgroundColor(mColor);
        BarUtils.setColor(this, mColor, mAlpha);
    }
}
