package net.lsafer.edgeseek.app.impl

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.core.content.getSystemService
import co.touchlab.kermit.Logger

class CustomDimmerFacade(context: Context) {
    companion object {
        private val logger = Logger.withTag(CustomDimmerFacade::class.qualifiedName!!)
    }

    private val windowManager = context.getSystemService<WindowManager>()!!
    private val windowParams = WindowManager.LayoutParams()

    private var view = View(context)

    private var attached = false

    var currentValue: Int = 0
        private set

    init {
        @Suppress("DEPRECATION")
        windowParams.type = when {
            Build.VERSION.SDK_INT >= 26 ->
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY

            else ->
                WindowManager.LayoutParams.TYPE_PHONE
        }
        @Suppress("DEPRECATION")
        windowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR or
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_OVERSCAN or
                WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS

        val windowSize = Point()
        @Suppress("DEPRECATION")
        windowManager.defaultDisplay.getSize(windowSize)
        windowParams.height = windowSize.y * 3
        windowParams.width = windowSize.x * 3
    }

    fun update(value: Int) {
        require(value in 0..255) { "Bad alpha value: $value" }

        currentValue = value

        if (value == 0) {
            if (attached) {
                runCatching { windowManager.removeView(view) }
                    .onSuccess { attached = false }
            }
        } else {
            view.setBackgroundColor(android.graphics.Color.argb(255, 0, 0, 0))
            view.alpha = value / 255f
            windowParams.alpha = value / 255f

            if (!attached) {
                runCatching { windowManager.addView(view, windowParams) }
                    .onFailure { e -> logger.e("failed to attach dimmer to window", e) }
                    .onSuccess { attached = true }
            } else {
                runCatching { windowManager.updateViewLayout(view, windowParams) }
                    .onFailure { e -> logger.e("failed to update dimmer window params", e) }
            }
        }
    }
}
