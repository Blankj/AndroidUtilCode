package com.blankj.utildebug.debug;

import android.content.Context;
import android.view.View;

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

    int getCategory();

    int getIcon();

    int getName();

    void onClick(View view);

    int TOOLS       = 0;
    int PERFORMANCE = 1;
    int UI          = 2;
    int BIZ         = 3;
}
