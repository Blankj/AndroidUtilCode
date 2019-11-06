package com.blankj.bus

import com.blankj.bus.util.LogUtils
import com.blankj.bus.util.ZipUtils
import groovy.io.FileType
import org.apache.commons.io.FileUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

class BusScan {

    Map<String, List<BusInfo>> busMap = [:]
    File busUtilsTransformFile
    String busUtilsClass

    BusScan(String busUtilsClass) {
        this.busUtilsClass = busUtilsClass
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
            if (busUtilsClass == className) {
                busUtilsTransformFile = source
                LogUtils.l("<BusUtils transform file>: $source")
            }

            ClassReader cr = new ClassReader(file.bytes);
            ClassWriter cw = new ClassWriter(cr, 0);
            ClassVisitor cv = new BusClassVisitor(cw, busMap, busUtilsClass);
            try {
                cr.accept(cv, ClassReader.SKIP_FRAMES);
            } catch (Exception ignore) {
                ignore.printStackTrace()
            }
        }
    }
}
