import org.gradle.BuildListener
import org.gradle.BuildResult
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.initialization.Settings
import org.gradle.api.invocation.Gradle
import org.gradle.api.tasks.TaskState

class BuildConfig {

    static addBuildListener(Gradle gradle) {
        gradle.addBuildListener(new CustomListener())
    }

    static class CustomListener implements BuildListener {

        private timings = []
        private File file

        @Override
        void settingsEvaluated(Settings settings) {
            GLog.log("settingsEvaluated")
            includeConfig(settings)
        }

        @Override
        void projectsLoaded(Gradle gradle) {
            GLog.log("projectsLoaded")
            configDep(gradle)
        }

        @Override
        void projectsEvaluated(Gradle gradle) {
            GLog.log("projectsEvaluated")
        }

        @Override
        void buildStarted(Gradle gradle) {
            GLog.log("buildStarted")

            gradle.addListener(new TaskExecutionListener() {

                @Override
                void beforeExecute(Task task) {
                    GLog.log("beforeExecute")
                    task.ext.startTime = System.currentTimeMillis()
                }

                @Override
                void afterExecute(Task task, TaskState state) {
                    GLog.log("afterExecute")
                    def ms = System.currentTimeMillis() - task.ext.startTime
                    timings.add([ms, task.path])
                }
            })

            def sdf = new java.text.SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")
            file = new File(rootProject.buildDir.getAbsolutePath(),
                    "buildTime_" + sdf.format(new Date(System.currentTimeMillis())) + ".txt")
        }

        @Override
        void buildFinished(BuildResult result) {
            ArrayList<Long> array = new ArrayList()
            for (timing in timings) {
                if (timing[0] >= 100) {
                    array.add(timing)
                }
            }
            if (!array.isEmpty()) {
                Collections.sort(array)
                StringBuilder sb = new StringBuilder()
                array.reverseEach {
                    sb.append(String.format("%7sms  %s\n", it[0], it[1]))
                }
                def content = sb.toString()
                GLog.log(content)
                com.android.utils.FileUtils.writeToFile(file, content)
            }
        }

        private static includeConfig(Settings settings) {
            GLog.log("depConfig = ${GLog.object2String(Config.depConfig)}")
            Config.depConfig.each { String name, DepConfig config ->
                if (config.isApply && config.useLocal) {
                    settings.include config.localPath
                }
            }
        }

        /**
         * 根据 depConfig 生成 dep
         */
        private static configDep(Gradle gradle) {
            Config.depConfig.each { name, config ->
                if (!config.isApply) return
                if (config.useLocal) {
                    if (config.localPath == "") return
                    config.dep = gradle.rootProject.findProject(config.localPath)
                    Config.dep[name] = config.dep
                } else {
                    config.dep = config.remotePath
                    Config.dep[name] = config.dep
                }
            }
            GLog.log("dep = ${GLog.object2String(Config.dep)}")
        }
    }
}