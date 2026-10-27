package com.geo.ablemove.presentation.weather.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.geo.ablemove.R
import com.geo.ablemove.domain.model.AirQuality
import com.geo.ablemove.ui.components.cards.DefaultCard
import com.geo.ablemove.ui.theme.WeatherTheme

@Composable
fun AirQualityCard(
    airQuality: AirQuality,
    modifier: Modifier = Modifier
) {
    val aqi = airQuality.current?.europeanAqi
    val pm25 = airQuality.current?.pm25
    val pm10 = airQuality.current?.pm10
    val status = when {
        aqi == null -> stringResource(R.string.unknown)
        aqi <= 20 -> stringResource(R.string.good)
        aqi <= 40 -> stringResource(R.string.fair)
        aqi <= 60 -> stringResource(R.string.moderate)
        aqi <= 80 -> stringResource(R.string.poor)
        aqi <= 100 -> stringResource(R.string.very_poor)
        else -> stringResource(R.string.extremely_poor)
    }
    val accentColor = WeatherTheme.colors.aqiColor(aqi)
    DefaultCard(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Eco,
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(19.dp)
                    )
                    Text(
                        text = stringResource(R.string.air_quality),
                        color = accentColor,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                Text(
                    text = status,
                    color = accentColor,
                    style = MaterialTheme.typography.titleSmall
                )
            }
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = aqi?.toString() ?: "—",
                    style = MaterialTheme.typography.displayMedium
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text = stringResource(R.string.aqi),
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            LinearProgressIndicator(
                progress = {
                    ((aqi ?: 0).toFloat() / 100f)
                        .coerceIn(0f, 1f)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(7.dp)
                    .clip(RoundedCornerShape(50)),
                color = accentColor,
                trackColor = Color(0xFFE4EDF8),
                drawStopIndicator = {}
            )
            Text(
                text = buildString {
                    if (pm25 != null) append("PM2.5: $pm25 µg/m³")
                    if (pm25 != null && pm10 != null) append(" · ")
                    if (pm10 != null) append("PM10: $pm10 µg/m³")
                }.ifEmpty { "No particle data available" },
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}
