package com.blankj.utilcode.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Process;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/04/30
 *     desc  : 缓存相关工具类
 * </pre>
 */
public class CacheUtils {

    private static final String LINE_SEP          = System.getProperty("line.separator");
    private static final int    DEFAULT_MAX_SIZE  = 104857600; // 100Mb
    private static final int    DEFAULT_MAX_COUNT = Integer.MAX_VALUE;

    private static Map<String, CacheUtils> sCacheMap = new HashMap<>();
    private CacheManager mCacheManager;

    /**
     * 获取缓存实例
     * <p>在/data/data/com.xxx.xxx/cache/cacheUtils目录</p>
     * <p>最大缓存100M</p>
     * <p>缓存个数不限</p>
     *
     * @return {@link CacheUtils}
     */
    public static CacheUtils getInstance() {
        return getInstance("cacheUtils", DEFAULT_MAX_SIZE, DEFAULT_MAX_COUNT);
    }

    /**
     * 获取缓存实例
     * <p>在/data/data/com.xxx.xxx/cache/cacheName目录</p>
     * <p>最大缓存100M</p>
     * <p>缓存个数不限</p>
     *
     * @param cacheName 缓存目录名
     * @return {@link CacheUtils}
     */
    public static CacheUtils getInstance(@NonNull String cacheName) {
        return getInstance(cacheName, DEFAULT_MAX_SIZE, DEFAULT_MAX_COUNT);
    }

    /**
     * 获取缓存实例
     * <p>在/data/data/com.xxx.xxx/cache/cacheUtils目录</p>
     *
     * @param maxSize  缓存大小，单位字节
     * @param maxCount 缓存个数
     * @return {@link CacheUtils}
     */
    public static CacheUtils getInstance(long maxSize, int maxCount) {
        return getInstance("cacheUtils", maxSize, maxCount);
    }

    /**
     * 获取缓存实例
     * <p>在/data/data/com.xxx.xxx/cache/cacheName目录</p>
     *
     * @param cacheName 缓存目录名
     * @param maxSize   缓存大小，单位字节
     * @param maxCount  缓存个数
     * @return {@link CacheUtils}
     */
    public static CacheUtils getInstance(@NonNull String cacheName, long maxSize, int maxCount) {
        File file = new File(Utils.getContext().getCacheDir(), cacheName);
        return getInstance(file, maxSize, maxCount);
    }

    /**
     * 获取缓存实例
     * <p>在cacheDir目录</p>
     * <p>最大缓存100M</p>
     * <p>缓存个数不限</p>
     *
     * @param cacheDir 缓存目录
     * @return {@link CacheUtils}
     */
    public static CacheUtils getInstance(@NonNull File cacheDir) {
        return getInstance(cacheDir, DEFAULT_MAX_SIZE, DEFAULT_MAX_COUNT);
    }

    /**
     * 获取缓存实例
     * <p>在cacheDir目录</p>
     *
     * @param cacheDir 缓存目录
     * @param maxSize  缓存大小，单位字节
     * @param maxCount 缓存个数
     * @return {@link CacheUtils}
     */
    public static CacheUtils getInstance(File cacheDir, long maxSize, int maxCount) {
        final String cacheKey = cacheDir.getAbsoluteFile() + "_" + Process.myPid();
        CacheUtils cache = sCacheMap.get(cacheKey);
        if (cache == null) {
            cache = new CacheUtils(cacheDir, maxSize, maxCount);
            sCacheMap.put(cacheKey, cache);
        }
        return cache;
    }

    private CacheUtils(File cacheDir, long maxSize, int maxCount) {
        if (!cacheDir.exists() && !cacheDir.mkdirs()) {
            throw new RuntimeException("can't make dirs in " + cacheDir.getAbsolutePath());
        }
        mCacheManager = new CacheManager(cacheDir, maxSize, maxCount);
    }


