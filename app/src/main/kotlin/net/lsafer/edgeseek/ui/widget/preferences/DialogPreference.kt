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
