package com.blankj.utilcode.util;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileFilter;

import static com.blankj.utilcode.util.TestUtils.FILE_SEP;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/19
 *     desc  : FileUtils单元测试
 * </pre>
 */
public class FileUtilsTest {

    private String path  = TestUtils.TEST_PATH + FILE_SEP + "file" + FILE_SEP;
    private String path1 = TestUtils.TEST_PATH + FILE_SEP + "file1" + FILE_SEP;

    private FileFilter filter = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            return pathname.getName().endsWith("8.txt");
        }
    };

    @Test
    public void getFileByPath() throws Exception {
        Assert.assertNull(FileUtils.getFileByPath(" "));
        Assert.assertNotNull(FileUtils.getFileByPath(path));
    }

    @Test
    public void isFileExists() throws Exception {
        Assert.assertTrue(FileUtils.isFileExists(path + "UTF8.txt"));
        Assert.assertFalse(FileUtils.isFileExists(path + "UTF8"));
    }

    @Test
    public void rename() throws Exception {
        Assert.assertTrue(FileUtils.rename(path + "GBK.txt", "GBK1.txt"));
        Assert.assertTrue(FileUtils.rename(path + "GBK1.txt", "GBK.txt"));
    }

    @Test
    public void isDir() throws Exception {
        Assert.assertFalse(FileUtils.isDir(path + "UTF8.txt"));
        Assert.assertTrue(FileUtils.isDir(path));
    }

    @Test
    public void isFile() throws Exception {
        Assert.assertTrue(FileUtils.isFile(path + "UTF8.txt"));
        Assert.assertFalse(FileUtils.isFile(path));
    }

    @Test
    public void createOrExistsDir() throws Exception {
        Assert.assertTrue(FileUtils.createOrExistsDir(path + "new Dir"));
        Assert.assertTrue(FileUtils.createOrExistsDir(path));
        Assert.assertTrue(FileUtils.deleteDir(path + "new Dir"));
    }

    @Test
    public void createOrExistsFile() throws Exception {
        Assert.assertTrue(FileUtils.createOrExistsFile(path + "new File"));
        Assert.assertFalse(FileUtils.createOrExistsFile(path));
        Assert.assertTrue(FileUtils.deleteFile(path + "new File"));
    }

    @Test
    public void createFileByDeleteOldFile() throws Exception {
        Assert.assertTrue(FileUtils.createFileByDeleteOldFile(path + "new File"));
        Assert.assertFalse(FileUtils.createFileByDeleteOldFile(path));
        Assert.assertTrue(FileUtils.deleteFile(path + "new File"));
    }

    @Test
    public void copyDir() throws Exception {
        Assert.assertFalse(FileUtils.copyDir(
                path,
                path,
                new FileUtils.OnReplaceListener() {
                    @Override
                    public boolean onReplace() {
                        return true;
                    }
                })
        );
        Assert.assertFalse(FileUtils.copyDir(
                path,
                path + "new Dir",
                new FileUtils.OnReplaceListener() {
                    @Override
                    public boolean onReplace() {
                        return true;
                    }
                })
        );
        Assert.assertTrue(FileUtils.copyDir(
                path,
                path1,
                new FileUtils.OnReplaceListener() {
                    @Override
                    public boolean onReplace() {
                        return true;
                    }
                })
        );
        Assert.assertTrue(FileUtils.deleteDir(path1));
    }

    @Test
    public void copyFile() throws Exception {
        Assert.assertFalse(FileUtils.copyFile(
                path + "GBK.txt",
                path + "GBK.txt",
                new FileUtils.OnReplaceListener() {
                    @Override
                    public boolean onReplace() {
                        return true;
                    }
                })
        );
        Assert.assertTrue(FileUtils.copyFile(
                path + "GBK.txt",
                path + "new Dir" + FILE_SEP + "GBK.txt",
                new FileUtils.OnReplaceListener() {
                    @Override
                    public boolean onReplace() {
                        return true;
                    }
                })
        );
        Assert.assertTrue(FileUtils.copyFile(
                path + "GBK.txt",
                path1 + "GBK.txt",
                new FileUtils.OnReplaceListener() {
                    @Override
                    public boolean onReplace() {
                        return true;
                    }
                })
        );
        Assert.assertTrue(FileUtils.deleteDir(path + "new Dir"));
        Assert.assertTrue(FileUtils.deleteDir(path1));
    }

