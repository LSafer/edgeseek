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
