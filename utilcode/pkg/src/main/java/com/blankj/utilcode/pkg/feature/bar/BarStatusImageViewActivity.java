package com.blankj.utilcode.pkg.feature.bar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.blankj.utilcode.pkg.R;
import com.blankj.lib.base.BaseActivity;
import com.blankj.utilcode.util.BarUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/05/27
 *     desc  : demo about BarUtils
 * </pre>
 */
public class BarStatusImageViewActivity extends BaseActivity {

    private int mAlpha;

    private TextView mTvStatusAlpha;
    private SeekBar  sbChangeAlpha;

    public static void start(Context context) {
        Intent starter = new Intent(context, BarStatusImageViewActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        mAlpha = 112;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_bar_status_image_view;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        findViewById(R.id.btn_set_transparent).setOnClickListener(this);
        mTvStatusAlpha = findViewById(R.id.tv_status_alpha);
        sbChangeAlpha = findViewById(R.id.sb_change_alpha);
        sbChangeAlpha.setOnSeekBarChangeListener(translucentListener);
        mTvStatusAlpha.setText(String.valueOf(mAlpha));

        updateStatusBar();
    }

    @Override
    public void doBusiness() {

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
        BarUtils.setStatusBarAlpha(BarStatusImageViewActivity.this, mAlpha, true);
    }
}
