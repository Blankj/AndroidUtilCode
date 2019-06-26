package com.blankj.utilcode.util;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2019/01/04
 *     desc  : utils about double cache
 * </pre>
 */
public final class CacheDoubleStaticUtils {

    private static CacheDoubleUtils sDefaultCacheDoubleUtils;

    /**
     * Set the default instance of {@link CacheDoubleUtils}.
     *
     * @param cacheDoubleUtils The default instance of {@link CacheDoubleUtils}.
     */
    public static void setDefaultCacheDoubleUtils(final CacheDoubleUtils cacheDoubleUtils) {
        sDefaultCacheDoubleUtils = cacheDoubleUtils;
    }

    /**
     * Put bytes in cache.
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public static void put(@NonNull final String key, final byte[] value) {
        put(key, value, getDefaultCacheDoubleUtils());
    }

    /**
     * Put bytes in cache.
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public static void put(@NonNull final String key, byte[] value, final int saveTime) {
        put(key, value, saveTime, getDefaultCacheDoubleUtils());
    }

    /**
     * Return the bytes in cache.
     *
     * @param key The key of cache.
     * @return the bytes if cache exists or null otherwise
     */
    public static byte[] getBytes(@NonNull final String key) {
        return getBytes(key, getDefaultCacheDoubleUtils());
    }

    /**
     * Return the bytes in cache.
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the bytes if cache exists or defaultValue otherwise
     */
    public static byte[] getBytes(@NonNull final String key, final byte[] defaultValue) {
        return getBytes(key, defaultValue, getDefaultCacheDoubleUtils());
    }

    ///////////////////////////////////////////////////////////////////////////
    // about String
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put string value in cache.
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public static void put(@NonNull final String key, final String value) {
        put(key, value, getDefaultCacheDoubleUtils());
    }

    /**
     * Put string value in cache.
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public static void put(@NonNull final String key, final String value, final int saveTime) {
        put(key, value, saveTime, getDefaultCacheDoubleUtils());
    }

    /**
     * Return the string value in cache.
     *
     * @param key The key of cache.
     * @return the string value if cache exists or null otherwise
     */
    public static String getString(@NonNull final String key) {
        return getString(key, getDefaultCacheDoubleUtils());
    }

    /**
     * Return the string value in cache.
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the string value if cache exists or defaultValue otherwise
     */
    public static String getString(@NonNull final String key, final String defaultValue) {
        return getString(key, defaultValue, getDefaultCacheDoubleUtils());
    }

    ///////////////////////////////////////////////////////////////////////////
    // about JSONObject
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put JSONObject in cache.
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public static void put(@NonNull final String key, final JSONObject value) {
        put(key, value, getDefaultCacheDoubleUtils());
    }

    /**
     * Put JSONObject in cache.
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public static void put(@NonNull final String key,
                           final JSONObject value,
                           final int saveTime) {
        put(key, value, saveTime, getDefaultCacheDoubleUtils());
    }

    /**
     * Return the JSONObject in cache.
     *
     * @param key The key of cache.
     * @return the JSONObject if cache exists or null otherwise
     */
    public static JSONObject getJSONObject(@NonNull final String key) {
        return getJSONObject(key, getDefaultCacheDoubleUtils());
    }

    /**
     * Return the JSONObject in cache.
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the JSONObject if cache exists or defaultValue otherwise
     */
    public static JSONObject getJSONObject(@NonNull final String key, final JSONObject defaultValue) {
        return getJSONObject(key, defaultValue, getDefaultCacheDoubleUtils());
    }


    ///////////////////////////////////////////////////////////////////////////
    // about JSONArray
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put JSONArray in cache.
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public static void put(@NonNull final String key, final JSONArray value) {
        put(key, value, getDefaultCacheDoubleUtils());
    }

