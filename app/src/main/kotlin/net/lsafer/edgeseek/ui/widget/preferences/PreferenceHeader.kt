package net.lsafer.edgeseek.ui.widget.preferences

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PreferenceHeader(
    title: String,
    summary: String = ""
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(50.dp)
    ) {
        Text(
            text = title,
            color = MaterialTheme.colors.secondary,
            fontSize = 30.sp
        )
        Text(
            text = summary,
            fontSize = 15.sp,
            color = MaterialTheme.colors.onBackground.copy(.5f)
        )
    }
}

@Preview
@Composable
fun PreferenceHeaderPreview() {
    Surface(Modifier.fillMaxSize()) {
        PreferenceHeader(
            title = "Title",
            summary = "Summary"
        )
    }
}
