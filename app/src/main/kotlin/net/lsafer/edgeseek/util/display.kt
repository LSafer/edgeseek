package net.lsafer.edgeseek.util

import android.graphics.Point
import android.view.WindowManager

fun WindowManager.windowSize(): Pair<Int, Int> {
    val point = Point()
    @Suppress("DEPRECATION")
    defaultDisplay.getRealSize(point)
    return point.y to point.x
}

fun WindowManager.windowRotation(): Int {
    @Suppress("DEPRECATION")
    return defaultDisplay.rotation
}
