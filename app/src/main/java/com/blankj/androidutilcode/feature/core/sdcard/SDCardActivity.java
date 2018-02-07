package com.blankj.androidutilcode.feature.core.sdcard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseBackActivity;
import com.blankj.utilcode.util.SDCardUtils;
import com.blankj.utilcode.util.SpanUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/27
 *     desc  : SDCard 工具类 Demo
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
        tvAboutSdcard.setText(new SpanUtils()
                .appendLine("isSDCardEnable: " + SDCardUtils.isSDCardEnable())
                .appendLine("getSDCardPaths: " + SDCardUtils.getSDCardPaths())
                .appendLine("getInnerSDCardPaths: " + SDCardUtils.getSDCardPaths(true))
                .appendLine("getOuterSDCardPaths: " + SDCardUtils.getSDCardPaths(false))
                .create()
        );
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }
}
