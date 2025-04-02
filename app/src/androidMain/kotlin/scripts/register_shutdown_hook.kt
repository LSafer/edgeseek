package net.lsafer.edgeseek.app.scripts

import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking
import net.lsafer.edgeseek.app.Local

@Suppress("FunctionName")
fun Local.register_shutdown_hook() {
    Runtime.getRuntime().addShutdownHook(Thread {
        runBlocking {
            ioScope.cancel()
        }
    })
}
