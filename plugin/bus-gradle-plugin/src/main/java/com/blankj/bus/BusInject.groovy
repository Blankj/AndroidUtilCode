package com.blankj.bus

import com.blankj.util.ZipUtils
import org.apache.commons.io.FileUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes

class BusInject {

    static void start(Map<String, String> busMap, File apiJar) {
        String jarPath = apiJar.getAbsolutePath()
        String decompressedJarPath = jarPath.substring(0, jarPath.length() - 4);
        File decompressedJar = new File(decompressedJarPath)
        ZipUtils.unzipFile(apiJar, decompressedJar)

        File apiUtilsFile = new File(decompressedJarPath + Config.FILE_SEP + Config.BUS_UTILS_CLASS)

        ClassReader cr = new ClassReader(apiUtilsFile.bytes);
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
        ClassVisitor cv = new BusUtilsClassVisitor(cw, busMap);
        cr.accept(cv, Opcodes.ASM5);

        FileUtils.writeByteArrayToFile(apiUtilsFile, cw.toByteArray())

        FileUtils.forceDelete(apiJar)
        ZipUtils.zipFiles(Arrays.asList(decompressedJar.listFiles()), apiJar)
        FileUtils.forceDelete(decompressedJar)
    }
}