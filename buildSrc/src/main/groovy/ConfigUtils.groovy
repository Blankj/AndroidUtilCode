import org.apache.commons.io.FileUtils
import org.gradle.BuildListener
import org.gradle.BuildResult
import org.gradle.api.Project
import org.gradle.api.ProjectEvaluationListener
import org.gradle.api.ProjectState
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.initialization.Settings
import org.gradle.api.invocation.Gradle
import org.gradle.api.tasks.TaskState

import java.text.SimpleDateFormat

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/07/13
 *     desc  :
 * </pre>
 */
class ConfigUtils {

    static getApplyPlugins() {
        def plugins = getDepConfigByFilter(new DepConfigFilter() {
            @Override
            boolean accept(String name, DepConfig config) {
                if (!name.startsWith("plugin.")) return false
                if (!config.isApply) return false
                return true
            }
        })
        GLog.d("getApplyPlugins = ${GLog.object2String(plugins)}")
        return plugins
    }

    static getApplyPkgs() {
        def applyPkgs = getDepConfigByFilter(new DepConfigFilter() {
            @Override
            boolean accept(String name, DepConfig config) {
                if (!config.isApply) return false
                return name.endsWith(".pkg")
            }
        })
        GLog.d("getApplyPkgs = ${GLog.object2String(applyPkgs)}")
        return applyPkgs
    }

    static getApplyExports() {
        def applyExports = getDepConfigByFilter(new DepConfigFilter() {
            @Override
            boolean accept(String name, DepConfig config) {
                if (!config.isApply) return false
                return name.endsWith(".export")
            }
        })
        GLog.d("getApplyExports = ${GLog.object2String(applyExports)}")
        return applyExports
    }

    static addBuildListener(Gradle gradle) {
        gradle.addBuildListener(new ConfigBuildListener())
    }

    private static class ConfigBuildListener implements BuildListener {

        private List<TaskInfo> taskInfoList = []
        private long startBuildMillis

        @Override
        void buildStarted(Gradle gradle) {}

        @Override
        void settingsEvaluated(Settings settings) {
            startBuildMillis = System.currentTimeMillis()
            GLog.d("settingsEvaluated")
            includeModule(settings)
        }

        @Override
        void projectsLoaded(Gradle gradle) {
            GLog.d("projectsLoaded")
            generateDep(gradle)

            gradle.addProjectEvaluationListener(new ProjectEvaluationListener() {
                @Override
                void beforeEvaluate(Project project) {
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
                void afterEvaluate(Project project, ProjectState projectState) {
                }
            })
        }

        @Override
        void projectsEvaluated(Gradle gradle) {
            GLog.d("projectsEvaluated")

            gradle.addListener(new TaskExecutionListener() {
                @Override
                void beforeExecute(Task task) {
                    task.ext.startTime = System.currentTimeMillis()
                }

                @Override
                void afterExecute(Task task, TaskState state) {
                    def exeDuration = System.currentTimeMillis() - task.ext.startTime
                    if (exeDuration >= 100) {
                        taskInfoList.add(new TaskInfo(task, exeDuration))
                    }
                }
            })
        }

        @Override
        void buildFinished(BuildResult result) {
            GLog.d("buildFinished")
            if (!taskInfoList.isEmpty()) {
                Collections.sort(taskInfoList, new Comparator<TaskInfo>() {
                    @Override
                    int compare(TaskInfo t, TaskInfo t1) {
                        return t1.exeDuration - t.exeDuration
                    }
                })
                StringBuilder sb = new StringBuilder()
                int buildSec = (System.currentTimeMillis() - startBuildMillis) / 1000;
                int m = buildSec / 60;
                int s = buildSec % 60;
                def timeInfo = (m == 0 ? "${s}s" : "${m}m ${s}s (${buildSec}s)")
                sb.append("BUILD FINISHED in $timeInfo\n")
                taskInfoList.each {
                    sb.append(String.format("%7sms %s\n", it.exeDuration, it.task.path))
                }
                def content = sb.toString()
                GLog.l(content)
                File file = new File(result.gradle.rootProject.buildDir.getAbsolutePath(),
                        "build_time_records_" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".txt")
                FileUtils.write(file, content)
            }
        }

        /**
         * 在 settings.gradle 中 根据 appConfig 和 pkgConfig 来 include 本地模块
         */
        private static includeModule(Settings settings) {
            if (Config.pkgConfig.isEmpty()) {
                Config.depConfig.feature.mock.isApply = false
            }
            def config = getDepConfigByFilter(new DepConfigFilter() {
                @Override
                boolean accept(String name, DepConfig config) {
                    if (name.endsWith('.app')) {
                        def appName = name.substring('feature.'.length(), name.length() - 4)
                        if (!Config.appConfig.contains(appName)) {
                            config.isApply = false
                        }
                    }
                    if (name.endsWith('.pkg')) {
                        if (!Config.pkgConfig.isEmpty()) {
                            def pkgName = name.substring('feature.'.length(), name.length() - 4)
                            if (!Config.pkgConfig.contains(pkgName)) {
                                config.isApply = false
                            }
                        }
                    }

                    if (!config.isApply) return false
                    if (!config.useLocal) return false
                    if (config.localPath == "") return false
                    return true
                }
            }).each { _, cfg ->
                settings.include cfg.localPath
            }
            GLog.l("includeModule = ${GLog.object2String(config)}")
        }

        /**
         * 根据 depConfig 生成 dep
         */
        private static generateDep(Gradle gradle) {
            def config = getDepConfigByFilter(new DepConfigFilter() {
                @Override
                boolean accept(String name, DepConfig config) {
                    if (name.startsWith("plugin.")) {
                        config.dep = config.remotePath
                        return true
                    }
                    if (config.useLocal) {
                        config.dep = gradle.rootProject.findProject(config.localPath)
                    } else {
                        config.dep = config.remotePath
                    }
                    return true
                }
            })
            GLog.l("generateDep = ${GLog.object2String(config)}")
        }

        private static class TaskInfo {

            Task task
            long exeDuration

            TaskInfo(Task task, long exeDuration) {
                this.task = task
                this.exeDuration = exeDuration
            }
        }
    }

    static Map<String, DepConfig> getDepConfigByFilter(DepConfigFilter filter) {
        return _getDepConfigByFilter("", Config.depConfig, filter)
    }

    private static _getDepConfigByFilter(String namePrefix, Map map, DepConfigFilter filter) {
        def depConfigList = [:]
        for (Map.Entry entry : map.entrySet()) {
            def (name, value) = [entry.getKey(), entry.getValue()]
            if (value instanceof Map) {
                namePrefix += (name + '.')
                depConfigList.putAll(_getDepConfigByFilter(namePrefix, value, filter))
                namePrefix -= (name + '.')
                continue
            }
            def config = value as DepConfig
            if (filter == null || filter.accept(namePrefix + name, config)) {
                depConfigList.put(namePrefix + name, config)
            }
        }
        return depConfigList
    }

    interface DepConfigFilter {
        boolean accept(String name, DepConfig config);
    }
}
