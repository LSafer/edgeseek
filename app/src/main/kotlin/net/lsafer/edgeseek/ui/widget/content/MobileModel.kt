package net.lsafer.edgeseek.ui.widget.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MobileModel(modifier: Modifier) {
    val startColor = when (MaterialTheme.colors.background) {
        Color.Black -> Color(0xFF785858)
        Color.White -> Color(0xFF8989F4)
        else -> Color(0xFF81A0A1)
    }
    val centerColor = when (MaterialTheme.colors.background) {
        Color.Black -> Color(0xFF816060)
        Color.White -> Color(0xFF9E7CEF)
        else -> Color(0xFF81A0A1)
    }
    val endColor = when (MaterialTheme.colors.background) {
        Color.Black -> Color(0xFF804949)
        Color.White -> Color(0xFFB070EB)
        else -> Color(0xFF63ABAE)
    }

    val shapeModifier = Modifier
        .background(Brush.linearGradient(
            0f to startColor,
            .5f to centerColor,
            1f to endColor,
            start = Offset(Float.POSITIVE_INFINITY, 0f),
            end = Offset(0f, Float.POSITIVE_INFINITY)
        ), RoundedCornerShape(22.dp))

    Box(shapeModifier then modifier)
}
