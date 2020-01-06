import org.gradle.api.Project
import org.gradle.api.ProjectEvaluationListener
import org.gradle.api.ProjectState
import org.gradle.api.invocation.Gradle

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/07/13
 *     desc  :
 * </pre>
 */
class ConfigUtils {

    static init(Gradle gradle) {
        generateDep(gradle)
        addCommonGradle(gradle)
        TaskDurationUtils.init(gradle)
        GitUtils.init(gradle)
    }

    /**
     * 根据 depConfig 生成 dep
     */
    private static void generateDep(Gradle gradle) {
        def configs = [:]
        for (Map.Entry<String, DepConfig> entry : Config.depConfig.entrySet()) {
            def (name, config) = [entry.key, entry.value]
            if (name.startsWith("plugin_")) {
                config.dep = config.pluginPath
            } else {
                if (config.useLocal) {
                    config.dep = gradle.rootProject.findProject(config.localPath)
                } else {
                    config.dep = config.remotePath
                }
            }
            configs.put(name, config)
        }
        GLog.l("generateDep = ${GLog.object2String(configs)}")
    }

    private static addCommonGradle(Gradle gradle) {
        gradle.addProjectEvaluationListener(new ProjectEvaluationListener() {
            @Override
            void beforeEvaluate(Project project) {
                // 在 project 的 build.gradle 前 do sth.
                if (project.subprojects.isEmpty()) {
                    if (project.path.contains(":plugin:")) {
                        return
                    }
                    if (project.name == "app") {
                        GLog.l(project.toString() + " applies buildApp.gradle")
                        project.apply {
                            from "${project.rootDir.path}/buildApp.gradle"
                        }
                    } else {
                        GLog.l(project.toString() + " applies buildLib.gradle")
                        project.apply {
                            from "${project.rootDir.path}/buildLib.gradle"
                        }
                    }
                }
            }

            @Override
            void afterEvaluate(Project project, ProjectState state) {
                // 在 project 的 build.gradle 末 do sth.
            }
        })
    }

    static getApplyPlugins() {
        def plugins = [:]
        for (Map.Entry<String, DepConfig> entry : Config.depConfig.entrySet()) {
            if (entry.value.isApply && entry.value.pluginPath != null) {
                plugins.put(entry.key, entry.value)
            }
        }
        GLog.d("getApplyPlugins = ${GLog.object2String(plugins)}")
        return plugins
    }

    static getApplyPkgs() {
        def pkgs = [:]
        for (Map.Entry<String, DepConfig> entry : Config.depConfig.entrySet()) {
            if (entry.value.isApply && entry.key.endsWith("_pkg")) {
                pkgs.put(entry.key, entry.value)
            }
        }
        GLog.d("getApplyPkgs = ${GLog.object2String(pkgs)}")
        return pkgs
    }

    static getApplyExports() {
        def exports = [:]
        for (Map.Entry<String, DepConfig> entry : Config.depConfig.entrySet()) {
            if (entry.value.isApply && entry.key.endsWith("_export")) {
                exports.put(entry.key, entry.value)
            }
        }
        GLog.d("getApplyExports = ${GLog.object2String(exports)}")
        return exports
    }
}
