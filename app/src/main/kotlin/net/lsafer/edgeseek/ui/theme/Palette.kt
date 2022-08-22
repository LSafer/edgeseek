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

@file:SuppressLint("ConflictingOnColor")

package net.lsafer.edgeseek.ui.theme

import android.annotation.SuppressLint
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val BlackColorPalette = darkColors(
    primary = Gray800,
    primaryVariant = Gray500,
    onPrimary = Color.White,
    secondary = Color.Red,
    background = Color.Black,
    onBackground = Color.White,
    surface = Color.Black,
    onSurface = Color.White
)

val DarkColorPalette = darkColors(
    primary = Gray600,
    primaryVariant = Gray300,
    onPrimary = Color.White,
    secondary = Cyan500,
    background = Gray900,
    onBackground = Color.White,
    surface = Gray900,
    onSurface = Color.White
)

val LightColorPalette = lightColors(
    primary = Gray400,
    primaryVariant = Gray700,
    onPrimary = Color.White,
    secondary = Cyan900,
    background = Cyan100,
    onBackground = Color.Black,
    surface = Cyan100,
    onSurface = Color.Black
)

val WhiteColorPalette = lightColors(
    primary = Gray200,
    primaryVariant = Gray600,
    onPrimary = Color.Black,
    secondary = Purple500,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black
)
