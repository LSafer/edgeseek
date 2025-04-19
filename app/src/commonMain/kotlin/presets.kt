package net.lsafer.edgeseek.app

import net.lsafer.edgeseek.app.data.settings.*

val PRESET_SIDE_STANDARD = listOf(
    EdgeSideData(
        side = EdgeSide.Top,
        nSegments = 1,
    ),
    EdgeSideData(
        side = EdgeSide.Bottom,
        nSegments = 1,
    ),
    EdgeSideData(
        side = EdgeSide.Left,
        nSegments = 1,
    ),
    EdgeSideData(
        side = EdgeSide.Right,
        nSegments = 1,
    ),
)

val PRESET_SIDE_CENTERED = listOf(
    EdgeSideData(
        side = EdgeSide.Top,
        nSegments = 1,
    ),
    EdgeSideData(
        side = EdgeSide.Bottom,
        nSegments = 1,
    ),
    EdgeSideData(
        side = EdgeSide.Left,
        nSegments = 3,
    ),
    EdgeSideData(
        side = EdgeSide.Right,
        nSegments = 3,
    ),
)

val PRESET_POS_STANDARD = listOf(
    // Left
    EdgePosData(
        pos = EdgePos.LeftCenter,
        activated = true,
        onSeek = ControlFeature.Music
    ),
    // Right
    EdgePosData(
        pos = EdgePos.RightCenter,
        activated = true,
        onSeek = ControlFeature.Brightness
    ),
)

val PRESET_POS_BRIGHTNESS_ONLY = listOf(
    // Right
    EdgePosData(
        pos = EdgePos.RightCenter,
        activated = true,
        onSeek = ControlFeature.Brightness
    ),
)

val PRESET_POS_DOUBLE_BRIGHTNESS = listOf(
    // Left
    EdgePosData(
        pos = EdgePos.LeftCenter,
        activated = true,
        onSeek = ControlFeature.Brightness
    ),
    // Right
    EdgePosData(
        pos = EdgePos.RightCenter,
        activated = true,
        onSeek = ControlFeature.Brightness
    ),
)
