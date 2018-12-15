package com.blankj.utilcode.pkg.feature.sdcard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.pkg.R;
import com.blankj.lib.base.BaseBackActivity;
import com.blankj.utilcode.util.SDCardUtils;
import com.blankj.utilcode.util.SpanUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/27
 *     desc  : demo about SDCardUtils
 * </pre>
 */
public class SDCardActivity extends BaseBackActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, SDCardActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_sdcard;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        setTitle(R.string.demo_sdcard);

        TextView tvAboutSdcard = findViewById(R.id.tv_about_sdcard);
        tvAboutSdcard.setText(new SpanUtils()
                .appendLine("isSDCardEnableByEnvironment: " + SDCardUtils.isSDCardEnableByEnvironment())
                .appendLine("getSDCardPathByEnvironment: " + SDCardUtils.getSDCardPathByEnvironment())
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
