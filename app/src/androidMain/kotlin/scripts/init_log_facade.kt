package net.lsafer.edgeseek.app.scripts

import android.os.Build
import co.touchlab.kermit.LogcatWriter
import co.touchlab.kermit.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import net.lsafer.edgeseek.app.Local
import net.lsafer.edgeseek.app.util.SimpleLogFormatter

@Suppress("FunctionName")
fun Local.init_log_facade() {
    val formatter = SimpleLogFormatter(timeZone, clock)

    // Initialize kermit writers with format
    Logger.setLogWriters(LogcatWriter(formatter))

    // Cold flow for everyone wanting to read the full log.
    fullLog = flow {
        val p = Runtime.getRuntime().exec("logcat")

        try {
            for (line in p.inputStream.bufferedReader().lineSequence()) {
                emit(line)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                p.destroyForcibly()
            } else {
                p.destroy()
            }
        }
    }.flowOn(Dispatchers.IO)
}
