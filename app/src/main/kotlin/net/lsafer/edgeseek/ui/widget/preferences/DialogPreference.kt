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

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.lsafer.edgeseek.R

@Composable
fun DialogPreference(
    title: String,
    summary: String = "",
    action: @Composable () -> Unit = {},
    onConfirm: () -> Unit,
    block: @Composable () -> Unit
) {
    var dialogState by remember { mutableStateOf(false) }

    if (dialogState) {
        AlertDialog(
            onDismissRequest = { dialogState = false },
            shape = RoundedCornerShape(20.dp),
            title = {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            },
            dismissButton = {
                TextButton(onClick = { dialogState = false }) {
                    Text(text = stringResource(R.string.cancel))
                }
            },
            confirmButton = {
                TextButton(onClick = { dialogState = false; onConfirm() }) {
                    Text(text = stringResource(R.string.confirm))
                }
            },
            text = {
                block()
            }
        )
    }

    return Preference(
        title = title,
        summary = summary,
        onClick = { dialogState = true },
        action = action
    )
}
