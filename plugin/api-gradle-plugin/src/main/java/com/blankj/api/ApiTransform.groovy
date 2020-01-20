package com.blankj.api

import com.android.build.api.transform.*
import com.android.build.gradle.api.ApplicationVariant
import com.android.build.gradle.internal.pipeline.TransformManager
import com.blankj.api.util.JsonUtils
import com.blankj.api.util.LogUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

import java.util.regex.Pattern

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

                if (jumpScan(jarName, ext)) {
                    LogUtils.l("jump jar: $jarName -> $dest")
                    return
                }

                LogUtils.l("scan jar: $jarName -> $dest")
                apiScan.scanJar(dest)

            }
        }

        if (apiScan.apiClasses.isEmpty()) {
            LogUtils.l("no api.")
        } else {
            print2__api__(apiScan, ext, jsonFile)
        }
        processApiWithAssets(outputProvider, apiScan)

        LogUtils.l(getName() + " finished: " + (System.currentTimeMillis() - stTime) + "ms")
    }

    private static void print2__api__(ApiScan apiScan, ApiExtension ext, File jsonFile) {
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
            LogUtils.w("u should impl these apis: " + noImplApis +
                    "\n u can check it in file: " + jsonFile.toString())
        }
    }


    private void processApiWithAssets(TransformOutputProvider outputProvider, ApiScan apiScan) {
        def dest = outputProvider.getContentLocation(
                Config.API_PATH,
                TransformManager.CONTENT_CLASS,
                TransformManager.PROJECT_ONLY,
                Format.DIRECTORY
        )

        String variantName = ""
        while (dest.getParentFile().getName() != getName()) {
            variantName = dest.getParentFile().getName().capitalize() + variantName
            dest = dest.getParentFile()
        }
        LogUtils.l("get variant name from ${getName()}Dir: " + variantName)

        mProject.android.applicationVariants.all { ApplicationVariant variant ->
            if (variant.name.capitalize() == variantName) {
                File assetsDir = variant.mergeAssetsProvider.get().outputDir.get().asFile
                File apiDir = new File(assetsDir, Config.API_PATH)
                apiDir.deleteDir()
                if (!apiScan.apiImplMap.isEmpty()) {
                    apiDir.mkdirs()
                    LogUtils.l("${apiDir.getAbsolutePath()} -> api inject assets dir")
                    apiScan.apiImplMap.each { key, value ->
                        File apiClassDir = new File(apiDir, key)
                        apiClassDir.mkdir()
                        File apiClassImplFile = new File(apiClassDir, value.getFileDesc())
                        apiClassImplFile.createNewFile()
                    }
                }
            }
        }
    }

    private static jumpScan(String jarName, ApiExtension ext) {
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
}