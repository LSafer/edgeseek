package net.lsafer.edgeseek.ui.widget.preferences

import androidx.compose.material.Switch
import androidx.compose.runtime.Composable

@Composable
fun SwitchPreference(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    title: String,
    summary: String = ""
) {
    Preference(
        title = title,
        summary = summary,
        onClick = { onCheckedChange?.invoke(!checked) },
        action = {
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        }
    )
}
