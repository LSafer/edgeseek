package net.lsafer.edgeseek.app.components.page.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import net.lsafer.edgeseek.app.*
import net.lsafer.edgeseek.app.components.lib.SingleSelectPreferenceListItem
import net.lsafer.edgeseek.app.components.lib.SwitchPreferenceListItem
import net.lsafer.edgeseek.app.l10n.strings
import net.lsafer.sundry.storage.edit
import net.lsafer.sundry.storage.select
import org.cufy.json.set

@Composable
fun HomePage_ListItem_activation(
    local: Local,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    val value by produceState(false) {
        local.dataStore
            .select<Boolean>(PK_FLAG_ACTIVATED)
            .filterNotNull()
            .collect { value = it }
    }

    val handleOnChange = { newValue: Boolean ->
        local.dataStore.edit { it[PK_FLAG_ACTIVATED] = newValue }

        if (newValue) coroutineScope.launch {
            local.eventbus.emit(UniEvent.StartService)
        }
    }

    SwitchPreferenceListItem(
        value = value,
        onChange = handleOnChange,
        headline = strings.stmt.app_activation_headline,
        supporting = strings.stmt.app_activation_supporting,
        modifier = modifier,
    )
}

@Composable
fun HomePage_ListItem_ui_colors(
    local: Local,
    modifier: Modifier = Modifier,
) {
    val value by produceState(UI_COLORS_DEFAULT) {
        local.dataStore
            .select<String>(PK_UI_COLORS)
            .filterNotNull()
            .collect { value = it }
    }

    val handleOnChange = { newValue: String ->
        local.dataStore.edit { it[PK_UI_COLORS] = newValue }
    }

    SingleSelectPreferenceListItem(
        value = value,
        onChange = handleOnChange,
        headline = strings.stmt.app_colors_headline,
        items = mapOf(
            UI_COLORS_SYSTEM to strings.stmt.app_colors_value_system,
            UI_COLORS_BLACK to strings.stmt.app_colors_value_black,
            UI_COLORS_DARK to strings.stmt.app_colors_value_dark,
            UI_COLORS_LIGHT to strings.stmt.app_colors_value_light,
            UI_COLORS_WHITE to strings.stmt.app_colors_value_white,
        ),
        modifier = modifier,
    )
}

@Composable
fun HomePage_ListItem_auto_boot(
    local: Local,
    modifier: Modifier = Modifier,
) {
    val value by produceState(false) {
        local.dataStore
            .select<Boolean>(PK_FLAG_AUTO_BOOT)
            .filterNotNull()
            .collect { value = it }
    }

    val handleOnChange = { newValue: Boolean ->
        local.dataStore.edit { it[PK_FLAG_AUTO_BOOT] = newValue }
    }

    SwitchPreferenceListItem(
        value = value,
        onChange = handleOnChange,
        headline = strings.stmt.app_auto_boot_headline,
        supporting = strings.stmt.app_auto_boot_supporting,
        modifier = modifier,
    )
}

@Composable
fun HomePage_ListItem_brightness_reset(
    local: Local,
    modifier: Modifier = Modifier,
) {
    val value by produceState(false) {
        local.dataStore
            .select<Boolean>(PK_FLAG_BRIGHTNESS_RESET)
            .filterNotNull()
            .collect { value = it }
    }

    val handleOnChange = { newValue: Boolean ->
        local.dataStore.edit { it[PK_FLAG_BRIGHTNESS_RESET] = newValue }
    }

    SwitchPreferenceListItem(
        value = value,
        onChange = handleOnChange,
        headline = strings.stmt.app_brightness_reset_headline,
        supporting = strings.stmt.app_brightness_reset_supporting,
        modifier = modifier,
    )
}
