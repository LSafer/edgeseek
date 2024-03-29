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
