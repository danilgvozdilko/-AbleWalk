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
    val temperatureWarm: Color,
    val aqiUnknown: Color,
    val aqiGood: Color,
    val aqiFair: Color,
    val aqiModerate: Color,
    val aqiPoor: Color,
    val aqiVeryPoor: Color
) {
    val temperatureGradient: List<Color>
        get() = listOf(
            temperatureCold,
            temperatureMild,
            temperatureWarm
        )

    fun aqiColor(aqi: Int?): Color = when {
        aqi == null -> aqiUnknown
        aqi <= 20 -> aqiGood
        aqi <= 40 -> aqiFair
        aqi <= 60 -> aqiModerate
        aqi <= 80 -> aqiPoor
        else -> aqiVeryPoor
    }
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
    temperatureWarm = Color(0xFFFFC52E),
    aqiUnknown = Color(0xFF64748B),
    aqiGood = Color(0xFF008C69),
    aqiFair = Color(0xFF16A34A),
    aqiModerate = Color(0xFFF59E0B),
    aqiPoor = Color(0xFFF97316),
    aqiVeryPoor = Color(0xFFEF4444)
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
    temperatureWarm = Color(0xFFFFD45C),
    aqiUnknown = Color(0xFF94A3B8),
    aqiGood = Color(0xFF34D399),
    aqiFair = Color(0xFF4ADE80),
    aqiModerate = Color(0xFFFBBF24),
    aqiPoor = Color(0xFFFB923C),
    aqiVeryPoor = Color(0xFFF87171)
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
