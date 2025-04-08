package net.lsafer.edgeseek.app.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import net.lsafer.edgeseek.app.Local
import net.lsafer.edgeseek.app.PK_UI_COLORS
import net.lsafer.edgeseek.app.UI_COLORS_DEFAULT
import net.lsafer.sundry.storage.select

fun Local.uiColors(): Flow<String> {
    return dataStore.select<String>(PK_UI_COLORS)
        .map { it ?: UI_COLORS_DEFAULT }
}
