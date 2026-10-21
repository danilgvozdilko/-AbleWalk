package com.batumove.geogo.presentation.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.batumove.geogo.domain.model.AirQuality
import com.batumove.geogo.domain.model.CurrentWeather
import com.batumove.geogo.domain.model.DailyWeather
import com.batumove.geogo.domain.model.HourlyWeather
import com.batumove.geogo.domain.model.Marine
import com.batumove.geogo.domain.model.Weather
import com.batumove.geogo.domain.model.WeatherCode
import com.batumove.geogo.domain.model.isDay
import com.batumove.geogo.presentation.home.UiState
import com.batumove.geogo.ui.components.background.WeatherBackground
import com.batumove.geogo.ui.theme.GeoGOTheme

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val locationName by viewModel.locationName.collectAsStateWithLifecycle()

    WeatherBackground(state.weather)
    Column(modifier = Modifier.fillMaxSize()) {
        WeatherBlock(
            locationName = locationName,
            state = state.weather,
            onRetry = viewModel::retryWeather
        )
        AirQualityBlock(
            state = state.airQuality,
            onRetry = viewModel::retryAirQuality
        )
        MarineBlock(
            state = state.marine,
            onRetry = viewModel::retryMarine
        )
    }
}

@Composable
fun WeatherBackground(state: UiState<Weather>) {
    when (state) {
        is UiState.Error, UiState.Loading -> {
            WeatherBackground(
                weatherCode = WeatherCode.UNKNOWN,
                isDay = true
            )
        }

        is UiState.Success -> {
            WeatherBackground(
                weatherCode = state.data.currentWeather.weatherCode ?: WeatherCode.UNKNOWN,
                isDay = state.data.currentWeather.isDay()
            )
        }
    }
}

@Composable
fun WeatherBlock(
    locationName: String,
    state: UiState<Weather>,
    onRetry: () -> Unit
) {
    when (state) {
        is UiState.Error -> {

        }

        UiState.Loading -> {

        }

        is UiState.Success -> {
            WeatherCard(locationName, state.data)
        }
    }
}

@Composable
fun AirQualityBlock(
    state: UiState<AirQuality>,
    onRetry: () -> Unit
) {
    when (state) {
        is UiState.Error -> {

        }

        UiState.Loading -> {

        }

        is UiState.Success -> {

        }
    }

}

@Composable
fun MarineBlock(
    state: UiState<Marine>,
    onRetry: () -> Unit
) {
    when (state) {
        is UiState.Error -> {

        }

        UiState.Loading -> {

        }

        is UiState.Success -> {

        }
    }

}

@Composable
fun WeatherCard(locationName: String, data: Weather) {
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = locationName,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = data.currentWeather.temperature.toString(),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Icon(
            imageVector = Icons.Outlined.LocationOn,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(
    name = "Light",
    showBackground = true
)
@Composable
fun WeatherCardLightPreview() {
    GeoGOTheme(darkTheme = false) {
        val context = LocalContext.current
        WeatherCard(
            "Batumi",
            Weather(
                0.11,
                0.11,
                "Europe/Berlin",
                CurrentWeather(
                    "",
                    23.01,
                    12.00,
                    1,
                    12.1,
                    WeatherCode.CLEAR_SKY,
                    windSpeed = 1.0
                ),
                HourlyWeather(
                    listOf(),
                    listOf(),
                    listOf(),
                    listOf(),
                    listOf(),
                    listOf(),
                    listOf(),
                    listOf(),
                    listOf(),
                    listOf(),
                    listOf(1, 1, 1, 0, 0, 0)
                ),
                DailyWeather(
                    listOf(),
                    listOf(),
                    listOf(),
                    listOf(),
                    listOf(),
                    listOf(),
                    listOf(),
                    listOf()
                )
            )
        )
    }
}

@Preview(
    name = "Dark",
    showBackground = true
)
@Composable
fun WeatherCardDarkPreview() {
    val context = LocalContext.current
    GeoGOTheme(darkTheme = true) {
        WeatherCard(
            "Batumi",
            Weather(
                0.11,
                0.11,
                "Europe/Berlin",
                CurrentWeather(
                    "",
                    23.01,
                    12.00,
                    1,
                    12.1,
                    WeatherCode.CLEAR_SKY,
                    windSpeed = 1.0
                ),
                HourlyWeather(
                    listOf(),
                    listOf(),
                    listOf(),
                    listOf(),
                    listOf(),
                    listOf(),
                    listOf(),
                    listOf(),
                    listOf(),
                    listOf(),
                    listOf(0, 1, 1, 0, 0, 0)
                ),
                DailyWeather(
                    listOf(),
                    listOf(),
                    listOf(),
                    listOf(),
                    listOf(),
                    listOf(),
                    listOf(),
                    listOf()
                )
            )
        )
    }
}
