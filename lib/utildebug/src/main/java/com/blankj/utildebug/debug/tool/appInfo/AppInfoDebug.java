package com.blankj.utildebug.debug.tool.appInfo;

import android.content.Context;
import android.view.View;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utildebug.R;
import com.blankj.utildebug.debug.tool.AbsToolDebug;
import com.blankj.utildebug.menu.DebugMenu;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/09/02
 *     desc  :
 * </pre>
 */
public class AppInfoDebug extends AbsToolDebug {

    @Override
    public void onAppCreate(Context context) {

    }

    @Override
    public int getIcon() {
        int appIconId = AppUtils.getAppIconId();
        if (appIconId != 0) return appIconId;
        return R.drawable.du_ic_debug_app_info_default;
    }

    @Override
    public int getName() {
        return R.string.du_app_info;
    }

    @Override
    public void onClick(View view) {
        DebugMenu.getInstance().dismiss();
        new AppInfoFloatView().show();
    }
}
