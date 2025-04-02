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
package net.lsafer.edgeseek.app.components.page.presets

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import net.lsafer.edgeseek.app.*
import net.lsafer.edgeseek.app.components.common.clearAndSetEdgeDataList
import net.lsafer.edgeseek.app.components.common.editEachEdgeData
import net.lsafer.edgeseek.app.components.lib.ListHeader
import net.lsafer.edgeseek.app.components.lib.ListSectionTitle

@Composable
fun PresetsPage(
    local: Local,
    route: UniRoute.PresetsPage,
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
        PresetsPageContent(local, Modifier.padding(innerPadding))
    }
}

@Composable
fun PresetsPageContent(
    local: Local,
    modifier: Modifier = Modifier,
) {
    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .then(modifier),
    ) {
        ListHeader(title = "Presets", summary = "Preset configurations")
        ListSectionTitle(title = "Presets")
        ListItem(
            modifier = Modifier
                .clickable { local.clearAndSetEdgeDataList(PRESET_STANDARD) },
            headlineContent = { Text("Standard") },
            supportingContent = { Text("Control music audio left and brightness right") }
        )
        ListItem(
            modifier = Modifier
                .clickable { local.clearAndSetEdgeDataList(PRESET_STANDARD_CENTERED) },
            headlineContent = { Text("Standard (Centered)") },
            supportingContent = { Text("Same as Standard but with centered bars") }
        )
        ListItem(
            modifier = Modifier
                .clickable { local.clearAndSetEdgeDataList(PRESET_BRIGHTNESS_ONLY) },
            headlineContent = { Text("Brightness Only") },
            supportingContent = { Text("Only control brightness from the right") }
        )
        ListItem(
            modifier = Modifier
                .clickable { local.clearAndSetEdgeDataList(PRESET_BRIGHTNESS_ONLY_CENTERED) },
            headlineContent = { Text("Brightness Only (Centered)") },
            supportingContent = { Text("Same as Brightness Only but with centered bars") }
        )
        ListItem(
            modifier = Modifier
                .clickable { local.clearAndSetEdgeDataList(PRESET_DOUBLE_BRIGHTNESS) },
            headlineContent = { Text("Double Brightness") },
            supportingContent = { Text("Control brightness from both sides") }
        )
        ListItem(
            modifier = Modifier
                .clickable { local.clearAndSetEdgeDataList(PRESET_DOUBLE_BRIGHTNESS_CENTERED) },
            headlineContent = { Text("Double Brightness (Centered)") },
            supportingContent = { Text("Same as Double Brightness but with centered bars (LSafer's choice)") }
        )

        ListSectionTitle(title = "Utility")
        ListItem(
            modifier = Modifier
                .clickable {
                    local.editEachEdgeData {
                        it.copy(color = Color(it.color).copy(alpha = 1f).toArgb())
                    }
                },
            headlineContent = { Text("Show All") },
            supportingContent = { Text("Increase the opacity of all edges") }
        )
        ListItem(
            modifier = Modifier
                .clickable {
                    local.editEachEdgeData {
                        it.copy(color = Color(it.color).copy(alpha = 0f).toArgb())
                    }
                },
            headlineContent = { Text("Hide All") },
            supportingContent = { Text("Decrease the opacity of all edges") }
        )
        ListItem(
            modifier = Modifier
                .clickable {
                    local.editEachEdgeData {
                        it.copy(color = Color(it.color).copy(alpha = .01f).toArgb())
                    }
                },
            headlineContent = { Text("Stealth All") },
            supportingContent = { Text("Decrease the opacity of all edges (Compatibility Mode)") }
        )

        Spacer(Modifier.height(50.dp))
    }
}
