package com.blankj.androidutilcode.feature.sub.pinyin;

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
 *     desc  : Pinyin 工具类 Demo
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

        TextView tvAboutPinyin = findViewById(R.id.tv_about_pinyin);

        String surnames = "乐乘乜仇会便区单参句召员宓弗折曾朴查洗盖祭种秘繁缪能蕃覃解谌适都阿难黑";
        int size = surnames.length();
        StringBuilder sb = new StringBuilder("汉字转拼音: " + PinyinUtils.ccs2Pinyin("汉字转拼音", " ")
                + "\n获取首字母: " + PinyinUtils.getPinyinFirstLetters("获取首字母", " ")
                + "\n\n测试姓氏"
                + "\n澹台: " + PinyinUtils.getSurnamePinyin("澹台")
                + "\n尉迟: " + PinyinUtils.getSurnamePinyin("尉迟")
                + "\n万俟: " + PinyinUtils.getSurnamePinyin("万俟")
                + "\n单于: " + PinyinUtils.getSurnamePinyin("单于"));
        for (int i = 0; i < size; ++i) {
            String surname = String.valueOf(surnames.charAt(i));
            sb.append(String.format(
                    "\n%s 正确: %-6s 错误: %-6s",
                    surname,
                    PinyinUtils.getSurnamePinyin(surname),
                    PinyinUtils.ccs2Pinyin(surname)
            ));
        }
        tvAboutPinyin.setText(sb.toString());
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }
}
