package com.blankj.utilcode.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/22
 *     desc  : SPUtils单元测试
 * </pre>
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class SPUtilsTest {

    static {
        TestUtils.init();
    }
    private SPUtils spUtils1;

    private SPUtils spUtils2;

    @Before
    public void setUp() throws Exception {
        if (spUtils1 == null) {
            spUtils1 = SPUtils.getInstance("test1");
            spUtils1.put("stringKey1", "stringVal1");
            spUtils1.put("intKey1", 1);
            spUtils1.put("longKey1", 1L);
            spUtils1.put("floatKey1", 1f);
            spUtils1.put("booleanKey1", true);
        }
        if (spUtils2 == null) {
            spUtils2 = SPUtils.getInstance("test2");
            spUtils2.put("stringKey2", "stringVal2");
            spUtils2.put("intKey2", 2);
            spUtils2.put("longKey2", 2L);
            spUtils2.put("floatKey2", 2f);
            spUtils2.put("booleanKey2", true);
        }
    }

    @Test
    public void getString() throws Exception {
        assertEquals("stringVal1", spUtils1.getString("stringKey1"));
        assertEquals("stringVal", spUtils1.getString("stringKey", "stringVal"));
        assertEquals("", spUtils1.getString("stringKey"));

        assertEquals("stringVal2", spUtils2.getString("stringKey2"));
        assertEquals("stringVal", spUtils2.getString("stringKey", "stringVal"));
        assertEquals("", spUtils2.getString("stringKey"));
    }

    @Test
    public void getInt() throws Exception {
        assertEquals(1, spUtils1.getInt("intKey1"));
        assertEquals(2048, spUtils1.getInt("intKey", 2048));
        assertEquals(-1, spUtils1.getInt("intKey"));

        assertEquals(2, spUtils2.getInt("intKey2"));
        assertEquals(2048, spUtils2.getInt("intKey", 2048));
        assertEquals(-1, spUtils2.getInt("intKey"));
    }

    @Test
    public void getLong() throws Exception {
        assertEquals(1L, spUtils1.getLong("longKey1"));
        assertEquals(2048L, spUtils1.getLong("longKey", 2048));
        assertEquals(-1L, spUtils1.getLong("longKey"));

        assertEquals(2L, spUtils2.getLong("longKey2"));
        assertEquals(2048L, spUtils2.getLong("longKey", 2048));
        assertEquals(-1L, spUtils2.getLong("longKey"));
    }

    @Test
    public void getFloat() throws Exception {
        assertEquals(1f, spUtils1.getFloat("floatKey1"), 0f);
        assertEquals(2048f, spUtils1.getFloat("floatKey", 2048f), 0f);
        assertEquals(-1f, spUtils1.getFloat("floatKey"), 0f);

        assertEquals(2f, spUtils2.getFloat("floatKey2"), 0f);
        assertEquals(2048f, spUtils2.getFloat("floatKey", 2048f), 0f);
        assertEquals(-1f, spUtils2.getFloat("floatKey"), 0f);
    }

    @Test
    public void getBoolean() throws Exception {
        assertTrue(spUtils1.getBoolean("booleanKey1"));
        assertTrue(spUtils1.getBoolean("booleanKey", true));
        assertFalse(spUtils1.getBoolean("booleanKey"));

        assertTrue(spUtils2.getBoolean("booleanKey2"));
        assertTrue(spUtils2.getBoolean("booleanKey", true));
        assertFalse(spUtils2.getBoolean("booleanKey"));
    }

    @Test
    public void getAll() throws Exception {
        Map<String, ?> map;

        System.out.println("sp1 {");
        map = spUtils1.getAll();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            System.out.println("    " + entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("}");
        System.out.println("sp2 {");
        map = spUtils2.getAll();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            System.out.println("    " + entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("}");
    }

    @Test
    public void remove() throws Exception {
        assertEquals("stringVal1", spUtils1.getString("stringKey1"));
        spUtils1.remove("stringKey1");
        assertEquals("", spUtils1.getString("stringKey1"));

        assertEquals("stringVal2", spUtils2.getString("stringKey2"));
        spUtils2.remove("stringKey2");
        assertEquals("", spUtils2.getString("stringKey2"));
    }

    @Test
    public void contains() throws Exception {
        assertTrue(spUtils1.contains("stringKey1"));
        assertFalse(spUtils1.contains("stringKey"));

        assertTrue(spUtils2.contains("stringKey2"));
        assertFalse(spUtils2.contains("stringKey"));
    }

    @Test
    public void clear() throws Exception {
        spUtils1.clear();
        assertEquals("", spUtils1.getString("stringKey1"));
        assertEquals(-1, spUtils1.getInt("intKey1"));
        assertEquals(-1L, spUtils1.getLong("longKey1"));
        assertEquals(-1f, spUtils1.getFloat("floatKey1"), 0f);
        assertFalse(spUtils1.getBoolean("booleanKey1"));

        spUtils2.clear();
        assertEquals("", spUtils2.getString("stringKey2"));
        assertEquals(-1, spUtils2.getInt("intKey2"));
        assertEquals(-1L, spUtils2.getLong("longKey1"));
        assertEquals(-1f, spUtils2.getFloat("floatKey1"), 0f);
        assertFalse(spUtils2.getBoolean("booleanKey1"));
    }

    @After
    public void tearDown() throws Exception {
        spUtils1.clear();
        spUtils2.clear();
    }
}