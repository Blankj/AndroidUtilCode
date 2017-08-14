package com.blankj.androidutilcode;

import com.blankj.utilcode.util.Utils;

import java.io.File;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/05/10
 *     desc  :
 * </pre>
 */
public class Config {
    public static final String PKG      = "com.blankj.androidutilcode";
    public static final String TEST_PKG = "com.blankj.testinstall";
    public static final String GITHUB   = "https://github.com/Blankj/AndroidUtilCode";
    public static final String BLOG     = "http://www.jianshu.com/u/46702d5c6978";


    private static String testApkPath;

    public static String getTestApkPath() {
        if (testApkPath == null)
            testApkPath = Utils.getApp().getCacheDir().getAbsolutePath() + File.separatorChar + "apk" + File.separatorChar + "test_install.apk";
        return testApkPath;
    }
}