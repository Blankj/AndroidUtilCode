package com.blankj.utilcode.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
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
@Config(sdk = 21)
public class SPUtilsTest {



    @Before
    public void setUp() throws Exception {

        if (Utils.getContext()==null)Utils.init(RuntimeEnvironment.application);

            SPUtils.init(Utils.getContext(),"test")
            .put("stringKey", "stringVal")
            .put("intKey", 1)
            .put("longKey", 1L)
            .put("floatKey", 1f)
            .put("booleanKey", true);

    }

    @Test
    public void testGetString() throws Exception {
        assertThat(SPUtils.getInstant().getString("stringKey")).isEqualTo("stringVal");
        assertThat(SPUtils.getInstant().getString("stringKey1", "stringVal1")).isEqualTo("stringVal1");
        assertThat(SPUtils.getInstant().getString("stringKey1")).isNull();
    }

    @Test
    public void testGetInt() throws Exception {
        assertThat(SPUtils.getInstant().getInt("intKey")).isEqualTo(1);
        assertThat(SPUtils.getInstant().getInt("intKey1", 10086)).isEqualTo(10086);
        assertThat(SPUtils.getInstant().getInt("intKey1")).isEqualTo(-1);
    }

    @Test
    public void testGetLong() throws Exception {
        assertThat(SPUtils.getInstant().getLong("longKey")).isEqualTo(1L);
        assertThat(SPUtils.getInstant().getLong("longKey1", 10086L)).isEqualTo(10086L);
        assertThat(SPUtils.getInstant().getLong("longKey1")).isEqualTo(-1L);
    }

    @Test
    public void testGetFloat() throws Exception {
        assertThat(SPUtils.getInstant().getFloat("floatKey") - 1.f).isWithin(0.f);
        assertThat(SPUtils.getInstant().getFloat("floatKey1", 10086f) - 10086f).isWithin(0.f);
        assertThat(SPUtils.getInstant().getFloat("floatKey1") + 1.f).isWithin(0.f);
    }

    @Test
    public void testGetBoolean() throws Exception {
        assertThat(SPUtils.getInstant().getBoolean("booleanKey")).isTrue();
        assertThat(SPUtils.getInstant().getBoolean("booleanKey1", true)).isTrue();
        assertThat(SPUtils.getInstant().getBoolean("booleanKey1")).isFalse();
    }

    @Test
    public void testGetAll() throws Exception {
        Map<String, ?> map = SPUtils.getInstant().getAll();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
    }

    @Test
    public void testRemove() throws Exception {
        SPUtils.getInstant().remove("stringKey");
        testGetAll();
    }

    @Test
    public void testContains() throws Exception {
        assertThat(SPUtils.getInstant().contains("stringKey")).isTrue();
        assertThat(SPUtils.getInstant().contains("string")).isFalse();
    }

    @Test
    public void testClear() throws Exception {
        SPUtils.getInstant().clear();
        testGetAll();
    }
}