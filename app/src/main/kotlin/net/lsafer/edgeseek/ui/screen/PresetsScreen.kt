package net.lsafer.edgeseek.ui.screen

import androidx.compose.foundation.layout.Column
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
import net.lsafer.edgeseek.model.EdgeData
import net.lsafer.edgeseek.model.EdgeSeekTask
import net.lsafer.edgeseek.model.EdgeSide
import net.lsafer.edgeseek.model.rememberApplicationData
import net.lsafer.edgeseek.ui.widget.preferences.Preference
import net.lsafer.edgeseek.ui.widget.preferences.PreferenceHeader
import net.lsafer.edgeseek.ui.widget.preferences.PreferenceSection
import net.lsafer.edgeseek.util.NavigationBarPadding
import net.lsafer.edgeseek.util.StatusBarPadding

const val PresetsScreenRoute = "presets"

@Composable
fun PresetsScreen() {
    Scaffold(
        Modifier.padding(
            top = StatusBarPadding,
            bottom = NavigationBarPadding
        ),
        content = {
            PresetsScreenContent()
        }
    )
}

@Composable
fun PresetsScreenContent() {
    var data by rememberApplicationData()

    Column(Modifier.verticalScroll(rememberScrollState())) {
        PreferenceHeader(title = "Presets", summary = "Preset configurations")
        PreferenceSection(title = "Presets")
        Preference(title = "LSafer's choice", summary = "Whatever LSafer likes", {
            data = data.copy(edges = listOf(
                EdgeData(
                    id = "left",
                    activated = true,
                    side = EdgeSide.Left,
                    ratio = .6f,
                    offset = .2f,
                    seekTask = EdgeSeekTask.ControlBrightness
                ),
                EdgeData(
                    id = "right",
                    activated = true,
                    side = EdgeSide.Right,
                    ratio = .6f,
                    offset = .2f,
                    seekTask = EdgeSeekTask.ControlBrightness
                ),
            ))
        })
        PreferenceSection(title = "Utility")
        Preference(title = "Show All", summary = "Increase the opacity of all edges", {
            data = data.copy(edges = data.edges.map {
                it.copy(color = Color(it.color).copy(alpha = 1f).toArgb())
            })
        })
        Preference(title = "Hide All", summary = "Decrease the opacity of all edges", {
            data = data.copy(edges = data.edges.map {
                it.copy(color = Color(it.color).copy(alpha = 0f).toArgb())
            })
        })

    }
}
