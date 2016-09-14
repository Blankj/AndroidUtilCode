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
    public static String getFreeSpace(MemoryUnit unit) {
        if (!isSDCardEnable()) return "sdcard unable!";
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
            return ConvertUtils.byte2FitSize(availableBlocks * blockSize);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}