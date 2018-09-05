package com.blankj.utilcode.util;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/11
 *     desc  : utils about sdcard
 * </pre>
 */
public final class SDCardUtils {

    private SDCardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Return whether sdcard is enabled by environment.
     *
     * @return true : enabled<br>false : disabled
     */
    public static boolean isSDCardEnableByEnvironment() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * Return the path of sdcard by environment.
     *
     * @return the path of sdcard by environment
     */
    public static String getSDCardPathByEnvironment() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return "";
    }

    /**
     * Return whether sdcard is enabled.
     *
     * @return true : enabled<br>false : disabled
     */
    public static boolean isSDCardEnable() {
        return !getSDCardPaths().isEmpty();
    }

    /**
     * Return the paths of sdcard.
     *
     * @param removable True to return the paths of removable sdcard, false otherwise.
     * @return the paths of sdcard
     */
    public static List<String> getSDCardPaths(final boolean removable) {
        List<String> paths = new ArrayList<>();
        StorageManager sm =
                (StorageManager) Utils.getApp().getSystemService(Context.STORAGE_SERVICE);
        try {
            Class<?> storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            //noinspection JavaReflectionMemberAccess
            Method getVolumeList = StorageManager.class.getMethod("getVolumeList");
            //noinspection JavaReflectionMemberAccess
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Object result = getVolumeList.invoke(sm);
            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String path = (String) getPath.invoke(storageVolumeElement);
                boolean res = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (removable == res) {
                    paths.add(path);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return paths;
    }

    /**
     * Return the paths of sdcard.
     *
     * @return the paths of sdcard
     */
    public static List<String> getSDCardPaths() {
        StorageManager storageManager = (StorageManager) Utils.getApp()
                .getSystemService(Context.STORAGE_SERVICE);
        List<String> paths = new ArrayList<>();
        try {
            //noinspection JavaReflectionMemberAccess
            Method getVolumePathsMethod = StorageManager.class.getMethod("getVolumePaths");
            getVolumePathsMethod.setAccessible(true);
            Object invoke = getVolumePathsMethod.invoke(storageManager);
            paths = Arrays.asList((String[]) invoke);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return paths;
    }
}
