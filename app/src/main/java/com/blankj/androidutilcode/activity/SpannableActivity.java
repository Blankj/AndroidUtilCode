package com.blankj.androidutilcode.activity;

import android.app.Activity;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.utilcode.util.SpannableStringUtils;
import com.blankj.utilcode.util.ToastUtils;

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

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };

        TextView tvAboutSpannable = (TextView) findViewById(R.id.tv_about_spannable);
        // 响应点击事件的话必须设置以下属性
        tvAboutSpannable.setMovementMethod(LinkMovementMethod.getInstance());
        tvAboutSpannable.setText(SpannableStringUtils
                .getBuilder("测试SpannableStringUtils\n")
                .setBold().setForegroundColor(Color.YELLOW).setBackgroundColor(Color.GRAY).setAlign(Layout.Alignment.ALIGN_CENTER)
                .append("测试")
                .append("前景色").setForegroundColor(Color.GREEN)
                .append("背景色\n").setBackgroundColor(Color.RED)
                .append("测试首行缩进\n").setLeadingMargin(30, 50)
                .append("测试引用\n").setQuoteColor(Color.YELLOW)
                .append("测试列表项\n").setBullet(30, Color.YELLOW)
                .append("测试")
                .append("2倍字体\n").setProportion(2)
                .append("测试")
                .append("横向2倍字体\n").setXProportion(2)
                .append("测试")
                .append("删除线").setStrikethrough()
                .append("下划线\n").setUnderline()
                .append("测试")
                .append("上标").setSuperscript()
                .append("下标\n").setSubscript()
                .append("测试")
                .append("粗体").setBold()
                .append("斜体").setItalic()
                .append("粗斜体\n").setBoldItalic()
                .append("monospace font\n").setFontFamily("monospace")
                .append("serif font\n").setFontFamily("serif")
                .append("sans-serif font\n").setFontFamily("sans-serif")
                .append("测试正常对齐\n").setAlign(Layout.Alignment.ALIGN_NORMAL)
                .append("测试居中对齐\n").setAlign(Layout.Alignment.ALIGN_CENTER)
                .append("测试相反对齐\n").setAlign(Layout.Alignment.ALIGN_OPPOSITE)
                .append("测试")
                .append("图片\n").setResourceId(R.mipmap.ic_launcher)
                .append("测试")
                .append("点击事件\n").setClickSpan(clickableSpan)
                .append("测试")
                .append("Url\n").setUrl("https://github.com/Blankj/AndroidUtilCode")
                .append("测试")
                .append("模糊字体\n").setBlur(3, BlurMaskFilter.Blur.NORMAL)
                .create()
        );
    }
}
