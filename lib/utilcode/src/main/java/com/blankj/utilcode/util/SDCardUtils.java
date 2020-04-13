package com.blankj.utilcode.util;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.text.format.Formatter;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
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
     * @return {@code true}: enabled<br>{@code false}: disabled
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
        if (isSDCardEnableByEnvironment()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return "";
    }

    /**
     * Return the information of sdcard.
     *
     * @return the information of sdcard
     */
    public static List<SDCardInfo> getSDCardInfo() {
        List<SDCardInfo> paths = new ArrayList<>();
        StorageManager sm = (StorageManager) Utils.getApp().getSystemService(Context.STORAGE_SERVICE);
        if (sm == null) return paths;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            List<StorageVolume> storageVolumes = sm.getStorageVolumes();
            try {
                //noinspection JavaReflectionMemberAccess
                Method getPathMethod = StorageVolume.class.getMethod("getPath");
                for (StorageVolume storageVolume : storageVolumes) {
                    boolean isRemovable = storageVolume.isRemovable();
                    String state = storageVolume.getState();
                    String path = (String) getPathMethod.invoke(storageVolume);
                    paths.add(new SDCardInfo(path, state, isRemovable));
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Class<?> storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
                //noinspection JavaReflectionMemberAccess
                Method getPathMethod = storageVolumeClazz.getMethod("getPath");
                Method isRemovableMethod = storageVolumeClazz.getMethod("isRemovable");
                //noinspection JavaReflectionMemberAccess
                Method getVolumeStateMethod = StorageManager.class.getMethod("getVolumeState", String.class);
                //noinspection JavaReflectionMemberAccess
                Method getVolumeListMethod = StorageManager.class.getMethod("getVolumeList");
                Object result = getVolumeListMethod.invoke(sm);
                final int length = Array.getLength(result);
                for (int i = 0; i < length; i++) {
                    Object storageVolumeElement = Array.get(result, i);
                    String path = (String) getPathMethod.invoke(storageVolumeElement);
                    boolean isRemovable = (Boolean) isRemovableMethod.invoke(storageVolumeElement);
                    String state = (String) getVolumeStateMethod.invoke(sm, path);
                    paths.add(new SDCardInfo(path, state, isRemovable));
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
        }
        return paths;
    }

    /**
     * Return the ptah of mounted sdcard.
     *
     * @return the ptah of mounted sdcard.
     */
    public static List<String> getMountedSDCardPath() {
        List<String> path = new ArrayList<>();
        List<SDCardInfo> sdCardInfo = getSDCardInfo();
        if (sdCardInfo == null || sdCardInfo.isEmpty()) return path;
        for (SDCardInfo cardInfo : sdCardInfo) {
            String state = cardInfo.state;
            if (state == null) continue;
            if ("mounted".equals(state.toLowerCase())) {
                path.add(cardInfo.path);
            }
        }
        return path;
    }


    /**
     * Return the total size of external storage
     *
     * @return the total size of external storage
     */
    public static long getExternalTotalSize() {
        return UtilsBridge.getFsTotalSize(getSDCardPathByEnvironment());
    }

    /**
     * Return the available size of external storage.
     *
     * @return the available size of external storage
     */
    public static long getExternalAvailableSize() {
        return UtilsBridge.getFsAvailableSize(getSDCardPathByEnvironment());
    }

    /**
     * Return the total size of internal storage
     *
     * @return the total size of internal storage
     */
    public static long getInternalTotalSize() {
        return UtilsBridge.getFsTotalSize(Environment.getDataDirectory().getAbsolutePath());
    }

    /**
     * Return the available size of internal storage.
     *
     * @return the available size of internal storage
     */
    public static long getInternalAvailableSize() {
        return UtilsBridge.getFsAvailableSize(Environment.getDataDirectory().getAbsolutePath());
    }

    public static class SDCardInfo {

        private String  path;
        private String  state;
        private boolean isRemovable;
        private long    totalSize;
        private long    availableSize;

        SDCardInfo(String path, String state, boolean isRemovable) {
            this.path = path;
            this.state = state;
            this.isRemovable = isRemovable;
            this.totalSize = UtilsBridge.getFsTotalSize(path);
            this.availableSize = UtilsBridge.getFsAvailableSize(path);
        }

        public String getPath() {
            return path;
        }

        public String getState() {
            return state;
        }

        public boolean isRemovable() {
            return isRemovable;
        }

        public long getTotalSize() {
            return totalSize;
        }

        public long getAvailableSize() {
            return availableSize;
        }

        @Override
        public String toString() {
            return "SDCardInfo {" +
                    "path = " + path +
                    ", state = " + state +
                    ", isRemovable = " + isRemovable +
                    ", totalSize = " + Formatter.formatFileSize(Utils.getApp(), totalSize) +
                    ", availableSize = " + Formatter.formatFileSize(Utils.getApp(), availableSize) +
                    '}';
        }
    }
}
