package com.blankj.utilcode.pkg

import com.blankj.utilcode.util.PathUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/05/10
 * desc  : config about constants
 * ```
 */
object Config {
    val FILE_SEP = System.getProperty("file.separator")
    val LINE_SEP = System.getProperty("line.separator")
    const val TEST_PKG = "com.blankj.testinstall"
    val CACHE_PATH = PathUtils.getCachePathExternalFirst() + FILE_SEP
    val TEST_APK_PATH: String = CACHE_PATH + "test_install.apk"
}
