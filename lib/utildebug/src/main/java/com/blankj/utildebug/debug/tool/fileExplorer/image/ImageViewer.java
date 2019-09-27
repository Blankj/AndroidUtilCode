package com.blankj.utildebug.debug.tool.fileExplorer.image;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utildebug.R;
import com.blankj.utildebug.base.view.BaseContentView;
import com.blankj.utildebug.debug.tool.fileExplorer.FileExplorerFloatView;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/09/08
 *     desc  :
 * </pre>
 */
public class ImageViewer extends BaseContentView<FileExplorerFloatView> {

    private File mFile;

    private PhotoView photoView;

    public static void show(FileExplorerFloatView floatView, File file) {
        new ImageViewer(file).attach(floatView, true);
    }

    public ImageViewer(File file) {
        mFile = file;
    }

    @Override
    public int bindLayout() {
        return R.layout.du_debug_file_explorer_image;
    }

    @Override
    public void onAttach() {
        photoView = findViewById(R.id.imageViewerPv);
        photoView.setImageBitmap(ImageUtils.getBitmap(mFile));
    }
}
