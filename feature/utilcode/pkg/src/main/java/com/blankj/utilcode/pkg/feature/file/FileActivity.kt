package com.blankj.utilcode.pkg.feature.file

import android.content.Context
import android.content.Intent
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemTitle
import com.blankj.utilcode.pkg.Config.CACHE_PATH
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.PathUtils
import com.blankj.utilcode.util.SnackbarUtils
import java.io.File

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/29
 * desc  : demo about FileUtils
 * ```
 */
class FileActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, FileActivity::class.java)
            context.startActivity(starter)
        }

        val TEST_FILE_PATH: String = CACHE_PATH + "test_file.txt"
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_file
    }

    override fun bindItems(): MutableList<CommonItem<*>> {
        return CollectionUtils.newArrayList(
                CommonItemTitle("isFileExists: " + PathUtils.getInternalAppFilesPath(), "" + FileUtils.isFileExists(PathUtils.getInternalAppFilesPath())),
                CommonItemTitle("isFileExists: " + PathUtils.getExternalAppFilesPath(), "" + FileUtils.isFileExists(PathUtils.getExternalAppFilesPath())),
                CommonItemTitle("isFileExists: " + PathUtils.getExternalStoragePath(), "" + FileUtils.isFileExists(PathUtils.getExternalStoragePath())),
                CommonItemTitle("isFileExists: " + PathUtils.getDownloadCachePath(), "" + FileUtils.isFileExists(PathUtils.getDownloadCachePath())),
                CommonItemTitle("isFileExists: " + PathUtils.getExternalDownloadsPath(), "" + FileUtils.isFileExists(PathUtils.getExternalDownloadsPath())),

                CommonItemTitle("isFileExists: " + PathUtils.getInternalAppFilesPath(), "" + FileUtils.isFileExists(File(PathUtils.getInternalAppFilesPath()))),
                CommonItemTitle("isFileExists: " + PathUtils.getExternalAppFilesPath(), "" + FileUtils.isFileExists(File(PathUtils.getExternalAppFilesPath()))),
                CommonItemTitle("isFileExists: " + PathUtils.getExternalStoragePath(), "" + FileUtils.isFileExists(File(PathUtils.getExternalStoragePath()))),
                CommonItemTitle("isFileExists: " + PathUtils.getDownloadCachePath(), "" + FileUtils.isFileExists(File(PathUtils.getDownloadCachePath()))),
                CommonItemTitle("isFileExists: " + PathUtils.getExternalDownloadsPath(), "" + FileUtils.isFileExists(File(PathUtils.getExternalDownloadsPath())))
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        SnackbarUtils.dismiss()
    }
}
