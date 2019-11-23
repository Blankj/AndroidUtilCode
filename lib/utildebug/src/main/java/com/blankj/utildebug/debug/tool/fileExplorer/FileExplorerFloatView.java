package com.blankj.utildebug.debug.tool.fileExplorer;

import com.blankj.utildebug.R;
import com.blankj.utildebug.base.view.BaseContentFloatView;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/09/04
 *     desc  :
 * </pre>
 */
public class FileExplorerFloatView extends BaseContentFloatView<FileExplorerFloatView> {

    @Override
    public int bindTitle() {
        return R.string.du_file_explorer;
    }

    @Override
    public int bindContentLayout() {
        return NO_ID;
    }

    @Override
    public void initContentView() {
        FileContentView.show(this);
    }
}
