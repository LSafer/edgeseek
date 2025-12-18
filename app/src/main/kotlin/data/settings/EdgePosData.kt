package net.lsafer.edgeseek.app.data.settings

import android.graphics.Color
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EdgePosData(
    /**
     * The position of this edge.
     */
    @SerialName("pos")
    val id: EdgePos,
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
    /**
     * Reverse seek direction.
     */
    val seekReverse: Boolean = false,
    val seekAcceleration: Boolean = false,
    val seekRelateToScreen: Boolean = false,
    val orientationFilter: OrientationFilter = OrientationFilter.All,
)