    /**
     * Put JSONArray in cache.
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public static void put(@NonNull final String key, final JSONArray value, final int saveTime) {
        put(key, value, saveTime, getDefaultCacheDoubleUtils());
    }

    /**
     * Return the JSONArray in cache.
     *
     * @param key The key of cache.
     * @return the JSONArray if cache exists or null otherwise
     */
    public static JSONArray getJSONArray(@NonNull final String key) {
        return getJSONArray(key, getDefaultCacheDoubleUtils());
    }

    /**
     * Return the JSONArray in cache.
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the JSONArray if cache exists or defaultValue otherwise
     */
    public static JSONArray getJSONArray(@NonNull final String key, final JSONArray defaultValue) {
        return getJSONArray(key, defaultValue, getDefaultCacheDoubleUtils());
    }

    ///////////////////////////////////////////////////////////////////////////
    // Bitmap cache
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put bitmap in cache.
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public static void put(@NonNull final String key, final Bitmap value) {
        put(key, value, getDefaultCacheDoubleUtils());
    }

    /**
     * Put bitmap in cache.
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public static void put(@NonNull final String key, final Bitmap value, final int saveTime) {
        put(key, value, saveTime, getDefaultCacheDoubleUtils());
    }

    /**
     * Return the bitmap in cache.
     *
     * @param key The key of cache.
     * @return the bitmap if cache exists or null otherwise
     */
    public static Bitmap getBitmap(@NonNull final String key) {
        return getBitmap(key, getDefaultCacheDoubleUtils());
    }

    /**
     * Return the bitmap in cache.
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the bitmap if cache exists or defaultValue otherwise
     */
    public static Bitmap getBitmap(@NonNull final String key, final Bitmap defaultValue) {
        return getBitmap(key, defaultValue, getDefaultCacheDoubleUtils());
    }

    ///////////////////////////////////////////////////////////////////////////
    // about Drawable
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put drawable in cache.
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public static void put(@NonNull final String key, final Drawable value) {
        put(key, value, getDefaultCacheDoubleUtils());
    }

    /**
     * Put drawable in cache.
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public static void put(@NonNull final String key, final Drawable value, final int saveTime) {
        put(key, value, saveTime, getDefaultCacheDoubleUtils());
    }

    /**
     * Return the drawable in cache.
     *
     * @param key The key of cache.
     * @return the drawable if cache exists or null otherwise
     */
    public static Drawable getDrawable(@NonNull final String key) {
        return getDrawable(key, getDefaultCacheDoubleUtils());
    }

    /**
     * Return the drawable in cache.
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the drawable if cache exists or defaultValue otherwise
     */
    public static Drawable getDrawable(@NonNull final String key, final Drawable defaultValue) {
        return getDrawable(key, defaultValue, getDefaultCacheDoubleUtils());
    }

    ///////////////////////////////////////////////////////////////////////////
    // about Parcelable
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put parcelable in cache.
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public static void put(@NonNull final String key, final Parcelable value) {
        put(key, value, getDefaultCacheDoubleUtils());
    }

    /**
     * Put parcelable in cache.
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public static void put(@NonNull final String key, final Parcelable value, final int saveTime) {
        put(key, value, saveTime, getDefaultCacheDoubleUtils());
    }

    /**
     * Return the parcelable in cache.
     *
     * @param key     The key of cache.
     * @param creator The creator.
     * @param <T>     The value type.
     * @return the parcelable if cache exists or null otherwise
     */
    public static <T> T getParcelable(@NonNull final String key,
                                      @NonNull final Parcelable.Creator<T> creator) {
        return getParcelable(key, creator, getDefaultCacheDoubleUtils());
    }

    /**
     * Return the parcelable in cache.
     *
     * @param key          The key of cache.
     * @param creator      The creator.
     * @param defaultValue The default value if the cache doesn't exist.
     * @param <T>          The value type.
     * @return the parcelable if cache exists or defaultValue otherwise
     */
    public static <T> T getParcelable(@NonNull final String key,
                                      @NonNull final Parcelable.Creator<T> creator,
                                      final T defaultValue) {
        return getParcelable(key, creator, defaultValue, getDefaultCacheDoubleUtils());
    }

