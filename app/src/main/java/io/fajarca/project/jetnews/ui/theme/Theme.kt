package io.fajarca.project.jetnews.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color(0xFFc7c7c7),
    secondary = Color(0xFF494949),
    onPrimary = Grey50,
    onSecondary = Grey50,
    error = Color(0xFFd00036),
    onError = Grey50
)

private val LightColorPalette = lightColors(
    primary = Grey50,
    secondary = Grey600,
    onPrimary = Grey900,
    onSecondary = Grey50,
    error = Color(0xFFd00036),
    onError = Grey50

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun JetNewsTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}