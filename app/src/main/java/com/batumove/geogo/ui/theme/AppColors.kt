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
    gradientStart = Color(0xFFB8E0FD),
    gradientEnd = Color(0xFFF5F8FF)
)

val DarkAppColors = AppColors(
    success = Color(0xFF4DD6A7),
    warning = Color(0xFFFFC266),
    weatherCard = Color(0xFF243247),
    weatherIcon = Color(0xFF8BC4FF),
    gradientStart = Color(0xFF142D4A),
    gradientEnd = Color(0xFF101827)
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