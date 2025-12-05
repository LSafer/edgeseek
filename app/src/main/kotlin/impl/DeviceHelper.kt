/*
 * OnePlus/Oppo Device Detection Helper
 * 
 * This file provides helper functions to detect OnePlus/Oppo/Realme devices
 * that have volume control restrictions in background apps.
 */
package net.lsafer.edgeseek.app.impl

import android.os.Build

/**
 * Helper object to detect OnePlus/Oppo/Realme devices that need special volume handling.
 * 
 * These OEMs restrict AudioManager.setStreamVolume() calls from background apps
 * since Android 12 (API 31). For these devices, we use adjustStreamVolume() 
 * with FLAG_SHOW_UI to display the native volume panel instead.
 */
object DeviceHelper {
    private val AFFECTED_BRANDS = listOf("oneplus", "oppo", "realme", "oplus")
    
    /**
     * Check if the current device is a OnePlus/Oppo/Realme device.
     */
    fun isOnePlusOppoDevice(): Boolean {
        val manufacturer = Build.MANUFACTURER.lowercase()
        val brand = Build.BRAND.lowercase()
        
        return AFFECTED_BRANDS.any { 
            manufacturer.contains(it) || brand.contains(it) 
        }
    }
    
    /**
     * Check if the device requires the volume control workaround.
     * This is true for OnePlus/Oppo/Realme devices running Android 12 or later.
     */
    fun requiresVolumeWorkaround(): Boolean {
        return isOnePlusOppoDevice() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    }
    
    /**
     * Get a human-readable device description for logging.
     */
    fun getDeviceDescription(): String {
        return "${Build.MANUFACTURER} ${Build.MODEL} (Android ${Build.VERSION.RELEASE}, SDK ${Build.VERSION.SDK_INT})"
    }
}
