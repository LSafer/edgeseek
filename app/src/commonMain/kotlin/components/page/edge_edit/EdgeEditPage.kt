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
package net.lsafer.edgeseek.app.components.page.edge_edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import net.lsafer.edgeseek.app.Local
import net.lsafer.edgeseek.app.UniRoute
import net.lsafer.edgeseek.app.components.common.editEdgeData
import net.lsafer.edgeseek.app.components.lib.*
import net.lsafer.edgeseek.app.data.settings.EdgeData
import net.lsafer.edgeseek.app.data.settings.EdgePos
import net.lsafer.edgeseek.app.data.settings.EdgeSeekFeature
import net.lsafer.sundry.storage.select

@Composable
fun EdgeEditPage(
    local: Local,
    route: UniRoute.EdgeEditPage,
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
        EdgeEditPageContent(local, route.pos, Modifier.padding(innerPadding))
    }
}

@Composable
fun EdgeEditPageContent(
    local: Local,
    pos: EdgePos,
    modifier: Modifier = Modifier,
) {
    val data by produceState(EdgeData(pos), pos, local) {
        local.dataStore
            .select<EdgeData>(pos.key)
            .filterNotNull()
            .distinctUntilChanged()
            .collect { value = it }
    }

    fun edit(block: (EdgeData) -> EdgeData) {
        local.editEdgeData(pos, block)
    }

    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .then(modifier)
    ) {
        ListHeader(
            title = "Edge Configuration",
            summary = pos.key,
        )

        ListSectionTitle(title = "Job")
        SwitchPreferenceListItem(
            value = data.activated,
            onChange = { newValue -> edit { it.copy(activated = newValue) } },
            headline = "Activation",
            supporting = "Toggle to activate or deactivate this edge",
        )
        SingleSelectPreferenceListItem(
            value = data.seekFeature,
            onChange = { newValue -> edit { data.copy(seekFeature = newValue) } },
            headline = "Seek Task",
            items = mapOf(
                EdgeSeekFeature.Nothing to "Nothing",
                EdgeSeekFeature.ControlBrightness to "Control Brightness",
                EdgeSeekFeature.ControlBrightnessWithDimmer to "Control Brightness with Dimmer",
                EdgeSeekFeature.ControlAlarm to "Control Alarm",
                EdgeSeekFeature.ControlMusic to "Control Music",
                EdgeSeekFeature.ControlRing to "Control Ring",
                EdgeSeekFeature.ControlSystem to "Control System",
            )
        )

        ListDivider()
        ListSectionTitle(title = "Input")
        SliderPreferenceListItem(
            value = data.sensitivity,
            onChange = { newValue -> edit { data.copy(sensitivity = newValue) } },
            headline = "Sensitivity",
            supporting = "How much you want the edge to be sensitive",
            valueRange = 5..100,
        )

        ListDivider()
        ListSectionTitle(title = "Dimensions")
        SliderPreferenceListItem(
            value = data.thickness,
            onChange = { newValue -> edit { data.copy(thickness = newValue) } },
            headline = "Thickness",
            supporting = "The thickness of the edge",
            valueRange = 0..100,
        )

        ListDivider()
        ListSectionTitle(title = "Appearance")
        ColorPreferenceListItem(
            value = data.color,
            onChange = { newValue -> edit { data.copy(color = newValue) } },
            headline = "Color",
            supporting = "The color of the edge.",
        )

        ListDivider()
        ListSectionTitle(title = "Misc")
        SwitchPreferenceListItem(
            value = data.seekToast,
            onChange = { newValue -> edit { data.copy(seekToast = newValue) } },
            headline = "Toast",
            supporting = "Display a message with the current volume when seeking",
        )
        SwitchPreferenceListItem(
            value = data.seekSteps,
            onChange = { newValue -> edit { data.copy(seekSteps = newValue) } },
            headline = "Step on pivot",
            supporting = "Limit seeking range around pivot points",
        )
        SliderPreferenceListItem(
            value = data.seekVibrate,
            onChange = { newValue -> edit { data.copy(seekVibrate = newValue) } },
            headline = "Vibrate",
            supporting = "The strength of vibration when the edge is touched",
            valueRange = 0..100,
        )

        Spacer(Modifier.height(50.dp))
    }
}
