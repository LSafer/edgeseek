package net.lsafer.edgeseek.app.components.lib

import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ListHeader(
    modifier: Modifier = Modifier,
    title: String,
    summary: String = "",
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(50.dp)
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 30.sp
        )
        Text(
            text = summary,
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(.5f)
        )
    }
}

@Composable
fun ListSectionTitle(
    modifier: Modifier = Modifier,
    title: String,
) {
    Text(
        text = title,
        color = MaterialTheme.colorScheme.secondary,
        fontSize = 14.sp,
        modifier = Modifier
            .padding(start = 65.dp)
            .then(modifier)
    )
}

@Composable
fun ListDivider() {
    HorizontalDivider()
    Spacer(Modifier.height(15.dp))
}
