package com.blankj.androidutilcode.activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.utilcode.utils.SpannableStringUtils;
import com.blankj.utilcode.utils.ToastUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/9/27
 *     desc  : SDCard工具类Demo
 * </pre>
 */
public class SpannableActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spannable);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                ToastUtils.showShortToast("事件触发了");
            }
        };

        TextView tvAboutSpannable = (TextView) findViewById(R.id.tv_about_spannable);
        // 响应点击事件的话必须设置以下属性
        tvAboutSpannable.setMovementMethod(LinkMovementMethod.getInstance());
//        tvAboutSpannable.setHighlightColor(Color.BLUE);
        tvAboutSpannable.setText(SpannableStringUtils
                .getBuilder("测试SpannableStringUtils").setBold().setForegroundColor(Color.YELLOW).setBackgroundColor(Color.GRAY)
                .append("\n测试")
                .append("前景色").setForegroundColor(Color.GREEN)
                .append("\n测试")
                .append("背景色").setBackgroundColor(Color.RED)
                .append("\n测试")
                .append("2倍字体").setProportion(2)
                .append("\n测试")
                .append("删除线").setStrikethrough()
                .append("\n测试")
                .append("下划线").setUnderline()
                .append("\n测试")
                .append("上标").setSuperscript()
                .append("\n测试")
                .append("下标").setSubscript()
                .append("\n测试")
                .append("粗体").setBold()
                .append("\n测试")
                .append("斜体").setItalic()
                .append("\n测试")
                .append("粗斜体").setBoldItalic()
                .append("\n测试")
                .append("图片").setResourceId(R.mipmap.ic_launcher)
                .append("\n测试")
                .append("点击事件").setClickSpan(clickableSpan)
                .append("\n测试")
                .append("AndroidUtilCode").setUrl("https://github.com/Blankj/AndroidUtilCode")
                .create()
        );
    }
}
