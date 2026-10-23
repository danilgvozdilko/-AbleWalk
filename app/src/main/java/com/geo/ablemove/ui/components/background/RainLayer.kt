package com.geo.ablemove.ui.components.background

import androidx.compose.animation.core.withInfiniteAnimationFrameNanos
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import kotlin.random.Random

private data class Raindrop(
    val x: Float,
    val y: Float,
    val speed: Float,
    val length: Float,
    val alpha: Float,
    val tilt: Float,
    val strokeWidth: Float,
)

@Composable
internal fun RainLayer(
    modifier: Modifier = Modifier,
    intensity: RainIntensity = RainIntensity.MODERATE,
) {
    val drops = remember(intensity) {
        val random = Random(System.nanoTime())

        List(intensity.dropCount) {
            Raindrop(
                x = random.nextFloat(),
                y = random.nextFloat(),
                speed = random.nextFloat(
                    intensity.speedRange.start,
                    intensity.speedRange.endInclusive,
                ),
                length = random.nextFloat(
                    intensity.lengthRange.start,
                    intensity.lengthRange.endInclusive,
                ),
                alpha = random.nextFloat(
                    intensity.alphaRange.start,
                    intensity.alphaRange.endInclusive,
                ),
                tilt = random.nextFloat(-0.25f, -0.10f),
                strokeWidth = random.nextFloat(0.7f, 1.5f),
            )
        }
    }

    var elapsedSeconds by remember {
        mutableFloatStateOf(0f)
    }

    LaunchedEffect(Unit) {
        var previousFrame = 0L

        while (true) {
            withInfiniteAnimationFrameNanos { frameTime ->
                if (previousFrame != 0L) {
                    elapsedSeconds += (
                            frameTime - previousFrame
                            ) / 1_000_000_000f
                }

                previousFrame = frameTime
            }
        }
    }

    Canvas(
        modifier = modifier.fillMaxSize()
    ) {
        val width = size.width
        val height = size.height

        drops.forEach { drop ->
            val x = drop.x * width

            val y = (
                    (drop.y + elapsedSeconds * drop.speed) % 1f
                    ) * height

            val length = drop.length

            drawLine(
                color = Color.White.copy(alpha = drop.alpha),
                start = Offset(
                    x = x,
                    y = y,
                ),
                end = Offset(
                    x = x + length * drop.tilt,
                    y = y + length,
                ),
                strokeWidth = drop.strokeWidth,
                cap = StrokeCap.Round,
            )
        }
    }
}

internal enum class RainIntensity(
    val dropCount: Int,
    val speedRange: ClosedFloatingPointRange<Float>,
    val lengthRange: ClosedFloatingPointRange<Float>,
    val alphaRange: ClosedFloatingPointRange<Float>,
) {
    LIGHT(
        dropCount = 65,
        speedRange = 0.6f..1.0f,
        lengthRange = 8f..16f,
        alphaRange = 0.12f..0.25f,
    ),
    MODERATE(
        dropCount = 150,
        speedRange = 1.0f..1.6f,
        lengthRange = 14f..26f,
        alphaRange = 0.18f..0.35f,
    ),
    HEAVY(
        dropCount = 300,
        speedRange = 1.5f..2.4f,
        lengthRange = 22f..40f,
        alphaRange = 0.25f..0.50f,
    ),
}