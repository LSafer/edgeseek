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
package net.lsafer.edgeseek.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import net.lsafer.edgeseek.BuildConfig
import net.lsafer.edgeseek.LocalNavController
import net.lsafer.edgeseek.ui.widget.preferences.Preference
import net.lsafer.edgeseek.ui.widget.preferences.PreferenceDivider
import net.lsafer.edgeseek.ui.widget.preferences.PreferenceHeader
import net.lsafer.edgeseek.ui.widget.preferences.PreferenceSection
import net.lsafer.edgeseek.ui.wizard.IntroductionWizardRoute
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
    val navController = LocalNavController.current
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

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
        PreferenceDivider()
        PreferenceSection(title = "Misc")
        Preference(
            title = "Re-introduce",
            summary = "Run the introduction wizard",
            onClick = {
                coroutineScope.launch {
                    while (navController.popBackStack());
                    navController.navigate(IntroductionWizardRoute)
                }
            }
        )
        Spacer(Modifier.height(50.dp))
    }
}