    ///////////////////////////////////////////////////////////////////////////
    // about Serializable
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put serializable in cache.
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public static void put(@NonNull final String key, final Serializable value) {
        put(key, value, getDefaultCacheDoubleUtils());
    }

    /**
     * Put serializable in cache.
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public static void put(@NonNull final String key, final Serializable value, final int saveTime) {
        put(key, value, saveTime, getDefaultCacheDoubleUtils());
    }

    /**
     * Return the serializable in cache.
     *
     * @param key The key of cache.
     * @return the bitmap if cache exists or null otherwise
     */
    public static Object getSerializable(@NonNull final String key) {
        return getSerializable(key, getDefaultCacheDoubleUtils());
    }

    /**
     * Return the serializable in cache.
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the bitmap if cache exists or defaultValue otherwise
     */
    public static Object getSerializable(@NonNull final String key, final Object defaultValue) {
        return getSerializable(key, defaultValue, getDefaultCacheDoubleUtils());
    }

    /**
     * Return the size of cache in disk.
     *
     * @return the size of cache in disk
     */
    public static long getCacheDiskSize() {
        return getCacheDiskSize(getDefaultCacheDoubleUtils());
    }

    /**
     * Return the count of cache in disk.
     *
     * @return the count of cache in disk
     */
    public static int getCacheDiskCount() {
        return getCacheDiskCount(getDefaultCacheDoubleUtils());
    }

    /**
     * Return the count of cache in memory.
     *
     * @return the count of cache in memory.
     */
    public static int getCacheMemoryCount() {
        return getCacheMemoryCount(getDefaultCacheDoubleUtils());
    }

    /**
     * Remove the cache by key.
     *
     * @param key The key of cache.
     */
    public static void remove(@NonNull String key) {
        remove(key, getDefaultCacheDoubleUtils());
    }

    /**
     * Clear all of the cache.
     */
    public static void clear() {
        clear(getDefaultCacheDoubleUtils());
    }

    ///////////////////////////////////////////////////////////////////////////
    // dividing line
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put bytes in cache.
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     */
    public static void put(@NonNull final String key,
                           final byte[] value,
                           @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.put(key, value);
    }

    /**
     * Put bytes in cache.
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param saveTime         The save time of cache, in seconds.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     */
    public static void put(@NonNull final String key,
                           final byte[] value,
                           final int saveTime,
                           @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.put(key, value, saveTime);
    }

    /**
     * Return the bytes in cache.
     *
     * @param key              The key of cache.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @return the bytes if cache exists or null otherwise
     */
    public static byte[] getBytes(@NonNull final String key, @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getBytes(key);
    }

    /**
     * Return the bytes in cache.
     *
     * @param key              The key of cache.
     * @param defaultValue     The default value if the cache doesn't exist.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @return the bytes if cache exists or defaultValue otherwise
     */
    public static byte[] getBytes(@NonNull final String key,
                                  final byte[] defaultValue,
                                  @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getBytes(key, defaultValue);
    }

    ///////////////////////////////////////////////////////////////////////////
    // about String
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put string value in cache.
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     */
    public static void put(@NonNull final String key,
                           final String value,
                           @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.put(key, value);
    }

    /**
     * Put string value in cache.
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param saveTime         The save time of cache, in seconds.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     */
    public static void put(@NonNull final String key,
                           final String value,
                           final int saveTime,
                           @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.put(key, value, saveTime);
    }

    /**
     * Return the string value in cache.
     *
     * @param key              The key of cache.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @return the string value if cache exists or null otherwise
     */
    public static String getString(@NonNull final String key, @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getString(key);
    }

