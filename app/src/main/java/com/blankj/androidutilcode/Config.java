package com.blankj.androidutilcode;

import android.os.Environment;

import com.blankj.utilcode.util.Utils;

import java.io.File;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/05/10
 *     desc  : 配置常量
 * </pre>
 */
public class Config {

    public static final String FILE_SEP = System.getProperty("file.separator");
    public static final String LINE_SEP = System.getProperty("line.separator");
    public static final String PKG      = "com.blankj.androidutilcode";
    public static final String TEST_PKG = "com.blankj.testinstall";
    public static final String GITHUB   = "https://github.com/Blankj/AndroidUtilCode";
    public static final String BLOG     = "http://www.jianshu.com/u/46702d5c6978";
    public static final String CACHE_PATH;
    public static final String TEST_APK_PATH;

    static {
        File cacheDir = Utils.getApp().getExternalCacheDir();
        if (cacheDir != null) {
            CACHE_PATH = cacheDir.getAbsolutePath();
        } else {
            CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        TEST_APK_PATH = CACHE_PATH + FILE_SEP + "test_install.apk";

    }
}
