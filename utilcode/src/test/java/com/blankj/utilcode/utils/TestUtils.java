package com.blankj.utilcode.utils;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.io.File;

import static com.blankj.utilcode.utils.TimeUtils.milliseconds2String;
import static com.google.common.truth.Truth.assertThat;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/21
 *     desc  : 单元测试工具类
 * </pre>
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class TestUtils {

    public static final char SEP = File.separatorChar;

    public static final String BASEPATH = System.getProperty("user.dir")
            + SEP + "src" + SEP + "test" + SEP + "res" + SEP;

    public static Context getContext() {
        return RuntimeEnvironment.application;
    }

    @Test
    public void test() throws Exception {

    }
}
