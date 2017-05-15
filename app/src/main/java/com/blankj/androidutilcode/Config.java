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
    private static String testApkPath;

    public static String getTestApkPath() {
        if (testApkPath == null)
            testApkPath = Utils.getContext().getCacheDir().getAbsolutePath() + File.separatorChar + "apk" + File.separatorChar + "test_install.apk";
        return testApkPath;
    }
}
