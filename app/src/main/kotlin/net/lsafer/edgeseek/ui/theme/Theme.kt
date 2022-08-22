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
