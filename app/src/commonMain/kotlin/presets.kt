package net.lsafer.edgeseek.app

import net.lsafer.edgeseek.app.data.settings.EdgeData
import net.lsafer.edgeseek.app.data.settings.EdgePos
import net.lsafer.edgeseek.app.data.settings.EdgeSeekFeature

val PRESET_STANDARD = listOf(
    // Left
    EdgeData(
        pos = EdgePos.LeftTop,
        activated = true,
        seekFeature = EdgeSeekFeature.ControlMusic
    ),
    EdgeData(
        pos = EdgePos.LeftCenter,
        activated = true,
        seekFeature = EdgeSeekFeature.ControlMusic
    ),
    EdgeData(
        pos = EdgePos.LeftBottom,
        activated = true,
        seekFeature = EdgeSeekFeature.ControlMusic
    ),
    // Right
    EdgeData(
        pos = EdgePos.RightTop,
        activated = true,
        seekFeature = EdgeSeekFeature.ControlBrightness
    ),
    EdgeData(
        pos = EdgePos.RightCenter,
        activated = true,
        seekFeature = EdgeSeekFeature.ControlBrightness
    ),
    EdgeData(
        pos = EdgePos.RightBottom,
        activated = true,
        seekFeature = EdgeSeekFeature.ControlBrightness
    ),
)

val PRESET_STANDARD_CENTERED = listOf(
    // Left
    EdgeData(
        pos = EdgePos.LeftCenter,
        activated = true,
        seekFeature = EdgeSeekFeature.ControlMusic
    ),
    // Right
    EdgeData(
        pos = EdgePos.RightCenter,
        activated = true,
        seekFeature = EdgeSeekFeature.ControlBrightness
    ),
)

val PRESET_BRIGHTNESS_ONLY = listOf(
    // Right
    EdgeData(
        pos = EdgePos.RightTop,
        activated = true,
        seekFeature = EdgeSeekFeature.ControlBrightness
    ),
    EdgeData(
        pos = EdgePos.RightCenter,
        activated = true,
        seekFeature = EdgeSeekFeature.ControlBrightness
    ),
    EdgeData(
        pos = EdgePos.RightBottom,
        activated = true,
        seekFeature = EdgeSeekFeature.ControlBrightness
    ),
)

val PRESET_BRIGHTNESS_ONLY_CENTERED = listOf(
    EdgeData(
        pos = EdgePos.RightCenter,
        activated = true,
        seekFeature = EdgeSeekFeature.ControlBrightness
    ),
)

val PRESET_DOUBLE_BRIGHTNESS = listOf(
    // Left
    EdgeData(
        pos = EdgePos.LeftTop,
        activated = true,
        seekFeature = EdgeSeekFeature.ControlBrightness
    ),
    EdgeData(
        pos = EdgePos.LeftCenter,
        activated = true,
        seekFeature = EdgeSeekFeature.ControlBrightness
    ),
    EdgeData(
        pos = EdgePos.LeftBottom,
        activated = true,
        seekFeature = EdgeSeekFeature.ControlBrightness
    ),
    // Right
    EdgeData(
        pos = EdgePos.RightTop,
        activated = true,
        seekFeature = EdgeSeekFeature.ControlBrightness
    ),
    EdgeData(
        pos = EdgePos.RightCenter,
        activated = true,
        seekFeature = EdgeSeekFeature.ControlBrightness
    ),
    EdgeData(
        pos = EdgePos.RightBottom,
        activated = true,
        seekFeature = EdgeSeekFeature.ControlBrightness
    ),
)

val PRESET_DOUBLE_BRIGHTNESS_CENTERED = listOf(
    // Left
    EdgeData(
        pos = EdgePos.LeftCenter,
        activated = true,
        seekFeature = EdgeSeekFeature.ControlBrightness
    ),
    // Right
    EdgeData(
        pos = EdgePos.RightCenter,
        activated = true,
        seekFeature = EdgeSeekFeature.ControlBrightness
    ),
)
