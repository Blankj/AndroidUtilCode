package com.blankj.api

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.blankj.api.util.LogUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Plugin
import org.gradle.api.Project

class ApiPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        if (project.plugins.hasPlugin(AppPlugin)) {
            LogUtils.init(project)
            LogUtils.l('project(' + project.toString() + ') apply api gradle plugin!')
            project.extensions.create(Config.EXT_NAME, ApiExtension)
            def android = project.extensions.getByType(AppExtension)
            android.registerTransform(new ApiTransform(project))
        }
    }
}