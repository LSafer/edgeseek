import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.extraProperties

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.android.application)
    alias(libs.plugins.gmazzo.buildConfig)
}

buildConfig {
    className = "BuildConfig"
    packageName = "net.lsafer.edgeseek.app"
    useKotlinOutput {
        internalVisibility = false
    }
    buildConfigField(
        type = "kotlin.String",
        name = "VERSION",
        value = "\"$version\""
    )
    buildConfigField(
        type = "kotlin.Int",
        name = "VERSION_CODE",
        value = rootProject.extraProperties["version_code"].toString().toInt()
    )
}

kotlin {
    jvmToolchain(17)

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    sourceSets {
        commonMain.dependencies {
            // ##### Official Dependencies #####
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)

            implementation(libs.okio)

            // ##### Builtin Dependencies #####
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.material3AdaptiveNavigationSuite)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            // ##### Internal Dependencies #####

            implementation(libs.extkt.json)

            implementation(libs.lsafer.sundry.compose)
            implementation(libs.lsafer.sundry.compose.adaptive)
            implementation(libs.lsafer.sundry.storage)

            // ##### Community Dependencies #####

            implementation(libs.touchlab.kermit)

            // ##### ANDROID Dependencies #####

            implementation(libs.androidx.lifecycle.runtime.compose)

            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.viewmodel.compose)

            implementation(libs.material3.adaptive)
            implementation(libs.material3.adaptive.layout)
            implementation(libs.material3.adaptive.navigation)

        }
        androidMain.dependencies {
            // ##### ANDROID Dependencies #####

            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.appcompat)
            implementation(libs.androidx.cardview)

            // ##### Community Dependencies #####

            implementation(libs.godaddy.colorpickerCompose)
        }
    }
}

android {
    namespace = "net.lsafer.edgeseek.app"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    dependenciesInfo {
        // Disables dependency metadata when building APKs.
        includeInApk = false
        // Disables dependency metadata when building Android App Bundles.
        includeInBundle = false
    }
    lint {
        baseline = file("lint-baseline.xml")
    }
    defaultConfig {
        applicationId = rootProject.extraProperties["application_id"].toString()
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = rootProject.extraProperties["version_code"].toString().toInt()
        versionName = version.toString()
    }
    packaging {
        resources {
            excludes += "/META-INF/LICENSE.md"
            excludes += "/META-INF/LICENSE-notice.md"
            excludes += "/META-INF/INDEX.LIST"
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/groovy-release-info.properties"
            excludes += "/META-INF/groovy/org.codehaus.groovy.runtime.ExtensionModule"
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
}
