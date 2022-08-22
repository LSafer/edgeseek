package net.lsafer.edgeseek

import android.app.Application
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

lateinit var EdgeSeekApplicationInstance: EdgeSeekApplication

class EdgeSeekApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        EdgeSeekApplicationInstance = this
        EventBus.getDefault().register(this)
    }

    @Subscribe
    fun onEvent(event: Any) {
    }
}
