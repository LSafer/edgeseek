package net.lsafer.edgeseek.app.scripts

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
        try {
            val p = Runtime.getRuntime().exec("logcat")

            for (line in p.inputStream.bufferedReader().lineSequence()) {
                emit(line)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)
}
