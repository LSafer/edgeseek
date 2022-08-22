@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "lsafer.edgeseek"
        minSdk = 24
        targetSdk = 32
        versionCode = 10
        versionName = "0.2.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = compose_version
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/LICENSE.md"
            excludes += "/META-INF/LICENSE-notice.md"
            excludes += "/META-INF/INDEX.LIST"
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/groovy-release-info.properties"
            excludes += "/META-INF/groovy/org.codehaus.groovy.runtime.ExtensionModule"
        }
    }
}

dependencies {
    // Kotlin
    implementation(Dependencies.Kotlin.stdlib)
    implementation(Dependencies.Kotlin.reflect)
    implementation(Dependencies.Kotlin.serialization_json)

    // Android
    implementation(Dependencies.Android.activity_compose)
    implementation(Dependencies.Android.activity_kts)
    implementation(Dependencies.Android.appcompat)
    implementation(Dependencies.Android.core_ktx)
    implementation(Dependencies.Android.lifecycle_runtime_ktx)
    implementation(Dependencies.Android.navigation_compose)
    implementation(Dependencies.Android.preference_ktx)
    implementation(Dependencies.Android.datastore)
    implementation(Dependencies.Android.datastore_core)
    implementation(Dependencies.Android.datastore_preferences)
    implementation(Dependencies.Android.datastore_preferences_core)

    // Compose
    implementation(Dependencies.Compose.foundation)
    implementation(Dependencies.Compose.material)
    implementation(Dependencies.Compose.material_icons_core)
    implementation(Dependencies.Compose.material_icons_extended)
    implementation(Dependencies.Compose.ui)

    // Accompanist
    implementation(Dependencies.Accompanist.insets)
    implementation(Dependencies.Accompanist.insets_ui)
    implementation(Dependencies.Accompanist.swiperefresh)
    implementation(Dependencies.Accompanist.systemuicontroller)

    // Misc
    implementation(Dependencies.material)
    implementation(Dependencies.color_picker)
    implementation(Dependencies.eventbus)
    implementation(Dependencies.datastore_ext)

    // Debug
    debugImplementation(Dependencies.Compose.ui_tooling)
    debugImplementation(Dependencies.Compose.ui_tooling_preview)

    // Test
    testImplementation(Dependencies.junit)

    // Android Test
    androidTestImplementation(Dependencies.Android.junit)
    androidTestImplementation(Dependencies.Android.espresso_core)
    androidTestImplementation(Dependencies.Compose.ui_test_junit4)
}
