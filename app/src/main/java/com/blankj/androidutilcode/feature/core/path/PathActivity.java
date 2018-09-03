package com.blankj.androidutilcode.feature.core.path;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseBackActivity;
import com.blankj.utilcode.util.SpanUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/13
 *     desc  : demo about PathUtils
 * </pre>
 */
public class PathActivity extends BaseBackActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, PathActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_path;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        getToolBar().setTitle(getString(R.string.demo_path));

        TextView tvAboutMetaData = findViewById(R.id.tv_about_path);
        tvAboutMetaData.setText(new SpanUtils()
//                .appendLine("getRootPath: " + PathUtils.getRootPath())
                .append("")
                .create());
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }
}
