package net.lsafer.edgeseek.app.components.page.permissions

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.PowerManager
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import net.lsafer.edgeseek.app.components.lib.SwitchPreferenceListItem
import net.lsafer.edgeseek.app.l10n.strings
import net.lsafer.edgeseek.app.util.observeAsState

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
