package com.batumove.geogo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.batumove.geogo.ui.theme.WeatherTheme

@Composable
fun Background(
    content: @Composable BoxScope.() -> Unit
) {
    val colors = WeatherTheme.colors
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        colors.gradientStart,
                        colors.gradientEnd
                    )
                )
            ),
        content = content
    )
}
