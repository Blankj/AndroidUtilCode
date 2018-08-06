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
 *     time  : 2018/06/13
 *     desc  :
 * </pre>
 */
public class CacheDoubleUtilsTest extends BaseTest {

    private CacheDiskUtils   mCacheDiskUtils;
    private CacheMemoryUtils mCacheMemoryUtils;
    private CacheDoubleUtils mCacheDoubleUtils;
    private final String           cachePath         = PATH_CACHE + "double" + FILE_SEP;
    private final File             cacheFile         = new File(cachePath);
    private final byte[]           mBytes            = "CacheDoubleUtils".getBytes();
    private final String           mString           = "CacheDoubleUtils";
    private final JSONObject       mJSONObject       = new JSONObject();
    private final JSONArray        mJSONArray        = new JSONArray();
    private final ParcelableTest   mParcelableTest   = new ParcelableTest("Blankj", "CacheDoubleUtils");
    private final SerializableTest mSerializableTest = new SerializableTest("Blankj", "CacheDoubleUtils");
    private final Bitmap           mBitmap           = Bitmap.createBitmap(100, 100, Bitmap.Config.RGB_565);
    private final Drawable         mDrawable         = new BitmapDrawable(Utils.getApp().getResources(), mBitmap);


    @Before
    public void setUp() throws Exception {
        mCacheMemoryUtils = CacheMemoryUtils.getInstance();
        mCacheDiskUtils = CacheDiskUtils.getInstance(cacheFile);
        mCacheDoubleUtils = CacheDoubleUtils.getInstance(mCacheMemoryUtils, mCacheDiskUtils);

        mJSONObject.put("class", "CacheDoubleUtils");
        mJSONObject.put("author", "Blankj");
        mJSONArray.put(0, mJSONObject);

        mCacheDoubleUtils.put("bytes", mBytes);
        mCacheDoubleUtils.put("string", mString);
        mCacheDoubleUtils.put("jsonObject", mJSONObject);
        mCacheDoubleUtils.put("jsonArray", mJSONArray);
        mCacheDoubleUtils.put("bitmap", mBitmap);
        mCacheDoubleUtils.put("drawable", mDrawable);
        mCacheDoubleUtils.put("parcelable", mParcelableTest);
        mCacheDoubleUtils.put("serializable", mSerializableTest);
    }

    @Test
    public void getBytes() {
        assertEquals(mString, new String(mCacheDoubleUtils.getBytes("bytes")));
        mCacheMemoryUtils.remove("bytes");
        assertEquals(mString, new String(mCacheDoubleUtils.getBytes("bytes")));
        mCacheDiskUtils.remove("bytes");
        assertNull(mCacheDoubleUtils.getBytes("bytes"));
    }

    @Test
    public void getString() {
        assertEquals(mString, mCacheDoubleUtils.getString("string"));
        mCacheMemoryUtils.remove("string");
        assertEquals(mString, mCacheDoubleUtils.getString("string"));
        mCacheDiskUtils.remove("string");
        assertNull(mCacheDoubleUtils.getString("string"));
    }

    @Test
    public void getJSONObject() {
        assertEquals(mJSONObject.toString(), mCacheDoubleUtils.getJSONObject("jsonObject").toString());
        mCacheMemoryUtils.remove("jsonObject");
        assertEquals(mJSONObject.toString(), mCacheDoubleUtils.getJSONObject("jsonObject").toString());
        mCacheDiskUtils.remove("jsonObject");
        assertNull(mCacheDoubleUtils.getString("jsonObject"));
    }

    @Test
    public void getJSONArray() {
        assertEquals(mJSONArray.toString(), mCacheDoubleUtils.getJSONArray("jsonArray").toString());
        mCacheMemoryUtils.remove("jsonArray");
        assertEquals(mJSONArray.toString(), mCacheDoubleUtils.getJSONArray("jsonArray").toString());
        mCacheDiskUtils.remove("jsonArray");
        assertNull(mCacheDoubleUtils.getString("jsonArray"));
    }

