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
