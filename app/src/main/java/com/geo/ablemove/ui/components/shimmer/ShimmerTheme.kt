package com.geo.ablemove.ui.components.shimmer

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

@Immutable
data class ShimmerTheme(
    val colors: List<Color>,
    val durationMillis: Int = 2_000,
    val angle: Float = 20f,
) {
    internal val argbColors: IntArray = colors.map(Color::toArgb).toIntArray()
    internal val colorPositions: FloatArray = FloatArray(colors.size) { index ->
        index / (colors.size - 1).toFloat()
    }

    init {
        require(colors.size >= 2) { "ShimmerTheme requires at least 2 colors." }
    }
}

object ShimmerThemes {
    val SpatialSilverLight = ShimmerTheme(
        colors = listOf(
            Color(0xFFD4D9DF),
            Color(0xFFE8EDF2),
            Color(0xFFFFFFFF),
            Color(0xFFE8EDF2),
            Color(0xFFD4D9DF),
        ),
        durationMillis = 1_800,
        angle = 10f,
    )

    val SpatialSilverDark = ShimmerTheme(
        colors = listOf(
            Color(0xFF2D333D),
            Color(0xFF454D59),
            Color(0xFF707A89),
            Color(0xFF2D333D),
        ),
        durationMillis = 1_800,
        angle = 10f,
    )
}
