package net.lsafer.edgeseek.app

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import kotlinx.coroutines.flow.MutableStateFlow

class MainAccessibilityService : AccessibilityService() {
    companion object {
        val aliveState = MutableStateFlow(false)
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        aliveState.value = true
    }

    override fun onDestroy() {
        super.onDestroy()
        aliveState.value = false
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // See https://github.com/LSafer/edgeseek/tree/ebeb6df678a4a1ff02e9ea24dccef12d2e6d4086
    }

    override fun onInterrupt() {
    }
}
