package com.solara.browser.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

val SolaraPurple = Color(0xFF7C5CFC)
val SolaraPurpleDark = Color(0xFF5A3FD9)
val SolaraPurpleLight = Color(0xFFA78BFA)
val SolaraBackgroundDark = Color(0xFF08080F)
val SolaraSurfaceDark = Color(0xFF16162A)
val SolaraBackgroundLight = Color(0xFFF8F8FC)
val SolaraSurfaceLight = Color(0xFFFFFFFF)

private val DarkColorScheme = darkColorScheme(
    primary = SolaraPurple,
    secondary = SolaraPurpleDark,
    tertiary = SolaraPurpleLight,
    background = SolaraBackgroundDark,
    surface = SolaraSurfaceDark,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = SolaraPurple,
    secondary = SolaraPurpleDark,
    tertiary = SolaraPurpleLight,
    background = SolaraBackgroundLight,
    surface = SolaraSurfaceLight,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color(0xFF08080F),
    onSurface = Color(0xFF08080F)
)

@Composable
fun dynamicColorScheme(
    darkTheme: Boolean = isSystemInDarkTheme()
): ColorScheme {
    val context = LocalContext.current
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (darkTheme) {
            dynamicDarkColorScheme(context) ?: DarkColorScheme
        } else {
            dynamicLightColorScheme(context) ?: LightColorScheme
        }
    } else {
        if (darkTheme) DarkColorScheme else LightColorScheme
    }
}

@Composable
fun SolaraTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (dynamicColor) {
        dynamicColorScheme(darkTheme)
    } else {
        if (darkTheme) DarkColorScheme else LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = androidx.compose.material3.Typography(),
        content = content
    )
}
