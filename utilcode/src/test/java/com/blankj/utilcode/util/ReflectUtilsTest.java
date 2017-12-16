package com.blankj.utilcode.util;

import com.blankj.utilcode.util.reflect.PrivateConstructors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/12/15
 *     desc  :
 * </pre>
 */
public class ReflectUtilsTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void on() throws Exception {
        assertEquals(ReflectUtils.reflect(Object.class), ReflectUtils.reflect("java.lang.Object", ClassLoader.getSystemClassLoader()));
        assertEquals(ReflectUtils.reflect(Object.class), ReflectUtils.reflect("java.lang.Object"));
        assertEquals(ReflectUtils.reflect(String.class).get(), ReflectUtils.reflect("java.lang.String").get());
        assertEquals(Object.class, ReflectUtils.reflect(Object.class).get());
        assertEquals("abc", ReflectUtils.reflect((Object) "abc").get());
        assertEquals(1, (int) (Integer) ReflectUtils.reflect(1).get());
    }

    @Test
    public void create() throws Exception {
        assertEquals("", ReflectUtils.reflect(String.class).create().get());
        assertEquals("abc", ReflectUtils.reflect(String.class).create("abc").get());
        assertEquals("abc", ReflectUtils.reflect(String.class).create("abc".getBytes()).get());
        assertEquals("abc", ReflectUtils.reflect(String.class).create("abc".toCharArray()).get());
        assertEquals("b", ReflectUtils.reflect(String.class).create("abc".toCharArray(), 1, 1).get());

        /*private*/
        assertNull(ReflectUtils.reflect(PrivateConstructors.class).create().field("string").get());
        assertEquals("abc", ReflectUtils.reflect(PrivateConstructors.class).create("abc").field("string").get());
    }
}