/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/07/13
 *     desc  :
 * </pre>
 */
class DepConfig {
    boolean useLocal // 是否使用本地的
    String localPath // 本地路径
    String remotePath// 远程路径
    boolean isApply  // 是否应用
    String path      // 最后的路径
    def dep          // 根据条件生成项目最终的依赖项

    DepConfig(String path) {
        this(path, true)
    }

    DepConfig(String path, boolean isApply) {
        if (path.startsWith(":")) {
            this.useLocal = true
            this.localPath = path
            this.isApply = isApply
        } else {
            this.useLocal = false
            this.remotePath = path
            this.isApply = isApply
        }
        this.path = path
    }

    DepConfig(boolean useLocal, String path, boolean isApply) { // 自定义插件的构造函数
        this(useLocal, "", path, isApply)
    }

    DepConfig(boolean useLocal, String localPath, String remotePath) {
        this(useLocal, localPath, remotePath, true)
    }

    DepConfig(boolean useLocal, String localPath, String remotePath, boolean isApply) {
        this.useLocal = useLocal
        this.localPath = localPath
        this.remotePath = remotePath
        this.isApply = isApply
        this.path = useLocal ? localPath : remotePath
    }

    String getGroupId() {
        String[] splits = remotePath.split(":")
        return splits.length == 3 ? splits[0] : null
    }

    String getArtifactId() {
        String[] splits = remotePath.split(":")
        return splits.length == 3 ? splits[1] : null
    }

    String getVersion() {
        String[] splits = remotePath.split(":")
        return splits.length == 3 ? splits[2] : null
    }


    @Override
    String toString() {
        return "DepConfig { " +
                "useLocal = " + useLocal +
                (dep == null ? ", path = " + path : (", dep = " + dep)) +
                ", isApply = " + isApply +
                " }"
    }
}