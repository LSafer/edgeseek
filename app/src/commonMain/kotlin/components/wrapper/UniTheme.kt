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
package net.lsafer.edgeseek.app.components.wrapper

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import net.lsafer.edgeseek.app.*
import net.lsafer.sundry.storage.select

@Composable
fun UniTheme(local: Local, content: @Composable () -> Unit) {
    val uiColors by produceState(UI_COLORS_DEFAULT, local) {
        local.dataStore
            .select<String>(PK_UI_COLORS)
            .filterNotNull()
            .distinctUntilChanged()
            .collect { value = it }
    }

    MaterialTheme(
        colorScheme = when (uiColors) {
            UI_COLORS_BLACK -> BlackColorPalette
            UI_COLORS_DARK -> DarkColorPalette
            UI_COLORS_LIGHT -> LightColorPalette
            UI_COLORS_WHITE -> WhiteColorPalette

            else -> when {
                isSystemInDarkTheme() -> DarkColorPalette
                else -> LightColorPalette
            }
        },
        typography = Typography(
            bodyLarge = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
        ),
        shapes = Shapes(
            small = RoundedCornerShape(10.dp),
            medium = RoundedCornerShape(10.dp),
            large = RoundedCornerShape(0.dp)
        ),
        content = content
    )
}
