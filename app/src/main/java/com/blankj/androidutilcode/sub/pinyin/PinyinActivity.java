package com.blankj.androidutilcode.sub.pinyin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseBackActivity;
import com.blankj.subutil.util.PinyinUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 17/02/01
 *     desc  : Pinyin工具类Demo
 * </pre>
 */
public class PinyinActivity extends BaseBackActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, PinyinActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_pinyin;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        getToolBar().setTitle(getString(R.string.demo_pinyin));

        TextView tvAboutPinyin = (TextView) findViewById(R.id.tv_about_pinyin);
        tvAboutPinyin.setText("测试拼音工具类"
                + "\n转拼音: " + PinyinUtils.ccs2Pinyin("测试拼音工具类", " ")
                + "\n获取首字母: " + PinyinUtils.getPinyinFirstLetters("测试拼音工具类", " ")
                + "\n澹台: " + PinyinUtils.getSurnamePinyin("澹台"));
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void onWidgetClick(View view) {

    }
}
