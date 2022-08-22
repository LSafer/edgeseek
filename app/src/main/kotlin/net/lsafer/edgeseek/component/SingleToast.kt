/*
 *	Copyright 2020 LSafer
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

package net.lsafer.edgeseek.component

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import android.view.WindowManager.LayoutParams
import android.widget.TextView
import androidx.cardview.widget.CardView
import kotlinx.coroutines.*

object SingleToast {
    lateinit var text: String
    lateinit var cardView: CardView
    lateinit var textView: TextView
    var showId = 0

    var params = LayoutParams().also {
        @Suppress("DEPRECATION")
        it.type = when {
            Build.VERSION.SDK_INT >= 26 ->
                LayoutParams.TYPE_APPLICATION_OVERLAY
            else ->
                LayoutParams.TYPE_PHONE
        }
        @Suppress("DEPRECATION")
        it.flags = LayoutParams.FLAG_NOT_FOCUSABLE or
                LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                LayoutParams.FLAG_NOT_TOUCH_MODAL or
                LayoutParams.FLAG_SHOW_WHEN_LOCKED
        it.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
        it.height = 100
        it.width = 200
        it.y = 50
    }
    var attached: Boolean = false

    fun display(context: Context, text: String) {
        buildView(context)
        updateText(text)
        show(context)
    }

    fun show(context: Context) {
        attachToWindow(context)

        val expectedId = ++showId

        CoroutineScope(Dispatchers.Unconfined).launch {
            delay(2_000)
            if (showId == expectedId) {
                withContext(Dispatchers.Main) {
                    detachFromWindow(context)
                }
            }
        }
    }

    fun buildView(context: Context) {
        if (!SingleToast::textView.isInitialized || !SingleToast::cardView.isInitialized) {
            cardView = CardView(context)
            textView = TextView(context)
            textView.gravity = Gravity.CENTER
            cardView.radius = 25f
            cardView.addView(textView)
        }
    }

    fun updateText(newText: String) {
        textView.text = newText
    }

    fun attachToWindow(context: Context) {
        val manager = context.getSystemService(WindowManager::class.java)

        try {
            if (!attached) {
                manager.addView(cardView, params)
                attached = true
            }
        } catch (e: Exception) {
            Log.e("LALA_LAND", "attachToWindow: ", e)
        }
    }

    fun detachFromWindow(context: Context) {
        val manager = context.getSystemService(WindowManager::class.java)

        try {
            if (attached) {
                manager.removeView(cardView)
                attached = false
            }
        } catch (e: Exception) {
            Log.e("LALA_LAND", "detachFromWindow: ", e)
        }
    }
}
