package com.blankj.utilcode.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

import static com.blankj.utilcode.utils.ConstUtils.*;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/11
 *     desc  : SD卡相关工具类
 * </pre>
 */
public class SDCardUtils {

    private SDCardUtils() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    /**
     * 判断SD卡是否可用
     *
     * @return true : 可用<br>false : 不可用
     */
    public static boolean isSDCardEnable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取SD卡路径
     * <p>一般是/storage/emulated/0/</p>
     *
     * @return SD卡路径
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath() + File.separator;
    }

    /**
     * 获取SD卡Data路径
     *
     * @return Data路径
     */
    public static String getDataPath() {
        return Environment.getDataDirectory().getPath();

    }

    /**
     * 计算SD卡的剩余空间
     *
     * @param unit <ul>
     *             <li>{@link MemoryUnit#BYTE}: 字节</li>
     *             <li>{@link MemoryUnit#KB}  : 千字节</li>
     *             <li>{@link MemoryUnit#MB}  : 兆</li>
     *             <li>{@link MemoryUnit#GB}  : GB</li>
     *             </ul>
     * @return 返回-1，说明SD卡不可用，否则返回SD卡剩余空间
     */
    public static double getFreeSpace(MemoryUnit unit) {
        if (isSDCardEnable()) {
            try {
                StatFs stat = new StatFs(getSDCardPath());
                long blockSize, availableBlocks;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    availableBlocks = stat.getAvailableBlocksLong();
                    blockSize = stat.getBlockSizeLong();
                } else {
                    availableBlocks = stat.getAvailableBlocks();
                    blockSize = stat.getBlockSize();
                }
                return FileUtils.byte2Size(availableBlocks * blockSize, unit);
            } catch (Exception e) {
                e.printStackTrace();
                return -1.0;
            }
        } else {
            return -1.0;
        }
    }

//    /**
//     * 获取指定路径所在空间的剩余可用容量字节数，单位byte
//     *
//     * @param filePath
//     * @return 容量字节 SDCard可用空间，内部存储可用空间
//     */
//    public static long getFreeBytes(String filePath) {
//        // 如果是sd卡的下的路径，则获取sd卡可用容量
//        if (filePath.startsWith(getSDCardPath())) {
//            filePath = getSDCardPath();
//        } else {// 如果是内部存储的路径，则获取内存存储的可用容量
//            filePath = Environment.getDataDirectory().getAbsolutePath();
//        }
//        StatFs stat = new StatFs(filePath);
//        long availableBlocks = (long) stat.getAvailableBlocks() - 4;
//        return stat.getBlockSize() * availableBlocks;
//    }
//
//    /**
//     * 获取系统存储路径
//     *
//     * @return
//     */
//    public static String getRootDirectoryPath() {
//        return Environment.getRootDirectory().getAbsolutePath();
//    }
//
//    /**
//     * Check if the file is exists
//     *
//     * @param filePath
//     * @param fileName
//     * @return
//     */
//    public static boolean isFileExistsInSDCard(String filePath, String fileName) {
//        boolean flag = false;
//        if (isSDCardEnable()) {
//            File file = new File(filePath, fileName);
//            if (file.exists()) {
//                flag = true;
//            }
//        }
//        return flag;
//    }
//
//    /**
//     * Write file to SD card
//     *
//     * @param filePath
//     * @param filename
//     * @param content
//     * @return
//     * @throws Exception
//     */
//    public static boolean saveFileToSDCard(String filePath, String filename, String content)
//            throws Exception {
//        boolean flag = false;
//        if (isSDCardEnable()) {
//            File dir = new File(filePath);
//            if (!dir.exists()) {
//                dir.mkdirs();
//            }
//            File file = new File(filePath, filename);
//            FileOutputStream outStream = new FileOutputStream(file);
//            outStream.write(content.getBytes());
//            outStream.close();
//            flag = true;
//        }
//        return flag;
//    }
//
//    /**
//     * Read file as stream from SD card
//     *
//     * @param fileName String PATH =
//     *                 Environment.getExternalStorageDirectory().getAbsolutePath() +
//     *                 "/dirName";
//     * @return
//     */
//    public static byte[] readFileFromSDCard(String filePath, String fileName) {
//        byte[] buffer = null;
//        FileInputStream fin = null;
//        try {
//            if (isSDCardEnable()) {
//                String filePaht = filePath + "/" + fileName;
//                fin = new FileInputStream(filePaht);
//                int length = fin.available();
//                buffer = new byte[length];
//                fin.read(buffer);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (fin != null) fin.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return buffer;
//    }
}