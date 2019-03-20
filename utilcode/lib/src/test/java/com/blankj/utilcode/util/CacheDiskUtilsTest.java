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
 *     time  : 2017/05/26
 *     desc  : test CacheDiskUtils
 * </pre>
 */
public class CacheDiskUtilsTest extends BaseTest {

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
        CACHE_DISK_UTILS1.put("bytes1", BYTES, 60 * CacheDiskUtils.SEC);
        CACHE_DISK_UTILS1.put("string1", STRING, 60 * CacheDiskUtils.MIN);
        CACHE_DISK_UTILS1.put("jsonObject1", JSON_OBJECT, 24 * CacheDiskUtils.HOUR);
        CACHE_DISK_UTILS1.put("jsonArray1", JSON_ARRAY, 365 * CacheDiskUtils.DAY);
        CACHE_DISK_UTILS1.put("bitmap1", BITMAP, 60 * CacheDiskUtils.SEC);
        CACHE_DISK_UTILS1.put("drawable1", DRAWABLE, 60 * CacheDiskUtils.SEC);
        CACHE_DISK_UTILS1.put("parcelable1", PARCELABLE_TEST, 60 * CacheDiskUtils.SEC);
        CACHE_DISK_UTILS1.put("serializable1", SERIALIZABLE_TEST, 60 * CacheDiskUtils.SEC);

