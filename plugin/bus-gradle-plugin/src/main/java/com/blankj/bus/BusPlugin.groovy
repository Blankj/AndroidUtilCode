package com.blankj.bus

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.blankj.util.LogUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Plugin
import org.gradle.api.Project

class BusPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        if (project.plugins.hasPlugin(AppPlugin)) {
            LogUtils.init(project)
            LogUtils.l('project(' + project.toString() + ') apply api gradle plugin!')
            File jsonFile = new File(project.projectDir.getAbsolutePath(), "__bus__.json")
            FileUtils.write(jsonFile, "{}")

            project.extensions.create(Config.EXT_NAME, BusExtension)
            def android = project.extensions.getByType(AppExtension)
            android.registerTransform(new BusTransform(project))
            project.afterEvaluate {
                def ext = project[Config.EXT_NAME] as BusExtension
            }
        }
    }
}