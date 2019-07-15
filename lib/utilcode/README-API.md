# ApiUtils

## 关于

组件化开发会涉及到模块与模块之的相互调用，而各模块之间又是解偶的，所以就产生了很多路由方案，或者是把接口下沉到 `base` 组件中，在 AucFrame 架构中，我们可以通过 ApiUtils 来自由调用各模块的 APIs，各业务通过对外提供 export 模块来供其他业务方使用，自身实现 export 中的接口即可。AucFrame 结构如下图所示：

![AucFrame](https://raw.githubusercontent.com/Blankj/AndroidUtilCode/master/art/auc_frame.png)

ApiUtils 扮演的角色如下所示：

![ApiUtilsPlayer](https://raw.githubusercontent.com/Blankj/AndroidUtilCode/master/art/communication.png)

当然，在正常项目中你也可以使用 ApiUtils，下面来介绍其使用方式。


## 基本使用

在项目根目录的 `build.gradle` 中添加 `api` 插件：

```groovy
buildscript {
    dependencies {
        ...
        classpath 'com.blankj:api-gradle-plugin:1.0'
    }
}
```

然后在 application 模块中使用该插件：

```groovy
apply plugin: "com.blankj.api"
```

给 base 模块添加 [AndroidUtilCode](https://github.com/Blankj/AndroidUtilCode) 依赖：

```groovy
api "com.blankj:utilcode:1.25.0"
```

比如 feature0 中存在的 `Feature0Activity.java`，我们通常都是在它内部写一个 `start` 函数来启动它，现在我们通过 ApiUtils 来启动它，建立一个抽象类 `Feature0Api` 如下所示：

```java

```

在其他模块通过 `BusUtils.post("startModule0", Context, String, int)` 即可访问到它，一定要注意 `name` 之后的参数顺序和个数一定要和前面声明的函数相一致，其返回值也就是前面函数的返回值：

```java
// java
boolean api = BusUtils.post("startModule0", context, "blankj", 18);

// kotlin
val result = BusUtils.post("startModule0", context, "blankj", 18)
```

点击编译之后会在该 application 模块中生成 `__bus__.json` 文件

```txt
{
  "startModule0": "boolean com.blankj.module0.Module0Activity.start(android.content.Context,java.lang.String,int)"
}
```


## 高级使用

参看本项目的组件化即可。

主要文件：settings.gradle




[logo]: https://raw.githubusercontent.com/Blankj/AndroidUtilCode/master/art/logo_static_bus.png
[bus]: https://github.com/Blankj/AndroidUtilCode/utilcode/README-STATIC-BUS.md