package com.blankj.utilcode.util;

import android.os.Build;
import android.os.Environment;

import java.io.File;

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
        return getAbsolutePath(Environment.getRootDirectory());
    }

    /**
     * Return the path of /data.
     *
     * @return the path of /data
     */
    public static String getDataPath() {
        return getAbsolutePath(Environment.getDataDirectory());
    }

    /**
     * Return the path of /cache.
     *
     * @return the path of /cache
     */
    public static String getDownloadCachePath() {
        return getAbsolutePath(Environment.getDownloadCacheDirectory());
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
        return getAbsolutePath(Utils.getApp().getDataDir());
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
        return getAbsolutePath(Utils.getApp().getCodeCacheDir());
    }

    /**
     * Return the path of /data/data/package/cache.
     *
     * @return the path of /data/data/package/cache
     */
    public static String getInternalAppCachePath() {
        return getAbsolutePath(Utils.getApp().getCacheDir());
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
        return getAbsolutePath(Utils.getApp().getDatabasePath(name));
    }

    /**
     * Return the path of /data/data/package/files.
     *
     * @return the path of /data/data/package/files
     */
    public static String getInternalAppFilesPath() {
        return getAbsolutePath(Utils.getApp().getFilesDir());
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
        return getAbsolutePath(Utils.getApp().getNoBackupFilesDir());
    }

    /**
     * Return the path of /storage/emulated/0.
     *
     * @return the path of /storage/emulated/0
     */
    public static String getExternalStoragePath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Environment.getExternalStorageDirectory());
    }

    /**
     * Return the path of /storage/emulated/0/Music.
     *
     * @return the path of /storage/emulated/0/Music
     */
    public static String getExternalMusicPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC));
    }

    /**
     * Return the path of /storage/emulated/0/Podcasts.
     *
     * @return the path of /storage/emulated/0/Podcasts
     */
    public static String getExternalPodcastsPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS));
    }

    /**
     * Return the path of /storage/emulated/0/Ringtones.
     *
     * @return the path of /storage/emulated/0/Ringtones
     */
    public static String getExternalRingtonesPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES));
    }

    /**
     * Return the path of /storage/emulated/0/Alarms.
     *
     * @return the path of /storage/emulated/0/Alarms
     */
    public static String getExternalAlarmsPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS));
    }

    /**
     * Return the path of /storage/emulated/0/Notifications.
     *
     * @return the path of /storage/emulated/0/Notifications
     */
    public static String getExternalNotificationsPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS));
    }

    /**
     * Return the path of /storage/emulated/0/Pictures.
     *
     * @return the path of /storage/emulated/0/Pictures
     */
    public static String getExternalPicturesPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
    }

    /**
     * Return the path of /storage/emulated/0/Movies.
     *
     * @return the path of /storage/emulated/0/Movies
     */
    public static String getExternalMoviesPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES));
    }

    /**
     * Return the path of /storage/emulated/0/Download.
     *
     * @return the path of /storage/emulated/0/Download
     */
    public static String getExternalDownloadsPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
    }

    /**
     * Return the path of /storage/emulated/0/DCIM.
     *
     * @return the path of /storage/emulated/0/DCIM
     */
    public static String getExternalDcimPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM));
    }

    /**
     * Return the path of /storage/emulated/0/Documents.
     *
     * @return the path of /storage/emulated/0/Documents
     */
    public static String getExternalDocumentsPath() {
        if (isExternalStorageDisable()) return "";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return getAbsolutePath(Environment.getExternalStorageDirectory()) + "/Documents";
        }
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS));
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package.
     *
     * @return the path of /storage/emulated/0/Android/data/package
     */
    public static String getExternalAppDataPath() {
        if (isExternalStorageDisable()) return "";
        File externalCacheDir = Utils.getApp().getExternalCacheDir();
        if (externalCacheDir == null) return "";
        return getAbsolutePath(externalCacheDir.getParentFile());
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/cache.
     *
     * @return the path of /storage/emulated/0/Android/data/package/cache
     */
    public static String getExternalAppCachePath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Utils.getApp().getExternalCacheDir());
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files
     */
    public static String getExternalAppFilesPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Utils.getApp().getExternalFilesDir(null));
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Music.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Music
     */
    public static String getExternalAppMusicPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_MUSIC));
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Podcasts.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Podcasts
     */
    public static String getExternalAppPodcastsPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_PODCASTS));
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Ringtones.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Ringtones
     */
    public static String getExternalAppRingtonesPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_RINGTONES));
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Alarms.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Alarms
     */
    public static String getExternalAppAlarmsPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_ALARMS));
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Notifications.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Notifications
     */
    public static String getExternalAppNotificationsPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS));
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Pictures.
     *
     * @return path of /storage/emulated/0/Android/data/package/files/Pictures
     */
    public static String getExternalAppPicturesPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_PICTURES));
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Movies.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Movies
     */
    public static String getExternalAppMoviesPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_MOVIES));
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Download.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Download
     */
    public static String getExternalAppDownloadPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/DCIM.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/DCIM
     */
    public static String getExternalAppDcimPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_DCIM));
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Documents.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Documents
     */
    public static String getExternalAppDocumentsPath() {
        if (isExternalStorageDisable()) return "";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return getAbsolutePath(Utils.getApp().getExternalFilesDir(null)) + "/Documents";
        }
        return getAbsolutePath(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS));
    }

    /**
     * Return the path of /storage/emulated/0/Android/obb/package.
     *
     * @return the path of /storage/emulated/0/Android/obb/package
     */
    public static String getExternalAppObbPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Utils.getApp().getObbDir());
    }

    private static boolean isExternalStorageDisable() {
        return !Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    private static String getAbsolutePath(final File file) {
        if (file == null) return "";
        return file.getAbsolutePath();
    }
}
