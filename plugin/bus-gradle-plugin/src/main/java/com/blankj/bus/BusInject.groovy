package com.blankj.bus

import com.blankj.bus.util.ZipUtils
import org.apache.commons.io.FileUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

class BusInject {

    static void start(Map<String, BusInfo> busMap, File busUtilsTransformFile, String busUtilsClass) {
        if (busUtilsTransformFile.getPath().endsWith(".jar")) {
            String jarPath = busUtilsTransformFile.getAbsolutePath()
            String decompressedJarPath = jarPath.substring(0, jarPath.length() - 4);
            File decompressedJar = new File(decompressedJarPath)
            ZipUtils.unzipFile(busUtilsTransformFile, decompressedJar)

            File busUtilsFile = new File(
                    decompressedJarPath + Config.FILE_SEP +
                            busUtilsClass.replace('.', Config.FILE_SEP) + '.class'
            )

            inject2BusUtils(busUtilsFile, busMap, busUtilsClass)

            FileUtils.forceDelete(busUtilsTransformFile)
            ZipUtils.zipFiles(Arrays.asList(decompressedJar.listFiles()), busUtilsTransformFile)
            FileUtils.forceDelete(decompressedJar)
        } else {
            File apiUtilsFile = new File(
                    busUtilsTransformFile.getAbsolutePath() + Config.FILE_SEP +
                            busUtilsClass.replace('.', Config.FILE_SEP) + '.class'
            )

            inject2BusUtils(apiUtilsFile, busMap, busUtilsClass)
        }
    }

    private static void inject2BusUtils(File apiUtilsFile, Map<String, BusInfo> busMap, String busUtilsClass) {
        ClassReader cr = new ClassReader(apiUtilsFile.bytes);
        ClassWriter cw = new ClassWriter(cr, 0);
        ClassVisitor cv = new BusUtilsClassVisitor(cw, busMap, busUtilsClass);
        cr.accept(cv, ClassReader.SKIP_FRAMES);
        FileUtils.writeByteArrayToFile(apiUtilsFile, cw.toByteArray())
    }
}