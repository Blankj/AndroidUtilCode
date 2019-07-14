package com.blankj.api

import com.blankj.api.util.ZipUtils
import groovy.io.FileType
import org.apache.commons.io.FileUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

class ApiScan {

    Map<String, ApiInfo> apiImplMap = [:]
    List<String> apiClasses = []
    File utilcodeJar

    void scanJar(File jar) {
        File tmp = new File(jar.getParent(), "temp_" + jar.getName())
        List<File> unzipFile = ZipUtils.unzipFile(jar, tmp)
        if (unzipFile != null && unzipFile.size() > 0) {
            scanDir(tmp)
            FileUtils.forceDelete(tmp)
        }
    }

    void scanDir(File root) {
        if (!root.isDirectory()) return
        root.eachFileRecurse(FileType.FILES) { File file ->
            def fileName = file.name
            if (!fileName.endsWith('.class')
                    || fileName.startsWith('R$')
                    || fileName == 'R.class'
                    || fileName == 'BuildConfig.class') {
                return
            }

            ClassReader cr = new ClassReader(file.bytes);
            ClassWriter cw = new ClassWriter(cr, 0);
            ClassVisitor cv = new ApiClassVisitor(cw, apiImplMap, apiClasses);
            cr.accept(cv, ClassReader.SKIP_FRAMES);

            FileUtils.writeByteArrayToFile(file, cw.toByteArray());
        }
    }
}
