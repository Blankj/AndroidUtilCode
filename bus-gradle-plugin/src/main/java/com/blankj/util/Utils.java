package com.blankj.util;

import org.gradle.api.Project;

public final class Utils {

    private static Project mProject;

    public static void init(Project project) {
        mProject = project;
    }

    public static Project getProject() {
        return mProject;
    }
}