package com.blankj.utilcode.util;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;

/**
 * Created by xiboke on 2017/10/16.
 * 路径相关工具类
 */

public class PathUtils {

    /**
     * 获取 Android 内置储存的根目录
     * (/storage/emulated/0)
     * @return 内置储存根目录
     */
    public static String getStoragePath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 获取 Android 系统根目录
     * (/system)
     * @return 系统根目录
     */
    public static String getRootPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }

    /**
     * 获取 data 目录
     * (/data)
     * @return data 目录
     */
    public static String getDataPath() {
        return Environment.getDataDirectory().getAbsolutePath();
    }

    /**
     * 获取缓存目录
     * (data/cache)
     * @return 缓存目录
     */
    public static String getCachePath() {
        return Environment.getDownloadCacheDirectory().getAbsolutePath();
    }

    /**
     * 获取闹钟铃声目录
     * (/storage/emulated/0/Alarms)
     * @return 闹钟铃声目录
     */
    public static String getAlarmsPath() {
        return Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS)
                .getAbsolutePath();
    }

    /**
     * 获取相机拍摄的照片和视频的目录
     * (/storage/emulated/0/DCIM)
     * @return 照片和视频目录
     */
    public static String getDcimPath() {
        return Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                .getAbsolutePath();

    }

    /**
     * 获取文档目录
     * (/storage/emulated/0/Documents)
     * @return 文档目录
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getDocumentsPath() {
        return Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                .getAbsolutePath();
    }

    /**
     * 获取下载目录
     * (/storage/emulated/0/Download)
     * @return 下载目录
     */
    public static String getDownloadPath() {
        return Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .getAbsolutePath();
    }

    /**
     * 获取视频目录
     * (/storage/emulated/0/Movies)
     * @return 视频目录
     */
    public static String getMoviesPath() {
        return Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)
                .getAbsolutePath();
    }

    /**
     * 获取音乐目录
     * (/storage/emulated/0/Music)
     * @return 音乐目录
     */
    public static String getMusicPath() {
        return Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
                .getAbsolutePath();
    }

    /**
     * 获取提示音目录
     * (/storage/emulated/0/Notifications)
     * @return 提示音目录
     */
    public static String getNotificationsPath() {
        return Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS)
                .getAbsolutePath();
    }

    /**
     * 获取图片目录
     * (/storage/emulated/0/Pictures)
     * @return 图片目录
     */
    public static String getPicturesPath() {
        return Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .getAbsolutePath();
    }

    /**
     * 获取 Podcasts 目录
     * (/storage/emulated/0/Podcasts)
     * @return Podcasts 目录
     */
    public static String getPodcastsPath() {
        return Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS)
                .getAbsolutePath();
    }

    /**
     * 获取铃声目录
     * (/storage/emulated/0/Ringtones)
     * @return 下载目录
     */
    public static String getRingtonesPath() {
        return Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES)
                .getAbsolutePath();
    }

    /**
     * 获取此应用的缓存目录
     * (/data/data/包名/cache)
     * @param context context
     * @return 此应用的缓存目录
     */
    public static String getAppCachePath(Context context) {
        return context.getCacheDir().getAbsolutePath();
    }

    /**
     * 获取此应用的文件目录
     * (/data/data/包名/files)
     * @param context context
     * @return 此应用的文件目录
     */
    public static String getAppFilesPath(Context context) {
        return context.getFilesDir().getAbsolutePath();
    }

    /**
     * 获取此应用的 Obb 目录（一般用来存放游戏数据包）
     * (/storage/emulated/0/Android/obb/包名)
     * @param context context
     * @return 此应用的 Obb 目录
     */
    public static String getObbPath(Context context) {
        return context.getObbDir().getAbsolutePath();
    }

    /**
     * 获取此应用在内置储存中的缓存目录
     * (/storage/emulated/0/Android/data/包名/cache)
     * @param context context
     * @return 此应用在内置储存中的缓存目录
     */
    public static String getExtCachePath(Context context) {
        return context.getExternalCacheDir().getAbsolutePath();
    }

    /**
     * 获取此应用在内置储存中的文件目录
     * (/storage/emulated/0/Android/data/包名/files)
     * @param context context
     * @return 此应用在内置储存中的文件目录
     */
    public static String getExtFilePath(Context context) {
        return context.getExternalFilesDir(null).getAbsolutePath();
    }

    /**
     * 获取此应用在内置储存中的闹钟铃声目录
     * (/storage/emulated/0/Android/data/包名/files/Alarms)
     * @param context context
     * @return 此应用在内置储存中的闹钟铃声目录
     */
    public static String getExtAlarmsPath(Context context) {
        return context.getExternalFilesDir(Environment.DIRECTORY_ALARMS)
                .getAbsolutePath();
    }

    /**
     * 获取此应用在内置储存中的相机目录
     * (/storage/emulated/0/Android/data/包名/files/DCIM)
     * @param context context
     * @return 此应用在内置储存中的相机目录
     */
    public static String getExtDcimPath(Context context) {
        return context.getExternalFilesDir(Environment.DIRECTORY_DCIM)
                .getAbsolutePath();
    }

    /**
     * 获取此应用在内置储存中的文档目录
     * (/storage/emulated/0/Android/data/包名/files/Documents)
     * @param context context
     * @return 此应用在内置储存中的文档目录
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getExtDocumentsPath(Context context) {
        return context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
                .getAbsolutePath();
    }

    /**
     * 获取此应用在内置储存中的闹钟目录
     * (/storage/emulated/0/Android/data/包名/files/Download)
     * @param context context
     * @return 此应用在内置储存中的闹钟目录
     */
    public static String getExtDownloadPath(Context context) {
        return context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                .getAbsolutePath();
    }

    /**
     * 获取此应用在内置储存中的视频目录
     * (/storage/emulated/0/Android/data/包名/files/Movies)
     * @param context context
     * @return 此应用在内置储存中的视频目录
     */
    public static String getExtMoviesPath(Context context) {
        return context.getExternalFilesDir(Environment.DIRECTORY_MOVIES)
                .getAbsolutePath();
    }

    /**
     * 获取此应用在内置储存中的音乐目录
     * (/storage/emulated/0/Android/data/包名/files/Music)
     * @param context context
     * @return 此应用在内置储存中的音乐目录
     */
    public static String getExtMusicPath(Context context) {
        return context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
                .getAbsolutePath();
    }

    /**
     * 获取此应用在内置储存中的提示音目录
     * (/storage/emulated/0/Android/data/包名/files/Notifications)
     * @param context context
     * @return 此应用在内置储存中的提示音目录
     */
    public static String getExtNotificationsPath(Context context) {
        return context.getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS)
                .getAbsolutePath();
    }

    /**
     * 获取此应用在内置储存中的图片目录
     * (/storage/emulated/0/Android/data/包名/files/Pictures)
     * @param context context
     * @return 此应用在内置储存中的图片目录
     */
    public static String getExtPicturesPath(Context context) {
        return context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                .getAbsolutePath();
    }

    /**
     * 获取此应用在内置储存中的 Podcasts 目录
     * (/storage/emulated/0/Android/data/包名/files/Podcasts)
     * @param context context
     * @return 此应用在内置储存中的 Podcasts 目录
     */
    public static String getExtPodcastsPath(Context context) {
        return context.getExternalFilesDir(Environment.DIRECTORY_PODCASTS)
                .getAbsolutePath();
    }

    /**
     * 获取此应用在内置储存中的铃声目录
     * (/storage/emulated/0/Android/data/包名/files/Ringtones)
     * @param context context
     * @return 此应用在内置储存中的铃声目录
     */
    public static String getExtRingtonesPath(Context context) {
        return context.getExternalFilesDir(Environment.DIRECTORY_RINGTONES)
                .getAbsolutePath();
    }

    /**
     * 获取此应用的数据库文件目录
     * (/data/data/包名/databases/name)
     * @param context context
     * @param name 数据库文件名
     * @return 数据库文件目录
     */
    public static String getDataBasePath(Context context, String name) {
        return context.getDatabasePath(name).getAbsolutePath();
    }



}
