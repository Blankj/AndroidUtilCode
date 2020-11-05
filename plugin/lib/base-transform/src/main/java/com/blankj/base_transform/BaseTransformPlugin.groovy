package com.blankj.base_transform

import com.android.build.api.transform.*
import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.internal.pipeline.TransformManager
import com.blankj.base_transform.util.LogUtils
import com.blankj.base_transform.util.ZipUtils
import groovy.io.FileType
import org.apache.commons.io.FileUtils
import org.gradle.api.Plugin
import org.gradle.api.Project

import java.lang.reflect.ParameterizedType

abstract class BaseTransformPlugin<T> implements Plugin<Project>, BaseTransformCallback<T> {

    Project mProject

    T getExt() {
        return mProject.getExtensions().getByName(getPluginName())
    }

    @Override
    void apply(Project project) {
        if (project.plugins.hasPlugin(AppPlugin)) {
            mProject = project
            LogUtils.init(project)
            log('project(' + project.toString() + ') apply ' + getPluginName() + ' gradle plugin!')
            project.extensions.create(getPluginName(), getGenericClass())
            def android = project.extensions.getByType(AppExtension)
            android.registerTransform(new BaseTransform())
        }
    }

    Class<T> getGenericClass() {
        return ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]
    }

    class BaseTransform extends Transform {

        @Override
        String getName() {
            return "${getPluginName()}Transform"
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
            log(getName() + " started")
            long stTime = System.currentTimeMillis()

            def inputs = transformInvocation.getInputs()
            def referencedInputs = transformInvocation.getReferencedInputs()
            def outputProvider = transformInvocation.getOutputProvider()
            def isIncremental = transformInvocation.isIncremental()
            outputProvider.deleteAll()

            log("${getPluginName()}Extension: $ext")
            onScanStarted()

            inputs.each { TransformInput input ->
                input.directoryInputs.each { DirectoryInput dirInput ->// 遍历文件夹
                    File dir = dirInput.file
                    File dest = outputProvider.getContentLocation(
                            dirInput.name,
                            dirInput.contentTypes,
                            dirInput.scopes,
                            Format.DIRECTORY
                    )
                    FileUtils.copyDirectory(dir, dest)

                    log("scan dir: ${dirInput.file} -> $dest")
                    scanDir(dest)
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

                    if (isIgnoreScan(jarInput)) {
                        log("jump jar: $jarName -> $dest")
                        return
                    }

                    log("scan jar: $jarName -> $dest")
                    scanJar(dest)
                }
            }

            onScanFinished()
            log(getName() + " finished: " + (System.currentTimeMillis() - stTime) + "ms")
        }


        void scanJar(File jar) {
            File tmp = new File(jar.getParent(), "temp_" + jar.getName())
            List<File> unzipFile = ZipUtils.unzipFile(jar, tmp)
            if (unzipFile != null && unzipFile.size() > 0) {
                scanDir(tmp, jar)
                FileUtils.forceDelete(tmp)
            }
        }

        void scanDir(File root) {
            scanDir(root, root)
        }

        void scanDir(File dir, File originScannedJarOrDir) {
            if (!dir.isDirectory()) return
            String rootPath = dir.getAbsolutePath()
            if (!rootPath.endsWith(BaseTransformConfig.FILE_SEP)) {
                rootPath += BaseTransformConfig.FILE_SEP
            }

            dir.eachFileRecurse(FileType.FILES) { File file ->
                def fileName = file.name
                if (!fileName.endsWith('.class')
                        || fileName.startsWith('R$')
                        || fileName == 'R.class'
                        || fileName == 'BuildConfig.class') {
                    return
                }

                def filePath = file.absolutePath
                def packagePath = filePath.replace(rootPath, '')
                def className = packagePath.replace(BaseTransformConfig.FILE_SEP, ".")
                // delete .class
                className = className.substring(0, className.length() - 6)

                scanClassFile(file, className, originScannedJarOrDir)
            }
        }
    }

    void log(Object obj) {
        LogUtils.l(getPluginName(), obj)
    }
}