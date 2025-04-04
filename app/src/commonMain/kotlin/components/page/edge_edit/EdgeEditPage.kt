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
import net.lsafer.edgeseek.app.l10n.strings
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
            title = strings.stmt.page_edge_edit_heading,
            summary = pos.key,
        )

        ListSectionTitle(title = strings.label.job)
        SwitchPreferenceListItem(
            value = data.activated,
            onChange = { newValue -> edit { it.copy(activated = newValue) } },
            headline = strings.stmt.edge_activation_headline,
            supporting = strings.stmt.edge_activation_supporting,
        )
        SingleSelectPreferenceListItem(
            value = data.seekFeature,
            onChange = { newValue -> edit { data.copy(seekFeature = newValue) } },
            headline = strings.stmt.edge_seek_task_headline,
            items = mapOf(
                EdgeSeekFeature.Nothing to strings.stmt.edge_seek_task_value_nothing,
                EdgeSeekFeature.ControlBrightness to strings.stmt.edge_seek_task_value_control_brightness,
                EdgeSeekFeature.ControlBrightnessWithDimmer to strings.stmt.edge_seek_task_value_control_brightness_dimmer,
                EdgeSeekFeature.ControlAlarm to strings.stmt.edge_seek_task_value_control_alarm,
                EdgeSeekFeature.ControlMusic to strings.stmt.edge_seek_task_value_control_music,
                EdgeSeekFeature.ControlRing to strings.stmt.edge_seek_task_value_control_ring,
                EdgeSeekFeature.ControlSystem to strings.stmt.edge_seek_task_value_control_system,
            )
        )

        ListDivider()
        ListSectionTitle(title = strings.label.input)
        SliderPreferenceListItem(
            value = data.sensitivity,
            onChange = { newValue -> edit { data.copy(sensitivity = newValue) } },
            headline = strings.stmt.edge_sensitivity_headline,
            supporting = strings.stmt.edge_sensitivity_supporting,
            valueRange = 5..100,
        )

        ListDivider()
        ListSectionTitle(title = strings.label.dimensions)
        SliderPreferenceListItem(
            value = data.thickness,
            onChange = { newValue -> edit { data.copy(thickness = newValue) } },
            headline = strings.stmt.edge_thickness_headline,
            supporting = strings.stmt.edge_thickness_supporting,
            valueRange = 0..100,
        )

        ListDivider()
        ListSectionTitle(title = strings.label.appearance)
        ColorPreferenceListItem(
            value = data.color,
            onChange = { newValue -> edit { data.copy(color = newValue) } },
            headline = strings.stmt.edge_color_headline,
            supporting = strings.stmt.edge_color_supporting,
        )

        ListDivider()
        ListSectionTitle(title = strings.label.misc)
        SwitchPreferenceListItem(
            value = data.seekToast,
            onChange = { newValue -> edit { data.copy(seekToast = newValue) } },
            headline = strings.stmt.edge_seek_toast_headline,
            supporting = strings.stmt.edge_seek_toast_supporting,
        )
        SwitchPreferenceListItem(
            value = data.seekSteps,
            onChange = { newValue -> edit { data.copy(seekSteps = newValue) } },
            headline = strings.stmt.edge_seek_steps_headline,
            supporting = strings.stmt.edge_seek_steps_supporting,
        )
        SliderPreferenceListItem(
            value = data.seekVibrate,
            onChange = { newValue -> edit { data.copy(seekVibrate = newValue) } },
            headline = strings.stmt.edge_seek_vibrate_headline,
            supporting = strings.stmt.edge_seek_vibrate_supporting,
            valueRange = 0..100,
        )

        Spacer(Modifier.height(50.dp))
    }
}
