package com.blankj.utildebug.debug.tool.deviceInfo;

import android.content.Context;
import android.view.View;

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
public class DeviceInfoDebug extends AbsToolDebug {

    @Override
    public void onAppCreate(Context context) {

    }

    @Override
    public int getIcon() {
        return R.drawable.du_ic_debug_device_info;
    }

    @Override
    public int getName() {
        return R.string.du_device_info;
    }

    @Override
    public void onClick(View view) {
        DebugMenu.getInstance().dismiss();
        new DeviceInfoFloatView().show();
    }
}
