package com.blankj.utilcode.utils;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.blankj.utilcode.utils.TestUtils.BASEPATH;
import static com.blankj.utilcode.utils.TestUtils.SEP;
import static com.google.common.truth.Truth.*;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/9/10
 *     desc  : ZipUtils单元测试
 * </pre>
 */
public class ZipUtilsTest {

    String testZip = BASEPATH + "zip" + SEP + "testZip" + SEP;
    String testZipFiles = BASEPATH + "zip" + SEP + "testZips.zip";
    String testZipFile = BASEPATH + "zip" + SEP + "testZip.zip";
    String unzipFile = BASEPATH + "zip" + SEP + "testUnzip";

    @Test
    public void testZipFiles() throws Exception {
        List<File> files = FileUtils.listFilesInDir(testZip, false);
        FileUtils.createOrExistsFile(testZipFiles);
        ZipUtils.zipFiles(files, testZipFiles);
    }

    @Test
    public void testZipFile() throws Exception {
        ZipUtils.zipFile(testZip, testZipFile, "测试zip");
    }

    @Test
    public void testUpZipFile() throws Exception {
        assertThat(ZipUtils.unzipFile(testZipFile, unzipFile)).isTrue();
        assertThat(ZipUtils.unzipFile(testZipFiles, unzipFile)).isTrue();
    }

    @Test
    public void testUpZipFiles() throws Exception {
        List<File> files = new ArrayList<>();
        files.add(new File(testZipFile));
        files.add(new File(testZipFiles));
        assertThat(ZipUtils.unzipFiles(files, unzipFile)).isTrue();
    }

    @Test
    public void testUnzipFileByKeyword() throws Exception {
        System.out.println((ZipUtils.unzipFileByKeyword(testZipFile, unzipFile, ".txt")).toString());
    }

    @Test
    public void testGetFileNamesInZip() throws Exception {
        System.out.println(ZipUtils.getFilePathInZip(testZipFile));
    }

    @Test
    public void testGetComments() throws Exception {
        System.out.println(ZipUtils.getComments(testZipFile));
    }
}