package com.geo.ablemove.ui.components.background

import androidx.compose.animation.core.withInfiniteAnimationFrameNanos
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import com.geo.ablemove.R
import kotlin.math.roundToInt
import kotlin.random.Random

private data class Fog(
    val initialPhase: Float,
    val y: Float,
    val width: Float,
    val height: Float,
    val speed: Float,
    val alpha: Float,
    val direction: Int,
)

@Composable
internal fun FogLayer(
    modifier: Modifier = Modifier,
    isDay: Boolean = true,
) {
    val density = LocalDensity.current

    BoxWithConstraints(
        modifier = modifier.fillMaxSize()
    ) {
        val screenWidth = constraints.maxWidth.toFloat()
        val screenHeight = constraints.maxHeight.toFloat()

        if (screenWidth <= 0f || screenHeight <= 0f) {
            return@BoxWithConstraints
        }

        val fogTint = remember(isDay) {
            if (isDay) {
                Color(0xFFE3E9EC)
            } else {
                Color(0xFF9AA9BA)
            }
        }

        // Основной статичный слой тумана
        Image(
            painter = painterResource(R.drawable.fog_dense),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.75f),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(
                fogTint,
                blendMode = BlendMode.Modulate,
            ),
        )

        val fogLayers = remember(screenWidth, screenHeight) {
            val random = Random(System.nanoTime())

            List(4) { index ->
                Fog(
                    initialPhase = random.nextFloat(),

                    y = screenHeight * when (index) {
                        0 -> 0.10f
                        1 -> 0.30f
                        2 -> 0.55f
                        else -> 0.75f
                    },

                    width = screenWidth * random.nextFloat(
                        from = 1.2f,
                        until = 2.0f,
                    ),

                    height = screenHeight * random.nextFloat(
                        from = 0.25f,
                        until = 0.45f,
                    ),

                    speed = random.nextFloat(
                        from = 1.5f,
                        until = 4f,
                    ),

                    alpha = random.nextFloat(
                        from = 0.12f,
                        until = 0.28f,
                    ),

                    direction = if (index % 2 == 0) 1 else -1,
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

        fogLayers.forEach { fog ->
            val travelDistance = screenWidth + fog.width

            val traveled = (
                    fog.initialPhase * travelDistance +
                            elapsedSeconds * fog.speed
                    ) % travelDistance

            val x = if (fog.direction > 0) {
                traveled - fog.width
            } else {
                screenWidth - traveled
            }

            Image(
                painter = painterResource(R.drawable.fog_soft),
                contentDescription = null,
                modifier = Modifier
                    .size(
                        width = with(density) {
                            fog.width.toDp()
                        },
                        height = with(density) {
                            fog.height.toDp()
                        },
                    )
                    .offset {
                        IntOffset(
                            x = x.roundToInt(),
                            y = fog.y.roundToInt(),
                        )
                    }
                    .alpha(fog.alpha),
                contentScale = ContentScale.FillBounds,
                colorFilter = ColorFilter.tint(
                    fogTint,
                    blendMode = BlendMode.Modulate,
                ),
            )
        }
    }
}
