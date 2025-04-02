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
package net.lsafer.edgeseek.app.components.lib

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MobileModel(modifier: Modifier) {
    val startColor = when (MaterialTheme.colorScheme.background) {
        Color.Black -> Color(0xFF785858)
        Color.White -> Color(0xFF8989F4)
        else -> Color(0xFF81A0A1)
    }
    val centerColor = when (MaterialTheme.colorScheme.background) {
        Color.Black -> Color(0xFF816060)
        Color.White -> Color(0xFF9E7CEF)
        else -> Color(0xFF81A0A1)
    }
    val endColor = when (MaterialTheme.colorScheme.background) {
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
