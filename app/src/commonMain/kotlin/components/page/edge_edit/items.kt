package net.lsafer.edgeseek.app.components.page.edge_edit

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.lsafer.edgeseek.app.components.lib.SingleSelectPreferenceListItem
import net.lsafer.edgeseek.app.data.settings.ActionFeature
import net.lsafer.edgeseek.app.data.settings.ControlFeature
import net.lsafer.edgeseek.app.l10n.strings

@Composable
fun ControlFeaturePreferenceListItem(
    value: ControlFeature,
    onChange: (ControlFeature) -> Unit,
    headline: String,
    modifier: Modifier = Modifier,
) {
    SingleSelectPreferenceListItem(
        value = value,
        onChange = onChange,
        headline = headline,
        items = mapOf(
            ControlFeature.Nothing to strings.stmt.control_feature_nothing,
            ControlFeature.Brightness to strings.stmt.control_feature_brightness,
            ControlFeature.BrightnessWithDimmer to strings.stmt.control_feature_brightness_dimmer,
            ControlFeature.Alarm to strings.stmt.control_feature_alarm,
            ControlFeature.Music to strings.stmt.control_feature_music,
            ControlFeature.Ring to strings.stmt.control_feature_ring,
            ControlFeature.System to strings.stmt.control_feature_system,
        ),
        modifier = modifier,
    )
}

@Composable
fun ActionFeaturePreferenceListItem(
    value: ActionFeature,
    onChange: (ActionFeature) -> Unit,
    headline: String,
    modifier: Modifier = Modifier,
) {
    SingleSelectPreferenceListItem(
        value = value,
        onChange = onChange,
        headline = headline,
        items = mapOf(
            ActionFeature.Nothing to strings.stmt.action_feature_nothing,
            ActionFeature.ExpandStatusBar to strings.stmt.action_feature_expand_status_bar,
        ),
        modifier = modifier,
    )
}
