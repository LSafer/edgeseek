package net.lsafer.edgeseek.app.impl

import android.annotation.SuppressLint
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.MotionEvent
import android.view.View
import net.lsafer.edgeseek.app.data.settings.EdgeData
import net.lsafer.edgeseek.app.data.settings.EdgeSide

class SeekFeatureListener(
    private val implLocal: ImplLocal,
    private val data: EdgeData,
    private val side: EdgeSide,
    private val impl: SeekFeatureImpl,
) : View.OnTouchListener {
    private var currentRange: IntRange? = null
    private var currentOriginXOrY: Float? = null
    private var currentOriginValue: Int? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View, event: MotionEvent): Boolean {
        val newXOrY = when (side) {
            EdgeSide.Left, EdgeSide.Right -> event.y
            EdgeSide.Top, EdgeSide.Bottom -> event.x
        }

        return when (event.action) {
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_DOWN, MotionEvent.ACTION_UP -> {
                currentRange = null
                currentOriginXOrY = null
                currentOriginValue = null

                if (data.seekVibrate > 0) {
                    val vibrator = implLocal.context.getSystemService(Vibrator::class.java)

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                        vibrator.vibrate(
                            VibrationEffect.createOneShot(
                                data.seekVibrate.toLong(),
                                VibrationEffect.DEFAULT_AMPLITUDE
                            )
                        )
                    else
                        @Suppress("DEPRECATION")
                        vibrator.vibrate(data.seekVibrate.toLong())
                }
                if (data.seekToast) {
                    val value = impl.fetchValue(implLocal)
                    implLocal.toast.update("$value")
                }
                false
            }

            else -> {
                if (currentOriginValue == null) {
                    currentOriginValue = impl.fetchValue(implLocal)
                }
                if (currentOriginXOrY == null) {
                    currentOriginXOrY = newXOrY
                    return false
                }

                val deltaXOrY = currentOriginXOrY!! - newXOrY

                if (currentRange == null) {
                    currentRange = if (data.seekSteps)
                        impl.fetchStepRange(implLocal, deltaXOrY.toInt())
                    else
                        impl.fetchRange(implLocal)
                }

                val factor = 20_000f / (currentRange!!.last - currentRange!!.first)
                val newValue = currentOriginValue!! + (deltaXOrY / factor * data.sensitivity).toInt()
                val newValueCoerced = newValue.coerceIn(currentRange!!)

                val value = impl.updateValue(implLocal, newValueCoerced)

                if (data.seekToast) {
                    implLocal.toast.update("$value")
                }

                false
            }
        }
    }
}
