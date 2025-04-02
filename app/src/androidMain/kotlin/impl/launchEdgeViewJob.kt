package net.lsafer.edgeseek.app.impl

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.WindowManager.LayoutParams
import co.touchlab.kermit.Logger
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import net.lsafer.edgeseek.app.data.settings.*
import kotlin.math.roundToInt

private val logger = Logger.withTag("net.lsafer.edgeseek.app.impl.launchEdgeViewJob")

@SuppressLint("RtlHardcoded")
fun CoroutineScope.launchEdgeViewJob(
    implLocal: ImplLocal,
    windowManager: WindowManager,
    displayRotation: Int,
    displayHeight: Int,
    displayWidth: Int,
    dataFlow: Flow<EdgeData>,
): Job {
    val view = View(implLocal.context)
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
            LayoutParams.FLAG_LAYOUT_IN_SCREEN or
            LayoutParams.FLAG_LAYOUT_NO_LIMITS or
            LayoutParams.FLAG_NOT_TOUCH_MODAL or
            LayoutParams.FLAG_SHOW_WHEN_LOCKED

    val job = launch {
        dataFlow.collect { data ->
            withContext(Dispatchers.Main) {
                if (!data.activated) {
                    runCatching { windowManager.removeView(view) }
                    return@withContext
                }

                val side = data.pos.side.rotate(displayRotation)

                val windowLength = when (side) {
                    EdgeSide.Left, EdgeSide.Right -> displayHeight
                    EdgeSide.Top, EdgeSide.Bottom -> displayWidth
                }

                val lengthPct = data.pos.calculateLengthPct()
                val length = (lengthPct * windowLength).roundToInt()

                val offsetPct = data.pos.calculateRotationalOffsetPct()
                val offset = (offsetPct * windowLength).roundToInt()

                windowParams.height = when (side) {
                    EdgeSide.Left, EdgeSide.Right -> length
                    EdgeSide.Top, EdgeSide.Bottom -> data.thickness
                }
                windowParams.width = when (side) {
                    EdgeSide.Left, EdgeSide.Right -> data.thickness
                    EdgeSide.Top, EdgeSide.Bottom -> length
                }
                windowParams.x = when (side) {
                    EdgeSide.Top, EdgeSide.Bottom -> offset
                    EdgeSide.Left, EdgeSide.Right -> 0
                }
                windowParams.y = when (side) {
                    EdgeSide.Left, EdgeSide.Right -> offset
                    EdgeSide.Top, EdgeSide.Bottom -> 0
                }
                windowParams.gravity = when (side) {
                    EdgeSide.Bottom -> Gravity.BOTTOM or Gravity.RIGHT
                    EdgeSide.Left -> Gravity.LEFT or Gravity.BOTTOM
                    EdgeSide.Top -> Gravity.TOP or Gravity.LEFT
                    EdgeSide.Right -> Gravity.RIGHT or Gravity.TOP
                }
                windowParams.alpha = Color.alpha(data.color) / 255f

                view.setBackgroundColor(data.color)
                view.alpha = Color.alpha(data.color) / 255f

                view.setOnTouchListener(createSeekFeatureTouchListener(implLocal, data, side))

                runCatching { windowManager.removeView(view) }
                runCatching { windowManager.addView(view, windowParams) }
                    .onFailure { e -> logger.e("failed adding view to window", e) }
            }
        }
    }

    job.invokeOnCompletion { e ->
        logger.e("failure while executing job", e)

        launch(Dispatchers.Main + NonCancellable) {
            runCatching { windowManager.removeView(view) }
        }
    }

    return job
}

@SuppressLint("ClickableViewAccessibility")
private fun createSeekFeatureTouchListener(
    implLocal: ImplLocal,
    data: EdgeData,
    side: EdgeSide,
): View.OnTouchListener {
    return when (data.seekFeature) {
        EdgeSeekFeature.Nothing ->
            View.OnTouchListener { _, _ -> false }

        EdgeSeekFeature.ControlBrightness ->
            SeekFeatureListener(implLocal, data, side, SeekFeatureImpl.ControlBrightness)

        EdgeSeekFeature.ControlBrightnessWithDimmer ->
            SeekFeatureListener(implLocal, data, side, SeekFeatureImpl.ControlBrightnessWithDimmer)

        EdgeSeekFeature.ControlMusic ->
            SeekFeatureListener(implLocal, data, side, SeekFeatureImpl.ControlAudio.Music)

        EdgeSeekFeature.ControlAlarm ->
            SeekFeatureListener(implLocal, data, side, SeekFeatureImpl.ControlAudio.Alarm)

        EdgeSeekFeature.ControlSystem ->
            SeekFeatureListener(implLocal, data, side, SeekFeatureImpl.ControlAudio.System)

        EdgeSeekFeature.ControlRing ->
            SeekFeatureListener(implLocal, data, side, SeekFeatureImpl.ControlAudio.Ring)
    }
}
