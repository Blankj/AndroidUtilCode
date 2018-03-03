package com.blankj.utilcode.util;

import org.junit.Test;

import java.io.File;
import java.io.FileFilter;

import static com.blankj.utilcode.util.TestConfig.PATH_FILE;
import static com.blankj.utilcode.util.TestConfig.FILE_SEP;
import static com.blankj.utilcode.util.TestConfig.PATH_TEMP;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/19
 *     desc  : test FileUtils
 * </pre>
 */
public class FileUtilsTest {

    private FileFilter mFilter = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            return pathname.getName().endsWith("8.txt");
        }
    };

    private FileUtils.OnReplaceListener mListener = new FileUtils.OnReplaceListener() {
        @Override
        public boolean onReplace() {
            return true;
        }
    };


    @Test
    public void getFileByPath() throws Exception {
        assertNull(FileUtils.getFileByPath(" "));
        assertNotNull(FileUtils.getFileByPath(PATH_FILE));
    }

    @Test
    public void isFileExists() throws Exception {
        assertTrue(FileUtils.isFileExists(PATH_FILE + "UTF8.txt"));
        assertFalse(FileUtils.isFileExists(PATH_FILE + "UTF8"));
    }

    @Test
    public void rename() throws Exception {
        assertTrue(FileUtils.rename(PATH_FILE + "GBK.txt", "GBK1.txt"));
        assertTrue(FileUtils.rename(PATH_FILE + "GBK1.txt", "GBK.txt"));
    }

    @Test
    public void isDir() throws Exception {
        assertFalse(FileUtils.isDir(PATH_FILE + "UTF8.txt"));
        assertTrue(FileUtils.isDir(PATH_FILE));
    }

    @Test
    public void isFile() throws Exception {
        assertTrue(FileUtils.isFile(PATH_FILE + "UTF8.txt"));
        assertFalse(FileUtils.isFile(PATH_FILE));
    }

    @Test
    public void createOrExistsDir() throws Exception {
        assertTrue(FileUtils.createOrExistsDir(PATH_FILE + "new Dir"));
        assertTrue(FileUtils.createOrExistsDir(PATH_FILE));
        assertTrue(FileUtils.deleteDir(PATH_FILE + "new Dir"));
    }

    @Test
    public void createOrExistsFile() throws Exception {
        assertTrue(FileUtils.createOrExistsFile(PATH_FILE + "new File"));
        assertFalse(FileUtils.createOrExistsFile(PATH_FILE));
        assertTrue(FileUtils.deleteFile(PATH_FILE + "new File"));
    }

    @Test
    public void createFileByDeleteOldFile() throws Exception {
        assertTrue(FileUtils.createFileByDeleteOldFile(PATH_FILE + "new File"));
        assertFalse(FileUtils.createFileByDeleteOldFile(PATH_FILE));
        assertTrue(FileUtils.deleteFile(PATH_FILE + "new File"));
    }

    @Test
    public void copyDir() throws Exception {
        assertFalse(FileUtils.copyDir(PATH_FILE, PATH_FILE, mListener));
        assertFalse(FileUtils.copyDir(PATH_FILE, PATH_FILE + "new Dir", mListener));
        assertTrue(FileUtils.copyDir(PATH_FILE, PATH_TEMP, mListener));
        assertTrue(FileUtils.deleteDir(PATH_TEMP));
    }

    @Test
    public void copyFile() throws Exception {
        assertFalse(FileUtils.copyFile(PATH_FILE + "GBK.txt", PATH_FILE + "GBK.txt", mListener));
        assertTrue(FileUtils.copyFile(PATH_FILE + "GBK.txt", PATH_FILE + "new Dir" + FILE_SEP + "GBK.txt", mListener));
        assertTrue(FileUtils.copyFile(PATH_FILE + "GBK.txt", PATH_TEMP + "GBK.txt", mListener));
        assertTrue(FileUtils.deleteDir(PATH_FILE + "new Dir"));
        assertTrue(FileUtils.deleteDir(PATH_TEMP));
    }

    @Test
    public void moveDir() throws Exception {
        assertFalse(FileUtils.moveDir(PATH_FILE, PATH_FILE, mListener));
        assertFalse(FileUtils.moveDir(PATH_FILE, PATH_FILE + "new Dir", mListener));
        assertTrue(FileUtils.moveDir(PATH_FILE, PATH_TEMP, mListener));
        assertTrue(FileUtils.moveDir(PATH_TEMP, PATH_FILE, mListener));
    }

    @Test
    public void moveFile() throws Exception {
        assertFalse(FileUtils.moveFile(PATH_FILE + "GBK.txt", PATH_FILE + "GBK.txt", mListener));
        assertTrue(FileUtils.moveFile(PATH_FILE + "GBK.txt", PATH_TEMP + "GBK.txt", mListener));
        assertTrue(FileUtils.moveFile(PATH_TEMP + "GBK.txt", PATH_FILE + "GBK.txt", mListener));
        FileUtils.deleteDir(PATH_TEMP);
    }

    @Test
    public void listFilesInDir() throws Exception {
        System.out.println(FileUtils.listFilesInDir(PATH_FILE, false).toString());
        System.out.println(FileUtils.listFilesInDir(PATH_FILE, true).toString());
    }

    @Test
    public void listFilesInDirWithFilter() throws Exception {
        System.out.println(FileUtils.listFilesInDirWithFilter(PATH_FILE, mFilter, false).toString());
        System.out.println(FileUtils.listFilesInDirWithFilter(PATH_FILE, mFilter, true).toString());
    }

    @Test
    public void getFileLastModified() throws Exception {
        System.out.println(TimeUtils.millis2String(FileUtils.getFileLastModified(PATH_FILE)));
    }

    @Test
    public void getFileCharsetSimple() throws Exception {
        assertEquals("GBK", FileUtils.getFileCharsetSimple(PATH_FILE + "GBK.txt"));
        assertEquals("Unicode", FileUtils.getFileCharsetSimple(PATH_FILE + "Unicode.txt"));
        assertEquals("UTF-8", FileUtils.getFileCharsetSimple(PATH_FILE + "UTF8.txt"));
        assertEquals("UTF-16BE", FileUtils.getFileCharsetSimple(PATH_FILE + "UTF16BE.txt"));
    }

    @Test
    public void getFileLines() throws Exception {
        assertEquals(7, FileUtils.getFileLines(PATH_FILE + "UTF8.txt"));
    }

    @Test
    public void getDirSize() throws Exception {
        System.out.println(FileUtils.getDirSize(PATH_FILE));
    }

    @Test
    public void getFileSize() throws Exception {
        System.out.println(FileUtils.getFileSize(PATH_FILE + "UTF8.txt"));
    }

    @Test
    public void getDirLength() throws Exception {
        System.out.println(FileUtils.getDirLength(PATH_FILE));
    }

    @Test
    public void getFileLength() throws Exception {
        System.out.println(FileUtils.getFileLength(PATH_FILE + "UTF8.txt"));
        System.out.println(FileUtils.getFileLength("https://raw.githubusercontent.com/Blankj/AndroidUtilCode/85bc44d1c8adb31027870ea4cb7a931700c80cad/LICENSE"));
    }

    @Test
    public void getFileMD5ToString() throws Exception {
        assertEquals("249D3E76851DCC56C945994DE9DAC406", FileUtils.getFileMD5ToString(PATH_FILE + "UTF8.txt"));
    }

    @Test
    public void getDirName() throws Exception {
        assertEquals(PATH_FILE, FileUtils.getDirName(new File(PATH_FILE + "UTF8.txt")));
        assertEquals(PATH_FILE, FileUtils.getDirName(PATH_FILE + "UTF8.txt"));
    }

    @Test
    public void getFileName() throws Exception {
        assertEquals("UTF8.txt", FileUtils.getFileName(PATH_FILE + "UTF8.txt"));
        assertEquals("UTF8.txt", FileUtils.getFileName(new File(PATH_FILE + "UTF8.txt")));
    }

    @Test
    public void getFileNameNoExtension() throws Exception {
        assertEquals("UTF8", FileUtils.getFileNameNoExtension(PATH_FILE + "UTF8.txt"));
        assertEquals("UTF8", FileUtils.getFileNameNoExtension(new File(PATH_FILE + "UTF8.txt")));
    }

    @Test
    public void getFileExtension() throws Exception {
        assertEquals("txt", FileUtils.getFileExtension(new File(PATH_FILE + "UTF8.txt")));
        assertEquals("txt", FileUtils.getFileExtension(PATH_FILE + "UTF8.txt"));
    }
}