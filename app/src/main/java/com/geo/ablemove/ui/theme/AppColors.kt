package com.geo.ablemove.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.geo.ablemove.ui.components.shimmer.ShimmerTheme
import com.geo.ablemove.ui.components.shimmer.ShimmerThemes

@Immutable
data class AppColors(
    val success: Color,
    val warning: Color,
    val weatherCard: Color,
    val weatherIcon: Color,
    val gradientStart: Color,
    val gradientEnd: Color,
    val temperatureCold: Color,
    val temperatureMild: Color,
    val temperatureWarm: Color
) {
    val temperatureGradient: List<Color>
        get() = listOf(
            temperatureCold,
            temperatureMild,
            temperatureWarm
        )
}

val LightAppColors = AppColors(
    success = Color(0xFF10A67A),
    warning = Color(0xFFF5A623),
    weatherCard = Color.White,
    weatherIcon = Color(0xFF3488F6),
    gradientStart = Color(0xFFF2F8FF),
    gradientEnd = Color(0xFFFFFFFF),
    temperatureCold = Color(0xFF008F75),
    temperatureMild = Color(0xFF368AF5),
    temperatureWarm = Color(0xFFFFC52E)
)

val DarkAppColors = AppColors(
    success = Color(0xFF4DD6A7),
    warning = Color(0xFFFFC266),
    weatherCard = Color(0xFF243247),
    weatherIcon = Color(0xFF8BC4FF),
    gradientStart = Color(0xFF071424),
    gradientEnd = Color(0xFF000000),
    temperatureCold = Color(0xFF20BFA0),
    temperatureMild = Color(0xFF70ADFF),
    temperatureWarm = Color(0xFFFFD45C)
)

internal val LocalAppColors = staticCompositionLocalOf {
    LightAppColors
}

internal val LocalShimmerTheme = staticCompositionLocalOf {
    ShimmerThemes.SpatialSilverLight
}

object WeatherTheme {
    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current

    val shimmerTheme: ShimmerTheme
        @Composable
        @ReadOnlyComposable
        get() = LocalShimmerTheme.current
}
