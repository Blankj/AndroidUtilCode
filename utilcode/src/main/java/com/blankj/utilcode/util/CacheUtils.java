package com.blankj.utilcode.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
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
import java.nio.ByteBuffer;
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

    private static final int DEFAULT_MAX_SIZE  = 104857600; // 100Mb
    private static final int DEFAULT_MAX_COUNT = Integer.MAX_VALUE;

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
        return getInstance("", DEFAULT_MAX_SIZE, DEFAULT_MAX_COUNT);
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
    public static CacheUtils getInstance(String cacheName) {
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
        return getInstance("", maxSize, maxCount);
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
    public static CacheUtils getInstance(String cacheName, long maxSize, int maxCount) {
        if (isSpace(cacheName)) cacheName = "cacheUtils";
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
    public static CacheUtils getInstance(@NonNull File cacheDir, long maxSize, int maxCount) {
        final String cacheKey = cacheDir.getAbsoluteFile() + "_" + Process.myPid();
        CacheUtils cache = sCacheMap.get(cacheKey);
        if (cache == null) {
            cache = new CacheUtils(cacheDir, maxSize, maxCount);
            sCacheMap.put(cacheKey, cache);
        }
        return cache;
    }

    private CacheUtils(@NonNull File cacheDir, long maxSize, int maxCount) {
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
    public void put(@NonNull String key, @NonNull byte[] value) {
        put(key, value, -1);
    }

    /**
     * 缓存中写入字节数组
     *
     * @param key      键
     * @param value    值
     * @param saveTime 保存时长，单位：秒
     */
    public void put(@NonNull String key, @NonNull byte[] value, int saveTime) {
        if (value.length <= 0) return;
        if (saveTime >= 0) value = CacheHelper.newByteArrayWithTime(saveTime, value);
        File file = mCacheManager.getFileByKey(key);
        CacheHelper.writeFileFromBytes(file, value);
        mCacheManager.put(file);

    }

    /**
     * 缓存中读取字节数组
     *
     * @param key 键
     * @return 字节数组
     */
    public byte[] getBytes(@NonNull String key) {
        final File file = mCacheManager.getFileIfExists(key);
        if (file == null) return null;
        byte[] data = CacheHelper.readFile2Bytes(file);
        if (CacheHelper.isDue(data)) {
            mCacheManager.removeByKey(key);
            return null;
        }
        mCacheManager.updateModify(file);
        return CacheHelper.getDataWithoutDueTime(data);
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
    public void put(@NonNull String key, @NonNull String value) {
        put(key, value, -1);
    }

    /**
     * 缓存中写入String
     *
     * @param key      键
     * @param value    值
     * @param saveTime 保存时长，单位：秒
     */
    public void put(@NonNull String key, @NonNull String value, int saveTime) {
        put(key, CacheHelper.string2Bytes(value), saveTime);
    }

    /**
     * 缓存中读取String
     *
     * @param key 键
     * @return String
     */
    public String getString(@NonNull String key) {
        return CacheHelper.bytes2String(getBytes(key));
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
    public void put(@NonNull String key, @NonNull JSONObject value) {
        put(key, value, -1);
    }

    /**
     * 缓存中写入JSONObject
     *
     * @param key      键
     * @param value    值
     * @param saveTime 保存时长，单位：秒
     */
    public void put(@NonNull String key, @NonNull JSONObject value, int saveTime) {
        put(key, CacheHelper.jsonObject2Bytes(value), saveTime);
    }

    /**
     * 缓存中读取JSONObject
     *
     * @param key 键
     * @return JSONObject
     */
    public JSONObject getJSONObject(@NonNull String key) {
        return CacheHelper.bytes2JSONObject(getBytes(key));
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
    public void put(@NonNull String key, @NonNull JSONArray value) {
        put(key, value, -1);
    }

    /**
     * 缓存中写入JSONArray
     *
     * @param key      键
     * @param value    值
     * @param saveTime 保存时长，单位：秒
     */
    public void put(@NonNull String key, @NonNull JSONArray value, int saveTime) {
        put(key, CacheHelper.jsonArray2Bytes(value), saveTime);
    }

    /**
     * 缓存中读取JSONArray
     *
     * @param key 键
     * @return JSONArray
     */
    public JSONArray getJSONArray(@NonNull String key) {
        return CacheHelper.bytes2JSONArray(getBytes(key));
    }


    ///////////////////////////////////////////////////////////////////////////
    // bitmap 读写
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 缓存中写入bitmap
     *
     * @param key   键
     * @param value 值
     */
    public void put(@NonNull String key, @NonNull Bitmap value) {
        put(key, value, -1);
    }

    /**
     * 缓存中写入bitmap
     *
     * @param key      键
     * @param value    值
     * @param saveTime 保存时长，单位：秒
     */
    public void put(@NonNull String key, @NonNull Bitmap value, int saveTime) {
        put(key, CacheHelper.bitmap2Bytes(value), saveTime);
    }

    /**
     * 缓存中读取bitmap
     *
     * @param key 键
     * @return bitmap
     */
    public Bitmap getBitmap(@NonNull String key) {
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
    public void put(@NonNull String key, @NonNull Drawable value) {
        put(key, CacheHelper.drawable2Bytes(value));
    }

    /**
     * 缓存中写入drawable
     *
     * @param key      键
     * @param value    值
     * @param saveTime 保存时长，单位：秒
     */
    public void put(@NonNull String key, @NonNull Drawable value, int saveTime) {
        put(key, CacheHelper.drawable2Bytes(value), saveTime);
    }

    /**
     * 缓存中读取drawable
     *
     * @param key 键
     * @return bitmap
     */
    public Drawable getDrawable(@NonNull String key) {
        byte[] bytes = getBytes(key);
        if (bytes == null) return null;
        return CacheHelper.bytes2Drawable(bytes);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Parcelable 读写
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 缓存中写入Parcelable
     *
     * @param key   键
     * @param value 值
     */
    public void put(@NonNull String key, @NonNull Parcelable value) {
        put(key, value, -1);
    }

    /**
     * 缓存中写入Parcelable
     *
     * @param key      键
     * @param value    值
     * @param saveTime 保存时长，单位：秒
     */
    public void put(@NonNull String key, @NonNull Parcelable value, int saveTime) {
        put(key, CacheHelper.parcelable2Bytes(value), saveTime);
    }

    /**
     * 缓存中读取Parcelable
     *
     * @param key     键
     * @param creator 建造器
     * @return T
     */
    public <T> T getParcelable(@NonNull String key, @NonNull Parcelable.Creator<T> creator) {
        return CacheHelper.bytes2Parcelable(getBytes(key), creator);
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
    public void put(@NonNull String key, @NonNull Serializable value) {
        put(key, value, -1);
    }

    /**
     * 缓存中写入Serializable
     *
     * @param key      键
     * @param value    值
     * @param saveTime 保存时长，单位：秒
     */
    public void put(@NonNull String key, @NonNull Serializable value, int saveTime) {
        put(key, CacheHelper.serializable2Bytes(value), saveTime);
    }

    /**
     * 缓存中读取Serializable
     *
     * @param key 键
     * @return Serializable
     */
    public Object getOSerializable(@NonNull String key) {
        return CacheHelper.bytes2Object(getBytes(key));
    }

    /**
     * 获取缓存文件
     *
     * @param key 键
     * @return 缓存文件
     */
    public File getCacheFile(@NonNull String key) {
        return mCacheManager.getFileIfExists(key);
    }

    /**
     * 移除某个key
     *
     * @param key 键
     * @return 是否移除成功
     */
    public boolean remove(@NonNull String key) {
        return mCacheManager.removeByKey(key);
    }

    /**
     * 清除所有
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
                            lastUsageDates.put(cachedFile, cachedFile.lastModified());
                        }
                        cacheSize.set(size);
                        cacheCount.set(count);
                    }
                }
            }).start();
        }

        private File getFileByKey(String key) {
            return new File(cacheDir, String.valueOf(key.hashCode()));
        }

        private File getFileIfExists(String key) {
            File file = new File(cacheDir, String.valueOf(key.hashCode()));
            if (!file.exists()) return null;
            return file;
        }

        private void put(File file) {
            cacheCount.addAndGet(1);
            cacheSize.addAndGet(file.length());
            while (cacheCount.get() > countLimit || cacheSize.get() > sizeLimit) {
                cacheSize.addAndGet(-removeOldest());
                cacheCount.addAndGet(-1);
            }
            updateModify(file);
        }

        private void updateModify(File file) {
            Long millis = System.currentTimeMillis();
            file.setLastModified(millis);
            lastUsageDates.put(file, millis);
        }

        private boolean removeByKey(String key) {
            File file = getFileIfExists(key);
            if (file == null) return true;
            if (!file.delete()) return false;
            cacheSize.addAndGet(-file.length());
            cacheCount.addAndGet(-1);
            lastUsageDates.remove(file);
            return true;
        }

        private void clear() {
            File[] files = cacheDir.listFiles();
            if (files == null) return;
            for (File file : files) {
                if (!file.delete()) continue;
                cacheSize.addAndGet(file.length());
                cacheCount.addAndGet(-1);
                lastUsageDates.remove(file);
            }
            lastUsageDates.clear();
            cacheSize.set(0);
            cacheCount.set(0);
        }

        /**
         * 移除旧的文件
         *
         * @return 移除的字节数
         */
        private long removeOldest() {
            if (lastUsageDates.isEmpty()) return 0;
            Long oldestUsage = Long.MAX_VALUE;
            File oldestFile = null;
            Set<Map.Entry<File, Long>> entries = lastUsageDates.entrySet();
            synchronized (lastUsageDates) {
                for (Map.Entry<File, Long> entry : entries) {
                    Long lastValueUsage = entry.getValue();
                    if (lastValueUsage < oldestUsage) {
                        oldestUsage = lastValueUsage;
                        oldestFile = entry.getKey();
                    }
                }
            }
            if (oldestFile == null) return 0;
            long fileSize = oldestFile.length();
            if (oldestFile.delete()) {
                lastUsageDates.remove(oldestFile);
            }
            return fileSize;
        }
    }

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
                    && data[15] == '$'
                    && data[16] == '_';
        }

        private static void writeFileFromBytes(File file, byte[] bytes) {
            FileChannel fc = null;
            try {
                fc = new FileOutputStream(file, false).getChannel();
                fc.write(ByteBuffer.wrap(bytes));
                fc.force(true);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                CloseUtils.closeIO(fc);
            }
        }

        private static byte[] readFile2Bytes(File file) {
            FileChannel fc = null;
            try {
                fc = new RandomAccessFile(file, "r").getChannel();
                int size = (int) fc.size();
                MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, size).load();
                byte[] data = new byte[size];
                mbb.get(data, 0, size);
                return data;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                CloseUtils.closeIO(fc);
            }
        }

        private static byte[] string2Bytes(String string) {
            if (string == null) return null;
            return string.getBytes();
        }

        private static String bytes2String(byte[] bytes) {
            if (bytes == null) return null;
            return new String(bytes);
        }

        private static byte[] jsonObject2Bytes(JSONObject jsonObject) {
            if (jsonObject == null) return null;
            return jsonObject.toString().getBytes();
        }

        private static JSONObject bytes2JSONObject(byte[] bytes) {
            if (bytes == null) return null;
            try {
                return new JSONObject(new String(bytes));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private static byte[] jsonArray2Bytes(JSONArray jsonArray) {
            if (jsonArray == null) return null;
            return jsonArray.toString().getBytes();
        }

        private static JSONArray bytes2JSONArray(byte[] bytes) {
            if (bytes == null) return null;
            try {
                return new JSONArray(new String(bytes));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private static byte[] parcelable2Bytes(Parcelable parcelable) {
            if (parcelable == null) return null;
            Parcel parcel = Parcel.obtain();
            parcelable.writeToParcel(parcel, 0);
            byte[] bytes = parcel.marshall();
            parcel.recycle();
            return bytes;
        }

        private static <T> T bytes2Parcelable(byte[] bytes, Parcelable.Creator<T> creator) {
            if (bytes == null) return null;
            Parcel parcel = Parcel.obtain();
            parcel.unmarshall(bytes, 0, bytes.length);
            parcel.setDataPosition(0);
            T result = creator.createFromParcel(parcel);
            parcel.recycle();
            return result;
        }

        private static byte[] serializable2Bytes(Serializable serializable) {
            if (serializable == null) return null;
            ByteArrayOutputStream baos;
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(baos = new ByteArrayOutputStream());
                oos.writeObject(serializable);
                return baos.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                CloseUtils.closeIO(oos);
            }
        }

        private static Object bytes2Object(byte[] bytes) {
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

        private static byte[] bitmap2Bytes(Bitmap bitmap) {
            if (bitmap == null) return null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            return baos.toByteArray();
        }

        private static Bitmap bytes2Bitmap(byte[] bytes) {
            return (bytes == null || bytes.length == 0) ? null : BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }

        private static byte[] drawable2Bytes(Drawable drawable) {
            return drawable == null ? null : bitmap2Bytes(drawable2Bitmap(drawable));
        }

        private static Drawable bytes2Drawable(byte[] bytes) {
            return bytes == null ? null : bitmap2Drawable(bytes2Bitmap(bytes));
        }

        private static Bitmap drawable2Bitmap(Drawable drawable) {
            if (drawable == null) return null;
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

        private static Drawable bitmap2Drawable(Bitmap bitmap) {
            return bitmap == null ? null : new BitmapDrawable(Utils.getContext().getResources(), bitmap);
        }
    }

    private static boolean isSpace(String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
