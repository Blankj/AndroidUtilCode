package com.blankj.bus

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.blankj.util.JsonUtils
import com.blankj.util.LogUtils
import com.blankj.util.Utils
import org.apache.commons.io.FileUtils

class BusTransform extends Transform {

    @Override
    String getName() {
        return "busTransform"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation)
            throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        LogUtils.l(getName() + " started")

        long stTime = System.currentTimeMillis();

        def inputs = transformInvocation.getInputs()
        def referencedInputs = transformInvocation.getReferencedInputs()
        def outputProvider = transformInvocation.getOutputProvider()
        def isIncremental = transformInvocation.isIncremental()

        outputProvider.deleteAll()

        Config.initClassPool()
        BusScan busScan = new BusScan()

        inputs.each { TransformInput input ->
            input.directoryInputs.each { DirectoryInput dirInput ->// 遍历文件夹
                File dir = dirInput.file
                Config.mPool.appendClassPath(dir.absolutePath)

                def dest = outputProvider.getContentLocation(
                        dirInput.name,
                        dirInput.contentTypes,
                        dirInput.scopes,
                        Format.DIRECTORY
                )
                FileUtils.copyDirectory(dir, dest)

                LogUtils.l("scan dir: " + dirInput.name)

                busScan.scanDir(dir)
            }

            input.jarInputs.each { JarInput jarInput ->// 遍历 jar 文件
                File jar = jarInput.file
                Config.mPool.appendClassPath(jarInput.file.absolutePath)

                def jarName = jarInput.name
                def dest = outputProvider.getContentLocation(
                        jarName,
                        jarInput.contentTypes,
                        jarInput.scopes,
                        Format.JAR
                )
                FileUtils.copyFile(jar, dest)

                if (jumpScan(jarName)) {
                    LogUtils.l("jump jar: " + jarName)
                    return
                }

                if (jarName.startsWith("com.blankj:utilcode:")
                        || jarName.contains("utilcode-lib")) {
                    busScan.busJar = dest
                    return
                }

                LogUtils.l("scan jar: " + jarName)
                busScan.scanJar(jar)
            }
        }

        if (busScan.busJar != null) {
            File jsonFile = new File(Utils.project.projectDir.getAbsolutePath(), "__bus__.json")
            String busJson = JsonUtils.getFormatJson(busScan.busMap)
            FileUtils.write(jsonFile, busJson)
            LogUtils.l(busJson)
            BusInject.start(busScan.busMap, busScan.busJar)
        } else {
            LogUtils.l('u should <implementation "com.blankj:utilcode:1.30.+"> ' +
                       'or <implementation "com.blankj:bus:1.0+">')
        }

        LogUtils.l(getName() + " finished: " + (System.currentTimeMillis() - stTime) + "ms")
    }

    private static boolean jumpScan(String jarName) {
        boolean isExcept = false
        for (String except : Config.EXCEPTS) {
            if (jarName.startsWith(except)) {
                isExcept = true
                break
            }
        }
        isExcept
    }
}