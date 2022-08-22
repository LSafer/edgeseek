/*
 *	Copyright 2020-2022 LSafer
 *
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *	    http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 */
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
    val introduced: Boolean = false,
    val autoBoot: Boolean = true,
    val autoBrightness: Boolean = true,
    val edges: List<EdgeData> = listOf(),
    val theme: AppTheme = AppTheme.System
)

@Composable
fun rememberApplicationData(): MutableState<AppData> {
    val context = LocalContext.current
    return context.appDataStore.observeJsonPreference("appData") {
        context.applicationData().value
    }
}

fun Context.applicationData(): MutableState<AppData> {
    return appDataStore.jsonPreference("appData") { AppData() }
}
