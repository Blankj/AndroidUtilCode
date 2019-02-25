package com.blankj.utilcode.pkg.feature.span

import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.support.annotation.ColorInt
import android.text.Layout
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.style.CharacterStyle
import android.text.style.ClickableSpan
import android.text.style.UpdateAppearance
import android.view.View
import android.view.animation.LinearInterpolator
import com.blankj.lib.base.BaseTitleBarActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.SpanUtils
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.activity_span.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/27
 * desc  : demo about SpanUtils
 * ```
 */
class SpanActivity : BaseTitleBarActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, SpanActivity::class.java)
            context.startActivity(starter)
        }
    }

    private lateinit var mSpanUtils: SpanUtils
    private lateinit var animSsb: SpannableStringBuilder

    private var lineHeight: Int = 0
    private var textSize: Float = 0f
    private lateinit var valueAnimator: ValueAnimator
    private lateinit var mShader: Shader
    private var mShaderWidth: Float = 0f
    private lateinit var matrix: Matrix
    private lateinit var mBlurMaskFilterSpan: BlurMaskFilterSpan
    private lateinit var mShadowSpan: ShadowSpan
    private lateinit var mForegroundAlphaColorSpan: ForegroundAlphaColorSpan
    private lateinit var mForegroundAlphaColorSpanGroup: ForegroundAlphaColorSpanGroup
    private lateinit var mPrinterString: String
    internal var density: Float = 0f

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_span)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_span
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        setTitle(R.string.demo_span)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                ToastUtils.showShort("事件触发了")
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.color = Color.BLUE
                ds.isUnderlineText = false
            }
        }

        lineHeight = spanAboutTv.lineHeight
        textSize = spanAboutTv.textSize
        density = resources.displayMetrics.density

        //        initAnimSpan();
        //        startAnim();

        SpanUtils.with(spanAboutTv)
                .appendLine("SpanUtils").setBackgroundColor(Color.LTGRAY).setBold().setForegroundColor(Color.YELLOW).setHorizontalAlign(Layout.Alignment.ALIGN_CENTER)
                .appendLine("前景色").setForegroundColor(Color.GREEN)
                .appendLine("背景色").setBackgroundColor(Color.LTGRAY)
                .appendLine("行高居中对齐").setLineHeight(2 * lineHeight, SpanUtils.ALIGN_CENTER).setBackgroundColor(Color.LTGRAY)
                .appendLine("行高底部对齐").setLineHeight(2 * lineHeight, SpanUtils.ALIGN_BOTTOM).setBackgroundColor(Color.GREEN)
                .appendLine("测试段落缩，首行缩进两字，其他行不缩进").setLeadingMargin(textSize.toInt() * 2, 10).setBackgroundColor(Color.GREEN)
                .appendLine("测试引用，后面的字是为了凑到两行的效果").setQuoteColor(Color.GREEN, 10, 10).setBackgroundColor(Color.LTGRAY)
                .appendLine("测试列表项，后面的字是为了凑到两行的效果").setBullet(Color.GREEN, 20, 10).setBackgroundColor(Color.LTGRAY).setBackgroundColor(Color.GREEN)
                .appendLine("32dp 字体").setFontSize(32, true)
                .appendLine("2 倍字体").setFontProportion(2f)
                .appendLine("横向 2 倍字体").setFontXProportion(1.5f)
                .appendLine("删除线").setStrikethrough()
                .appendLine("下划线").setUnderline()
                .append("测试").appendLine("上标").setSuperscript()
                .append("测试").appendLine("下标").setSubscript()
                .appendLine("粗体").setBold()
                .appendLine("斜体").setItalic()
                .appendLine("粗斜体").setBoldItalic()
                .appendLine("monospace 字体").setFontFamily("monospace")
                .appendLine("自定义字体").setTypeface(Typeface.createFromAsset(assets, "fonts/dnmbhs.ttf"))
                .appendLine("相反对齐").setHorizontalAlign(Layout.Alignment.ALIGN_OPPOSITE)
                .appendLine("居中对齐").setHorizontalAlign(Layout.Alignment.ALIGN_CENTER)
                .appendLine("正常对齐").setHorizontalAlign(Layout.Alignment.ALIGN_NORMAL)
                .append("测试").appendLine("点击事件").setClickSpan(clickableSpan)
                .append("测试").appendLine("Url").setUrl("https://github.com/Blankj/AndroidUtilCode")
                .append("测试").appendLine("模糊").setBlur(3f, BlurMaskFilter.Blur.NORMAL)
                .appendLine("颜色渐变").setShader(LinearGradient(0f, 0f, 64f * density * 4f, 0f, resources.getIntArray(R.array.rainbow), null, Shader.TileMode.REPEAT)).setFontSize(64, true)
                .appendLine("图片着色").setFontSize(64, true).setShader(BitmapShader(BitmapFactory.decodeResource(resources, R.drawable.span_cheetah), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT))
                .appendLine("阴影效果").setFontSize(64, true).setBackgroundColor(Color.BLACK).setShadow(24f, 8f, 8f, Color.WHITE)

                .append("小图").setBackgroundColor(Color.GREEN)
                .appendImage(R.drawable.span_shape_block_low, SpanUtils.ALIGN_TOP)
                .append("顶部").setBackgroundColor(Color.GREEN)
                .appendImage(R.drawable.span_shape_block_low, SpanUtils.ALIGN_CENTER)
                .append("居中").setBackgroundColor(Color.GREEN)
                .appendImage(R.drawable.span_shape_block_low, SpanUtils.ALIGN_BASELINE)
                .append("底部").setBackgroundColor(Color.GREEN)
                .appendImage(R.drawable.span_shape_block_low, SpanUtils.ALIGN_BOTTOM)
                .appendLine("对齐").setBackgroundColor(Color.GREEN)
                .appendImage(R.drawable.span_shape_block_high, SpanUtils.ALIGN_TOP)
                .append("大图").setBackgroundColor(Color.LTGRAY)
                .appendImage(R.drawable.span_shape_block_high, SpanUtils.ALIGN_TOP)
                .append("顶部").setBackgroundColor(Color.LTGRAY)
                .appendImage(R.drawable.span_shape_block_high, SpanUtils.ALIGN_TOP)
                .appendLine("对齐").setBackgroundColor(Color.LTGRAY)

                .appendImage(R.drawable.span_shape_block_high, SpanUtils.ALIGN_CENTER)
                .append("大图").setBackgroundColor(Color.GREEN)
                .appendImage(R.drawable.span_shape_block_high, SpanUtils.ALIGN_CENTER)
                .append("居中").setBackgroundColor(Color.GREEN)
                .appendImage(R.drawable.span_shape_block_high, SpanUtils.ALIGN_CENTER)
                .appendLine("对齐").setBackgroundColor(Color.GREEN)

                .appendImage(R.drawable.span_shape_block_high, SpanUtils.ALIGN_BOTTOM)
                .append("大图").setBackgroundColor(Color.LTGRAY)
                .appendImage(R.drawable.span_shape_block_high, SpanUtils.ALIGN_BOTTOM)
                .append("底部").setBackgroundColor(Color.LTGRAY)
                .appendImage(R.drawable.span_shape_block_high, SpanUtils.ALIGN_BOTTOM)
                .appendLine("对齐").setBackgroundColor(Color.LTGRAY)

                .append("测试空格").appendSpace(30, Color.LTGRAY).appendSpace(50, Color.GREEN).appendSpace(100).appendSpace(30, Color.LTGRAY).appendSpace(50, Color.GREEN)
                .create()


//        SpanUtils.with(aboutSpanTv)
//                .append("行高顶部对齐").setLineHeight(3 * lineHeight, SpanUtils.ALIGN_TOP).setFontSize(20).setBackgroundColor(Color.GREEN)
//                .append("行高").setLineHeight(3 * lineHeight, SpanUtils.ALIGN_CENTER).setFontSize(40).setBackgroundColor(Color.LTGRAY)
//                .appendLine("行高顶部").setLineHeight(3 * lineHeight, SpanUtils.ALIGN_BOTTOM).setFontSize(60).setBackgroundColor(Color.LTGRAY)
//                .append("行高").setFontSize(100).setBackgroundColor(Color.GREEN)
//                .append("行高").setFontSize(20).setBackgroundColor(Color.LTGRAY).setUnderline().setVerticalAlign(SpanUtils.ALIGN_CENTER)
//                .create();
    }

    private fun initAnimSpan() {
        mShaderWidth = 64f * density * 4f
        mShader = LinearGradient(0f, 0f,
                mShaderWidth, 0f,
                resources.getIntArray(R.array.rainbow), null,
                Shader.TileMode.REPEAT)
        matrix = Matrix()

        mBlurMaskFilterSpan = BlurMaskFilterSpan(25f)

        mShadowSpan = ShadowSpan(8f, 8f, 8f, Color.WHITE)

        mForegroundAlphaColorSpan = ForegroundAlphaColorSpan(Color.TRANSPARENT)

        mForegroundAlphaColorSpanGroup = ForegroundAlphaColorSpanGroup(0f)

        mPrinterString = "打印动画，后面的文字是为了测试打印效果..."

        mSpanUtils = SpanUtils()
                .appendLine("彩虹动画").setFontSize(64, true).setShader(mShader)
                .appendLine("模糊动画").setFontSize(64, true).setSpans(mBlurMaskFilterSpan)
                .appendLine("阴影动画").setFontSize(64, true).setBackgroundColor(Color.BLACK).setSpans(mShadowSpan)
                .appendLine("透明动画").setFontSize(64, true).setSpans(mForegroundAlphaColorSpan)
        var i = 0
        val len = mPrinterString.length
        while (i < len) {
            val span = ForegroundAlphaColorSpan(Color.TRANSPARENT)
            mSpanUtils.append(mPrinterString.substring(i, i + 1)).setSpans(span)
            mForegroundAlphaColorSpanGroup.addSpan(span)
            ++i
        }
        animSsb = mSpanUtils.create()
    }

    private fun startAnim() {
        valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        valueAnimator.addUpdateListener { animation ->
            // shader
            matrix.reset()
            matrix.setTranslate(animation.animatedValue as Float * mShaderWidth, 0f)
            mShader.setLocalMatrix(matrix)

            // blur
            mBlurMaskFilterSpan.radius = 25 * (1.00001f - animation.animatedValue as Float)

            // shadow
            mShadowSpan.dx = 16 * (0.5f - animation.animatedValue as Float)
            mShadowSpan.dy = 16 * (0.5f - animation.animatedValue as Float)

            // alpha
            mForegroundAlphaColorSpan.setAlpha((255 * animation.animatedValue as Float).toInt())

            // printer
            mForegroundAlphaColorSpanGroup.alpha = animation.animatedValue as Float

            // update
            spanAboutAnimTv.text = animSsb
        }

        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.duration = (600 * 3).toLong()
        valueAnimator.repeatCount = ValueAnimator.INFINITE
        valueAnimator.start()
    }

    override fun doBusiness() {}

    override fun onWidgetClick(view: View) {}

//    override fun onDestroy() {
//        if (valueAnimator.isRunning) {
//            valueAnimator.cancel()
//        }
//        super.onDestroy()
//    }
}

class BlurMaskFilterSpan(private var mRadius: Float) : CharacterStyle(), UpdateAppearance {
    private var mFilter: MaskFilter? = null

    var radius: Float
        get() = mRadius
        set(radius) {
            mRadius = radius
            mFilter = BlurMaskFilter(mRadius, BlurMaskFilter.Blur.NORMAL)
        }

    override fun updateDrawState(ds: TextPaint) {
        ds.maskFilter = mFilter
    }
}

class ForegroundAlphaColorSpan(@param:ColorInt private var mColor: Int) : CharacterStyle(), UpdateAppearance {

    fun setAlpha(alpha: Int) {
        mColor = Color.argb(alpha, Color.red(mColor), Color.green(mColor), Color.blue(mColor))
    }

    override fun updateDrawState(ds: TextPaint) {
        ds.color = mColor
    }
}

class ForegroundAlphaColorSpanGroup(private val mAlpha: Float) {

    private val mSpans: ArrayList<ForegroundAlphaColorSpan> = ArrayList()

    var alpha: Float
        get() = mAlpha
        set(alpha) {
            val size = mSpans.size
            var total = 1.0f * size.toFloat() * alpha
            for (index in 0 until size) {
                val span = mSpans[index]
                if (total >= 1.0f) {
                    span.setAlpha(255)
                    total -= 1.0f
                } else {
                    span.setAlpha((total * 255).toInt())
                    total = 0.0f
                }
            }
        }

    fun addSpan(span: ForegroundAlphaColorSpan) {
        span.setAlpha((mAlpha * 255).toInt())
        mSpans.add(span)
    }
}

class ShadowSpan(private val radius: Float, var dx: Float, var dy: Float, private val shadowColor: Int) : CharacterStyle(), UpdateAppearance {

    override fun updateDrawState(tp: TextPaint) {
        tp.setShadowLayer(radius, dx, dy, shadowColor)
    }
}