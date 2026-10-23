package com.batumove.geogo.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class AppColors(
    val success: Color,
    val warning: Color,
    val weatherCard: Color,
    val weatherIcon: Color,
    val gradientStart: Color,
    val gradientEnd: Color
)

val LightAppColors = AppColors(
    success = Color(0xFF10A67A),
    warning = Color(0xFFF5A623),
    weatherCard = Color.White,
    weatherIcon = Color(0xFF3488F6),
    gradientStart = Color(0xFFF2F8FF),
    gradientEnd = Color(0xFFFFFFFF)
)

val DarkAppColors = AppColors(
    success = Color(0xFF4DD6A7),
    warning = Color(0xFFFFC266),
    weatherCard = Color(0xFF243247),
    weatherIcon = Color(0xFF8BC4FF),
    gradientStart = Color(0xFF071424),
    gradientEnd = Color(0xFF000000)
)

internal val LocalAppColors = staticCompositionLocalOf {
    LightAppColors
}

object WeatherTheme {
    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current
}