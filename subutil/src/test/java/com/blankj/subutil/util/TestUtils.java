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

    static final String FILE_SEP = System.getProperty("file.separator");

    static final String LINE_SEP = System.getProperty("line.separator");

    static final String TEST_PATH;

    static {
        String projectPath = System.getProperty("user.dir");
        if (!projectPath.contains("utilcode")) {
            projectPath += FILE_SEP + "subutil";
        }
        TEST_PATH = projectPath + FILE_SEP + "src" + FILE_SEP + "test" + FILE_SEP + "res";
    }

    public static void init() {
        Utils.init(RuntimeEnvironment.application);
    }

    @Test
    public void test() throws Exception {

    }
}