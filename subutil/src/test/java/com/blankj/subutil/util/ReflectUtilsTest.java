package com.blankj.subutil.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/08/07
 *     desc  : ReflectUtils单元测试
 * </pre>
 */
public class ReflectUtilsTest {

    @Test
    public void init() throws Exception {
        Assert.assertEquals(ReflectUtils.init(Object.class), ReflectUtils.init("java.lang.Object", ClassLoader.getSystemClassLoader()));
        Assert.assertEquals(ReflectUtils.init(Object.class), ReflectUtils.init("java.lang.Object"));
    }


}