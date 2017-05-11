package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
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
import android.util.Log;

import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 16/12/13
 *     desc  : SpannableString相关工具类
 * </pre>
 */
public final class SpannableStringUtils {

    private SpannableStringUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static final int ALIGN_BOTTOM = 0;

    public static final int ALIGN_BASELINE = 1;

    public static final int ALIGN_CENTER = 2;

    public static final int ALIGN_TOP = 3;

    @IntDef({ALIGN_BOTTOM, ALIGN_BASELINE, ALIGN_CENTER, ALIGN_TOP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Align {
    }

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static class Builder {

        private int defaultValue = 0x12000000;

        private CharSequence text;
        private int flag;
        @ColorInt
        private int foregroundColor;
        @ColorInt
        private int backgroundColor;
        @ColorInt
        private int quoteColor;
        private int stripeWidth;
        private int quoteGapWidth;
        private boolean isLeadingMargin;
        private int first;
        private int rest;
        private int margin;
        private boolean isBullet;
        private int bulletColor;
        private int bulletRadius;
        private int bulletGapWidth;
        private int fontSize;
        private boolean fontSizeIsDp;
        private float proportion;
        private float xProportion;
        private boolean isStrikethrough;
        private boolean isUnderline;
        private boolean isSuperscript;
        private boolean isSubscript;
        private boolean isBold;
        private boolean isItalic;
        private boolean isBoldItalic;
        private String fontFamily;
        private Typeface typeface;
        private Alignment alignment;
        private boolean imageIsBitmap;
        private Bitmap bitmap;
        private boolean imageIsDrawable;
        private Drawable drawable;
        private boolean imageIsUri;
        private Uri uri;
        private boolean imageIsResourceId;
        @DrawableRes
        private int resourceId;
        @Align
        int align;

        private ClickableSpan clickSpan;
        private String url;

        private boolean isBlur;
        private float blurRadius;
        private BlurMaskFilter.Blur style;

        private SpannableStringBuilder mBuilder;

        public Builder() {
            flag = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
            foregroundColor = defaultValue;
            backgroundColor = defaultValue;
            quoteColor = defaultValue;
            margin = -1;
            fontSize = -1;
            proportion = -1;
            xProportion = -1;
            align = ALIGN_BOTTOM;
            mBuilder = new SpannableStringBuilder();
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
         * @return {@link Builder}
         */
        public Builder setFlag(int flag) {
            this.flag = flag;
            return this;
        }

        /**
         * 设置前景色
         *
         * @param color 前景色
         * @return {@link Builder}
         */
        public Builder setForegroundColor(@ColorInt int color) {
            this.foregroundColor = color;
            return this;
        }

        /**
         * 设置背景色
         *
         * @param color 背景色
         * @return {@link Builder}
         */
        public Builder setBackgroundColor(@ColorInt int color) {
            this.backgroundColor = color;
            return this;
        }

        /**
         * 设置引用线的颜色
         *
         * @param color 引用线的颜色
         * @return {@link Builder}
         */
        public Builder setQuoteColor(@ColorInt int color) {
            this.quoteColor = color;
            this.stripeWidth = 2;
            this.quoteGapWidth = 2;
            return this;
        }

        /**
         * 设置引用线的颜色
         *
         * @param color         引用线的颜色
         * @param stripeWidth   引用线线宽
         * @param quoteGapWidth 引用线和文字间距
         * @return {@link Builder}
         */
        public Builder setQuoteColor(@ColorInt int color, int stripeWidth, int quoteGapWidth) {
            this.quoteColor = color;
            this.stripeWidth = stripeWidth;
            this.quoteGapWidth = quoteGapWidth;
            return this;
        }

        /**
         * 设置缩进
         *
         * @param first 首行缩进
         * @param rest  剩余行缩进
         * @return {@link Builder}
         */
        public Builder setLeadingMargin(int first, int rest) {
            this.first = first;
            this.rest = rest;
            isLeadingMargin = true;
            return this;
        }

        /**
         * 设置间距
         *
         * @param margin 间距
         * @return {@link Builder}
         */
        public Builder setMargin(int margin) {
            this.margin = margin;
            this.text = " " + this.text;
            return this;
        }

        /**
         * 设置列表标记
         *
         * @param gapWidth 列表标记和文字间距离
         * @return {@link Builder}
         */
        public Builder setBullet(@ColorInt int gapWidth) {
            this.bulletColor = 0;
            this.bulletRadius = 3;
            this.bulletGapWidth = gapWidth;
            isBullet = true;
            return this;
        }

        /**
         * 设置列表标记
         *
         * @param color    列表标记的颜色
         * @param radius   列表标记颜色
         * @param gapWidth 列表标记和文字间距离
         * @return {@link Builder}
         */
        public Builder setBullet(@ColorInt int color, int radius, int gapWidth) {
            this.bulletColor = color;
            this.bulletRadius = radius;
            this.bulletGapWidth = gapWidth;
            isBullet = true;
            return this;
        }

        /**
         * 设置字体尺寸
         *
         * @param size 尺寸
         * @return {@link Builder}
         */
        public Builder setFontSize(int size) {
            this.fontSize = size;
            this.fontSizeIsDp = false;
            return this;
        }

        /**
         * 设置字体尺寸
         *
         * @param size 尺寸
         * @param isDp 是否使用dip
         * @return {@link Builder}
         */
        public Builder setFontSize(int size, boolean isDp) {
            this.fontSize = size;
            this.fontSizeIsDp = isDp;
            return this;
        }

        /**
         * 设置字体比例
         *
         * @param proportion 比例
         * @return {@link Builder}
         */
        public Builder setFontProportion(float proportion) {
            this.proportion = proportion;
            return this;
        }

        /**
         * 设置字体横向比例
         *
         * @param proportion 比例
         * @return {@link Builder}
         */
        public Builder setFontXProportion(float proportion) {
            this.xProportion = proportion;
            return this;
        }

        /**
         * 设置删除线
         *
         * @return {@link Builder}
         */
        public Builder setStrikethrough() {
            this.isStrikethrough = true;
            return this;
        }

        /**
         * 设置下划线
         *
         * @return {@link Builder}
         */
        public Builder setUnderline() {
            this.isUnderline = true;
            return this;
        }

        /**
         * 设置上标
         *
         * @return {@link Builder}
         */
        public Builder setSuperscript() {
            this.isSuperscript = true;
            return this;
        }

        /**
         * 设置下标
         *
         * @return {@link Builder}
         */
        public Builder setSubscript() {
            this.isSubscript = true;
            return this;
        }

        /**
         * 设置粗体
         *
         * @return {@link Builder}
         */
        public Builder setBold() {
            isBold = true;
            return this;
        }

        /**
         * 设置斜体
         *
         * @return {@link Builder}
         */
        public Builder setItalic() {
            isItalic = true;
            return this;
        }

        /**
         * 设置粗斜体
         *
         * @return {@link Builder}
         */
        public Builder setBoldItalic() {
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
         * @return {@link Builder}
         */
        public Builder setFontFamily(@NonNull String fontFamily) {
            this.fontFamily = fontFamily;
            return this;
        }

        /**
         * 设置字体
         *
         * @param typeface 字体
         * @return {@link Builder}
         */
        public Builder setTypeface(@NonNull Typeface typeface) {
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
         * @return {@link Builder}
         */
        public Builder setAlign(@NonNull Alignment alignment) {
            this.alignment = alignment;
            return this;
        }

        /**
         * 设置图片
         *
         * @param bitmap 图片位图
         * @return {@link Builder}
         */
        public Builder setBitmap(@NonNull Bitmap bitmap) {
            return setBitmap(bitmap, align);
        }

        /**
         * 设置图片
         *
         * @param bitmap 图片位图
         * @param align  对齐
         *               <ul>
         *               <li>{@link Align#ALIGN_TOP}顶部对齐</li>
         *               <li>{@link Align#ALIGN_CENTER}居中对齐</li>
         *               <li>{@link Align#ALIGN_BASELINE}基线对齐</li>
         *               <li>{@link Align#ALIGN_BOTTOM}底部对齐</li>
         *               </ul>
         * @return {@link Builder}
         */
        public Builder setBitmap(@NonNull Bitmap bitmap, @Align int align) {
            this.bitmap = bitmap;
            this.align = align;
            this.text = " " + this.text;
            imageIsBitmap = true;
            return this;
        }

        /**
         * 设置图片
         *
         * @param drawable 图片资源
         * @return {@link Builder}
         */
        public Builder setDrawable(@NonNull Drawable drawable) {
            return setDrawable(drawable, align);
        }

        /**
         * 设置图片
         *
         * @param drawable 图片资源
         * @param align    对齐
         *                 <ul>
         *                 <li>{@link Align#ALIGN_TOP}顶部对齐</li>
         *                 <li>{@link Align#ALIGN_CENTER}居中对齐</li>
         *                 <li>{@link Align#ALIGN_BASELINE}基线对齐</li>
         *                 <li>{@link Align#ALIGN_BOTTOM}底部对齐</li>
         *                 </ul>
         * @return {@link Builder}
         */
        public Builder setDrawable(@NonNull Drawable drawable, @Align int align) {
            this.drawable = drawable;
            this.align = align;
            this.text = " " + this.text;
            imageIsDrawable = true;
            return this;
        }

        /**
         * 设置图片
         *
         * @param uri 图片uri
         * @return {@link Builder}
         */
        public Builder setUri(@NonNull Uri uri) {
            setUri(uri, ALIGN_BOTTOM);
            return this;
        }

        /**
         * 设置图片
         *
         * @param uri   图片uri
         * @param align 对齐
         *              <ul>
         *              <li>{@link Align#ALIGN_TOP}顶部对齐</li>
         *              <li>{@link Align#ALIGN_CENTER}居中对齐</li>
         *              <li>{@link Align#ALIGN_BASELINE}基线对齐</li>
         *              <li>{@link Align#ALIGN_BOTTOM}底部对齐</li>
         *              </ul>
         * @return {@link Builder}
         */
        public Builder setUri(@NonNull Uri uri, @Align int align) {
            this.uri = uri;
            this.align = align;
            this.text = " " + this.text;
            imageIsUri = true;
            return this;
        }

        /**
         * 设置图片
         *
         * @param resourceId 图片资源id
         * @return {@link Builder}
         */
        public Builder setResourceId(@DrawableRes int resourceId) {
            return setResourceId(resourceId, align);
        }

        /**
         * 设置图片
         *
         * @param resourceId 图片资源id
         * @param align      对齐
         * @return {@link Builder}
         */
        public Builder setResourceId(@DrawableRes int resourceId, @Align int align) {
            this.resourceId = resourceId;
            this.align = align;
            this.text = " " + this.text;
            imageIsResourceId = true;
            return this;
        }

        /**
         * 设置点击事件
         * <p>需添加view.setMovementMethod(LinkMovementMethod.getInstance())</p>
         *
         * @param clickSpan 点击事件
         * @return {@link Builder}
         */
        public Builder setClickSpan(@NonNull ClickableSpan clickSpan) {
            this.clickSpan = clickSpan;
            return this;
        }

        /**
         * 设置超链接
         * <p>需添加view.setMovementMethod(LinkMovementMethod.getInstance())</p>
         *
         * @param url 超链接
         * @return {@link Builder}
         */
        public Builder setUrl(@NonNull String url) {
            this.url = url;
            return this;
        }

        /**
         * 设置模糊
         * <p>尚存bug，其他地方存在相同的字体的话，相同字体出现在之前的话那么就不会模糊，出现在之后的话那会一起模糊</p>
         * <p>推荐还是把所有字体都模糊这样使用</p>
         *
         * @param radius 模糊半径（需大于0）
         * @param style  模糊样式<ul>
         *               <li>{@link BlurMaskFilter.Blur#NORMAL}</li>
         *               <li>{@link BlurMaskFilter.Blur#SOLID}</li>
         *               <li>{@link BlurMaskFilter.Blur#OUTER}</li>
         *               <li>{@link BlurMaskFilter.Blur#INNER}</li>
         *               </ul>
         * @return {@link Builder}
         */
        public Builder setBlur(float radius, BlurMaskFilter.Blur style) {
            this.blurRadius = radius;
            this.style = style;
            this.isBlur = true;
            return this;
        }

        /**
         * 追加样式一行字符串
         *
         * @param text 样式字符串文本
         * @return {@link Builder}
         */
        public Builder appendLine(@NonNull CharSequence text) {
            return append(text + LINE_SEPARATOR);
        }

        /**
         * 追加样式字符串
         *
         * @param text 样式字符串文本
         * @return {@link Builder}
         */
        public Builder append(@NonNull CharSequence text) {
            setSpan();
            this.text = text;
            return this;
        }

        /**
         * 创建样式字符串
         *
         * @return 样式字符串
         */
        public SpannableStringBuilder create() {
            setSpan();
            return mBuilder;
        }

        /**
         * 设置样式
         */
        private void setSpan() {
            if (text == null || text.length() == 0) return;
            int start = mBuilder.length();
            mBuilder.append(this.text);
            int end = mBuilder.length();
            if (backgroundColor != defaultValue) {
                mBuilder.setSpan(new BackgroundColorSpan(backgroundColor), start, end, flag);
                backgroundColor = defaultValue;
            }
            if (foregroundColor != defaultValue) {
                mBuilder.setSpan(new ForegroundColorSpan(foregroundColor), start, end, flag);
                foregroundColor = defaultValue;
            }
            if (isLeadingMargin) {
                mBuilder.setSpan(new LeadingMarginSpan.Standard(first, rest), start, end, flag);
                isLeadingMargin = false;
            }
            if (margin != -1) {
                mBuilder.setSpan(new MarginSpan(margin), start, end, flag);
                margin = -1;
            }
            if (quoteColor != defaultValue) {
                mBuilder.setSpan(new CustomQuoteSpan(quoteColor, stripeWidth, quoteGapWidth), start, end, flag);
                quoteColor = defaultValue;
            }
            if (isBullet) {
                mBuilder.setSpan(new CustomBulletSpan(bulletColor, bulletRadius, bulletGapWidth), start, end, flag);
                isBullet = false;
            }
            if (fontSize != -1) {
                mBuilder.setSpan(new AbsoluteSizeSpan(fontSize, fontSizeIsDp), start, end, flag);
                fontSize = -1;
                fontSizeIsDp = false;
            }
            if (proportion != -1) {
                mBuilder.setSpan(new RelativeSizeSpan(proportion), start, end, flag);
                proportion = -1;
            }
            if (xProportion != -1) {
                mBuilder.setSpan(new ScaleXSpan(xProportion), start, end, flag);
                xProportion = -1;
            }
            if (isStrikethrough) {
                mBuilder.setSpan(new StrikethroughSpan(), start, end, flag);
                isStrikethrough = false;
            }
            if (isUnderline) {
                mBuilder.setSpan(new UnderlineSpan(), start, end, flag);
                isUnderline = false;
            }
            if (isSuperscript) {
                mBuilder.setSpan(new SuperscriptSpan(), start, end, flag);
                isSuperscript = false;
            }
            if (isSubscript) {
                mBuilder.setSpan(new SubscriptSpan(), start, end, flag);
                isSubscript = false;
            }
            if (isBold) {
                mBuilder.setSpan(new StyleSpan(Typeface.BOLD), start, end, flag);
                isBold = false;
            }
            if (isItalic) {
                mBuilder.setSpan(new StyleSpan(Typeface.ITALIC), start, end, flag);
                isItalic = false;
            }
            if (isBoldItalic) {
                mBuilder.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), start, end, flag);
                isBoldItalic = false;
            }
            if (fontFamily != null) {
                mBuilder.setSpan(new TypefaceSpan(fontFamily), start, end, flag);
                fontFamily = null;
            }
            if (typeface != null) {
                mBuilder.setSpan(new CustomTypefaceSpan(typeface), start, end, flag);
                typeface = null;
            }
            if (alignment != null) {
                mBuilder.setSpan(new AlignmentSpan.Standard(alignment), start, end, flag);
                alignment = null;
            }
            if (imageIsBitmap || imageIsDrawable || imageIsUri || imageIsResourceId) {
                if (imageIsBitmap) {
                    mBuilder.setSpan(new CustomImageSpan(Utils.getContext(), bitmap, align), start, end, flag);
                    bitmap = null;
                    imageIsBitmap = false;
                } else if (imageIsDrawable) {
                    mBuilder.setSpan(new CustomImageSpan(drawable, align), start, end, flag);
                    drawable = null;
                    imageIsDrawable = false;
                } else if (imageIsUri) {
                    mBuilder.setSpan(new CustomImageSpan(Utils.getContext(), uri, align), start, end, flag);
                    uri = null;
                    imageIsUri = false;
                } else {
                    mBuilder.setSpan(new CustomImageSpan(Utils.getContext(), resourceId, align), start, end, flag);
                    resourceId = 0;
                    imageIsResourceId = false;
                }
            }
            if (clickSpan != null) {
                mBuilder.setSpan(clickSpan, start, end, flag);
                clickSpan = null;
            }
            if (url != null) {
                mBuilder.setSpan(new URLSpan(url), start, end, flag);
                url = null;
            }
            if (isBlur) {
                mBuilder.setSpan(new MaskFilterSpan(new BlurMaskFilter(blurRadius, style)), start, end, flag);
                isBlur = false;
            }
            flag = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
        }
    }

    static class MarginSpan extends ReplacementSpan {

        private final int margin;

        private MarginSpan(int margin) {
            super();
            this.margin = margin;
        }

        @Override
        public int getSize(@NonNull Paint paint, CharSequence text, @IntRange(from = 0) int start, @IntRange(from = 0) int end, @Nullable Paint.FontMetricsInt fm) {
            text = " ";
            return margin;
        }

        @Override
        public void draw(@NonNull Canvas canvas, CharSequence text, @IntRange(from = 0) int start, @IntRange(from = 0) int end, float x, int top, int y, int bottom, @NonNull Paint paint) {

        }
    }

    static class CustomQuoteSpan implements LeadingMarginSpan {

        private final int color;
        private final int stripeWidth;
        private final int gapWidth;

        private CustomQuoteSpan(@ColorInt int color, int stripeWidth, int gapWidth) {
            super();
            this.color = color;
            this.stripeWidth = stripeWidth;
            this.gapWidth = gapWidth;
        }

        public int getLeadingMargin(boolean first) {
            return stripeWidth + gapWidth;
        }

        public void drawLeadingMargin(Canvas c, Paint p, int x, int dir,
                                      int top, int baseline, int bottom,
                                      CharSequence text, int start, int end,
                                      boolean first, Layout layout) {
            Paint.Style style = p.getStyle();
            int color = p.getColor();

            p.setStyle(Paint.Style.FILL);
            p.setColor(this.color);

            c.drawRect(x, top, x + dir * stripeWidth, bottom, p);

            p.setStyle(style);
            p.setColor(color);
        }
    }

    static class CustomBulletSpan implements LeadingMarginSpan {

        private final int color;
        private final int radius;
        private final int gapWidth;

        private static Path sBulletPath = null;

        private CustomBulletSpan(int color, int radius, int gapWidth) {
            this.color = color;
            this.radius = radius;
            this.gapWidth = gapWidth;
        }

        public int getLeadingMargin(boolean first) {
            return 2 * radius + gapWidth;
        }

        public void drawLeadingMargin(Canvas c, Paint p, int x, int dir,
                                      int top, int baseline, int bottom,
                                      CharSequence text, int start, int end,
                                      boolean first, Layout l) {
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

    @SuppressLint("ParcelCreator")
    static class CustomTypefaceSpan extends TypefaceSpan {

        private final Typeface newType;

        private CustomTypefaceSpan(Typeface type) {
            super("");
            newType = type;
        }

        @Override
        public void updateDrawState(TextPaint textPaint) {
            apply(textPaint, newType);
        }

        @Override
        public void updateMeasureState(TextPaint paint) {
            apply(paint, newType);
        }

        private static void apply(Paint paint, Typeface tf) {
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

            paint.setTypeface(tf);
        }
    }

    static class CustomImageSpan extends CustomDynamicDrawableSpan {
        private Drawable mDrawable;
        private Uri mContentUri;
        private int mResourceId;
        private Context mContext;

        CustomImageSpan(Context context, Bitmap b, int verticalAlignment) {
            super(verticalAlignment);
            mContext = context;
            mDrawable = context != null
                    ? new BitmapDrawable(context.getResources(), b)
                    : new BitmapDrawable(b);
            int width = mDrawable.getIntrinsicWidth();
            int height = mDrawable.getIntrinsicHeight();
            mDrawable.setBounds(0, 0, width > 0 ? width : 0, height > 0 ? height : 0);
        }

        CustomImageSpan(Drawable d, int verticalAlignment) {
            super(verticalAlignment);
            mDrawable = d;
            mDrawable.setBounds(0, 0, mDrawable.getIntrinsicWidth(),
                    mDrawable.getIntrinsicHeight());
        }

        CustomImageSpan(Context context, Uri uri, int verticalAlignment) {
            super(verticalAlignment);
            mContext = context;
            mContentUri = uri;
        }

        CustomImageSpan(Context context, @DrawableRes int resourceId, int verticalAlignment) {
            super(verticalAlignment);
            mContext = context;
            mResourceId = resourceId;
        }

        @Override
        public Drawable getDrawable() {
            Drawable drawable = null;
            if (mDrawable != null) {
                drawable = mDrawable;
            } else if (mContentUri != null) {
                Bitmap bitmap = null;
                try {
                    InputStream is = mContext.getContentResolver().openInputStream(
                            mContentUri);
                    bitmap = BitmapFactory.decodeStream(is);
                    drawable = new BitmapDrawable(mContext.getResources(), bitmap);
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight());
                    if (is != null) {
                        is.close();
                    }
                } catch (Exception e) {
                    Log.e("sms", "Failed to loaded content " + mContentUri, e);
                }
            } else {
                try {
                    drawable = ContextCompat.getDrawable(mContext, mResourceId);
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight());
                } catch (Exception e) {
                    Log.e("sms", "Unable to find resource: " + mResourceId);
                }
            }
            return drawable;
        }
    }

    static abstract class CustomDynamicDrawableSpan extends ReplacementSpan {

        static final int ALIGN_BOTTOM = 0;

        static final int ALIGN_BASELINE = 1;

        static final int ALIGN_CENTER = 2;

        static final int ALIGN_TOP = 3;

        final int mVerticalAlignment;

        CustomDynamicDrawableSpan() {
            mVerticalAlignment = ALIGN_BOTTOM;
        }

        CustomDynamicDrawableSpan(int verticalAlignment) {
            mVerticalAlignment = verticalAlignment;
        }

        public abstract Drawable getDrawable();

        @Override
        public int getSize(@NonNull Paint paint, CharSequence text,
                           int start, int end,
                           Paint.FontMetricsInt fm) {
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
                    } else if (mVerticalAlignment == ALIGN_BASELINE) {
                        fm.ascent -= rect.height() - fontHeight + fm.descent;
                    } else {
                        fm.ascent -= rect.height() - fontHeight;
                    }
                }
            }

            return rect.right;
        }

        @Override
        public void draw(@NonNull Canvas canvas, CharSequence text,
                         int start, int end, float x,
                         int top, int y, int bottom, @NonNull Paint paint) {
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
            } else {
                if (mVerticalAlignment == ALIGN_BASELINE) {
                    transY -= paint.getFontMetricsInt().descent;
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
}