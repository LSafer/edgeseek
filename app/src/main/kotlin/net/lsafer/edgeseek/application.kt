package net.lsafer.edgeseek

import android.app.Application
import android.content.Intent
import android.os.Build
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import net.lsafer.edgeseek.model.AppData
import net.lsafer.edgeseek.model.applicationData
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

lateinit var EdgeSeekApplicationInstance: EdgeSeekApplication

class EdgeSeekApplication : Application() {
    lateinit var data: AppData

    override fun onCreate() {
        super.onCreate()
        EdgeSeekApplicationInstance = this
        EventBus.getDefault().register(this)
        updateAppData()
        observeAppData()
        startEdgesService()
    }

    fun onDataChange() {
        updateAppData()
        startEdgesService()
    }

    fun observeAppData() {
        appDataStore.data
            .catch { }
            .onEach { onDataChange() }
            .launchIn(CoroutineScope(Dispatchers.IO))
    }

    fun startEdgesService() {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ->
                startForegroundService(Intent(this, MainService::class.java))
            else ->
                startService(Intent(this, MainService::class.java))
        }
    }

    fun updateAppData() {
        data = applicationData().value
    }

    @Subscribe
    fun onEvent(event: Any) {
    }
}
