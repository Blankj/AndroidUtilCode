package com.blankj.utilcode.pkg.feature.path

import android.content.Context
import android.content.Intent
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemTitle
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.PathUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/13
 * desc  : demo about PathUtils
 * ```
 */
class PathActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, PathActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_path
    }

    override fun bindItems(): MutableList<CommonItem<*>> {
        return CollectionUtils.newArrayList(
                CommonItemTitle("getRootPath", PathUtils.getRootPath()),
                CommonItemTitle("getDataPath", PathUtils.getDataPath()),
                CommonItemTitle("getDownloadCachePath", PathUtils.getDownloadCachePath()),

                CommonItemTitle("getInternalAppDataPath", PathUtils.getInternalAppDataPath()),
                CommonItemTitle("getInternalAppCodeCacheDir", PathUtils.getInternalAppCodeCacheDir()),
                CommonItemTitle("getInternalAppCachePath", PathUtils.getInternalAppCachePath()),
                CommonItemTitle("getInternalAppDbsPath", PathUtils.getInternalAppDbsPath()),
                CommonItemTitle("getInternalAppDbPath", PathUtils.getInternalAppDbPath("demo")),
                CommonItemTitle("getInternalAppFilesPath", PathUtils.getInternalAppFilesPath()),
                CommonItemTitle("getInternalAppSpPath", PathUtils.getInternalAppSpPath()),
                CommonItemTitle("getInternalAppNoBackupFilesPath", PathUtils.getInternalAppNoBackupFilesPath()),

                CommonItemTitle("getExternalStoragePath", PathUtils.getExternalStoragePath()),
                CommonItemTitle("getExternalMusicPath", PathUtils.getExternalMusicPath()),
                CommonItemTitle("getExternalPodcastsPath", PathUtils.getExternalPodcastsPath()),
                CommonItemTitle("getExternalRingtonesPath", PathUtils.getExternalRingtonesPath()),
                CommonItemTitle("getExternalAlarmsPath", PathUtils.getExternalAlarmsPath()),
                CommonItemTitle("getExternalNotificationsPath", PathUtils.getExternalNotificationsPath()),
                CommonItemTitle("getExternalPicturesPath", PathUtils.getExternalPicturesPath()),
                CommonItemTitle("getExternalMoviesPath", PathUtils.getExternalMoviesPath()),
                CommonItemTitle("getExternalDownloadsPath", PathUtils.getExternalDownloadsPath()),
                CommonItemTitle("getExternalDcimPath", PathUtils.getExternalDcimPath()),
                CommonItemTitle("getExternalDocumentsPath", PathUtils.getExternalDocumentsPath()),

                CommonItemTitle("getExternalAppDataPath", PathUtils.getExternalAppDataPath()),
                CommonItemTitle("getExternalAppCachePath", PathUtils.getExternalAppCachePath()),
                CommonItemTitle("getExternalAppFilesPath", PathUtils.getExternalAppFilesPath()),
                CommonItemTitle("getExternalAppMusicPath", PathUtils.getExternalAppMusicPath()),
                CommonItemTitle("getExternalAppPodcastsPath", PathUtils.getExternalAppPodcastsPath()),
                CommonItemTitle("getExternalAppRingtonesPath", PathUtils.getExternalAppRingtonesPath()),
                CommonItemTitle("getExternalAppAlarmsPath", PathUtils.getExternalAppAlarmsPath()),
                CommonItemTitle("getExternalAppNotificationsPath", PathUtils.getExternalAppNotificationsPath()),
                CommonItemTitle("getExternalAppPicturesPath", PathUtils.getExternalAppPicturesPath()),
                CommonItemTitle("getExternalAppMoviesPath", PathUtils.getExternalAppMoviesPath()),
                CommonItemTitle("getExternalAppDownloadPath", PathUtils.getExternalAppDownloadPath()),
                CommonItemTitle("getExternalAppDcimPath", PathUtils.getExternalAppDcimPath()),
                CommonItemTitle("getExternalAppDocumentsPath", PathUtils.getExternalAppDocumentsPath()),
                CommonItemTitle("getExternalAppObbPath", PathUtils.getExternalAppObbPath())
        )
    }
}
