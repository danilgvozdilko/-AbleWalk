package com.batumove.geogo.presentation.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.batumove.geogo.domain.model.AirQuality
import com.batumove.geogo.domain.model.Marine
import com.batumove.geogo.domain.model.Weather
import com.batumove.geogo.presentation.home.UiState
import com.batumove.geogo.ui.theme.GeoGOTheme
import com.batumove.geogo.ui.theme.WeatherTheme

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        WeatherBlock(
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
fun WeatherBlock(
    state: UiState<Weather>,
    onRetry: () -> Unit
) {
    when (state) {
        is UiState.Error -> {

        }

        UiState.Loading -> {

        }

        is UiState.Success -> {
            WeatherCard(state.data)
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
fun WeatherCard(data: Weather) {
    Card(
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Text(
            text = "24°",
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
