package com.blankj.utilcode.utils;

import org.junit.Test;

import static com.blankj.utilcode.utils.FileUtils.*;
import static com.google.common.truth.Truth.assertThat;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/18
 *     desc  :
 * </pre>
 */
public class FileUtilsTest1 {

    public static String path = System.getProperty("user.dir") + "\\src\\test\\res\\";

    @Test
    public void testGetFileByPath() throws Exception {
        assertThat(getFileByPath(path + "GBK.txt")).isNotNull();
    }

    @Test
    public void testGetFileCharset() throws Exception {
//        createDir(path+"\\cmj");
        System.out.println(createFile(path + "cmj\\GBK.txt"));
        System.out.println(getFileCharsetSimple(path + "GBK.txt"));
        System.out.println(getFileCharsetSimple(path + "Unicode.txt"));
        System.out.println(getFileCharsetSimple(path + "UTF8.txt"));
        System.out.println(getFileCharsetSimple(path + "UTF16BE.txt"));
    }

    @Test
    public void testGetFileLines() throws Exception {
        System.out.println(getFileLines(path + "UTF8.txt"));
    }

    @Test
    public void testReadFileByLine() throws Exception {

    }

    @Test
    public void testReadFileByLine1() throws Exception {

    }

    @Test
    public void testReadFileByLine2() throws Exception {

    }

    @Test
    public void testCreateFile() throws Exception {

    }

    @Test
    public void testMoveFile() throws Exception {

    }

    @Test
    public void testMoveFile1() throws Exception {

    }

    @Test
    public void testCopyFile() throws Exception {

    }

    @Test
    public void testReadFile() throws Exception {

    }

    @Test
    public void testReadFile1() throws Exception {

    }

    @Test
    public void testWriteFile() throws Exception {

    }

    @Test
    public void testWriteFile1() throws Exception {

    }

    @Test
    public void testWriteFile2() throws Exception {

    }

    @Test
    public void testWriteFile3() throws Exception {

    }

    @Test
    public void testWriteFile4() throws Exception {

    }

    @Test
    public void testWriteFile5() throws Exception {

    }

    @Test
    public void testReadFileToList() throws Exception {

    }

    @Test
    public void testGetFolderName() throws Exception {

    }

    @Test
    public void testGetFileName() throws Exception {

    }

    @Test
    public void testGetFileNameWithoutExtension() throws Exception {

    }

    @Test
    public void testGetFileExtension() throws Exception {

    }

    @Test
    public void testMakeDirs() throws Exception {

    }

    @Test
    public void testMakeFolders() throws Exception {

    }

    @Test
    public void testIsFileExist() throws Exception {

    }

    @Test
    public void testIsFolderExist() throws Exception {

    }

    @Test
    public void testDeleteFile() throws Exception {

    }

    @Test
    public void testGetFileSize() throws Exception {

    }

    @Test
    public void testCleanFile() throws Exception {

    }
}