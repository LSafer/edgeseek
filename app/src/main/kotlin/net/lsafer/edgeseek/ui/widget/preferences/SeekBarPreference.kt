package net.lsafer.edgeseek.ui.widget.preferences

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun SliderPreference(
    value: Float,
    onValueChange: (Float) -> Unit,
    range: ClosedFloatingPointRange<Float> = 0f..1f,
    showCurrentValue: Boolean = true,
    steps: Int = 0,
    title: String,
    summary: String = "",
    format: (Float) -> String = { "$it" }
) {
    var localValue by remember(value) { mutableStateOf(value) }

    Preference(
        title = title,
        summary = summary,
        details = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(Modifier.width(AlignmentPadding - SpacingPadding - 5.dp))

                Slider(
                    value = localValue,
                    onValueChange = { localValue = it },
                    onValueChangeFinished = { onValueChange(localValue) },
                    valueRange = range,
                    steps = steps,
                    colors = SliderDefaults.colors(
                        thumbColor = MaterialTheme.colors.secondary,
                        activeTrackColor = MaterialTheme.colors.secondary
                    ),
                    modifier = Modifier.weight(1f)
                )

                if (showCurrentValue) {
                    Spacer(Modifier.width(5.dp))

                    Text(
                        text = format(localValue),
                        textAlign = TextAlign.End,
                        modifier = Modifier.width(50.dp)
                    )
                }
            }
        }
    )
}
