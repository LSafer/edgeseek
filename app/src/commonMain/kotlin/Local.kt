package net.lsafer.edgeseek.app

import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.serialization.json.JsonObject
import net.lsafer.edgeseek.app.l10n.UniL10n
import net.lsafer.sundry.compose.simplenav.SimpleNavController
import net.lsafer.sundry.storage.SimpleDataStore
import kotlin.random.Random

class Local {
    //    lateinit var etc: EtcOptions
    //    lateinit var misc: MiscOptions
    lateinit var dataDir: okio.Path
    lateinit var cacheDir: okio.Path

    // etc

    lateinit var clock: Clock
    lateinit var random: Random
    lateinit var timeZone: TimeZone
    lateinit var ioScope: CoroutineScope

    lateinit var eventbus: MutableSharedFlow<UniEvent>
    lateinit var l10nState: StateFlow<UniL10n>
    lateinit var dataStore: SimpleDataStore<JsonObject>
    lateinit var navController: SimpleNavController<UniRoute>
    lateinit var snackbar: SnackbarHostState

    lateinit var fullLog: Flow<String>
}
