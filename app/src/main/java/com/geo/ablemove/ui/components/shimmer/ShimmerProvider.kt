package com.geo.ablemove.ui.components.shimmer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import com.geo.ablemove.ui.theme.WeatherTheme
import kotlinx.coroutines.isActive
import kotlin.math.hypot

@Composable
fun ShimmerProvider(
    theme: ShimmerTheme = WeatherTheme.shimmerTheme,
    content: @Composable () -> Unit,
) {
    val configuration = LocalWindowInfo.current.containerSize
    val density = LocalDensity.current

    val screenWidthPx = with(density) { configuration.width.dp.toPx() }
    val screenHeightPx = with(density) { configuration.height.dp.toPx() }

    val waveSize = remember(screenWidthPx, screenHeightPx) {
        hypot(screenWidthPx, screenHeightPx) / 2f
    }

    val hostState = remember(theme, waveSize) {
        ShimmerHostState(theme = theme, waveSize = waveSize)
    }

    val isRunning = hostState.activeNodeCount > 0

    LaunchedEffect(isRunning, theme.durationMillis) {
        if (!isRunning) return@LaunchedEffect

        val durationNanos = theme.durationMillis * 1_000_000L
        var playTimeNanos = (hostState.progress * durationNanos).toLong()
        var lastFrameTimeNanos = withFrameNanos { it }

        while (isActive) {
            withFrameNanos { frameTimeNanos ->
                val deltaNanos = frameTimeNanos - lastFrameTimeNanos
                lastFrameTimeNanos = frameTimeNanos
                playTimeNanos += deltaNanos
                hostState.progress = (playTimeNanos % durationNanos) / durationNanos.toFloat()
            }
        }
    }

    CompositionLocalProvider(
        LocalShimmerHostState provides hostState,
        content = content,
    )
}
