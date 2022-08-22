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
