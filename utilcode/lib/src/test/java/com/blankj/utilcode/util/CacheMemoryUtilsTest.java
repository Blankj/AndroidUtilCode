package com.blankj.utilcode.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/06/13
 *     desc  : test CacheMemoryUtils
 * </pre>
 */
public class CacheMemoryUtilsTest extends BaseTest {

    private CacheMemoryUtils mCacheMemoryUtils1 = CacheMemoryUtils.getInstance();
    private CacheMemoryUtils mCacheMemoryUtils2 = CacheMemoryUtils.getInstance(3);

    @Before
    public void setUp() {
        for (int i = 0; i < 10; i++) {
            mCacheMemoryUtils1.put(String.valueOf(i), i);
        }
        for (int i = 0; i < 10; i++) {
            mCacheMemoryUtils2.put(String.valueOf(i), i);
        }
    }

    @Test
    public void get() {
        for (int i = 0; i < 10; i++) {
            assertEquals(i, mCacheMemoryUtils1.get(String.valueOf(i)));
        }
        for (int i = 0; i < 10; i++) {
            if (i < 7) {
                assertNull(mCacheMemoryUtils2.get(String.valueOf(i)));
            } else {
                assertEquals(i, mCacheMemoryUtils2.get(String.valueOf(i)));
            }
        }
    }

    @Test
    public void getExpired() throws Exception {
        mCacheMemoryUtils1.put("10", 10, 2 * CacheMemoryUtils.SEC);

        assertEquals(10, mCacheMemoryUtils1.get("10"));
        Thread.sleep(1500);
        assertEquals(10, mCacheMemoryUtils1.get("10"));
        Thread.sleep(1500);
        assertNull(mCacheMemoryUtils1.get("10"));
    }

    @Test
    public void getDefault() {
        assertNull(mCacheMemoryUtils1.get("10"));
        assertEquals("10", mCacheMemoryUtils1.get("10", "10"));
    }

    @Test
    public void getCacheCount() {
        assertEquals(10, mCacheMemoryUtils1.getCacheCount());
        assertEquals(3, mCacheMemoryUtils2.getCacheCount());
    }

    @Test
    public void remove() {
        assertEquals(0, mCacheMemoryUtils1.remove("0"));
        assertNull(mCacheMemoryUtils1.get("0"));
        assertNull(mCacheMemoryUtils1.remove("0"));
    }

    @Test
    public void clear() {
        mCacheMemoryUtils1.clear();
        mCacheMemoryUtils2.clear();

        for (int i = 0; i < 10; i++) {
            assertNull(mCacheMemoryUtils1.get(String.valueOf(i)));
        }
        for (int i = 0; i < 10; i++) {
            assertNull(mCacheMemoryUtils2.get(String.valueOf(i)));
        }
        assertEquals(0, mCacheMemoryUtils1.getCacheCount());
        assertEquals(0, mCacheMemoryUtils2.getCacheCount());
    }
}