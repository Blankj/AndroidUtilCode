package com.blankj.bus

class Config {

    public static final String EXT_NAME = 'bus'

    public static final List<String> EXCEPTS = [
            'com.android.support:',
            'com.android.support.constraint:',
            'android.arch.',
            'com.blankj:',
            'org.jetbrains.kotlin:',
            'org.jetbrains:',
            'com.squareup.'
    ]

    public static final String FILE_SEP = System.getProperty("file.separator")

    public static final String BUS_UTILS_CLASS = 'com.blankj.utilcode.util.BusUtils'
}
