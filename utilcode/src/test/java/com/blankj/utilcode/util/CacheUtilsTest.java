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
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.File;
import java.io.Serializable;
import java.util.LinkedHashMap;

import static com.blankj.utilcode.util.TestConfig.FILE_SEP;
import static com.blankj.utilcode.util.TestConfig.PATH_CACHE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/05/26
 *     desc  : test CacheUtils
 * </pre>
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class CacheUtilsTest {

    static {
        TestUtils.init();
    }

    private final String cache1Path = PATH_CACHE + "cache1" + FILE_SEP;
    private final String cache2Path = PATH_CACHE + "cache2" + FILE_SEP;
    private final File   cache1File = new File(cache1Path);
    private final File   cache2File = new File(cache2Path);

    private CacheUtils mCacheUtils1;
    private CacheUtils mCacheUtils2;

    private static LinkedHashMap<String, String> map;

    private byte[]           mBytes            = "CacheUtils".getBytes();
    private String           mString           = "CacheUtils";
    private JSONObject       mJSONObject       = new JSONObject();
    private JSONArray        mJSONArray        = new JSONArray();
    private ParcelableTest   mParcelableTest   = new ParcelableTest("Blankj", "CacheUtils");
    private SerializableTest mSerializableTest = new SerializableTest("Blankj", "CacheUtils");
    private Bitmap           mBitmap           = Bitmap.createBitmap(100, 100, Bitmap.Config.RGB_565);
    private Drawable         mDrawable         = new BitmapDrawable(Utils.getApp().getResources(), mBitmap);

    public CacheUtilsTest() {
        try {
            mJSONObject.put("class", "CacheUtils");
            mJSONObject.put("author", "Blankj");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            mJSONArray.put(0, mJSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() throws Exception {
        if (mCacheUtils1 == null) {
            mCacheUtils1 = CacheUtils.getInstance(cache1File);
            mCacheUtils1.put("bytes1", mBytes, 60 * CacheUtils.SEC);
            mCacheUtils1.put("string1", mString, 60 * CacheUtils.MIN);
            mCacheUtils1.put("jsonObject1", mJSONObject, 24 * CacheUtils.HOUR);
            mCacheUtils1.put("jsonArray1", mJSONArray, 365 * CacheUtils.DAY);
            mCacheUtils1.put("bitmap1", mBitmap, 60 * CacheUtils.SEC);
            mCacheUtils1.put("drawable1", mDrawable, 60 * CacheUtils.SEC);
            mCacheUtils1.put("parcelable1", mParcelableTest, 60 * CacheUtils.SEC);
            mCacheUtils1.put("serializable1", mSerializableTest, 60 * CacheUtils.SEC);
        }
        if (mCacheUtils2 == null) {
            mCacheUtils2 = CacheUtils.getInstance(cache2File);
            mCacheUtils2.put("bytes2", mBytes);
            mCacheUtils2.put("string2", mString);
            mCacheUtils2.put("jsonObject2", mJSONObject);
            mCacheUtils2.put("jsonArray2", mJSONArray);
            mCacheUtils2.put("parcelable2", mParcelableTest);
            mCacheUtils2.put("serializable2", mSerializableTest);
            mCacheUtils2.put("bitmap2", mBitmap);
            mCacheUtils2.put("drawable2", mDrawable);
        }
    }

    @Test
    public void getBytes() throws Exception {
        assertEquals(mString, new String(mCacheUtils1.getBytes("bytes1")));
        assertEquals(mString, new String(mCacheUtils1.getBytes("bytes1", null)));
        assertNull(mCacheUtils1.getBytes("bytes2", null));

        assertEquals(mString, new String(mCacheUtils2.getBytes("bytes2")));
        assertEquals(mString, new String(mCacheUtils2.getBytes("bytes2", null)));
        assertNull(mCacheUtils2.getBytes("bytes1", null));
    }

    @Test
    public void getString() throws Exception {
        assertEquals(mString, mCacheUtils1.getString("string1"));
        assertEquals(mString, mCacheUtils1.getString("string1", null));
        assertNull(mCacheUtils1.getString("string2", null));

        assertEquals(mString, mCacheUtils2.getString("string2"));
        assertEquals(mString, mCacheUtils2.getString("string2", null));
        assertNull(mCacheUtils2.getString("string1", null));
    }

    @Test
    public void getJSONObject() throws Exception {
        assertEquals(mJSONObject.toString(), mCacheUtils1.getJSONObject("jsonObject1").toString());
        assertEquals(mJSONObject.toString(), mCacheUtils1.getJSONObject("jsonObject1", null).toString());
        assertNull(mCacheUtils1.getJSONObject("jsonObject2", null));

        assertEquals(mJSONObject.toString(), mCacheUtils2.getJSONObject("jsonObject2").toString());
        assertEquals(mJSONObject.toString(), mCacheUtils2.getJSONObject("jsonObject2", null).toString());
        assertNull(mCacheUtils2.getJSONObject("jsonObject1", null));
    }

    @Test
    public void getJSONArray() throws Exception {
        assertEquals(mJSONArray.toString(), mCacheUtils1.getJSONArray("jsonArray1").toString());
        assertEquals(mJSONArray.toString(), mCacheUtils1.getJSONArray("jsonArray1", null).toString());
        assertNull(mCacheUtils1.getJSONArray("jsonArray2", null));


        assertEquals(mJSONArray.toString(), mCacheUtils2.getJSONArray("jsonArray2").toString());
        assertEquals(mJSONArray.toString(), mCacheUtils2.getJSONArray("jsonArray2", null).toString());
        assertNull(mCacheUtils2.getJSONArray("jsonArray1", null));
    }

    @Test
    public void getBitmap() throws Exception {
        String bitmapString = "Bitmap (100 x 100) compressed as PNG with quality 100";
        assertTrue(mCacheUtils1.getString("bitmap1").equals(bitmapString));
        assertTrue(mCacheUtils1.getString("bitmap1", null).equals(bitmapString));
        assertNull(mCacheUtils1.getString("bitmap2", null));

        assertTrue(mCacheUtils2.getString("bitmap2").equals(bitmapString));
        assertTrue(mCacheUtils2.getString("bitmap2", null).equals(bitmapString));
        assertNull(mCacheUtils2.getString("bitmap1", null));
    }

    @Test
    public void getDrawable() throws Exception {
        String bitmapString = "Bitmap (100 x 100) compressed as PNG with quality 100";
        assertTrue(mCacheUtils1.getString("drawable1").equals(bitmapString));
        assertTrue(mCacheUtils1.getString("drawable1", null).equals(bitmapString));
        assertNull(mCacheUtils1.getString("drawable2", null));

        assertTrue(mCacheUtils2.getString("drawable2").equals(bitmapString));
        assertTrue(mCacheUtils2.getString("drawable2", null).equals(bitmapString));
        assertNull(mCacheUtils2.getString("drawable1", null));
    }

    @Test
    public void getParcel() throws Exception {
        assertTrue(mCacheUtils1.getParcelable("parcelable1", ParcelableTest.CREATOR).equals(mParcelableTest));
        assertTrue(mCacheUtils1.getParcelable("parcelable1", ParcelableTest.CREATOR, null).equals(mParcelableTest));
        assertNull(mCacheUtils1.getParcelable("parcelable2", ParcelableTest.CREATOR, null));

        assertTrue(mCacheUtils2.getParcelable("parcelable2", ParcelableTest.CREATOR).equals(mParcelableTest));
        assertTrue(mCacheUtils2.getParcelable("parcelable2", ParcelableTest.CREATOR, null).equals(mParcelableTest));
        assertNull(mCacheUtils2.getParcelable("parcelable1", ParcelableTest.CREATOR, null));
    }

    @Test
    public void getSerializable() throws Exception {
        assertTrue(mCacheUtils1.getSerializable("serializable1").equals(mSerializableTest));
        assertTrue(mCacheUtils1.getSerializable("serializable1", null).equals(mSerializableTest));
        assertNull(mCacheUtils1.getSerializable("parcelable2", null));

        assertTrue(mCacheUtils2.getSerializable("serializable2").equals(mSerializableTest));
        assertTrue(mCacheUtils2.getSerializable("serializable2", null).equals(mSerializableTest));
        assertNull(mCacheUtils2.getSerializable("parcelable1", null));
    }

    @Test
    public void getCacheSize() throws Exception {
        assertEquals(FileUtils.getDirLength(cache1File), mCacheUtils1.getCacheSize());

        assertEquals(FileUtils.getDirLength(cache2File), mCacheUtils2.getCacheSize());
    }

    @Test
    public void getCacheCount() throws Exception {
        assertEquals(8, mCacheUtils1.getCacheCount());

        assertEquals(8, mCacheUtils2.getCacheCount());
    }

    @Test
    public void remove() throws Exception {
        assertNotNull(mCacheUtils1.getString("string1"));
        mCacheUtils1.remove("string1");
        assertNull(mCacheUtils1.getString("string1"));

        assertNotNull(mCacheUtils2.getString("string2"));
        mCacheUtils2.remove("string2");
        assertNull(mCacheUtils2.getString("string2"));
    }

    @Test
    public void clear() throws Exception {
        assertNotNull(mCacheUtils1.getBytes("bytes1"));
        assertNotNull(mCacheUtils1.getString("string1"));
        assertNotNull(mCacheUtils1.getJSONObject("jsonObject1"));
        assertNotNull(mCacheUtils1.getJSONArray("jsonArray1"));
        assertNotNull(mCacheUtils1.getString("bitmap1"));
        assertNotNull(mCacheUtils1.getString("drawable1"));
        assertNotNull(mCacheUtils1.getParcelable("parcelable1", ParcelableTest.CREATOR));
        assertNotNull(mCacheUtils1.getSerializable("serializable1"));
        mCacheUtils1.clear();
        assertNull(mCacheUtils1.getBytes("bytes1"));
        assertNull(mCacheUtils1.getString("string1"));
        assertNull(mCacheUtils1.getJSONObject("jsonObject1"));
        assertNull(mCacheUtils1.getJSONArray("jsonArray1"));
        assertNull(mCacheUtils1.getString("bitmap1"));
        assertNull(mCacheUtils1.getString("drawable1"));
        assertNull(mCacheUtils1.getParcelable("parcelable1", ParcelableTest.CREATOR));
        assertNull(mCacheUtils1.getSerializable("serializable1"));


        assertNotNull(mCacheUtils2.getBytes("bytes2"));
        assertNotNull(mCacheUtils2.getString("string2"));
        assertNotNull(mCacheUtils2.getJSONObject("jsonObject2"));
        assertNotNull(mCacheUtils2.getJSONArray("jsonArray2"));
        assertNotNull(mCacheUtils2.getString("bitmap2"));
        assertNotNull(mCacheUtils2.getString("drawable2"));
        assertNotNull(mCacheUtils2.getParcelable("parcelable2", ParcelableTest.CREATOR));
        assertNotNull(mCacheUtils2.getSerializable("serializable2"));
        mCacheUtils2.clear();
        assertNull(mCacheUtils2.getBytes("bytes2"));
        assertNull(mCacheUtils2.getString("string2"));
        assertNull(mCacheUtils2.getJSONObject("jsonObject2"));
        assertNull(mCacheUtils2.getJSONArray("jsonArray2"));
        assertNull(mCacheUtils2.getString("bitmap2"));
        assertNull(mCacheUtils2.getString("drawable2"));
        assertNull(mCacheUtils2.getParcelable("parcelable2", ParcelableTest.CREATOR));
        assertNull(mCacheUtils2.getSerializable("serializable2"));
    }

    @After
    public void tearDown() throws Exception {
        mCacheUtils1.clear();
        mCacheUtils2.clear();
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
            return obj instanceof ParcelableTest && ((ParcelableTest) obj).author.equals(author) && ((ParcelableTest) obj).className.equals(className);
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

