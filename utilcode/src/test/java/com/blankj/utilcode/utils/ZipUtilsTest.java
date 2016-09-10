package com.blankj.utilcode.utils;

import org.junit.Test;

import java.io.File;

import static com.blankj.utilcode.utils.TestUtils.BASEPATH;
import static com.blankj.utilcode.utils.TestUtils.SEP;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/9/10
 *     desc  : ZipUtils单元测试
 * </pre>
 */
public class ZipUtilsTest {

    String path = BASEPATH + "zip" + SEP;

    @Test
    public void testZipFiles() throws Exception {

    }

    @Test
    public void testZipFiles1() throws Exception {

    }

    @Test
    public void testUpZipFile() throws Exception {
        System.out.println(FileUtils.listFilesInDir(path));
    }

    @Test
    public void testUpZipSelectedFile() throws Exception {

    }

    @Test
    public void testGetEntriesNames() throws Exception {

    }

    @Test
    public void testGetEntriesEnumeration() throws Exception {

    }

    @Test
    public void testGetEntryComment() throws Exception {

    }

    @Test
    public void testGetEntryName() throws Exception {

    }
}