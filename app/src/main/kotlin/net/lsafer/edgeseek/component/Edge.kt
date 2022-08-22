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

@file:SuppressLint("RtlHardcoded", "ClickableViewAccessibility")

package net.lsafer.edgeseek.component

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.WindowManager.LayoutParams
import net.lsafer.edgeseek.model.EdgeData
import net.lsafer.edgeseek.model.EdgeLongClickTask
import net.lsafer.edgeseek.model.EdgeSeekTask
import net.lsafer.edgeseek.model.EdgeSide
import net.lsafer.edgeseek.util.windowRotation
import net.lsafer.edgeseek.util.windowSize
import kotlin.math.roundToInt

class Edge(
    val context: Context,
    var data: EdgeData
) {
    val manager: WindowManager = context.getSystemService(WindowManager::class.java)

    var view = View(context)
    var params = LayoutParams().also {
        @Suppress("DEPRECATION")
        it.type = when {
            Build.VERSION.SDK_INT >= 26 ->
                LayoutParams.TYPE_APPLICATION_OVERLAY
            else ->
                LayoutParams.TYPE_PHONE
        }
        @Suppress("DEPRECATION")
        it.flags = LayoutParams.FLAG_NOT_FOCUSABLE or
                LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                LayoutParams.FLAG_NOT_TOUCH_MODAL or
                LayoutParams.FLAG_SHOW_WHEN_LOCKED
    }
    var side: EdgeSide = data.side
    var attached: Boolean = false

    fun start() {
        if (!data.activated) {
            detachFromWindow()
            return
        }

        updateSide()
        updateParams()
        updateView()
        updateSeekObserver()
        updateLongClickObserver()
        attachToWindow()
    }

    fun stop() {
        detachFromWindow()
    }

    fun update(newData: EdgeData) {
        data = newData

        if (!data.activated) {
            detachFromWindow()
            return
        }

        updateSide()
        updateParams()
        updateView()
        updateSeekObserver()
        updateLongClickObserver()
        attachToWindow()
    }

    fun updateSide() {
        val rotation = manager.windowRotation()

        side = when {
            data.persistent -> EdgeSide.values().first {
                it.value == (rotation * 3 + data.side.value) % 4
            }
            else -> data.side
        }
    }

    fun updateParams() {
        val (windowHeight, windowWidth) = manager.windowSize()

        params.height = when (side) {
            EdgeSide.Left, EdgeSide.Right ->
                (data.ratio * windowHeight).roundToInt()
            EdgeSide.Top, EdgeSide.Bottom ->
                data.width
        }
        params.width = when (side) {
            EdgeSide.Top, EdgeSide.Bottom ->
                (data.ratio * windowWidth).roundToInt()
            EdgeSide.Left, EdgeSide.Right ->
                data.width
        }
        params.x = when (side) {
            EdgeSide.Top, EdgeSide.Bottom ->
                (data.offset * windowWidth).roundToInt()
            EdgeSide.Left, EdgeSide.Right -> 0
        }
        params.y = when (side) {
            EdgeSide.Left, EdgeSide.Right ->
                (data.offset * windowHeight).roundToInt()
            EdgeSide.Top, EdgeSide.Bottom -> 0
        }
        params.gravity = when (side) {
            EdgeSide.Bottom -> Gravity.BOTTOM or Gravity.RIGHT
            EdgeSide.Left -> Gravity.BOTTOM or Gravity.LEFT
            EdgeSide.Top -> Gravity.TOP or Gravity.LEFT
            EdgeSide.Right -> Gravity.TOP or Gravity.RIGHT
        }
        params.alpha = Color.alpha(data.color) / 255f
    }

    fun updateView() {
        view.setBackgroundColor(data.color)
        view.alpha = Color.alpha(data.color) / 255f
    }

    fun updateSeekObserver() {
        view.setOnTouchListener(when (data.seekTask) {
            EdgeSeekTask.ControlBrightness ->
                EdgeSeekListener(context, data, side, ControlBrightness)
            EdgeSeekTask.ControlMusic ->
                EdgeSeekListener(context, data, side, ControlAudio.Music)
            EdgeSeekTask.ControlAlarm ->
                EdgeSeekListener(context, data, side, ControlAudio.Alarm)
            EdgeSeekTask.ControlSystem ->
                EdgeSeekListener(context, data, side, ControlAudio.System)
            EdgeSeekTask.ControlRing ->
                EdgeSeekListener(context, data, side, ControlAudio.Ring)
            else ->
                EdgeSeekListener
        })
    }

    fun updateLongClickObserver() {
        view.setOnLongClickListener(when (data.longClickTask) {
            EdgeLongClickTask.ExpandStatusBar ->
                EdgeLongClickListener(context, ExpandStatusBar)
            else ->
                EdgeLongClickListener
        })
    }

    fun attachToWindow() {
        try {
            if (attached) {
                manager.updateViewLayout(view, params)
            } else {
                manager.addView(view, params)
                attached = true
            }
        } catch (e: Exception) {
            Log.e("LALA_LAND", "attachToWindow: ", e)
        }
    }

    fun detachFromWindow() {
        try {
            if (attached) {
                manager.removeView(view)
                attached = false
            }
        } catch (e: Exception) {
            Log.e("LALA_LAND", "detachFromWindow: ", e)
        }
    }
}

class EdgeSeekListener(
    val context: Context,
    val data: EdgeData,
    val side: EdgeSide,
    val observer: SeekObserver
) : View.OnTouchListener {
    companion object : View.OnTouchListener {
        override fun onTouch(view: View, event: MotionEvent): Boolean {
            return false
        }
    }

    var currentAxis: Float? = null

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_DOWN, MotionEvent.ACTION_UP -> {
                currentAxis = null
                if (data.seekVibrate > 0) {
                    val vibrator = context.getSystemService(Vibrator::class.java)

                    @Suppress("DEPRECATION")
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                        vibrator.vibrate(VibrationEffect.createOneShot(data.seekVibrate.toLong(), VibrationEffect.DEFAULT_AMPLITUDE))
                    else
                        vibrator.vibrate(data.seekVibrate.toLong())
                }
                false
            }
            else -> {
                val newAxis = when (side) {
                    EdgeSide.Left, EdgeSide.Right -> event.y
                    EdgeSide.Top, EdgeSide.Bottom -> event.x
                }

                val delta = currentAxis?.minus(newAxis)?.times(data.sensitivity)
                val value = observer.onSeek(context, delta ?: 0f)

                if (data.seekToast) {
                    SingleToast.display(context, "$value")
                }

                currentAxis = newAxis
                false
            }
        }
    }
}

class EdgeLongClickListener(
    val context: Context,
    val observer: LongClickObserver
) : View.OnLongClickListener {
    companion object : View.OnLongClickListener {
        override fun onLongClick(view: View?): Boolean {
            return false
        }
    }

    override fun onLongClick(view: View?): Boolean {
        observer.onLongClick(context)
        return true
    }
}
