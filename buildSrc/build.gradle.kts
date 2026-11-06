plugins {
    `kotlin-dsl`
}

private val reposDir = File(rootDir, "../repos")

repositories {
    maven { url = reposDir.toURI() }
    maven { setUrl("https://maven.aliyun.com/repository/gradle-plugin") }
    maven { setUrl("https://maven.aliyun.com/repository/google") }
    maven { setUrl("https://maven.aliyun.com/repository/central") }
    maven { setUrl("https://maven.aliyun.com/repository/public") }
    maven { setUrl("https://jitpack.io") }
    google()
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation(gradleKotlinDsl())
    implementation("com.android.tools.build:gradle-api:9.5.0")
    val properties = org.jetbrains.kotlin
        .konan.properties
        .loadProperties(File(rootDir, "../gradle.properties").absolutePath)
    val groupId = properties["lib.groupId"] as String
    val dokitCompatVersion = properties["dokit.compat.version"] as String
    val hasPlugin = groupId
        .replace(".", File.separator)
        .let { File(reposDir, it) }
        .takeIf { it.isDirectory }
        ?.list()
        ?.filter { it == "dokit-plugin-compat" }
        .isNullOrEmpty()
        .not()
    if (hasPlugin) {
        implementation("$groupId:dokit-plugin-compat:$dokitCompatVersion")
    }
    implementation(properties["bcu.plugin"] as String)
}
