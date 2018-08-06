package com.blankj.utilcode.util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
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

    private final String           disk1Path         = PATH_CACHE + "disk1" + FILE_SEP;
    private final String           disk2Path         = PATH_CACHE + "disk2" + FILE_SEP;
    private final File             disk1File         = new File(disk1Path);
    private final File             disk2File         = new File(disk2Path);
    private final CacheDiskUtils   mCacheDiskUtils1  = CacheDiskUtils.getInstance(disk1File);
    private final CacheDiskUtils   mCacheDiskUtils2  = CacheDiskUtils.getInstance(disk2File);
    private final byte[]           mBytes            = "CacheDiskUtils".getBytes();
    private final String           mString           = "CacheDiskUtils";
    private final JSONObject       mJSONObject       = new JSONObject();
    private final JSONArray        mJSONArray        = new JSONArray();
    private final ParcelableTest   mParcelableTest   = new ParcelableTest("Blankj", "CacheDiskUtils");
    private final SerializableTest mSerializableTest = new SerializableTest("Blankj", "CacheDiskUtils");
    private final Bitmap           mBitmap           = Bitmap.createBitmap(100, 100, Bitmap.Config.RGB_565);
    private final Drawable         mDrawable         = new BitmapDrawable(Utils.getApp().getResources(), mBitmap);

    @Before
    public void setUp() throws Exception {
        mJSONObject.put("class", "CacheDiskUtils");
        mJSONObject.put("author", "Blankj");
        mJSONArray.put(0, mJSONObject);

        mCacheDiskUtils1.put("bytes1", mBytes, 60 * CacheDiskUtils.SEC);
        mCacheDiskUtils1.put("string1", mString, 60 * CacheDiskUtils.MIN);
        mCacheDiskUtils1.put("jsonObject1", mJSONObject, 24 * CacheDiskUtils.HOUR);
        mCacheDiskUtils1.put("jsonArray1", mJSONArray, 365 * CacheDiskUtils.DAY);
        mCacheDiskUtils1.put("bitmap1", mBitmap, 60 * CacheDiskUtils.SEC);
        mCacheDiskUtils1.put("drawable1", mDrawable, 60 * CacheDiskUtils.SEC);
        mCacheDiskUtils1.put("parcelable1", mParcelableTest, 60 * CacheDiskUtils.SEC);
        mCacheDiskUtils1.put("serializable1", mSerializableTest, 60 * CacheDiskUtils.SEC);

        mCacheDiskUtils2.put("bytes2", mBytes);
        mCacheDiskUtils2.put("string2", mString);
        mCacheDiskUtils2.put("jsonObject2", mJSONObject);
        mCacheDiskUtils2.put("jsonArray2", mJSONArray);
        mCacheDiskUtils2.put("parcelable2", mParcelableTest);
        mCacheDiskUtils2.put("serializable2", mSerializableTest);
        mCacheDiskUtils2.put("bitmap2", mBitmap);
        mCacheDiskUtils2.put("drawable2", mDrawable);
    }

    @Test
    public void getBytes() {
        assertEquals(mString, new String(mCacheDiskUtils1.getBytes("bytes1")));
        assertEquals(mString, new String(mCacheDiskUtils1.getBytes("bytes1", null)));
        assertNull(mCacheDiskUtils1.getBytes("bytes2", null));

        assertEquals(mString, new String(mCacheDiskUtils2.getBytes("bytes2")));
        assertEquals(mString, new String(mCacheDiskUtils2.getBytes("bytes2", null)));
        assertNull(mCacheDiskUtils2.getBytes("bytes1", null));
    }

    @Test
    public void getString() {
        assertEquals(mString, mCacheDiskUtils1.getString("string1"));
        assertEquals(mString, mCacheDiskUtils1.getString("string1", null));
        assertNull(mCacheDiskUtils1.getString("string2", null));

        assertEquals(mString, mCacheDiskUtils2.getString("string2"));
        assertEquals(mString, mCacheDiskUtils2.getString("string2", null));
        assertNull(mCacheDiskUtils2.getString("string1", null));
    }

    @Test
    public void getJSONObject() {
        assertEquals(mJSONObject.toString(), mCacheDiskUtils1.getJSONObject("jsonObject1").toString());
        assertEquals(mJSONObject.toString(), mCacheDiskUtils1.getJSONObject("jsonObject1", null).toString());
        assertNull(mCacheDiskUtils1.getJSONObject("jsonObject2", null));

        assertEquals(mJSONObject.toString(), mCacheDiskUtils2.getJSONObject("jsonObject2").toString());
        assertEquals(mJSONObject.toString(), mCacheDiskUtils2.getJSONObject("jsonObject2", null).toString());
        assertNull(mCacheDiskUtils2.getJSONObject("jsonObject1", null));
    }

    @Test
    public void getJSONArray() {
        assertEquals(mJSONArray.toString(), mCacheDiskUtils1.getJSONArray("jsonArray1").toString());
        assertEquals(mJSONArray.toString(), mCacheDiskUtils1.getJSONArray("jsonArray1", null).toString());
        assertNull(mCacheDiskUtils1.getJSONArray("jsonArray2", null));


        assertEquals(mJSONArray.toString(), mCacheDiskUtils2.getJSONArray("jsonArray2").toString());
        assertEquals(mJSONArray.toString(), mCacheDiskUtils2.getJSONArray("jsonArray2", null).toString());
        assertNull(mCacheDiskUtils2.getJSONArray("jsonArray1", null));
    }

    @Test
    public void getBitmap() {
        String bitmapString = "Bitmap (100 x 100) compressed as PNG with quality 100";
        assertEquals(bitmapString, mCacheDiskUtils1.getString("bitmap1"));
        assertEquals(bitmapString, mCacheDiskUtils1.getString("bitmap1", null));
        assertNull(mCacheDiskUtils1.getString("bitmap2", null));

        assertEquals(bitmapString, mCacheDiskUtils2.getString("bitmap2"));
        assertEquals(bitmapString, mCacheDiskUtils2.getString("bitmap2", null));
        assertNull(mCacheDiskUtils2.getString("bitmap1", null));
    }

    @Test
    public void getDrawable() {
        String bitmapString = "Bitmap (100 x 100) compressed as PNG with quality 100";
        assertEquals(bitmapString, mCacheDiskUtils1.getString("drawable1"));
        assertEquals(bitmapString, mCacheDiskUtils1.getString("drawable1", null));
        assertNull(mCacheDiskUtils1.getString("drawable2", null));

        assertEquals(bitmapString, mCacheDiskUtils2.getString("drawable2"));
        assertEquals(bitmapString, mCacheDiskUtils2.getString("drawable2", null));
        assertNull(mCacheDiskUtils2.getString("drawable1", null));
    }

    @Test
    public void getParcel() {
        assertEquals(mParcelableTest, mCacheDiskUtils1.getParcelable("parcelable1", ParcelableTest.CREATOR));
        assertEquals(mParcelableTest, mCacheDiskUtils1.getParcelable("parcelable1", ParcelableTest.CREATOR, null));
        assertNull(mCacheDiskUtils1.getParcelable("parcelable2", ParcelableTest.CREATOR, null));

        assertEquals(mParcelableTest, mCacheDiskUtils2.getParcelable("parcelable2", ParcelableTest.CREATOR));
        assertEquals(mParcelableTest, mCacheDiskUtils2.getParcelable("parcelable2", ParcelableTest.CREATOR, null));
        assertNull(mCacheDiskUtils2.getParcelable("parcelable1", ParcelableTest.CREATOR, null));
    }

    @Test
    public void getSerializable() {
        assertEquals(mSerializableTest, mCacheDiskUtils1.getSerializable("serializable1"));
        assertEquals(mSerializableTest, mCacheDiskUtils1.getSerializable("serializable1", null));
        assertNull(mCacheDiskUtils1.getSerializable("parcelable2", null));

        assertEquals(mSerializableTest, mCacheDiskUtils2.getSerializable("serializable2"));
        assertEquals(mSerializableTest, mCacheDiskUtils2.getSerializable("serializable2", null));
        assertNull(mCacheDiskUtils2.getSerializable("parcelable1", null));
    }

    @Test
    public void getCacheSize() {
        assertEquals(FileUtils.getDirLength(disk1File), mCacheDiskUtils1.getCacheSize());

        assertEquals(FileUtils.getDirLength(disk2File), mCacheDiskUtils2.getCacheSize());
    }

    @Test
    public void getCacheCount() {
        assertEquals(8, mCacheDiskUtils1.getCacheCount());

        assertEquals(8, mCacheDiskUtils2.getCacheCount());
    }

    @Test
    public void remove() {
        assertNotNull(mCacheDiskUtils1.getString("string1"));
        mCacheDiskUtils1.remove("string1");
        assertNull(mCacheDiskUtils1.getString("string1"));

        assertNotNull(mCacheDiskUtils2.getString("string2"));
        mCacheDiskUtils2.remove("string2");
        assertNull(mCacheDiskUtils2.getString("string2"));
    }

    @Test
    public void clear() {
        assertNotNull(mCacheDiskUtils1.getBytes("bytes1"));
        assertNotNull(mCacheDiskUtils1.getString("string1"));
        assertNotNull(mCacheDiskUtils1.getJSONObject("jsonObject1"));
        assertNotNull(mCacheDiskUtils1.getJSONArray("jsonArray1"));
        assertNotNull(mCacheDiskUtils1.getString("bitmap1"));
        assertNotNull(mCacheDiskUtils1.getString("drawable1"));
        assertNotNull(mCacheDiskUtils1.getParcelable("parcelable1", ParcelableTest.CREATOR));
        assertNotNull(mCacheDiskUtils1.getSerializable("serializable1"));
        mCacheDiskUtils1.clear();
        assertNull(mCacheDiskUtils1.getBytes("bytes1"));
        assertNull(mCacheDiskUtils1.getString("string1"));
        assertNull(mCacheDiskUtils1.getJSONObject("jsonObject1"));
        assertNull(mCacheDiskUtils1.getJSONArray("jsonArray1"));
        assertNull(mCacheDiskUtils1.getString("bitmap1"));
        assertNull(mCacheDiskUtils1.getString("drawable1"));
        assertNull(mCacheDiskUtils1.getParcelable("parcelable1", ParcelableTest.CREATOR));
        assertNull(mCacheDiskUtils1.getSerializable("serializable1"));
        assertEquals(0, mCacheDiskUtils1.getCacheSize());
        assertEquals(0, mCacheDiskUtils1.getCacheCount());


        assertNotNull(mCacheDiskUtils2.getBytes("bytes2"));
        assertNotNull(mCacheDiskUtils2.getString("string2"));
        assertNotNull(mCacheDiskUtils2.getJSONObject("jsonObject2"));
        assertNotNull(mCacheDiskUtils2.getJSONArray("jsonArray2"));
        assertNotNull(mCacheDiskUtils2.getString("bitmap2"));
        assertNotNull(mCacheDiskUtils2.getString("drawable2"));
        assertNotNull(mCacheDiskUtils2.getParcelable("parcelable2", ParcelableTest.CREATOR));
        assertNotNull(mCacheDiskUtils2.getSerializable("serializable2"));
        mCacheDiskUtils2.clear();
        assertNull(mCacheDiskUtils2.getBytes("bytes2"));
        assertNull(mCacheDiskUtils2.getString("string2"));
        assertNull(mCacheDiskUtils2.getJSONObject("jsonObject2"));
        assertNull(mCacheDiskUtils2.getJSONArray("jsonArray2"));
        assertNull(mCacheDiskUtils2.getString("bitmap2"));
        assertNull(mCacheDiskUtils2.getString("drawable2"));
        assertNull(mCacheDiskUtils2.getParcelable("parcelable2", ParcelableTest.CREATOR));
        assertNull(mCacheDiskUtils2.getSerializable("serializable2"));
        assertEquals(0, mCacheDiskUtils2.getCacheSize());
        assertEquals(0, mCacheDiskUtils2.getCacheCount());
    }

    @After
    public void tearDown() {
        mCacheDiskUtils1.clear();
        mCacheDiskUtils2.clear();
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

