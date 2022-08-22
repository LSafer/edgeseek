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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.godaddy.android.colorpicker.ClassicColorPicker

@Composable
fun ColorPreference(
    value: Color,
    onValueChange: (Color) -> Unit,
    title: String,
    summary: String
) {
    var localValue by remember(value) { mutableStateOf(value) }

    DialogPreference(
        title = title,
        summary = summary,
        onConfirm = { onValueChange(localValue) },
        action = {
            Box(
                Modifier
                    .size(30.dp)
                    .padding(2.dp)
                    .background(value, RoundedCornerShape(40))
            )
        },
        block = {
            ClassicColorPicker(
                modifier = Modifier.fillMaxWidth().aspectRatio(.9f),
                color = localValue,
                onColorChanged = { localValue = it.toColor() }
            )
        }
    )
}
