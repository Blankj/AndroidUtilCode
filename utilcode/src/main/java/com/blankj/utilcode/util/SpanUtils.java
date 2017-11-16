package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.MaskFilterSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ReplacementSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.text.style.UpdateAppearance;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

import static android.graphics.BlurMaskFilter.Blur;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 16/12/13
 *     desc  : SpannableString相关工具类
 * </pre>
 */
public final class SpanUtils {

    private static final int COLOR_DEFAULT = 0xFEFFFFFF;

    public static final int ALIGN_BOTTOM   = 0;
    public static final int ALIGN_BASELINE = 1;
    public static final int ALIGN_CENTER   = 2;
    public static final int ALIGN_TOP      = 3;

    @IntDef({ALIGN_BOTTOM, ALIGN_BASELINE, ALIGN_CENTER, ALIGN_TOP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Align {
    }

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private CharSequence  mText;
    private int           flag;
    private int           foregroundColor;
    private int           backgroundColor;
    private int           lineHeight;
    private int           alignLine;
    private int           quoteColor;
    private int           stripeWidth;
    private int           quoteGapWidth;
    private int           first;
    private int           rest;
    private int           bulletColor;
    private int           bulletRadius;
    private int           bulletGapWidth;
    private Bitmap        iconMarginBitmap;
    private Drawable      iconMarginDrawable;
    private Uri           iconMarginUri;
    private int           iconMarginResourceId;
    private int           iconMarginGapWidth;
    private int           alignIconMargin;
    private int           fontSize;
    private boolean       fontSizeIsDp;
    private float         proportion;
    private float         xProportion;
    private boolean       isStrikethrough;
    private boolean       isUnderline;
    private boolean       isSuperscript;
    private boolean       isSubscript;
    private boolean       isBold;
    private boolean       isItalic;
    private boolean       isBoldItalic;
    private String        fontFamily;
    private Typeface      typeface;
    private Alignment     alignment;
    private ClickableSpan clickSpan;
    private String        url;
    private float         blurRadius;
    private Blur          style;
    private Shader        shader;
    private float         shadowRadius;
    private float         shadowDx;
    private float         shadowDy;
    private int           shadowColor;
    private Object[]      spans;

    private Bitmap   imageBitmap;
    private Drawable imageDrawable;
    private Uri      imageUri;
    private int      imageResourceId;
    private int      alignImage;

    private int spaceSize;
    private int spaceColor;

    private SpannableStringBuilder mBuilder;

    private int mType;
    private final int mTypeCharSequence = 0;
    private final int mTypeImage        = 1;
    private final int mTypeSpace        = 2;


    public SpanUtils() {
        mBuilder = new SpannableStringBuilder();
        mText = "";
        setDefault();
    }

    private void setDefault() {
        flag = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
        foregroundColor = COLOR_DEFAULT;
        backgroundColor = COLOR_DEFAULT;
        lineHeight = -1;
        quoteColor = COLOR_DEFAULT;
        first = -1;
        bulletColor = COLOR_DEFAULT;
        iconMarginBitmap = null;
        iconMarginDrawable = null;
        iconMarginUri = null;
        iconMarginResourceId = -1;
        iconMarginGapWidth = -1;
        fontSize = -1;
        proportion = -1;
        xProportion = -1;
        isStrikethrough = false;
        isUnderline = false;
        isSuperscript = false;
        isSubscript = false;
        isBold = false;
        isItalic = false;
        isBoldItalic = false;
        fontFamily = null;
        typeface = null;
        alignment = null;
        clickSpan = null;
        url = null;
        blurRadius = -1;
        shader = null;
        shadowRadius = -1;
        spans = null;

        imageBitmap = null;
        imageDrawable = null;
        imageUri = null;
        imageResourceId = -1;

        spaceSize = -1;
    }

    /**
     * 设置标识
     *
     * @param flag <ul>
     *             <li>{@link Spanned#SPAN_INCLUSIVE_EXCLUSIVE}</li>
     *             <li>{@link Spanned#SPAN_INCLUSIVE_INCLUSIVE}</li>
     *             <li>{@link Spanned#SPAN_EXCLUSIVE_EXCLUSIVE}</li>
     *             <li>{@link Spanned#SPAN_EXCLUSIVE_INCLUSIVE}</li>
     *             </ul>
     * @return {@link SpanUtils}
     */
    public SpanUtils setFlag(final int flag) {
        this.flag = flag;
        return this;
    }

    /**
     * 设置前景色
     *
     * @param color 前景色
     * @return {@link SpanUtils}
     */
    public SpanUtils setForegroundColor(@ColorInt final int color) {
        this.foregroundColor = color;
        return this;
    }

    /**
     * 设置背景色
     *
     * @param color 背景色
     * @return {@link SpanUtils}
     */
    public SpanUtils setBackgroundColor(@ColorInt final int color) {
        this.backgroundColor = color;
        return this;
    }

    /**
     * 设置行高
     * <p>当行高大于字体高度时，字体在行中的位置默认居中</p>
     *
     * @param lineHeight 行高
     * @return {@link SpanUtils}
     */
    public SpanUtils setLineHeight(@IntRange(from = 0) final int lineHeight) {
        return setLineHeight(lineHeight, ALIGN_CENTER);
    }

    /**
     * 设置行高
     * <p>当行高大于字体高度时，字体在行中的位置由{@code align}决定</p>
     *
     * @param lineHeight 行高
     * @param align      对齐
     *                   <ul>
     *                   <li>{@link Align#ALIGN_TOP}顶部对齐</li>
     *                   <li>{@link Align#ALIGN_CENTER}居中对齐</li>
     *                   <li>{@link Align#ALIGN_BOTTOM}底部对齐</li>
     *                   </ul>
     * @return {@link SpanUtils}
     */
    public SpanUtils setLineHeight(@IntRange(from = 0) final int lineHeight, @Align final int align) {
        this.lineHeight = lineHeight;
        this.alignLine = align;
        return this;
    }

    /**
     * 设置引用线的颜色
     *
     * @param color 引用线的颜色
     * @return {@link SpanUtils}
     */
    public SpanUtils setQuoteColor(@ColorInt final int color) {
        return setQuoteColor(color, 2, 2);
    }

    /**
     * 设置引用线的颜色
     *
     * @param color       引用线的颜色
     * @param stripeWidth 引用线线宽
     * @param gapWidth    引用线和文字间距
     * @return {@link SpanUtils}
     */
    public SpanUtils setQuoteColor(@ColorInt final int color, @IntRange(from = 1) final int stripeWidth, @IntRange(from = 0) final int gapWidth) {
        this.quoteColor = color;
        this.stripeWidth = stripeWidth;
        this.quoteGapWidth = gapWidth;
        return this;
    }

    /**
     * 设置缩进
     *
     * @param first 首行缩进
     * @param rest  剩余行缩进
     * @return {@link SpanUtils}
     */
    public SpanUtils setLeadingMargin(@IntRange(from = 0) final int first, @IntRange(from = 0) final int rest) {
        this.first = first;
        this.rest = rest;
        return this;
    }

    /**
     * 设置列表标记
     *
     * @param gapWidth 列表标记和文字间距离
     * @return {@link SpanUtils}
     */
    public SpanUtils setBullet(@IntRange(from = 0) final int gapWidth) {
        return setBullet(0, 3, gapWidth);
    }

    /**
     * 设置列表标记
     *
     * @param color    列表标记的颜色
     * @param radius   列表标记颜色
     * @param gapWidth 列表标记和文字间距离
     * @return {@link SpanUtils}
     */
    public SpanUtils setBullet(@ColorInt final int color, @IntRange(from = 0) final int radius, @IntRange(from = 0) final int gapWidth) {
        this.bulletColor = color;
        this.bulletRadius = radius;
        this.bulletGapWidth = gapWidth;
        return this;
    }

    /**
     * 设置图标
     * <p>默认0边距，居中对齐</p>
     *
     * @param bitmap 图标bitmap
     * @return {@link SpanUtils}
     */
    public SpanUtils setIconMargin(final Bitmap bitmap) {
        return setIconMargin(bitmap, 0, ALIGN_CENTER);
    }

    /**
     * 设置图标
     *
     * @param bitmap   图标bitmap
     * @param gapWidth 图标和文字间距离
     * @param align    对齐
     *                 <ul>
     *                 <li>{@link Align#ALIGN_TOP}顶部对齐</li>
     *                 <li>{@link Align#ALIGN_CENTER}居中对齐</li>
     *                 <li>{@link Align#ALIGN_BOTTOM}底部对齐</li>
     *                 </ul>
     * @return {@link SpanUtils}
     */
    public SpanUtils setIconMargin(final Bitmap bitmap, final int gapWidth, @Align final int align) {
        this.iconMarginBitmap = bitmap;
        this.iconMarginGapWidth = gapWidth;
        this.alignIconMargin = align;
        return this;
    }

    /**
     * 设置图标
     * <p>默认0边距，居中对齐</p>
     *
     * @param drawable 图标drawable
     * @return {@link SpanUtils}
     */
    public SpanUtils setIconMargin(final Drawable drawable) {
        return setIconMargin(drawable, 0, ALIGN_CENTER);
    }

    /**
     * 设置图标
     *
     * @param drawable 图标drawable
     * @param gapWidth 图标和文字间距离
     * @param align    对齐
     *                 <ul>
     *                 <li>{@link Align#ALIGN_TOP}顶部对齐</li>
     *                 <li>{@link Align#ALIGN_CENTER}居中对齐</li>
     *                 <li>{@link Align#ALIGN_BOTTOM}底部对齐</li>
     *                 </ul>
     * @return {@link SpanUtils}
     */
    public SpanUtils setIconMargin(final Drawable drawable, final int gapWidth, @Align final int align) {
        this.iconMarginDrawable = drawable;
        this.iconMarginGapWidth = gapWidth;
        this.alignIconMargin = align;
        return this;
    }

    /**
     * 设置图标
     * <p>默认0边距，居中对齐</p>
     *
     * @param uri 图标uri
     * @return {@link SpanUtils}
     */
    public SpanUtils setIconMargin(final Uri uri) {
        return setIconMargin(uri, 0, ALIGN_CENTER);
    }

    /**
     * 设置图标
     *
     * @param uri      图标uri
     * @param gapWidth 图标和文字间距离
     * @param align    对齐
     *                 <ul>
     *                 <li>{@link Align#ALIGN_TOP}顶部对齐</li>
     *                 <li>{@link Align#ALIGN_CENTER}居中对齐</li>
     *                 <li>{@link Align#ALIGN_BOTTOM}底部对齐</li>
     *                 </ul>
     * @return {@link SpanUtils}
     */
    public SpanUtils setIconMargin(final Uri uri, final int gapWidth, @Align final int align) {
        this.iconMarginUri = uri;
        this.iconMarginGapWidth = gapWidth;
        this.alignIconMargin = align;
        return this;
    }

    /**
     * 设置图标
     * <p>默认0边距，居中对齐</p>
     *
     * @param resourceId 图标resourceId
     * @return {@link SpanUtils}
     */
    public SpanUtils setIconMargin(@DrawableRes final int resourceId) {
        return setIconMargin(resourceId, 0, ALIGN_CENTER);
    }

    /**
     * 设置图标
     *
     * @param resourceId 图标resourceId
     * @param gapWidth   图标和文字间距离
     * @param align      对齐
     *                   <ul>
     *                   <li>{@link Align#ALIGN_TOP}顶部对齐</li>
     *                   <li>{@link Align#ALIGN_CENTER}居中对齐</li>
     *                   <li>{@link Align#ALIGN_BOTTOM}底部对齐</li>
     *                   </ul>
     * @return {@link SpanUtils}
     */
    public SpanUtils setIconMargin(@DrawableRes final int resourceId, final int gapWidth, @Align final int align) {
        this.iconMarginResourceId = resourceId;
        this.iconMarginGapWidth = gapWidth;
        this.alignIconMargin = align;
        return this;
    }

    /**
     * 设置字体尺寸
     *
     * @param size 尺寸
     * @return {@link SpanUtils}
     */
    public SpanUtils setFontSize(@IntRange(from = 0) final int size) {
        return setFontSize(size, false);
    }

    /**
     * 设置字体尺寸
     *
     * @param size 尺寸
     * @param isDp 是否使用dip
     * @return {@link SpanUtils}
     */
    public SpanUtils setFontSize(@IntRange(from = 0) final int size, final boolean isDp) {
        this.fontSize = size;
        this.fontSizeIsDp = isDp;
        return this;
    }

    /**
     * 设置字体比例
     *
     * @param proportion 比例
     * @return {@link SpanUtils}
     */
    public SpanUtils setFontProportion(@FloatRange(from = 0, fromInclusive = false) final float proportion) {
        this.proportion = proportion;
        return this;
    }

    /**
     * 设置字体横向比例
     *
     * @param proportion 比例
     * @return {@link SpanUtils}
     */
    public SpanUtils setFontXProportion(@FloatRange(from = 0, fromInclusive = false) final float proportion) {
        this.xProportion = proportion;
        return this;
    }

    /**
     * 设置删除线
     *
     * @return {@link SpanUtils}
     */
    public SpanUtils setStrikethrough() {
        this.isStrikethrough = true;
        return this;
    }

    /**
     * 设置下划线
     *
     * @return {@link SpanUtils}
     */
    public SpanUtils setUnderline() {
        this.isUnderline = true;
        return this;
    }

    /**
     * 设置上标
     *
     * @return {@link SpanUtils}
     */
    public SpanUtils setSuperscript() {
        this.isSuperscript = true;
        return this;
    }

    /**
     * 设置下标
     *
     * @return {@link SpanUtils}
     */
    public SpanUtils setSubscript() {
        this.isSubscript = true;
        return this;
    }

    /**
     * 设置粗体
     *
     * @return {@link SpanUtils}
     */
    public SpanUtils setBold() {
        isBold = true;
        return this;
    }

    /**
     * 设置斜体
     *
     * @return {@link SpanUtils}
     */
    public SpanUtils setItalic() {
        isItalic = true;
        return this;
    }

    /**
     * 设置粗斜体
     *
     * @return {@link SpanUtils}
     */
    public SpanUtils setBoldItalic() {
        isBoldItalic = true;
        return this;
    }

    /**
     * 设置字体系列
     *
     * @param fontFamily 字体系列
     *                   <ul>
     *                   <li>monospace</li>
     *                   <li>serif</li>
     *                   <li>sans-serif</li>
     *                   </ul>
     * @return {@link SpanUtils}
     */
    public SpanUtils setFontFamily(@NonNull final String fontFamily) {
        this.fontFamily = fontFamily;
        return this;
    }

    /**
     * 设置字体
     *
     * @param typeface 字体
     * @return {@link SpanUtils}
     */
    public SpanUtils setTypeface(@NonNull final Typeface typeface) {
        this.typeface = typeface;
        return this;
    }

    /**
     * 设置对齐
     *
     * @param alignment 对其方式
     *                  <ul>
     *                  <li>{@link Alignment#ALIGN_NORMAL}正常</li>
     *                  <li>{@link Alignment#ALIGN_OPPOSITE}相反</li>
     *                  <li>{@link Alignment#ALIGN_CENTER}居中</li>
     *                  </ul>
     * @return {@link SpanUtils}
     */
    public SpanUtils setAlign(@NonNull final Alignment alignment) {
        this.alignment = alignment;
        return this;
    }

    /**
     * 设置点击事件
     * <p>需添加view.setMovementMethod(LinkMovementMethod.getInstance())</p>
     *
     * @param clickSpan 点击事件
     * @return {@link SpanUtils}
     */
    public SpanUtils setClickSpan(@NonNull final ClickableSpan clickSpan) {
        this.clickSpan = clickSpan;
        return this;
    }

    /**
     * 设置超链接
     * <p>需添加view.setMovementMethod(LinkMovementMethod.getInstance())</p>
     *
     * @param url 超链接
     * @return {@link SpanUtils}
     */
    public SpanUtils setUrl(@NonNull final String url) {
        this.url = url;
        return this;
    }

    /**
     * 设置模糊
     * <p>尚存bug，其他地方存在相同的字体的话，相同字体出现在之前的话那么就不会模糊，出现在之后的话那会一起模糊</p>
     * <p>以上bug关闭硬件加速即可</p>
     *
     * @param radius 模糊半径（需大于0）
     * @param style  模糊样式<ul>
     *               <li>{@link Blur#NORMAL}</li>
     *               <li>{@link Blur#SOLID}</li>
     *               <li>{@link Blur#OUTER}</li>
     *               <li>{@link Blur#INNER}</li>
     *               </ul>
     * @return {@link SpanUtils}
     */
    public SpanUtils setBlur(@FloatRange(from = 0, fromInclusive = false) final float radius, final Blur style) {
        this.blurRadius = radius;
        this.style = style;
        return this;
    }

    /**
     * 设置着色器
     *
     * @param shader 着色器
     * @return {@link SpanUtils}
     */
    public SpanUtils setShader(@NonNull final Shader shader) {
        this.shader = shader;
        return this;
    }

    /**
     * 设置阴影
     *
     * @param radius      阴影半径
     * @param dx          x轴偏移量
     * @param dy          y轴偏移量
     * @param shadowColor 阴影颜色
     * @return {@link SpanUtils}
     */
    public SpanUtils setShadow(@FloatRange(from = 0, fromInclusive = false) final float radius,
                               final float dx,
                               final float dy,
                               final int shadowColor) {
        this.shadowRadius = radius;
        this.shadowDx = dx;
        this.shadowDy = dy;
        this.shadowColor = shadowColor;
        return this;
    }


    /**
     * 设置样式
     *
     * @param spans 样式
     * @return {@link SpanUtils}
     */
    public SpanUtils setSpans(@NonNull final Object... spans) {
        if (spans.length > 0) {
            this.spans = spans;
        }
        return this;
    }

    /**
     * 追加样式字符串
     *
     * @param text 样式字符串文本
     * @return {@link SpanUtils}
     */
    public SpanUtils append(@NonNull final CharSequence text) {
        apply(mTypeCharSequence);
        mText = text;
        return this;
    }

    /**
     * 追加一行
     *
     * @return {@link SpanUtils}
     */
    public SpanUtils appendLine() {
        apply(mTypeCharSequence);
        mText = LINE_SEPARATOR;
        return this;
    }

    /**
     * 追加一行样式字符串
     *
     * @return {@link SpanUtils}
     */
    public SpanUtils appendLine(@NonNull final CharSequence text) {
        apply(mTypeCharSequence);
        mText = text + LINE_SEPARATOR;
        return this;
    }

    /**
     * 追加图片
     *
     * @param bitmap 图片位图
     * @return {@link SpanUtils}
     */
    public SpanUtils appendImage(@NonNull final Bitmap bitmap) {
        return appendImage(bitmap, ALIGN_BOTTOM);
    }

    /**
     * 追加图片
     *
     * @param bitmap 图片位图
     * @param align  对齐
     *               <ul>
     *               <li>{@link Align#ALIGN_TOP}顶部对齐</li>
     *               <li>{@link Align#ALIGN_CENTER}居中对齐</li>
     *               <li>{@link Align#ALIGN_BASELINE}基线对齐</li>
     *               <li>{@link Align#ALIGN_BOTTOM}底部对齐</li>
     *               </ul>
     * @return {@link SpanUtils}
     */
    public SpanUtils appendImage(@NonNull final Bitmap bitmap, @Align final int align) {
        apply(mTypeImage);
        this.imageBitmap = bitmap;
        this.alignImage = align;
        return this;
    }

    /**
     * 追加图片
     *
     * @param drawable 图片资源
     * @return {@link SpanUtils}
     */
    public SpanUtils appendImage(@NonNull final Drawable drawable) {
        return appendImage(drawable, ALIGN_BOTTOM);
    }

    /**
     * 追加图片
     *
     * @param drawable 图片资源
     * @param align    对齐
     *                 <ul>
     *                 <li>{@link Align#ALIGN_TOP}顶部对齐</li>
     *                 <li>{@link Align#ALIGN_CENTER}居中对齐</li>
     *                 <li>{@link Align#ALIGN_BASELINE}基线对齐</li>
     *                 <li>{@link Align#ALIGN_BOTTOM}底部对齐</li>
     *                 </ul>
     * @return {@link SpanUtils}
     */
    public SpanUtils appendImage(@NonNull final Drawable drawable, @Align final int align) {
        apply(mTypeImage);
        this.imageDrawable = drawable;
        this.alignImage = align;
        return this;
    }

    /**
     * 追加图片
     *
     * @param uri 图片uri
     * @return {@link SpanUtils}
     */
    public SpanUtils appendImage(@NonNull final Uri uri) {
        return appendImage(uri, ALIGN_BOTTOM);
    }

    /**
     * 追加图片
     *
     * @param uri   图片uri
     * @param align 对齐
     *              <ul>
     *              <li>{@link Align#ALIGN_TOP}顶部对齐</li>
     *              <li>{@link Align#ALIGN_CENTER}居中对齐</li>
     *              <li>{@link Align#ALIGN_BASELINE}基线对齐</li>
     *              <li>{@link Align#ALIGN_BOTTOM}底部对齐</li>
     *              </ul>
     * @return {@link SpanUtils}
     */
    public SpanUtils appendImage(@NonNull final Uri uri, @Align final int align) {
        apply(mTypeImage);
        this.imageUri = uri;
        this.alignImage = align;
        return this;
    }

    /**
     * 追加图片
     *
     * @param resourceId 图片资源id
     * @return {@link SpanUtils}
     */
    public SpanUtils appendImage(@DrawableRes final int resourceId) {
        return appendImage(resourceId, ALIGN_BOTTOM);
    }

    /**
     * 追加图片
     *
     * @param resourceId 图片资源id
     * @param align      对齐
     * @return {@link SpanUtils}
     */
    public SpanUtils appendImage(@DrawableRes final int resourceId, @Align final int align) {
        apply(mTypeImage);
        this.imageResourceId = resourceId;
        this.alignImage = align;
        return this;
    }

    /**
     * 追加空白
     *
     * @param size 间距
     * @return {@link SpanUtils}
     */
    public SpanUtils appendSpace(@IntRange(from = 0) final int size) {
        return appendSpace(size, Color.TRANSPARENT);
    }

    /**
     * 追加空白
     *
     * @param size  间距
     * @param color 颜色
     * @return {@link SpanUtils}
     */
    public SpanUtils appendSpace(@IntRange(from = 0) final int size, @ColorInt final int color) {
        apply(mTypeSpace);
        spaceSize = size;
        spaceColor = color;
        return this;
    }

    private void apply(final int type) {
        applyLast();
        mType = type;
    }

    /**
     * 创建样式字符串
     *
     * @return 样式字符串
     */
    public SpannableStringBuilder create() {
        applyLast();
        return mBuilder;
    }

    /**
     * 设置上一次的样式
     */
    private void applyLast() {
        if (mType == mTypeCharSequence) {
            updateCharCharSequence();
        } else if (mType == mTypeImage) {
            updateImage();
        } else if (mType == mTypeSpace) {
            updateSpace();
        }
        setDefault();
    }

    private void updateCharCharSequence() {
        if (mText.length() == 0) return;
        int start = mBuilder.length();
        mBuilder.append(mText);
        int end = mBuilder.length();
        if (foregroundColor != COLOR_DEFAULT) {
            mBuilder.setSpan(new ForegroundColorSpan(foregroundColor), start, end, flag);
        }
        if (backgroundColor != COLOR_DEFAULT) {
            mBuilder.setSpan(new BackgroundColorSpan(backgroundColor), start, end, flag);
        }
        if (first != -1) {
            mBuilder.setSpan(new LeadingMarginSpan.Standard(first, rest), start, end, flag);
        }
        if (quoteColor != COLOR_DEFAULT) {
            mBuilder.setSpan(new CustomQuoteSpan(quoteColor, stripeWidth, quoteGapWidth), start, end, flag);
        }
        if (bulletColor != COLOR_DEFAULT) {
            mBuilder.setSpan(new CustomBulletSpan(bulletColor, bulletRadius, bulletGapWidth), start, end, flag);
        }
        if (iconMarginGapWidth != -1) {
            if (iconMarginBitmap != null) {
                mBuilder.setSpan(new CustomIconMarginSpan(iconMarginBitmap, iconMarginGapWidth, alignIconMargin), start, end, flag);
            } else if (iconMarginDrawable != null) {
                mBuilder.setSpan(new CustomIconMarginSpan(iconMarginDrawable, iconMarginGapWidth, alignIconMargin), start, end, flag);
            } else if (iconMarginUri != null) {
                mBuilder.setSpan(new CustomIconMarginSpan(iconMarginUri, iconMarginGapWidth, alignIconMargin), start, end, flag);
            } else if (iconMarginResourceId != -1) {
                mBuilder.setSpan(new CustomIconMarginSpan(iconMarginResourceId, iconMarginGapWidth, alignIconMargin), start, end, flag);
            }
        }
        if (fontSize != -1) {
            mBuilder.setSpan(new AbsoluteSizeSpan(fontSize, fontSizeIsDp), start, end, flag);
        }
        if (proportion != -1) {
            mBuilder.setSpan(new RelativeSizeSpan(proportion), start, end, flag);
        }
        if (xProportion != -1) {
            mBuilder.setSpan(new ScaleXSpan(xProportion), start, end, flag);
        }
        if (lineHeight != -1) {
            mBuilder.setSpan(new CustomLineHeightSpan(lineHeight, alignLine), start, end, flag);
        }
        if (isStrikethrough) {
            mBuilder.setSpan(new StrikethroughSpan(), start, end, flag);
        }
        if (isUnderline) {
            mBuilder.setSpan(new UnderlineSpan(), start, end, flag);
        }
        if (isSuperscript) {
            mBuilder.setSpan(new SuperscriptSpan(), start, end, flag);
        }
        if (isSubscript) {
            mBuilder.setSpan(new SubscriptSpan(), start, end, flag);
        }
        if (isBold) {
            mBuilder.setSpan(new StyleSpan(Typeface.BOLD), start, end, flag);
        }
        if (isItalic) {
            mBuilder.setSpan(new StyleSpan(Typeface.ITALIC), start, end, flag);
        }
        if (isBoldItalic) {
            mBuilder.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), start, end, flag);
        }
        if (fontFamily != null) {
            mBuilder.setSpan(new TypefaceSpan(fontFamily), start, end, flag);
        }
        if (typeface != null) {
            mBuilder.setSpan(new CustomTypefaceSpan(typeface), start, end, flag);
        }
        if (alignment != null) {
            mBuilder.setSpan(new AlignmentSpan.Standard(alignment), start, end, flag);
        }
        if (clickSpan != null) {
            mBuilder.setSpan(clickSpan, start, end, flag);
        }
        if (url != null) {
            mBuilder.setSpan(new URLSpan(url), start, end, flag);
        }
        if (blurRadius != -1) {
            mBuilder.setSpan(new MaskFilterSpan(new BlurMaskFilter(blurRadius, style)), start, end, flag);
        }
        if (shader != null) {
            mBuilder.setSpan(new ShaderSpan(shader), start, end, flag);
        }
        if (shadowRadius != -1) {
            mBuilder.setSpan(new ShadowSpan(shadowRadius, shadowDx, shadowDy, shadowColor), start, end, flag);
        }
        if (spans != null) {
            for (Object span : spans) {
                mBuilder.setSpan(span, start, end, flag);
            }
        }
    }

