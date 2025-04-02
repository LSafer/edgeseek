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
        headline = "Activation",
        supporting = "Toggle to activate or deactivate the application",
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
        headline = "Change Theme",
        items = mapOf(
            UI_COLORS_SYSTEM to "System",
            UI_COLORS_BLACK to "Black",
            UI_COLORS_DARK to "Dark",
            UI_COLORS_LIGHT to "Light",
            UI_COLORS_WHITE to "White",
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
        headline = "Auto Boot",
        supporting = "Auto boot the service every time the device booted-up",
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
        headline = "Brightness Reset",
        supporting = "Turn on auto brightness each time the device turn on to sleep",
        modifier = modifier,
    )
}
