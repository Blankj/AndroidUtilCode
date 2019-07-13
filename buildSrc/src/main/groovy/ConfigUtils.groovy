import org.gradle.api.initialization.Settings
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

    /**
     * 在 settings.gradle 中 根据 appConfig 和 pkgConfig 来 include 本地模块
     */
    static includeModule(Settings settings) {
        def config = getDepConfigByFilter(new DepConfigFilter() {
            @Override
            boolean accept(String name, DepConfig config) {
                if (name.endsWith('.app')) {
                    def appName = name.substring('feature.'.length(), name.length() - 4)
                    if (!Config.appConfig.contains(appName)) {
                        config.isApply = false
                    }
                }
                if (!Config.pkgConfig.isEmpty()) {
                    if (name.endsWith('.pkg')) {
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
        GLog.log("includeModule = ${GLog.object2String(config)}")
    }

    /**
     * 根据 depConfig 生成 dep
     */
    static generateDep(Gradle gradle) {
        def config = getDepConfigByFilter(new DepConfigFilter() {
            @Override
            boolean accept(String name, DepConfig config) {
                if (config.useLocal) {
                    if (config.localPath == "") return false
                    config.dep = gradle.rootProject.findProject(config.localPath)
                } else {
                    config.dep = config.remotePath
                }
                return true
            }
        })
        GLog.log("generateDep = ${GLog.object2String(config)}")
    }

    static getApplyPkgs() {
        return getDepConfigByFilter(new DepConfigFilter() {
            @Override
            boolean accept(String name, DepConfig config) {
                if (!config.isApply) return false
                return name.endsWith(".pkg")
            }
        })
    }

    static getApplyExports() {
        return getDepConfigByFilter(new DepConfigFilter() {
            @Override
            boolean accept(String name, DepConfig config) {
                if (!config.isApply) return false
                return name.endsWith(".export")
            }
        })
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
}
