package com.geo.ablemove.ui.components.shimmer

import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue

@Stable
class ShimmerHostState(
    val theme: ShimmerTheme,
    val waveSize: Float,
) {
    var activeNodeCount by mutableIntStateOf(0)
    var progress by mutableFloatStateOf(0f)
}

val LocalShimmerHostState = compositionLocalOf<ShimmerHostState?> { null }