package com.blankj.androidutilcode.activity;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseActivity;
import com.blankj.utilcode.util.SpannableStringUtils;
import com.blankj.utilcode.util.ToastUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/27
 *     desc  : SpannableString工具类Demo
 * </pre>
 */
public class SpannableActivity extends BaseActivity {

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_spannable;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                ToastUtils.showShort("事件触发了");
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
                .appendLine("测试SpannableStringUtils").setBackgroundColor(Color.LTGRAY).setBold().setForegroundColor(Color.YELLOW).setAlign(Layout.Alignment.ALIGN_CENTER)
                .append("测试")
                .append("前景色").setForegroundColor(Color.GREEN)
                .appendLine("背景色").setBackgroundColor(Color.LTGRAY)
                .appendLine("测试首行缩进").setLeadingMargin(30, 50)
                .appendLine("测试引用").setQuoteColor(Color.BLUE, 10, 10)
                .appendLine("测试列表项").setBullet(Color.GREEN, 30, 10)
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
                .appendLine("测试自定义字体").setTypeface(Typeface.createFromAsset(getAssets(), "fonts/dnmbhs.ttf"))
                .appendLine("测试相反对齐").setAlign(Layout.Alignment.ALIGN_OPPOSITE)
                .appendLine("测试居中对齐").setAlign(Layout.Alignment.ALIGN_CENTER)
                .appendLine("测试正常对齐").setAlign(Layout.Alignment.ALIGN_NORMAL)
                .append("测试小图对齐").setBackgroundColor(Color.LTGRAY)
                .append("").setResourceId(R.drawable.shape_spannable_block_low, SpannableStringUtils.ALIGN_TOP)
                .append("").setResourceId(R.drawable.shape_spannable_block_low, SpannableStringUtils.ALIGN_CENTER)
                .append("").setResourceId(R.drawable.shape_spannable_block_low, SpannableStringUtils.ALIGN_BASELINE)
                .append("").setResourceId(R.drawable.shape_spannable_block_low, SpannableStringUtils.ALIGN_BOTTOM)
                .appendLine("end").setBackgroundColor(Color.LTGRAY)
                .append("测试顶部对齐").setBackgroundColor(Color.GREEN)
                .append("image").setResourceId(R.drawable.shape_spannable_block_high, SpannableStringUtils.ALIGN_TOP)
                .appendLine("end").setBackgroundColor(Color.GREEN)
                .append("居中对齐").setBackgroundColor(Color.LTGRAY)
                .append("").setResourceId(R.drawable.shape_spannable_block_high, SpannableStringUtils.ALIGN_CENTER)
                .appendLine("end").setBackgroundColor(Color.LTGRAY)
                .append("Baseline对齐").setBackgroundColor(Color.GREEN)
                .append("").setResourceId(R.drawable.shape_spannable_block_high, SpannableStringUtils.ALIGN_BASELINE)
                .appendLine("end").setBackgroundColor(Color.GREEN)
                .append("底部对齐").setBackgroundColor(Color.LTGRAY)
                .append("").setResourceId(R.drawable.shape_spannable_block_high, SpannableStringUtils.ALIGN_BOTTOM)
                .appendLine("end").setBackgroundColor(Color.LTGRAY)
                .append("测试")
                .appendLine("点击事件").setClickSpan(clickableSpan)
                .append("测试")
                .appendLine("Url").setUrl("https://github.com/Blankj/AndroidUtilCode")
                .append("测试")
                .append("模糊").setBlur(3, BlurMaskFilter.Blur.NORMAL)
                .create()
        );
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void onWidgetClick(View view) {

    }
}
