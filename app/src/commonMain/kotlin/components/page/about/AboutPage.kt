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
package net.lsafer.edgeseek.app.components.page.about

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import net.lsafer.edgeseek.app.BuildConfig
import net.lsafer.edgeseek.app.Local
import net.lsafer.edgeseek.app.UniEvent
import net.lsafer.edgeseek.app.UniRoute
import net.lsafer.edgeseek.app.components.lib.ListDivider
import net.lsafer.edgeseek.app.components.lib.ListHeader
import net.lsafer.edgeseek.app.components.lib.ListSectionTitle

@Composable
fun AboutPage(
    local: Local,
    route: UniRoute.AboutPage,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .then(modifier),
        snackbarHost = {
            SnackbarHost(local.snackbar)
        },
    ) { innerPadding ->
        AboutPageContent(local, Modifier.padding(innerPadding))
    }
}

@Composable
fun AboutPageContent(
    local: Local,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()

    fun openIntroductionWizard() {
        @Suppress("ControlFlowWithEmptyBody")
        while (local.navController.back());
        local.navController.push(UniRoute.IntroductionWizard())
    }

    fun openUrl(url: String) = coroutineScope.launch {
        val event = UniEvent.OpenUrlRequest(url)
        local.eventbus.emit(event)
    }

    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .then(modifier)
    ) {
        ListHeader(title = "About")
        ListSectionTitle(title = "Credits")
        ListItem(
            headlineContent = { Text("Author") },
            supportingContent = { Text("LSafer") }
        )

        ListDivider()
        ListSectionTitle(title = "Versions")
        ListItem(
            headlineContent = { Text("Version Name") },
            supportingContent = { Text(BuildConfig.VERSION) },
        )
        ListItem(
            headlineContent = { Text("Version Code") },
            supportingContent = { Text(BuildConfig.VERSION_CODE.toString()) },
        )

        ListDivider()
        ListSectionTitle(title = "Links")
        ListItem(
            modifier = Modifier
                .clickable { openUrl("https://lsafer.net/edgeseek") },
            headlineContent = { Text("Website") },
            supportingContent = { Text("The official EdgeSeek website") },
        )
        ListItem(
            modifier = Modifier
                .clickable { openUrl("https://play.google.com/store/apps/details?id=lsafer.edgeseek") },
            headlineContent = { Text("Google Play") },
            supportingContent = { Text("Stable releases only") }
        )
        ListItem(
            modifier = Modifier
                .clickable { openUrl("https://github.com/lsafer/edgeseek") },
            headlineContent = { Text("GitHub") },
            supportingContent = { Text("The application source code") }
        )

        ListDivider()
        ListSectionTitle(title = "Misc")
        ListItem(
            modifier = Modifier
                .clickable { openIntroductionWizard() },
            headlineContent = { Text("Re-introduce") },
            supportingContent = { Text("Run the introduction wizard") },
        )
        ListItem(
            modifier = Modifier
                .clickable { local.navController.push(UniRoute.LogPage) },
            headlineContent = { Text("Logs") },
            supportingContent = { Text("Open log file") },
        )

        Spacer(Modifier.height(50.dp))
    }
}
