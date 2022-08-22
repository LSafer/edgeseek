package net.lsafer.edgeseek.model

import android.graphics.Color
import androidx.compose.runtime.*
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
enum class EdgeSide(val value: Int) {
    Bottom(0),
    Left(1),
    Top(2),
    Right(3)
}

@Serializable
enum class EdgeSeekTask {
    Nothing,
    ControlBrightness,
    ControlAlarm,
    ControlMusic,
    ControlRing,
    ControlSystem
}

@Serializable
enum class EdgeLongClickTask {
    Nothing,
    ExpandStatusBar
}

/**
 * Edge configuration.
 */
@Serializable
data class EdgeData(
    /**
     * The id of this edge.
     */
    val id: String = UUID.randomUUID().toString(),
    /**
     * True, if this edge is activated.
     */
    val activated: Boolean = false,
    /**
     * The side this edge should be at.
     */
    val side: EdgeSide = EdgeSide.Bottom,
    /**
     * How much this edge takes of space. % of available space.
     */
    val ratio: Float = 1f,
    /**
     * How much this edge offsets. % of available space.
     */
    val offset: Float = 0f,
    /**
     * The width of the edge.
     */
    val width: Int = 35,
    /**
     * The color of the edge. argb
     */
    val color: Int = Color.argb(0, 255, 0, 0),
    /**
     * True, if this edge should not rotate with the screen.
     */
    val persistent: Boolean = true,
    /**
     * The sensitivity of this edge.
     */
    val sensitivity: Int = 45,
    /**
     * The name of the task to be performed on seeking.
     */
    val seekTask: EdgeSeekTask = EdgeSeekTask.Nothing,
    /**
     * The strength of vibration when seeking.
     */
    val seekVibrate: Int = 1,
    /**
     * Display a toast with the current value when seeking.
     */
    val seekToast: Boolean = true,
    /**
     * The name of the task to be performed on long clicking.
     */
    val longClickTask: EdgeLongClickTask = EdgeLongClickTask.Nothing
)

@Composable
fun rememberEdgeData(id: String): MutableState<EdgeData> {
    var data by rememberApplicationData()
    val edge = remember(data) {
        data.edges.firstOrNull { it.id == id }
            ?: EdgeData(id = id)
    }

    return object : MutableState<EdgeData> {
        override var value: EdgeData
            get() = edge
            set(value) {
                data = data.copy(
                    edges = data.edges.filter { it.id != id } + value
                )
            }

        override fun component1(): EdgeData = value
        override fun component2(): (EdgeData) -> Unit = { value = it }
    }
}
