package net.lsafer.edgeseek.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.runtime.getValue
import net.lsafer.edgeseek.model.applicationData

open class ScreenOffBroadCastReceiver : BroadcastReceiver() {
    companion object : ScreenOffBroadCastReceiver()

    override fun onReceive(context: Context, intent: Intent) {
        val data by context.applicationData()

        if (intent.action == Intent.ACTION_SCREEN_OFF) {
            if (data.autoBrightness) {
                Settings.System.putInt(
                    context.contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE,
                    Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
                )
            }
        }
    }
}
