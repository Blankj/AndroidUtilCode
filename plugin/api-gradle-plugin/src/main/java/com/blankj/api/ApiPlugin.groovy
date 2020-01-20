package com.blankj.api

import com.android.build.gradle.AppExtension
import com.blankj.api.util.LogUtils
import org.gradle.api.Plugin
import org.gradle.api.Project

class ApiPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        if (project.plugins.hasPlugin("com.android.application") || project.plugins.hasPlugin("com.android.dynamic-feature")) {
            LogUtils.init(project)
            LogUtils.l('project(' + project.toString() + ') apply api gradle plugin!')
            project.extensions.create(Config.EXT_NAME, ApiExtension)
            def android = project.extensions.getByType(AppExtension)
            android.registerTransform(new ApiTransform(project))
        }
    }
}