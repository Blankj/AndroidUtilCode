package com.blankj.androidutilcode.feature.core.path;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseBackActivity;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.SpanUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/13
 *     desc  : demo about PathUtils
 * </pre>
 */
public class PathActivity extends BaseBackActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, PathActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_path;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        getToolBar().setTitle(getString(R.string.demo_path));

        TextView tvAboutMetaData = findViewById(R.id.tv_about_path);
        tvAboutMetaData.setText(new SpanUtils()
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
                .append("")
                .create());
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }
}
