/*
 *	Copyright 2020-2022 LSafer
 *
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *	    http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 */
package net.lsafer.edgeseek.app

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.graphics.Point
import android.os.Build
import android.os.IBinder
import android.view.WindowManager
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import net.lsafer.edgeseek.app.MainApplication.Companion.globalLocal
import net.lsafer.edgeseek.app.data.settings.EdgeData
import net.lsafer.edgeseek.app.data.settings.EdgePos
import net.lsafer.edgeseek.app.impl.CustomDimmerFacade
import net.lsafer.edgeseek.app.impl.CustomToastFacade
import net.lsafer.edgeseek.app.impl.ImplLocal
import net.lsafer.edgeseek.app.impl.launchEdgeViewJob
import net.lsafer.edgeseek.app.receiver.ScreenOffBroadCastReceiver
import net.lsafer.sundry.storage.select

class MainService : Service() {
    private val implLocal = ImplLocal()
    private var launchedEdgeViewJobsSubJobFlow = MutableSharedFlow<Job>(1)

    override fun onBind(intent: Intent?): IBinder? = null
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int = START_STICKY

    override fun onCreate() {
        super.onCreate()
        implLocal.local = globalLocal
        implLocal.context = this
        implLocal.defaultScope = CoroutineScope(
            Dispatchers.Default + SupervisorJob() +
                    CoroutineExceptionHandler { _, e ->
                        moduleLogger.e("Unhandled coroutine exception", e)
                    }
        )
        implLocal.toast = CustomToastFacade(this)
        implLocal.dimmer = CustomDimmerFacade(this)
        startForeground()

        implLocal.defaultScope.launch {
            val activated = implLocal.local.dataStore
                .select<Boolean>(PK_FLAG_ACTIVATED)
                .firstOrNull()
                ?: false

            if (!activated) {
                stopSelf()
                return@launch
            }

            launchReceiverJob()
            launchEdgeViewJobsSubJobsCleanupSubJob()
            launchEdgeViewJobsSubJob()
            launchSelfStopSubJob()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        implLocal.defaultScope.cancel()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        launchEdgeViewJobsSubJob()
    }

    @Suppress("DEPRECATION")
    private fun launchEdgeViewJobsSubJob() {
        val windowManager = getSystemService<WindowManager>()!!
        val display = windowManager.defaultDisplay
        val displayRotation = display.rotation

        var displayHeight: Int
        var displayWidth: Int

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            displayWidth = windowManager.currentWindowMetrics.bounds.width()
            displayWidth -= windowManager.currentWindowMetrics.windowInsets.systemWindowInsetLeft
            displayWidth -= windowManager.currentWindowMetrics.windowInsets.systemWindowInsetRight
            displayHeight = windowManager.currentWindowMetrics.bounds.height()
            displayHeight -= windowManager.currentWindowMetrics.windowInsets.systemWindowInsetTop
            displayHeight -= windowManager.currentWindowMetrics.windowInsets.systemWindowInsetBottom
        } else {
            val displayRealSize = Point()
            @Suppress("DEPRECATION")
            display.getRealSize(displayRealSize)
            displayWidth = displayRealSize.x
            displayHeight = displayRealSize.y
        }

        val subJob = Job(implLocal.defaultScope.coroutineContext.job)

        // [DRYRUN]
        moduleLogger.i("DRYRUN dh: $displayHeight dw: $displayWidth")

        implLocal.defaultScope.launch {
            launchedEdgeViewJobsSubJobFlow.emit(subJob)

            launch(subJob) {
                for (pos in EdgePos.entries) {
                    val dataFlow = implLocal.local.dataStore
                        .select<EdgeData>(pos.key)
                        .filterNotNull()
                        .distinctUntilChanged()

                    launchEdgeViewJob(
                        implLocal = implLocal,
                        windowManager = windowManager,
                        displayRotation = displayRotation,
                        displayHeight = displayHeight,
                        displayWidth = displayWidth,
                        dataFlow = dataFlow,
                    )
                }
            }
        }
    }

    private fun launchEdgeViewJobsSubJobsCleanupSubJob() {
        launchedEdgeViewJobsSubJobFlow
            .runningReduce { oldJob, newJob ->
                oldJob.cancel()
                newJob
            }
            .launchIn(implLocal.defaultScope)
    }

    private fun launchSelfStopSubJob() {
        implLocal.local.dataStore
            .select<Boolean>(PK_FLAG_ACTIVATED)
            .onEach { if (it == null || !it) stopSelf() }
            .launchIn(implLocal.defaultScope)
    }

    private fun launchReceiverJob() {
        val screenOffReceiver = ScreenOffBroadCastReceiver(implLocal)

        registerReceiver(screenOffReceiver, IntentFilter(Intent.ACTION_SCREEN_OFF))

        implLocal.defaultScope.coroutineContext.job.invokeOnCompletion {
            unregisterReceiver(screenOffReceiver)
        }
    }

    private fun startForeground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val strings = implLocal.local.l10nState.value.strings
            val title = strings.stmt.foreground_noti_title
            val description = strings.stmt.foreground_noti_text

            val channel = NotificationChannel("main", title, NotificationManager.IMPORTANCE_MIN)
            channel.description = description
            this.getSystemService(NotificationManager::class.java)
                .createNotificationChannel(channel)
            val notification = NotificationCompat.Builder(this, channel.id)
                .setContentTitle(strings.stmt.foreground_noti_title)
                .setContentText(strings.stmt.foreground_noti_text)
                .setSmallIcon(R.drawable.ic_sync)
                .build()
            this.startForeground(1, notification)
        }
    }
}
