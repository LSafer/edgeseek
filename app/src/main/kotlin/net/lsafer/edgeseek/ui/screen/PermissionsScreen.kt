@file:SuppressLint("BatteryLife")

package net.lsafer.edgeseek.ui.screen

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.PowerManager
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import net.lsafer.edgeseek.ui.widget.preferences.PreferenceDivider
import net.lsafer.edgeseek.ui.widget.preferences.PreferenceHeader
import net.lsafer.edgeseek.ui.widget.preferences.PreferenceSection
import net.lsafer.edgeseek.ui.widget.preferences.SwitchPreference
import net.lsafer.edgeseek.util.NavigationBarPadding
import net.lsafer.edgeseek.util.StatusBarPadding
import net.lsafer.edgeseek.util.observeAsState

const val PermissionsScreenRoute = "permissions"

@Composable
fun PermissionsScreen() {
    Scaffold(
        Modifier.padding(
            top = StatusBarPadding,
            bottom = NavigationBarPadding
        ),
        content = {
            PermissionsScreenContent()
        }
    )
}

@Composable
fun PermissionsScreenContent() {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val lifecycleState = lifecycleOwner.lifecycle.observeAsState()

    val canDisplayOverOtherApps = remember(lifecycleState) {
        Settings.canDrawOverlays(context)
    }
    val canWriteSystemSettings = remember(lifecycleState) {
        Settings.System.canWrite(context)
    }
    val isIgnoreBatteryOptimizations = remember(lifecycleState) {
        context.getSystemService(PowerManager::class.java)
            .isIgnoringBatteryOptimizations(context.packageName)
    }

    fun requestDisplayOverOtherApps() {
        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
        intent.data = Uri.parse("package:${context.packageName}")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun requestWriteSystemSettings() {
        val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
        intent.data = Uri.parse("package:${context.packageName}")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun requestIgnoreBatteryOptimizations() {
        val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
        intent.data = Uri.parse("package:${context.packageName}")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    Column(Modifier.verticalScroll(rememberScrollState())) {
        PreferenceHeader(title = "Permissions")
        PreferenceSection(title = "Mandatory")
        SwitchPreference(
            checked = canDisplayOverOtherApps,
            onCheckedChange = { requestDisplayOverOtherApps() },
            title = "Display Over Other Apps",
            summary = "Allow this application to draw views floating on your screen"
        )
        PreferenceDivider()
        PreferenceSection(title = "Additional")
        SwitchPreference(
            checked = canWriteSystemSettings,
            onCheckedChange = { requestWriteSystemSettings() },
            title = "Write System Settings",
            summary = "Allow this application to edit settings such as brightness level and music volume"
        )
        SwitchPreference(
            checked = isIgnoreBatteryOptimizations,
            onCheckedChange = { requestIgnoreBatteryOptimizations() },
            title = "Ignore Battery Optimizations",
            summary = "Make this application free from the system battery optimizations."
        )
        Spacer(Modifier.height(50.dp))
    }
}
