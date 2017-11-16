package com.blankj.androidutilcode.core.sdcard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseBackActivity;
import com.blankj.utilcode.util.SDCardUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/27
 *     desc  : SDCard工具类Demo
 * </pre>
 */
public class SDCardActivity extends BaseBackActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, SDCardActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_sdcard;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        getToolBar().setTitle(getString(R.string.demo_sdcard));

        TextView tvAboutSdcard = findViewById(R.id.tv_about_sdcard);
        tvAboutSdcard.setText("isSDCardEnable: " + SDCardUtils.isSDCardEnable()
                + "\ngetSDCardPaths: " + SDCardUtils.getSDCardPaths()
                + "\ngetInnerSDCardPaths: " + SDCardUtils.getSDCardPaths(true)
                + "\ngetOuterSDCardPaths: " + SDCardUtils.getSDCardPaths(false)
        );
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }
}
