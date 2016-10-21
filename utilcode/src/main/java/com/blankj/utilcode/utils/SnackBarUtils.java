package com.blankj.utilcode.utils;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.R;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/16
 *     desc  : SnackBar相关工具类
 * </pre>
 */

public class SnackbarUtils {
    private static Snackbar snackbar;

    public enum SnackbarType {
        INFO,
        CONFIRM,
        WARNING,
        ALERT
    }

    private Snackbar mSnackbar;

    public static Snackbar getSnackbar() {
        return snackbar;
    }

    public static final int INFO    = 1;
    public static final int CONFIRM = 2;
    public static final int WARNING = 3;
    public static final int ALERT   = 4;

    public static int red    = 0xfff44336;
    public static int green  = 0xff4caf50;
    public static int blue   = 0xff2195f3;
    public static int orange = 0xffffc107;

    /**
     * Snackbar：自定义颜色的短显示
     *
     * @param view      视图
     * @param text      文本
     * @param textColor 文本颜色
     * @param bgColor
     * @return
     */
    public static Snackbar shortSnackbar(View view, CharSequence text, int textColor, int bgColor) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
        return setSnackBarColor(snackbar, textColor, bgColor);
    }

    /**
     * Snackbar：自定义颜色的长显示
     *
     * @param view
     * @param text
     * @param textColor
     * @param bgColor
     * @return
     */
    public static Snackbar longSnackbar(View view, CharSequence text, int textColor, int bgColor) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        return setSnackBarColor(snackbar, textColor, bgColor);
    }


    /**
     * 短显示Snackbar，可选预设类型
     *
     * @param view
     * @param text
     * @param type
     * @return
     */
    public static Snackbar shortSnackbar(View view, CharSequence text, int type) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
        switchType(snackbar, type);
        return snackbar;
    }

    /**
     * 长显示Snackbar，可选预设类型
     *
     * @param view
     * @param text
     * @param type
     * @return
     */
    public static Snackbar longSnackbar(View view, CharSequence text, int type) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        switchType(snackbar, type);
        return snackbar;
    }


    /**
     * 自定义时常显示Snackbar，自定义颜色
     *
     * @param view
     * @param text
     * @param textColor
     * @param bgColor
     * @return
     */
    public static Snackbar IndefiniteSnackbar(View view, CharSequence text, int duration,
                                              int textColor, int bgColor) {
        Snackbar snackbar = Snackbar
                .make(view, text, Snackbar.LENGTH_INDEFINITE)
                .setDuration(duration);
        return setSnackBarColor(snackbar, textColor, bgColor);
    }

    /**
     * 自定义时常显示Snackbar，可选预设类型
     *
     * @param view
     * @param text
     * @param type
     * @return
     */
    public static Snackbar IndefiniteSnackbar(View view, String text, int duration, int type) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_INDEFINITE).setDuration(duration);
        switchType(snackbar, type);
        return snackbar;
    }

    /**
     * 设置Snackbar背景颜色
     *
     * @param snackbar
     * @param bgColor
     */
    public static Snackbar setSnackBarColor(Snackbar snackbar, int bgColor) {
        snackbar.getView().setBackgroundColor(bgColor);
        return snackbar;
    }

    /**
     * 设置SnackBar消息的颜色
     *
     * @param snackbar
     * @param textColor
     */
    public static void setSnackBarTextColor(Snackbar snackbar, int textColor) {
        ((TextView) snackbar.getView().findViewById(R.id.snackbar_text)).setTextColor(textColor);
    }

    /**
     * 设置Snackbar文字和背景颜色
     *
     * @param snackbar
     * @param textColor
     * @param bgColor
     */
    public static Snackbar setSnackBarColor(Snackbar snackbar, int textColor, int bgColor) {
        View view = snackbar.getView();
        view.setBackgroundColor(bgColor);
        ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(textColor);
        return snackbar;
    }

    /**
     * 为SnackBar添加布局
     *
     * @param snackbar SnackBar实例
     * @param layoutId 布局文件
     * @param index    位置
     */
    public static void addView(Snackbar snackbar, int layoutId, int index) {
        View view = snackbar.getView();
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) view;
        View add_view = LayoutInflater.from(view.getContext()).inflate(layoutId, null);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        p.gravity = Gravity.CENTER_VERTICAL;
        layout.addView(add_view, index, p);
    }

    private static void switchType(Snackbar snackbar, int type) {
        switch (type) {
            case INFO:
                setSnackBarColor(snackbar, blue);
                break;
            case CONFIRM:
                setSnackBarColor(snackbar, green);
                break;
            case WARNING:
                setSnackBarColor(snackbar, orange);
                break;
            case ALERT:
                setSnackBarColor(snackbar, Color.YELLOW, red);
                break;
        }
    }
}
