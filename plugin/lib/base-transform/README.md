# 基础 transform 库

## 背景

api 和 bus 插件存在大量重复代码，所以抽出这么一个基础 transform 库。


## 使用

```groovy
implementation com.blankj:base-transform:1.0
```

写插件直接继承 BaseTransformPlugin 即可，比如 ApiPlugin：
```groovy
class ApiPlugin extends BaseTransformPlugin<ApiExtension> {

    @Override
    String getPluginName() {
        // 获取插件名
    }

    @Override
    void onScanStarted() {
        // 扫描开始的处理
    }

    @Override
    boolean isIgnoreScan(JarInput input) {
        // 对 jar 包进行过滤扫描，
        // 工程中的 module 就是以 : 开头的 jar 包
        // 远端仓库也是 jar 包
    }

    @Override
    void scanClassFile(File classFile, String className, File originScannedJarOrDir) {
        // 扫描到类文件的处理
    }

    @Override
    void onScanFinished() {
        // 扫描结束的处理
    }
}
```

更具体可以参考 ApiPlugin 及 BusPlugin 的源码。


## [Change Log](https://github.com/Blankj/AndroidUtilCode/blob/master/plugin/lib/base-transform/CHANGELOG.md)