    private void updateImage() {
        int start = mBuilder.length();
        mBuilder.append("<img>");
        int end = start + 5;
        if (imageBitmap != null) {
            mBuilder.setSpan(new CustomImageSpan(imageBitmap, alignImage), start, end, flag);
        } else if (imageDrawable != null) {
            mBuilder.setSpan(new CustomImageSpan(imageDrawable, alignImage), start, end, flag);
        } else if (imageUri != null) {
            mBuilder.setSpan(new CustomImageSpan(imageUri, alignImage), start, end, flag);
        } else if (imageResourceId != -1) {
            mBuilder.setSpan(new CustomImageSpan(imageResourceId, alignImage), start, end, flag);
        }
    }

    private void updateSpace() {
        int start = mBuilder.length();
        mBuilder.append("< >");
        int end = start + 3;
        mBuilder.setSpan(new SpaceSpan(spaceSize, spaceColor), start, end, flag);
    }

    /**
     * 行高
     */
    class CustomLineHeightSpan extends CharacterStyle
            implements android.text.style.LineHeightSpan {

        private final int height;

        static final int ALIGN_CENTER = 2;

        static final int ALIGN_TOP = 3;

        final int mVerticalAlignment;

        CustomLineHeightSpan(int height, int verticalAlignment) {
            this.height = height;
            mVerticalAlignment = verticalAlignment;
        }

        @Override
        public void chooseHeight(final CharSequence text, final int start, final int end, final int spanstartv, final int v, final Paint.FontMetricsInt fm) {
            int need = height - (v + fm.descent - fm.ascent - spanstartv);
            if (need > 0) {
                if (mVerticalAlignment == ALIGN_TOP) {
                    fm.descent += need;
                } else if (mVerticalAlignment == ALIGN_CENTER) {
                    fm.descent += need / 2;
                    fm.ascent -= need / 2;
                } else {
                    fm.ascent -= need;
                }
            }
            need = height - (v + fm.bottom - fm.top - spanstartv);
            if (need > 0) {
                if (mVerticalAlignment == ALIGN_TOP) {
                    fm.top += need;
                } else if (mVerticalAlignment == ALIGN_CENTER) {
                    fm.bottom += need / 2;
                    fm.top -= need / 2;
                } else {
                    fm.top -= need;
                }
            }
        }

        @Override
        public void updateDrawState(final TextPaint tp) {

        }
    }

