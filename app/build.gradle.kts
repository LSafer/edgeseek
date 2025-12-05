@file:Suppress("PropertyName")

plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.application)
    alias(libs.plugins.gradleup.patrouille)
    alias(libs.plugins.gmazzo.buildConfig)
}

val pkg = "net.lsafer.edgeseek.app"
val application_id = rootProject.ext["application_id"] as String
val version_code = rootProject.ext["version_code"] as Int
val version_name = project.version.toString()

buildConfig {
    className = "BuildConfig"
    packageName = pkg
    useKotlinOutput { internalVisibility = false }
    buildConfigField("VERSION", version_name)
    buildConfigField("VERSION_CODE", version_code)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xcontext-parameters")
        freeCompilerArgs.add("-Xnested-type-aliases")
        optIn.add("kotlin.time.ExperimentalTime")
        optIn.add("kotlin.uuid.ExperimentalUuidApi")
    }
}

compatPatrouille {
    java(libs.versions.java.get().toInt())
    kotlin(libs.versions.kotlin.get())
}

android {
    namespace = pkg
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = application_id
        versionCode = version_code
        versionName = version_name
    }
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
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
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
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // ##### Official Dependencies #####
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.datetime)

    implementation(libs.okio)

    // ##### Builtin Dependencies #####
    implementation(libs.compose.runtime)
    implementation(libs.compose.foundation)
    implementation(libs.compose.ui)
    implementation(libs.compose.material3)

    // ##### Internal Dependencies #####

    implementation(libs.optionkt)
    implementation(libs.extkt.json)

    implementation(libs.lsafer.compose.simplenav)

    // ##### Community Dependencies #####

    implementation(libs.godaddy.colorpickerCompose)

    // ##### ANDROID Dependencies #####

    implementation(libs.androidx.lifecycle.runtime.compose)

    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.cardview)



    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.5")
}