    /**
     * Return the string value in cache.
     *
     * @param key              The key of cache.
     * @param defaultValue     The default value if the cache doesn't exist.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @return the string value if cache exists or defaultValue otherwise
     */
    public static String getString(@NonNull final String key,
                                   final String defaultValue,
                                   @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getString(key, defaultValue);
    }

    ///////////////////////////////////////////////////////////////////////////
    // about JSONObject
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put JSONObject in cache.
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     */
    public static void put(@NonNull final String key,
                           final JSONObject value,
                           @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.put(key, value);
    }

    /**
     * Put JSONObject in cache.
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param saveTime         The save time of cache, in seconds.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     */
    public static void put(@NonNull final String key,
                           final JSONObject value,
                           final int saveTime,
                           @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.put(key, value, saveTime);
    }

    /**
     * Return the JSONObject in cache.
     *
     * @param key              The key of cache.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @return the JSONObject if cache exists or null otherwise
     */
    public static JSONObject getJSONObject(@NonNull final String key,
                                           @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getJSONObject(key);
    }

    /**
     * Return the JSONObject in cache.
     *
     * @param key              The key of cache.
     * @param defaultValue     The default value if the cache doesn't exist.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @return the JSONObject if cache exists or defaultValue otherwise
     */
    public static JSONObject getJSONObject(@NonNull final String key,
                                           final JSONObject defaultValue,
                                           @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getJSONObject(key, defaultValue);
    }


    ///////////////////////////////////////////////////////////////////////////
    // about JSONArray
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put JSONArray in cache.
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     */
    public static void put(@NonNull final String key,
                           final JSONArray value,
                           @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.put(key, value);
    }

    /**
     * Put JSONArray in cache.
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param saveTime         The save time of cache, in seconds.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     */
    public static void put(@NonNull final String key,
                           final JSONArray value,
                           final int saveTime,
                           @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.put(key, value, saveTime);
    }

    /**
     * Return the JSONArray in cache.
     *
     * @param key              The key of cache.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @return the JSONArray if cache exists or null otherwise
     */
    public static JSONArray getJSONArray(@NonNull final String key, @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getJSONArray(key);
    }

