package com.blankj.utilcode.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.blankj.utilcode.util.TestConfig.PATH_TEMP;
import static com.blankj.utilcode.util.TestConfig.PATH_ZIP;
import static org.junit.Assert.assertTrue;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/10
 *     desc  : ZipUtils单元测试
 * </pre>
 */
public class ZipUtilsTest {

    private String zipFile = PATH_TEMP + "zipFile.zip";

    @Before
    public void setUp() throws Exception {
        FileUtils.createOrExistsDir(PATH_TEMP);
        assertTrue(ZipUtils.zipFile(PATH_ZIP, zipFile, "测试zip"));
    }

    @Test
    public void unzipFile() throws Exception {
        System.out.println(ZipUtils.unzipFile(zipFile, PATH_TEMP));
    }

    @Test
    public void unzipFileByKeyword() throws Exception {
        System.out.println((ZipUtils.unzipFileByKeyword(zipFile, PATH_TEMP, null)).toString());
    }

    @Test
    public void getFilesPath() throws Exception {
        System.out.println(ZipUtils.getFilesPath(zipFile));
    }

    @Test
    public void getComments() throws Exception {
        System.out.println(ZipUtils.getComments(zipFile));
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.deleteAllInDir(PATH_TEMP);
    }
}