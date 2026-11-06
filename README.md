## DoKitPluginCompat

由于 [DoKit](https://github.com/didi/DoKit) 插件官方未适配 AGP 9，本项目通过 [BCU](https://github.com/JasonBtc/BytecodeUtil) 的 `IModifier` 接口代理并驱动 DoKit 的 `Transformer`，提供 AGP 9 兼容能力。当前版本使用 AGP 9.5、Gradle 9.6 和 BytecodeUtil 2.2.0-agp9 构建验证。

本兼容包的主版本和 DoKit 保持一致便于理解和使用，例子：

- 官方插件依赖：io.github.didi.dokit:dokitx-plugin:3.7.1
- 兼容插件依赖：com.github.JasonBtc:DoKitPluginCompat:3.7.1-agp9

**注意！不要同时使用兼容插件和官方插件**



### Compile

如果你要编译本项目，可了解如下工程结构

- `app` 用于演示
- `buildSrc` 管理 maven 发布和版本控制项目统一配置
- `repos` 本地 maven 仓库，便于开发时调试
- `dokit_plugin_compat` 本项目核心实现

在编译本项目前请先在根目录下执行 `gradlew publishAllPublicationsToLocalRepository` 然后重新 sync。



### Quick Start

1. 推荐使用下面所示的最新版本：

   - BCU：[![](https://jitpack.io/v/JasonBtc/BytecodeUtil.svg)](https://jitpack.io/#JasonBtc/BytecodeUtil)
   - DoKitPluginCompat：[![](https://jitpack.io/v/JasonBtc/DoKitPluginCompat.svg)](https://jitpack.io/#JasonBtc/DoKitPluginCompat)

2. 在项目的根 `build.gradle.kts` 中配置如下

   ```kotlin
   // Top-level build file
   buildscript {
       repositories {
           maven { setUrl("https://jitpack.io") }
       }
       
       dependencies {
           // BCU 插件依赖
           classpath("com.github.JasonBtc.BytecodeUtil:plugin:2.2.0-agp9")
           // DoKitPluginCompat 依赖
           classpath("com.github.JasonBtc:DoKitPluginCompat:3.7.1-agp9")
       }
   }
   
   subprojects {
       repositories {
           maven { url 'https://jitpack.io' }
       }
   }
   ```

3. 在 `app` 模块的 `build.gradle.kts` 中的配置如下

   - [kts 脚本写法例子点我](app/build.gradle.kts)
   - [groovy 脚本写法例子点我](app/groovy_demo_build.gradle)

   ```kotlin
   plugins {
       id("com.android.application")
       id("org.jetbrains.kotlin.android")
       // 添加 bcu 插件
       id("bcu-plugin")
       // 添加 dokit 兼容插件
       id("dokit-compat")
   }
   
   // 配置 bcu 插件
   bcu {
       config { variant ->
           loggerLevel = 2
           modifiers = arrayOf(
           	// 添加 DoKit 插装兼容实现
           	Class.forName("com.ysj.lib.dokit.plugin.compat.DoKitTransformCompat"),
           )
       }
       filterNot { variant, entryName ->
           // 请按需配置过滤，可大幅提升编译输速度
           false
       }
   }
   
   // 配置 dokit
   // 详见：https://xingyun.xiaojukeji.com/docs/dokit/#/androidGuide
   dokitExt {
       comm {
           // ...
       }
       slowMethod {
           // ...
       }
   }
   
   ```



### Other

- 如果本项目对你有所帮助，欢迎 start。
- 如果有问题或 bug 欢迎提 issues 给我。