    /**
     * 空格
     */
    class SpaceSpan extends ReplacementSpan {

        private final int width;
        private final int color;

        private SpaceSpan(final int width) {
            this(width, Color.TRANSPARENT);
        }

        private SpaceSpan(final int width, final int color) {
            super();
            this.width = width;
            this.color = color;
        }

        @Override
        public int getSize(@NonNull final Paint paint, final CharSequence text,
                           @IntRange(from = 0) final int start,
                           @IntRange(from = 0) final int end,
                           @Nullable final Paint.FontMetricsInt fm) {
            return width;
        }

        @Override
        public void draw(@NonNull final Canvas canvas, final CharSequence text,
                         @IntRange(from = 0) final int start,
                         @IntRange(from = 0) final int end,
                         final float x, final int top, final int y, final int bottom,
                         @NonNull final Paint paint) {
            Paint.Style style = paint.getStyle();
            int color = paint.getColor();

            paint.setStyle(Paint.Style.FILL);
            paint.setColor(this.color);

            canvas.drawRect(x, top, x + width, bottom, paint);

            paint.setStyle(style);
            paint.setColor(color);
        }
    }

    /**
     * 引用
     */
    class CustomQuoteSpan implements LeadingMarginSpan {

        private final int color;
        private final int stripeWidth;
        private final int gapWidth;

