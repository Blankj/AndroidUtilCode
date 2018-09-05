//package com.blankj.utilcode.util;
//
//import android.os.Build;
//import android.os.Environment;
//import android.support.annotation.RequiresApi;
//
///**
// * <pre>
// *     author: Blankj
// *     blog  : http://blankj.com
// *     time  : 2018/04/15
// *     desc  : utils about path
// * </pre>
// */
//public class PathUtils {
//
//    private PathUtils() {
//        throw new UnsupportedOperationException("u can't instantiate me...");
//    }
//
//    /**
//     * Return the path of /system.
//     *
//     * @return the path of /system
//     */
//    public static String getRootPath() {
//        return Environment.getRootDirectory().getAbsolutePath();
//    }
//
//    /**
//     * Return the path of /data.
//     *
//     * @return the path of /data
//     */
//    public static String getDataPath() {
//        return Environment.getDataDirectory().getAbsolutePath();
//    }
//
//    /**
//     * Return the path of download/cache.
//     *
//     * @return the path of download/cache
//     */
//    public static String getInternalDownloadCachePath() {
//        return Environment.getDownloadCacheDirectory().getAbsolutePath();
//    }
//
//    /**
//     * Return the path of /data/data/package/cache.
//     *
//     * @return the path of /data/data/package/cache
//     */
//    public static String getInternalAppCachePath() {
//        return Utils.getApp().getCacheDir().getAbsolutePath();
//    }
//
//    /**
//     * Return the path of /data/data/package/files.
//     *
//     * @return the path of /data/data/package/files
//     */
//    public static String getInternalAppFilesPath() {
//        return Utils.getApp().getFilesDir().getAbsolutePath();
//    }
//
//    /**
//     * Return the path of /data/data/package/databases/name.
//     *
//     * @param name The name of database.
//     * @return the path of /data/data/package/databases/name
//     */
//    public static String getInternalAppDbPath(String name) {
//        return Utils.getApp().getDatabasePath(name).getAbsolutePath();
//    }
//
//    /**
//     * Return the path of /storage/emulated/0.
//     *
//     * @return the path of /storage/emulated/0
//     */
//    public static String getExternalStoragePath() {
//        return Environment.getExternalStorageDirectory().getAbsolutePath();
//    }
//
//    /**
//     * Return the path of /storage/emulated/0/Alarms.
//     *
//     * @return the path of /storage/emulated/0/Alarms
//     */
//    public static String getExternalAlarmsPath() {
//        return Environment
//                .getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS)
//                .getAbsolutePath();
//    }
//
//    /**
//     * Return the path of /storage/emulated/0/DCIM.
//     *
//     * @return the path of /storage/emulated/0/DCIM
//     */
//    public static String getExternalDcimPath() {
//        return Environment
//                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
//                .getAbsolutePath();
//    }
//
//    /**
//     * Return the path of /storage/emulated/0/Documents.
//     *
//     * @return the path of /storage/emulated/0/Documents
//     */
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    public static String getExternalDocumentsPath() {
//        return Environment
//                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
//                .getAbsolutePath();
//    }
//
//    /**
//     * Return the path of /storage/emulated/0/Download.
//     *
//     * @return the path of /storage/emulated/0/Download
//     */
//    public static String getExtDownloadsPath() {
//        return Environment
//                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//                .getAbsolutePath();
//    }
//
//    /**
//     * 获取视频目录
//     * <pre>path: /storage/emulated/0/Movies</pre>
//     *
//     * @return 视频目录
//     */
//    public static String getExtMoviesPath() {
//        return Environment
//                .getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)
//                .getAbsolutePath();
//    }
//
//    /**
//     * 获取音乐目录
//     * <pre>path: /storage/emulated/0/Music</pre>
//     *
//     * @return 音乐目录
//     */
//    public static String getExtMusicPath() {
//        return Environment
//                .getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
//                .getAbsolutePath();
//    }
//
//    /**
//     * 获取提示音目录
//     * <pre>path: /storage/emulated/0/Notifications</pre>
//     *
//     * @return 提示音目录
//     */
//    public static String getExtNotificationsPath() {
//        return Environment
//                .getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS)
//                .getAbsolutePath();
//    }
//
//    /**
//     * 获取图片目录
//     * <pre>path: /storage/emulated/0/Pictures</pre>
//     *
//     * @return 图片目录
//     */
//    public static String getExtPicturesPath() {
//        return Environment
//                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
//                .getAbsolutePath();
//    }
//
//    /**
//     * 获取 Podcasts 目录
//     * <pre>path: /storage/emulated/0/Podcasts</pre>
//     *
//     * @return Podcasts 目录
//     */
//    public static String getExtPodcastsPath() {
//        return Environment
//                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS)
//                .getAbsolutePath();
//    }
//
//    /**
//     * 获取铃声目录
//     * <pre>path: /storage/emulated/0/Ringtones</pre>
//     *
//     * @return 下载目录
//     */
//    public static String getExtRingtonesPath() {
//        return Environment
//                .getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES)
//                .getAbsolutePath();
//    }
//
//    /**
//     * 获取此应用在外置储存中的缓存目录
//     * <pre>path: /storage/emulated/0/Android/data/package/cache</pre>
//     *
//     * @return 此应用在外置储存中的缓存目录
//     */
//    public static String getAppExtCachePath() {
//        return Utils.getApp().getExternalCacheDir().getAbsolutePath();
//    }
//
//    /**
//     * 获取此应用在外置储存中的文件目录
//     * <pre>path: /storage/emulated/0/Android/data/package/files</pre>
//     *
//     * @return 此应用在外置储存中的文件目录
//     */
//    public static String getAppExtFilePath() {
//        return Utils.getApp().getExternalFilesDir(null).getAbsolutePath();
//    }
//
//    /**
//     * 获取此应用在外置储存中的闹钟铃声目录
//     * <pre>path: /storage/emulated/0/Android/data/package/files/Alarms</pre>
//     *
//     * @return 此应用在外置储存中的闹钟铃声目录
//     */
//    public static String getAppExtAlarmsPath() {
//        return Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_ALARMS)
//                .getAbsolutePath();
//    }
//
//    /**
//     * 获取此应用在外置储存中的相机目录
//     * <pre>path: /storage/emulated/0/Android/data/package/files/DCIM</pre>
//     *
//     * @return 此应用在外置储存中的相机目录
//     */
//    public static String getAppExtDcimPath() {
//        return Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_DCIM)
//                .getAbsolutePath();
//    }
//
//    /**
//     * 获取此应用在外置储存中的文档目录
//     * <pre>path: /storage/emulated/0/Android/data/package/files/Documents</pre>
//     *
//     * @return 此应用在外置储存中的文档目录
//     */
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    public static String getAppExtDocumentsPath() {
//        return Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
//                .getAbsolutePath();
//    }
//
//    /**
//     * 获取此应用在外置储存中的闹钟目录
//     * <pre>path: /storage/emulated/0/Android/data/package/files/Download</pre>
//     *
//     * @return 此应用在外置储存中的闹钟目录
//     */
//    public static String getAppExtDownloadPath() {
//        return Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
//                .getAbsolutePath();
//    }
//
//    /**
//     * 获取此应用在外置储存中的视频目录
//     * <pre>path: /storage/emulated/0/Android/data/package/files/Movies</pre>
//     *
//     * @return 此应用在外置储存中的视频目录
//     */
//    public static String getAppExtMoviesPath() {
//        return Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_MOVIES)
//                .getAbsolutePath();
//    }
//
//    /**
//     * 获取此应用在外置储存中的音乐目录
//     * <pre>path: /storage/emulated/0/Android/data/package/files/Music</pre>
//     *
//     * @return 此应用在外置储存中的音乐目录
//     */
//    public static String getAppExtMusicPath() {
//        return Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_MUSIC)
//                .getAbsolutePath();
//    }
//
//    /**
//     * 获取此应用在外置储存中的提示音目录
//     * <pre>path: /storage/emulated/0/Android/data/package/files/Notifications</pre>
//     *
//     * @return 此应用在外置储存中的提示音目录
//     */
//    public static String getAppExtNotificationsPath() {
//        return Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS)
//                .getAbsolutePath();
//    }
//
//    /**
//     * 获取此应用在外置储存中的图片目录
//     * <pre>path: /storage/emulated/0/Android/data/package/files/Pictures</pre>
//     *
//     * @return 此应用在外置储存中的图片目录
//     */
//    public static String getAppExtPicturesPath() {
//        return Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//                .getAbsolutePath();
//    }
//
//    /**
//     * 获取此应用在外置储存中的 Podcasts 目录
//     * <pre>path: /storage/emulated/0/Android/data/package/files/Podcasts</pre>
//     *
//     * @return 此应用在外置储存中的 Podcasts 目录
//     */
//    public static String getAppExtPodcastsPath() {
//        return Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_PODCASTS)
//                .getAbsolutePath();
//    }
//
//    /**
//     * 获取此应用在外置储存中的铃声目录
//     * <pre>path: /storage/emulated/0/Android/data/package/files/Ringtones</pre>
//     *
//     * @return 此应用在外置储存中的铃声目录
//     */
//    public static String getAppExtRingtonesPath() {
//        return Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_RINGTONES)
//                .getAbsolutePath();
//    }
//
//    /**
//     * 获取此应用的 Obb 目录
//     * <pre>path: /storage/emulated/0/Android/obb/package</pre>
//     * <pre>一般用来存放游戏数据包</pre>
//     *
//     * @return 此应用的 Obb 目录
//     */
//    public static String getObbPath() {
//        return Utils.getApp().getObbDir().getAbsolutePath();
//    }
//}
