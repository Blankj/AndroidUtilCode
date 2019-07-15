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
        long stTime = System.currentTimeMillis()

        def ext = mProject[Config.EXT_NAME] as ApiExtension
        LogUtils.l("apiExtension: $ext")
        if (ext.apiUtilsClass.trim().equals("")) {
            throw new Exception("ApiExtension's apiUtilsClass is empty.")
        }

        File jsonFile = new File(mProject.projectDir.getAbsolutePath(), "__api__.json")
        FileUtils.write(jsonFile, "{}")

        def inputs = transformInvocation.getInputs()
        def referencedInputs = transformInvocation.getReferencedInputs()
        def outputProvider = transformInvocation.getOutputProvider()
        def isIncremental = transformInvocation.isIncremental()

        outputProvider.deleteAll()

        ApiScan apiScan = new ApiScan(ext.apiUtilsClass)

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

                LogUtils.l("scan dir: ${dirInput.file} -> $dest")

                apiScan.scanDir(dest)
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

                if (jumpScan(jarName)) {
                    LogUtils.l("jump jar: $jarName -> $dest")
                    return
                }

                LogUtils.l("scan jar: $jarName -> $dest")
                apiScan.scanJar(dest)

            }
        }

        if (apiScan.apiUtilsTransformFile != null) {
            if (apiScan.apiClasses.isEmpty()) {
                LogUtils.l("no api.")
            } else {
                Map implApis = [:]
                List<String> noImplApis = []
                apiScan.apiImplMap.each { key, value ->
                    implApis.put(key, value.toString())
                }
                apiScan.apiClasses.each {
                    if (!apiScan.apiImplMap.containsKey(it)) {
                        noImplApis.add(it)
                    }
                }
                Map apiDetails = [:]
                apiDetails.put("ApiUtilsClass", ext.apiUtilsClass)
                apiDetails.put("implApis", implApis)
                apiDetails.put("noImplApis", noImplApis)
                String apiJson = JsonUtils.getFormatJson(apiDetails)
                LogUtils.l(jsonFile.toString() + ": " + apiJson)
                FileUtils.write(jsonFile, apiJson)

                if (noImplApis.size() > 0) {
                    if (ext.abortOnError) {
                        throw new Exception("u should impl these apis: " + noImplApis +
                                "\n u can check it in file: " + jsonFile.toString())
                    }
                }
                ApiInject.start(apiScan.apiImplMap, apiScan.apiUtilsTransformFile, ext.apiUtilsClass)
            }
        } else {
            throw new Exception("No ApiUtils of ${ext.apiUtilsClass} in $mProject.")
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