package com.batumove.geogo.ui.components.background

import androidx.compose.animation.core.withInfiniteAnimationFrameNanos
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import com.batumove.geogo.R
import kotlin.math.roundToInt
import kotlin.random.Random

private data class Cloud(
    val drawableRes: Int,
    val initialX: Float,
    val y: Float,
    val width: Float,
    val speed: Float,
    val alpha: Float,
)

@Composable
internal fun CloudLayer(
    modifier: Modifier = Modifier,
    isDay: Boolean = true,
    isStorm: Boolean = false,
    cloudCount: Int = 6,
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

        val clouds = remember(screenWidth, screenHeight, cloudCount) {
            val random = Random(System.nanoTime())

            List(cloudCount.coerceAtLeast(0)) { index ->
                val width = screenWidth * random.nextFloat(
                    from = 0.8f,
                    until = 1.8f,
                )

                Cloud(
                    drawableRes = when (index % 3) {
                        0 -> R.drawable.cloud_far
                        1 -> R.drawable.cloud_mid
                        else -> R.drawable.cloud_near
                    },

                    // Облака изначально находятся в разных
                    // позициях, включая частично видимые.
                    initialX = random.nextFloat(
                        from = -width,
                        until = screenWidth,
                    ),

                    // Основная облачность в верхних 65% экрана.
                    y = random.nextFloat(
                        from = -screenHeight * 0.1f,
                        until = screenHeight * 0.65f,
                    ),

                    width = width,

                    // Скорость в пикселях в секунду.
                    speed = random.nextFloat(
                        from = 3f,
                        until = 12f,
                    ),

                    alpha = random.nextFloat(
                        from = 0.35f,
                        until = 0.85f,
                    ),
                )
            }.sortedByDescending { it.width }
        }

        var elapsedNanos by remember {
            mutableLongStateOf(0L)
        }

        LaunchedEffect(Unit) {
            var previousFrameNanos = 0L

            while (true) {
                withInfiniteAnimationFrameNanos { frameTimeNanos ->
                    if (previousFrameNanos != 0L) {
                        val delta = (
                                frameTimeNanos - previousFrameNanos
                                ).coerceAtLeast(0L)

                        elapsedNanos += delta
                    }

                    previousFrameNanos = frameTimeNanos
                }
            }
        }

        val elapsedSeconds = elapsedNanos / 1_000_000_000f

        val cloudTint = remember(isDay, isStorm) {
            when {
                isStorm -> Color(0xFF65717D)
                !isDay -> Color(0xFF8191A5)
                else -> Color.White
            }
        }

        clouds.forEach { cloud ->
            val travelDistance = screenWidth + cloud.width

            val traveled = (
                    elapsedSeconds * cloud.speed
                    ) % travelDistance

            // Движение слева направо.
            // После выхода за правую границу облако
            // возвращается за левую.
            val x = (
                    cloud.initialX + traveled + cloud.width
                    ) % travelDistance - cloud.width

            Image(
                painter = painterResource(cloud.drawableRes),
                contentDescription = null,
                modifier = Modifier
                    .width(
                        with(density) {
                            cloud.width.toDp()
                        }
                    )
                    .offset {
                        IntOffset(
                            x = x.roundToInt(),
                            y = cloud.y.roundToInt(),
                        )
                    }
                    .alpha(cloud.alpha)
                    .graphicsLayer {
                        clip = false
                    },
                contentScale = ContentScale.FillWidth,
                colorFilter = ColorFilter.tint(
                    cloudTint,
                    blendMode = BlendMode.Modulate,
                ),
            )
        }
    }
}

internal fun Random.nextFloat(
    from: Float,
    until: Float,
): Float {
    return from + nextFloat() * (until - from)
}
