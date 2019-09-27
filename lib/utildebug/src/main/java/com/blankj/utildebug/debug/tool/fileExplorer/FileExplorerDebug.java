package com.blankj.utildebug.debug.tool.fileExplorer;

import android.content.Context;
import android.view.View;

import com.blankj.utildebug.R;
import com.blankj.utildebug.debug.tool.AbsToolDebug;
import com.blankj.utildebug.menu.DebugMenu;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/09/04
 *     desc  :
 * </pre>
 */
public class FileExplorerDebug extends AbsToolDebug {

    @Override
    public void onAppCreate(Context context) {

    }

    @Override
    public int getIcon() {
        return R.drawable.du_ic_debug_file_explorer;
    }

    @Override
    public int getName() {
        return R.string.du_file_explorer;
    }

    @Override
    public void onClick(View view) {
        DebugMenu.getInstance().dismiss();
        new FileExplorerFloatView().show();
    }
}
