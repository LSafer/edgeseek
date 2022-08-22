package net.lsafer.edgeseek.model

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import kotlinx.serialization.Serializable
import net.lsafer.datastore.jsonPreference
import net.lsafer.datastore.observeJsonPreference
import net.lsafer.edgeseek.appDataStore

@Serializable
enum class AppTheme {
    System,
    Black,
    Dark,
    Light,
    White
}

/**
 * The data of the application
 */
@Serializable
data class AppData(
    val activated: Boolean = false,
    val autoBoot: Boolean = true,
    val autoBrightness: Boolean = true,
    val edges: List<EdgeData> = listOf(),
    val theme: AppTheme = AppTheme.System
)

@Composable
fun rememberApplicationData(): MutableState<AppData> {
    val context = LocalContext.current
    return context.appDataStore.observeJsonPreference("appData") { AppData() }
}

fun Context.applicationData(): MutableState<AppData> {
    return appDataStore.jsonPreference("appData") { AppData() }
}
