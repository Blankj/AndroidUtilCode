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
 *     time  : 2019/01/04
 *     desc  : test CacheDiskStaticUtils
 * </pre>
 */
public class CacheDiskStaticUtilsTest extends BaseTest {

    private static final String           DISK1_PATH        = PATH_CACHE + "disk1" + FILE_SEP;
    private static final String           DISK2_PATH        = PATH_CACHE + "disk2" + FILE_SEP;
    private static final File             DISK1_FILE        = new File(DISK1_PATH);
    private static final File             DISK2_FILE        = new File(DISK2_PATH);
    private static final CacheDiskUtils   CACHE_DISK_UTILS1 = CacheDiskUtils.getInstance(DISK1_FILE);
    private static final CacheDiskUtils   CACHE_DISK_UTILS2 = CacheDiskUtils.getInstance(DISK2_FILE);
    private static final byte[]           BYTES             = "CacheDiskUtils".getBytes();
    private static final String           STRING            = "CacheDiskUtils";
    private static final JSONObject       JSON_OBJECT       = new JSONObject();
    private static final JSONArray        JSON_ARRAY        = new JSONArray();
    private static final ParcelableTest   PARCELABLE_TEST   = new ParcelableTest("Blankj", "CacheDiskUtils");
    private static final SerializableTest SERIALIZABLE_TEST = new SerializableTest("Blankj", "CacheDiskUtils");
    private static final Bitmap           BITMAP            = Bitmap.createBitmap(100, 100, Bitmap.Config.RGB_565);
    private static final Drawable         DRAWABLE          = new BitmapDrawable(Utils.getApp().getResources(), BITMAP);

