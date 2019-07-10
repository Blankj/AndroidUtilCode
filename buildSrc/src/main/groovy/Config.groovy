class Config {

    static applicationId = 'com.blankj.androidutilcode'
    static appName = 'Util'

    static compileSdkVersion = 27
    static minSdkVersion = 14
    static targetSdkVersion = 27
    static versionCode = 1_024_007
    static versionName = '1.24.7'// E.g. 1.9.72 => 1,009,072

    // lib version
    static kotlin_version = '1.3.10'
    static support_version = '27.1.1'
    static leakcanary_version = '1.6.3'

    static depConfig = [
            plugin_gradle              : new DepConfig("com.android.tools.build:gradle:3.4.0"),
            plugin_kotlin              : new DepConfig("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"),
            plugin_maven               : new DepConfig("com.github.dcendents:android-maven-gradle-plugin:2.1"),// 上传到 maven
            plugin_bintray             : new DepConfig("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.4"),// 上传到 bintray
            plugin_traute              : new DepConfig("tech.harmonysoft:traute-gradle:1.1.10"),// 注解转非空判断

            plugin_bus                 : new DepConfig(true/*是否本地调试*/, "", "com.blankj:bus-gradle-plugin:1.8", false),
            bus_gradle_plugin          : new DepConfig(":plugin:bus-gradle-plugin", true),

            plugin_api                 : new DepConfig(true/*是否本地调试*/, "", "com.blankj:api-gradle-plugin:1.0", true),
            api_gradle_plugin          : new DepConfig(":plugin:api-gradle-plugin", true),

            launcher_app               : new DepConfig(":app:launcher:app"),
            launcher_pkg               : new DepConfig(":app:launcher:pkg"),

            mock                       : new DepConfig(":app:mock"),

            main_app                   : new DepConfig(":app:main:app"),
            main_pkg                   : new DepConfig(":app:main:pkg"),

            subutil_app                : new DepConfig(":app:subutil:app"),
            subutil_pkg                : new DepConfig(":app:subutil:pkg"),
            subutil_export             : new DepConfig(":app:subutil:export"),

            utilcode_app               : new DepConfig(":app:utilcode:app"),
            utilcode_pkg               : new DepConfig(":app:utilcode:pkg"),
            utilcode_export            : new DepConfig(":app:utilcode:export"),

            lib_base                   : new DepConfig(":lib:base"),
            lib_common                 : new DepConfig(":lib:common"),
            lib_subutil                : new DepConfig(":lib:subutil"),
            lib_utilcode               : new DepConfig(true/*是否本地调试*/, ":lib:utilcode", "com.blankj:utilcode:$versionName"),

            support_appcompat_v7       : new DepConfig("com.android.support:appcompat-v7:$support_version"),
            support_design             : new DepConfig("com.android.support:design:$support_version"),
            support_multidex           : new DepConfig("com.android.support:multidex:1.0.2"),

            constraint                 : new DepConfig("com.android.support.constraint:constraint-layout:1.1.3"),
            kotlin                     : new DepConfig("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"),

            leakcanary_android         : new DepConfig("com.squareup.leakcanary:leakcanary-android:$leakcanary_version"),
            leakcanary_android_no_op   : new DepConfig("com.squareup.leakcanary:leakcanary-android-no-op:$leakcanary_version"),
            leakcanary_support_fragment: new DepConfig("com.squareup.leakcanary:leakcanary-support-fragment:$leakcanary_version"),

            free_proguard              : new DepConfig("com.blankj:free-proguard:1.0.1"),
            swipe_panel                : new DepConfig("com.blankj:swipe-panel:1.1"),

            gson                       : new DepConfig("com.google.code.gson:gson:2.8.2"),
            glide                      : new DepConfig("com.github.bumptech.glide:glide:4.7.1"),
            retrofit                   : new DepConfig("com.squareup.retrofit2:retrofit:2.4.0"),
            commons_io                 : new DepConfig("commons-io:commons-io:2.5"),

            junit                      : new DepConfig("junit:junit:4.12"),
            robolectric                : new DepConfig("org.robolectric:robolectric:4.2"),
    ]

    static dep = [:]
}
//./gradlew clean :utilcode:lib:bintrayUpload