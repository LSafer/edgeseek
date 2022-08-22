package net.lsafer.edgeseek.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import net.lsafer.edgeseek.LocalNavController
import net.lsafer.edgeseek.model.AppTheme
import net.lsafer.edgeseek.model.rememberApplicationData
import net.lsafer.edgeseek.ui.widget.preferences.*
import net.lsafer.edgeseek.util.NavigationBarPadding
import net.lsafer.edgeseek.util.StatusBarPadding

const val MainScreenRoute = "main"

@Composable
fun MainScreen() {
    Scaffold(
        Modifier.padding(
            top = StatusBarPadding,
            bottom = NavigationBarPadding
        ),
        content = {
            MainScreenContent()
        }
    )
}

@Composable
fun MainScreenContent() {
    val navController = LocalNavController.current

    val coroutineScope = rememberCoroutineScope()

    var data by rememberApplicationData()

    Column(Modifier.verticalScroll(rememberScrollState())) {
        PreferenceHeader(title = "Edge Seek")
        PreferenceSection(title = "Application")
        SwitchPreference(
            checked = data.activated,
            onCheckedChange = {
                data = data.copy(activated = it)
            },
            title = "Activation",
            summary = "Toggle to activate or deactivate the application"
        )
        ListPreference(
            value = data.theme,
            values = listOf(
                AppTheme.System to "System",
                AppTheme.Black to "Black",
                AppTheme.Dark to "Dark",
                AppTheme.Light to "Light",
                AppTheme.White to "White",
            ),
            onValueChange = { data = data.copy(theme = it) },
            title = "Theme"
        )
        PreferenceDivider()
        PreferenceSection(title = "Job")
        Preference(
            title = "Edges",
            summary = "Customize the edges",
            onClick = {
                coroutineScope.launch {
                    navController.navigate(EdgesScreenRoute)
                }
            }
        )
        SwitchPreference(
            checked = data.autoBoot,
            onCheckedChange = { data = data.copy(autoBoot = it) },
            title = "Auto Boot",
            summary = "Auto boot the service every time the device booted-up"
        )
        SwitchPreference(
            checked = data.autoBrightness,
            onCheckedChange = { data = data.copy(autoBrightness = it) },
            title = "Auto Brightness",
            summary = "Turn on auto brightness each time the device turn on to sleep"
        )
        PreferenceDivider()
        PreferenceSection(title = "Misc")
        Preference(
            title = "Permissions",
            summary = "Manage application's permissions",
            onClick = {
                coroutineScope.launch {
                    navController.navigate(PermissionsScreenRoute)
                }
            }
        )
        Preference(
            title = "About",
            summary = "Information about this application",
            onClick = {
                coroutineScope.launch {
                    navController.navigate(AboutScreenRoute)
                }
            }
        )
        Spacer(Modifier.height(50.dp))
    }
}
