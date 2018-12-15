package com.blankj.utilcode.pkg.feature.resource;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.pkg.Config;
import com.blankj.utilcode.pkg.R;
import com.blankj.lib.base.BaseBackActivity;
import com.blankj.utilcode.util.ResourceUtils;
import com.blankj.utilcode.util.SpanUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/05/07
 *     desc  :
 * </pre>
 */
public class ResourceActivity extends BaseBackActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, ResourceActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_resource;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        findViewById(R.id.btn_resource_test_assets).setOnClickListener(this);
        findViewById(R.id.btn_resource_test_raw).setOnClickListener(this);
        TextView tvAboutResource = findViewById(R.id.tv_about_resource);

        tvAboutResource.setText(new SpanUtils()
                .appendLine("readAssets2String: " + ResourceUtils.readAssets2String("test/test.txt"))
                .appendLine("readAssets2List: " + ResourceUtils.readAssets2List("test/test.txt").toString())
                .append("readRaw2List: " + ResourceUtils.readRaw2List(R.raw.test).toString())
                .create());
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_resource_test_assets) {
            ResourceUtils.copyFileFromAssets("test", Config.CACHE_PATH + "/assets/test");

        } else if (i == R.id.btn_resource_test_raw) {
            ResourceUtils.copyFileFromRaw(R.raw.test, Config.CACHE_PATH + "/raw/test.txt");

        }
    }
}
