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

import android.media.AudioManager
import android.provider.Settings
import androidx.core.content.getSystemService
import co.touchlab.kermit.Logger

sealed class SeekFeatureImpl {
    companion object {
        private val logger = Logger.withTag(SeekFeatureImpl::class.qualifiedName!!)
    }

    abstract fun fetchRange(implLocal: ImplLocal): IntRange
    open fun fetchStepRange(implLocal: ImplLocal, sign: Int) = fetchRange(implLocal)
    abstract fun fetchValue(implLocal: ImplLocal): Int
    abstract fun updateValue(implLocal: ImplLocal, newValue: Int): Int

    data object ControlBrightness : SeekFeatureImpl() {
        override fun fetchRange(implLocal: ImplLocal) = 0..255

        override fun fetchValue(implLocal: ImplLocal): Int {
            return Settings.System.getInt(
                implLocal.context.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS,
            )
        }

        override fun updateValue(implLocal: ImplLocal, newValue: Int): Int {
            val newSystemValue = newValue.coerceIn(0..255)

            try {
                Settings.System.putInt(
                    implLocal.context.contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE,
                    Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL,
                )
                Settings.System.putInt(
                    implLocal.context.contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS,
                    newSystemValue,
                )
                implLocal.dimmer.update(0)
                return newSystemValue
            } catch (e: Exception) {
                logger.e("Couldn't update brightness level", e)
                return fetchValue(implLocal)
            }
        }
    }

    data object ControlBrightnessWithDimmer : SeekFeatureImpl() {
        override fun fetchRange(implLocal: ImplLocal) = -255..255

        override fun fetchStepRange(implLocal: ImplLocal, sign: Int): IntRange {
            val value = fetchValue(implLocal)
            return when {
                value in 1..255 -> 0..255
                value in -255..-1 -> -255..0
                sign > 0 -> 0..255
                sign < 0 -> -255..0
                else -> -255..255
            }
        }

        override fun fetchValue(implLocal: ImplLocal): Int {
            val currentSystemValue = Settings.System.getInt(
                implLocal.context.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS,
            )
            val currentDimmerValue = implLocal.dimmer.currentValue
            return currentSystemValue - currentDimmerValue
        }

        override fun updateValue(implLocal: ImplLocal, newValue: Int): Int {
            val newSystemValue = newValue.coerceIn(0..255)
            val newDimmerValue = -newValue.coerceIn(-255..0)

            try {
                Settings.System.putInt(
                    implLocal.context.contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE,
                    Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL,
                )
                Settings.System.putInt(
                    implLocal.context.contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS,
                    newSystemValue,
                )
                implLocal.dimmer.update(newDimmerValue)
                return newSystemValue - newDimmerValue
            } catch (e: Exception) {
                logger.e("Couldn't update brightness (with dimmer) level", e)
                return fetchValue(implLocal)
            }
        }
    }

    sealed class ControlAudio(private val streamType: Int) : SeekFeatureImpl() {
        data object Music : ControlAudio(AudioManager.STREAM_MUSIC)
        data object System : ControlAudio(AudioManager.STREAM_SYSTEM)
        data object Alarm : ControlAudio(AudioManager.STREAM_ALARM)
        data object Ring : ControlAudio(AudioManager.STREAM_RING)

        override fun fetchRange(implLocal: ImplLocal): IntRange {
            val manager = implLocal.context.getSystemService<AudioManager>()!!
            val maximumValue = manager.getStreamMaxVolume(streamType)
            return 0..maximumValue
        }

        override fun fetchValue(implLocal: ImplLocal): Int {
            val manager = implLocal.context.getSystemService<AudioManager>()!!
            return manager.getStreamVolume(streamType)
        }

        override fun updateValue(implLocal: ImplLocal, newValue: Int): Int {
            val manager = implLocal.context.getSystemService(AudioManager::class.java)
            val maximumValue = manager.getStreamMaxVolume(streamType)
            val newSystemValue = newValue.coerceIn(0..maximumValue)

            try {
                manager.setStreamVolume(
                    streamType,
                    newSystemValue,
                    AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE,
                )
                return newSystemValue
            } catch (e: Exception) {
                logger.e("Couldn't update stream volume of type: $streamType", e)
                return fetchValue(implLocal)
            }
        }
    }
}
