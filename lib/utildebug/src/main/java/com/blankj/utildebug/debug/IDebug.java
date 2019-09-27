package com.blankj.utildebug.debug;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.StringRes;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/08/28
 *     desc  :
 * </pre>
 */
public interface IDebug {

    void onAppCreate(Context context);

    @Category
    int getCategory();

    @DrawableRes
    int getIcon();

    @StringRes
    int getName();

    void onClick(View view);

    int TOOLS       = 0;
    int PERFORMANCE = 1;
    int UI          = 2;
    int BIZ         = 3;

    @IntDef({TOOLS, PERFORMANCE, UI, BIZ})
    @Retention(RetentionPolicy.SOURCE)
    @interface Category {
    }
}
