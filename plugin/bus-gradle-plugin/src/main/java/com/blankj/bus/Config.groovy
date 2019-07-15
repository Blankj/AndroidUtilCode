package com.blankj.bus

class Config {

    public static final String EXT_NAME = 'bus'

    public static final List<String> EXCEPTS = [
            'com.android.support:',
            'com.android.support.constraint:',
            'android.arch.',
            'org.jetbrains.kotlin:',
            'org.jetbrains:',
            'com.squareup.'
    ]

    public static final String FILE_SEP = System.getProperty("file.separator")
}
