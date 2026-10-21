package com.batumove.geogo.ui.components.background

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.batumove.geogo.data.mapper.toVisualType
import com.batumove.geogo.domain.model.WeatherCode
import com.batumove.geogo.domain.model.WeatherVisualType
import com.batumove.geogo.domain.model.toGradient

@Composable
internal fun WeatherBackground(
    weatherCode: WeatherCode,
    isDay: Boolean,
    modifier: Modifier = Modifier,
) {
    val visualType = remember(weatherCode) {
        weatherCode.toVisualType()
    }

    val gradient = remember(visualType, isDay) {
        visualType.toGradient(isDay)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = gradient.colors
                )
            )
    ) {
        when (visualType) {
            WeatherVisualType.CLEAR -> {
                SunLayer(isDay = isDay)
            }

            WeatherVisualType.PARTLY_CLOUDY -> {
                SunLayer(isDay = isDay)
                CloudLayer(
                    isDay = isDay,
                    isStorm = weatherCode in listOf(
                        WeatherCode.THUNDERSTORM,
                        WeatherCode.HEAVY_THUNDERSTORM,
                        WeatherCode.HEAVY_RAIN,
                    ),
                )
            }

            WeatherVisualType.CLOUDY,
            WeatherVisualType.OVERCAST -> {
                CloudLayer(
                    isDay = isDay,
                    isStorm = weatherCode in listOf(
                        WeatherCode.THUNDERSTORM,
                        WeatherCode.HEAVY_THUNDERSTORM,
                        WeatherCode.HEAVY_RAIN,
                    ),
                )
            }

            WeatherVisualType.FOG -> {
                FogLayer(isDay = isDay)
            }

            WeatherVisualType.DRIZZLE,
            WeatherVisualType.RAIN -> {
                CloudLayer(
                    isDay = isDay,
                    isStorm = weatherCode in listOf(
                        WeatherCode.THUNDERSTORM,
                        WeatherCode.HEAVY_THUNDERSTORM,
                        WeatherCode.HEAVY_RAIN,
                    ),
                )
                RainLayer(intensity = RainIntensity.LIGHT)
            }

            WeatherVisualType.HEAVY_RAIN -> {
                CloudLayer(
                    isDay = isDay,
                    isStorm = weatherCode in listOf(
                        WeatherCode.THUNDERSTORM,
                        WeatherCode.HEAVY_THUNDERSTORM,
                        WeatherCode.HEAVY_RAIN,
                    ),
                )
                RainLayer(intensity = RainIntensity.MODERATE)
            }

            WeatherVisualType.SNOW -> {
                CloudLayer(
                    isDay = isDay,
                    isStorm = weatherCode in listOf(
                        WeatherCode.THUNDERSTORM,
                        WeatherCode.HEAVY_THUNDERSTORM,
                        WeatherCode.HEAVY_RAIN,
                    ),
                )
                SnowLayer(intensity = 0.5f)
            }

            WeatherVisualType.HEAVY_SNOW -> {
                CloudLayer(
                    isDay = isDay,
                    isStorm = weatherCode in listOf(
                        WeatherCode.THUNDERSTORM,
                        WeatherCode.HEAVY_THUNDERSTORM,
                        WeatherCode.HEAVY_RAIN,
                    ),
                )
                SnowLayer(intensity = 1f)
            }

            WeatherVisualType.THUNDERSTORM -> {
                CloudLayer(
                    isDay = isDay,
                    isStorm = weatherCode in listOf(
                        WeatherCode.THUNDERSTORM,
                        WeatherCode.HEAVY_THUNDERSTORM,
                        WeatherCode.HEAVY_RAIN,
                    ),
                )
                RainLayer(intensity = RainIntensity.HEAVY)
                LightningLayer()
            }
        }
    }
}
