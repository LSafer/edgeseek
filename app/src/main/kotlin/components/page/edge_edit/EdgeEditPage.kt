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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import net.lsafer.edgeseek.app.Local
import net.lsafer.edgeseek.app.R
import net.lsafer.edgeseek.app.components.lib.*
import net.lsafer.edgeseek.app.data.settings.EdgePos
import net.lsafer.edgeseek.app.data.settings.EdgePosData
import net.lsafer.edgeseek.app.data.settings.EdgeSide
import net.lsafer.edgeseek.app.data.settings.OrientationFilter

@Composable
context(local: Local)
fun EdgeEditPage(
    pos: EdgePos,
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
        EdgeEditPageContent(pos, Modifier.padding(innerPadding))
    }
}

@Composable
context(local: Local)
fun EdgeEditPageContent(
    pos: EdgePos,
    modifier: Modifier = Modifier,
) {
    val data by remember { derivedStateOf { local.repo.getEdgePosData(pos) } }

    fun edit(block: (EdgePosData) -> EdgePosData) {
        local.repo.edgePosList += block(data)
    }

    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .then(modifier)
    ) {
        ListHeader(
            title = stringResource(R.string.page_edge_edit_heading),
            summary = pos.key,
        )

        ListSectionTitle(title = stringResource(R.string.job))
        SwitchPreferenceListItem(
            value = data.activated,
            onChange = { newValue -> edit { it.copy(activated = newValue) } },
            headline = stringResource(R.string.edge_activation_headline),
            supporting = stringResource(R.string.edge_activation_supporting),
        )
        ControlFeaturePreferenceListItem(
            value = data.onSeek,
            onChange = { newValue -> edit { data.copy(onSeek = newValue) } },
            headline = stringResource(R.string.edge_seek_task_headline),
        )
        ActionFeaturePreferenceListItem(
            value = data.onLongClick,
            onChange = { newValue -> edit { data.copy(onLongClick = newValue) } },
            headline = stringResource(R.string.edge_long_click_task_headline),
        )
        ActionFeaturePreferenceListItem(
            value = data.onDoubleClick,
            onChange = { newValue -> edit { data.copy(onDoubleClick = newValue) } },
            headline = stringResource(R.string.edge_double_click_task_headline),
        )
        if (pos.side != EdgeSide.Top) ActionFeaturePreferenceListItem(
            value = data.onSwipeUp,
            onChange = { newValue -> edit { data.copy(onSwipeUp = newValue) } },
            headline = stringResource(R.string.edge_swipe_up_task_headline),
        )
        if (pos.side != EdgeSide.Bottom) ActionFeaturePreferenceListItem(
            value = data.onSwipeDown,
            onChange = { newValue -> edit { data.copy(onSwipeDown = newValue) } },
            headline = stringResource(R.string.edge_swipe_down_task_headline),
        )
        if (pos.side != EdgeSide.Left) ActionFeaturePreferenceListItem(
            value = data.onSwipeLeft,
            onChange = { newValue -> edit { data.copy(onSwipeLeft = newValue) } },
            headline = stringResource(R.string.edge_swipe_left_task_headline),
        )
        if (pos.side != EdgeSide.Right) ActionFeaturePreferenceListItem(
            value = data.onSwipeRight,
            onChange = { newValue -> edit { data.copy(onSwipeRight = newValue) } },
            headline = stringResource(R.string.edge_swipe_right_task_headline),
        )

        ListDivider()
        ListSectionTitle(title = stringResource(R.string.input))
        SliderPreferenceListItem(
            value = data.sensitivity,
            onChange = { newValue -> edit { data.copy(sensitivity = newValue) } },
            headline = stringResource(R.string.edge_sensitivity_headline),
            supporting = stringResource(R.string.edge_sensitivity_supporting),
            valueRange = 5..100,
        )

        ListDivider()
        ListSectionTitle(title = stringResource(R.string.dimensions))
        SliderPreferenceListItem(
            value = data.thickness,
            onChange = { newValue -> edit { data.copy(thickness = newValue) } },
            headline = stringResource(R.string.edge_thickness_headline),
            supporting = stringResource(R.string.edge_thickness_supporting),
            valueRange = 0..100,
        )

        ListDivider()
        ListSectionTitle(title = stringResource(R.string.appearance))
        ColorPreferenceListItem(
            value = data.color,
            onChange = { newValue -> edit { data.copy(color = newValue) } },
            headline = stringResource(R.string.edge_color_headline),
            supporting = stringResource(R.string.edge_color_supporting),
        )

        ListDivider()
        ListSectionTitle(title = stringResource(R.string.misc))
        SwitchPreferenceListItem(
            value = data.seekSteps,
            onChange = { newValue -> edit { data.copy(seekSteps = newValue) } },
            headline = stringResource(R.string.edge_seek_steps_headline),
            supporting = stringResource(R.string.edge_seek_steps_supporting),
        )
        SwitchPreferenceListItem(
            value = data.seekReverse,
            onChange = { newValue -> edit { data.copy(seekReverse = newValue) } },
            headline = stringResource(R.string.edge_seek_reverse_headline),
            supporting = stringResource(R.string.edge_seek_reverse_supporting),
        )
        SwitchPreferenceListItem(
            value = data.seekRelateToScreen,
            onChange = { newValue -> edit { data.copy(seekRelateToScreen = newValue) } },
            headline = stringResource(R.string.edge_seek_relate_to_screen_headline),
            supporting = stringResource(R.string.edge_seek_relate_to_screen_supporting),
        )
        SwitchPreferenceListItem(
            value = data.seekAcceleration,
            onChange = { newValue -> edit { data.copy(seekAcceleration = newValue) } },
            headline = stringResource(R.string.edge_seek_acceleration_headline),
            supporting = stringResource(R.string.edge_seek_acceleration_supporting),
        )
        SwitchPreferenceListItem(
            value = data.feedbackToast,
            onChange = { newValue -> edit { data.copy(feedbackToast = newValue) } },
            headline = stringResource(R.string.edge_feedback_toast_headline),
            supporting = stringResource(R.string.edge_feedback_toast_supporting),
        )
        SwitchPreferenceListItem(
            value = data.feedbackSystemPanel,
            onChange = { newValue -> edit { data.copy(feedbackSystemPanel = newValue) } },
            headline = stringResource(R.string.edge_feedback_system_panel_headline),
            supporting = stringResource(R.string.edge_feedback_system_panel_supporting),
        )
        SingleSelectPreferenceListItem(
            value = data.orientationFilter,
            onChange = { newValue -> edit { it.copy(orientationFilter = newValue) } },
            headline = stringResource(R.string.edge_orientation_filter_headline),
            items = mapOf(
                OrientationFilter.All to stringResource(R.string.edge_orientation_filter_value_all),
                OrientationFilter.PortraitOnly to stringResource(R.string.edge_orientation_filter_value_portrait_only),
                OrientationFilter.LandscapeOnly to stringResource(R.string.edge_orientation_filter_value_landscape_only),
            )
        )
        SliderPreferenceListItem(
            value = data.feedbackVibration,
            onChange = { newValue -> edit { data.copy(feedbackVibration = newValue) } },
            headline = stringResource(R.string.edge_feedback_vibration_headline),
            supporting = stringResource(R.string.edge_feedback_vibration_supporting),
            valueRange = 0..100,
        )

        Spacer(Modifier.height(50.dp))
    }
}
