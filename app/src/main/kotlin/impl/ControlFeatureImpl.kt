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
package net.lsafer.edgeseek.app.impl

import android.content.Context
import android.media.AudioManager
import android.provider.Settings
import android.util.Log
import androidx.annotation.UiThread
import androidx.core.content.getSystemService
import net.lsafer.edgeseek.app.Local
import net.lsafer.edgeseek.app.data.settings.ControlFeature

sealed class ControlFeatureImpl {
    companion object {
        private val TAG = ControlFeatureImpl::class.simpleName!!

        fun from(feature: ControlFeature): ControlFeatureImpl? {
            return when (feature) {
                ControlFeature.Nothing -> null
                ControlFeature.Brightness -> Brightness
                ControlFeature.BrightnessWithDimmer -> BrightnessWithDimmer
                ControlFeature.Music -> Audio.Music
                ControlFeature.Alarm -> Audio.Alarm
                ControlFeature.System -> Audio.System
                ControlFeature.Ring -> Audio.Ring
            }
        }
    }

    context(_: Context, _: Local)
    abstract fun fetchRange(): IntRange

    context(_: Context, _: Local)
    open fun fetchStepRange(sign: Int) = fetchRange()

    context(_: Context, _: Local)
    abstract fun fetchValue(): Int

    @UiThread
    context(_: Context, _: Local)
    abstract fun updateValue(newValue: Int, showSystemPanel: Boolean): Int

    data object Brightness : ControlFeatureImpl() {
        context(_: Context, _: Local)
        override fun fetchRange() = 0..255

        context(ctx: Context, _: Local)
        override fun fetchValue(): Int {
            return Settings.System.getInt(
                ctx.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS,
            )
        }

        context(ctx: Context, local: Local)
        override fun updateValue(newValue: Int, showSystemPanel: Boolean): Int {
            val newSystemValue = newValue.coerceIn(0..255)

            try {
                Settings.System.putInt(
                    ctx.contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE,
                    Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL,
                )
                Settings.System.putInt(
                    ctx.contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS,
                    newSystemValue,
                )
                local.dimmer.update(0)
                return newSystemValue
            } catch (e: Exception) {
                Log.e(TAG, "Couldn't update brightness level", e)
                return fetchValue()
            }
        }
    }

    data object BrightnessWithDimmer : ControlFeatureImpl() {
        context(_: Context, _: Local)
        override fun fetchRange() = -255..255

        context(_: Context, _: Local)
        override fun fetchStepRange(sign: Int): IntRange {
            val value = fetchValue()
            return when {
                value in 1..255 -> 0..255
                value in -255..-1 -> -255..0
                sign > 0 -> 0..255
                sign < 0 -> -255..0
                else -> -255..255
            }
        }

        context(ctx: Context, local: Local)
        override fun fetchValue(): Int {
            val currentSystemValue = Settings.System.getInt(
                ctx.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS,
            )
            val currentDimmerValue = local.dimmer.currentValue
            return currentSystemValue - currentDimmerValue
        }

        context(ctx: Context, local: Local)
        override fun updateValue(newValue: Int, showSystemPanel: Boolean): Int {
            val newSystemValue = newValue.coerceIn(0..255)
            val newDimmerValue = -newValue.coerceIn(-255..0)

            try {
                Settings.System.putInt(
                    ctx.contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE,
                    Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL,
                )
                Settings.System.putInt(
                    ctx.contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS,
                    newSystemValue,
                )
                local.dimmer.update(newDimmerValue)
                return newSystemValue - newDimmerValue
            } catch (e: Exception) {
                Log.e(TAG, "Couldn't update brightness (with dimmer) level", e)
                return fetchValue()
            }
        }
    }

    sealed class Audio(private val streamType: Int) : ControlFeatureImpl() {
        data object Alarm : Audio(AudioManager.STREAM_ALARM)
        data object Music : Audio(AudioManager.STREAM_MUSIC)
        data object Ring : Audio(AudioManager.STREAM_RING)
        data object System : Audio(AudioManager.STREAM_SYSTEM)

        context(ctx: Context, _: Local)
        override fun fetchRange(): IntRange {
            val manager = ctx.getSystemService<AudioManager>()!!
            val maximumValue = manager.getStreamMaxVolume(streamType)
            return 0..maximumValue
        }

        context(ctx: Context, _: Local)
        override fun fetchValue(): Int {
            val manager = ctx.getSystemService<AudioManager>()!!
            return manager.getStreamVolume(streamType)
        }

        context(ctx: Context, _: Local)
        override fun updateValue(newValue: Int, showSystemPanel: Boolean): Int {
            val manager = ctx.getSystemService(AudioManager::class.java)
            val maximumValue = manager.getStreamMaxVolume(streamType)
            val newSystemValue = newValue.coerceIn(0..maximumValue)

            // Check if we're on a OnePlus/Oppo/Realme device that needs special handling
            if (DeviceHelper.requiresVolumeWorkaround()) {
                return updateValueForOnePlus(ctx, newSystemValue, showSystemPanel)
            }

            // Standard path for other devices
            return updateValueViaAudioManager(manager, newSystemValue, showSystemPanel)
        }

        private fun updateValueForOnePlus(ctx: Context, newSystemValue: Int, showSystemPanel: Boolean): Int {
            // On OnePlus devices, we can't set volume directly in background.
            // Instead, we just show the native volume panel with a small adjustment.
            // This lets the user see and control volume from the native UI.
            val manager = ctx.getSystemService(AudioManager::class.java)
            val currentVolume = manager.getStreamVolume(streamType)
            
            // Calculate direction of change
            val direction = when {
                newSystemValue > currentVolume -> AudioManager.ADJUST_RAISE
                newSystemValue < currentVolume -> AudioManager.ADJUST_LOWER
                else -> AudioManager.ADJUST_SAME
            }
            
            try {
                // Use adjustStreamVolume which works better on OnePlus
                // FLAG_SHOW_UI will show the native volume panel
                manager.adjustStreamVolume(
                    streamType,
                    direction,
                    AudioManager.FLAG_SHOW_UI
                )
                Log.d(TAG, "Volume adjusted via native UI (direction: $direction)")
                return manager.getStreamVolume(streamType)
            } catch (e: Exception) {
                Log.e(TAG, "adjustStreamVolume failed", e)
            }
            
            // Return current volume since direct setting doesn't work
            return currentVolume
        }

        private fun updateValueViaAudioManager(manager: AudioManager, newSystemValue: Int, showSystemPanel: Boolean): Int {
            try {
                manager.setStreamVolume(
                    streamType,
                    newSystemValue,
                    if (showSystemPanel)
                        AudioManager.FLAG_SHOW_UI
                    else
                        AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE,
                )
                return newSystemValue
            } catch (e: Exception) {
                Log.e(TAG, "Couldn't update stream volume of type: $streamType", e)
                val currentVolume = manager.getStreamVolume(streamType)
                return currentVolume
            }
        }
    }
}

