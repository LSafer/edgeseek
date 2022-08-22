package net.lsafer.edgeseek.util

import android.graphics.Point
import android.view.WindowManager

fun WindowManager.windowSize(): Pair<Int, Int> {
    val point = Point()
    @Suppress("DEPRECATION")
    defaultDisplay.getRealSize(point)
    return point.x to point.y
}

fun WindowManager.windowRotation(): Int {
    @Suppress("DEPRECATION")
    return defaultDisplay.rotation
}
