package net.lsafer.edgeseek

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.view.WindowManager
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import net.lsafer.edgeseek.component.Edge
import net.lsafer.edgeseek.model.AppData
import net.lsafer.edgeseek.model.applicationData
import net.lsafer.edgeseek.receiver.ScreenOffBroadCastReceiver

class MainService : Service() {
    lateinit var data: AppData
    val edges: MutableList<Edge> = mutableListOf()

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        startForeground()
        updateAppData()
        detectDeactivation()
        observeAppData()
        createEdges()
        registerBroadcastReceivers()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        updateAppData()
        detectDeactivation()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyEdges()
        unregisterBroadcastReceivers()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        updateEdges()
    }

    fun onDataChange() {
        updateAppData()
        detectDeactivation()
        updateEdges()
    }

    fun startForeground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val title = "Running In Background"
            val description = """
                Allows the application to work in background.
                Recommended disabling this notification since it has no real value
            """.trimIndent()

            val channel = NotificationChannel("main", title, NotificationManager.IMPORTANCE_MIN)
            channel.description = description
            this.getSystemService(NotificationManager::class.java)
                .createNotificationChannel(channel)
            val notification = NotificationCompat.Builder(this, channel.id)
                .setContentTitle(title)
                .setContentText(description)
                .setSmallIcon(R.drawable.ic_sync)
                .build()
            this.startForeground(1, notification)
        }
    }

    fun observeAppData() {
        appDataStore.data
            .onEach { onDataChange() }
            .launchIn(CoroutineScope(Dispatchers.IO))
    }

    fun detectDeactivation() {
        if (!data.activated) {
            stopSelf()
        }
    }

    fun updateAppData() {
        data = applicationData().value
    }

    fun createEdges() {
        val manager = getSystemService(WindowManager::class.java)
        val context = when {
            false && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                createWindowContext(
                    manager.defaultDisplay,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    Bundle()
                )
            }
            else -> this
        }

        CoroutineScope(Dispatchers.Main).launch {
            edges.clear()
            edges += data.edges.map { Edge(context, it) }
            edges.forEach { it.start() }
        }
    }

    fun updateEdges() {
        val context = this

        CoroutineScope(Dispatchers.Main).launch {
            edges -= edges
                .filter { edge -> data.edges.none { edge.data.id == it.id } }
                .onEach { it.stop() }
                .toSet()

            edges += data.edges
                .filter { edges.none { edge -> edge.data.id == it.id } }
                .map { Edge(context, it) }
                .onEach { it.start() }

            data.edges
                .associateWith { edges.firstOrNull { edge -> edge.data.id == it.id } }
                .filterValues { it != null }
                .forEach { (data, edge) -> edge!!.update(data) }
        }
    }

    fun destroyEdges() {
        CoroutineScope(Dispatchers.Main).launch {
            edges.forEach { it.stop() }
            edges.clear()
        }
    }

    fun registerBroadcastReceivers() {
        registerReceiver(ScreenOffBroadCastReceiver, IntentFilter(Intent.ACTION_SCREEN_OFF))
    }

    fun unregisterBroadcastReceivers() {
        unregisterReceiver(ScreenOffBroadCastReceiver)
    }
}
