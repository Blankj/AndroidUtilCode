package com.blankj.utilcode.util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.Serializable;

import static com.blankj.utilcode.util.TestConfig.FILE_SEP;
import static com.blankj.utilcode.util.TestConfig.PATH_CACHE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/06/13
 *     desc  : test CacheDoubleStaticUtils
 * </pre>
 */
public class CacheDoubleStaticUtilsTest extends BaseTest {

    private static final String           CACHE_PATH         = PATH_CACHE + "double" + FILE_SEP;
    private static final File             CACHE_FILE         = new File(CACHE_PATH);
    private static final byte[]           BYTES              = "CacheDoubleUtils".getBytes();
    private static final String           STRING             = "CacheDoubleUtils";
    private static final JSONObject       JSON_OBJECT        = new JSONObject();
    private static final JSONArray        JSON_ARRAY         = new JSONArray();
    private static final ParcelableTest   PARCELABLE_TEST    = new ParcelableTest("Blankj", "CacheDoubleUtils");
    private static final SerializableTest SERIALIZABLE_TEST  = new SerializableTest("Blankj", "CacheDoubleUtils");
    private static final Bitmap           BITMAP             = Bitmap.createBitmap(100, 100, Bitmap.Config.RGB_565);
    private static final Drawable         DRAWABLE           = new BitmapDrawable(Utils.getApp().getResources(), BITMAP);
    private static final CacheMemoryUtils CACHE_MEMORY_UTILS = CacheMemoryUtils.getInstance();
    private static final CacheDiskUtils   CACHE_DISK_UTILS   = CacheDiskUtils.getInstance(CACHE_FILE);
    private static final CacheDoubleUtils CACHE_DOUBLE_UTILS = CacheDoubleUtils.getInstance(CACHE_MEMORY_UTILS, CACHE_DISK_UTILS);

