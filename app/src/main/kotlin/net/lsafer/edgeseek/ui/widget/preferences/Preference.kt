package net.lsafer.edgeseek.ui.widget.preferences

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Preference(
    title: String,
    summary: String = "",
    onClick: () -> Unit = {},
    action: @Composable () -> Unit = {},
    details: @Composable () -> Unit = {}
) {
    Column(Modifier.clickable { onClick() }) {
        Column(Modifier.padding(SpacingPadding)) {
            Row(Modifier.padding(start = AlignmentPadding - SpacingPadding), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.fillMaxWidth().weight(1f)) {
                    Text(
                        text = title,
                        fontSize = 16.sp
                    )
                    Text(
                        text = summary,
                        fontSize = 15.sp,
                        color = MaterialTheme.colors.onBackground.copy(.5f),
                        maxLines = 3
                    )
                }

                Box(Modifier.width(60.dp), contentAlignment = Alignment.CenterEnd) {
                    action()
                }
            }

            details()
        }
    }
}
