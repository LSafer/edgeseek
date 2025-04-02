package net.lsafer.edgeseek.app.components.wrapper

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLayoutDirection
import net.lsafer.edgeseek.app.Local
import net.lsafer.edgeseek.app.l10n.LocalStrings

@Composable
fun AndroidUniLocaleProvider(
    local: Local,
    content: @Composable () -> Unit,
) {
    val l10n by local.l10nState.collectAsState()

    LaunchedEffect(l10n.lang) {
        val juLocale = java.util.Locale.forLanguageTag(l10n.lang)
        java.util.Locale.setDefault(juLocale)
    }

    CompositionLocalProvider(
        LocalLayoutDirection provides l10n.dir,
        LocalStrings provides l10n.strings,
    ) {
        key(l10n.lang) {
            content()
        }
    }
}
