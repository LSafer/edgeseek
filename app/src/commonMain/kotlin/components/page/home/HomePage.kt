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
package net.lsafer.edgeseek.app.components.page.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.lsafer.edgeseek.app.Local
import net.lsafer.edgeseek.app.UniRoute
import net.lsafer.edgeseek.app.components.lib.ListDivider
import net.lsafer.edgeseek.app.components.lib.ListHeader
import net.lsafer.edgeseek.app.components.lib.ListSectionTitle

@Composable
fun HomePage(
    local: Local,
    route: UniRoute.HomePage,
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
        MainPageContent(local, Modifier.padding(innerPadding))
    }
}

@Composable
fun MainPageContent(
    local: Local,
    modifier: Modifier = Modifier,
) {
    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .then(modifier),
    ) {
        ListHeader(title = "Edge Seek")

        ListSectionTitle(title = "Application")
        HomePage_ListItem_activation(local)
        HomePage_ListItem_ui_colors(local)

        ListDivider()
        ListSectionTitle(title = "Job")
        ListItem(
            modifier = Modifier
                .clickable { local.navController.push(UniRoute.EdgeListPage) },
            headlineContent = { Text("Edges") },
            supportingContent = { Text("Customize the edges") },
        )
        HomePage_ListItem_auto_boot(local)
        HomePage_ListItem_brightness_reset(local)

        ListDivider()
        ListSectionTitle(title = "Misc")
        ListItem(
            modifier = Modifier
                .clickable { local.navController.push(UniRoute.PermissionsPage) },
            headlineContent = { Text("Permissions") },
            supportingContent = { Text("Manage application's permissions") },
        )
        ListItem(
            modifier = Modifier
                .clickable { local.navController.push(UniRoute.PresetsPage) },
            headlineContent = { Text("Presets") },
            supportingContent = { Text("Choose a set of configurations") },
        )
        ListItem(
            modifier = Modifier
                .clickable { local.navController.push(UniRoute.AboutPage) },
            headlineContent = { Text("About") },
            supportingContent = { Text("Information about this application") },
        )

        Spacer(Modifier.height(50.dp))
    }
}
