package net.lsafer.edgeseek.app.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

fun <T, R> StateFlow<T>.mapShareStateIn(
    scope: CoroutineScope,
    block: (T) -> R,
): StateFlow<R> {
    val started: SharingStarted = SharingStarted.Eagerly
    return drop(1).map { block(it) }
        .shareIn(scope, started, replay = 1)
        .stateIn(scope, started, block(value))
}

suspend fun <T> Flow<T>.firstShareStateIn(scope: CoroutineScope): StateFlow<T> {
    val started: SharingStarted = SharingStarted.Eagerly
    return shareIn(scope, started, replay = 1).let {
        it.stateIn(scope, started, it.first())
    }
}
