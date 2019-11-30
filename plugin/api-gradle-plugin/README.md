# 一学就会的模块间通讯（ApiUtils）

## 背景

随着项目业务越来越多，开发出一套好的组件化方案势在必行，如果还在探寻一套好的组件化架构，那么 **[AucFrame](https://github.com/Blankj/AucFrameTemplate)** 想必会是你的菜。

组件化方案中各业务是相互隔离的，所以两个业务模块要通信的话，就需要通过路由或者接口下沉来完成，业界的方案都无法与 **[AucFrame](https://github.com/Blankj/AucFrameTemplate)** 完美融合，所以我就只好自己动手来完成一个更方便、精简、完美的 `ApiUtils`，它功能类似 SPI，但比 SPI 更适合于 Android，而且功能更强大。

在 **[AucFrame](https://github.com/Blankj/AucFrameTemplate)** 架构中，我们可以通过 `ApiUtils` 来自由调用各模块的 `apis`，各业务通过对外提供的 `export` 模块来供其他业务方使用，自身只需要实现自身的 `export` 中的 `apis` 即可。其 **[AucFrame](https://github.com/Blankj/AucFrameTemplate)** 的架构图如下所示：


![AucFrame](https://raw.githubusercontent.com/Blankj/AndroidUtilCode/master/art/auc_frame.png)

`ApiUtils` 扮演的角色如下所示：

![ApiUtilsRole](https://raw.githubusercontent.com/Blankj/AndroidUtilCode/master/art/communication.png)

图中还有提到 **[BusUtils](https://github.com/Blankj/AndroidUtilCode/blob/master/plugin/bus-gradle-plugin)**，这是一个比 EventBus 更高效的模块内通讯工具，想了解的可以点进去看看哈。当然，`ApiUtils` 不仅在 **[AucFrame](https://github.com/Blankj/AucFrameTemplate)** 中可以使用，在正常项目中你也可以使用它来做业务隔离，下面来介绍其具体使用方式。


## 使用

### 配置

在项目根目录的 `build.gradle` 中添加 `api` 插件：

```groovy
buildscript {
    dependencies {
        ...
        classpath 'com.blankj:api-gradle-plugin:1.2'
    }
}
```

然后在 `application` 模块中使用该插件：

```groovy
apply plugin: "com.blankj.api"
```

给你的项目添加 **[AndroidUtilCode](https://github.com/Blankj/AndroidUtilCode)** 依赖：

```groovy
api "com.blankj:utilcode:latest_version"
```

如果你单纯只想引入 `ApiUtils` 也是可以的，需要你自己拷贝一份这个类放到你工程里，然后在 app 下的 `build.gradle` 中 配置 api 的 DSL 域如下所示：

```groovy
api {
    apiUtilsClass "com.xxx.xxx.ApiUtils"
}

android {
    ...
}
```

可以猜测到默认的 apiUtilsClass 为 `com.blankj.utilcode.util.ApiUtils` 哈。

当然，如果你项目是开启混淆的话，全量引入 **[AndroidUtilCode](https://github.com/Blankj/AndroidUtilCode)** 也是可以的，混淆会帮你去除未使用到的类和方法。

### 例子

插件和依赖都配置完毕，下面就让我们在项目中使用吧，举一个实际的例子，比如 `login` 模块中存在 `LoginActivity`，`main` 模块存在 `MainActivity`，这两个模块是平行的关系，两者互不依赖，现在我们通过 `ApiUtils` 让 `LoginActivity` 来启动 `MainActivity`，在 **[AucFrame](https://github.com/Blankj/AucFrameTemplate)** 中每个业务模块下都有 `export` 模块，类似于你们自己项目中的底层公共模块，因为是 `login` 来调用 `main` 模块，所以是 `mian` 模块需要提供 `api` 来供 `login` 来调，所以我们在 `main` 的 `export` 中加入一个继承自 `ApiUtils.BaseApi` 的抽象类 `MainApi`，并添加启动 `MainActivity` 的抽象方法，我们把方法搞得更复杂点，带上自定义的参数和返回值，具体如下所示：

```java
public abstract class MainApi extends ApiUtils.BaseApi {
    public abstract MainResult startMainActivity(Context context, MainParam param);
}

public class MainParam {

    private String name;

    public MainParam(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

public class MainResult {

    private String name;

    public MainResult(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

接下来我们在 `main` 模块新建 `MainApiImpl` 去实现这个抽象类，但需要额外做一步，**对该实现类加一个 `@ApiUtils.Api` 注解**，该注解是提供给 `api` 插件注入使用的，后面原理分析会提到，而且需要保证只能有一个空参的构造函数，因为后续调用的时候会用无参构造来生成实例，具体如下所示：

```java
@ApiUtils.Api
public class MainApiImpl extends MainApi {
    @Override
    public MainResult startMainActivity(Context context, MainParam param) {
        MainActivity.start(context, param);
        return new MainResult("result");
    }
}
```

关于各模块的 `impl` 我建议最好放在各模块包名的最外层，这样方便打开这个模块源码就能找到这个模块向外暴露的 `apis`。

注解 `@ApiUtils.Api` 中还提供了一个 `isMock` 的值，该值默认是 `false`，所以如上注解相当于 `@ApiUtils.Api(isMock = false)`，该值代表该接口是否用于 mock，一个 `api` 在项目中只有一个实现，所以在单模块调试的时候，该模块在通过 `ApiUtils` 调用其他模块 `api` 的时候，因为其他模块没有依赖进来，所以此时就需要 mock 其他模块的 `api`，防止因为找不到其他模块的 `api` 而 crash，在 **[AucFrame](https://github.com/Blankj/AucFrameTemplate)** 中的 `mock` 层中我们就是这么做的，比如 `login` 模块在单独调试的时候，此时 `main` 模块是不存在的，我们就要写一个 mock 的 `api` 如下所示：

```java
@ApiUtils.Api(isMock = true)
public class MainApiMockImpl extends MainApi {
    @Override
    public MainResult startMainActivity(Context context, MainParam param) {
        ToastUtils.showLong("Start MainActivity succeed.");
        return new MainResult("mock result");
    }
}
```

当 `MainApiImpl` 和 `MainApiMockImpl` 同时存在的时候，`api` 插件会让 `isMock = false` 为最终的 `api` 实现类，也就是 `MainApiImpl`，在 **[AucFrame](https://github.com/Blankj/AucFrameTemplate)** 中，mock 层是在非全量 pkg 的情况下才会参与编译，所以不用担心最终全量的 app 中存有 `mock` 的冗余操作。

下面就让我们在 `login` 中通过 `ApiUtils` 来调用 `mian` 提供的 `api` 吧，如下所示：

```java
MainResult result = ApiUtils.getApi(MainApi.class)
        .startMainActivity(LoginActivity.this, new MainParam("MainParam"));
```

这里说明下，`ApiUtils.getApi(MainApi.class)` 这一步是懒加载的，也就是只会在第一次调用的时候才初始化，所以不用担心初始化的时候把所有业务 `api` 都一股脑实现降低效率的风险。

现在，我们便可以运行一下查看是否可以跳转成功，`api` 插件还会在你的 application 目录下生成一份 `__api__.json` 的 api 列表文件，具体如下所示：

```json
{
  "ApiUtilsClass": "com.blankj.utilcode.util.ApiUtils",
  "implApis": {
    "com/blankj/main/export/api/MainApi": "{ implApiClass: com/blankj/main/pkg/MainApiImpl, isMock: false }"
  },
  "noImplApis": []
}
```

如果项目中并没有实现 `MainApi` 的话，为了确保项目不会因为 `ApiUtils` 在运行时崩溃，`api` 插件会使其在编译时就不通过，此时 `__api__.json` 文件如下所示，提示你需要实现 `MainApi`：

```json
{
  "ApiUtilsClass": "com.blankj.utilcode.util.ApiUtils",
  "implApis": {},
  "noImplApis": [
    "com/blankj/main/export/api/MainApi"
  ]
}
```

好了，使用已经介绍完毕了，看完是不是觉得还不错，6 着干嘛，赶紧扣愣吧，结合 **[AucFrame](https://github.com/Blankj/AucFrameTemplate)** 使用简直美哉哈。


## 规范

要想工具用得舒服，规范肯定要遵守的，所谓无规矩不成方圆，不然五花八门的问题肯定一堆堆，这里推荐如下规范：

* `impl` 和 `api` 应该都是 `public` 的，而且 `impl` 中应该只存在一个无参的 `public` 构造函数（默认不写即可）。
* `api` 中接口的修改我们遵循类似于官方 Android SDK 的升级，大部分情况是新接口的出现需要兼容老接口，如果老接口并不影响功能的正常使用，也就无需通知业务方更新为新接口，新的接口一般都是新的业务方来调用；除非老的接口存有问题或漏洞，我们明确需要删除它，那在删除它的同时，我们还需要把业务中调用老接口的地方统一替换为新的接口，类似于我们升级 Android SDK 的时候，某些 `api` 明确被官方删除了，那我们就需要强行替换为新的接口。还是上面的跳转 `main` 的例子，由于新的业务在跳转的时候需新增一个 `UserInfo` 的参数，具体如下所示：
```java
// old
public abstract class MainApi extends ApiUtils.BaseApi {
    public abstract MainResult startMainActivity(Context context, MainParam param);
}

// good
public abstract class MainApi extends ApiUtils.BaseApi {
    public abstract MainResult startMainActivity(Context context, MainParam param);

    public abstract MainResult startMainActivity(Context context, MainParam param, UserInfo info);
}

// don't
public abstract class MainApi extends ApiUtils.BaseApi {
    public abstract MainResult startMainActivity(Context context, MainParam param, UserInfo info);
}
```
如果删除或修改老的接口的话，会导致其他模块还没来得及更新你的接口，从而调用你老的接口直接编译不过的问题。
* 把 `impl` 放在业务包的最外层，开门见山，方便寻找。
* 如果能结合 **[AucFrame](https://github.com/Blankj/AucFrameTemplate)** 来使用，那就更规范不过了。


## 原理

### api 插件原理分析

api 插件的源码在这里：[api 插件源码传送门](https://github.com/Blankj/AndroidUtilCode/tree/master/plugin/api-gradle-plugin)，该插件通过 Gradle 的 transform 来完成对 `ApiUtils.init()` 做注入，下面来一步步分析：

不明白 transform 的可以先去了解下，简单来说 transform 就是专门用来做字节码插入操作的，最常见的就是 AOP（面向切面编程），这部分我就不科普了，有兴趣的可以自己搜索了解。

说到字节码操作，那就又有知识点了，想要上手快速简单的可以使用 `javassist`，不过，我选择了更强大快速的 `ASM`，这里我就不详细介绍了，有兴趣的可以自己去学习，`ASM` 其实也很简单的，在 [ASM Bytecode Outline](https://plugins.jetbrains.com/plugin/5918-asm-bytecode-outline) 这个插件帮助下写得还是很快的。

通过 ASM 扫描出所有继承自 `ApiUtils.BaseApi` 的类，以及所有带有 `@ApiUtils.Api` 注解的类，然后保存起来，相关代码如下所示：

```java
@Override
public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
    className = name;
    superClassName = superName;
    if ((mApiUtilsClass + "$BaseApi").equals(superName)) {
        mApiClasses.add(name);
    }
    super.visit(version, access, name, signature, superName, interfaces);
}

@Override
public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
    if (("L" + mApiUtilsClass + "$Api;").equals(desc)) {
        hasAnnotation = true;
        return new AnnotationVisitor(Opcodes.ASM5, super.visitAnnotation(desc, visible)) {
            @Override
            public void visit(String name, Object value) {// 可获取注解的值
                isMock = (boolean) value;
                super.visit(name, value);
            }
        };
    }
    return super.visitAnnotation(desc, visible);
}

@Override
public void visitEnd() {
    super.visitEnd();
    if (hasAnnotation) {
        if (!isMock) {// 如果不是 mock 的话
            ApiInfo apiInfo = mApiImplMap.get(superClassName);
            if (apiInfo == null) {
                mApiImplMap.put(superClassName, new ApiInfo(className, false));
            } else {// 存在一个 api 多个实现就报错
                errorStr = "<" + className + "> and <" + apiInfo.implApiClass + "> impl same api of <" + superClassName + ">";
            }
        } else {// mock 的话，如果 map 中已存在就不覆盖了
            if (!mApiImplMap.containsKey(superClassName)) {
                mApiImplMap.put(superClassName, new ApiInfo(className, true));
            }
        }
    }
}
```

然后往 `ApiUtils.init()` 插入扫描出来的内容，比如上面举例的 `MainApi`，那么其最终插入的代码如下所示：

```java
private void init() {
    this.registerImpl(MainApiImpl.class);
}
```

其 ASM 插入的代码如下所示：

```java
@Override
public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
    if (!"init".equals(name)) {
        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }
    // 往 init() 函数中写入
    if (cv == null) return null;
    MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
    mv = new AdviceAdapter(Opcodes.ASM5, mv, access, name, descriptor) {
        @Override
        public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
            return super.visitAnnotation(desc, visible);
        }
        @Override
        protected void onMethodEnter() {
            super.onMethodEnter();
        }
        @Override
        protected void onMethodExit(int opcode) {
            super.onMethodExit(opcode);
            for (Map.Entry<String, ApiInfo> apiImplEntry : mApiImplMap.entrySet()) {
                mv.visitVarInsn(Opcodes.ALOAD, 0);
                mv.visitLdcInsn(Type.getType("L" + apiImplEntry.getValue().implApiClass + ";"));
                mv.visitMethodInsn(Opcodes.INVOKESPECIAL, mApiUtilsClass, "registerImpl", "(Ljava/lang/Class;)V", false);
            }
        }
    };
    return mv;
}
```

### ApiUtils 原理分析

接下来看下 `ApiUtils.registerImpl` 的实现：

```java
private void registerImpl(Class implClass) {
    mInjectApiImplMap.put(implClass.getSuperclass(), implClass);
}
```

很简单，就是往 `mInjectApiImplMap` 中插入了 key 为 `MainApiImpl` 的父类：`MainApi` 的 class，value 为 `MainApiImpl` 的 class。

后面就让我们来看具体调用 `getApi` 的操作吧：

```java
public static <T extends BaseApi> T getApi(@NonNull final Class<T> apiClass) {
    return getInstance().getApiInner(apiClass);
}

private <Result> Result getApiInner(Class apiClass) {
    BaseApi api = mApiMap.get(apiClass);
    if (api == null) {
        synchronized (this) {
            api = mApiMap.get(apiClass);
            if (api == null) {
                Class implClass = mInjectApiImplMap.get(apiClass);
                if (implClass != null) {
                    try {
                        api = (BaseApi) implClass.newInstance();
                        mApiMap.put(apiClass, api);
                    } catch (Exception ignore) {
                        Log.e(TAG, "The <" + implClass + "> has no parameterless constructor.");
                        return null;
                    }
                } else {
                    Log.e(TAG, "The <" + apiClass + "> doesn't implement.");
                    return null;
                }
            }
        }
    }
    //noinspection unchecked
    return (Result) api;
}

public abstract static class BaseApi {
}
```

这段代码很好理解，而且加了同步锁操作，防止多线程生成多个 `impl`，然后，根据传进来的 `api` 的 class，我们通过注入的 `map` 中找到具体的 `impl` 的 class，如果缓存中有就取缓存中的，没有的话就通过 `newInstance` 来实例化一个 `impl`，并放入缓存中，最终返回其 `impl`。因为是通过 `newInstance` 来实例化 `impl`，这也解释了为什么 `impl` 中需保留无参构造函数，而且只有在使用时才会初始化，而不是一股脑把所有的 `api` 都初始化。

简易实用，不到 100 行代码实现模块间跳转的 `ApiUtils` 已介绍完毕，接下来你就可以小试牛刀了。


## [Change Log](https://github.com/Blankj/AndroidUtilCode/blob/master/plugin/api-gradle-plugin/CHANGELOG.md)