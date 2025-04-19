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
package net.lsafer.edgeseek.app.data.settings

import android.graphics.Color
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class EdgeSide(
    val key: String,
) {
    Bottom("side_bottom"),
    Left("side_left"),
    Top("side_top"),
    Right("side_right");

    fun rotate(rotation: Int) =
        entries[(8 + ordinal - (rotation % 4) * 1) % 4]
}

@Serializable
enum class EdgeCorner {
    BottomRight, Bottom, BottomLeft, Left,
    TopLeft, Top, TopRight, Right;

    fun rotate(rotation: Int) =
        entries[(16 + ordinal - (rotation % 8) * 2) % 8]
}

@Serializable
enum class EdgePos(
    val key: String,
    val side: EdgeSide,
    val corner: EdgeCorner,
) {
    BottomRight("pos_bottom_right", EdgeSide.Bottom, EdgeCorner.BottomRight),
    BottomCenter("pos_bottom_center", EdgeSide.Bottom, EdgeCorner.Bottom),
    BottomLeft("pos_bottom_left", EdgeSide.Bottom, EdgeCorner.BottomLeft),
    LeftBottom("pos_left_bottom", EdgeSide.Left, EdgeCorner.BottomLeft),
    LeftCenter("pos_left_center", EdgeSide.Left, EdgeCorner.Left),
    LeftTop("pos_left_top", EdgeSide.Left, EdgeCorner.TopLeft),
    TopLeft("pos_top_left", EdgeSide.Top, EdgeCorner.TopLeft),
    TopCenter("pos_top_center", EdgeSide.Top, EdgeCorner.Top),
    TopRight("pos_top_right", EdgeSide.Top, EdgeCorner.TopRight),
    RightTop("pos_right_top", EdgeSide.Right, EdgeCorner.TopRight),
    RightCenter("pos_right_center", EdgeSide.Right, EdgeCorner.Right),
    RightBottom("pos_right_bottom", EdgeSide.Right, EdgeCorner.BottomRight);

    fun isIncludedWhenSegmented(nSegments: Int): Boolean {
        return when (this) {
            BottomCenter,
            TopCenter,
            RightCenter,
            LeftCenter,
            -> nSegments == 1 || nSegments == 3

            BottomLeft, BottomRight,
            TopLeft, TopRight,
            LeftTop, LeftBottom,
            RightTop, RightBottom,
            -> nSegments == 2 || nSegments == 3
        }
    }
}

@Serializable
sealed interface ControlFeature {
    @Serializable
    @SerialName("nothing")
    data object Nothing : ControlFeature
    @Serializable
    @SerialName("brightness")
    data object Brightness : ControlFeature
    @Serializable
    @SerialName("brightness_dimmer")
    data object BrightnessWithDimmer : ControlFeature
    @Serializable
    @SerialName("alarm")
    data object Alarm : ControlFeature
    @Serializable
    @SerialName("music")
    data object Music : ControlFeature
    @Serializable
    @SerialName("ring")
    data object Ring : ControlFeature
    @Serializable
    @SerialName("system")
    data object System : ControlFeature
}

@Serializable
sealed interface ActionFeature {
    @Serializable
    @SerialName("nothing")
    data object Nothing : ActionFeature
    @Serializable
    @SerialName("expand_status_bar")
    data object ExpandStatusBar : ActionFeature
}

@Serializable
enum class OrientationFilter {
    @SerialName("all")
    All,
    @SerialName("portrait_only")
    PortraitOnly,
    @SerialName("landscape_only")
    LandscapeOnly, ;

    fun test(displayRotation: Int): Boolean {
        return when (this) {
            All -> true
            PortraitOnly -> displayRotation % 2 == 0
            LandscapeOnly -> displayRotation % 2 == 1
        }
    }
}

@Serializable
data class EdgeSideData(
    val side: EdgeSide,
    val nSegments: Int = when (side) {
        EdgeSide.Top, EdgeSide.Bottom -> 2
        EdgeSide.Left, EdgeSide.Right -> 3
    },
)

@Serializable
data class EdgePosData(
    /**
     * The position of this edge.
     */
    val pos: EdgePos,
    /**
     * True, if this edge is activated.
     */
    val activated: Boolean = false,
    /**
     * The width of the edge.
     */
    val thickness: Int = 35,
    /**
     * The color of the edge. argb
     */
    val color: Int = Color.argb(1, 255, 0, 0),
    /**
     * The sensitivity of this edge.
     */
    val sensitivity: Int = 45,
    val onSeek: ControlFeature = ControlFeature.Nothing,
    val onLongClick: ActionFeature = ActionFeature.Nothing,
    val onDoubleClick: ActionFeature = ActionFeature.Nothing,
    val onSwipeUp: ActionFeature = ActionFeature.Nothing,
    val onSwipeDown: ActionFeature = ActionFeature.Nothing,
    val onSwipeLeft: ActionFeature = ActionFeature.Nothing,
    val onSwipeRight: ActionFeature = ActionFeature.Nothing,
    /**
     * The strength of vibrations.
     */
    val feedbackVibration: Int = 1,
    /**
     * Display a toast with the current value when seeking.
     */
    val feedbackToast: Boolean = true,
    /**
     * Show the system panel for the currently-being-controlled volume.
     */
    val feedbackSystemPanel: Boolean = false,
    /**
     * Stop seek at pivot points requiring user to reengage gesture for going further.
     */
    val seekSteps: Boolean = true,
    val seekAcceleration: Boolean = false,
    val orientationFilter: OrientationFilter = OrientationFilter.All,
)
