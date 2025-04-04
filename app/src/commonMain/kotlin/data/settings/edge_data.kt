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
import kotlinx.serialization.Serializable

@Serializable
enum class EdgeSide {
    Bottom, Left, Top, Right;

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
    BottomRight("bottom_right", EdgeSide.Bottom, EdgeCorner.BottomRight),
    BottomLeft("bottom_left", EdgeSide.Bottom, EdgeCorner.BottomLeft),
    LeftBottom("left_bottom", EdgeSide.Left, EdgeCorner.BottomLeft),
    LeftCenter("left_center", EdgeSide.Left, EdgeCorner.Left),
    LeftTop("left_top", EdgeSide.Left, EdgeCorner.TopLeft),
    TopLeft("top_left", EdgeSide.Top, EdgeCorner.TopLeft),
    TopRight("top_right", EdgeSide.Top, EdgeCorner.TopRight),
    RightTop("right_top", EdgeSide.Right, EdgeCorner.TopRight),
    RightCenter("right_center", EdgeSide.Right, EdgeCorner.Right),
    RightBottom("right_bottom", EdgeSide.Right, EdgeCorner.BottomRight),
}

@Serializable
enum class EdgeSeekFeature {
    Nothing,
    ControlBrightness,
    ControlBrightnessWithDimmer,
    ControlAlarm,
    ControlMusic,
    ControlRing,
    ControlSystem,
}

@Serializable
data class EdgeData(
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
    /**
     * The name of the task to be performed on seeking.
     */
    val seekFeature: EdgeSeekFeature = EdgeSeekFeature.Nothing,
    /**
     * The strength of vibration when seeking.
     */
    val seekVibrate: Int = 1,
    /**
     * Display a toast with the current value when seeking.
     */
    val seekToast: Boolean = true,
    /**
     * Stop seek at pivot points requiring user to reengage gesture for going further.
     */
    val seekSteps: Boolean = true,
)
