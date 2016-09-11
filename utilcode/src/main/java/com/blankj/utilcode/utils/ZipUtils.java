package com.blankj.utilcode.utils;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import static com.blankj.utilcode.utils.ConstUtils.MB;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/27
 *     desc  : 压缩相关工具类
 * </pre>
 */
public class ZipUtils {

    private ZipUtils() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    /**
     * 批量压缩文件
     *
     * @param resFiles    要压缩的文件列表
     * @param zipFilePath 压缩文件路径
     * @throws IOException IO错误时抛出
     */
    public static void zipFiles(Collection<File> resFiles, String zipFilePath)
            throws IOException {
        zipFiles(resFiles, zipFilePath, null);
    }

    /**
     * 批量压缩文件
     *
     * @param resFiles    要压缩的文件列表
     * @param zipFilePath 压缩文件路径
     * @param comment     压缩文件的注释
     * @throws IOException IO错误时抛出
     */
    public static void zipFiles(Collection<File> resFiles, String zipFilePath, String comment)
            throws IOException {
        zipFiles(resFiles, FileUtils.getFileByPath(zipFilePath), comment);
    }

    /**
     * 批量压缩文件
     *
     * @param resFiles 要压缩的文件列表
     * @param zipFile  生成的压缩文件
     * @throws IOException IO错误时抛出
     */
    public static void zipFiles(Collection<File> resFiles, File zipFile)
            throws IOException {
        zipFiles(resFiles, zipFile, null);
    }

    /**
     * 批量压缩文件
     *
     * @param resFiles 要压缩的文件列表
     * @param zipFile  压缩文件
     * @param comment  压缩文件的注释
     * @throws IOException IO错误时抛出
     */
    public static void zipFiles(Collection<File> resFiles, File zipFile, String comment)
            throws IOException {
        if (resFiles == null || zipFile == null) return;
        zipFiles(resFiles, new ZipOutputStream(new FileOutputStream(zipFile)), comment);
    }

    /**
     * 批量压缩文件
     *
     * @param resFiles 要压缩的文件列表
     * @param zos      压缩文件输出流
     * @param comment  压缩文件的注释
     * @throws IOException IO错误时抛出
     */
    public static void zipFiles(Collection<File> resFiles, ZipOutputStream zos, String comment)
            throws IOException {
        for (File resFile : resFiles) {
            zipFile(resFile, "", zos, comment);
        }
    }

    /**
     * 压缩文件
     *
     * @param resFilePath 需要压缩的文件路径
     * @param zipFilePath 压缩文件路径
     * @throws IOException IO错误时抛出
     */
    public static void zipFile(String resFilePath, String zipFilePath)
            throws IOException {
        zipFile(resFilePath, zipFilePath, null);
    }

    /**
     * 压缩文件
     *
     * @param resFilePath 需要压缩的文件路径
     * @param zipFilePath 压缩文件路径
     * @param comment     压缩文件的注释
     * @throws IOException IO错误时抛出
     */
    public static void zipFile(String resFilePath, String zipFilePath, String comment)
            throws IOException {
        zipFile(FileUtils.getFileByPath(resFilePath), FileUtils.getFileByPath(zipFilePath), comment);
    }

    /**
     * 压缩文件
     *
     * @param resFile 需要压缩的文件
     * @param zipFile 压缩文件
     * @throws IOException IO错误时抛出
     */
    public static void zipFile(File resFile, File zipFile)
            throws IOException {
        zipFile(resFile, zipFile, null);
    }

    /**
     * 压缩文件
     *
     * @param resFile 需要压缩的文件
     * @param zipFile 压缩文件
     * @param comment 压缩文件的注释
     * @throws IOException IO错误时抛出
     */
    public static void zipFile(File resFile, File zipFile, String comment)
            throws IOException {
        if (resFile == null || zipFile == null) return;
        zipFile(resFile, "", new ZipOutputStream(new FileOutputStream(zipFile)), comment);
    }