    static {
        try {
            JSON_OBJECT.put("class", "CacheDoubleUtils");
            JSON_OBJECT.put("author", "Blankj");
            JSON_ARRAY.put(0, JSON_OBJECT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() {
        CacheDoubleStaticUtils.put("bytes", BYTES, CACHE_DOUBLE_UTILS);
        CacheDoubleStaticUtils.put("string", STRING, CACHE_DOUBLE_UTILS);
        CacheDoubleStaticUtils.put("jsonObject", JSON_OBJECT, CACHE_DOUBLE_UTILS);
        CacheDoubleStaticUtils.put("jsonArray", JSON_ARRAY, CACHE_DOUBLE_UTILS);
        CacheDoubleStaticUtils.put("bitmap", BITMAP, CACHE_DOUBLE_UTILS);
        CacheDoubleStaticUtils.put("drawable", DRAWABLE, CACHE_DOUBLE_UTILS);
        CacheDoubleStaticUtils.put("parcelable", PARCELABLE_TEST, CACHE_DOUBLE_UTILS);
        CacheDoubleStaticUtils.put("serializable", SERIALIZABLE_TEST, CACHE_DOUBLE_UTILS);
    }

    @Test
    public void getBytes() {
        assertEquals(STRING, new String(CacheDoubleStaticUtils.getBytes("bytes", CACHE_DOUBLE_UTILS)));
        CACHE_MEMORY_UTILS.remove("bytes");
        assertEquals(STRING, new String(CacheDoubleStaticUtils.getBytes("bytes", CACHE_DOUBLE_UTILS)));
        CACHE_DISK_UTILS.remove("bytes");
        assertNull(CacheDoubleStaticUtils.getBytes("bytes"));
    }

    @Test
    public void getString() {
        assertEquals(STRING, CACHE_DOUBLE_UTILS.getString("string"));
        CACHE_MEMORY_UTILS.remove("string");
        assertEquals(STRING, CACHE_DOUBLE_UTILS.getString("string"));
        CACHE_DISK_UTILS.remove("string");
        assertNull(CACHE_DOUBLE_UTILS.getString("string"));
    }

    @Test
    public void getJSONObject() {
        assertEquals(JSON_OBJECT.toString(), CACHE_DOUBLE_UTILS.getJSONObject("jsonObject").toString());
        CACHE_MEMORY_UTILS.remove("jsonObject");
        assertEquals(JSON_OBJECT.toString(), CACHE_DOUBLE_UTILS.getJSONObject("jsonObject").toString());
        CACHE_DISK_UTILS.remove("jsonObject");
        assertNull(CACHE_DOUBLE_UTILS.getString("jsonObject"));
    }

    @Test
    public void getJSONArray() {
        assertEquals(JSON_ARRAY.toString(), CACHE_DOUBLE_UTILS.getJSONArray("jsonArray").toString());
        CACHE_MEMORY_UTILS.remove("jsonArray");
        assertEquals(JSON_ARRAY.toString(), CACHE_DOUBLE_UTILS.getJSONArray("jsonArray").toString());
        CACHE_DISK_UTILS.remove("jsonArray");
        assertNull(CACHE_DOUBLE_UTILS.getString("jsonArray"));
    }

    @Test
    public void getBitmap() {
        String bitmapString = "Bitmap (100 x 100) compressed as PNG with quality 100";
        assertEquals(BITMAP, CACHE_DOUBLE_UTILS.getBitmap("bitmap"));
        CACHE_MEMORY_UTILS.remove("bitmap");
        assertEquals(bitmapString, CACHE_DOUBLE_UTILS.getString("bitmap"));
        CACHE_DISK_UTILS.remove("bitmap");
        assertNull(CACHE_DOUBLE_UTILS.getString("bitmap"));
    }

    @Test
    public void getDrawable() {
        String bitmapString = "Bitmap (100 x 100) compressed as PNG with quality 100";
        assertEquals(DRAWABLE, CACHE_DOUBLE_UTILS.getDrawable("drawable"));
        CACHE_MEMORY_UTILS.remove("drawable");
        assertEquals(bitmapString, CACHE_DOUBLE_UTILS.getString("drawable"));
        CACHE_DISK_UTILS.remove("drawable");
        assertNull(CACHE_DOUBLE_UTILS.getString("drawable"));
    }

    @Test
    public void getParcel() {
        assertEquals(PARCELABLE_TEST, CACHE_DOUBLE_UTILS.getParcelable("parcelable", ParcelableTest.CREATOR));
        CACHE_MEMORY_UTILS.remove("parcelable");
        assertEquals(PARCELABLE_TEST, CACHE_DOUBLE_UTILS.getParcelable("parcelable", ParcelableTest.CREATOR));
        CACHE_DISK_UTILS.remove("parcelable");
        assertNull(CACHE_DOUBLE_UTILS.getString("parcelable"));
    }

    @Test
    public void getSerializable() {
        assertEquals(SERIALIZABLE_TEST, CACHE_DOUBLE_UTILS.getSerializable("serializable"));
        CACHE_MEMORY_UTILS.remove("serializable");
        assertEquals(SERIALIZABLE_TEST, CACHE_DOUBLE_UTILS.getSerializable("serializable"));
        CACHE_DISK_UTILS.remove("serializable");
        assertNull(CACHE_DOUBLE_UTILS.getString("serializable"));
    }

    @Test
    public void getCacheDiskSize() {
        assertEquals(FileUtils.getDirLength(CACHE_FILE), CACHE_DOUBLE_UTILS.getCacheDiskSize());
    }

    @Test
    public void getCacheDiskCount() {
        assertEquals(8, CACHE_DOUBLE_UTILS.getCacheDiskCount());
    }

    @Test
    public void getCacheMemoryCount() {
        assertEquals(8, CACHE_DOUBLE_UTILS.getCacheMemoryCount());
    }

    @Test
    public void remove() {
        assertNotNull(CACHE_DOUBLE_UTILS.getString("string"));
        CACHE_DOUBLE_UTILS.remove("string");
        assertNull(CACHE_DOUBLE_UTILS.getString("string"));
    }

    @Test
    public void clear() {
        assertNotNull(CACHE_DOUBLE_UTILS.getBytes("bytes"));
        assertNotNull(CACHE_DOUBLE_UTILS.getString("string"));
        assertNotNull(CACHE_DOUBLE_UTILS.getJSONObject("jsonObject"));
        assertNotNull(CACHE_DOUBLE_UTILS.getJSONArray("jsonArray"));
        assertNotNull(CACHE_DOUBLE_UTILS.getBitmap("bitmap"));
        assertNotNull(CACHE_DOUBLE_UTILS.getDrawable("drawable"));
        assertNotNull(CACHE_DOUBLE_UTILS.getParcelable("parcelable", ParcelableTest.CREATOR));
        assertNotNull(CACHE_DOUBLE_UTILS.getSerializable("serializable"));
        CACHE_DOUBLE_UTILS.clear();
        assertNull(CACHE_DOUBLE_UTILS.getBytes("bytes"));
        assertNull(CACHE_DOUBLE_UTILS.getString("string"));
        assertNull(CACHE_DOUBLE_UTILS.getJSONObject("jsonObject"));
        assertNull(CACHE_DOUBLE_UTILS.getJSONArray("jsonArray"));
        assertNull(CACHE_DOUBLE_UTILS.getBitmap("bitmap"));
        assertNull(CACHE_DOUBLE_UTILS.getDrawable("drawable"));
        assertNull(CACHE_DOUBLE_UTILS.getParcelable("parcelable", ParcelableTest.CREATOR));
        assertNull(CACHE_DOUBLE_UTILS.getSerializable("serializable"));
        assertEquals(0, CACHE_DOUBLE_UTILS.getCacheDiskSize());
        assertEquals(0, CACHE_DOUBLE_UTILS.getCacheDiskCount());
        assertEquals(0, CACHE_DOUBLE_UTILS.getCacheMemoryCount());
    }

    @After
    public void tearDown() {
        CACHE_DOUBLE_UTILS.clear();
    }

    static class ParcelableTest implements Parcelable {
        String author;
        String className;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        ParcelableTest(String author, String className) {
            this.author = author;
            this.className = className;
        }

        ParcelableTest(Parcel in) {
            author = in.readString();
            className = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(author);
            dest.writeString(className);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<ParcelableTest> CREATOR = new Creator<ParcelableTest>() {
            @Override
            public ParcelableTest createFromParcel(Parcel in) {
                return new ParcelableTest(in);
            }

            @Override
            public ParcelableTest[] newArray(int size) {
                return new ParcelableTest[size];
            }
        };

        @Override
        public boolean equals(Object obj) {
            return obj instanceof ParcelableTest
                    && ((ParcelableTest) obj).author.equals(author)
                    && ((ParcelableTest) obj).className.equals(className);
        }
    }

    static class SerializableTest implements Serializable {

        private static final long serialVersionUID = -5806706668736895024L;

        String author;
        String className;

        SerializableTest(String author, String className) {
            this.author = author;
            this.className = className;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof SerializableTest
                    && ((SerializableTest) obj).author.equals(author)
                    && ((SerializableTest) obj).className.equals(className);
        }
    }
}