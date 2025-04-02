package net.lsafer.edgeseek.app.l10n

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.LayoutDirection
import net.lsafer.edgeseek.app.l10n.strings.Strings_en

val LocalStrings = compositionLocalOf<Strings> {
    error("CompositionLocal LocalStrings not present")
}

val strings @Composable get() = LocalStrings.current

val Strings_all = mapOf(
    "en" to Strings_en,
)

data class UniL10n(
    val lang: String,
    val dir: LayoutDirection,
    val strings: Strings,
)
