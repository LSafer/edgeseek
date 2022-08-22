package net.lsafer.edgeseek.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp

val StatusBarPadding: Dp
    @Composable
    get() = dimensionResource(
        LocalContext.current.resources.getIdentifier(
            "status_bar_height",
            "dimen",
            "android"
        )
    )

val NavigationBarPadding: Dp
    @Composable
    get() = dimensionResource(
        LocalContext.current.resources.getIdentifier(
            "navigation_bar_height",
            "dimen",
            "android"
        )
    )
