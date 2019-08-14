package com.blankj.utilcode.util;

import org.junit.After;
import org.junit.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.blankj.utilcode.util.TestConfig.PATH_TEMP;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/05/24
 *     desc  : test FileIOUtils
 * </pre>
 */
public class FileIOUtilsTest extends BaseTest {

    @Test
    public void writeFileFromIS() throws Exception {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100000; i++) {
            sb.append(String.format("%5dFileIOUtilsTest\n", i));
        }
        InputStream is = ConvertUtils.string2InputStream(sb.toString(), "UTF-8");

        FileIOUtils.writeFileFromIS(PATH_TEMP + "writeFileFromIS.txt", is, new FileIOUtils.OnProgressUpdateListener() {
            @Override
            public void onProgressUpdate(double progress) {
                System.out.println(String.format("%.2f", progress));
            }
        });
    }

    @Test
    public void writeFileFromBytesByStream() throws Exception {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100000; i++) {
            sb.append(String.format("%5dFileIOUtilsTest\n", i));
        }
        byte[] bytes = sb.toString().getBytes(StandardCharsets.UTF_8);

        FileIOUtils.writeFileFromBytesByStream(PATH_TEMP + "writeFileFromBytesByStream.txt", bytes, new FileIOUtils.OnProgressUpdateListener() {
            @Override
            public void onProgressUpdate(double progress) {
                System.out.println(String.format("%.2f", progress));
            }
        });
    }

    @Test
    public void writeFileFromString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(i).append("FileIOUtilsTest\n");
        }
        FileIOUtils.writeFileFromString(PATH_TEMP + "writeFileFromString.txt", sb.toString());
    }

    @Test
    public void readFile2List() {
        writeFileFromString();
        for (String s : FileIOUtils.readFile2List(PATH_TEMP + "writeFileFromString.txt")) {
            System.out.println(s);
        }
    }

    @Test
    public void readFile2String() {
        writeFileFromString();
        System.out.println(FileIOUtils.readFile2String(PATH_TEMP + "writeFileFromString.txt"));
    }

    @Test
    public void readFile2Bytes() throws Exception {
        writeFileFromBytesByStream();
        FileIOUtils.readFile2BytesByStream(PATH_TEMP + "writeFileFromIS.txt", new FileIOUtils.OnProgressUpdateListener() {
            @Override
            public void onProgressUpdate(double progress) {
                System.out.println(String.format("%.2f", progress));
            }
        });
    }

    @After
    public void tearDown() {
        FileUtils.deleteAllInDir(PATH_TEMP);
    }

}