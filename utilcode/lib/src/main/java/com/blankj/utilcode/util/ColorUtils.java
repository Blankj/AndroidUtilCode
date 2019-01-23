package com.blankj.utilcode.util;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/01/15
 *     desc  : utils about color
 * </pre>
 */
public final class ColorUtils {

    private ColorUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Returns a color associated with a particular resource ID.
     *
     * @param id The desired resource identifier.
     * @return a color associated with a particular resource ID
     */
    public static int getColor(@ColorRes int id) {
        return ContextCompat.getColor(Utils.getApp(), id);
    }

    /**
     * Set the alpha component of {@code color} to be {@code alpha}.
     *
     * @param color The color.
     * @param alpha Alpha component \([0..255]\) of the color.
     * @return the {@code color} with {@code alpha} component
     */
    public static int setAlphaComponent(@ColorInt final int color,
                                        @IntRange(from = 0x0, to = 0xFF) int alpha) {
        return (color & 0x00ffffff) | (alpha << 24);
    }

    /**
     * Set the alpha component of {@code color} to be {@code alpha}.
     *
     * @param color The color.
     * @param alpha Alpha component \([0..1]\) of the color.
     * @return the {@code color} with {@code alpha} component
     */
    public static int setAlphaComponent(@ColorInt int color,
                                        @FloatRange(from = 0, to = 1) float alpha) {
        return (color & 0x00ffffff) | ((int) (alpha * 255.0f + 0.5f) << 24);
    }

    /**
     * Set the red component of {@code color} to be {@code red}.
     *
     * @param color The color.
     * @param red   Red component \([0..255]\) of the color.
     * @return the {@code color} with {@code red} component
     */
    public static int setRedComponent(@ColorInt int color,
                                      @IntRange(from = 0x0, to = 0xFF) int red) {
        return (color & 0xff00ffff) | (red << 16);
    }

    /**
     * Set the red component of {@code color} to be {@code red}.
     *
     * @param color The color.
     * @param red   Red component \([0..1]\) of the color.
     * @return the {@code color} with {@code red} component
     */
    public static int setRedComponent(@ColorInt int color,
                                      @FloatRange(from = 0, to = 1) float red) {
        return (color & 0xff00ffff) | ((int) (red * 255.0f + 0.5f) << 16);
    }

    /**
     * Set the green component of {@code color} to be {@code green}.
     *
     * @param color The color.
     * @param green Green component \([0..255]\) of the color.
     * @return the {@code color} with {@code green} component
     */
    public static int setGreenComponent(@ColorInt int color,
                                        @IntRange(from = 0x0, to = 0xFF) int green) {
        return (color & 0xffff00ff) | (green << 8);
    }

    /**
     * Set the green component of {@code color} to be {@code green}.
     *
     * @param color The color.
     * @param green Green component \([0..1]\) of the color.
     * @return the {@code color} with {@code green} component
     */
    public static int setGreenComponent(@ColorInt int color,
                                        @FloatRange(from = 0, to = 1) float green) {
        return (color & 0xffff00ff) | ((int) (green * 255.0f + 0.5f) << 8);
    }

    /**
     * Set the blue component of {@code color} to be {@code blue}.
     *
     * @param color The color.
     * @param blue  Blue component \([0..255]\) of the color.
     * @return the {@code color} with {@code blue} component
     */
    public static int setBlueComponent(@ColorInt int color,
                                       @IntRange(from = 0x0, to = 0xFF) int blue) {
        return (color & 0xffffff00) | blue;
    }

    /**
     * Set the blue component of {@code color} to be {@code blue}.
     *
     * @param color The color.
     * @param blue  Blue component \([0..1]\) of the color.
     * @return the {@code color} with {@code blue} component
     */
    public static int setBlueComponent(@ColorInt int color,
                                       @FloatRange(from = 0, to = 1) float blue) {
        return (color & 0xffffff00) | (int) (blue * 255.0f + 0.5f);
    }

    /**
     * Color-string to color-int.
     * <p>Supported formats are:</p>
     *
     * <ul>
     * <li><code>#RRGGBB</code></li>
     * <li><code>#AARRGGBB</code></li>
     * </ul>
     *
     * <p>The following names are also accepted: <code>red</code>, <code>blue</code>,
     * <code>green</code>, <code>black</code>, <code>white</code>, <code>gray</code>,
     * <code>cyan</code>, <code>magenta</code>, <code>yellow</code>, <code>lightgray</code>,
     * <code>darkgray</code>, <code>grey</code>, <code>lightgrey</code>, <code>darkgrey</code>,
     * <code>aqua</code>, <code>fuchsia</code>, <code>lime</code>, <code>maroon</code>,
     * <code>navy</code>, <code>olive</code>, <code>purple</code>, <code>silver</code>,
     * and <code>teal</code>.</p>
     *
     * @param colorString The color-string.
     * @return color-int
     * @throws IllegalArgumentException The string cannot be parsed.
     */
    public static int string2Int(@NonNull String colorString) {
        return Color.parseColor(colorString);
    }

    /**
     * Color-int to color-string.
     *
     * @param colorInt The color-int.
     * @return color-string
     */
    public static String int2RgbString(@ColorInt int colorInt) {
        colorInt = colorInt & 0x00ffffff;
        String color = Integer.toHexString(colorInt);
        while (color.length() < 6) {
            color = "0" + color;
        }
        return "#" + color;
    }

    /**
     * Color-int to color-string.
     *
     * @param colorInt The color-int.
     * @return color-string
     */
    public static String int2ArgbString(@ColorInt final int colorInt) {
        String color = Integer.toHexString(colorInt);
        while (color.length() < 6) {
            color = "0" + color;
        }
        while (color.length() < 8) {
            color = "f" + color;
        }
        return "#" + color;
    }

    /**
     * Return the random color.
     *
     * @return the random color
     */
    public static int getRandomColor() {
        return getRandomColor(true);
    }

    /**
     * Return the random color.
     *
     * @param supportAlpha True to support alpha, false otherwise.
     * @return the random color
     */
    public static int getRandomColor(final boolean supportAlpha) {
        int high = supportAlpha ? (int) (Math.random() * 0x100) << 24 : 0xFF000000;
        return high | (int) (Math.random() * 0x1000000);
    }
}
