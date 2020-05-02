package com.blankj.bus

import com.android.build.api.transform.JarInput
import com.blankj.base_transform.BaseTransformPlugin
import com.blankj.base_transform.util.JsonUtils
import org.apache.commons.io.FileUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

import java.util.regex.Pattern

class BusPlugin extends BaseTransformPlugin<BusExtension> {

    String busUtilsClass
    File jsonFile
    Map<String, List<BusInfo>> busMap = [:]
    File busUtilsTransformFile

    @Override
    String getPluginName() {
        return Config.EXT_NAME
    }

    @Override
    void onScanStarted() {
        busUtilsClass = ext.busUtilsClass
        if (busUtilsClass.trim().equals("")) {
            throw new Exception("BusExtension's busUtilsClass is empty.")
        }

        jsonFile = new File(mProject.projectDir.getAbsolutePath(), "__bus__.json")
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
        if (busUtilsClass == className) {
            busUtilsTransformFile = originScannedJarOrDir
            log("<BusUtils transform file>: $originScannedJarOrDir")
        }

        ClassReader cr = new ClassReader(classFile.bytes);
        ClassWriter cw = new ClassWriter(cr, 0);
        ClassVisitor cv = new BusClassVisitor(cw, busMap, busUtilsClass);
        try {
            cr.accept(cv, ClassReader.SKIP_FRAMES);
        } catch (Exception ignore) {
            ignore.printStackTrace()
        }
    }

    @Override
    void onScanFinished() {
        if (busUtilsTransformFile != null) {
            if (busMap.isEmpty()) {
                log("no bus.")
            } else {
                busMap.each { String tag, List<BusInfo> infoList ->
                    infoList.sort(new Comparator<BusInfo>() {
                        @Override
                        int compare(BusInfo t0, BusInfo t1) {
                            return t1.priority - t0.priority
                        }
                    })
                }

                Map<String, List<String>> rightBus = [:]
                Map<String, List<String>> wrongBus = [:]
                busMap.each { String tag, List<BusInfo> infoList ->
                    List<String> rightInfoString = []
                    List<String> wrongInfoString = []
                    infoList.each { BusInfo info ->
                        if (info.isParamSizeNoMoreThanOne) {
                            rightInfoString.add(info.toString())
                        } else {
                            wrongInfoString.add(info.toString())
                        }
                    }
                    if (!rightInfoString.isEmpty()) {
                        rightBus.put(tag, rightInfoString)
                    }
                    if (!wrongInfoString.isEmpty()) {
                        wrongBus.put(tag, wrongInfoString)
                    }
                }
                Map busDetails = [:]
                busDetails.put("BusUtilsClass", ext.busUtilsClass)
                busDetails.put("rightBus", rightBus)
                busDetails.put("wrongBus", wrongBus)
                String busJson = JsonUtils.getFormatJson(busDetails)
                log(jsonFile.toString() + ": " + busJson)
                FileUtils.write(jsonFile, busJson)

                if (wrongBus.size() > 0) {
                    if (ext.abortOnError) {
                        throw new Exception("These buses is not right: " + wrongBus +
                                "\n u can check it in file: " + jsonFile.toString())
                    }
                }

                BusInject.start(busMap, busUtilsTransformFile, ext.busUtilsClass)
            }
        } else {
            throw new Exception("No BusUtils of ${ext.busUtilsClass} in $mProject.")
        }
    }
}