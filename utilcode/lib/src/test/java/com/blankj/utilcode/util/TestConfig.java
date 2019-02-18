package com.blankj.utilcode.util;

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
        if (!projectPath.contains("utilcode")) {
            projectPath += FILE_SEP + "utilcode";
        }
        TEST_PATH = projectPath + FILE_SEP + "src" + FILE_SEP + "test" + FILE_SEP + "res" + FILE_SEP;
    }

    static final String PATH_TEMP = TEST_PATH + "temp" + FILE_SEP;

    static final String PATH_CACHE = TEST_PATH + "cache" + FILE_SEP;

    static final String PATH_ENCRYPT = TEST_PATH + "encrypt" + FILE_SEP;

    static final String PATH_FILE = TEST_PATH + "file" + FILE_SEP;

    static final String PATH_ZIP = TEST_PATH + "zip" + FILE_SEP;

    public static final String PATH_HTTP = TEST_PATH + "http" + FILE_SEP;
}
