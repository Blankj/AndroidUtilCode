package com.blankj.androidutilcode.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseBackActivity;
import com.blankj.utilcode.util.BarUtils;

import java.util.Random;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/05/27
 *     desc  : Bar工具类Demo分支
 * </pre>
 */
public class BarBranchActivity extends BaseBackActivity
        implements SeekBar.OnSeekBarChangeListener {

    public static final String BRANCH_BAR_COLOR = "branch_bar_color";


    private int      mColor;
    private int      mAlpha;
    private Random   mRandom;
    private TextView mTvStatusAlpha;
    private String   branch;

    @Override
    public void initData(Bundle bundle) {

        branch = bundle.getString("branch");

        mRandom = new Random();
        mColor = ContextCompat.getColor(this, R.color.colorPrimary);
        mAlpha = 112;
    }

    @Override
    public int bindLayout() {
        int layoutId = 0;
        if (branch.equals(BRANCH_BAR_COLOR)) {
            layoutId = R.layout.activity_bar_color;
        }
        return layoutId;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        getSupportActionBar().setTitle(getString(R.string.demo_bar));

        if (branch.equals(BRANCH_BAR_COLOR)) {
            findViewById(R.id.btn_set_color).setOnClickListener(this);
            mTvStatusAlpha = (TextView) findViewById(R.id.tv_status_alpha);
            SeekBar sbChangeAlpha = (SeekBar) findViewById(R.id.sb_change_alpha);
            sbChangeAlpha.setOnSeekBarChangeListener(this);
            sbChangeAlpha.setProgress(mAlpha);
            updateStatusBar();
        }
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


    ///////////////////////////////////////////////////////////////////////////
    // OnSeekBarChangeListener
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mAlpha = progress;
        BarUtils.setColor(BarBranchActivity.this, mColor, mAlpha);
        mTvStatusAlpha.setText(String.valueOf(mAlpha));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
    ///////////////////////////////////////////////////////////////////////////
    // OnSeekBarChangeListener
    ///////////////////////////////////////////////////////////////////////////
}
