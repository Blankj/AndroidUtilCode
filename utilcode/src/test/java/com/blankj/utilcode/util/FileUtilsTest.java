package com.blankj.utilcode.util;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;

import static com.blankj.utilcode.util.FileUtils.*;
import static com.blankj.utilcode.util.TestUtils.BASEPATH;
import static com.blankj.utilcode.util.TestUtils.SEP;
import static com.google.common.truth.Truth.assertThat;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/19
 *     desc  : FileUtils单元测试
 * </pre>
 */
public class FileUtilsTest {


    String path = BASEPATH + "file" + SEP;

    @Test
    public void testGetFileByPath() throws Exception {
        System.out.println(new byte[0].length);
        assertThat(getFileByPath(" ")).isNull();
        assertThat(getFileByPath("c:")).isNotNull();
    }

    @Test
    public void testIsFileExists() throws Exception {
        assertThat(isFileExists(path + "UTF8.txt")).isTrue();
        assertThat(isFileExists(path + "UTF8")).isFalse();
    }

    @Test
    public void testRename() throws Exception {
        assertThat(rename(path + "GBK.txt", "GBK1.txt")).isTrue();
    }

    @Test
    public void testIsDir() throws Exception {
        assertThat(isDir(path + "UTF8.txt")).isFalse();
        assertThat(isDir(path)).isTrue();
    }

    @Test
    public void testIsFile() throws Exception {
        assertThat(isFile(path + "UTF8.txt")).isTrue();
        assertThat(isFile(path)).isFalse();
    }

    @Test
    public void testCreateOrExistsDir() throws Exception {
        assertThat(createOrExistsDir(path + "new Dir")).isTrue();
        assertThat(createOrExistsDir(path)).isTrue();
    }

    @Test
    public void testCreateOrExistsFile() throws Exception {
        assertThat(createOrExistsFile(path + "new File")).isTrue();
        assertThat(createOrExistsFile(path)).isFalse();
    }

    @Test
    public void testCreateFileByDeleteOldFile() throws Exception {
        assertThat(createFileByDeleteOldFile(path + "new File")).isTrue();
        assertThat(createFileByDeleteOldFile(path)).isFalse();
    }

    String path1 = BASEPATH + "file1" + SEP;

    @Test
    public void testCopyDir() throws Exception {
        assertThat(copyDir(path, path)).isFalse();
        assertThat(copyDir(path, path + "new Dir")).isFalse();
        assertThat(copyDir(path, path1)).isTrue();
    }

    @Test
    public void testCopyFile() throws Exception {
        assertThat(copyFile(path + "GBK.txt", path + "GBK.txt")).isFalse();
        assertThat(copyFile(path + "GBK.txt", path + "new Dir" + SEP + "GBK.txt")).isTrue();
        assertThat(copyFile(path + "GBK.txt", path1 + "GBK.txt")).isTrue();
    }

    @Test
    public void testMoveDir() throws Exception {
        assertThat(moveDir(path, path)).isFalse();
        assertThat(moveDir(path, path + "new Dir")).isFalse();
        assertThat(moveDir(path, path1)).isTrue();
        assertThat(moveDir(path1, path)).isTrue();
    }

    @Test
    public void testMoveFile() throws Exception {
        assertThat(moveFile(path + "GBK.txt", path + "GBK.txt")).isFalse();
        assertThat(moveFile(path + "GBK.txt", path1 + "GBK.txt")).isTrue();
        assertThat(moveFile(path1 + "GBK.txt", path + "GBK.txt")).isTrue();
    }

    @Test
    public void testDeleteDir() throws Exception {
        assertThat(deleteDir(path + "GBK.txt")).isFalse();
        assertThat(deleteDir(path + "del")).isTrue();
    }

    @Test
    public void testDeleteFile() throws Exception {
        assertThat(deleteFile(path)).isFalse();
        assertThat(deleteFile(path + "GBK1.txt")).isTrue();
        assertThat(deleteFile(path + "del.txt")).isTrue();
    }

    @Test
    public void testDeleteFilesInDir() throws Exception {
        assertThat(deleteFilesInDir(path + "child")).isTrue();
    }

    @Test
    public void testListFilesInDir() throws Exception {
        System.out.println(listFilesInDir(path, false).toString());
        System.out.println(listFilesInDir(path, true).toString());
    }

    FilenameFilter filter = new FilenameFilter() {
        public boolean accept(File dir, String name) {
            return name.endsWith("k.txt");
        }
    };

