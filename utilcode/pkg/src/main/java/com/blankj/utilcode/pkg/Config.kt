package com.blankj.utilcode.pkg

import android.os.Environment
import com.blankj.utilcode.util.Utils

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
    val CACHE_PATH: String
    val TEST_APK_PATH: String

    init {
        val cacheDir = Utils.getApp().externalCacheDir
        CACHE_PATH = if (cacheDir != null) {
            cacheDir.absolutePath
        } else {
            Environment.getExternalStorageDirectory().absolutePath
        }
        TEST_APK_PATH = CACHE_PATH + FILE_SEP + "test_install.apk"
    }
}
