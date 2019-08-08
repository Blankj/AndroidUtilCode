/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/07/13
 *     desc  :
 * </pre>
 */
class Config {

    static applicationId = 'com.blankj.androidutilcode'
    static appName = 'Util'

    static compileSdkVersion = 28
    static minSdkVersion = 14
    static targetSdkVersion = 28
    static versionCode = 1_025_007
    static versionName = '1.25.7'// E.g. 1.9.72 => 1,009,072

    // lib version
    static kotlin_version = '1.3.10'
    static support_version = '28.0.0'
    static leakcanary_version = '1.6.3'

    // appConfig 配置的是可以跑 app 的模块，git 提交务必只包含 launcher
    static appConfig = ['launcher']
    // pkgConfig 配置的是要依赖的功能包，为空则依赖全部，git 提交务必为空
    static pkgConfig = []

    static depConfig = [
            plugin           : [
                    gradle : new DepConfig("com.android.tools.build:gradle:3.4.2"),
                    kotlin : new DepConfig("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"),
                    maven  : new DepConfig("com.github.dcendents:android-maven-gradle-plugin:2.1"),// 上传到 maven
                    bintray: new DepConfig("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.4"),// 上传到 bintray
                    traute : new DepConfig("tech.harmonysoft:traute-gradle:1.1.10"),// 注解转非空判断

                    // 本地第一次上传插件新的版本需设置 useLocal = true, isApply = false
                    // 本地上传成功之后 isApply = true 即可应用插件来调试，后续版本更新无需设置 isApply = false
                    // 发布版本的话把 useLocal = false, isApply = false，更新版本号，发布成功后 isApply = true 即可使用远程库版本
                    api    : new DepConfig(false/*是否本地调试*/, "com.blankj:api-gradle-plugin:1.0", true/*是否使用插件*/),
                    bus    : new DepConfig(false/*是否本地调试*/, "com.blankj:bus-gradle-plugin:2.1", true/*是否使用插件*/),
            ],

            api_gradle_plugin: new DepConfig(":plugin:api-gradle-plugin", false),
            bus_gradle_plugin: new DepConfig(":plugin:bus-gradle-plugin", false),

            feature          : [
                    mock    : new DepConfig(":feature:mock"),

                    launcher: [
                            app: new DepConfig(":feature:launcher:app")
                    ],

                    main    : [
                            app: new DepConfig(":feature:main:app"),
                            pkg: new DepConfig(":feature:main:pkg"),
                    ],

                    subutil : [
                            app   : new DepConfig(":feature:subutil:app"),
                            pkg   : new DepConfig(":feature:subutil:pkg"),
                            export: new DepConfig(":feature:subutil:export"),
                    ],

                    utilcode: [
                            app   : new DepConfig(":feature:utilcode:app"),
                            pkg   : new DepConfig(":feature:utilcode:pkg"),
                            export: new DepConfig(":feature:utilcode:export"),
                    ],
            ],

            lib              : [
                    base     : new DepConfig(":lib:base"),
                    common   : new DepConfig(":lib:common"),
                    subutil  : new DepConfig(":lib:subutil"),
                    utilcode : new DepConfig(true/*是否本地调试*/, ":lib:utilcode", "com.blankj:utilcode:$versionName"),
                    utildebug: new DepConfig(true/*是否本地调试*/, ":lib:utildebug", "com.blankj:utildebug:$versionName"),
            ],

            support          : [
                    appcompat_v7: new DepConfig("com.android.support:appcompat-v7:$support_version"),
                    design      : new DepConfig("com.android.support:design:$support_version"),
                    multidex    : new DepConfig("com.android.support:multidex:1.0.2"),
                    constraint  : new DepConfig("com.android.support.constraint:constraint-layout:1.1.3"),
            ],

            kotlin           : new DepConfig("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"),

            leakcanary       : [
                    android         : new DepConfig("com.squareup.leakcanary:leakcanary-android:$leakcanary_version"),
                    android_no_op   : new DepConfig("com.squareup.leakcanary:leakcanary-android-no-op:$leakcanary_version"),
                    support_fragment: new DepConfig("com.squareup.leakcanary:leakcanary-support-fragment:$leakcanary_version"),
            ],

            free_proguard    : new DepConfig("com.blankj:free-proguard:1.0.1"),
            swipe_panel      : new DepConfig("com.blankj:swipe-panel:1.1"),

            gson             : new DepConfig("com.google.code.gson:gson:2.8.2"),
            glide            : new DepConfig("com.github.bumptech.glide:glide:4.7.1"),
            retrofit         : new DepConfig("com.squareup.retrofit2:retrofit:2.4.0"),
            commons_io       : new DepConfig("commons-io:commons-io:2.6"),
            eventbus         : [
                    lib      : new DepConfig("org.greenrobot:eventbus:3.1.1"),
                    processor: new DepConfig("org.greenrobot:eventbus-annotation-processor:3.0.1")
            ],

            test             : [
                    junit      : new DepConfig("junit:junit:4.12"),
                    robolectric: new DepConfig("org.robolectric:robolectric:4.2"),
            ],
    ]
}
//./gradlew clean :lib:utilcode:bintrayUpload