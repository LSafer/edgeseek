package net.lsafer.edgeseek.app.components.wrapper

import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.BackHandler
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import net.lsafer.edgeseek.app.*
import net.lsafer.edgeseek.app.l10n.LocalStrings
import net.lsafer.sundry.compose.simplenav.InMemorySimpleNavController
import net.lsafer.sundry.compose.util.SubscribeEffect
import net.lsafer.sundry.storage.select

@Composable
fun AndroidUniWindowCompat(
    local: Local,
    activity: ComponentActivity,
    content: @Composable () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    val coroutineScope = rememberCoroutineScope()
    val strings = LocalStrings.current
    val isSystemDarkTheme = isSystemInDarkTheme()
    val uiColors by produceState(UI_COLORS_DEFAULT) {
        local.dataStore
            .select<String>(PK_UI_COLORS)
            .filterNotNull()
            .collect { value = it }
    }

    fun onLeaveRequest() = coroutineScope.launch {
        val result = local.snackbar.showSnackbar(
            message = strings.stmt.exit_application_qm,
            actionLabel = strings.label.yes,
            withDismissAction = true,
            duration = SnackbarDuration.Short,
        )

        if (result == SnackbarResult.ActionPerformed)
            activity.finish()
    }

    BackHandler {
        val nc = local.navController as InMemorySimpleNavController

        if (nc.state.value.position == 0)
            onLeaveRequest()
        else
            local.navController.back()
    }

    SubscribeEffect(local.eventbus) { event ->
        when (event) {
            is UniEvent.OpenUrlRequest -> {
                val uri = Uri.parse(event.url)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                activity.startActivity(intent)
            }

            is UniEvent.FocusRequest -> {
                val intent = Intent(activity, activity.javaClass)
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                activity.startActivity(intent)
            }

            else -> {}
        }
    }

    LaunchedEffect(uiColors, isSystemDarkTheme) {
        val isDark = when (uiColors) {
            UI_COLORS_BLACK, UI_COLORS_DARK -> true
            UI_COLORS_LIGHT, UI_COLORS_WHITE -> false
            else -> isSystemDarkTheme
        }

        activity.enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT,
            ) { isDark },
            navigationBarStyle = SystemBarStyle.auto(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT,
            ) { isDark },
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures {
                    focusManager.clearFocus()
                }
            }
    ) {
        content()
    }
}
