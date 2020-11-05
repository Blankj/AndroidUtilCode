package com.blankj.utildebug.debug.tool.clearStorage;

import android.content.Context;
import android.view.View;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ShellUtils;
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
public class ClearStorageDebug extends AbsToolDebug {

    @Override
    public void onAppCreate(Context context) {

    }

    @Override
    public int getIcon() {
        return R.drawable.du_ic_debug_clear_storage;
    }

    @Override
    public int getName() {
        return R.string.du_clear_storage;
    }

    @Override
    public void onClick(View view) {
        ShellUtils.execCmd("pm clear " + AppUtils.getAppPackageName(), false);
    }
}
