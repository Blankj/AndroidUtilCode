package com.blankj.utilcode.utils;

import org.junit.Test;

import static com.blankj.utilcode.utils.FileUtils.*;
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

    String path = System.getProperty("user.dir") + "\\src\\test\\res\\";

    @Test
    public void testIsFileExists() throws Exception {
        assertThat(isFileExists(path + "UTF8.txt")).isTrue();
        assertThat(isFileExists(path + "UTF8")).isFalse();
    }

    @Test
    public void testIsDirectory() throws Exception {
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
        assertThat(createOrExistsFile(path + "new Dir")).isTrue();
        assertThat(createOrExistsFile(path)).isFalse();
    }

    @Test
    public void testCreateFileByDeleteOldFile() throws Exception {
        assertThat(createFileByDeleteOldFile(path + "new Dir")).isTrue();
        assertThat(createFileByDeleteOldFile(path)).isFalse();
    }
}