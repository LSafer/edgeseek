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
import net.lsafer.edgeseek.app.data.settings.EdgePos
import net.lsafer.edgeseek.app.data.settings.EdgePosData
import net.lsafer.edgeseek.app.data.settings.EdgeSide
import net.lsafer.edgeseek.app.data.settings.OrientationFilter
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
    val data by produceState(EdgePosData(pos), pos, local) {
        local.dataStore
            .select<EdgePosData>(pos.key)
            .filterNotNull()
            .distinctUntilChanged()
            .collect { value = it }
    }

    fun edit(block: (EdgePosData) -> EdgePosData) {
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
        ControlFeaturePreferenceListItem(
            value = data.onSeek,
            onChange = { newValue -> edit { data.copy(onSeek = newValue) } },
            headline = strings.stmt.edge_seek_task_headline,
        )
        ActionFeaturePreferenceListItem(
            value = data.onLongClick,
            onChange = { newValue -> edit { data.copy(onLongClick = newValue) } },
            headline = strings.stmt.edge_long_click_task_headline,
        )
        ActionFeaturePreferenceListItem(
            value = data.onDoubleClick,
            onChange = { newValue -> edit { data.copy(onDoubleClick = newValue) } },
            headline = strings.stmt.edge_double_click_task_headline,
        )
        if (pos.side != EdgeSide.Top) ActionFeaturePreferenceListItem(
            value = data.onSwipeUp,
            onChange = { newValue -> edit { data.copy(onSwipeUp = newValue) } },
            headline = strings.stmt.edge_swipe_up_task_headline,
        )
        if (pos.side != EdgeSide.Bottom) ActionFeaturePreferenceListItem(
            value = data.onSwipeDown,
            onChange = { newValue -> edit { data.copy(onSwipeDown = newValue) } },
            headline = strings.stmt.edge_swipe_down_task_headline,
        )
        if (pos.side != EdgeSide.Left) ActionFeaturePreferenceListItem(
            value = data.onSwipeLeft,
            onChange = { newValue -> edit { data.copy(onSwipeLeft = newValue) } },
            headline = strings.stmt.edge_swipe_left_task_headline,
        )
        if (pos.side != EdgeSide.Right) ActionFeaturePreferenceListItem(
            value = data.onSwipeRight,
            onChange = { newValue -> edit { data.copy(onSwipeRight = newValue) } },
            headline = strings.stmt.edge_swipe_right_task_headline,
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
            value = data.seekSteps,
            onChange = { newValue -> edit { data.copy(seekSteps = newValue) } },
            headline = strings.stmt.edge_seek_steps_headline,
            supporting = strings.stmt.edge_seek_steps_supporting,
        )
        SwitchPreferenceListItem(
            value = data.seekAcceleration,
            onChange = { newValue -> edit { data.copy(seekAcceleration = newValue) } },
            headline = strings.stmt.edge_seek_acceleration_headline,
            supporting = strings.stmt.edge_seek_acceleration_supporting,
        )
        SwitchPreferenceListItem(
            value = data.feedbackToast,
            onChange = { newValue -> edit { data.copy(feedbackToast = newValue) } },
            headline = strings.stmt.edge_feedback_toast_headline,
            supporting = strings.stmt.edge_feedback_toast_supporting,
        )
        SwitchPreferenceListItem(
            value = data.feedbackSystemPanel,
            onChange = { newValue -> edit { data.copy(feedbackSystemPanel = newValue) } },
            headline = strings.stmt.edge_feedback_system_panel_headline,
            supporting = strings.stmt.edge_feedback_system_panel_supporting,
        )
        SingleSelectPreferenceListItem(
            value = data.orientationFilter,
            onChange = { newValue -> edit { it.copy(orientationFilter = newValue) } },
            headline = strings.stmt.edge_orientation_filter_headline,
            items = mapOf(
                OrientationFilter.All to strings.stmt.edge_orientation_filter_value_all,
                OrientationFilter.PortraitOnly to strings.stmt.edge_orientation_filter_value_portrait_only,
                OrientationFilter.LandscapeOnly to strings.stmt.edge_orientation_filter_value_landscape_only,
            )
        )
        SliderPreferenceListItem(
            value = data.feedbackVibration,
            onChange = { newValue -> edit { data.copy(feedbackVibration = newValue) } },
            headline = strings.stmt.edge_feedback_vibration_headline,
            supporting = strings.stmt.edge_feedback_vibration_supporting,
            valueRange = 0..100,
        )

        Spacer(Modifier.height(50.dp))
    }
}
