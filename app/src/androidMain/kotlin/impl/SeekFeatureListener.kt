package net.lsafer.edgeseek.app.impl

import android.annotation.SuppressLint
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.MotionEvent
import android.view.View
import net.lsafer.edgeseek.app.data.settings.EdgeData
import net.lsafer.edgeseek.app.data.settings.EdgeSide
import kotlin.math.sign

class SeekFeatureListener(
    private val implLocal: ImplLocal,
    private val data: EdgeData,
    private val side: EdgeSide,
    private val impl: SeekFeatureImpl,
) : View.OnTouchListener {
    private var currentRange: IntRange? = null
    private var previousXOrY: Float? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View, event: MotionEvent): Boolean {
        val newXOrY = when (side) {
            EdgeSide.Left, EdgeSide.Right -> event.y
            EdgeSide.Top, EdgeSide.Bottom -> event.x
        }

        return when (event.action) {
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_DOWN, MotionEvent.ACTION_UP -> {
                previousXOrY = newXOrY
                currentRange = null

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
                false
            }

            else -> {
                if (previousXOrY == null) {
                    previousXOrY = newXOrY
                    return false
                }

                val deltaXOrY = previousXOrY!! - newXOrY

                if (currentRange == null) {
                    currentRange = if (data.seekSteps)
                        impl.fetchStepRange(implLocal, deltaXOrY.sign.toInt())
                    else
                        impl.fetchRange(implLocal)
                }

                val currentValue = impl.fetchValue(implLocal)

                val newValue = currentValue + (deltaXOrY / 100f * data.sensitivity).toInt()
                val newValueCoerced = newValue.coerceIn(currentRange!!)

                val value = impl.updateValue(implLocal, newValueCoerced)

                if (data.seekToast) {
                    implLocal.toast.update("$value")
                }

                previousXOrY = newXOrY
                false
            }
        }
    }
}
