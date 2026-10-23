package com.geo.ablemove.ui.components.background

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.sin
import kotlin.random.Random

private data class Snowflake(
    val x: Float,
    val y: Float,
    val radius: Float,
    val speed: Float,
    val drift: Float,
    val alpha: Float,
    val phase: Float,
)

@Composable
internal fun SnowLayer(
    modifier: Modifier = Modifier,
    intensity: Float = 1f,
) {
    val flakes = remember {
        List(140) {
            Snowflake(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                radius = Random.nextFloat() * 2.5f + 0.5f,
                speed = Random.nextFloat() * 0.5f + 0.3f,
                drift = Random.nextFloat() * 20f + 5f,
                alpha = Random.nextFloat() * 0.5f + 0.25f,
                phase = Random.nextFloat() * 2f * PI.toFloat(),
            )
        }
    }

    val transition = rememberInfiniteTransition(
        label = "snow",
    )

    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 12000,
                easing = LinearEasing,
            ),
        ),
        label = "snowProgress",
    )

    Canvas(modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        flakes.forEach { flake ->
            val y = (
                    (flake.y + progress * flake.speed) % 1f
                    ) * height

            val driftX = sin(
                progress * 2f * PI.toFloat() + flake.phase
            ) * flake.drift

            val x = flake.x * width + driftX

            drawCircle(
                color = Color.White.copy(
                    alpha = flake.alpha * intensity.coerceIn(0f, 1f)
                ),
                radius = flake.radius.dp.toPx(),
                center = Offset(x, y),
            )
        }
    }
}
