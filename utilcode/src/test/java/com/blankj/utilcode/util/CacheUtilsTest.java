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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.File;
import java.io.Serializable;
import java.util.LinkedHashMap;

import static com.blankj.utilcode.util.TestUtils.FILE_SEP;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/05/26
 *     desc  :
 * </pre>
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class CacheUtilsTest {

    final String path1 = TestUtils.TEST_PATH + FILE_SEP + "cache" + FILE_SEP + "cache1" + FILE_SEP;
    final String path2 = TestUtils.TEST_PATH + FILE_SEP + "cache" + FILE_SEP + "cache2" + FILE_SEP;
    final File   file1 = new File(path1);
    final File   file2 = new File(path2);


    private CacheUtils mCacheUtils1;
    private CacheUtils mCacheUtils2;

    private static LinkedHashMap<String, String> map;

    byte[]           mBytes            = "CacheUtils".getBytes();
    String           mString           = "CacheUtils";
    JSONObject       mJSONObject       = new JSONObject();
    JSONArray        mJSONArray        = new JSONArray();
    ParcelableTest   mParcelableTest   = new ParcelableTest("Blankj", "CacheUtils");
    SerializableTest mSerializableTest = new SerializableTest("Blankj", "CacheUtils");
    Bitmap           mBitmap           = Bitmap.createBitmap(100, 100, Bitmap.Config.RGB_565);
    Drawable         mDrawable         = new BitmapDrawable(Utils.getContext().getResources(), mBitmap);

    static {
        TestUtils.init();
    }

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
            mCacheUtils1 = CacheUtils.getInstance(file1);
            mCacheUtils1.put("bytes1", mBytes, 6000);
            mCacheUtils1.put("string1", mString, 6000);
            mCacheUtils1.put("jsonObject1", mJSONObject, 6000);
            mCacheUtils1.put("jsonArray1", mJSONArray, 6000);
            mCacheUtils1.put("bitmap1", mBitmap, 6000);
            mCacheUtils1.put("drawable1", mDrawable, 6000);
            mCacheUtils1.put("parcelable1", mParcelableTest, 6000);
            mCacheUtils1.put("serializable1", mSerializableTest, 6000);
        }
        if (mCacheUtils2 == null) {
            mCacheUtils2 = CacheUtils.getInstance(file2);
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
        Assert.assertEquals(mString, new String(mCacheUtils1.getBytes("bytes1")));

        Assert.assertEquals(mString, new String(mCacheUtils2.getBytes("bytes2")));
    }

    @Test
    public void getString() throws Exception {
        Assert.assertEquals(mString, mCacheUtils1.getString("string1"));

        Assert.assertEquals(mString, mCacheUtils2.getString("string2"));
    }

    @Test
    public void getJSONObject() throws Exception {
        Assert.assertEquals(mJSONObject.toString(), mCacheUtils1.getJSONObject("jsonObject1").toString());

        Assert.assertEquals(mJSONObject.toString(), mCacheUtils2.getJSONObject("jsonObject2").toString());
    }

    @Test
    public void getJSONArray() throws Exception {
        Assert.assertEquals(mJSONArray.toString(), mCacheUtils1.getJSONArray("jsonArray1").toString());

        Assert.assertEquals(mJSONArray.toString(), mCacheUtils2.getJSONArray("jsonArray2").toString());
    }

    @Test
    public void getBitmap() throws Exception {
        Assert.assertTrue(mCacheUtils1.getString("bitmap1").equals("Bitmap (100 x 100) compressed as PNG with quality 100"));

        Assert.assertTrue(mCacheUtils2.getString("bitmap2").equals("Bitmap (100 x 100) compressed as PNG with quality 100"));
    }

    @Test
    public void getDrawable() throws Exception {
        Assert.assertTrue(mCacheUtils1.getString("drawable1").equals("Bitmap (100 x 100) compressed as PNG with quality 100"));

        Assert.assertTrue(mCacheUtils2.getString("drawable2").equals("Bitmap (100 x 100) compressed as PNG with quality 100"));
    }

    @Test
    public void getParcel() throws Exception {
        Assert.assertTrue(mCacheUtils1.getParcelable("parcelable1", ParcelableTest.CREATOR).equals(mParcelableTest));

        Assert.assertTrue(mCacheUtils2.getParcelable("parcelable2", ParcelableTest.CREATOR).equals(mParcelableTest));
    }

    @Test
    public void getSerializable() throws Exception {
        Assert.assertTrue(mCacheUtils1.getSerializable("serializable1").equals(mSerializableTest));

        Assert.assertTrue(mCacheUtils2.getSerializable("serializable2").equals(mSerializableTest));
    }

    @Test
    public void getCacheSize() throws Exception {
        Assert.assertEquals(FileUtils.getDirLength(file1), mCacheUtils1.getCacheSize());

        Assert.assertEquals(FileUtils.getDirLength(file2), mCacheUtils2.getCacheSize());
    }

    @Test
    public void getCacheCount() throws Exception {
        Assert.assertEquals(8, mCacheUtils1.getCacheCount());

        Assert.assertEquals(8, mCacheUtils2.getCacheCount());
    }

    @Test
    public void remove() throws Exception {
        Assert.assertNotNull(mCacheUtils1.getString("string1"));
        mCacheUtils1.remove("string1");
        Assert.assertNull(mCacheUtils1.getString("string1"));

        Assert.assertNotNull(mCacheUtils2.getString("string2"));
        mCacheUtils2.remove("string2");
        Assert.assertNull(mCacheUtils2.getString("string2"));
    }

    @Test
    public void clear() throws Exception {
        Assert.assertNotNull(mCacheUtils1.getBytes("bytes1"));
        Assert.assertNotNull(mCacheUtils1.getString("string1"));
        Assert.assertNotNull(mCacheUtils1.getJSONObject("jsonObject1"));
        Assert.assertNotNull(mCacheUtils1.getJSONArray("jsonArray1"));
        Assert.assertNotNull(mCacheUtils1.getString("bitmap1"));
        Assert.assertNotNull(mCacheUtils1.getString("drawable1"));
        Assert.assertNotNull(mCacheUtils1.getParcelable("parcelable1", ParcelableTest.CREATOR));
        Assert.assertNotNull(mCacheUtils1.getSerializable("serializable1"));
        mCacheUtils1.clear();
        Assert.assertNull(mCacheUtils1.getBytes("bytes1"));
        Assert.assertNull(mCacheUtils1.getString("string1"));
        Assert.assertNull(mCacheUtils1.getJSONObject("jsonObject1"));
        Assert.assertNull(mCacheUtils1.getJSONArray("jsonArray1"));
        Assert.assertNull(mCacheUtils1.getString("bitmap1"));
        Assert.assertNull(mCacheUtils1.getString("drawable1"));
        Assert.assertNull(mCacheUtils1.getParcelable("parcelable1", ParcelableTest.CREATOR));
        Assert.assertNull(mCacheUtils1.getSerializable("serializable1"));


        Assert.assertNotNull(mCacheUtils2.getBytes("bytes2"));
        Assert.assertNotNull(mCacheUtils2.getString("string2"));
        Assert.assertNotNull(mCacheUtils2.getJSONObject("jsonObject2"));
        Assert.assertNotNull(mCacheUtils2.getJSONArray("jsonArray2"));
        Assert.assertNotNull(mCacheUtils2.getString("bitmap2"));
        Assert.assertNotNull(mCacheUtils2.getString("drawable2"));
        Assert.assertNotNull(mCacheUtils2.getParcelable("parcelable2", ParcelableTest.CREATOR));
        Assert.assertNotNull(mCacheUtils2.getSerializable("serializable2"));
        mCacheUtils2.clear();
        Assert.assertNull(mCacheUtils2.getBytes("bytes2"));
        Assert.assertNull(mCacheUtils2.getString("string2"));
        Assert.assertNull(mCacheUtils2.getJSONObject("jsonObject2"));
        Assert.assertNull(mCacheUtils2.getJSONArray("jsonArray2"));
        Assert.assertNull(mCacheUtils2.getString("bitmap2"));
        Assert.assertNull(mCacheUtils2.getString("drawable2"));
        Assert.assertNull(mCacheUtils2.getParcelable("parcelable2", ParcelableTest.CREATOR));
        Assert.assertNull(mCacheUtils2.getSerializable("serializable2"));
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
        private static final long serialVersionUID = -8021039743766780051L;
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
            return obj instanceof SerializableTest && ((SerializableTest) obj).author.equals(author) && ((SerializableTest) obj).className.equals(className);
        }
    }
}

