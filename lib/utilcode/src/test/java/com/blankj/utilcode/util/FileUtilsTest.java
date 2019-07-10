package com.blankj.utilcode.util;

import org.junit.Test;

import java.io.File;
import java.io.FileFilter;

import static com.blankj.utilcode.util.TestConfig.FILE_SEP;
import static com.blankj.utilcode.util.TestConfig.PATH_FILE;
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
public class FileUtilsTest extends BaseTest {

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
    public void getFileByPath() {
        assertNull(FileUtils.getFileByPath(" "));
        assertNotNull(FileUtils.getFileByPath(PATH_FILE));
    }

    @Test
    public void isFileExists() {
        assertTrue(FileUtils.isFileExists(PATH_FILE + "UTF8.txt"));
        assertFalse(FileUtils.isFileExists(PATH_FILE + "UTF8"));
    }

    @Test
    public void rename() {
        assertTrue(FileUtils.rename(PATH_FILE + "GBK.txt", "GBK1.txt"));
        assertTrue(FileUtils.rename(PATH_FILE + "GBK1.txt", "GBK.txt"));
    }

    @Test
    public void isDir() {
        assertFalse(FileUtils.isDir(PATH_FILE + "UTF8.txt"));
        assertTrue(FileUtils.isDir(PATH_FILE));
    }

    @Test
    public void isFile() {
        assertTrue(FileUtils.isFile(PATH_FILE + "UTF8.txt"));
        assertFalse(FileUtils.isFile(PATH_FILE));
    }

    @Test
    public void createOrExistsDir() {
        assertTrue(FileUtils.createOrExistsDir(PATH_FILE + "new Dir"));
        assertTrue(FileUtils.createOrExistsDir(PATH_FILE));
        assertTrue(FileUtils.deleteDir(PATH_FILE + "new Dir"));
    }

    @Test
    public void createOrExistsFile() {
        assertTrue(FileUtils.createOrExistsFile(PATH_FILE + "new File"));
        assertFalse(FileUtils.createOrExistsFile(PATH_FILE));
        assertTrue(FileUtils.deleteFile(PATH_FILE + "new File"));
    }

    @Test
    public void createFileByDeleteOldFile() {
        assertTrue(FileUtils.createFileByDeleteOldFile(PATH_FILE + "new File"));
        assertFalse(FileUtils.createFileByDeleteOldFile(PATH_FILE));
        assertTrue(FileUtils.deleteFile(PATH_FILE + "new File"));
    }

    @Test
    public void copyDir() {
        assertFalse(FileUtils.copyDir(PATH_FILE, PATH_FILE, mListener));
        assertFalse(FileUtils.copyDir(PATH_FILE, PATH_FILE + "new Dir", mListener));
        assertTrue(FileUtils.copyDir(PATH_FILE, PATH_TEMP, mListener));
        assertTrue(FileUtils.deleteDir(PATH_TEMP));
    }

    @Test
    public void copyFile() {
        assertFalse(FileUtils.copyFile(PATH_FILE + "GBK.txt", PATH_FILE + "GBK.txt", mListener));
        assertTrue(FileUtils.copyFile(PATH_FILE + "GBK.txt", PATH_FILE + "new Dir" + FILE_SEP + "GBK.txt", mListener));
        assertTrue(FileUtils.copyFile(PATH_FILE + "GBK.txt", PATH_TEMP + "GBK.txt", mListener));
        assertTrue(FileUtils.deleteDir(PATH_FILE + "new Dir"));
        assertTrue(FileUtils.deleteDir(PATH_TEMP));
    }

    @Test
    public void moveDir() {
        assertFalse(FileUtils.moveDir(PATH_FILE, PATH_FILE, mListener));
        assertFalse(FileUtils.moveDir(PATH_FILE, PATH_FILE + "new Dir", mListener));
        assertTrue(FileUtils.moveDir(PATH_FILE, PATH_TEMP, mListener));
        assertTrue(FileUtils.moveDir(PATH_TEMP, PATH_FILE, mListener));
    }

