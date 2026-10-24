package com.geo.ablemove.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import com.geo.ablemove.ui.components.shimmer.ShimmerThemes

private val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    background = LightBackground,
    surface = LightSurface,
    onBackground = LightText,
    onSurface = LightText,
    onSurfaceVariant = LightSecondaryText,
    error = LightError,
    outlineVariant = LightOutlineVariant,
    surfaceBright = LightBackground
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    background = DarkBackground,
    surface = DarkSurface,
    onBackground = DarkText,
    onSurface = DarkText,
    onSurfaceVariant = DarkSecondaryText,
    error = DarkError,
    outlineVariant = DarkOutlineVariant,
    surfaceBright = LightBackground
)

@Composable
fun GeoGOTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val appColors = if (darkTheme) {
        DarkAppColors
    } else {
        LightAppColors
    }

    val shimmerTheme = if (darkTheme) {
        ShimmerThemes.SpatialSilverDark
    } else {
        ShimmerThemes.SpatialSilverLight
    }

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    CompositionLocalProvider(
        LocalAppColors provides appColors,
        LocalShimmerTheme provides shimmerTheme
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content,
            shapes = Shape
        )
    }
}
