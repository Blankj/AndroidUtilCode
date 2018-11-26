package com.blankj.bus

class Config {

    public static final String EXT_NAME = 'bus'

    public static final List<String> EXCEPTS = [
            'com.android.support:',
            'com.android.support.constraint:',
            'android.arch.',
            'com.blankj:'
    ]

    public static final String FILE_SEP = System.getProperty("file.separator")

    public static final String CLASS_BUS_UTILS = 'com.blankj.utilcode.util.BusUtils'
}
