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
package net.lsafer.edgeseek.app.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import net.lsafer.edgeseek.app.MainApplication.Companion.globalLocal
import net.lsafer.edgeseek.app.PK_FLAG_BRIGHTNESS_RESET
import net.lsafer.edgeseek.app.impl.ImplLocal
import net.lsafer.sundry.storage.select

// Used @JvmOverloads afraid android **might** try instantiating it
open class ScreenOffBroadCastReceiver @JvmOverloads constructor(
    private val implLocal: ImplLocal? = null,
) : BroadcastReceiver() {
    companion object {
        private val logger = Logger.withTag(ScreenOffBroadCastReceiver::class.qualifiedName!!)
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_SCREEN_OFF) {
            globalLocal.ioScope.launch {
                val brightnessReset = globalLocal.dataStore
                    .select<Boolean>(PK_FLAG_BRIGHTNESS_RESET)
                    .firstOrNull()

                if (brightnessReset == false)
                    return@launch

                try {
                    Settings.System.putInt(
                        context.contentResolver,
                        Settings.System.SCREEN_BRIGHTNESS_MODE,
                        Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
                    )
                    implLocal?.dimmer?.update(0)
                } catch (e: Exception) {
                    logger.e("Couldn't toggle auto brightness", e)
                }
            }
        }
    }
}
