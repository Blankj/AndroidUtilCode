class ModuleConfig {

    boolean isApply   // 是否应用
    boolean useLocal  // 是否使用本地的
    String localPath  // 本地路径
    String remotePath // 远程路径
    def dep          // 根据条件生成项目最终的依赖项

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
        return "ModuleConfig { isApply = ${getFlag(isApply)}" +
                ", dep = " + dep +
                " }"
    }

    static String getFlag(boolean b) {
        return b ? "✅" : "❌"
    }
}

