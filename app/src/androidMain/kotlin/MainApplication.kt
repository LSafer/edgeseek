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

import android.app.Application
import android.content.Intent
import android.os.Build
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

class MainApplication : Application() {
    companion object {
        lateinit var globalLocal: Local
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)

        globalLocal = runBlocking {
            createAndroidLocal(this@MainApplication)
        }

        globalLocal.eventbus
            .filterIsInstance<UniEvent.StartService>()
            .onEach {
                val context = this@MainApplication

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    startForegroundService(Intent(context, MainService::class.java))
                else
                    startService(Intent(context, MainService::class.java))
            }
            .launchIn(globalLocal.ioScope)
    }
}
