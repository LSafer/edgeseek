package net.lsafer.edgeseek.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.compose.runtime.getValue
import net.lsafer.edgeseek.MainService
import net.lsafer.edgeseek.model.applicationData

open class BootCompleteBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val data by context.applicationData()

        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                context.startForegroundService(Intent(context, MainService::class.java))
            else
                context.startService(Intent(context, MainService::class.java))
        }
    }
}
