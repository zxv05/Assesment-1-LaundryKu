package com.athallah.laundryku.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = SkyBlue,
    secondary = SkyBlueDark,
    background = CreamWhite,
    surface = androidx.compose.ui.graphics.Color.White,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    onBackground = SlateText,
    onSurface = SlateText
)

private val DarkColors = darkColorScheme(
    primary = SkyBlue,
    secondary = SkyBlueDark
)

@Composable
fun LaundryKuTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography,
        content = content
    )
}
