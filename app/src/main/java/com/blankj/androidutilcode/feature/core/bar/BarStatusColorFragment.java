package com.blankj.androidutilcode.feature.core.bar;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.UtilsApp;
import com.blankj.androidutilcode.base.BaseFragment;
import com.blankj.utilcode.util.BarUtils;

import java.util.Random;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/07/01
 *     desc  : Bar 工具类 Demo
 * </pre>
 */
public class BarStatusColorFragment extends BaseFragment {

    private Random mRandom;
    private int    mColor;
    private int    mAlpha;

    private TextView mTvStatusAlpha;
    private SeekBar  sbChangeAlpha;
    private View     fakeStatusBar;

    public static BarStatusColorFragment newInstance() {
        return new BarStatusColorFragment();
    }

    @Override
    public void initData(Bundle bundle) {
        mRandom = new Random();
        mColor = ContextCompat.getColor(UtilsApp.getInstance(), R.color.colorPrimary);
        mAlpha = 112;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_bar_status_color;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        fakeStatusBar = view.findViewById(R.id.fake_status_bar);
        view.findViewById(R.id.btn_random_color).setOnClickListener(this);
        view.findViewById(R.id.btn_set_transparent).setOnClickListener(this);
        mTvStatusAlpha = (TextView) view.findViewById(R.id.tv_status_alpha);
        sbChangeAlpha = (SeekBar) view.findViewById(R.id.sb_change_alpha);
        sbChangeAlpha.setOnSeekBarChangeListener(colorListener);
        mTvStatusAlpha.setText(String.valueOf(mAlpha));

        updateFakeStatusBar();
    }


    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.btn_random_color:
                mColor = 0xff000000 | mRandom.nextInt(0xffffff);
                updateFakeStatusBar();
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
            updateFakeStatusBar();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    public void updateFakeStatusBar() {
        BarUtils.setStatusBarColor(fakeStatusBar, mColor, mAlpha);
    }
}
