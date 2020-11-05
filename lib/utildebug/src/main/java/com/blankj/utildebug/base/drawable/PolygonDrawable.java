package com.blankj.utildebug.base.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/09/04
 *     desc  :
 * </pre>
 */
public class PolygonDrawable extends Drawable {

    private Paint mPaint;
    private int   mNum;

    public PolygonDrawable(int num, int color) {
        mNum = num;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(color);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (mNum < 3) return;
        final Rect rect = getBounds();
        float r = rect.right / 2;
        float x = r;
        float y = r;
        Path path = new Path();
        for (int i = 0; i <= mNum; i++) {
            float alpha = Double.valueOf(((2f / mNum) * i - 0.5) * Math.PI).floatValue();
            float nextX = x + Double.valueOf(r * Math.cos(alpha)).floatValue();
            float nextY = y + Double.valueOf(r * Math.sin(alpha)).floatValue();
            if (i == 0) {
                path.moveTo(nextX, nextY);
            } else {
                path.lineTo(nextX, nextY);
            }
        }
        canvas.drawPath(path, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }
}
