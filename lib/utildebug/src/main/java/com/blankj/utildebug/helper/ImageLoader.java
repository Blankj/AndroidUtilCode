package com.blankj.utildebug.helper;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.ThreadUtils;

import java.io.File;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/09/06
 *     desc  :
 * </pre>
 */
public class ImageLoader {

    public static void load(final String path, final ImageView imageView) {
        load(FileUtils.getFileByPath(path), imageView);
    }

    public static void load(final File file, final ImageView imageView) {
        if (!FileUtils.isFileExists(file)) return;
        imageView.post(new Runnable() {
            @Override
            public void run() {
                ThreadUtils.executeByCached(new ThreadUtils.SimpleTask<Bitmap>() {
                    @Override
                    public Bitmap doInBackground() throws Throwable {
                        return ImageUtils.getBitmap(file, imageView.getWidth(), imageView.getHeight());
                    }

                    @Override
                    public void onSuccess(final Bitmap result) {
                        imageView.setImageBitmap(result);
                    }
                });
            }
        });
    }
}
