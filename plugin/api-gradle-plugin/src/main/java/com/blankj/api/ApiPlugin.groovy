package com.blankj.api

import com.android.build.api.transform.JarInput
import com.blankj.base_transform.BaseTransformPlugin
import com.blankj.base_transform.util.JsonUtils
import org.apache.commons.io.FileUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

import java.util.regex.Pattern

class ApiPlugin extends BaseTransformPlugin<ApiExtension> {

    String apiUtilsClass
    File jsonFile
    File apiUtilsTransformFile
    Map<String, ApiInfo> apiImplMap = [:]
    List<String> apiClasses = []

    @Override
    String getPluginName() {
        return Config.EXT_NAME
    }

    @Override
    void onScanStarted() {
        apiUtilsClass = ext.apiUtilsClass
        if (apiUtilsClass.trim().equals("")) {
            throw new Exception("ApiExtension's apiUtilsClass is empty.")
        }

        jsonFile = new File(mProject.projectDir.getAbsolutePath(), "__api__.json")
        FileUtils.write(jsonFile, "{}")
    }

    @Override
    boolean isIgnoreScan(JarInput input) {
        def jarName = input.name
        if (jarName.contains("utilcode")) {
            return false
        }

        if (ext.onlyScanLibRegex != null && ext.onlyScanLibRegex.trim().length() > 0) {
            return !Pattern.matches(ext.onlyScanLibRegex, jarName)
        }

        if (ext.jumpScanLibRegex != null && ext.jumpScanLibRegex.trim().length() > 0) {
            if (Pattern.matches(ext.jumpScanLibRegex, jarName)) {
                return true
            }
        }

        for (exclude in Config.EXCLUDE_LIBS_START_WITH) {
            if (jarName.startsWith(exclude)) {
                return true
            }
        }
        return false
    }

    @Override
    void scanClassFile(File classFile, String className, File originScannedJarOrDir) {
        if (apiUtilsClass == className) {
            apiUtilsTransformFile = originScannedJarOrDir
            log("<ApiUtils transform file>: $originScannedJarOrDir")
        }

        ClassReader cr = new ClassReader(classFile.bytes);
        ClassWriter cw = new ClassWriter(cr, 0);
        ClassVisitor cv = new ApiClassVisitor(cw, apiImplMap, apiClasses, apiUtilsClass);
        try {
            cr.accept(cv, ClassReader.SKIP_FRAMES);
        } catch (Exception ignore) {
            ignore.printStackTrace()
        }
    }

    @Override
    void onScanFinished() {
        if (apiUtilsTransformFile != null) {
            if (apiClasses.isEmpty()) {
                log("no api.")
            } else {
                Map implApis = [:]
                List<String> noImplApis = []
                apiImplMap.each { key, value ->
                    implApis.put(key, value.toString())
                }
                apiClasses.each {
                    if (!apiImplMap.containsKey(it)) {
                        noImplApis.add(it)
                    }
                }
                Map apiDetails = [:]
                apiDetails.put("ApiUtilsClass", apiUtilsClass)
                apiDetails.put("implApis", implApis)
                apiDetails.put("noImplApis", noImplApis)
                String apiJson = JsonUtils.getFormatJson(apiDetails)
                log(jsonFile.toString() + ": " + apiJson)
                FileUtils.write(jsonFile, apiJson)

                if (noImplApis.size() > 0) {
                    if (ext.abortOnError) {
                        throw new Exception("u should impl these apis: " + noImplApis +
                                "\n u can check it in file: " + jsonFile.toString())
                    }
                }
                ApiInject.start(apiImplMap, apiUtilsTransformFile, apiUtilsClass)
            }
        } else {
            throw new Exception("No ApiUtils of ${apiUtilsClass} in $mProject.")
        }
    }
}