package com.geo.ablemove.ui.components

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.lazyRowFadingEdge(
    state: LazyListState,
    edgeWidth: Dp = 28.dp
): Modifier {
    val edgeWidthPx = with(LocalDensity.current) {
        edgeWidth.toPx()
    }

    val fadeStart = state.canScrollBackward
    val fadeEnd = state.canScrollForward

    return this
        .graphicsLayer {
            compositingStrategy = CompositingStrategy.Offscreen
        }
        .drawWithContent {
            drawContent()

            if (fadeStart) {
                drawRect(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        ),
                        startX = 0f,
                        endX = edgeWidthPx
                    ),
                    blendMode = BlendMode.DstIn
                )
            }

            if (fadeEnd) {
                drawRect(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.Black,
                            Color.Transparent
                        ),
                        startX = size.width - edgeWidthPx,
                        endX = size.width
                    ),
                    blendMode = BlendMode.DstIn
                )
            }
        }
}