        private CustomQuoteSpan(final int color, final int stripeWidth, final int gapWidth) {
            super();
            this.color = color;
            this.stripeWidth = stripeWidth;
            this.gapWidth = gapWidth;
        }

        public int getLeadingMargin(final boolean first) {
            return stripeWidth + gapWidth;
        }

        public void drawLeadingMargin(final Canvas c, final Paint p, final int x, final int dir,
                                      final int top, final int baseline, final int bottom,
                                      final CharSequence text, final int start, final int end,
                                      final boolean first, final Layout layout) {
            Paint.Style style = p.getStyle();
            int color = p.getColor();

            p.setStyle(Paint.Style.FILL);
            p.setColor(this.color);

            c.drawRect(x, top, x + dir * stripeWidth, bottom, p);

            p.setStyle(style);
            p.setColor(color);
        }
    }

    /**
     * 列表项
     */
    class CustomBulletSpan implements LeadingMarginSpan {

        private final int color;
        private final int radius;
        private final int gapWidth;

        private Path sBulletPath = null;

        private CustomBulletSpan(final int color, final int radius, final int gapWidth) {
            this.color = color;
            this.radius = radius;
            this.gapWidth = gapWidth;
        }

        public int getLeadingMargin(final boolean first) {
            return 2 * radius + gapWidth;
        }

