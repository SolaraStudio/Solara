package com.solara.browser.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Custom colors for Solara
val SolaraPurple = Color(0xFF8B5CF6)
val SolaraPurpleDark = Color(0xFF6D28D9)
val SolaraPurpleLight = Color(0xFFC4B5FD)
val SolaraBackgroundDark = Color(0xFF0F0F1A)
val SolaraSurfaceDark = Color(0xFF1A1A2E)
val SolaraBackgroundLight = Color(0xFFF8F8FC)
val SolaraSurfaceLight = Color(0xFFFFFFFF)

// Dark color scheme (fallback)
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

// Light color scheme (fallback)
private val LightColorScheme = lightColorScheme(
    primary = SolaraPurple,
    secondary = SolaraPurpleDark,
    tertiary = SolaraPurpleLight,
    background = SolaraBackgroundLight,
    surface = SolaraSurfaceLight,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color(0xFF1A1A2E),
    onSurface = Color(0xFF1A1A2E)
)

// Dynamic color support (Material You)
@Composable
fun dynamicColorScheme(
    darkTheme: Boolean = isSystemInDarkTheme()
): ColorScheme {
    val context = LocalContext.current
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        // Use Material You dynamic colors on Android 12+
        val dynamicColors = androidx.compose.material3.dynamicColor(context)
        if (dynamicColors != null) {
            if (darkTheme) dynamicColors.darkColorScheme()
            else dynamicColors.lightColorScheme()
        } else {
            if (darkTheme) DarkColorScheme else LightColorScheme
        }
    } else {
        if (darkTheme) DarkColorScheme else LightColorScheme
    }
}

// Solara theme wrapper
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
        typography = Typography(),
        content = content
    )
}
