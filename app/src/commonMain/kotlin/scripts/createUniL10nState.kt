package net.lsafer.edgeseek.app.scripts

import androidx.compose.ui.unit.LayoutDirection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import net.lsafer.edgeseek.app.UI_LANG_DEFAULT
import net.lsafer.edgeseek.app.l10n.Strings_all
import net.lsafer.edgeseek.app.l10n.UniL10n
import net.lsafer.edgeseek.app.util.firstShareStateIn
import net.lsafer.edgeseek.app.util.langIsRTL
import net.lsafer.edgeseek.app.util.langSelect

suspend fun createUniL10nState(
    language: Flow<String?>,
    defaultLanguage: String,
    coroutineScope: CoroutineScope
): StateFlow<UniL10n> {
    return language
        .map { it ?: defaultLanguage }
        .distinctUntilChanged()
        .map {
            UniL10n(
                lang = it,
                dir = when {
                    langIsRTL(it) -> LayoutDirection.Rtl
                    else -> LayoutDirection.Ltr
                },
                strings = Strings_all[langSelect(
                    languages = Strings_all.keys,
                    ranges = listOf(it),
                    default = UI_LANG_DEFAULT,
                )]!!,
            )
        }
        .firstShareStateIn(coroutineScope)
}
