package net.lsafer.edgeseek.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import net.lsafer.edgeseek.model.AppTheme
import net.lsafer.edgeseek.model.rememberApplicationData

@Composable
fun EdgeseekTheme(content: @Composable () -> Unit) {
    val data by rememberApplicationData()

    MaterialTheme(
        colors = when (data.theme) {
            AppTheme.System -> when {
                isSystemInDarkTheme() -> DarkColorPalette
                else -> LightColorPalette
            }
            AppTheme.Black -> BlackColorPalette
            AppTheme.Dark -> DarkColorPalette
            AppTheme.Light -> LightColorPalette
            AppTheme.White -> WhiteColorPalette
        },
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
