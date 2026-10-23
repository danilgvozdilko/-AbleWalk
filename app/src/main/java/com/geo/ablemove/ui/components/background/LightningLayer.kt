package com.geo.ablemove.ui.components.background

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

@Composable
internal fun LightningLayer(
    modifier: Modifier = Modifier,
) {
    var flashAlpha by remember {
        mutableFloatStateOf(0f)
    }

    val animatedAlpha by animateFloatAsState(
        targetValue = flashAlpha,
        animationSpec = tween(
            durationMillis = 120,
            easing = LinearEasing,
        ),
        label = "lightningAlpha",
    )

    LaunchedEffect(Unit) {
        while (true) {
            delay(Random.nextLong(5000, 14000).milliseconds)

            flashAlpha = 0.35f
            delay(70.milliseconds)

            flashAlpha = 0f
            delay(100.milliseconds)

            if (Random.nextBoolean()) {
                flashAlpha = 0.55f
                delay(90.milliseconds)
                flashAlpha = 0f
            }
        }
    }

    Canvas(modifier.fillMaxSize()) {
        if (animatedAlpha <= 0f) return@Canvas

        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFDCE8FF).copy(
                        alpha = animatedAlpha
                    ),
                    Color(0xFFB8CBE8).copy(
                        alpha = animatedAlpha * 0.5f
                    ),
                    Color.Transparent,
                ),
                startY = 0f,
                endY = size.height * 0.8f,
            )
        )
    }
}
