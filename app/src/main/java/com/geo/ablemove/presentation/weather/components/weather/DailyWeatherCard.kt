package com.geo.ablemove.presentation.weather.components.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.geo.ablemove.R
import com.geo.ablemove.data.mapper.toVisualType
import com.geo.ablemove.domain.model.DailyWeather
import com.geo.ablemove.domain.model.formatDay
import com.geo.ablemove.domain.model.toIconRes
import com.geo.ablemove.ui.components.cards.DefaultCard
import com.geo.ablemove.ui.theme.WeatherTheme
import kotlin.math.roundToInt

@Composable
fun DailyWeatherCard(data: DailyWeather) {
    DefaultCard(Modifier.fillMaxWidth()) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            data.time.forEachIndexed { index, _ ->
                DailyWeatherItem(data, index)

                if (index != data.time.lastIndex) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                }
            }
        }
    }
}

@Composable
private fun DailyWeatherItem(
    data: DailyWeather,
    index: Int,
) {
    val day = formatDay(data.time[index], index == 0)
    val minTemperature = data.minTemperature[index]
    val maxTemperature = data.maxTemperature[index]
    val weekMin = data.minTemperature.filterNotNull().minOrNull() ?: 0.0
    val weekMax = data.maxTemperature.filterNotNull().maxOrNull() ?: 0.0
    val icon = data.weatherCode[index]?.toVisualType()?.toIconRes() ?: R.drawable.ic_weather_cloudy
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = day,
            modifier = Modifier.width(52.dp),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Image(
            painterResource(icon),
            "weather icon",
            Modifier.size(32.dp)
        )
        Spacer(Modifier.weight(1f))

        Text(
            text = minTemperature?.let {
                buildString {
                    append(it.roundToInt())
                    append(stringResource(R.string.degrees))
                }
            } ?: "--",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        TemperatureRangeBar(
            minTemperature = minTemperature,
            maxTemperature = maxTemperature,
            weekMin = weekMin,
            weekMax = weekMax,
            modifier = Modifier
                .weight(2f)
                .height(8.dp)
        )

        Text(
            text = maxTemperature?.let { "${it.roundToInt()}°" } ?: "--",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun TemperatureRangeBar(
    minTemperature: Double?,
    maxTemperature: Double?,
    weekMin: Double,
    weekMax: Double,
    modifier: Modifier = Modifier
) {
    val range = (weekMax - weekMin).coerceAtLeast(1.0)

    val startFraction = minTemperature
        ?.let { ((it - weekMin) / range).toFloat().coerceIn(0f, 1f) }
        ?: 0f

    val endFraction = maxTemperature
        ?.let { ((it - weekMin) / range).toFloat().coerceIn(0f, 1f) }
        ?: 0f

    BoxWithConstraints(
        modifier = modifier
            .clip(CircleShape)
            .background(
                MaterialTheme.colorScheme.surfaceVariant
            )
    ) {
        if (minTemperature != null && maxTemperature != null) {
            val barWidth = maxWidth

            Box(
                modifier = Modifier
                    .offset(x = barWidth * startFraction)
                    .width(
                        barWidth * (endFraction - startFraction)
                            .coerceAtLeast(0f)
                    )
                    .fillMaxHeight()
                    .clip(CircleShape)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = WeatherTheme.colors.temperatureGradient
                        )
                    )
            )
        }
    }
}
