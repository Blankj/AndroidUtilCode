package com.blankj.utildebug.debug.tool.logcat;

import android.content.Context;
import android.view.View;

import com.blankj.utildebug.R;
import com.blankj.utildebug.base.view.FloatToast;
import com.blankj.utildebug.debug.tool.AbsToolDebug;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/09/05
 *     desc  :
 * </pre>
 */
public class LogcatDebug extends AbsToolDebug {
    @Override
    public void onAppCreate(Context context) {

    }

    @Override
    public int getIcon() {
        return R.drawable.du_ic_debug_logcat;
    }

    @Override
    public int getName() {
        return R.string.du_logcat;
    }

    @Override
    public void onClick(View view) {
        FloatToast.showShort(FloatToast.WARNING, "Developing...");
    }
}
