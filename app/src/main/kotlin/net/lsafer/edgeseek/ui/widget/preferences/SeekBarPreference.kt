/*
 *	Copyright 2020-2022 LSafer
 *
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *	    http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 */
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
