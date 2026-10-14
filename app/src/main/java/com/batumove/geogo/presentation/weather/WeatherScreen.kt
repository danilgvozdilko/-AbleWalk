package com.batumove.geogo.presentation.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.batumove.geogo.domain.model.AirQuality
import com.batumove.geogo.domain.model.Marine
import com.batumove.geogo.domain.model.Weather
import com.batumove.geogo.presentation.home.UiState

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

        is UiState.Success<*> -> {

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

        is UiState.Success<*> -> {

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

        is UiState.Success<*> -> {

        }
    }

}