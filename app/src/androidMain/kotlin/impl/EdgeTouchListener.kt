package net.lsafer.edgeseek.app.impl

import android.annotation.SuppressLint
import android.os.Build
import android.os.SystemClock
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import net.lsafer.edgeseek.app.data.settings.EdgePosData
import net.lsafer.edgeseek.app.data.settings.EdgeSide
import kotlin.math.abs

class EdgeTouchListener(
    private val implLocal: ImplLocal,
    private val edgePosData: EdgePosData,
    private val edgeSide: EdgeSide,
    private val dpi: Int,

    private val onLongClick: ActionFeatureImpl?,
    private val onDoubleClick: ActionFeatureImpl?,

    private val onSeekImpl: ControlFeatureImpl?,
    private val onSwipeUp: ActionFeatureImpl?,
    private val onSwipeDown: ActionFeatureImpl?,
    private val onSwipeLeft: ActionFeatureImpl?,
    private val onSwipeRight: ActionFeatureImpl?,
) : View.OnTouchListener, GestureDetector.SimpleOnGestureListener() {
    private val detector = GestureDetector(implLocal.context, this)

    private val xSeekSensitivityFactor = 155f * dpi
    private val xSwipeThresholdDistant = 10f * dpi
//    private val xSeekSensitivityFactor = 80_000f
//    private val xSwipeThresholdDistant = 5_000f

    private val xSwipeEnabled =
        onSwipeUp != null ||
                onSwipeDown != null ||
                onSwipeLeft != null ||
                onSwipeRight != null

    private var mCurrentOriginXOrY: Float? = null
    private var mCurrentOriginYOrX: Float? = null

    private var mCurrentSeekRange: IntRange? = null
    private var mCurrentSeekOrigin: Int? = null

    private var mIsScrolling: Boolean = false
    private var mIsVibrateOnUp: Boolean = true

    init {
        detector.setIsLongpressEnabled(onLongClick != null)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        return detector.onTouchEvent(event).also {
            if (event.action == MotionEvent.ACTION_UP) {
                if (mIsVibrateOnUp)
                    doFeedbackVibration()
            }
        }
    }

    override fun onDown(e: MotionEvent): Boolean {
        mIsScrolling = false
        mIsVibrateOnUp = true
        mCurrentSeekRange = null
        mCurrentSeekOrigin = null
        mCurrentOriginXOrY = when (edgeSide) {
            EdgeSide.Left, EdgeSide.Right -> e.y
            EdgeSide.Top, EdgeSide.Bottom -> e.x
        }
        mCurrentOriginYOrX = when (edgeSide) {
            EdgeSide.Left, EdgeSide.Right -> e.x
            EdgeSide.Top, EdgeSide.Bottom -> e.y
        }

        if (onSeekImpl != null && !xSwipeEnabled) {
            mIsScrolling = true
            doFeedbackVibration()
            doFeedbackToast()
        }

        return true
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
        if (onDoubleClick != null) {
            mIsVibrateOnUp = false
            doFeedbackVibration()
            onDoubleClick.execute(implLocal)
            return true
        }

        return false
    }

    override fun onLongPress(e: MotionEvent) {
        if (onLongClick != null) {
            mIsVibrateOnUp = false
            doFeedbackVibration()
            onLongClick.execute(implLocal)
        }
    }

    override fun onShowPress(e: MotionEvent) {
        if (onSeekImpl != null && xSwipeEnabled) {
            mIsScrolling = true
            doFeedbackVibration()
            doFeedbackToast()
        }
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float,
    ): Boolean {
        e1 ?: return false
        onSeekImpl ?: return false

        if (!mIsScrolling) {
            val now = SystemClock.uptimeMillis()

            if (now - e2.downTime > 300L) {
                mIsScrolling = true
                doFeedbackVibration()
                doFeedbackToast()
            } else {
                return false
            }
        }

        val deltaXOrY = when (edgeSide) {
            EdgeSide.Left, EdgeSide.Right -> e1.y - e2.y
            EdgeSide.Top, EdgeSide.Bottom -> e1.x - e2.x
        }

        if (mCurrentSeekOrigin == null) {
            mCurrentSeekOrigin = onSeekImpl.fetchValue(implLocal)
        }

        if (mCurrentSeekRange == null) {
            mCurrentSeekRange = if (edgePosData.seekSteps)
                onSeekImpl.fetchStepRange(implLocal, deltaXOrY.toInt())
            else
                onSeekImpl.fetchRange(implLocal)
        }

        val factor = xSeekSensitivityFactor / (mCurrentSeekRange!!.last - mCurrentSeekRange!!.first)
        val accBoost = if (edgePosData.seekAcceleration) abs(deltaXOrY / factor) else 1f
        val newValue = mCurrentSeekOrigin!! + ((deltaXOrY * accBoost) / factor * edgePosData.sensitivity).toInt()
        val newValueCoerced = newValue.coerceIn(mCurrentSeekRange!!)

        val value = onSeekImpl.updateValue(implLocal, newValueCoerced, edgePosData.feedbackSystemPanel)

        if (edgePosData.feedbackToast) {
            implLocal.toast.update("$value")
        }

        return false
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float,
    ): Boolean {
        val isVLeaning = abs(velocityX) < abs(velocityY)
        val isVFling = abs(velocityY) > xSwipeThresholdDistant
        val isHFling = abs(velocityX) > xSwipeThresholdDistant

        val isSkipUp = !isVFling || onSwipeUp == null || velocityY > 0
        val isSkipDown = !isVFling || onSwipeDown == null || velocityY < 0
        val isSkipLeft = !isHFling || onSwipeLeft == null || velocityX > 0
        val isSkipRight = !isHFling || onSwipeRight == null || velocityX < 0

        if (!isSkipUp && (isVLeaning || isSkipLeft && isSkipRight)) {
            mIsVibrateOnUp = false
            doFeedbackVibration()
            onSwipeUp!!.execute(implLocal)
            return true
        }
        if (!isSkipDown && (isVLeaning || isSkipLeft && isSkipRight)) {
            mIsVibrateOnUp = false
            doFeedbackVibration()
            onSwipeDown!!.execute(implLocal)
            return true
        }
        if (!isSkipLeft /* && (!isVerticalLeaning || isSkipUp && isSkipDown) */) {
            mIsVibrateOnUp = false
            doFeedbackVibration()
            onSwipeLeft!!.execute(implLocal)
            return true
        }
        if (!isSkipRight /* && (!isVerticalLeaning || isSkipUp && isSkipDown) */) {
            mIsVibrateOnUp = false
            doFeedbackVibration()
            onSwipeRight!!.execute(implLocal)
            return true
        }

        return false
    }

    private fun doFeedbackVibration() {
        if (edgePosData.feedbackVibration > 0) {
            val vibrator = implLocal.context.getSystemService(Vibrator::class.java)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        edgePosData.feedbackVibration.toLong(),
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            else
                @Suppress("DEPRECATION")
                vibrator.vibrate(edgePosData.feedbackVibration.toLong())
        }
    }

    private fun doFeedbackToast() {
        if (onSeekImpl != null && edgePosData.feedbackToast) {
            val value = onSeekImpl.fetchValue(implLocal)
            implLocal.toast.update("$value")
        }
    }
}
