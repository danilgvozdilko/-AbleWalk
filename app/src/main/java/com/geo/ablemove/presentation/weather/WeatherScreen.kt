package com.geo.ablemove.presentation.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.geo.ablemove.domain.model.AirQuality
import com.geo.ablemove.domain.model.Marine
import com.geo.ablemove.domain.model.UnitFormatting
import com.geo.ablemove.domain.model.Weather
import com.geo.ablemove.domain.model.WeatherCode
import com.geo.ablemove.presentation.home.UiState
import com.geo.ablemove.presentation.weather.components.AirQualityCard
import com.geo.ablemove.presentation.weather.components.MarineCard
import com.geo.ablemove.presentation.weather.components.weather.WeatherCard
import com.geo.ablemove.presentation.weather.components.weather.WeatherCardShimmer
import com.geo.ablemove.ui.components.background.WeatherBackground
import com.geo.ablemove.ui.components.cards.ErrorCard
import com.geo.ablemove.ui.components.shimmer.ShimmerPlaceholder
import com.geo.ablemove.ui.components.shimmer.ShimmerProvider
import com.geo.ablemove.ui.theme.WeatherTheme

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel(),
    bottomPadding: Dp
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val locationName by viewModel.locationName.collectAsStateWithLifecycle()
    val unitFormatting by viewModel.unitFormatting.collectAsStateWithLifecycle()

    ShimmerProvider(theme = WeatherTheme.shimmerTheme) {
        WeatherBackgroundBlock(state.weather)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp,
                    bottom = bottomPadding + 8.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            WeatherBlock(
                locationName = locationName,
                unitFormatting = unitFormatting,
                state = state.weather,
                onRetry = viewModel::retryWeather
            )
            AirQualityBlock(
                state = state.airQuality,
                onRetry = viewModel::retryAirQuality
            )
            MarineBlock(
                state = state.marine,
                unitFormatting = unitFormatting,
                onRetry = viewModel::retryMarine
            )
        }
    }
}

@Composable
fun WeatherBackgroundBlock(state: UiState<Weather>) {
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
                isDay = state.data.hourlyWeather.isDay.first() == 1
            )
        }
    }
}

@Composable
fun WeatherBlock(
    locationName: String,
    unitFormatting: UnitFormatting,
    state: UiState<Weather>,
    onRetry: () -> Unit
) {
    when (state) {
        is UiState.Error -> {
            ErrorCard(onClick = onRetry)
        }

        UiState.Loading -> {
            WeatherCardShimmer()
        }

        is UiState.Success -> {
            WeatherCard(locationName, unitFormatting, state.data)
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
            ErrorCard(onClick = onRetry)
        }

        UiState.Loading -> {
            ShimmerPlaceholder(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f / 2f)
            )
        }

        is UiState.Success -> {
            AirQualityCard(state.data)
        }
    }
}

@Composable
fun MarineBlock(
    state: UiState<Marine>,
    onRetry: () -> Unit,
    unitFormatting: UnitFormatting
) {
    when (state) {
        is UiState.Error -> {
            ErrorCard(onClick = onRetry)
        }

        UiState.Loading -> {
            ShimmerPlaceholder(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f / 3f)
            )
        }

        is UiState.Success -> {
            MarineCard(state.data, unitFormatting)
        }
    }
}
