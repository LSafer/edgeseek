package net.lsafer.edgeseek.app

import android.app.Application
import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import net.lsafer.edgeseek.app.scripts.createUniL10nState
import net.lsafer.edgeseek.app.scripts.init_log_facade
import net.lsafer.edgeseek.app.scripts.register_shutdown_hook
import net.lsafer.sundry.compose.simplenav.InMemorySimpleNavController
import net.lsafer.sundry.compose.util.platformIODispatcher
import net.lsafer.sundry.storage.createFileJsonObjectDataStore
import net.lsafer.sundry.storage.select
import okio.Path.Companion.toOkioPath
import java.util.*
import kotlin.random.Random

suspend fun createAndroidLocal(application: Application): Local {
    val local = Local()
    local.dataDir = application.filesDir.toOkioPath()
    local.cacheDir = application.cacheDir.toOkioPath()

    local.clock = Clock.System
    local.timeZone = TimeZone.currentSystemDefault()
    local.random = Random.Default
    local.ioScope = CoroutineScope(
        platformIODispatcher + SupervisorJob() +
                CoroutineExceptionHandler { _, e ->
                    moduleLogger.e("Unhandled coroutine exception", e)
                }
    )

    local.eventbus = MutableSharedFlow()
    local.dataStore = createFileJsonObjectDataStore(
        file = local.dataDir.resolve("datastore.json").toFile(),
        coroutineScope = local.ioScope,
    )
    local.l10nState = createUniL10nState(
        language = local.dataStore.select<String>(PK_UI_LANG),
        defaultLanguage = Locale.getDefault().language,
        coroutineScope = local.ioScope,
    )
    local.navController = InMemorySimpleNavController(
        InMemorySimpleNavController.State(
            entries = when {
                local.dataStore.select<Boolean>(PK_WIZ_INTRO).first() != true ->
                    listOf(UniRoute.IntroductionWizard())

                else ->
                    listOf(UniRoute.HomePage)
            },
        ),
    )
    local.snackbar = SnackbarHostState()
    local.init_log_facade()
    local.register_shutdown_hook()

    return local
}