    ///////////////////////////////////////////////////////////////////////////
    // byte 读写
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 缓存中写入字节数组
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, byte[] value) {
        put(key, value, -1);
    }

    /**
     * 缓存中写入字节数组
     *
     * @param key      键
     * @param value    值
     * @param saveTime 保存时长，单位：秒
     */
    public void put(String key, byte[] value, int saveTime) {
        if (saveTime >= 0) value = CacheHelper.newByteArrayWithTime(saveTime, value);
        File file = mCacheManager.newFile(key);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            out.write(value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseUtils.closeIO(out);
            mCacheManager.put(file);
        }
    }

    /**
     * 缓存中读取字节数组
     *
     * @param key 键
     * @return 字节数组
     */
    public byte[] getBytes(String key) {
        File file = mCacheManager.getFile(key);
        if (!file.exists()) return null;
        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(file, "r").getChannel();
            int size = (int) fc.size();
            MappedByteBuffer byteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, size).load();
            byte[] data = new byte[size];
            byteBuffer.get(data, 0, size);
            if (!CacheHelper.isDue(data)) {
                return CacheHelper.getDataWithoutDueTime(data);
            } else {
                mCacheManager.remove(key);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtils.closeIO(fc);
        }
        return null;
    }

    ///////////////////////////////////////////////////////////////////////////
    // String 读写
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 缓存中写入String
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, String value) {
        put(key, value, -1);
    }

    /**
     * 缓存中写入String
     *
     * @param key      键
     * @param value    值
     * @param saveTime 保存时长，单位：秒
     */
    public void put(String key, String value, int saveTime) {
        put(key, value.getBytes(), saveTime);
    }

    /**
     * 缓存中读取String
     *
     * @param key 键
     * @return String
     */
    public String getString(String key) {
        byte[] bytes = getBytes(key);
        if (bytes == null) return null;
        return new String(bytes);
    }

    ///////////////////////////////////////////////////////////////////////////
    // JSONObject 读写
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 缓存中写入JSONObject
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, JSONObject value) {
        put(key, value, -1);
    }

    /**
     * 缓存中写入JSONObject
     *
     * @param key      键
     * @param value    值
     * @param saveTime 保存时长，单位：秒
     */
    public void put(String key, JSONObject value, int saveTime) {
        put(key, value.toString(), saveTime);
    }

    /**
     * 缓存中读取JSONObject
     *
     * @param key 键
     * @return JSONObject
     */
    public JSONObject getJSONObject(String key) {
        String json = getString(key);
        try {
            return new JSONObject(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    ///////////////////////////////////////////////////////////////////////////
    // JSONArray 读写
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 缓存中写入JSONArray
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, JSONArray value) {
        put(key, value, -1);
    }

    /**
     * 缓存中写入JSONArray
     *
     * @param key      键
     * @param value    值
     * @param saveTime 保存时长，单位：秒
     */
    public void put(String key, JSONArray value, int saveTime) {
        put(key, value.toString(), saveTime);
    }

    /**
     * 缓存中读取JSONArray
     *
     * @param key 键
     * @return JSONArray
     */
    public JSONArray getJSONArray(String key) {
        String JSONString = getString(key);
        try {
            return new JSONArray(JSONString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Serializable 读写
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 缓存中写入Serializable
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, Serializable value) {
        put(key, value, -1);
    }

    /**
     * 缓存中写入Serializable
     *
     * @param key      键
     * @param value    值
     * @param saveTime 保存时长，单位：秒
     */
    public void put(String key, Serializable value, int saveTime) {
        ByteArrayOutputStream baos;
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos = new ByteArrayOutputStream());
            oos.writeObject(value);
            byte[] data = baos.toByteArray();
            put(key, data, saveTime);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseUtils.closeIO(oos);
        }
    }

    /**
     * 缓存中读取Serializable
     *
     * @param key 键
     * @return Serializable
     */
    public Object getObject(String key) {
        byte[] bytes = getBytes(key);
        if (bytes == null) return null;
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtils.closeIO(ois);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // bitmap 数据 读写
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 缓存中写入bitmap
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, Bitmap value) {
        put(key, value, -1);
    }

    /**
     * 缓存中写入bitmap
     *
     * @param key      键
     * @param value    值
     * @param saveTime 保存时长，单位：秒
     */
    public void put(String key, Bitmap value, int saveTime) {
        put(key, CacheHelper.bitmap2Bytes(value), saveTime);
    }

    /**
     * 缓存中读取bitmap
     *
     * @param key 键
     * @return bitmap
     */
    public Bitmap getBitmap(String key) {
        byte[] bytes = getBytes(key);
        if (bytes == null) return null;
        return CacheHelper.bytes2Bitmap(bytes);
    }

    ///////////////////////////////////////////////////////////////////////////
    // drawable 数据 读写
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 缓存中写入drawable
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, Drawable value) {
        put(key, CacheHelper.drawable2Bytes(value));
    }

    /**
     * 缓存中写入drawable
     *
     * @param key      键
     * @param value    值
     * @param saveTime 保存时长，单位：秒
     */
    public void put(String key, Drawable value, int saveTime) {
        put(key, CacheHelper.drawable2Bytes(value), saveTime);
    }

    /**
     * 缓存中读取drawable
     *
     * @param key 键
     * @return bitmap
     */
    public Drawable getDrawable(String key) {
        byte[] bytes = getBytes(key);
        if (bytes == null) return null;
        return CacheHelper.bytes2Drawable(bytes);
    }

    /**
     * 获取缓存文件
     *
     * @param key 键
     * @return 缓存文件
     */
    public File getCacheFile(String key) {
        File file = mCacheManager.newFile(key);
        if (file.exists()) return file;
        return null;
    }

    /**
     * 移除某个key
     *
     * @param key 键
     * @return 是否移除成功
     */
    public boolean remove(String key) {
        return mCacheManager.remove(key);
    }

    /**
     * 清除所有数据
     */
    public void clear() {
        mCacheManager.clear();
    }

    private class CacheManager {
        private final AtomicLong    cacheSize;
        private final AtomicInteger cacheCount;
        private final long          sizeLimit;
        private final int           countLimit;
        private final Map<File, Long> lastUsageDates = Collections.synchronizedMap(new HashMap<File, Long>());
        private final File cacheDir;

        private CacheManager(File cacheDir, long sizeLimit, int countLimit) {
            this.cacheDir = cacheDir;
            this.sizeLimit = sizeLimit;
            this.countLimit = countLimit;
            cacheSize = new AtomicLong();
            cacheCount = new AtomicInteger();
            calculateCacheSizeAndCacheCount();
        }

        /**
         * 计算 cacheSize和cacheCount
         */
        private void calculateCacheSizeAndCacheCount() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int size = 0;
                    int count = 0;
                    File[] cachedFiles = cacheDir.listFiles();
                    if (cachedFiles != null) {
                        for (File cachedFile : cachedFiles) {
                            size += cachedFile.length();
                            count += 1;
                            lastUsageDates.put(cachedFile,
                                    cachedFile.lastModified());
                        }
                        cacheSize.set(size);
                        cacheCount.set(count);
                    }
                }
            }).start();
        }

        private File newFile(String key) {
            return new File(cacheDir, key.hashCode() + "");
        }

        private File getFile(String key) {
            File file = newFile(key);
            Long currentTime = System.currentTimeMillis();
            file.setLastModified(currentTime);
            lastUsageDates.put(file, currentTime);
            return file;
        }

        private void put(File file) {
            int curCacheCount = cacheCount.get();
            while (curCacheCount + 1 > countLimit) {
                long freedSize = removeOldest();
                cacheSize.addAndGet(-freedSize);
                curCacheCount = cacheCount.addAndGet(-1);
            }
            cacheCount.addAndGet(1);
            long valueSize = file.length();
            long curCacheSize = cacheSize.get();
            while (curCacheSize + valueSize > sizeLimit) {
                long freedSize = removeOldest();
                curCacheSize = cacheSize.addAndGet(-freedSize);
            }
            cacheSize.addAndGet(valueSize);
            Long millis = System.currentTimeMillis();
            file.setLastModified(millis);
            lastUsageDates.put(file, millis);
        }

        private boolean remove(String key) {
            File file = getFile(key);
            if (file.delete()) {
                cacheSize.addAndGet(-file.length());
                cacheCount.addAndGet(-1);
                return true;
            }
            return false;
        }

        private void clear() {
            lastUsageDates.clear();
            cacheSize.set(0);
            cacheCount.set(0);
            File[] files = cacheDir.listFiles();
            if (files == null) return;
            for (File file : files) file.delete();
        }

        /**
         * 移除旧的文件
         *
         * @return 移除的字节数
         */
        private long removeOldest() {
            if (lastUsageDates.isEmpty()) return 0;
            Long oldestUsage = null;
            File oldestFile = null;
            Set<Map.Entry<File, Long>> entries = lastUsageDates.entrySet();
            synchronized (lastUsageDates) {
                for (Map.Entry<File, Long> entry : entries) {
                    if (oldestFile == null) {
                        oldestFile = entry.getKey();
                        oldestUsage = entry.getValue();
                    } else {
                        Long lastValueUsage = entry.getValue();
                        if (lastValueUsage < oldestUsage) {
                            oldestUsage = lastValueUsage;
                            oldestFile = entry.getKey();
                        }
                    }
                }
            }
            long fileSize = oldestFile.length();
            if (oldestFile.delete()) {
                lastUsageDates.remove(oldestFile);
            }
            return fileSize;
        }
    }

    /**
     * 缓存帮助类
     */
    private static class CacheHelper {

        static final int timeInfoLen = 17;

        private static String newStringWithTime(int second, String strInfo) {
            return createDueTime(second) + strInfo;
        }

        private static byte[] newByteArrayWithTime(int second, byte[] data) {
            byte[] time = createDueTime(second).getBytes();
            byte[] content = new byte[time.length + data.length];
            System.arraycopy(time, 0, content, 0, time.length);
            System.arraycopy(data, 0, content, time.length, data.length);
            return content;
        }

        /**
         * 创建过期时间
         *
         * @param second 秒
         * @return _$millis$_
         */
        private static String createDueTime(int second) {
            return String.format(Locale.getDefault(), "_$%013d$_", System.currentTimeMillis() + second * 1000);
        }

        private static boolean isDue(String data) {
            return isDue(data.getBytes());
        }

        private static boolean isDue(byte[] data) {
            long millis = getDueTime(data);
            return millis != -1 && System.currentTimeMillis() > millis;
        }

        private static long getDueTime(byte[] data) {
            if (hasTimeInfo(data)) {
                String millis = new String(copyOfRange(data, 2, 15));
                try {
                    return Long.parseLong(millis);
                } catch (NumberFormatException e) {
                    return -1;
                }
            }
            return -1;
        }

        private static String getDataWithoutDueTime(String data) {
            if (hasTimeInfo(data.getBytes())) {
                data = data.substring(timeInfoLen);
            }
            return data;
        }

        private static byte[] getDataWithoutDueTime(byte[] data) {
            if (hasTimeInfo(data)) {
                return copyOfRange(data, timeInfoLen, data.length);
            }
            return data;
        }

        private static byte[] copyOfRange(byte[] original, int from, int to) {
            int newLength = to - from;
            if (newLength < 0) throw new IllegalArgumentException(from + " > " + to);
            byte[] copy = new byte[newLength];
            System.arraycopy(original, from, copy, 0, Math.min(original.length - from, newLength));
            return copy;
        }

        private static boolean hasTimeInfo(byte[] data) {
            return data != null
                    && data.length >= timeInfoLen
                    && data[0] == '_'
                    && data[1] == '$'
                    && data[15] == '_'
                    && data[16] == '$';
        }


        /**
         * bitmap转byteArr
         *
         * @param bitmap bitmap对象
         * @return 字节数组
         */
        private static byte[] bitmap2Bytes(Bitmap bitmap) {
            if (bitmap == null) return null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            return baos.toByteArray();
        }

        /**
         * byteArr转bitmap
         *
         * @param bytes 字节数组
         * @return bitmap
         */
        private static Bitmap bytes2Bitmap(byte[] bytes) {
            return (bytes == null || bytes.length == 0) ? null : BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }

        /**
         * drawable转bitmap
         *
         * @param drawable drawable对象
         * @return bitmap
         */
        private static Bitmap drawable2Bitmap(Drawable drawable) {
            if (drawable instanceof BitmapDrawable) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                if (bitmapDrawable.getBitmap() != null) {
                    return bitmapDrawable.getBitmap();
                }
            }
            Bitmap bitmap;
            if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
                bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            }
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        }

        /**
         * bitmap转drawable
         *
         * @param bitmap bitmap对象
         * @return drawable
         */
        private static Drawable bitmap2Drawable(Bitmap bitmap) {
            return bitmap == null ? null : new BitmapDrawable(Utils.getContext().getResources(), bitmap);
        }

        /**
         * drawable转byteArr
         *
         * @param drawable drawable对象
         * @return 字节数组
         */
        private static byte[] drawable2Bytes(Drawable drawable) {
            return drawable == null ? null : bitmap2Bytes(drawable2Bitmap(drawable));
        }

        /**
         * byteArr转drawable
         *
         * @param bytes 字节数组
         * @return drawable
         */
        private static Drawable bytes2Drawable(byte[] bytes) {
            return bytes == null ? null : bitmap2Drawable(bytes2Bitmap(bytes));
        }
    }
}
