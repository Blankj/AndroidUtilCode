package com.blankj.bus

import com.blankj.util.ZipUtils
import groovy.io.FileType
import org.apache.commons.io.FileUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes

class BusScan {

    Map<String, String> busMap = [:]
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
            ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
            ClassVisitor cv = new BusClassVisitor(cw, busMap);
            cr.accept(cv, Opcodes.ASM5);

            FileUtils.writeByteArrayToFile(file, cw.toByteArray());
        }
    }
}
