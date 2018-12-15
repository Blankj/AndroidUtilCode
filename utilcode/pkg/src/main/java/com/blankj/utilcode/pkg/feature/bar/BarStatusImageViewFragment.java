package com.blankj.utilcode.pkg.feature.bar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.blankj.utilcode.pkg.R;
import com.blankj.lib.base.BaseLazyFragment;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/07/01
 *     desc  : demo about BarUtils
 * </pre>
 */
public class BarStatusImageViewFragment extends BaseLazyFragment {

    private int mAlpha;

    private TextView mTvStatusAlpha;
    private SeekBar  sbChangeAlpha;
    private View     fakeStatusBar;

    public static BarStatusImageViewFragment newInstance() {
        return new BarStatusImageViewFragment();
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        mAlpha = 112;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_bar_status_image_view;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        fakeStatusBar = findViewById(R.id.fake_status_bar);
        mTvStatusAlpha = findViewById(R.id.tv_status_alpha);
        sbChangeAlpha = findViewById(R.id.sb_change_alpha);
        findViewById(R.id.btn_set_transparent).setOnClickListener(this);
        sbChangeAlpha.setOnSeekBarChangeListener(translucentListener);
        mTvStatusAlpha.setText(String.valueOf(mAlpha));

        updateFakeStatusBar();
    }

    @Override
    public void doLazyBusiness() {
        LogUtils.d("doLazyBusiness() called");
    }

    @Override
    public void onWidgetClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_set_transparent) {
            sbChangeAlpha.setProgress(0);

        }
    }

    private SeekBar.OnSeekBarChangeListener translucentListener = new SeekBar.OnSeekBarChangeListener() {
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
        BarUtils.setStatusBarAlpha(fakeStatusBar, mAlpha);
    }
}