        public void drawLeadingMargin(final Canvas c, final Paint p, final int x, final int dir,
                                      final int top, final int baseline, final int bottom,
                                      final CharSequence text, final int start, final int end,
                                      final boolean first, final Layout l) {
            if (((Spanned) text).getSpanStart(this) == start) {
                Paint.Style style = p.getStyle();
                int oldColor = 0;
                oldColor = p.getColor();
                p.setColor(color);
                p.setStyle(Paint.Style.FILL);
                if (c.isHardwareAccelerated()) {
                    if (sBulletPath == null) {
                        sBulletPath = new Path();
                        // Bullet is slightly better to avoid aliasing artifacts on mdpi devices.
                        sBulletPath.addCircle(0.0f, 0.0f, radius, Path.Direction.CW);
                    }
                    c.save();
                    c.translate(x + dir * radius, (top + bottom) / 2.0f);
                    c.drawPath(sBulletPath, p);
                    c.restore();
                } else {
                    c.drawCircle(x + dir * radius, (top + bottom) / 2.0f, radius, p);
                }
                p.setColor(oldColor);
                p.setStyle(style);
            }
        }
    }

    class CustomIconMarginSpan implements LeadingMarginSpan, android.text.style.LineHeightSpan {
        Bitmap mBitmap;

        static final int ALIGN_CENTER = 2;