//    @Test
//    public void moveDir() throws Exception {
//        Assert.assertFalse(FileUtils.moveDir(
//                path,
//                path,
//                new FileUtils.OnReplaceListener() {
//                    @Override
//                    public boolean onReplace() {
//                        return true;
//                    }
//                })
//        );
//        Assert.assertFalse(FileUtils.moveDir(
//                path,
//                path + "new Dir",
//                new FileUtils.OnReplaceListener() {
//                    @Override
//                    public boolean onReplace() {
//                        return true;
//                    }
//                })
//        );
//        Assert.assertTrue(FileUtils.moveDir(
//                path,
//                path1,
//                new FileUtils.OnReplaceListener() {
//                    @Override
//                    public boolean onReplace() {
//                        return true;
//                    }
//                })
//        );
//        Assert.assertTrue(FileUtils.moveDir(
//                path1,
//                path,
//                new FileUtils.OnReplaceListener() {
//                    @Override
//                    public boolean onReplace() {
//                        return true;
//                    }
//                })
//        );
//    }
//
//    @Test
//    public void moveFile() throws Exception {
//        Assert.assertFalse(FileUtils.moveFile(
//                path + "GBK.txt",
//                path + "GBK.txt",
//                new FileUtils.OnReplaceListener() {
//                    @Override
//                    public boolean onReplace() {
//                        return true;
//                    }
//                })
//        );
//        Assert.assertTrue(FileUtils.moveFile(
//                path + "GBK.txt",
//                path1 + "GBK.txt",
//                new FileUtils.OnReplaceListener() {
//                    @Override
//                    public boolean onReplace() {
//                        return true;
//                    }
//                })
//        );
//        Assert.assertTrue(FileUtils.moveFile(
//                path1 + "GBK.txt",
//                path + "GBK.txt",
//                new FileUtils.OnReplaceListener() {
//                    @Override
//                    public boolean onReplace() {
//                        return true;
//                    }
//                })
//        );
//        FileUtils.deleteDir(path1);
//    }
//
//    @Test
//    public void listFilesInDir() throws Exception {
//        System.out.println(FileUtils.listFilesInDir(path, false).toString());
//        System.out.println(FileUtils.listFilesInDir(path, true).toString());
//    }
//
//    @Test
//    public void listFilesInDirWithFilter() throws Exception {
//        System.out.println(FileUtils.listFilesInDirWithFilter(path, filter, false).toString());
//        System.out.println(FileUtils.listFilesInDirWithFilter(path, filter, true).toString());
//    }
//
//    @Test
//    public void getFileLastModified() throws Exception {
//        System.out.println(TimeUtils.millis2String(FileUtils.getFileLastModified(path)));
//    }
//
//    @Test
//    public void getFileCharsetSimple() throws Exception {
//        Assert.assertEquals("GBK", FileUtils.getFileCharsetSimple(path + "GBK.txt"));
//        Assert.assertEquals("Unicode", FileUtils.getFileCharsetSimple(path + "Unicode.txt"));
//        Assert.assertEquals("UTF-8", FileUtils.getFileCharsetSimple(path + "UTF8.txt"));
//        Assert.assertEquals("UTF-16BE", FileUtils.getFileCharsetSimple(path + "UTF16BE.txt"));
//    }
//
//    @Test
//    public void getFileLines() throws Exception {
//        Assert.assertEquals(7, FileUtils.getFileLines(path + "UTF8.txt"));
//    }
//
//    @Test
//    public void getDirSize() throws Exception {
//        System.out.println(FileUtils.getDirSize(path));
//    }
//
//    @Test
//    public void getFileSize() throws Exception {
//        System.out.println(FileUtils.getFileSize(path + "UTF8.txt"));
//    }
//
//    @Test
//    public void getDirLength() throws Exception {
//        System.out.println(FileUtils.getDirLength(path));
//    }
//
//    @Test
//    public void getFileLength() throws Exception {
//        System.out.println(FileUtils.getFileLength(path + "UTF8.txt"));
//    }
//
//    @Test
//    public void getFileMD5ToString() throws Exception {
//        Assert.assertEquals("249D3E76851DCC56C945994DE9DAC406", FileUtils.getFileMD5ToString(path + "UTF8.txt"));
//    }
//
//    @Test
//    public void getDirName() throws Exception {
//        Assert.assertEquals(path, FileUtils.getDirName(new File(path + "UTF8.txt")));
//        Assert.assertEquals(path, FileUtils.getDirName(path + "UTF8.txt"));
//    }
//
//    @Test
//    public void getFileName() throws Exception {
//        Assert.assertEquals("UTF8.txt", FileUtils.getFileName(path + "UTF8.txt"));
//        Assert.assertEquals("UTF8.txt", FileUtils.getFileName(new File(path + "UTF8.txt")));
//    }
//
//    @Test
//    public void getFileNameNoExtension() throws Exception {
//        Assert.assertEquals("UTF8", FileUtils.getFileNameNoExtension(path + "UTF8.txt"));
//        Assert.assertEquals("UTF8", FileUtils.getFileNameNoExtension(new File(path + "UTF8.txt")));
//    }
//
//    @Test
//    public void getFileExtension() throws Exception {
//        Assert.assertEquals("txt", FileUtils.getFileExtension(new File(path + "UTF8.txt")));
//        Assert.assertEquals("txt", FileUtils.getFileExtension(path + "UTF8.txt"));
//    }
}