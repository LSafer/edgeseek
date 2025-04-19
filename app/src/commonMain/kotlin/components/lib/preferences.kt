package net.lsafer.edgeseek.app.components.lib

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.godaddy.android.colorpicker.ClassicColorPicker
import com.godaddy.android.colorpicker.toColorInt
import kotlin.math.roundToInt

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun ColorPreferenceListItem(
    value: Int,
    onChange: (Int) -> Unit,
    headline: String,
    supporting: String? = null,
    modifier: Modifier = Modifier,
) {
    var localValueString by remember(value) { mutableStateOf(value.toHexString()) }
    val localValueInt by derivedStateOf { runCatching { localValueString.hexToInt() }.getOrNull() }

    var isMenuOpen by remember { mutableStateOf(false) }

    if (isMenuOpen) {
        Dialog(
            onDismissRequest = {
                onChange(localValueInt ?: value)
                isMenuOpen = false
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = true,
            )
        ) {
            OutlinedCard {
                Column(Modifier.padding(8.dp)) {
                    ClassicColorPicker(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(.9f),
                        color = Color(localValueInt ?: value),
                        onColorChanged = {
                            localValueString = it.toColorInt().toHexString()
                        }
                    )

                    Spacer(Modifier.height(8.dp))

                    Row(Modifier.height(IntrinsicSize.Max)) {
                        IconButton({
                            localValueString = Color(localValueInt ?: value)
                                .copy(alpha = 0.01f)
                                .toArgb()
                                .toHexString()
                        }) {
                            Icon(Icons.Default.VisibilityOff, "Fix transparency")
                        }

                        OutlinedCard(
                            Modifier.fillMaxHeight()
                        ) {
                            Row(
                                Modifier
                                    .padding(8.dp)
                                    .fillMaxHeight(),
                                Arrangement.spacedBy(8.dp),
                                Alignment.CenterVertically,
                            ) {
                                Text("#")
                                BasicTextField(
                                    value = localValueString,
                                    onValueChange = { localValueString = it },
                                    modifier = Modifier.fillMaxWidth(),
                                    textStyle = LocalTextStyle.current.copy(
                                        color = MaterialTheme.colorScheme.onSurface,
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    ListItem(
        modifier = Modifier
            .clickable { isMenuOpen = true }
            .then(modifier),
        headlineContent = { Text(headline) },
        supportingContent = supporting?.let { { Text(it) } },
        trailingContent = {
            Box(
                Modifier
                    .size(30.dp)
                    .padding(2.dp)
                    .background(
                        color = Color(value).copy(alpha = 1f),
                        shape = RoundedCornerShape(40),
                    )
            )
        }
    )
}

@Composable
fun SwitchPreferenceListItem(
    value: Boolean,
    onChange: (Boolean) -> Unit,
    headline: String,
    supporting: String? = null,
    modifier: Modifier = Modifier,
) {
    ListItem(
        modifier = Modifier
            .clickable { onChange(!value) }
            .then(modifier),
        headlineContent = { Text(headline) },
        supportingContent = supporting?.let { { Text(it) } },
        trailingContent = { Switch(value, onChange) },
    )
}

@Composable
fun SliderPreferenceListItem(
    value: Int,
    onChange: (Int) -> Unit,
    valueRange: IntRange,
    headline: String,
    supporting: String? = null,
    modifier: Modifier = Modifier,
) {
    var localValue by remember(value) {
        mutableStateOf(value.toFloat())
    }

    ListItem(
        modifier = modifier,
        headlineContent = { Text(headline) },
        supportingContent = {
            Column {
                if (supporting != null)
                    Text(supporting)

                Slider(
                    value = localValue,
                    onValueChange = { localValue = it },
                    onValueChangeFinished = { onChange(localValue.roundToInt()) },
                    valueRange = valueRange.first.toFloat()..valueRange.last.toFloat(),
                    colors = SliderDefaults.colors(
                        thumbColor = MaterialTheme.colorScheme.secondary,
                        activeTrackColor = MaterialTheme.colorScheme.secondary
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        trailingContent = {
            Text(
                text = "${localValue.toInt()}",
                textAlign = TextAlign.End,
                modifier = Modifier.width(50.dp)
            )
        },
    )
}

@Composable
fun <T> SingleSelectPreferenceListItem(
    value: T,
    onChange: (T) -> Unit,
    items: Map<T, String>,
    headline: String,
    supporting: String? = items[value],
    modifier: Modifier = Modifier,
) {
    var isMenuOpen by remember { mutableStateOf(false) }

    if (isMenuOpen) {
        Dialog(
            onDismissRequest = { isMenuOpen = false },
            properties = DialogProperties(
                usePlatformDefaultWidth = true,
            )
        ) {
            OutlinedCard {
                for ((itemValue, itemTitle) in items) {
                    ListItem(
                        modifier = Modifier
                            .clickable {
                                onChange(itemValue)
                                isMenuOpen = false
                            },
                        headlineContent = { Text(itemTitle) },
                        colors = if (itemValue == value)
                            ListItemDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.surfaceTint,
                            )
                        else
                            ListItemDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            )
                    )
                }
            }
        }
    }

    ListItem(
        modifier = Modifier
            .clickable { isMenuOpen = true }
            .then(modifier),
        headlineContent = { Text(headline) },
        supportingContent = supporting?.let { { Text(it) } },
    )
}
