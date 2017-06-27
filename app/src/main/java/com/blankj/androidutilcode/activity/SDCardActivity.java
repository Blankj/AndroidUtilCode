package com.blankj.androidutilcode.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseDrawerActivity;
import com.blankj.utilcode.util.SDCardUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/27
 *     desc  : SDCard工具类Demo
 * </pre>
 */
public class SDCardActivity extends BaseDrawerActivity {

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_sdcard;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        TextView tvAboutSdcard = (TextView) findViewById(R.id.tv_about_sdcard);
        tvAboutSdcard.setText("isSDCardEnable: " + SDCardUtils.isSDCardEnable()
                + "\ngetDataPath: " + SDCardUtils.getDataPath()
                + "\ngetSDCardPath: " + SDCardUtils.getSDCardPath()
                + "\ngetFreeSpace: " + SDCardUtils.getFreeSpace()
                + "\ngetSDCardInfo: " + SDCardUtils.getSDCardInfo()
        );
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void onWidgetClick(View view) {

    }
}