        CACHE_DISK_UTILS2.put("bytes2", BYTES);
        CACHE_DISK_UTILS2.put("string2", STRING);
        CACHE_DISK_UTILS2.put("jsonObject2", JSON_OBJECT);
        CACHE_DISK_UTILS2.put("jsonArray2", JSON_ARRAY);
        CACHE_DISK_UTILS2.put("parcelable2", PARCELABLE_TEST);
        CACHE_DISK_UTILS2.put("serializable2", SERIALIZABLE_TEST);
        CACHE_DISK_UTILS2.put("bitmap2", BITMAP);
        CACHE_DISK_UTILS2.put("drawable2", DRAWABLE);
    }

    @Test
    public void getBytes() {
        assertEquals(STRING, new String(CACHE_DISK_UTILS1.getBytes("bytes1")));
        assertEquals(STRING, new String(CACHE_DISK_UTILS1.getBytes("bytes1", null)));
        assertNull(CACHE_DISK_UTILS1.getBytes("bytes2", null));

        assertEquals(STRING, new String(CACHE_DISK_UTILS2.getBytes("bytes2")));
        assertEquals(STRING, new String(CACHE_DISK_UTILS2.getBytes("bytes2", null)));
        assertNull(CACHE_DISK_UTILS2.getBytes("bytes1", null));
    }

    @Test
    public void getString() {
        assertEquals(STRING, CACHE_DISK_UTILS1.getString("string1"));
        assertEquals(STRING, CACHE_DISK_UTILS1.getString("string1", null));
        assertNull(CACHE_DISK_UTILS1.getString("string2", null));

        assertEquals(STRING, CACHE_DISK_UTILS2.getString("string2"));
        assertEquals(STRING, CACHE_DISK_UTILS2.getString("string2", null));
        assertNull(CACHE_DISK_UTILS2.getString("string1", null));
    }

    @Test
    public void getJSONObject() {
        assertEquals(JSON_OBJECT.toString(), CACHE_DISK_UTILS1.getJSONObject("jsonObject1").toString());
        assertEquals(JSON_OBJECT.toString(), CACHE_DISK_UTILS1.getJSONObject("jsonObject1", null).toString());
        assertNull(CACHE_DISK_UTILS1.getJSONObject("jsonObject2", null));

        assertEquals(JSON_OBJECT.toString(), CACHE_DISK_UTILS2.getJSONObject("jsonObject2").toString());
        assertEquals(JSON_OBJECT.toString(), CACHE_DISK_UTILS2.getJSONObject("jsonObject2", null).toString());
        assertNull(CACHE_DISK_UTILS2.getJSONObject("jsonObject1", null));
    }

    @Test
    public void getJSONArray() {
        assertEquals(JSON_ARRAY.toString(), CACHE_DISK_UTILS1.getJSONArray("jsonArray1").toString());
        assertEquals(JSON_ARRAY.toString(), CACHE_DISK_UTILS1.getJSONArray("jsonArray1", null).toString());
        assertNull(CACHE_DISK_UTILS1.getJSONArray("jsonArray2", null));


        assertEquals(JSON_ARRAY.toString(), CACHE_DISK_UTILS2.getJSONArray("jsonArray2").toString());
        assertEquals(JSON_ARRAY.toString(), CACHE_DISK_UTILS2.getJSONArray("jsonArray2", null).toString());
        assertNull(CACHE_DISK_UTILS2.getJSONArray("jsonArray1", null));
    }

    @Test
    public void getBitmap() {
        String bitmapString = "Bitmap (100 x 100) compressed as PNG with quality 100";
        assertEquals(bitmapString, CACHE_DISK_UTILS1.getString("bitmap1"));
        assertEquals(bitmapString, CACHE_DISK_UTILS1.getString("bitmap1", null));
        assertNull(CACHE_DISK_UTILS1.getString("bitmap2", null));

        assertEquals(bitmapString, CACHE_DISK_UTILS2.getString("bitmap2"));
        assertEquals(bitmapString, CACHE_DISK_UTILS2.getString("bitmap2", null));
        assertNull(CACHE_DISK_UTILS2.getString("bitmap1", null));
    }

    @Test
    public void getDrawable() {
        String bitmapString = "Bitmap (100 x 100) compressed as PNG with quality 100";
        assertEquals(bitmapString, CACHE_DISK_UTILS1.getString("drawable1"));
        assertEquals(bitmapString, CACHE_DISK_UTILS1.getString("drawable1", null));
        assertNull(CACHE_DISK_UTILS1.getString("drawable2", null));

        assertEquals(bitmapString, CACHE_DISK_UTILS2.getString("drawable2"));
        assertEquals(bitmapString, CACHE_DISK_UTILS2.getString("drawable2", null));
        assertNull(CACHE_DISK_UTILS2.getString("drawable1", null));
    }

    @Test
    public void getParcel() {
        assertEquals(PARCELABLE_TEST, CACHE_DISK_UTILS1.getParcelable("parcelable1", ParcelableTest.CREATOR));
        assertEquals(PARCELABLE_TEST, CACHE_DISK_UTILS1.getParcelable("parcelable1", ParcelableTest.CREATOR, null));
        assertNull(CACHE_DISK_UTILS1.getParcelable("parcelable2", ParcelableTest.CREATOR, null));

        assertEquals(PARCELABLE_TEST, CACHE_DISK_UTILS2.getParcelable("parcelable2", ParcelableTest.CREATOR));
        assertEquals(PARCELABLE_TEST, CACHE_DISK_UTILS2.getParcelable("parcelable2", ParcelableTest.CREATOR, null));
        assertNull(CACHE_DISK_UTILS2.getParcelable("parcelable1", ParcelableTest.CREATOR, null));
    }

    @Test
    public void getSerializable() {
        assertEquals(SERIALIZABLE_TEST, CACHE_DISK_UTILS1.getSerializable("serializable1"));
        assertEquals(SERIALIZABLE_TEST, CACHE_DISK_UTILS1.getSerializable("serializable1", null));
        assertNull(CACHE_DISK_UTILS1.getSerializable("parcelable2", null));

        assertEquals(SERIALIZABLE_TEST, CACHE_DISK_UTILS2.getSerializable("serializable2"));
        assertEquals(SERIALIZABLE_TEST, CACHE_DISK_UTILS2.getSerializable("serializable2", null));
        assertNull(CACHE_DISK_UTILS2.getSerializable("parcelable1", null));
    }

    @Test
    public void getCacheSize() {
        assertEquals(FileUtils.getDirLength(DISK1_FILE), CACHE_DISK_UTILS1.getCacheSize());

        assertEquals(FileUtils.getDirLength(DISK2_FILE), CACHE_DISK_UTILS2.getCacheSize());
    }

    @Test
    public void getCacheCount() {
        assertEquals(8, CACHE_DISK_UTILS1.getCacheCount());

        assertEquals(8, CACHE_DISK_UTILS2.getCacheCount());
    }

    @Test
    public void remove() {
        assertNotNull(CACHE_DISK_UTILS1.getString("string1"));
        CACHE_DISK_UTILS1.remove("string1");
        assertNull(CACHE_DISK_UTILS1.getString("string1"));

        assertNotNull(CACHE_DISK_UTILS2.getString("string2"));
        CACHE_DISK_UTILS2.remove("string2");
        assertNull(CACHE_DISK_UTILS2.getString("string2"));
    }

    @Test
    public void clear() {
        assertNotNull(CACHE_DISK_UTILS1.getBytes("bytes1"));
        assertNotNull(CACHE_DISK_UTILS1.getString("string1"));
        assertNotNull(CACHE_DISK_UTILS1.getJSONObject("jsonObject1"));
        assertNotNull(CACHE_DISK_UTILS1.getJSONArray("jsonArray1"));
        assertNotNull(CACHE_DISK_UTILS1.getString("bitmap1"));
        assertNotNull(CACHE_DISK_UTILS1.getString("drawable1"));
        assertNotNull(CACHE_DISK_UTILS1.getParcelable("parcelable1", ParcelableTest.CREATOR));
        assertNotNull(CACHE_DISK_UTILS1.getSerializable("serializable1"));
        CACHE_DISK_UTILS1.clear();
        assertNull(CACHE_DISK_UTILS1.getBytes("bytes1"));
        assertNull(CACHE_DISK_UTILS1.getString("string1"));
        assertNull(CACHE_DISK_UTILS1.getJSONObject("jsonObject1"));
        assertNull(CACHE_DISK_UTILS1.getJSONArray("jsonArray1"));
        assertNull(CACHE_DISK_UTILS1.getString("bitmap1"));
        assertNull(CACHE_DISK_UTILS1.getString("drawable1"));
        assertNull(CACHE_DISK_UTILS1.getParcelable("parcelable1", ParcelableTest.CREATOR));
        assertNull(CACHE_DISK_UTILS1.getSerializable("serializable1"));
        assertEquals(0, CACHE_DISK_UTILS1.getCacheSize());
        assertEquals(0, CACHE_DISK_UTILS1.getCacheCount());


        assertNotNull(CACHE_DISK_UTILS2.getBytes("bytes2"));
        assertNotNull(CACHE_DISK_UTILS2.getString("string2"));
        assertNotNull(CACHE_DISK_UTILS2.getJSONObject("jsonObject2"));
        assertNotNull(CACHE_DISK_UTILS2.getJSONArray("jsonArray2"));
        assertNotNull(CACHE_DISK_UTILS2.getString("bitmap2"));
        assertNotNull(CACHE_DISK_UTILS2.getString("drawable2"));
        assertNotNull(CACHE_DISK_UTILS2.getParcelable("parcelable2", ParcelableTest.CREATOR));
        assertNotNull(CACHE_DISK_UTILS2.getSerializable("serializable2"));
        CACHE_DISK_UTILS2.clear();
        assertNull(CACHE_DISK_UTILS2.getBytes("bytes2"));
        assertNull(CACHE_DISK_UTILS2.getString("string2"));
        assertNull(CACHE_DISK_UTILS2.getJSONObject("jsonObject2"));
        assertNull(CACHE_DISK_UTILS2.getJSONArray("jsonArray2"));
        assertNull(CACHE_DISK_UTILS2.getString("bitmap2"));
        assertNull(CACHE_DISK_UTILS2.getString("drawable2"));
        assertNull(CACHE_DISK_UTILS2.getParcelable("parcelable2", ParcelableTest.CREATOR));
        assertNull(CACHE_DISK_UTILS2.getSerializable("serializable2"));
        assertEquals(0, CACHE_DISK_UTILS2.getCacheSize());
        assertEquals(0, CACHE_DISK_UTILS2.getCacheCount());
    }

    @After
    public void tearDown() {
        CACHE_DISK_UTILS1.clear();
        CACHE_DISK_UTILS2.clear();
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