    /**
     * 压缩文件
     *
     * @param resFile  需要压缩的文件
     * @param rootPath 相对于压缩文件的路径
     * @param zos      压缩文件输出流
     * @param comment  压缩文件的注释
     * @throws IOException IO错误时抛出
     */
    public static void zipFile(File resFile, String rootPath, ZipOutputStream zos, String comment)
            throws IOException {
        rootPath = rootPath + (StringUtils.isSpace(rootPath) ? "" : File.separator) + resFile.getName();
        if (resFile.isDirectory()) {
            File[] fileList = resFile.listFiles();
            for (File file : fileList) {
                zipFile(file, rootPath, zos, comment);
            }
        } else {
            InputStream is = null;
            try {
                is = new BufferedInputStream(new FileInputStream(resFile));
                zos.putNextEntry(new ZipEntry(rootPath));
                byte buffer[] = new byte[MB];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    zos.write(buffer, 0, len);
                }
                if (StringUtils.isEmpty(comment)) zos.setComment(comment);
            } finally {
                FileUtils.closeIO(is);
                zos.flush();
                zos.closeEntry();
            }
        }
    }

    /**
     * 解压缩一个文件
     *
     * @param zipFilePath 压缩文件路径
     * @param destDirPath 解压缩的目标目录
     * @throws IOException IO错误时抛出
     */
    public static void unzipFile(String zipFilePath, String destDirPath)
            throws IOException {
        if (!FileUtils.createOrExistsDir(destDirPath)) return;
        ZipFile zf = new ZipFile(zipFilePath);
        Enumeration<?> entries = zf.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            String str = destDirPath + File.separator + entry.getName();
            File desFile = new File(str);
            if (entry.isDirectory()) {
                if (!FileUtils.createOrExistsDir(desFile)) return;
            } else {
                if (!FileUtils.createOrExistsFile(desFile)) return;
                InputStream in = null;
                OutputStream out = null;
                try {
                    in = zf.getInputStream(entry);
                    out = new FileOutputStream(desFile);
                    byte buffer[] = new byte[MB];
                    int len;
                    while ((len = in.read(buffer)) != -1) {
                        out.write(buffer, 0, len);
                    }
                } finally {
                    FileUtils.closeIO(in, out);
                }
            }
        }
    }

    /**
     * 解压文件名中指定文件名的文件
     *
     * @param zipFile     压缩文件
     * @param destDirPath 目标文件夹
     * @param targetFile  目标文件名
     * @throws IOException IO错误时抛出
     */
    public static List<File> unzipSelectedFile(File zipFile, String destDirPath, String targetFile)
            throws IOException {
        if (!FileUtils.createOrExistsDir(destDirPath)) return null;
        List<File> fileList = new ArrayList<>();
        ZipFile zf = new ZipFile(zipFile);
        Enumeration<?> entries = zf.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = ((ZipEntry) entries.nextElement());
            if (entry.getName().equals(targetFile)) {
                String str = destDirPath + File.separator + entry.getName();
                File desFile = new File(str);
                if (entry.isDirectory()) {
                    if (!FileUtils.createOrExistsDir(desFile)) return fileList;
                } else {
                    if (!FileUtils.createOrExistsFile(desFile)) return fileList;
                    InputStream in = null;
                    OutputStream out = null;
                    try {
                        in = zf.getInputStream(entry);
                        out = new FileOutputStream(desFile);
                        byte buffer[] = new byte[MB];
                        int len;
                        while ((len = in.read(buffer)) != -1) {
                            out.write(buffer, 0, len);
                        }
                    } finally {
                        FileUtils.closeIO(in, out);
                    }
                }
            }
        }
        return fileList;
    }

    /**
     * 获得压缩文件内文件列表
     *
     * @param zipFile 压缩文件
     * @return 压缩文件内文件名称
     * @throws IOException 当解压缩过程出错时抛出
     */
    public static List<String> getEntriesNames(File zipFile)
            throws IOException {
        List<String> entryNames = new ArrayList<String>();
        Enumeration<?> entries = getEntriesEnumeration(zipFile);
        while (entries.hasMoreElements()) {
            ZipEntry entry = ((ZipEntry) entries.nextElement());
            entryNames.add(getEntryName(entry));
        }
        return entryNames;
    }

    /**
     * 获得压缩文件内压缩文件对象以取得其属性
     *
     * @param zipFile 压缩文件
     * @return 返回一个压缩文件列表
     * @throws IOException IO操作有误时抛出
     */
    public static Enumeration<?> getEntriesEnumeration(File zipFile)
            throws IOException {
        ZipFile zf = new ZipFile(zipFile);
        return zf.entries();

    }

    /**
     * 取得压缩文件对象的注释
     *
     * @param entry 压缩文件对象
     * @return 压缩文件对象的注释
     * @throws UnsupportedEncodingException
     */
    public static String getEntryComment(ZipEntry entry)
            throws UnsupportedEncodingException {
        return entry.getComment();
    }

    /**
     * 取得压缩文件对象的名称
     *
     * @param entry 压缩文件对象
     * @return 压缩文件对象的名称
     * @throws UnsupportedEncodingException
     */
    public static String getEntryName(ZipEntry entry)
            throws UnsupportedEncodingException {
        return entry.getName();
    }
}
