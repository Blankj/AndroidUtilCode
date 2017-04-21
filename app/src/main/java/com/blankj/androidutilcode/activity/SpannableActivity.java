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
        tvAboutSpannable.setText(new SpannableStringUtils.Builder()
                .appendLine("测试SpannableStringUtils").setBold().setForegroundColor(Color.YELLOW).setBackgroundColor(Color.GRAY).setAlign(Layout.Alignment.ALIGN_CENTER)
                .append("测试")
                .append("前景色").setForegroundColor(Color.GREEN)
                .appendLine("背景色").setBackgroundColor(Color.RED)
                .appendLine("测试首行缩进").setLeadingMargin(30, 50)
                .appendLine("测试引用").setQuoteColor(Color.BLACK)
                .appendLine("测试列表项").setBullet(30, Color.BLACK)
                .appendLine("测试32dp字体").setFontSize(36, true)
                .append("测试")
                .appendLine("2倍字体").setFontProportion(2)
                .append("测试")
                .appendLine("横向2倍字体").setFontXProportion(2)
                .append("测试")
                .append("删除线").setStrikethrough()
                .appendLine("下划线").setUnderline()
                .append("测试")
                .append("上标").setSuperscript()
                .appendLine("下标").setSubscript()
                .append("测试")
                .append("粗体").setBold()
                .append("斜体").setItalic()
                .appendLine("粗斜体").setBoldItalic()
                .appendLine("monospace font").setFontFamily("monospace")
                .appendLine("serif font").setFontFamily("serif")
                .appendLine("sans-serif font").setFontFamily("sans-serif")
                .appendLine("测试正常对齐").setAlign(Layout.Alignment.ALIGN_NORMAL)
                .appendLine("测试居中对齐").setAlign(Layout.Alignment.ALIGN_CENTER)
                .appendLine("测试相反对齐").setAlign(Layout.Alignment.ALIGN_OPPOSITE)
                .append("测试")
                .appendLine("图片").setResourceId(R.mipmap.ic_launcher)
                .append("测试")
                .appendLine("点击事件").setClickSpan(clickableSpan)
                .append("测试")
                .appendLine("Url").setUrl("https://github.com/Blankj/AndroidUtilCode")
                .append("测试")
                .append("模糊").setBlur(3, BlurMaskFilter.Blur.NORMAL)
                .create()
        );
    }
}