        static final int ALIGN_TOP = 3;

        final int mVerticalAlignment;

        private int     mPad;
        private int     totalHeight;
        private int     lineHeight;
        private int     need0;
        private int     need1;
        private boolean flag;

        private CustomIconMarginSpan(final Bitmap b, final int pad, final int verticalAlignment) {
            mBitmap = b;
            mPad = pad;
            mVerticalAlignment = verticalAlignment;
        }

        private CustomIconMarginSpan(final Drawable drawable, final int pad, final int verticalAlignment) {
            mBitmap = drawable2Bitmap(drawable);
            mPad = pad;
            mVerticalAlignment = verticalAlignment;
        }

        private CustomIconMarginSpan(final Uri uri, final int pad, final int verticalAlignment) {
            mBitmap = uri2Bitmap(uri);
            mPad = pad;
            mVerticalAlignment = verticalAlignment;
        }

        private CustomIconMarginSpan(final int resourceId, final int pad, final int verticalAlignment) {
            mBitmap = resource2Bitmap(resourceId);
            mPad = pad;
            mVerticalAlignment = verticalAlignment;
        }

        private Bitmap drawable2Bitmap(final Drawable drawable) {
            if (drawable instanceof BitmapDrawable) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                if (bitmapDrawable.getBitmap() != null) {
                    return bitmapDrawable.getBitmap();
                }
            }
            Bitmap bitmap;
            if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
                bitmap = Bitmap.createBitmap(1, 1,
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
            }
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        }

