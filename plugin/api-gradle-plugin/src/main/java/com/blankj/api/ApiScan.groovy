package com.blankj.api

import com.blankj.api.util.LogUtils
import com.blankj.api.util.ZipUtils
import groovy.io.FileType
import org.apache.commons.io.FileUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

class ApiScan {

    Map<String, ApiInfo> apiImplMap = [:]
    List<String> apiClasses = []
    File apiUtilsTransformFile
    String apiUtilsClass

    ApiScan(String apiUtilsClass) {
        this.apiUtilsClass = apiUtilsClass
    }


    void scanJar(File jar) {
        File tmp = new File(jar.getParent(), "temp_" + jar.getName())
        List<File> unzipFile = ZipUtils.unzipFile(jar, tmp)
        if (unzipFile != null && unzipFile.size() > 0) {
            scanDir(tmp, jar)
            FileUtils.forceDelete(tmp)
        }
    }

    void scanDir(File root) {
        scanDir(root, root)
    }

    void scanDir(File root, File source) {
        if (!root.isDirectory()) return
        String rootPath = root.getAbsolutePath()
        if (!rootPath.endsWith(Config.FILE_SEP)) {
            rootPath += Config.FILE_SEP
        }

        root.eachFileRecurse(FileType.FILES) { File file ->
            def fileName = file.name
            if (!fileName.endsWith('.class')
                    || fileName.startsWith('R$')
                    || fileName == 'R.class'
                    || fileName == 'BuildConfig.class') {
                return
            }

            def filePath = file.absolutePath
            def packagePath = filePath.replace(rootPath, '')
            def className = packagePath.replace(Config.FILE_SEP, ".")
            // delete .class
            className = className.substring(0, className.length() - 6)
            if (apiUtilsClass == className) {
                apiUtilsTransformFile = source
                LogUtils.l("<ApiUtils transform file>: $source")
            }

            ClassReader cr = new ClassReader(file.bytes);
            ClassWriter cw = new ClassWriter(cr, 0);
            ClassVisitor cv = new ApiClassVisitor(cw, apiImplMap, apiClasses, apiUtilsClass);
            cr.accept(cv, ClassReader.SKIP_FRAMES);

            if (cv.errorStr != null) {
                throw new Exception(cv.errorStr)
            }
        }
    }
}
