const val gradle_version = "7.2.2"
const val kotlin_version = "1.7.10"
const val compose_version = "1.3.0"
const val accompanist_version = "0.25.1"

object Dependencies {
    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:$kotlin_version"
        const val serialization = "org.jetbrains.kotlin:kotlin-serialization:$kotlin_version"
        const val gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"

        const val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4"
        const val serialization_json = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.0"
    }

    object Android {
        const val activity_compose = "androidx.activity:activity-compose:1.5.1"
        const val activity_kts = "androidx.activity:activity-ktx:1.5.1"

        const val appcompat = "androidx.appcompat:appcompat:1.5.0"

        const val core_ktx = "androidx.core:core-ktx:1.8.0"

        const val lifecycle_runtime_ktx = "androidx.lifecycle:lifecycle-runtime-ktx:2.5.1"
        const val lifecycle_viewmodel_ktx = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1"

        const val navigation_compose = "androidx.navigation:navigation-compose:2.5.1"

        const val preference_ktx = "androidx.preference:preference-ktx:1.2.0"

        const val datastore = "androidx.datastore:datastore:1.0.0"
        const val datastore_core = "androidx.datastore:datastore-core:1.0.0"
        const val datastore_preferences = "androidx.datastore:datastore-preferences:1.0.0"
        const val datastore_preferences_core = "androidx.datastore:datastore-preferences-core:1.0.0"

        const val espresso_core = "androidx.test.espresso:espresso-core:3.4.0"
        const val junit = "androidx.test.ext:junit:1.1.3"

        const val gradle_build_tools = "com.android.tools.build:gradle:$gradle_version"
    }

    object Compose {
        // foundation
        const val foundation = "androidx.compose.foundation:foundation:1.2.1"

        // material
        const val material = "androidx.compose.material:material:1.2.1"
        const val material_icons_core = "androidx.compose.material:material-icons-core:1.2.1"
        const val material_icons_extended = "androidx.compose.material:material-icons-extended:1.2.1"

        // ui
        const val ui = "androidx.compose.ui:ui:1.2.1"
        const val ui_test_junit4 = "androidx.compose.ui:ui-test-junit4:1.2.1"
        const val ui_tooling = "androidx.compose.ui:ui-tooling:1.2.1"
        const val ui_tooling_preview = "androidx.compose.ui:ui-tooling-preview:1.2.1"
    }

    object Accompanist {
        const val insets = "com.google.accompanist:accompanist-insets:$accompanist_version"
        const val insets_ui = "com.google.accompanist:accompanist-insets-ui:$accompanist_version"
        const val swiperefresh = "com.google.accompanist:accompanist-swiperefresh:$accompanist_version"
        const val systemuicontroller = "com.google.accompanist:accompanist-systemuicontroller:$accompanist_version"
    }

    const val material = "com.google.android.material:material:1.4.0"
    const val color_picker = "com.godaddy.android.colorpicker:compose-color-picker:0.4.2"
    const val eventbus = "org.greenrobot:eventbus:3.3.1"

    const val datastore_ext = "net.lsafer:datastore-ext:3feaa8fcb5"

    const val junit = "junit:junit:4.13.2"
    const val junit_jupiter = "org.junit.jupiter:junit-jupiter:5.9.0"
}
