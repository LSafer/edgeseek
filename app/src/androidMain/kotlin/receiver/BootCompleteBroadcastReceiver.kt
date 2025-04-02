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
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import net.lsafer.edgeseek.app.MainApplication.Companion.globalLocal
import net.lsafer.edgeseek.app.PK_FLAG_AUTO_BOOT
import net.lsafer.edgeseek.app.UniEvent
import net.lsafer.sundry.storage.select

open class BootCompleteBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            globalLocal.ioScope.launch {
                val autoBoot = globalLocal.dataStore
                    .select<Boolean>(PK_FLAG_AUTO_BOOT)
                    .firstOrNull()

                if (autoBoot == null || !autoBoot)
                    return@launch

                globalLocal.eventbus.emit(UniEvent.StartService)
            }
        }
    }
}
