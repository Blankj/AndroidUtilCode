package com.blankj.utilcode.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Map;

import static com.google.common.truth.Truth.assertThat;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/22
 *     desc  : SPUtils单元测试
 * </pre>
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class SPUtilsTest {

    private SPUtils spUtils;

    @Before
    public void setUp() throws Exception {
        if (Utils.getContext() == null) TestUtils.init();
        if (spUtils == null) {
            spUtils = new SPUtils("test");
            spUtils.put("stringKey", "stringVal");
            spUtils.put("intKey", 1);
            spUtils.put("longKey", 1L);
            spUtils.put("floatKey", 1f);
            spUtils.put("booleanKey", true);
        }
    }

    @Test
    public void testGetString() throws Exception {
        assertThat(spUtils.getString("stringKey")).isEqualTo("stringVal");
        assertThat(spUtils.getString("stringKey1", "stringVal1")).isEqualTo("stringVal1");
        assertThat(spUtils.getString("stringKey1")).isNull();
    }

    @Test
    public void testGetInt() throws Exception {
        assertThat(spUtils.getInt("intKey")).isEqualTo(1);
        assertThat(spUtils.getInt("intKey1", 10086)).isEqualTo(10086);
        assertThat(spUtils.getInt("intKey1")).isEqualTo(-1);
    }

    @Test
    public void testGetLong() throws Exception {
        assertThat(spUtils.getLong("longKey")).isEqualTo(1L);
        assertThat(spUtils.getLong("longKey1", 10086L)).isEqualTo(10086L);
        assertThat(spUtils.getLong("longKey1")).isEqualTo(-1L);
    }

    @Test
    public void testGetFloat() throws Exception {
        assertThat(spUtils.getFloat("floatKey") - 1.f).isWithin(0.f);
        assertThat(spUtils.getFloat("floatKey1", 10086f) - 10086f).isWithin(0.f);
        assertThat(spUtils.getFloat("floatKey1") + 1.f).isWithin(0.f);
    }

    @Test
    public void testGetBoolean() throws Exception {
        assertThat(spUtils.getBoolean("booleanKey")).isTrue();
        assertThat(spUtils.getBoolean("booleanKey1", true)).isTrue();
        assertThat(spUtils.getBoolean("booleanKey1")).isFalse();
    }

    @Test
    public void testGetAll() throws Exception {
        Map<String, ?> map = spUtils.getAll();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
    }

    @Test
    public void testRemove() throws Exception {
        spUtils.remove("stringKey");
        testGetAll();
    }

    @Test
    public void testContains() throws Exception {
        assertThat(spUtils.contains("stringKey")).isTrue();
        assertThat(spUtils.contains("string")).isFalse();
    }

    @Test
    public void testClear() throws Exception {
        spUtils.clear();
        testGetAll();
    }
}