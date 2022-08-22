package net.lsafer.edgeseek.ui.widget.preferences

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun PreferenceSection(
    title: String
) {
    Text(
        text = title,
        color = MaterialTheme.colors.secondary,
        fontSize = 14.sp,
        modifier = Modifier.padding(start = AlignmentPadding)
    )
}

@Preview
@Composable
fun PreferenceSectionPreview() {
    Surface(Modifier.fillMaxSize()) {
        PreferenceSection("Section")
    }
}
