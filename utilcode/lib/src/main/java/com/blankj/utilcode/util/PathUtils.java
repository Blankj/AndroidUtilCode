package com.blankj.utilcode.util;

import android.os.Build;
import android.os.Environment;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/04/15
 *     desc  : utils about path
 * </pre>
 */
public class PathUtils {

    private PathUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Return the path of /system.
     *
     * @return the path of /system
     */
    public static String getRootPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }

    /**
     * Return the path of /data.
     *
     * @return the path of /data
     */
    public static String getDataPath() {
        return Environment.getDataDirectory().getAbsolutePath();
    }

    /**
     * Return the path of /cache.
     *
     * @return the path of /cache
     */
    public static String getDownloadCachePath() {
        return Environment.getDownloadCacheDirectory().getAbsolutePath();
    }

    /**
     * Return the path of /data/data/package.
     *
     * @return the path of /data/data/package
     */
    public static String getInternalAppDataPath() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            return Utils.getApp().getApplicationInfo().dataDir;
        }
        return Utils.getApp().getDataDir().getAbsolutePath();
    }

    /**
     * Return the path of /data/data/package/code_cache.
     *
     * @return the path of /data/data/package/code_cache
     */
    public static String getInternalAppCodeCacheDir() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return Utils.getApp().getApplicationInfo().dataDir + "/code_cache";
        }
        return Utils.getApp().getCodeCacheDir().getAbsolutePath();
    }

    /**
     * Return the path of /data/data/package/cache.
     *
     * @return the path of /data/data/package/cache
     */
    public static String getInternalAppCachePath() {
        return Utils.getApp().getCacheDir().getAbsolutePath();
    }

    /**
     * Return the path of /data/data/package/databases.
     *
     * @return the path of /data/data/package/databases
     */
    public static String getInternalAppDbsPath() {
        return Utils.getApp().getApplicationInfo().dataDir + "/databases";
    }

    /**
     * Return the path of /data/data/package/databases/name.
     *
     * @param name The name of database.
     * @return the path of /data/data/package/databases/name
     */
    public static String getInternalAppDbPath(String name) {
        return Utils.getApp().getDatabasePath(name).getAbsolutePath();
    }

    /**
     * Return the path of /data/data/package/files.
     *
     * @return the path of /data/data/package/files
     */
    public static String getInternalAppFilesPath() {
        return Utils.getApp().getFilesDir().getAbsolutePath();
    }

    /**
     * Return the path of /data/data/package/shared_prefs.
     *
     * @return the path of /data/data/package/shared_prefs
     */
    public static String getInternalAppSpPath() {
        return Utils.getApp().getApplicationInfo().dataDir + "shared_prefs";
    }

    /**
     * Return the path of /data/data/package/no_backup.
     *
     * @return the path of /data/data/package/no_backup
     */
    public static String getInternalAppNoBackupFilesPath() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return Utils.getApp().getApplicationInfo().dataDir + "no_backup";
        }
        return Utils.getApp().getNoBackupFilesDir().getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0.
     *
     * @return the path of /storage/emulated/0
     */
    public static String getExternalStoragePath() {
        if (isExternalStorageDisable()) return "";
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Music.
     *
     * @return the path of /storage/emulated/0/Music
     */
    public static String getExternalMusicPath() {
        if (isExternalStorageDisable()) return "";
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Podcasts.
     *
     * @return the path of /storage/emulated/0/Podcasts
     */
    public static String getExternalPodcastsPath() {
        if (isExternalStorageDisable()) return "";
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Ringtones.
     *
     * @return the path of /storage/emulated/0/Ringtones
     */
    public static String getExternalRingtonesPath() {
        if (isExternalStorageDisable()) return "";
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Alarms.
     *
     * @return the path of /storage/emulated/0/Alarms
     */
    public static String getExternalAlarmsPath() {
        if (isExternalStorageDisable()) return "";
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Notifications.
     *
     * @return the path of /storage/emulated/0/Notifications
     */
    public static String getExternalNotificationsPath() {
        if (isExternalStorageDisable()) return "";
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Pictures.
     *
     * @return the path of /storage/emulated/0/Pictures
     */
    public static String getExternalPicturesPath() {
        if (isExternalStorageDisable()) return "";
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Movies.
     *
     * @return the path of /storage/emulated/0/Movies
     */
    public static String getExternalMoviesPath() {
        if (isExternalStorageDisable()) return "";
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Download.
     *
     * @return the path of /storage/emulated/0/Download
     */
    public static String getExternalDownloadsPath() {
        if (isExternalStorageDisable()) return "";
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/DCIM.
     *
     * @return the path of /storage/emulated/0/DCIM
     */
    public static String getExternalDcimPath() {
        if (isExternalStorageDisable()) return "";
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Documents.
     *
     * @return the path of /storage/emulated/0/Documents
     */
    public static String getExternalDocumentsPath() {
        if (isExternalStorageDisable()) return "";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/Documents";
        }
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package.
     *
     * @return the path of /storage/emulated/0/Android/data/package
     */
    public static String getExternalAppDataPath() {
        if (isExternalStorageDisable()) return "";
        //noinspection ConstantConditions
        return Utils.getApp().getExternalCacheDir().getParentFile().getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/cache.
     *
     * @return the path of /storage/emulated/0/Android/data/package/cache
     */
    public static String getExternalAppCachePath() {
        if (isExternalStorageDisable()) return "";
        //noinspection ConstantConditions
        return Utils.getApp().getExternalCacheDir().getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files
     */
    public static String getExternalAppFilesPath() {
        if (isExternalStorageDisable()) return "";
        //noinspection ConstantConditions
        return Utils.getApp().getExternalFilesDir(null).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Music.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Music
     */
    public static String getExternalAppMusicPath() {
        if (isExternalStorageDisable()) return "";
        //noinspection ConstantConditions
        return Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_MUSIC).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Podcasts.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Podcasts
     */
    public static String getExternalAppPodcastsPath() {
        if (isExternalStorageDisable()) return "";
        //noinspection ConstantConditions
        return Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_PODCASTS).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Ringtones.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Ringtones
     */
    public static String getExternalAppRingtonesPath() {
        if (isExternalStorageDisable()) return "";
        //noinspection ConstantConditions
        return Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_RINGTONES).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Alarms.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Alarms
     */
    public static String getExternalAppAlarmsPath() {
        if (isExternalStorageDisable()) return "";
        //noinspection ConstantConditions
        return Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_ALARMS).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Notifications.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Notifications
     */
    public static String getExternalAppNotificationsPath() {
        if (isExternalStorageDisable()) return "";
        //noinspection ConstantConditions
        return Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Pictures.
     *
     * @return path of /storage/emulated/0/Android/data/package/files/Pictures
     */
    public static String getExternalAppPicturesPath() {
        if (isExternalStorageDisable()) return "";
        //noinspection ConstantConditions
        return Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Movies.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Movies
     */
    public static String getExternalAppMoviesPath() {
        if (isExternalStorageDisable()) return "";
        //noinspection ConstantConditions
        return Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_MOVIES).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Download.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Download
     */
    public static String getExternalAppDownloadPath() {
        if (isExternalStorageDisable()) return "";
        //noinspection ConstantConditions
        return Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/DCIM.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/DCIM
     */
    public static String getExternalAppDcimPath() {
        if (isExternalStorageDisable()) return "";
        //noinspection ConstantConditions
        return Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_DCIM).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Documents.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Documents
     */
    public static String getExternalAppDocumentsPath() {
        if (isExternalStorageDisable()) return "";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            //noinspection ConstantConditions
            return Utils.getApp().getExternalFilesDir(null).getAbsolutePath() + "/Documents";
        }
        //noinspection ConstantConditions
        return Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Android/obb/package.
     *
     * @return the path of /storage/emulated/0/Android/obb/package
     */
    public static String getExternalAppObbPath() {
        if (isExternalStorageDisable()) return "";
        return Utils.getApp().getObbDir().getAbsolutePath();
    }

    private static boolean isExternalStorageDisable() {
        return !Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }
}
