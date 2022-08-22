package net.lsafer.edgeseek.component

import android.content.Context
import android.media.AudioManager
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import kotlin.math.roundToInt

sealed class SeekObserver {
    abstract fun onSeek(context: Context, delta: Float): Int
}

object ControlBrightness : SeekObserver() {
    override fun onSeek(context: Context, delta: Float): Int {
        val resolver = context.contentResolver
        val currentValue = Settings.System.getInt(resolver, Settings.System.SCREEN_BRIGHTNESS)
        val value = (delta / 100 + currentValue)
            .roundToInt()
            .coerceIn(0, 255)

        return try {
            Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL)
            Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS, value.toInt())
            value
        } catch (e: Exception) {
            Log.e("ControlBrightness", e.message, e)
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            currentValue
        }
    }
}

sealed class ControlAudio(val type: Int) : SeekObserver() {
    object Music : ControlAudio(AudioManager.STREAM_MUSIC)
    object System : ControlAudio(AudioManager.STREAM_SYSTEM)
    object Alarm : ControlAudio(AudioManager.STREAM_ALARM)
    object Ring : ControlAudio(AudioManager.STREAM_RING)

    override fun onSeek(context: Context, delta: Float): Int {
        val manager = context.getSystemService(AudioManager::class.java)
        val currentValue = manager.getStreamVolume(type)
        val value = (delta / 1000 + currentValue)
            .roundToInt()
            .coerceIn(0, manager.getStreamMaxVolume(type))

        return try {
            manager.setStreamVolume(type, value, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE)
            value
        } catch (e: Exception) {
            Log.e("Control Audio $type", e.message, e)
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            currentValue
        }
    }
}
