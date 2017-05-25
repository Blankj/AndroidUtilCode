package com.blankj.utilcode.util;

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

    String path = TestUtils.TEST_PATH + FILE_SEP + "file" + FILE_SEP;
    String path1 = TestUtils.TEST_PATH + FILE_SEP + "file1" + FILE_SEP;

//    @Test
//    public void testGetFileByPath() throws Exception {
//        assertThat(getFileByPath(" ")).isNull();
//        assertThat(getFileByPath(path)).isNotNull();
//    }

//    @Test
//    public void testIsFileExists() throws Exception {
//        assertThat(isFileExists(path + "UTF8.txt")).isTrue();
//        assertThat(isFileExists(path + "UTF8")).isFalse();
//    }
//
//    @Test
//    public void testRename() throws Exception {
//        assertThat(rename(path + "GBK.txt", "GBK1.txt")).isTrue();
//        assertThat(rename(path + "GBK1.txt", "GBK.txt")).isTrue();
//    }
//
//    @Test
//    public void testIsDir() throws Exception {
//        assertThat(isDir(path + "UTF8.txt")).isFalse();
//        assertThat(isDir(path)).isTrue();
//    }
//
//    @Test
//    public void testIsFile() throws Exception {
//        assertThat(isFile(path + "UTF8.txt")).isTrue();
//        assertThat(isFile(path)).isFalse();
//    }
//
//    @Test
//    public void testCreateOrExistsDir() throws Exception {
//        assertThat(createOrExistsDir(path + "new Dir")).isTrue();
//        assertThat(createOrExistsDir(path)).isTrue();
//        deleteDir(path + "new Dir");
//    }
//
//    @Test
//    public void testCreateOrExistsFile() throws Exception {
//        assertThat(createOrExistsFile(path + "new File")).isTrue();
//        assertThat(createOrExistsFile(path)).isFalse();
//        deleteFile(path + "new File");
//    }
//
//    @Test
//    public void testCreateFileByDeleteOldFile() throws Exception {
//        assertThat(createFileByDeleteOldFile(path + "new File")).isTrue();
//        assertThat(createFileByDeleteOldFile(path)).isFalse();
//        deleteFile(path + "new File");
//    }
//
//    @Test
//    public void testCopyDir() throws Exception {
//        assertThat(copyDir(path, path)).isFalse();
//        assertThat(copyDir(path, path + "new Dir")).isFalse();
//        assertThat(copyDir(path, path1)).isTrue();
//        deleteDir(path1);
//    }
//
//    @Test
//    public void testCopyFile() throws Exception {
//        assertThat(copyFile(path + "GBK.txt", path + "GBK.txt")).isFalse();
//        assertThat(copyFile(path + "GBK.txt", path + "new Dir" + FILE_SEP + "GBK.txt")).isTrue();
//        assertThat(copyFile(path + "GBK.txt", path1 + "GBK.txt")).isTrue();
//        deleteDir(path + "new Dir" + FILE_SEP + "GBK.txt");
//        deleteDir(path1 + "GBK.txt");
//    }
//
//    @Test
//    public void testMoveDir() throws Exception {
//        assertThat(moveDir(path, path)).isFalse();
//        assertThat(moveDir(path, path + "new Dir")).isFalse();
//        assertThat(moveDir(path, path1)).isTrue();
//        assertThat(moveDir(path1, path)).isTrue();
//    }
//
//    @Test
//    public void testMoveFile() throws Exception {
//        assertThat(moveFile(path + "GBK.txt", path + "GBK.txt")).isFalse();
//        assertThat(moveFile(path + "GBK.txt", path1 + "GBK.txt")).isTrue();
//        assertThat(moveFile(path1 + "GBK.txt", path + "GBK.txt")).isTrue();
//        deleteDir(path1);
//    }
//
//    FilenameFilter filter = new FilenameFilter() {
//        public boolean accept(File dir, String name) {
//            return name.endsWith("k.txt");
//        }
//    };
//
//    @Test
//    public void testListFilesInDirWithFiltere() throws Exception {
//        System.out.println(listFilesInDirWithFilter(path, "k.txt", false).toString());
//        System.out.println(listFilesInDirWithFilter(path, "k.txt", true).toString());
//        System.out.println(listFilesInDirWithFilter(path, filter, false).toString());
//        System.out.println(listFilesInDirWithFilter(path, filter, true).toString());
//    }
//
//    @Test
//    public void testSearchFile() throws Exception {
//        System.out.println(searchFileInDir(path, "GBK.txt").toString());
//        System.out.println(searchFileInDir(path, "child").toString());
//    }
//
//    @Test
//    public void testWriteFileFromIS() throws Exception {
//        assertThat(writeFileFromIS(path + "NEW.txt", new FileInputStream(path + "UTF8.txt"), false))
//                .isTrue();
//        assertThat(writeFileFromIS(path + "NEW.txt", new FileInputStream(path + "UTF8.txt"), true))
//                .isTrue();
//    }
//
//    @Test
//    public void testWriteFileFromString() throws Exception {
//        assertThat(writeFileFromString(path + "NEW.txt", "这是新的", false)).isTrue();
//        assertThat(writeFileFromString(path + "NEW.txt", "这是追加的", true)).isTrue();
//    }
//
//    @Test
//    public void testGetFileCharsetSimple() throws Exception {
//        assertThat(getFileCharsetSimple(path + "GBK.txt")).isEqualTo("GBK");
//        assertThat(getFileCharsetSimple(path + "Unicode.txt")).isEqualTo("Unicode");
//        assertThat(getFileCharsetSimple(path + "UTF8.txt")).isEqualTo("UTF-8");
//        assertThat(getFileCharsetSimple(path + "UTF16BE.txt")).isEqualTo("UTF-16BE");
//    }
//
//    @Test
//    public void testGetFileLines() throws Exception {
//        System.out.println(getFileLines(path + "UTF8.txt"));
//    }
//
//    @Test
//    public void testGetFileLastModified()throws Exception{
//        System.out.println(TimeUtils.millis2String(getFileLastModified(path)));
//    }
//
//    @Test
//    public void testReadFile2List() throws Exception {
//        System.out.println(readFile2List(path + "UTF8.txt", "").toString());
//        System.out.println(readFile2List(path + "UTF8.txt", "UTF-8").toString());
//        System.out.println(readFile2List(path + "UTF8.txt", 2, 5, "UTF-8").toString());
//        System.out.println(readFile2List(path + "UTF8.txt", "GBK").toString());
//    }
//
//    @Test
//    public void testReadFile2String() throws Exception {
//        System.out.println(readFile2String(path + "UTF8.txt", ""));
//        System.out.println(readFile2String(path + "UTF8.txt", "UTF-8"));
//        System.out.println(readFile2String(path + "UTF8.txt", "GBK"));
//    }
//
//    @Test
//    public void testReadFile2Bytes() throws Exception {
//        String p = path + "UTF8.txt";
//        long st,end;
//        st = System.currentTimeMillis();
//        readFile2BytesByStream(p);
//        end = System.currentTimeMillis();
//        System.out.println(end - st);
//        st = System.currentTimeMillis();
//        readFile2BytesByChannel(p);
//        end = System.currentTimeMillis();
//        System.out.println(end - st);
//        st = System.currentTimeMillis();
//        readFile2BytesByMap(p);
//        end = System.currentTimeMillis();
//        System.out.println(end - st);
//    }
//
//    @Test
//    public void testGetDirLength() throws Exception {
//        assertThat(getDirLength(path)).isEqualTo(73);
//    }
//
//    @Test
//    public void testGetFileLength() throws Exception {
//        assertThat(getFileLength(path + "UTF8.txt")).isEqualTo(25);
//    }
//
//    @Test
//    public void testGetFileMD5ToString() throws Exception {
//        assertThat(getFileMD5ToString(path + "UTF8.txt")).isEqualTo("249D3E76851DCC56C945994DE9DAC406");
//    }
//
//    @Test
//    public void testGetDirName() throws Exception {
//        assertThat(getDirName(new File(path + "UTF8.txt"))).isEqualTo(path);
//        assertThat(getDirName(path + "UTF8.txt")).isEqualTo(path);
//    }
//
//    @Test
//    public void testGetFileName() throws Exception {
//        assertThat(getFileName(new File(path + "UTF8.txt"))).isEqualTo("UTF8.txt");
//        assertThat(getFileName(path + "UTF8.txt")).isEqualTo("UTF8.txt");
//    }
//
//    @Test
//    public void testGetFileNameNoExtension() throws Exception {
//        assertThat(getFileNameNoExtension(new File(path + "UTF8.txt"))).isEqualTo("UTF8");
//        assertThat(getFileNameNoExtension(path + "UTF8.txt")).isEqualTo("UTF8");
//    }
//
//    @Test
//    public void testGetFileExtension() throws Exception {
//        assertThat(getFileExtension(new File(path + "UTF8.txt"))).isEqualTo("txt");
//        assertThat(getFileExtension(path + "UTF8.txt")).isEqualTo("txt");
//    }
}