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

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import kotlinx.coroutines.launch
import net.lsafer.edgeseek.app.MainApplication.Companion.globalLocal
import net.lsafer.edgeseek.app.components.window.main.MainWindow
import net.lsafer.edgeseek.app.components.wrapper.AndroidUniLocaleProvider
import net.lsafer.edgeseek.app.components.wrapper.AndroidUniWindowCompat
import net.lsafer.edgeseek.app.components.wrapper.UniTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val local = globalLocal
            val activity = this@MainActivity

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

            AndroidUniLocaleProvider(local) {
                AndroidUniWindowCompat(local, activity) {
                    UniTheme(local) {
                        Surface(color = MaterialTheme.colorScheme.background) {
                            MainWindow(local)
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        globalLocal.ioScope.launch {
            globalLocal.eventbus.emit(UniEvent.StartService)
        }
    }
}
