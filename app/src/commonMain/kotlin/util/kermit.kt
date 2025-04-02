package net.lsafer.edgeseek.app.util

import co.touchlab.kermit.*
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class SimpleLogWriter(
    private val formatter: MessageStringFormatter,
    private val onLog: (message: String, throwable: Throwable?) -> Unit,
) : LogWriter() {
    override fun log(severity: Severity, message: String, tag: String, throwable: Throwable?) {
        onLog(formatter.formatMessage(severity, Tag(tag), Message(message)), throwable)
    }
}

class SimpleLogFormatter(
    private val timeZone: TimeZone,
    private val clock: Clock,
) : MessageStringFormatter {
    override fun formatMessage(severity: Severity?, tag: Tag?, message: Message) = buildString {
        val datetime = clock.now().toLocalDateTime(timeZone)

        append(datetime.year)
        append('-')
        append(datetime.monthNumber)
        append('-')
        append(datetime.dayOfMonth)
        append(' ')
        append(datetime.hour)
        append(':')
        append(datetime.minute)
        append(':')
        append(datetime.second)
        append(' ')
        if (tag != null) append("${tag.tag}: ")
        if (severity != null) append("[${severity.name.uppercase()}] - ")
        append(message.message)
    }
}
