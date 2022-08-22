package net.lsafer.edgeseek.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import net.lsafer.edgeseek.model.EdgeLongClickTask
import net.lsafer.edgeseek.model.EdgeSeekTask
import net.lsafer.edgeseek.model.EdgeSide
import net.lsafer.edgeseek.model.rememberEdgeData
import net.lsafer.edgeseek.ui.widget.preferences.*
import net.lsafer.edgeseek.util.NavigationBarPadding
import net.lsafer.edgeseek.util.StatusBarPadding
import kotlin.math.roundToInt

const val EdgeScreenRoute = "edge:{id}"

@Composable
fun EdgeScreen(id: String) {
    Scaffold(
        Modifier.padding(
            top = StatusBarPadding,
            bottom = NavigationBarPadding
        ),
        content = {
            EdgeScreenContent(id)
        }
    )
}

@Composable
fun EdgeScreenContent(id: String) {
    var data by rememberEdgeData(id)

    Column(Modifier.verticalScroll(rememberScrollState())) {
        PreferenceHeader(
            title = "Edge Configuration",
            summary = id
        )
        PreferenceSection(title = "Job")
        SwitchPreference(
            checked = data.activated,
            onCheckedChange = { data = data.copy(activated = it) },
            title = "Activation",
            summary = "Toggle to activate or deactivate this edge",
        )
        ListPreference(
            value = data.seekTask,
            values = listOf(
                EdgeSeekTask.Nothing to "Nothing",
                EdgeSeekTask.ControlBrightness to "Control Brightness",
                EdgeSeekTask.ControlAlarm to "Control Alarm",
                EdgeSeekTask.ControlMusic to "Control Music",
                EdgeSeekTask.ControlRing to "Control Ring",
                EdgeSeekTask.ControlSystem to "Control System",
            ),
            onValueChange = { data = data.copy(seekTask = it) },
            title = "Seek Task"
        )
        ListPreference(
            value = data.longClickTask,
            values = listOf(
                EdgeLongClickTask.Nothing to "Nothing",
                EdgeLongClickTask.ExpandStatusBar to "Expand StatusBar",
            ),
            onValueChange = { data = data.copy(longClickTask = it) },
            title = "Long Click Task"
        )
        PreferenceDivider()
        PreferenceSection(title = "Positioning")
        ListPreference(
            value = data.side,
            values = listOf(
                EdgeSide.Bottom to "Bottom",
                EdgeSide.Left to "Left",
                EdgeSide.Top to "Top",
                EdgeSide.Right to "Right"
            ),
            onValueChange = { data = data.copy(side = it) },
            title = "Side"
        )
        SliderPreference(
            value = (data.offset * 100).roundToInt().toFloat(),
            onValueChange = { data = data.copy(offset = (it / 100)) },
            range = 0f..100f,
            title = "Offset",
            summary = "An offset % from the corner",
            format = { "${it.toInt()}%" }
        )
        PreferenceDivider()
        PreferenceSection(title = "Input")
        SliderPreference(
            value = data.sensitivity.toFloat(),
            onValueChange = { data = data.copy(sensitivity = it.roundToInt()) },
            range = 5f..100f,
            title = "Sensitivity",
            summary = "How much you want the edge to be sensitive",
            format = { "${it.toInt()}" }
        )
        PreferenceDivider()
        PreferenceSection(title = "Dimensions")
        SliderPreference(
            value = (data.ratio * 100).roundToInt().toFloat(),
            onValueChange = { data = data.copy(ratio = (it / 100)) },
            range = 5f..100f,
            title = "Ratio",
            summary = "The size % of the side length",
            format = { "${it.toInt()}%" }
        )
        SliderPreference(
            value = data.width.toFloat(),
            onValueChange = { data = data.copy(width = it.roundToInt()) },
            range = 5f..100f,
            title = "Width",
            summary = "The width of the edge",
            format = { "${it.toInt()}dp" }
        )
        PreferenceDivider()
        PreferenceSection(title = "Appearance")
        ColorPreference(
            value = Color(data.color),
            onValueChange = { data = data.copy(color = it.toArgb()) },
            title = "Color",
            summary = "The color of the edge"
        )
        PreferenceDivider()
        PreferenceSection(title = "Misc")
        SwitchPreference(
            checked = data.persistent,
            onCheckedChange = { data = data.copy(persistent = it) },
            title = "Persistent",
            summary = "Stay at the exact physical side even when the screen is rotated"
        )
        SwitchPreference(
            checked = data.seekToast,
            onCheckedChange = { data = data.copy(seekToast = it) },
            title = "Toast",
            summary = "Display a message with the current volume when seeking"
        )
        SliderPreference(
            value = data.seekVibrate.toFloat(),
            onValueChange = { data = data.copy(seekVibrate = it.roundToInt()) },
            range = 0f..100f,
            title = "Vibrate",
            summary = "The strength of vibration when the edge is touched",
            format = { "${it.toInt()}" }
        )
        Spacer(Modifier.height(50.dp))
    }
}
