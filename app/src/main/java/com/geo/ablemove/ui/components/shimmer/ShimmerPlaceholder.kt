package com.geo.ablemove.ui.components.shimmer

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import com.geo.ablemove.ui.theme.WeatherTheme

@Composable
fun ShimmerPlaceholder(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.extraSmall,
) {
    Box(
        modifier = modifier.shimmer(
            visible = true,
            shape = shape,
            themeOverride = WeatherTheme.shimmerTheme
        )
    )
}