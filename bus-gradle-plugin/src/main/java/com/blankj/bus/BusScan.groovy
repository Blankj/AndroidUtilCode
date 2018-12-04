package com.blankj.bus

import com.blankj.util.JavassistUtils
import com.blankj.util.LogUtils
import com.blankj.util.ZipUtils
import com.blankj.utilcode.util.BusUtils
import groovy.io.FileType
import javassist.CtClass
import javassist.CtField
import javassist.CtMethod
import javassist.NotFoundException
import org.apache.commons.io.FileUtils

import java.lang.reflect.Modifier

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
        List<File> unzipFile = ZipUtils.unzipFile(jar, tmp)
        if (unzipFile != null && unzipFile.size() > 0) {
            scanDir(tmp)
            FileUtils.forceDelete(tmp)
        }
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

                CtClass ctClass = JavassistUtils.getPool().get(className)
                CtMethod[] methods = ctClass.getMethods();
                for (CtMethod method : methods) {
                    if (method.hasAnnotation(BusUtils.Subscribe.class)) {
                        String name = ((BusUtils.Subscribe) method.getAnnotation(BusUtils.Subscribe.class)).name();
                        if (busMap.containsKey(name)) {
                            LogUtils.l("bus of " + name + " has registered: " + method.getLongName());
                            continue;
                        }
                        String methodLongName = method.getLongName();
                        if (Modifier.isStatic(method.getModifiers())) {
                            String sign = method.getReturnType().getName() + ' ' + methodLongName;
                            busMap.put(name, sign);
                        } else {// may be is kotlin
                            processKt(method, name, methodLongName)
                        }
                    }
                }
            }
        }
    }

    private void processKt(CtMethod method, String name, String longMethodName) {
        CtClass ktClass = method.getDeclaringClass();
        try {
            CtField instance = ktClass.getField("INSTANCE");
            LogUtils.l("find INSTANCE: " + name + ": " + longMethodName);
            int i = longMethodName.lastIndexOf('(');
            String temp = longMethodName.substring(0, i);
            int j = temp.lastIndexOf('.');
            String sign = method.getReturnType().getName() + ' ' +
                          longMethodName.substring(0, j) +
                          ".INSTANCE" +
                          longMethodName.substring(j);
            busMap.put(name, sign);
        } catch (NotFoundException ignore) {
            String innerClassSimpleName = ktClass.getSimpleName();
            if (innerClassSimpleName.contains('$') && !innerClassSimpleName.endsWith('$')) {
                String innerClassName = ktClass.getName();
                String outerClassName = innerClassName.substring(0, innerClassName.lastIndexOf('$'));
                CtClass outerClass = JavassistUtils.getPool().get(outerClassName);
                try {
                    CtField ctField = outerClass.getField(innerClassSimpleName.substring(innerClassSimpleName.lastIndexOf('$') + 1));
                    String fieldName = ctField.getName();
                    String methodName = longMethodName.replace('$' + fieldName, '.' + fieldName);
                    String sign = method.getReturnType().getName() + ' ' + methodName;
                    busMap.put(name, sign);
                } catch (NotFoundException ignored) {
                    LogUtils.l(longMethodName + "is not static");
                }
            } else {
                LogUtils.l(longMethodName + "is not static");
            }
        }
    }
}
