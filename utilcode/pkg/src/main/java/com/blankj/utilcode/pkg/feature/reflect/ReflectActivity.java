package com.blankj.utilcode.pkg.feature.reflect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.pkg.R;
import com.blankj.lib.base.BaseBackActivity;
import com.blankj.utilcode.util.ReflectUtils;
import com.blankj.utilcode.util.SpanUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/01/29
 *     desc  : demo about ReflectUtils
 * </pre>
 */
public class ReflectActivity extends BaseBackActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, ReflectActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_reflect;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        setTitle(R.string.demo_reflect);

        TextView tvAboutReflect = findViewById(R.id.tv_about_reflect);

        tvAboutReflect.setText(new SpanUtils()
                .appendLine("before reflect: " + ReflectUtils.reflect(TestPrivateStaticFinal.class).field("I1").get())
                .append("after reflect: " + ReflectUtils.reflect(TestPrivateStaticFinal.class).field("I1", 2).field("I1").get())
                .create());
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }
}
