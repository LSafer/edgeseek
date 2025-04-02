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

@file:SuppressLint("StaticFieldLeak")

package net.lsafer.edgeseek.app.impl

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.Gravity
import android.view.WindowManager
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.getSystemService
import co.touchlab.kermit.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CustomToastFacade(context: Context) {
    companion object {
        private val logger = Logger.withTag(CustomToastFacade::class.qualifiedName!!)
    }

    private val windowManager = context.getSystemService<WindowManager>()!!
    private val windowParams = WindowManager.LayoutParams()

    private var containerView = CardView(context)
    private var textView = TextView(context)

    private var showId = 0
    private var attached = false

    init {
        @Suppress("DEPRECATION")
        windowParams.type = when {
            Build.VERSION.SDK_INT >= 26 ->
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY

            else ->
                WindowManager.LayoutParams.TYPE_PHONE
        }
        @Suppress("DEPRECATION")
        windowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
        windowParams.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
        windowParams.height = 100
        windowParams.width = 200
        windowParams.y = 50

        textView.gravity = Gravity.CENTER
        containerView.radius = 25f
        containerView.addView(textView)
    }

    fun update(text: String) {
        CoroutineScope(Dispatchers.Main).launch {
            if (!attached) {
                runCatching { windowManager.addView(containerView, windowParams) }
                    .onFailure { e -> logger.e("failed to attach toast to window", e) }
                    .onFailure { return@launch }
                    .onSuccess { attached = true }
            }

            val expectedId = ++showId

            textView.text = text

            delay(2_000)

            if (expectedId == showId && attached) {
                runCatching { windowManager.removeView(containerView) }
                    .onSuccess { attached = false }
            }
        }
    }
}
