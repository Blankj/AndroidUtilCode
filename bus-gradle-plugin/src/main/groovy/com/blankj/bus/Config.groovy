package com.blankj.bus

import com.blankj.util.Utils
import javassist.ClassPool

class Config {

    public static final String EXT_NAME = 'bus'

    public static final List<String> EXCEPTS = [
            'com.android.support:',
            'com.android.support.constraint:',
            'android.arch.'
    ]

    public static final String FILE_SEP = System.getProperty("file.separator")

    public static final String CLASS_BUS_UTILS = 'com.blankj.utilcode.util.BusUtils'

    public static ClassPool mPool

    static void initClassPool() {
        mPool = new ClassPool(null)
        mPool.appendSystemPath()
        // 加入本地 android 包
        mPool.appendClassPath(Utils.getProject().android.bootClasspath[0].toString())
    }
}
