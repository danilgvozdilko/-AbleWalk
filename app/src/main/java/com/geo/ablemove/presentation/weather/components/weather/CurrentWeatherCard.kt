package com.geo.ablemove.presentation.weather.components.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.geo.ablemove.R
import com.geo.ablemove.domain.model.CurrentWeather
import com.geo.ablemove.domain.model.UnitFormatting
import com.geo.ablemove.domain.model.Weather

@Composable
fun CurrentWeatherCard(locationName: String, unitFormatting: UnitFormatting, data: Weather) {
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = locationName,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.surfaceBright
        )
        Row {
            Text(
                text = data.currentWeather.temperature?.toInt().toString(),
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.surfaceBright
            )
            Text(
                text = stringResource(R.string.degrees),
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.surfaceBright
            )
        }
        Text(
            text = data.currentWeather.weatherCode?.description ?: "",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.surfaceBright
        )
        WeatherTemperatureInfo(data)
        WeatherHumidityAndWindInfo(data.currentWeather, unitFormatting)
    }
}

@Composable
fun WeatherTemperatureInfo(data: Weather) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = buildString {
                append(stringResource(R.string.feels_like))
                append(data.currentWeather.feelsLike?.toInt())
                append(stringResource(R.string.degrees))
            },
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.surfaceBright
        )

        Text(
            text = buildString {
                append(stringResource(R.string.max))
                append(data.dailyWeather.maxTemperature.firstOrNull()?.toInt())
                append(stringResource(R.string.degrees))
            },
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.surfaceBright
        )

        Text(
            text = buildString {
                append(stringResource(R.string.min))
                append(data.dailyWeather.minTemperature.firstOrNull()?.toInt())
                append(stringResource(R.string.degrees))
            },
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.surfaceBright
        )
    }
}

@Composable
fun WeatherHumidityAndWindInfo(data: CurrentWeather, unitFormatting: UnitFormatting) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = buildString {
                append(stringResource(R.string.humidity))
                append(data.humidity)
                append(stringResource(R.string.percent_symbol))
            },
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.surfaceBright
        )

        Text(
            text = buildString {
                append(stringResource(R.string.wind_speed))
                append(data.windSpeed?.toInt())
                append(unitFormatting.windSpeed.displayName)
            },
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.surfaceBright
        )
    }
}
