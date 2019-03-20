package com.blankj.bus

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.blankj.util.JavassistUtils
import com.blankj.util.JsonUtils
import com.blankj.util.LogUtils

import org.apache.commons.io.FileUtils
import org.gradle.api.Project

class BusTransform extends Transform {

    Project mProject;

    BusTransform(Project project) {
        mProject = project
    }

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
        JavassistUtils.init(mProject)
        LogUtils.l(getName() + " started")

        long stTime = System.currentTimeMillis();

        def inputs = transformInvocation.getInputs()
        def referencedInputs = transformInvocation.getReferencedInputs()
        def outputProvider = transformInvocation.getOutputProvider()
        def isIncremental = transformInvocation.isIncremental()

        outputProvider.deleteAll()

        BusScan busScan = new BusScan()

        inputs.each { TransformInput input ->
            input.directoryInputs.each { DirectoryInput dirInput ->// 遍历文件夹
                File dir = dirInput.file
                JavassistUtils.getPool().appendClassPath(dir.absolutePath)

                def dest = outputProvider.getContentLocation(
                        dirInput.name,
                        dirInput.contentTypes,
                        dirInput.scopes,
                        Format.DIRECTORY
                )
                FileUtils.copyDirectory(dir, dest)

                LogUtils.l("scan dir: $dir [$dest]")

                busScan.scanDir(dir)
            }
            input.jarInputs.each { JarInput jarInput ->// 遍历 jar 文件
                File jar = jarInput.file
                JavassistUtils.getPool().appendClassPath(jarInput.file.absolutePath)

                def jarName = jarInput.name
                def dest = outputProvider.getContentLocation(
                        jarName,
                        jarInput.contentTypes,
                        jarInput.scopes,
                        Format.JAR
                )
                FileUtils.copyFile(jar, dest)

                if (jarName.startsWith("com.blankj:utilcode:")
                        || jarName.contains("utilcode-lib")) {
                    busScan.busJar = dest
                    LogUtils.l("bus jar: $jarName [$dest]")
                    return
                }

                if (jumpScan(jarName)) {
                    LogUtils.l("jump jar: $jarName [$dest]")
                    return
                }

                LogUtils.l("scan jar: $jarName [$dest]")
                busScan.scanJar(jar)
            }

        }

        if (busScan.busJar != null) {
            File jsonFile = new File(mProject.projectDir.getAbsolutePath(), "__bus__.json")
            String busJson = JsonUtils.getFormatJson(busScan.busMap)
            LogUtils.l(jsonFile.toString() + ": " + busJson)
            FileUtils.write(jsonFile, busJson)
            BusInject.start(busScan.busMap, busScan.busJar)
        } else {
            LogUtils.l('u should <implementation "com.blankj:utilcode:1.22.+">')
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