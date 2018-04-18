package com.blankj.subutil.util;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/09/10
 *     desc  : config of test
 * </pre>
 */
public class TestConfig {

    static final String FILE_SEP = System.getProperty("file.separator");

    static final String LINE_SEP = System.getProperty("line.separator");

    static final String TEST_PATH;

    static {
        String projectPath = System.getProperty("user.dir");
        if (!projectPath.contains("subutil")) {
            projectPath += FILE_SEP + "subutil";
        }
        TEST_PATH = projectPath + FILE_SEP + "src" + FILE_SEP + "test" + FILE_SEP + "res";
    }
}
