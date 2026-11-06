plugins {
    id("java-library")
    id("kotlin")
}

group = providers.gradleProperty("lib.groupId").get()
version = providers.gradleProperty("dokit.compat.version").get()

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(gradleApi())
    implementation(providers.gradleProperty("bcu.plugin.api").get())
    compileOnly("com.android.tools.build:gradle-api:$ANDROID_GRADLE_VERSION")
    implementation(dokit_plugin)
}

mavenPublish()