    @Test
    public void testListFilesInDirWithFiltere() throws Exception {
        System.out.println(listFilesInDirWithFilter(path, "k.txt", false).toString());
        System.out.println(listFilesInDirWithFilter(path, "k.txt", true).toString());
        System.out.println(listFilesInDirWithFilter(path, filter, false).toString());
        System.out.println(listFilesInDirWithFilter(path, filter, true).toString());
    }

    @Test
    public void testSearchFile() throws Exception {
        System.out.println(searchFileInDir(path, "GBK.txt").toString());
        System.out.println(searchFileInDir(path, "child").toString());
    }

    @Test
    public void testWriteFileFromIS() throws Exception {
        assertThat(writeFileFromIS(path + "NEW.txt", new FileInputStream(path + "UTF8.txt"), false))
                .isTrue();
        assertThat(writeFileFromIS(path + "NEW.txt", new FileInputStream(path + "UTF8.txt"), true))
                .isTrue();
    }

    @Test
    public void testWriteFileFromString() throws Exception {
        assertThat(writeFileFromString(path + "NEW.txt", "这是新的", false)).isTrue();
        assertThat(writeFileFromString(path + "NEW.txt", "\r\n这是追加的", true)).isTrue();
    }

    @Test
    public void testGetFileCharsetSimple() throws Exception {
        assertThat(getFileCharsetSimple(path + "GBK.txt")).isEqualTo("GBK");
        assertThat(getFileCharsetSimple(path + "Unicode.txt")).isEqualTo("Unicode");
        assertThat(getFileCharsetSimple(path + "UTF8.txt")).isEqualTo("UTF-8");
        assertThat(getFileCharsetSimple(path + "UTF16BE.txt")).isEqualTo("UTF-16BE");
    }

    @Test
    public void testGetFileLines() throws Exception {
        assertThat(getFileLines(path + "UTF8.txt")).isEqualTo(7);
    }

    @Test
    public void testGetFileLastModified()throws Exception{
        System.out.println(TimeUtils.millis2String(getFileLastModified(path)));
    }

    @Test
    public void testReadFile2List() throws Exception {
        System.out.println(readFile2List(path + "UTF8.txt", "").toString());
        System.out.println(readFile2List(path + "UTF8.txt", "UTF-8").toString());
        System.out.println(readFile2List(path + "UTF8.txt", 2, 5, "UTF-8").toString());
        System.out.println(readFile2List(path + "UTF8.txt", "GBK").toString());
    }

    @Test
    public void testReadFile2String() throws Exception {
        System.out.println(readFile2String(path + "UTF8.txt", ""));
        System.out.println(readFile2String(path + "UTF8.txt", "UTF-8"));
        System.out.println(readFile2String(path + "UTF8.txt", "GBK"));
    }

    @Test
    public void testReadFile2Bytes() throws Exception {
        System.out.println(new String(readFile2Bytes(path + "UTF8.txt")));
    }

    @Test
    public void testGetDirSize() throws Exception {
        assertThat(getDirSize(path)).isEqualTo("73.000B");
    }

    @Test
    public void testGetDirLength() throws Exception {
        assertThat(getDirLength(path)).isEqualTo(73);
    }

    @Test
    public void testGetFileSize() throws Exception {
        assertThat(getFileSize(path + "UTF8.txt")).isEqualTo("25.000B");
    }

    @Test
    public void testGetFileLength() throws Exception {
        assertThat(getFileLength(path + "UTF8.txt")).isEqualTo(25);
    }

    @Test
    public void testGetFileMD5ToString() throws Exception {
        assertThat(getFileMD5ToString(path + "UTF8.txt")).isEqualTo("249D3E76851DCC56C945994DE9DAC406");
    }

    @Test
    public void testGetDirName() throws Exception {
        assertThat(getDirName(new File(path + "UTF8.txt"))).isEqualTo(path);
        assertThat(getDirName(path + "UTF8.txt")).isEqualTo(path);
    }

    @Test
    public void testGetFileName() throws Exception {
        assertThat(getFileName(new File(path + "UTF8.txt"))).isEqualTo("UTF8.txt");
        assertThat(getFileName(path + "UTF8.txt")).isEqualTo("UTF8.txt");
    }

    @Test
    public void testGetFileNameNoExtension() throws Exception {
        assertThat(getFileNameNoExtension(new File(path + "UTF8.txt"))).isEqualTo("UTF8");
        assertThat(getFileNameNoExtension(path + "UTF8.txt")).isEqualTo("UTF8");
    }

    @Test
    public void testGetFileExtension() throws Exception {
        assertThat(getFileExtension(new File(path + "UTF8.txt"))).isEqualTo(".txt");
        assertThat(getFileExtension(path + "UTF8.txt")).isEqualTo(".txt");
    }
}