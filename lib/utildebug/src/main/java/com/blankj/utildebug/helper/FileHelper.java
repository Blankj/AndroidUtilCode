package com.blankj.utildebug.helper;

import android.support.annotation.IntDef;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.StringUtils;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/09/08
 *     desc  :
 * </pre>
 */
public class FileHelper {

    public static final int IMAGE   = 0;
    public static final int SP      = 1;
    public static final int UTF8    = 2;
    public static final int UNKNOWN = -1;

    @IntDef({IMAGE, SP, UTF8, UNKNOWN})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FileType {
    }

    @FileType
    public static int getFileType(String path) {
        return getFileType(FileUtils.getFileByPath(path));
    }

    @FileType
    public static int getFileType(File file) {
        if (!FileUtils.isFileExists(file)) return UNKNOWN;
        if (ImageUtils.isImage(file)) {
            return IMAGE;
        }
        if (FileUtils.getFileExtension(file).equals("xml")) {
            File parentFile = file.getParentFile();
            if (parentFile != null) {
                if (StringUtils.equals(parentFile.getName(), "shared_prefs")) {
                    return SP;
                }
            }
        }
        if (FileUtils.isUtf8(file)) {
            return UTF8;
        }
        return UNKNOWN;
    }
}