    @Test
    public void getBitmap() {
        String bitmapString = "Bitmap (100 x 100) compressed as PNG with quality 100";
        assertEquals(mBitmap, mCacheDoubleUtils.getBitmap("bitmap"));
        mCacheMemoryUtils.remove("bitmap");
        assertEquals(bitmapString, mCacheDoubleUtils.getString("bitmap"));
        mCacheDiskUtils.remove("bitmap");
        assertNull(mCacheDoubleUtils.getString("bitmap"));
    }

    @Test
    public void getDrawable() {
        String bitmapString = "Bitmap (100 x 100) compressed as PNG with quality 100";
        assertEquals(mDrawable, mCacheDoubleUtils.getDrawable("drawable"));
        mCacheMemoryUtils.remove("drawable");
        assertEquals(bitmapString, mCacheDoubleUtils.getString("drawable"));
        mCacheDiskUtils.remove("drawable");
        assertNull(mCacheDoubleUtils.getString("drawable"));
    }

    @Test
    public void getParcel() {
        assertEquals(mParcelableTest, mCacheDoubleUtils.getParcelable("parcelable", ParcelableTest.CREATOR));
        mCacheMemoryUtils.remove("parcelable");
        assertEquals(mParcelableTest, mCacheDoubleUtils.getParcelable("parcelable", ParcelableTest.CREATOR));
        mCacheDiskUtils.remove("parcelable");
        assertNull(mCacheDoubleUtils.getString("parcelable"));
    }

    @Test
    public void getSerializable() {
        assertEquals(mSerializableTest, mCacheDoubleUtils.getSerializable("serializable"));
        mCacheMemoryUtils.remove("serializable");
        assertEquals(mSerializableTest, mCacheDoubleUtils.getSerializable("serializable"));
        mCacheDiskUtils.remove("serializable");
        assertNull(mCacheDoubleUtils.getString("serializable"));
    }

    @Test
    public void getCacheDiskSize() {
        assertEquals(FileUtils.getDirLength(cacheFile), mCacheDoubleUtils.getCacheDiskSize());
    }

    @Test
    public void getCacheDiskCount() {
        assertEquals(8, mCacheDoubleUtils.getCacheDiskCount());
    }

    @Test
    public void getCacheMemoryCount() {
        assertEquals(8, mCacheDoubleUtils.getCacheMemoryCount());
    }

    @Test
    public void remove() {
        assertNotNull(mCacheDoubleUtils.getString("string"));
        mCacheDoubleUtils.remove("string");
        assertNull(mCacheDoubleUtils.getString("string"));
    }

    @Test
    public void clear() {
        assertNotNull(mCacheDoubleUtils.getBytes("bytes"));
        assertNotNull(mCacheDoubleUtils.getString("string"));
        assertNotNull(mCacheDoubleUtils.getJSONObject("jsonObject"));
        assertNotNull(mCacheDoubleUtils.getJSONArray("jsonArray"));
        assertNotNull(mCacheDoubleUtils.getBitmap("bitmap"));
        assertNotNull(mCacheDoubleUtils.getDrawable("drawable"));
        assertNotNull(mCacheDoubleUtils.getParcelable("parcelable", ParcelableTest.CREATOR));
        assertNotNull(mCacheDoubleUtils.getSerializable("serializable"));
        mCacheDoubleUtils.clear();
        assertNull(mCacheDoubleUtils.getBytes("bytes"));
        assertNull(mCacheDoubleUtils.getString("string"));
        assertNull(mCacheDoubleUtils.getJSONObject("jsonObject"));
        assertNull(mCacheDoubleUtils.getJSONArray("jsonArray"));
        assertNull(mCacheDoubleUtils.getBitmap("bitmap"));
        assertNull(mCacheDoubleUtils.getDrawable("drawable"));
        assertNull(mCacheDoubleUtils.getParcelable("parcelable", ParcelableTest.CREATOR));
        assertNull(mCacheDoubleUtils.getSerializable("serializable"));
        assertEquals(0, mCacheDoubleUtils.getCacheDiskSize());
        assertEquals(0, mCacheDoubleUtils.getCacheDiskCount());
        assertEquals(0, mCacheDoubleUtils.getCacheMemoryCount());
    }

    @After
    public void tearDown() {
        mCacheDoubleUtils.clear();
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