package com.blankj.subutil.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/21
 *     desc  : 单元测试工具类
 * </pre>
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class TestUtils {

    public static void init() {
        Utils.init(RuntimeEnvironment.application);
    }

    @Test
    public void test() throws Exception {

    }
}