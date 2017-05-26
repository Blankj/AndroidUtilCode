package com.blankj.utilcode.util;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.File;
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


    private CacheUtils cacheUtils1;
    private CacheUtils cacheUtils2;

    byte[]     bytes1      = "cacheUtils1".getBytes();
    String     string1     = "cacheUtils1";
    JSONObject jsonObject1 = new JSONObject(new LinkedHashMap() {
    });

    @Before
    public void setUp() throws Exception {
        TestUtils.init();
        if (cacheUtils1 == null) {
            cacheUtils1 = CacheUtils.getInstance(file1);
            cacheUtils1.put("byteKey1", "cache", 100);
        }
        if (cacheUtils2 == null) {
            cacheUtils2 = CacheUtils.getInstance(file2);
            cacheUtils1.put("byteKey1", "cache", 3600);
        }
    }

    @Test
    public void getBytes() throws Exception {
        Assert.assertEquals("cache", new String(cacheUtils1.getBytes("byteKey1")));
        cacheUtils1.remove("byteKey1");
    }

    @Test
    public void getString() throws Exception {

    }

    @Test
    public void getJSONObject() throws Exception {

    }

    @Test
    public void getJSONArray() throws Exception {

    }

    @Test
    public void getBitmap() throws Exception {

    }

    @Test
    public void getDrawable() throws Exception {

    }

    @Test
    public void getParcel() throws Exception {

    }

    @Test
    public void getObject() throws Exception {

    }

    @Test
    public void getCacheFile() throws Exception {

    }

    @Test
    public void remove() throws Exception {

    }

    @Test
    public void clear() throws Exception {

    }

}