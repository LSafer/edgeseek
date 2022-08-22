package net.lsafer.edgeseek.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import net.lsafer.edgeseek.BuildConfig
import net.lsafer.edgeseek.ui.widget.preferences.Preference
import net.lsafer.edgeseek.ui.widget.preferences.PreferenceDivider
import net.lsafer.edgeseek.ui.widget.preferences.PreferenceHeader
import net.lsafer.edgeseek.ui.widget.preferences.PreferenceSection
import net.lsafer.edgeseek.util.NavigationBarPadding
import net.lsafer.edgeseek.util.StatusBarPadding

const val AboutScreenRoute = "about"

@Composable
fun AboutScreen() {
    Scaffold(
        Modifier.padding(
            top = StatusBarPadding,
            bottom = NavigationBarPadding
        ),
        content = {
            AboutScreenContent()
        }
    )
}

@Composable
fun AboutScreenContent() {
    val context = LocalContext.current

    Column(Modifier.verticalScroll(rememberScrollState())) {
        PreferenceHeader(title = "About")
        PreferenceSection(title = "Credits")
        Preference("Author", "LSafer")
        PreferenceDivider()
        PreferenceSection(title = "Versions")
        Preference("Version Name", BuildConfig.VERSION_NAME)
        Preference("Version Code", "${BuildConfig.VERSION_CODE}")
        PreferenceDivider()
        PreferenceSection(title = "Links")
        Preference("Website", "The official EdgeSeek website", {
            context.startActivity(Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://lsafer.net/edgeseek")
            ))
        })
        Preference("Google Play", "Stable releases only", {
            context.startActivity(Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=lsafer.edgeseek")
            ))
        })
        Preference("Github", "The application source code", {
            context.startActivity(Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://github.com/lsafer/edgeseek")
            ))
        })
    }
}
