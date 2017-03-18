package com.blankj.androidutilcode.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.utilcode.util.PinyinUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 17/02/01
 *     desc  : Pinyin工具类Demo
 * </pre>
 */
public class PinyinActivity extends Activity {

    private TextView tvAboutPinyin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinyin);

        tvAboutPinyin = (TextView) findViewById(R.id.tv_about_pinyin);

        tvAboutPinyin.setText("测试拼音工具类"
                + "\n转拼音: " + PinyinUtils.ccs2Pinyin("测试拼音工具类", " ")
                + "\n获取首字母: " + PinyinUtils.getPinyinFirstLetters("测试拼音工具类", " ")
                + "\n澹台: " + PinyinUtils.getSurnamePinyin("澹台"));
    }
}
