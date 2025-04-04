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
import net.lsafer.edgeseek.app.l10n.strings

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
        ListHeader(title = strings.stmt.page_about_heading)
        ListSectionTitle(title = strings.label.credits)
        ListItem(
            headlineContent = { Text(strings.label.author) },
            supportingContent = { Text("LSafer") }
        )

        ListDivider()
        ListSectionTitle(title = strings.label.version)
        ListItem(
            headlineContent = { Text(strings.label.version_name) },
            supportingContent = { Text(BuildConfig.VERSION) },
        )
        ListItem(
            headlineContent = { Text(strings.label.version_code) },
            supportingContent = { Text(BuildConfig.VERSION_CODE.toString()) },
        )

        ListDivider()
        ListSectionTitle(title = strings.label.links)
        ListItem(
            modifier = Modifier
                .clickable { openUrl("https://lsafer.net/edgeseek") },
            headlineContent = { Text(strings.stmt.about_website_headline) },
            supportingContent = { Text(strings.stmt.about_website_supporting) },
        )
        ListItem(
            modifier = Modifier
                .clickable { openUrl("https://github.com/lsafer/edgeseek") },
            headlineContent = { Text(strings.stmt.about_source_code_headline) },
            supportingContent = { Text(strings.stmt.about_source_code_supporting) }
        )

        ListDivider()
        ListSectionTitle(title = strings.label.misc)
        ListItem(
            modifier = Modifier
                .clickable { openIntroductionWizard() },
            headlineContent = { Text(strings.stmt.about_reintroduce_headline) },
            supportingContent = { Text(strings.stmt.about_reintroduce_supporting) },
        )
        ListItem(
            modifier = Modifier
                .clickable { local.navController.push(UniRoute.LogPage) },
            headlineContent = { Text(strings.stmt.page_log_headline) },
            supportingContent = { Text(strings.stmt.page_log_supporting) },
        )

        Spacer(Modifier.height(50.dp))
    }
}