    @Test
    public void moveFile() {
        assertFalse(FileUtils.moveFile(PATH_FILE + "GBK.txt", PATH_FILE + "GBK.txt", mListener));
        assertTrue(FileUtils.moveFile(PATH_FILE + "GBK.txt", PATH_TEMP + "GBK.txt", mListener));
        assertTrue(FileUtils.moveFile(PATH_TEMP + "GBK.txt", PATH_FILE + "GBK.txt", mListener));
        FileUtils.deleteDir(PATH_TEMP);
    }

    @Test
    public void listFilesInDir() {
        System.out.println(FileUtils.listFilesInDir(PATH_FILE, false).toString());
        System.out.println(FileUtils.listFilesInDir(PATH_FILE, true).toString());
    }

    @Test
    public void listFilesInDirWithFilter() {
        System.out.println(FileUtils.listFilesInDirWithFilter(PATH_FILE, mFilter, false).toString());
        System.out.println(FileUtils.listFilesInDirWithFilter(PATH_FILE, mFilter, true).toString());
    }

    @Test
    public void getFileLastModified() {
        System.out.println(TimeUtils.millis2String(FileUtils.getFileLastModified(PATH_FILE)));
    }

    @Test
    public void getFileCharsetSimple() {
        assertEquals("GBK", FileUtils.getFileCharsetSimple(PATH_FILE + "GBK.txt"));
        assertEquals("Unicode", FileUtils.getFileCharsetSimple(PATH_FILE + "Unicode.txt"));
        assertEquals("UTF-8", FileUtils.getFileCharsetSimple(PATH_FILE + "UTF8.txt"));
        assertEquals("UTF-16BE", FileUtils.getFileCharsetSimple(PATH_FILE + "UTF16BE.txt"));
    }

    @Test
    public void getFileLines() {
        assertEquals(7, FileUtils.getFileLines(PATH_FILE + "UTF8.txt"));
    }

    @Test
    public void getDirSize() {
        System.out.println(FileUtils.getDirSize(PATH_FILE));
    }

    @Test
    public void getFileSize() {
        System.out.println(FileUtils.getFileSize(PATH_FILE + "UTF8.txt"));
    }

    @Test
    public void getDirLength() {
        System.out.println(FileUtils.getDirLength(PATH_FILE));
    }

    @Test
    public void getFileLength() {
        System.out.println(FileUtils.getFileLength(PATH_FILE + "UTF8.txt"));
//        System.out.println(FileUtils.getFileLength("https://raw.githubusercontent.com/Blankj/AndroidUtilCode/85bc44d1c8adb31027870ea4cb7a931700c80cad/LICENSE"));
    }

    @Test
    public void getFileMD5ToString() {
        assertEquals("249D3E76851DCC56C945994DE9DAC406", FileUtils.getFileMD5ToString(PATH_FILE + "UTF8.txt"));
    }

    @Test
    public void getDirName() {
        assertEquals(PATH_FILE, FileUtils.getDirName(new File(PATH_FILE + "UTF8.txt")));
        assertEquals(PATH_FILE, FileUtils.getDirName(PATH_FILE + "UTF8.txt"));
    }

    @Test
    public void getFileName() {
        assertEquals("UTF8.txt", FileUtils.getFileName(PATH_FILE + "UTF8.txt"));
        assertEquals("UTF8.txt", FileUtils.getFileName(new File(PATH_FILE + "UTF8.txt")));
    }

    @Test
    public void getFileNameNoExtension() {
        assertEquals("UTF8", FileUtils.getFileNameNoExtension(PATH_FILE + "UTF8.txt"));
        assertEquals("UTF8", FileUtils.getFileNameNoExtension(new File(PATH_FILE + "UTF8.txt")));
    }

    @Test
    public void getFileExtension() {
        assertEquals("txt", FileUtils.getFileExtension(new File(PATH_FILE + "UTF8.txt")));
        assertEquals("txt", FileUtils.getFileExtension(PATH_FILE + "UTF8.txt"));
    }
}