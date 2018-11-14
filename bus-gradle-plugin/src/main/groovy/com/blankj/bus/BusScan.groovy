package com.blankj.bus

import com.blankj.util.ZipUtils
import com.blankj.utilcode.util.BusUtils
import groovy.io.FileType
import javassist.CtClass
import javassist.CtMethod
import org.apache.commons.io.FileUtils

class BusScan {

    HashMap<String, String> busMap
    List<File> scans
    File busJar

    BusScan() {
        busMap = [:]
        scans = []
    }

    void scanJar(File jar) {
        File tmp = new File(jar.getParent(), "temp_" + jar.getName())
        ZipUtils.unzipFile(jar, tmp)
        scanDir(tmp)
        FileUtils.forceDelete(tmp)
    }

    void scanDir(File root) {
        String rootPath = root.getAbsolutePath()
        if (!rootPath.endsWith(Config.FILE_SEP)) {
            rootPath += Config.FILE_SEP
        }

        if (root.isDirectory()) {
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

                CtClass ctClass = Config.mPool.get(className)

                CtMethod[] methods = ctClass.getDeclaredMethods();
                for (CtMethod method : methods) {
                    if (method.hasAnnotation(BusUtils.Subscribe)) {
                        String name = method.getAnnotation(BusUtils.Subscribe).name()
                        String sign = method.getReturnType().getName() + ' ' + method.getLongName()
                        busMap.put(name, sign)
                    }
                }
            }
        }
    }
}
