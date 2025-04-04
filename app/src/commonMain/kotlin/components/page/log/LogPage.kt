package net.lsafer.edgeseek.app.components.page.log

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ClearAll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import net.lsafer.edgeseek.app.Local
import net.lsafer.edgeseek.app.UniEvent
import net.lsafer.edgeseek.app.UniRoute
import net.lsafer.edgeseek.app.l10n.strings
import net.lsafer.sundry.compose.util.SubscribeEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogPage(
    local: Local,
    route: UniRoute.LogPage,
    modifier: Modifier = Modifier
) {
    val logs = remember { mutableStateListOf<String>() }

    SubscribeEffect(local.fullLog) {
        logs += it
    }

    Scaffold(
        Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .then(modifier),
        snackbarHost = {
            SnackbarHost(local.snackbar)
        },
        topBar = {
            TopAppBar(
                title = { Text(strings.stmt.page_log_heading) },
                actions = {
                    IconButton({
                        local.ioScope.launch {
                            logs.clear()
                            local.eventbus.emit(UniEvent.ClearLog)
                        }
                    }) {
                        Icon(Icons.Default.ClearAll, strings.label.clear_log)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .verticalScroll(rememberScrollState(), reverseScrolling = true)
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            for ((i, log) in logs.withIndex()) {
                Text(
                    text = log.trim(),
                    fontSize = 12.sp,
                    modifier = Modifier.background(
                        color = if (i % 2 == 0) Color.Gray else Color.DarkGray,
                    ),
                    fontFamily = FontFamily.Monospace,
                )
            }
        }
    }
}
