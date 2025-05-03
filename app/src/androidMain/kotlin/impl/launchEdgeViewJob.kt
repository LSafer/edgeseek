package net.lsafer.edgeseek.app.impl

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.view.Gravity
import android.view.WindowManager
import android.view.WindowManager.LayoutParams
import androidx.cardview.widget.CardView
import co.touchlab.kermit.Logger
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import net.lsafer.edgeseek.app.data.settings.EdgeCorner
import net.lsafer.edgeseek.app.data.settings.EdgePosData
import net.lsafer.edgeseek.app.data.settings.EdgeSide
import net.lsafer.edgeseek.app.data.settings.EdgeSideData
import kotlin.math.roundToInt

private val logger = Logger.withTag("net.lsafer.edgeseek.app.impl.launchEdgeViewJob")

@SuppressLint("RtlHardcoded", "ClickableViewAccessibility")
fun CoroutineScope.launchEdgeViewJob(
    implLocal: ImplLocal,
    windowManager: WindowManager,
    displayRotation: Int,
    displayHeight: Int,
    displayWidth: Int,
    displayDensityDpi: Int,
    sideDataFlow: Flow<EdgeSideData>,
    posDataFlow: Flow<EdgePosData>,
): Job {
    val view = CardView(implLocal.context)
    view.radius = 25f
    view.elevation = 1f

    val windowParams = LayoutParams()
    @Suppress("DEPRECATION")
    windowParams.type = when {
        Build.VERSION.SDK_INT >= 26 ->
            LayoutParams.TYPE_APPLICATION_OVERLAY

        else ->
            LayoutParams.TYPE_PHONE
    }
    @Suppress("DEPRECATION")
    windowParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE or
            LayoutParams.FLAG_NOT_TOUCH_MODAL or
            LayoutParams.FLAG_SHOW_WHEN_LOCKED // <-- this doesn't work for some reason

    val job = combine(sideDataFlow, posDataFlow) { a, b -> a to b }
        .onEach { (sideData, posData) ->
            if (
                !posData.activated ||
                !posData.pos.isIncludedWhenSegmented(sideData.nSegments) ||
                !posData.orientationFilter.test(displayRotation)
            ) {
                runCatching { windowManager.removeView(view) }
                return@onEach
            }

            val sideRotated = posData.pos.side.rotate(displayRotation)
            val cornerRotated = posData.pos.corner.rotate(displayRotation)

            val lengthPct = 1f / sideData.nSegments
            val windowLength = when (sideRotated) {
                EdgeSide.Left, EdgeSide.Right -> displayHeight
                EdgeSide.Top, EdgeSide.Bottom -> displayWidth
            }

            val length = (lengthPct * windowLength).roundToInt()

            windowParams.height = when (sideRotated) {
                EdgeSide.Left, EdgeSide.Right -> length
                EdgeSide.Top, EdgeSide.Bottom -> posData.thickness
            }
            windowParams.width = when (sideRotated) {
                EdgeSide.Left, EdgeSide.Right -> posData.thickness
                EdgeSide.Top, EdgeSide.Bottom -> length
            }
            windowParams.gravity = when (cornerRotated) {
                EdgeCorner.BottomRight -> Gravity.BOTTOM or Gravity.RIGHT
                EdgeCorner.Bottom -> Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
                EdgeCorner.BottomLeft -> Gravity.BOTTOM or Gravity.LEFT
                EdgeCorner.Left -> Gravity.LEFT or Gravity.CENTER_VERTICAL
                EdgeCorner.TopLeft -> Gravity.TOP or Gravity.LEFT
                EdgeCorner.Top -> Gravity.TOP or Gravity.CENTER_HORIZONTAL
                EdgeCorner.TopRight -> Gravity.TOP or Gravity.RIGHT
                EdgeCorner.Right -> Gravity.RIGHT or Gravity.CENTER_VERTICAL
            }
            windowParams.alpha = Color.alpha(posData.color) / 255f

            view.setCardBackgroundColor(posData.color)
            view.alpha = Color.alpha(posData.color) / 255f

            view.setOnTouchListener(
                EdgeTouchListener(
                    implLocal = implLocal,
                    edgePosData = posData,
                    edgeSide = sideRotated,
                    dpi = displayDensityDpi,
                    onSeekImpl = ControlFeatureImpl.from(posData.onSeek),
                    onLongClick = ActionFeatureImpl.from(posData.onLongClick),
                    onDoubleClick = ActionFeatureImpl.from(posData.onDoubleClick),
                    onSwipeUp = ActionFeatureImpl.from(posData.onSwipeUp),
                    onSwipeDown = ActionFeatureImpl.from(posData.onSwipeDown),
                    onSwipeLeft = ActionFeatureImpl.from(posData.onSwipeLeft),
                    onSwipeRight = ActionFeatureImpl.from(posData.onSwipeRight),
                )
            )

            runCatching { windowManager.removeView(view) }
            runCatching { windowManager.addView(view, windowParams) }
                .onFailure { e -> logger.e("failed adding view to window", e) }
        }
        .launchIn(scope = this + Dispatchers.Main)

    job.invokeOnCompletion { e ->
        if (e !is CancellationException)
            logger.e("failure while executing job", e)

        launch(Dispatchers.Main + NonCancellable) {
            runCatching { windowManager.removeView(view) }
        }
    }

    return job
}
