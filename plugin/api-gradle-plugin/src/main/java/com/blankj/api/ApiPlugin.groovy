package com.blankj.api

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.blankj.util.LogUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Plugin
import org.gradle.api.Project

class ApiPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        if (project.plugins.hasPlugin(AppPlugin)) {
            LogUtils.init(project)
            LogUtils.l('project(' + project.toString() + ') apply api gradle plugin!')
            File jsonFile = new File(project.projectDir.getAbsolutePath(), "__api__.json")
            FileUtils.write(jsonFile, "{}")

            project.extensions.create(Config.EXT_NAME, ApiExtension)
            def android = project.extensions.getByType(AppExtension)
            android.registerTransform(new ApiTransform(project))
            project.afterEvaluate {
                def ext = project[Config.EXT_NAME] as ApiExtension
            }
        }
    }
}