        private Bitmap uri2Bitmap(final Uri uri) {
            try {
                return MediaStore.Images.Media.getBitmap(Utils.getApp().getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
                return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            }
        }

        private Bitmap resource2Bitmap(final int resourceId) {
            Drawable drawable = ContextCompat.getDrawable(Utils.getApp(), resourceId);
            Canvas canvas = new Canvas();
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            canvas.setBitmap(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        }

        public int getLeadingMargin(final boolean first) {
            return mBitmap.getWidth() + mPad;
        }

        public void drawLeadingMargin(Canvas c, Paint p, int x, int dir,
                                      int top, int baseline, int bottom,
                                      CharSequence text, int start, int end,
                                      boolean first, Layout layout) {
            int st = ((Spanned) text).getSpanStart(this);
            int itop = layout.getLineTop(layout.getLineForOffset(st));

            if (dir < 0)
                x -= mBitmap.getWidth();

            int delta = totalHeight - mBitmap.getHeight();

            if (delta > 0) {
                if (mVerticalAlignment == ALIGN_TOP) {
                    c.drawBitmap(mBitmap, x, itop, p);
                } else if (mVerticalAlignment == ALIGN_CENTER) {
                    c.drawBitmap(mBitmap, x, itop + delta / 2, p);
                } else {
                    c.drawBitmap(mBitmap, x, itop + delta, p);
                }
            } else {
                c.drawBitmap(mBitmap, x, itop, p);
            }
        }

        public void chooseHeight(CharSequence text, int start, int end,
                                 int istartv, int v, Paint.FontMetricsInt fm) {
            if (lineHeight == 0) {
                lineHeight = v - istartv;
            }
            if (need0 == 0 && end == ((Spanned) text).getSpanEnd(this)) {
                int ht = mBitmap.getHeight();
                need0 = ht - (v + fm.descent - fm.ascent - istartv);
                need1 = ht - (v + fm.bottom - fm.top - istartv);
                totalHeight = v - istartv + lineHeight;
                return;
            }
            if (need0 > 0 || need1 > 0) {
                if (mVerticalAlignment == ALIGN_TOP) {
                    // the rest space should be filled with the end of line
                    if (end == ((Spanned) text).getSpanEnd(this)) {
                        if (need0 > 0) fm.descent += need0;
                        if (need1 > 0) fm.bottom += need1;
                    }
                } else if (mVerticalAlignment == ALIGN_CENTER) {
                    if (start == ((Spanned) text).getSpanStart(this)) {
                        if (need0 > 0) fm.ascent -= need0 / 2;
                        if (need1 > 0) fm.top -= need1 / 2;
                    } else {
                        if (!flag) {
                            if (need0 > 0) fm.ascent += need0 / 2;
                            if (need1 > 0) fm.top += need1 / 2;
                            flag = true;
                        }
                    }
                    if (end == ((Spanned) text).getSpanEnd(this)) {
                        if (need0 > 0) fm.descent += need0 / 2;
                        if (need1 > 0) fm.bottom += need1 / 2;
                    }
                } else {
                    // the top space should be filled with the first of line
                    if (start == ((Spanned) text).getSpanStart(this)) {
                        if (need0 > 0) fm.ascent -= need0;
                        if (need1 > 0) fm.top -= need1;
                    } else {
                        if (!flag) {
                            if (need0 > 0) fm.ascent += need0;
                            if (need1 > 0) fm.top += need1;
                            flag = true;
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("ParcelCreator")
    class CustomTypefaceSpan extends TypefaceSpan {

        private final Typeface newType;

        private CustomTypefaceSpan(final Typeface type) {
            super("");
            newType = type;
        }

        @Override
        public void updateDrawState(final TextPaint textPaint) {
            apply(textPaint, newType);
        }

        @Override
        public void updateMeasureState(final TextPaint paint) {
            apply(paint, newType);
        }

        private void apply(final Paint paint, final Typeface tf) {
            int oldStyle;
            Typeface old = paint.getTypeface();
            if (old == null) {
                oldStyle = 0;
            } else {
                oldStyle = old.getStyle();
            }

            int fake = oldStyle & ~tf.getStyle();
            if ((fake & Typeface.BOLD) != 0) {
                paint.setFakeBoldText(true);
            }

            if ((fake & Typeface.ITALIC) != 0) {
                paint.setTextSkewX(-0.25f);
            }

            paint.getShader();

            paint.setTypeface(tf);
        }
    }

    class CustomImageSpan extends CustomDynamicDrawableSpan {
        private Drawable mDrawable;
        private Uri      mContentUri;
        private int      mResourceId;

        private CustomImageSpan(final Bitmap b, final int verticalAlignment) {
            super(verticalAlignment);
            mDrawable = new BitmapDrawable(Utils.getApp().getResources(), b);
            mDrawable.setBounds(0, 0, mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight());
        }

        private CustomImageSpan(final Drawable d, final int verticalAlignment) {
            super(verticalAlignment);
            mDrawable = d;
            mDrawable.setBounds(0, 0, mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight());
        }

        private CustomImageSpan(final Uri uri, final int verticalAlignment) {
            super(verticalAlignment);
            mContentUri = uri;
        }

        private CustomImageSpan(@DrawableRes final int resourceId, final int verticalAlignment) {
            super(verticalAlignment);
            mResourceId = resourceId;
        }

        @Override
        public Drawable getDrawable() {
            Drawable drawable = null;
            if (mDrawable != null) {
                drawable = mDrawable;
            } else if (mContentUri != null) {
                Bitmap bitmap;
                try {
                    InputStream is = Utils.getApp().getContentResolver().openInputStream(mContentUri);
                    bitmap = BitmapFactory.decodeStream(is);
                    drawable = new BitmapDrawable(Utils.getApp().getResources(), bitmap);
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    if (is != null) {
                        is.close();
                    }
                } catch (Exception e) {
                    Log.e("sms", "Failed to loaded content " + mContentUri, e);
                }
            } else {
                try {
                    drawable = ContextCompat.getDrawable(Utils.getApp(), mResourceId);
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                } catch (Exception e) {
                    Log.e("sms", "Unable to find resource: " + mResourceId);
                }
            }
            return drawable;
        }
    }

    abstract class CustomDynamicDrawableSpan extends ReplacementSpan {

        static final int ALIGN_BOTTOM = 0;

        static final int ALIGN_BASELINE = 1;

        static final int ALIGN_CENTER = 2;

        static final int ALIGN_TOP = 3;

        final int mVerticalAlignment;

        private CustomDynamicDrawableSpan() {
            mVerticalAlignment = ALIGN_BOTTOM;
        }

        private CustomDynamicDrawableSpan(final int verticalAlignment) {
            mVerticalAlignment = verticalAlignment;
        }

        public abstract Drawable getDrawable();

        @Override
        public int getSize(@NonNull final Paint paint, final CharSequence text,
                           final int start, final int end,
                           final Paint.FontMetricsInt fm) {
            Drawable d = getCachedDrawable();
            Rect rect = d.getBounds();
            final int fontHeight = (int) (paint.getFontMetrics().descent - paint.getFontMetrics().ascent);
            if (fm != null) { // this is the fucking code which I waste 3 days
                if (rect.height() > fontHeight) {
                    if (mVerticalAlignment == ALIGN_TOP) {
                        fm.descent += rect.height() - fontHeight;
                    } else if (mVerticalAlignment == ALIGN_CENTER) {
                        fm.ascent -= (rect.height() - fontHeight) / 2;
                        fm.descent += (rect.height() - fontHeight) / 2;
                    } else {
                        fm.ascent -= rect.height() - fontHeight;
                    }
                }
            }
            return rect.right;
        }

        @Override
        public void draw(@NonNull final Canvas canvas, final CharSequence text,
                         final int start, final int end, final float x,
                         final int top, final int y, final int bottom, @NonNull final Paint paint) {
            Drawable d = getCachedDrawable();
            Rect rect = d.getBounds();
            canvas.save();
            final float fontHeight = paint.getFontMetrics().descent - paint.getFontMetrics().ascent;
            int transY = bottom - rect.bottom;
            if (rect.height() < fontHeight) { // this is the fucking code which I waste 3 days
                if (mVerticalAlignment == ALIGN_BASELINE) {
                    transY -= paint.getFontMetricsInt().descent;
                } else if (mVerticalAlignment == ALIGN_CENTER) {
                    transY -= (fontHeight - rect.height()) / 2;
                } else if (mVerticalAlignment == ALIGN_TOP) {
                    transY -= fontHeight - rect.height();
                }
            }
            canvas.translate(x, transY);
            d.draw(canvas);
            canvas.restore();
        }

        private Drawable getCachedDrawable() {
            WeakReference<Drawable> wr = mDrawableRef;
            Drawable d = null;
            if (wr != null)
                d = wr.get();
            if (d == null) {
                d = getDrawable();
                mDrawableRef = new WeakReference<>(d);
            }
            return getDrawable();
        }

        private WeakReference<Drawable> mDrawableRef;
    }

    class ShaderSpan extends CharacterStyle implements UpdateAppearance {
        private Shader mShader;

        private ShaderSpan(final Shader shader) {
            this.mShader = shader;
        }

        @Override
        public void updateDrawState(final TextPaint tp) {
            tp.setShader(mShader);
        }
    }

    class ShadowSpan extends CharacterStyle implements UpdateAppearance {
        private float radius;
        private float dx, dy;
        private int shadowColor;

        private ShadowSpan(final float radius, final float dx, final float dy, final int shadowColor) {
            this.radius = radius;
            this.dx = dx;
            this.dy = dy;
            this.shadowColor = shadowColor;
        }

        @Override
        public void updateDrawState(final TextPaint tp) {
            tp.setShadowLayer(radius, dx, dy, shadowColor);
        }
    }
}
