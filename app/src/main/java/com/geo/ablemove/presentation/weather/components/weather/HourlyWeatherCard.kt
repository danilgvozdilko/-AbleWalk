package com.geo.ablemove.presentation.weather.components.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.geo.ablemove.R
import com.geo.ablemove.data.mapper.toVisualType
import com.geo.ablemove.domain.model.HourlyWeather
import com.geo.ablemove.domain.model.toIconRes
import com.geo.ablemove.ui.components.cards.DefaultCard
import com.geo.ablemove.ui.components.lazyRowFadingEdge

@Composable
fun HourlyWeatherCard(data: HourlyWeather) {
    val listState = rememberLazyListState()
    DefaultCard(Modifier.fillMaxWidth()) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.hourly_forecast),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(8.dp))
            LazyRow(
                Modifier
                    .fillMaxWidth()
                    .lazyRowFadingEdge(
                        state = listState,
                        edgeWidth = 32.dp
                    ),
                state = listState,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(data.time.size) { index ->
                    HourlyWeatherItem(data, index)
                }
            }
        }
    }
}

@Composable
fun HourlyWeatherItem(data: HourlyWeather, index: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.surface,
                MaterialTheme.shapes.medium
            )
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Text(
            data.time[index],
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            buildString {
                append(data.temperature[index]?.toInt())
                append(stringResource(R.string.degrees))
            },
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Image(
            painterResource(
                data.weatherCode[index]?.toVisualType()?.toIconRes()
                    ?: R.drawable.ic_weather_cloudy
            ),
            "weather icon",
            Modifier.size(32.dp)
        )
        Text(
            buildString {
                append(data.precipitationProbability[index])
                append(stringResource(R.string.percent_symbol))
            },
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
