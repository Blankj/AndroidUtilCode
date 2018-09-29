package com.blankj.utilcode.util;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <pre>
 *     author: UFreedom
 *     blog  : http://ufreedom.me
 *     time  : 2018/09/29
 *     desc  : Util for file split
 * </pre>
 */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class FileSplitUtil {


    /**
     * Split a file into multiples files
     *
     * @param destCacheDir     The dir to be cached for split files
     * @param srcFile          The file need to be split
     * @param expireSplitCount How many files are expected to be cut.The value should at least 2.
     *                         Sometimes a file may not be cut into a specific number.For example, Expire cut into 3,
     *                         but the current file can not be completely cut after cutting 3, there may be a little left,
     *                         and it will eventually be cut into 4
     * @return 切片后的结果
     */
    public static List<File> splitFile(File destCacheDir, final int expireSplitCount, final File srcFile) throws IOException {
        checkDestDir(destCacheDir);
        checkSourceFile(srcFile);
        if (expireSplitCount < 2) {
            throw new IllegalArgumentException("The split count must at least 2");
        }

        final long sourceSize = srcFile.length();
        long bytesPerSplit = sourceSize / expireSplitCount;
        return splitFileInner(destCacheDir, srcFile, sourceSize, bytesPerSplit, expireSplitCount);
    }


    /**
     * Split a file into multiples files
     *
     * @param destCacheDir The dir to be cached for split files
     * @param srcFile      The file need to be split
     * @param sliceSize    How big is each slice are expected to be cut. Unit（MB）
     */
    public static List<File> splitFile(File destCacheDir, final File srcFile, final int sliceSize) throws IOException {

        checkDestDir(destCacheDir);
        checkSourceFile(srcFile);

        if (sliceSize <= 0) {
            throw new IllegalArgumentException("The slice size must be more than zero");
        }

        final long sourceSize = srcFile.length();
        final long bytesPerSplit = 1024L * 1024L * sliceSize;
        final long numSplits = sourceSize / bytesPerSplit;

        return splitFileInner(destCacheDir, srcFile, sourceSize, bytesPerSplit, numSplits);
    }

    private static List<File> splitFileInner(File destDir, File srcFile, long sourceSize, long bytesPerSplit, long numSplits) throws IOException {
        List<File> partFiles = new ArrayList<>();
        final long remainingBytes = sourceSize % bytesPerSplit;
        int position = 0;
        RandomAccessFile sourceFile = new RandomAccessFile(srcFile, "r");
        FileChannel sourceChannel = sourceFile.getChannel();
        for (; position < numSplits; position++) {
            writePartToFile(destDir, bytesPerSplit, position * bytesPerSplit, sourceChannel, partFiles);
        }

        if (remainingBytes > 0) {
            writePartToFile(destDir, remainingBytes, position * bytesPerSplit, sourceChannel, partFiles);
        }
        return partFiles;
    }

    private static void writePartToFile(File destDirFile, long byteSize, long position, FileChannel sourceChannel, List<File> partFiles) throws IOException {
        String tempDir = destDirFile.getAbsolutePath().concat("/");
        File file = new File(tempDir + UUID.randomUUID() + "_" + position + "" + ".splitPart");
        RandomAccessFile toFile = new RandomAccessFile(file, "rw");
        FileChannel toChannel = toFile.getChannel();
        sourceChannel.position(position);
        toChannel.transferFrom(sourceChannel, 0, byteSize);
        partFiles.add(file);
    }

    private static void checkSourceFile(File srcFileName) {
        if (srcFileName == null || !srcFileName.exists()) {
            throw new NullPointerException("The source file should be non-empty or exist");
        }
    }

    private static void checkDestDir(File cacheDir) {
        if (cacheDir == null || !cacheDir.exists()) {
            throw new NullPointerException("The cache directory should exist");
        }

        if (!cacheDir.isDirectory()) {
            throw new IllegalArgumentException("The cache directory should not be a file");
        }
    }


}
