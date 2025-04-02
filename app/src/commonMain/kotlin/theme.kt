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

package net.lsafer.edgeseek.app

import android.annotation.SuppressLint
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val Purple500 = Color(0xff905AD3)

val Gray200 = Color(0xff858585)
val Gray300 = Color(0xff808080)
val Gray400 = Color(0xFF6E6E6E)
val Gray500 = Color(0xFF5C5C5C)
val Gray600 = Color(0xFF5A5A5A)
val Gray700 = Color(0xff494949)
val Gray800 = Color(0xFF424242)
val Gray900 = Color(0xff303030)

val Cyan100 = Color(0xFFB5BDBD)
val Cyan500 = Color(0xFF00B1B8)
val Cyan900 = Color(0xff01579B)

val BlackColorPalette = darkColorScheme(
    primary = Gray800,
//    primaryVariant = Gray500,
    onPrimary = Color.White,
    secondary = Color.Red,
    background = Color.Black,
    onBackground = Color.White,
    surface = Color.Black,
    onSurface = Color.White
)

val DarkColorPalette = darkColorScheme(
    primary = Gray600,
//    primaryVariant = Gray300,
    onPrimary = Color.White,
    secondary = Cyan500,
    background = Gray900,
    onBackground = Color.White,
    surface = Gray900,
    onSurface = Color.White
)

val LightColorPalette = lightColorScheme(
    primary = Gray400,
//    primaryVariant = Gray700,
    onPrimary = Color.White,
    secondary = Cyan900,
    background = Cyan100,
    onBackground = Color.Black,
    surface = Cyan100,
    onSurface = Color.Black
)

val WhiteColorPalette = lightColorScheme(
    primary = Gray200,
//    primaryVariant = Gray600,
    onPrimary = Color.Black,
    secondary = Purple500,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black
)
