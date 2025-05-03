package net.lsafer.edgeseek.app.components.page.permissions

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.PowerManager
import android.provider.Settings
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import net.lsafer.edgeseek.app.MainAccessibilityService
import net.lsafer.edgeseek.app.components.lib.SwitchPreferenceListItem
import net.lsafer.edgeseek.app.l10n.strings
import net.lsafer.edgeseek.app.util.observeAsState

@Composable
fun PermissionsPage_ListItem_allow_restricted_permissions(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val handleOnClick: () -> Unit = {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:${context.packageName}")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
    val handleOnOpenTutorial: () -> Unit = {
        val uri = Uri.parse("https://www.youtube.com/watch?v=28TomZ9tztw")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
    }

    ListItem(
        modifier = Modifier
            .clickable(onClick = handleOnClick)
            .then(modifier),
        headlineContent = { Text(strings.stmt.restricted_permissions_headline) },
        trailingContent = {
            IconButton(handleOnClick) {
                Icon(Icons.Default.Settings, strings.stmt.open_settings)
            }
        },
        supportingContent = {
            Column {
                Text(strings.stmt.restricted_permissions_supporting)

                OutlinedButton(handleOnOpenTutorial, Modifier.padding(8.dp)) {
                    Text(strings.stmt.watch_tutorial)
                }
            }
        }
    )
}

@Composable
fun PermissionsPage_ListItem_display_over_other_apps(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState = lifecycleOwner.lifecycle.observeAsState()

    val isChecked = remember(lifecycleState) {
        Settings.canDrawOverlays(context)
    }

    val handleOnChange = { _: Boolean ->
        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
        intent.data = Uri.parse("package:${context.packageName}")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    SwitchPreferenceListItem(
        value = isChecked,
        onChange = handleOnChange,
        headline = strings.stmt.display_over_other_apps_headline,
        supporting = strings.stmt.display_over_other_apps_supporting,
        modifier = modifier,
    )
}

@Composable
fun PermissionsPage_ListItem_write_system_settings(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState = lifecycleOwner.lifecycle.observeAsState()

    val isChecked = remember(lifecycleState) {
        Settings.System.canWrite(context)
    }

    val handleOnChange = { _: Boolean ->
        val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
        intent.data = Uri.parse("package:${context.packageName}")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    SwitchPreferenceListItem(
        value = isChecked,
        onChange = handleOnChange,
        headline = strings.stmt.write_system_settings_headline,
        supporting = strings.stmt.write_system_settings_supporting,
        modifier = modifier,
    )
}

@Composable
fun PermissionsPage_ListItem_ignore_battery_optimizations(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState = lifecycleOwner.lifecycle.observeAsState()

    val isChecked = remember(lifecycleState) {
        context.getSystemService(PowerManager::class.java)
            .isIgnoringBatteryOptimizations(context.packageName)
    }

    val handleOnChange = { _: Boolean ->
        @SuppressLint("BatteryLife")
        val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
        intent.data = Uri.parse("package:${context.packageName}")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    SwitchPreferenceListItem(
        value = isChecked,
        onChange = handleOnChange,
        headline = strings.stmt.ignore_battery_optimizations_headline,
        supporting = strings.stmt.ignore_battery_optimizations_supporting,
        modifier = modifier,
    )
}

@Composable
fun PermissionsPage_ListItem_accessibility_service(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val isChecked by MainAccessibilityService.aliveState.collectAsState(false)

    val handleOnChange = { _: Boolean ->
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        // intent.data = Uri.parse("package:${context.packageName}")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    SwitchPreferenceListItem(
        value = isChecked,
        onChange = handleOnChange,
        headline = strings.stmt.accessibility_service_headline,
        supporting = strings.stmt.accessibility_service_supporting,
        modifier = modifier,
    )
}
