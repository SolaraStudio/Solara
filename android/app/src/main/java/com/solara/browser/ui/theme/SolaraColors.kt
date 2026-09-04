package com.solara.browser.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object SolaraColors {
    val MidnightDeep = Color(0xFF08080F)
    val Midnight = Color(0xFF0C0C18)
    val MidnightLight = Color(0xFF111122)
    val Surface = Color(0xFF16162A)
    val SurfaceLight = Color(0xFF1E1E38)
    val SurfaceHover = Color(0xFF252545)

    val Accent = Color(0xFF7C5CFC)
    val AccentBright = Color(0xFF9B7DFF)
    val AccentDim = Color(0xFF5A3FD9)
    val AccentGlow = Color(0x337C5CFC)

    val WarmGlow = Color(0xFFE8A84C)
    val WarmGlowDim = Color(0x22E8A84C)

    val TextPrimary = Color(0xFFEEEEF4)
    val TextSecondary = Color(0xFF9999B3)
    val TextTertiary = Color(0xFF666680)
    val TextGhost = Color(0xFF44445A)

    val Success = Color(0xFF4ADE80)
    val Error = Color(0xFFF87171)
    val Warning = Color(0xFFFBBF24)

    val GlassBorder = Color(0x1AFFFFFF)
    val GlassHighlight = Color(0x0DFFFFFF)

    val BackgroundGradient = Brush.verticalGradient(
        colors = listOf(MidnightDeep, Midnight, MidnightLight)
    )

    val SurfaceGradient = Brush.verticalGradient(
        colors = listOf(Surface, SurfaceLight)
    )

    val HorizontalGradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFF14142E), Color(0xFF1A1A35), Color(0xFF161630))
    )

    val GlowGradient = Brush.radialGradient(
        colors = listOf(Accent.copy(alpha = 0.08f), Accent.copy(alpha = 0.02f), Color.Transparent)
    )

    val UrlBarGradient = Brush.verticalGradient(
        colors = listOf(Color.White.copy(alpha = 0.08f), Color.White.copy(alpha = 0.03f))
    )

    val SidebarGradient = Brush.horizontalGradient(
        colors = listOf(MidnightDeep.copy(alpha = 0.97f), Midnight.copy(alpha = 0.93f))
    )

    val ToolbarGradient = Brush.verticalGradient(
        colors = listOf(MidnightLight.copy(alpha = 0.85f), MidnightDeep.copy(alpha = 0.95f))
    )

    val AccentGradient = Brush.horizontalGradient(
        colors = listOf(AccentDim, Accent, AccentBright)
    )

    fun itemBackground(isActive: Boolean): Color {
        return if (isActive) Accent.copy(alpha = 0.12f) else Color.Transparent
    }

    fun itemBorder(isActive: Boolean): Color {
        return if (isActive) Accent.copy(alpha = 0.25f) else Color.Transparent
    }
}
