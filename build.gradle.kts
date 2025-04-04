import org.jetbrains.kotlin.gradle.plugin.extraProperties

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.jetbrains.compose) apply false
    alias(libs.plugins.android.application) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
    id("com.google.firebase.crashlytics") version "3.0.3" apply false
}

group = "net.lsafer.edgeseek"
version = "0.3-pre.1"
project.extraProperties.set("version_code", 15)
project.extraProperties.set("application_id", "lsafer.edgeseek")

tasks.wrapper {
    gradleVersion = "8.9"
}

subprojects {
    version = rootProject.version
    group = buildString {
        append(rootProject.group)
        generateSequence(project.parent) { it.parent }
            .forEach {
                append(".")
                append(it.name)
            }
    }
}
