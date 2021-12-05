package com.blankj.buildSrc;

import org.gradle.api.Plugin
import org.gradle.api.Project

class BuildSrcPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println 'apply BuildSrcPlugin'
        ModuleCfg.main()
    }
}