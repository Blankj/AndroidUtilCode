package com.blankj.utilcode.utils;

import org.junit.Test;

import java.io.File;
import java.util.List;

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

    String zipPath = BASEPATH + "zip" + SEP + "testZip" + SEP;
    String zipFiles = BASEPATH + "zip" + SEP + "ZipFiles.zip";
    String zipFile = BASEPATH + "zip" + SEP + "ZipFile.zip";

    @Test
    public void testZipFiles() throws Exception {
        List<File> files = FileUtils.listFilesInDir(zipPath, false);
        ZipUtils.zipFiles(files, new File(zipFiles));
    }

    @Test
    public void testZipFile() throws Exception {
        ZipUtils.zipFile(new File(zipPath), new File(zipFile), "测试zip");
    }

    @Test
    public void testUpZipFile() throws Exception {

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