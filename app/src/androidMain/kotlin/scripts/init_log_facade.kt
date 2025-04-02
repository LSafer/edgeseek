package net.lsafer.edgeseek.app.scripts

import co.touchlab.kermit.Logger
import co.touchlab.kermit.platformLogWriter
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import net.lsafer.edgeseek.app.Local
import net.lsafer.edgeseek.app.UniEvent
import net.lsafer.edgeseek.app.util.SimpleLogFormatter
import net.lsafer.edgeseek.app.util.SimpleLogWriter

@Suppress("FunctionName")
fun Local.init_log_facade() {
    val logbus = MutableSharedFlow<String>()
    val formatter = SimpleLogFormatter(timeZone, clock)
    val logfile = cacheDir.resolve("log.txt").toFile()

    // Initialize kermit writers with format
    Logger.setLogWriters(platformLogWriter(formatter))

    // Initialize agent writer to emit to logbus
    Logger.addLogWriter(SimpleLogWriter(formatter) { message, e ->
        val stmt = buildString {
            append(message)
            if (e != null)
                append(e.stackTraceToString())
        }

        ioScope.launch { logbus.emit(stmt) }
    })

    // Append logfile on each log statement; use `\n---\n` as statement separator
    logbus
        .onEach { logfile.appendText("$it\n---\n") }
        .launchIn(ioScope)

    // Overwrite logfile with "" on each 'clear log' request
    eventbus
        .filterIsInstance<UniEvent.ClearLog>()
        .onEach { logfile.writeText("") }
        .launchIn(ioScope)

    // Cold flow for everyone wanting to read the full log.
    fullLog = flow {
        if (logfile.exists()) {
            val buffer = StringBuilder()

            for (line in logfile.bufferedReader().lineSequence()) {
                if (line == "---") {
                    emit(buffer.toString())
                    buffer.clear()
                } else {
                    buffer.appendLine(line)
                }
            }

            if (buffer.isNotEmpty()) {
                emit(buffer.toString())
            }
        }

        // Then, follow latest
        emitAll(logbus)
    }
}
