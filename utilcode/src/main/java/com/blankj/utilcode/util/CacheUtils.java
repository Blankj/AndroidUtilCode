package com.blankj.utilcode.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Process;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
     * @param key      保存的key
     * @param value    保存的数据
     * @param saveTime 保存的时间，单位：秒
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
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
            MappedByteBuffer byteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size()).load();
            byte[] data = new byte[(int) fc.size()];
            if (byteBuffer.remaining() > 0) {
                byteBuffer.get(data, 0, byteBuffer.remaining());
            }
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
        put(key, value.getBytes(), -1);
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
        return new String(getBytes(key));
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
        put(key, value.toString());
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
     * @param key   保存的key
     * @param value 保存的JSONArray数据
     */
    public void put(String key, JSONArray value) {
        put(key, value.toString());
    }

    /**
     * 缓存中写入JSONArray
     *
     * @param key      保存的key
     * @param value    保存的JSONArray数据
     * @param saveTime 保存的时间，单位：秒
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
            JSONArray obj = new JSONArray(JSONString);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // 序列化 数据 读写
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 保存 Serializable数据 到 缓存中
     *
     * @param key   保存的key
     * @param value 保存的value
     */
    public void put(String key, Serializable value) {
        put(key, value, -1);
    }

    /**
     * 保存 Serializable数据到 缓存中
     *
     * @param key      保存的key
     * @param value    保存的value
     * @param saveTime 保存的时间，单位：秒
     */
    public void put(String key, Serializable value, int saveTime) {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(value);
            byte[] data = baos.toByteArray();
            if (saveTime != -1) {
                put(key, data, saveTime);
            } else {
                put(key, data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * 读取 Serializable数据
     *
     * @param key
     * @return Serializable 数据
     */
    public Object getAsObject(String key) {
        byte[] data = getBytes(key);
        if (data != null) {
            ByteArrayInputStream bais = null;
            ObjectInputStream ois = null;
            try {
                bais = new ByteArrayInputStream(data);
                ois = new ObjectInputStream(bais);
                Object reObject = ois.readObject();
                return reObject;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                try {
                    if (bais != null)
                        bais.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (ois != null)
                        ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;

    }

    ///////////////////////////////////////////////////////////////////////////
    // bitmap 数据 读写
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 保存 bitmap 到 缓存中
     *
     * @param key   保存的key
     * @param value 保存的bitmap数据
     */
    public void put(String key, Bitmap value) {
        put(key, CacheHelper.Bitmap2Bytes(value));
    }

    /**
     * 保存 bitmap 到 缓存中
     *
     * @param key      保存的key
     * @param value    保存的 bitmap 数据
     * @param saveTime 保存的时间，单位：秒
     */
    public void put(String key, Bitmap value, int saveTime) {
        put(key, CacheHelper.Bitmap2Bytes(value), saveTime);
    }

    /**
     * 读取 bitmap 数据
     *
     * @param key
     * @return bitmap 数据
     */
    public Bitmap getAsBitmap(String key) {
        if (getBytes(key) == null) {
            return null;
        }
        return CacheHelper.Bytes2Bimap(getBytes(key));
    }

    ///////////////////////////////////////////////////////////////////////////
    // drawable 数据 读写
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 保存 drawable 到 缓存中
     *
     * @param key   保存的key
     * @param value 保存的drawable数据
     */
    public void put(String key, Drawable value) {
        put(key, CacheHelper.drawable2Bitmap(value));
    }

    /**
     * 保存 drawable 到 缓存中
     *
     * @param key      保存的key
     * @param value    保存的 drawable 数据
     * @param saveTime 保存的时间，单位：秒
     */
    public void put(String key, Drawable value, int saveTime) {
        put(key, CacheHelper.drawable2Bitmap(value), saveTime);
    }

    /**
     * 读取 Drawable 数据
     *
     * @param key
     * @return Drawable 数据
     */
    public Drawable getAsDrawable(String key) {
        if (getBytes(key) == null) {
            return null;
        }
        return CacheHelper.bitmap2Drawable(CacheHelper.Bytes2Bimap(getBytes(key)));
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
            for (File f : files) {
                f.delete();
            }
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


        /*
         * Bitmap → byte[]
         */
        private static byte[] Bitmap2Bytes(Bitmap bm) {
            if (bm == null) {
                return null;
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
            return baos.toByteArray();
        }

        /*
         * byte[] → Bitmap
         */
        private static Bitmap Bytes2Bimap(byte[] b) {
            if (b.length == 0) {
                return null;
            }
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        }

        /*
         * Drawable → Bitmap
         */
        private static Bitmap drawable2Bitmap(Drawable drawable) {
            if (drawable == null) {
                return null;
            }
            // 取 drawable 的长宽
            int w = drawable.getIntrinsicWidth();
            int h = drawable.getIntrinsicHeight();
            // 取 drawable 的颜色格式
            Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE
                    ? Bitmap.Config.ARGB_8888
                    : Bitmap.Config.RGB_565;
            // 建立对应 bitmap
            Bitmap bitmap = Bitmap.createBitmap(w, h, config);
            // 建立对应 bitmap 的画布
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, w, h);
            // 把 drawable 内容画到画布中
            drawable.draw(canvas);
            return bitmap;
        }

        /*
         * Bitmap → Drawable
         */
        @SuppressWarnings("deprecation")
        private static Drawable bitmap2Drawable(Bitmap bm) {
            if (bm == null) {
                return null;
            }
            return new BitmapDrawable(bm);
        }
    }
}
