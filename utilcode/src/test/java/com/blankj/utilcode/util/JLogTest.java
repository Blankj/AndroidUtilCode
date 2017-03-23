package com.blankj.utilcode.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/9
 *     desc  : LogUtils单元测试
 * </pre>
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class JLogTest {

    @Before
    public void setUp() throws Exception {
        TestUtils.init();
        ShadowLog.stream = System.out;
        JLog.getBuilder().
                setLogSwitch(true).
                setLog2FileSwitch(false).
                setTag("Blankj").
                setLogFilter('v').
                create();
    }

    @Test
    public void v() throws Exception {
        JLog.v("My Tag","Verbose");
    }

    @Test
    public void d() throws Exception {
        JLog.d("Debug");
    }

    @Test
    public void i() throws Exception {
        JLog.i("Info");
    }

    @Test
    public void w() throws Exception {
        JLog.w("Warn");
    }

    @Test
    public void e() throws Exception {
        JLog.e("Error");
    }
}