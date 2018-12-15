package com.blankj.utilcode.pkg.feature.metaData;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.pkg.R;
import com.blankj.lib.base.BaseBackActivity;
import com.blankj.utilcode.util.MetaDataUtils;
import com.blankj.utilcode.util.SpanUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/05/15
 *     desc  : demo about MetaDataUtils
 * </pre>
 */
public class MetaDataActivity extends BaseBackActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, MetaDataActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_meta_data;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        setTitle(R.string.demo_meta_data);

        TextView tvAboutMetaData = findViewById(R.id.tv_about_metadata);
        tvAboutMetaData.setText(new SpanUtils()
                .appendLine("getMetaDataInApp: " + MetaDataUtils.getMetaDataInApp("app_meta_data"))
                .append("getMetaDataInActivity: " + MetaDataUtils.getMetaDataInActivity(this, "activity_meta_data"))
                .create());
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }
}
