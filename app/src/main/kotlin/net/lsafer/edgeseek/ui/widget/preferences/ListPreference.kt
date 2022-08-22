package net.lsafer.edgeseek.ui.widget.preferences

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun <T> ListPreference(
    value: T,
    values: List<Pair<T, String>>,
    onValueChange: (T) -> Unit,
    title: String,
    summary: (String) -> String = { it }
) {
    var localValue by remember(value) { mutableStateOf(value) }

    DialogPreference(
        title = title,
        summary = summary(values.firstOrNull { it.first == value }?.second ?: "$value"),
        onConfirm = { onValueChange(localValue) },
        block = {
            Column {
                values.forEach { (value, title) ->
                    Box(Modifier.clickable { localValue = value }) {
                        Row(Modifier.padding(vertical = 2.5.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = localValue == value,
                                onClick = { localValue = value }
                            )

                            Text(
                                text = title,
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
        }
    )
}
