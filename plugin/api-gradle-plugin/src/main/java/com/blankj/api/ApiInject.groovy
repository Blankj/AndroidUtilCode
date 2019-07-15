package com.blankj.api

import com.blankj.api.util.ZipUtils
import org.apache.commons.io.FileUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

class ApiInject {

    static void start(Map<String, String> apiImplMap, File apiUtilsTransformFile, String busUtilsClass) {
        if (apiUtilsTransformFile.getPath().endsWith(".jar")) {
            String jarPath = apiUtilsTransformFile.getAbsolutePath()
            String decompressedJarPath = jarPath.substring(0, jarPath.length() - 4);
            File decompressedJar = new File(decompressedJarPath)
            ZipUtils.unzipFile(apiUtilsTransformFile, decompressedJar)

            File apiUtilsFile = new File(
                    decompressedJarPath + Config.FILE_SEP +
                            busUtilsClass.replace('.', Config.FILE_SEP) + '.class'
            )

            inject2ApiUtils(apiUtilsFile, apiImplMap, busUtilsClass)

            FileUtils.forceDelete(apiUtilsTransformFile)
            ZipUtils.zipFiles(Arrays.asList(decompressedJar.listFiles()), apiUtilsTransformFile)
            FileUtils.forceDelete(decompressedJar)
        } else {
            File apiUtilsFile = new File(
                    apiUtilsTransformFile.getAbsolutePath() + Config.FILE_SEP +
                            busUtilsClass.replace('.', Config.FILE_SEP) + '.class'
            )

            inject2ApiUtils(apiUtilsFile, apiImplMap, busUtilsClass)
        }
    }

    private static void inject2ApiUtils(File apiUtilsFile, Map<String, String> apiImplMap, String apiUtilsClass) {
        ClassReader cr = new ClassReader(apiUtilsFile.bytes);
        ClassWriter cw = new ClassWriter(cr, 0);
        ClassVisitor cv = new ApiUtilsClassVisitor(cw, apiImplMap, apiUtilsClass);
        cr.accept(cv, ClassReader.SKIP_FRAMES);
        FileUtils.writeByteArrayToFile(apiUtilsFile, cw.toByteArray())
    }
}