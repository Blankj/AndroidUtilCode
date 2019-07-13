package com.blankj.api

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.blankj.api.util.JsonUtils
import com.blankj.api.util.LogUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

class ApiTransform extends Transform {

    Project mProject;

    ApiTransform(Project project) {
        mProject = project
    }

    @Override
    String getName() {
        return "apiTransform"
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
        File jsonFile = new File(mProject.projectDir.getAbsolutePath(), "__api__.json")
        FileUtils.write(jsonFile, "{}")

        long stTime = System.currentTimeMillis()

        def inputs = transformInvocation.getInputs()
        def referencedInputs = transformInvocation.getReferencedInputs()
        def outputProvider = transformInvocation.getOutputProvider()
        def isIncremental = transformInvocation.isIncremental()

        outputProvider.deleteAll()

        ApiScan apiScan = new ApiScan()

        inputs.each { TransformInput input ->
            input.directoryInputs.each { DirectoryInput dirInput ->// 遍历文件夹
                File dir = dirInput.file

                def dest = outputProvider.getContentLocation(
                        dirInput.name,
                        dirInput.contentTypes,
                        dirInput.scopes,
                        Format.DIRECTORY
                )
                FileUtils.copyDirectory(dir, dest)

                LogUtils.l("scan dir: $dir [$dest]")

                apiScan.scanDir(dir)
            }
            input.jarInputs.each { JarInput jarInput ->// 遍历 jar 文件
                File jar = jarInput.file

                def jarName = jarInput.name
                def dest = outputProvider.getContentLocation(
                        jarName,
                        jarInput.contentTypes,
                        jarInput.scopes,
                        Format.JAR
                )
                FileUtils.copyFile(jar, dest)

                if (jarName.startsWith("com.blankj:utilcode:")
                        || jarName.startsWith("com.blankj:utilcodex:")
                        || jarName.equals(":lib:utilcode")) {
                    apiScan.utilcodeJar = dest
                    LogUtils.l("utilcode jar: $jarName [$dest]")
                    return
                }

                if (jumpScan(jarName)) {
                    LogUtils.l("jump jar: $jarName [$dest]")
                    return
                }

                LogUtils.l("scan jar: $jarName [$dest]")
                apiScan.scanJar(jar)
            }
        }

        if (apiScan.utilcodeJar != null) {
            if (apiScan.apiClasses.isEmpty()) {
                LogUtils.l("no api.")
            } else {
                List<String> noImplApis = []
                apiScan.apiClasses.each {
                    if (!apiScan.apiImplMap.containsKey(it)) {
                        noImplApis.add(it)
                    }
                }
                Map apiDetails = [:]
                apiDetails.put("implApis", apiScan.apiImplMap)
                apiDetails.put("noImplApis", noImplApis)
                String apiJson = JsonUtils.getFormatJson(apiDetails)
                LogUtils.l(jsonFile.toString() + ": " + apiJson)
                FileUtils.write(jsonFile, apiJson)
                ApiInject.start(apiScan.apiImplMap, apiScan.utilcodeJar)
            }
        } else {
            LogUtils.l('u should <implementation "com.blankj:utilcode(x):1.25.+">')
        }

        LogUtils.l(getName() + " finished: " + (System.currentTimeMillis() - stTime) + "ms")
    }

    private static jumpScan(String jarName) {
        boolean isExcept = false
        for (String except : Config.EXCEPTS) {
            if (jarName.startsWith(except)) {
                isExcept = true
                break
            }
        }
        return isExcept
    }
}