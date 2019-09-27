package com.blankj.utildebug.debug.tool.restartApp;

import android.content.Context;
import android.view.View;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utildebug.R;
import com.blankj.utildebug.debug.tool.AbsToolDebug;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/09/04
 *     desc  :
 * </pre>
 */
public class RestartAppDebug extends AbsToolDebug {

    @Override
    public void onAppCreate(Context context) {

    }

    @Override
    public int getIcon() {
        return R.drawable.du_ic_debug_restart_app;
    }

    @Override
    public int getName() {
        return R.string.du_restart_app;
    }

    @Override
    public void onClick(View view) {
        AppUtils.relaunchApp(true);
    }
}
