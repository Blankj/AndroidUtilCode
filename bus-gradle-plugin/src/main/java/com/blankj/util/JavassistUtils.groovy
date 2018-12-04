package com.blankj.util

import javassist.ClassPool
import org.gradle.api.Project;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2018/11/25
 *     desc  :
 * </pre>
 */
class JavassistUtils {

    public static ClassPool sPool

    static void init(Project project) {
        sPool = new ClassPool(null)
        sPool.appendSystemPath()
        // 加入本地 android 包
        LogUtils.l(project.android.bootClasspath[0].toString())
        sPool.appendClassPath(project.android.bootClasspath[0].toString())
    }

    static ClassPool getPool() {
        return sPool
    }
}
