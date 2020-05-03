package com.blankj.api

import com.blankj.base_transform.BaseTransformConfig

class Config {

    public static final String EXT_NAME = 'api'

    public static final List<String> EXCLUDE_LIBS_START_WITH = [
            'com.android.support',
            'androidx',
            'com.google',
            'android.arch',
            'org.jetbrains',
            'com.squareup',
            'org.greenrobot',
            'com.github.bumptech.glide'
    ]

    public static final String FILE_SEP = BaseTransformConfig.FILE_SEP
}
