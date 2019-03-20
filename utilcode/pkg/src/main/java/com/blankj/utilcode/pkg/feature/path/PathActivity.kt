package com.blankj.utilcode.pkg.feature.path

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.lib.base.BaseTitleBarActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.PathUtils
import com.blankj.utilcode.util.SpanUtils
import kotlinx.android.synthetic.main.activity_path.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/13
 * desc  : demo about PathUtils
 * ```
 */
class PathActivity : BaseTitleBarActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, PathActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_path)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_path
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        SpanUtils.with(pathAboutTv)
                .appendLine("getRootPath: " + PathUtils.getRootPath())
                .appendLine("getDataPath: " + PathUtils.getDataPath())
                .appendLine("getDownloadCachePath: " + PathUtils.getDownloadCachePath())

                .appendLine("getInternalAppDataPath: " + PathUtils.getInternalAppDataPath())
                .appendLine("getInternalAppCodeCacheDir: " + PathUtils.getInternalAppCodeCacheDir())
                .appendLine("getInternalAppCachePath: " + PathUtils.getInternalAppCachePath())
                .appendLine("getInternalAppDbsPath: " + PathUtils.getInternalAppDbsPath())
                .appendLine("getInternalAppDbPath: " + PathUtils.getInternalAppDbPath("demo"))
                .appendLine("getInternalAppFilesPath: " + PathUtils.getInternalAppFilesPath())
                .appendLine("getInternalAppSpPath: " + PathUtils.getInternalAppSpPath())
                .appendLine("getInternalAppNoBackupFilesPath: " + PathUtils.getInternalAppNoBackupFilesPath())

                .appendLine("getExternalStoragePath: " + PathUtils.getExternalStoragePath())
                .appendLine("getExternalMusicPath: " + PathUtils.getExternalMusicPath())
                .appendLine("getExternalPodcastsPath: " + PathUtils.getExternalPodcastsPath())
                .appendLine("getExternalRingtonesPath: " + PathUtils.getExternalRingtonesPath())
                .appendLine("getExternalAlarmsPath: " + PathUtils.getExternalAlarmsPath())
                .appendLine("getExternalNotificationsPath: " + PathUtils.getExternalNotificationsPath())
                .appendLine("getExternalPicturesPath: " + PathUtils.getExternalPicturesPath())
                .appendLine("getExternalMoviesPath: " + PathUtils.getExternalMoviesPath())
                .appendLine("getExternalDownloadsPath: " + PathUtils.getExternalDownloadsPath())
                .appendLine("getExternalDcimPath: " + PathUtils.getExternalDcimPath())
                .appendLine("getExternalDocumentsPath: " + PathUtils.getExternalDocumentsPath())

                .appendLine("getExternalAppDataPath: " + PathUtils.getExternalAppDataPath())
                .appendLine("getExternalAppCachePath: " + PathUtils.getExternalAppCachePath())
                .appendLine("getExternalAppFilesPath: " + PathUtils.getExternalAppFilesPath())
                .appendLine("getExternalAppMusicPath: " + PathUtils.getExternalAppMusicPath())
                .appendLine("getExternalAppPodcastsPath: " + PathUtils.getExternalAppPodcastsPath())
                .appendLine("getExternalAppRingtonesPath: " + PathUtils.getExternalAppRingtonesPath())
                .appendLine("getExternalAppAlarmsPath: " + PathUtils.getExternalAppAlarmsPath())
                .appendLine("getExternalAppNotificationsPath: " + PathUtils.getExternalAppNotificationsPath())
                .appendLine("getExternalAppPicturesPath: " + PathUtils.getExternalAppPicturesPath())
                .appendLine("getExternalAppMoviesPath: " + PathUtils.getExternalAppMoviesPath())
                .appendLine("getExternalAppDownloadPath: " + PathUtils.getExternalAppDownloadPath())
                .appendLine("getExternalAppDcimPath: " + PathUtils.getExternalAppDcimPath())
                .appendLine("getExternalAppDocumentsPath: " + PathUtils.getExternalAppDocumentsPath())
                .appendLine("getExternalAppObbPath: " + PathUtils.getExternalAppObbPath())
                .create()
    }

    override fun doBusiness() {}

    override fun onWidgetClick(view: View) {}
}