    /**
     * Return the JSONArray in cache.
     *
     * @param key              The key of cache.
     * @param defaultValue     The default value if the cache doesn't exist.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @return the JSONArray if cache exists or defaultValue otherwise
     */
    public static JSONArray getJSONArray(@NonNull final String key,
                                         final JSONArray defaultValue,
                                         @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getJSONArray(key, defaultValue);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Bitmap cache
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put bitmap in cache.
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     */
    public static void put(@NonNull final String key,
                           final Bitmap value,
                           @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.put(key, value);
    }

    /**
     * Put bitmap in cache.
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param saveTime         The save time of cache, in seconds.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     */
    public static void put(@NonNull final String key,
                           final Bitmap value,
                           final int saveTime,
                           @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.put(key, value, saveTime);
    }

    /**
     * Return the bitmap in cache.
     *
     * @param key              The key of cache.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @return the bitmap if cache exists or null otherwise
     */
    public static Bitmap getBitmap(@NonNull final String key, @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getBitmap(key);
    }

    /**
     * Return the bitmap in cache.
     *
     * @param key              The key of cache.
     * @param defaultValue     The default value if the cache doesn't exist.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @return the bitmap if cache exists or defaultValue otherwise
     */
    public static Bitmap getBitmap(@NonNull final String key,
                                   final Bitmap defaultValue,
                                   @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getBitmap(key, defaultValue);
    }

    ///////////////////////////////////////////////////////////////////////////
    // about Drawable
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put drawable in cache.
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     */
    public static void put(@NonNull final String key,
                           final Drawable value,
                           @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.put(key, value);
    }

    /**
     * Put drawable in cache.
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param saveTime         The save time of cache, in seconds.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     */
    public static void put(@NonNull final String key,
                           final Drawable value,
                           final int saveTime,
                           @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.put(key, value, saveTime);
    }

    /**
     * Return the drawable in cache.
     *
     * @param key              The key of cache.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @return the drawable if cache exists or null otherwise
     */
    public static Drawable getDrawable(@NonNull final String key, @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getDrawable(key);
    }

    /**
     * Return the drawable in cache.
     *
     * @param key              The key of cache.
     * @param defaultValue     The default value if the cache doesn't exist.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @return the drawable if cache exists or defaultValue otherwise
     */
    public static Drawable getDrawable(@NonNull final String key,
                                       final Drawable defaultValue,
                                       @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getDrawable(key, defaultValue);
    }

    ///////////////////////////////////////////////////////////////////////////
    // about Parcelable
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put parcelable in cache.
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     */
    public static void put(@NonNull final String key,
                           final Parcelable value,
                           @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.put(key, value);
    }

    /**
     * Put parcelable in cache.
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param saveTime         The save time of cache, in seconds.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     */
    public static void put(@NonNull final String key,
                           final Parcelable value,
                           final int saveTime,
                           @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.put(key, value, saveTime);
    }

    /**
     * Return the parcelable in cache.
     *
     * @param key              The key of cache.
     * @param creator          The creator.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @param <T>              The value type.
     * @return the parcelable if cache exists or null otherwise
     */
    public static <T> T getParcelable(@NonNull final String key,
                                      @NonNull final Parcelable.Creator<T> creator,
                                      @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getParcelable(key, creator);
    }

    /**
     * Return the parcelable in cache.
     *
     * @param key              The key of cache.
     * @param creator          The creator.
     * @param defaultValue     The default value if the cache doesn't exist.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @param <T>              The value type.
     * @return the parcelable if cache exists or defaultValue otherwise
     */
    public static <T> T getParcelable(@NonNull final String key,
                                      @NonNull final Parcelable.Creator<T> creator,
                                      final T defaultValue,
                                      @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getParcelable(key, creator, defaultValue);
    }

    ///////////////////////////////////////////////////////////////////////////
    // about Serializable
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put serializable in cache.
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     */
    public static void put(@NonNull final String key,
                           final Serializable value,
                           @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.put(key, value);
    }

    /**
     * Put serializable in cache.
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param saveTime         The save time of cache, in seconds.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     */
    public static void put(@NonNull final String key,
                           final Serializable value,
                           final int saveTime,
                           @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.put(key, value, saveTime);
    }

    /**
     * Return the serializable in cache.
     *
     * @param key              The key of cache.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @return the bitmap if cache exists or null otherwise
     */
    public static Object getSerializable(@NonNull final String key, @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getSerializable(key);
    }

    /**
     * Return the serializable in cache.
     *
     * @param key              The key of cache.
     * @param defaultValue     The default value if the cache doesn't exist.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @return the bitmap if cache exists or defaultValue otherwise
     */
    public static Object getSerializable(@NonNull final String key,
                                         final Object defaultValue,
                                         @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getSerializable(key, defaultValue);
    }

    /**
     * Return the size of cache in disk.
     *
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @return the size of cache in disk
     */
    public static long getCacheDiskSize(@NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getCacheDiskSize();
    }

    /**
     * Return the count of cache in disk.
     *
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @return the count of cache in disk
     */
    public static int getCacheDiskCount(@NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getCacheDiskCount();
    }

    /**
     * Return the count of cache in memory.
     *
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @return the count of cache in memory.
     */
    public static int getCacheMemoryCount(@NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getCacheMemoryCount();
    }

    /**
     * Remove the cache by key.
     *
     * @param key              The key of cache.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     */
    public static void remove(@NonNull String key, @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.remove(key);
    }

    /**
     * Clear all of the cache.
     *
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     */
    public static void clear(@NonNull final CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.clear();
    }

    private static CacheDoubleUtils getDefaultCacheDoubleUtils() {
        return sDefaultCacheDoubleUtils != null ? sDefaultCacheDoubleUtils : CacheDoubleUtils.getInstance();
    }
}