    static {
        try {
            JSON_OBJECT.put("class", "CacheDiskUtils");
            JSON_OBJECT.put("author", "Blankj");
            JSON_ARRAY.put(0, JSON_OBJECT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() {
        CacheDiskStaticUtils.put("bytes1", BYTES, 60 * CacheDiskUtils.SEC, CACHE_DISK_UTILS1);
        CacheDiskStaticUtils.put("string1", STRING, 60 * CacheDiskUtils.MIN, CACHE_DISK_UTILS1);
        CacheDiskStaticUtils.put("jsonObject1", JSON_OBJECT, 24 * CacheDiskUtils.HOUR, CACHE_DISK_UTILS1);
        CacheDiskStaticUtils.put("jsonArray1", JSON_ARRAY, 365 * CacheDiskUtils.DAY, CACHE_DISK_UTILS1);
        CacheDiskStaticUtils.put("bitmap1", BITMAP, 60 * CacheDiskUtils.SEC, CACHE_DISK_UTILS1);
        CacheDiskStaticUtils.put("drawable1", DRAWABLE, 60 * CacheDiskUtils.SEC, CACHE_DISK_UTILS1);
        CacheDiskStaticUtils.put("parcelable1", PARCELABLE_TEST, 60 * CacheDiskUtils.SEC, CACHE_DISK_UTILS1);
        CacheDiskStaticUtils.put("serializable1", SERIALIZABLE_TEST, 60 * CacheDiskUtils.SEC, CACHE_DISK_UTILS1);

        CacheDiskStaticUtils.put("bytes2", BYTES, CACHE_DISK_UTILS2);
        CacheDiskStaticUtils.put("string2", STRING, CACHE_DISK_UTILS2);
        CacheDiskStaticUtils.put("jsonObject2", JSON_OBJECT, CACHE_DISK_UTILS2);
        CacheDiskStaticUtils.put("jsonArray2", JSON_ARRAY, CACHE_DISK_UTILS2);
        CacheDiskStaticUtils.put("parcelable2", PARCELABLE_TEST, CACHE_DISK_UTILS2);
        CacheDiskStaticUtils.put("serializable2", SERIALIZABLE_TEST, CACHE_DISK_UTILS2);
        CacheDiskStaticUtils.put("bitmap2", BITMAP, CACHE_DISK_UTILS2);
        CacheDiskStaticUtils.put("drawable2", DRAWABLE, CACHE_DISK_UTILS2);
    }

    @Test
    public void getBytes() {
        assertEquals(STRING, new String(CacheDiskStaticUtils.getBytes("bytes1", CACHE_DISK_UTILS1)));
        assertEquals(STRING, new String(CacheDiskStaticUtils.getBytes("bytes1", null, CACHE_DISK_UTILS1)));
        assertNull(CacheDiskStaticUtils.getBytes("bytes2", null, CACHE_DISK_UTILS1));

        assertEquals(STRING, new String(CacheDiskStaticUtils.getBytes("bytes2", CACHE_DISK_UTILS2)));
        assertEquals(STRING, new String(CacheDiskStaticUtils.getBytes("bytes2", null, CACHE_DISK_UTILS2)));
        assertNull(CacheDiskStaticUtils.getBytes("bytes1", null, CACHE_DISK_UTILS2));
    }

    @Test
    public void getString() {
        assertEquals(STRING, CacheDiskStaticUtils.getString("string1", CACHE_DISK_UTILS1));
        assertEquals(STRING, CacheDiskStaticUtils.getString("string1", null, CACHE_DISK_UTILS1));
        assertNull(CacheDiskStaticUtils.getString("string2", null, CACHE_DISK_UTILS1));

        assertEquals(STRING, CacheDiskStaticUtils.getString("string2", CACHE_DISK_UTILS2));
        assertEquals(STRING, CacheDiskStaticUtils.getString("string2", null, CACHE_DISK_UTILS2));
        assertNull(CacheDiskStaticUtils.getString("string1", null, CACHE_DISK_UTILS2));
    }

    @Test
    public void getJSONObject() {
        assertEquals(JSON_OBJECT.toString(), CacheDiskStaticUtils.getJSONObject("jsonObject1", CACHE_DISK_UTILS1).toString());
        assertEquals(JSON_OBJECT.toString(), CacheDiskStaticUtils.getJSONObject("jsonObject1", null, CACHE_DISK_UTILS1).toString());
        assertNull(CacheDiskStaticUtils.getJSONObject("jsonObject2", null, CACHE_DISK_UTILS1));

        assertEquals(JSON_OBJECT.toString(), CacheDiskStaticUtils.getJSONObject("jsonObject2", CACHE_DISK_UTILS2).toString());
        assertEquals(JSON_OBJECT.toString(), CacheDiskStaticUtils.getJSONObject("jsonObject2", null, CACHE_DISK_UTILS2).toString());
        assertNull(CacheDiskStaticUtils.getJSONObject("jsonObject1", null, CACHE_DISK_UTILS2));
    }

    @Test
    public void getJSONArray() {
        assertEquals(JSON_ARRAY.toString(), CacheDiskStaticUtils.getJSONArray("jsonArray1", CACHE_DISK_UTILS1).toString());
        assertEquals(JSON_ARRAY.toString(), CacheDiskStaticUtils.getJSONArray("jsonArray1", null, CACHE_DISK_UTILS1).toString());
        assertNull(CacheDiskStaticUtils.getJSONArray("jsonArray2", null, CACHE_DISK_UTILS1));


        assertEquals(JSON_ARRAY.toString(), CacheDiskStaticUtils.getJSONArray("jsonArray2", CACHE_DISK_UTILS2).toString());
        assertEquals(JSON_ARRAY.toString(), CacheDiskStaticUtils.getJSONArray("jsonArray2", null, CACHE_DISK_UTILS2).toString());
        assertNull(CacheDiskStaticUtils.getJSONArray("jsonArray1", null, CACHE_DISK_UTILS2));
    }

    @Test
    public void getBitmap() {
        String bitmapString = "Bitmap (100 x 100) compressed as PNG with quality 100";
        assertEquals(bitmapString, CacheDiskStaticUtils.getString("bitmap1", CACHE_DISK_UTILS1));
        assertEquals(bitmapString, CacheDiskStaticUtils.getString("bitmap1", null, CACHE_DISK_UTILS1));
        assertNull(CacheDiskStaticUtils.getString("bitmap2", null, CACHE_DISK_UTILS1));

        assertEquals(bitmapString, CacheDiskStaticUtils.getString("bitmap2", CACHE_DISK_UTILS2));
        assertEquals(bitmapString, CacheDiskStaticUtils.getString("bitmap2", null, CACHE_DISK_UTILS2));
        assertNull(CacheDiskStaticUtils.getString("bitmap1", null, CACHE_DISK_UTILS2));
    }

    @Test
    public void getDrawable() {
        String bitmapString = "Bitmap (100 x 100) compressed as PNG with quality 100";
        assertEquals(bitmapString, CacheDiskStaticUtils.getString("drawable1", CACHE_DISK_UTILS1));
        assertEquals(bitmapString, CacheDiskStaticUtils.getString("drawable1", null, CACHE_DISK_UTILS1));
        assertNull(CacheDiskStaticUtils.getString("drawable2", null, CACHE_DISK_UTILS1));

        assertEquals(bitmapString, CacheDiskStaticUtils.getString("drawable2", CACHE_DISK_UTILS2));
        assertEquals(bitmapString, CacheDiskStaticUtils.getString("drawable2", null, CACHE_DISK_UTILS2));
        assertNull(CacheDiskStaticUtils.getString("drawable1", null, CACHE_DISK_UTILS2));
    }

    @Test
    public void getParcel() {
        assertEquals(PARCELABLE_TEST, CacheDiskStaticUtils.getParcelable("parcelable1", ParcelableTest.CREATOR, CACHE_DISK_UTILS1));
        assertEquals(PARCELABLE_TEST, CacheDiskStaticUtils.getParcelable("parcelable1", ParcelableTest.CREATOR, null, CACHE_DISK_UTILS1));
        assertNull(CacheDiskStaticUtils.getParcelable("parcelable2", ParcelableTest.CREATOR, null, CACHE_DISK_UTILS1));

        assertEquals(PARCELABLE_TEST, CacheDiskStaticUtils.getParcelable("parcelable2", ParcelableTest.CREATOR, CACHE_DISK_UTILS2));
        assertEquals(PARCELABLE_TEST, CacheDiskStaticUtils.getParcelable("parcelable2", ParcelableTest.CREATOR, null, CACHE_DISK_UTILS2));
        assertNull(CacheDiskStaticUtils.getParcelable("parcelable1", ParcelableTest.CREATOR, null, CACHE_DISK_UTILS2));
    }

    @Test
    public void getSerializable() {
        assertEquals(SERIALIZABLE_TEST, CacheDiskStaticUtils.getSerializable("serializable1", CACHE_DISK_UTILS1));
        assertEquals(SERIALIZABLE_TEST, CacheDiskStaticUtils.getSerializable("serializable1", null, CACHE_DISK_UTILS1));
        assertNull(CacheDiskStaticUtils.getSerializable("parcelable2", null, CACHE_DISK_UTILS1));

        assertEquals(SERIALIZABLE_TEST, CacheDiskStaticUtils.getSerializable("serializable2", CACHE_DISK_UTILS2));
        assertEquals(SERIALIZABLE_TEST, CacheDiskStaticUtils.getSerializable("serializable2", null, CACHE_DISK_UTILS2));
        assertNull(CacheDiskStaticUtils.getSerializable("parcelable1", null, CACHE_DISK_UTILS2));
    }

    @Test
    public void getCacheSize() {
        assertEquals(FileUtils.getDirLength(DISK1_FILE), CacheDiskStaticUtils.getCacheSize(CACHE_DISK_UTILS1));

        assertEquals(FileUtils.getDirLength(DISK2_FILE), CacheDiskStaticUtils.getCacheSize(CACHE_DISK_UTILS2));
    }

    @Test
    public void getCacheCount() {
        assertEquals(8, CacheDiskStaticUtils.getCacheCount(CACHE_DISK_UTILS1));

        assertEquals(8, CacheDiskStaticUtils.getCacheCount(CACHE_DISK_UTILS2));
    }

    @Test
    public void remove() {
        assertNotNull(CacheDiskStaticUtils.getString("string1", CACHE_DISK_UTILS1));
        CacheDiskStaticUtils.remove("string1", CACHE_DISK_UTILS1);
        assertNull(CacheDiskStaticUtils.getString("string1", CACHE_DISK_UTILS1));

        assertNotNull(CacheDiskStaticUtils.getString("string2", CACHE_DISK_UTILS2));
        CacheDiskStaticUtils.remove("string2", CACHE_DISK_UTILS2);
        assertNull(CacheDiskStaticUtils.getString("string2", CACHE_DISK_UTILS2));
    }

    @Test
    public void clear() {
        assertNotNull(CacheDiskStaticUtils.getBytes("bytes1", CACHE_DISK_UTILS1));
        assertNotNull(CacheDiskStaticUtils.getString("string1", CACHE_DISK_UTILS1));
        assertNotNull(CacheDiskStaticUtils.getJSONObject("jsonObject1", CACHE_DISK_UTILS1));
        assertNotNull(CacheDiskStaticUtils.getJSONArray("jsonArray1", CACHE_DISK_UTILS1));
        assertNotNull(CacheDiskStaticUtils.getString("bitmap1", CACHE_DISK_UTILS1));
        assertNotNull(CacheDiskStaticUtils.getString("drawable1", CACHE_DISK_UTILS1));
        assertNotNull(CacheDiskStaticUtils.getParcelable("parcelable1", ParcelableTest.CREATOR, CACHE_DISK_UTILS1));
        assertNotNull(CacheDiskStaticUtils.getSerializable("serializable1", CACHE_DISK_UTILS1));
        CacheDiskStaticUtils.clear(CACHE_DISK_UTILS1);
        assertNull(CacheDiskStaticUtils.getBytes("bytes1", CACHE_DISK_UTILS1));
        assertNull(CacheDiskStaticUtils.getString("string1", CACHE_DISK_UTILS1));
        assertNull(CacheDiskStaticUtils.getJSONObject("jsonObject1", CACHE_DISK_UTILS1));
        assertNull(CacheDiskStaticUtils.getJSONArray("jsonArray1", CACHE_DISK_UTILS1));
        assertNull(CacheDiskStaticUtils.getString("bitmap1", CACHE_DISK_UTILS1));
        assertNull(CacheDiskStaticUtils.getString("drawable1", CACHE_DISK_UTILS1));
        assertNull(CacheDiskStaticUtils.getParcelable("parcelable1", ParcelableTest.CREATOR, CACHE_DISK_UTILS1));
        assertNull(CacheDiskStaticUtils.getSerializable("serializable1", CACHE_DISK_UTILS1));
        assertEquals(0, CacheDiskStaticUtils.getCacheSize(CACHE_DISK_UTILS1));
        assertEquals(0, CacheDiskStaticUtils.getCacheCount(CACHE_DISK_UTILS1));


        assertNotNull(CacheDiskStaticUtils.getBytes("bytes2", CACHE_DISK_UTILS2));
        assertNotNull(CacheDiskStaticUtils.getString("string2", CACHE_DISK_UTILS2));
        assertNotNull(CacheDiskStaticUtils.getJSONObject("jsonObject2", CACHE_DISK_UTILS2));
        assertNotNull(CacheDiskStaticUtils.getJSONArray("jsonArray2", CACHE_DISK_UTILS2));
        assertNotNull(CacheDiskStaticUtils.getString("bitmap2", CACHE_DISK_UTILS2));
        assertNotNull(CacheDiskStaticUtils.getString("drawable2", CACHE_DISK_UTILS2));
        assertNotNull(CacheDiskStaticUtils.getParcelable("parcelable2", ParcelableTest.CREATOR, CACHE_DISK_UTILS2));
        assertNotNull(CacheDiskStaticUtils.getSerializable("serializable2", CACHE_DISK_UTILS2));
        CacheDiskStaticUtils.clear(CACHE_DISK_UTILS2);
        assertNull(CacheDiskStaticUtils.getBytes("bytes2", CACHE_DISK_UTILS2));
        assertNull(CacheDiskStaticUtils.getString("string2", CACHE_DISK_UTILS2));
        assertNull(CacheDiskStaticUtils.getJSONObject("jsonObject2", CACHE_DISK_UTILS2));
        assertNull(CacheDiskStaticUtils.getJSONArray("jsonArray2", CACHE_DISK_UTILS2));
        assertNull(CacheDiskStaticUtils.getString("bitmap2", CACHE_DISK_UTILS2));
        assertNull(CacheDiskStaticUtils.getString("drawable2", CACHE_DISK_UTILS2));
        assertNull(CacheDiskStaticUtils.getParcelable("parcelable2", ParcelableTest.CREATOR, CACHE_DISK_UTILS2));
        assertNull(CacheDiskStaticUtils.getSerializable("serializable2", CACHE_DISK_UTILS2));
        assertEquals(0, CacheDiskStaticUtils.getCacheSize(CACHE_DISK_UTILS2));
        assertEquals(0, CacheDiskStaticUtils.getCacheCount(CACHE_DISK_UTILS2));
    }

    @After
    public void tearDown() {
        CacheDiskStaticUtils.clear(CACHE_DISK_UTILS1);
        CacheDiskStaticUtils.clear(CACHE_DISK_UTILS2);
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

