package com.geo.ablemove.ui.components.background

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
internal fun SunLayer(
    modifier: Modifier = Modifier,
    isDay: Boolean = true,
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val center = Offset(
            x = size.width * 0.78f,
            y = size.height * 0.18f,
        )

        if (isDay) {
            val sunRadius = size.width * 0.075f
            val glowRadius = size.width * 0.65f

            // Atmospheric glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFFFF4D6).copy(alpha = 0.28f),
                        Color(0xFFFFE6B0).copy(alpha = 0.12f),
                        Color.Transparent,
                    ),
                    center = center,
                    radius = glowRadius,
                ),
                radius = glowRadius,
                center = center,
            )

            // Inner glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.55f),
                        Color(0xFFFFF2CC).copy(alpha = 0.25f),
                        Color.Transparent,
                    ),
                    center = center,
                    radius = sunRadius * 3.5f,
                ),
                radius = sunRadius * 3.5f,
                center = center,
            )

            // Sun disk
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.White,
                        Color(0xFFFFF8E7),
                        Color(0xFFFFEBC2),
                    ),
                    center = center,
                    radius = sunRadius,
                ),
                radius = sunRadius,
                center = center,
            )
        } else {
            val moonRadius = size.width * 0.055f
            val glowRadius = size.width * 0.42f

            // Atmospheric moonlight
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFB8D4F2).copy(alpha = 0.16f),
                        Color(0xFF8BAED6).copy(alpha = 0.07f),
                        Color.Transparent,
                    ),
                    center = center,
                    radius = glowRadius,
                ),
                radius = glowRadius,
                center = center,
            )

            // Inner moon glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFE8F2FF).copy(alpha = 0.35f),
                        Color(0xFFB8D4F2).copy(alpha = 0.12f),
                        Color.Transparent,
                    ),
                    center = center,
                    radius = moonRadius * 3.5f,
                ),
                radius = moonRadius * 3.5f,
                center = center,
            )

            // Moon disk
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFF5F8FC),
                        Color(0xFFE0E8F2),
                        Color(0xFFBBCBDD),
                    ),
                    center = center,
                    radius = moonRadius,
                ),
                radius = moonRadius,
                center = center,
            )
        }
    }
}
