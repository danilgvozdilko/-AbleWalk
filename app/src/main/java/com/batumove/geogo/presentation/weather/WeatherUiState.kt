package com.batumove.geogo.presentation.weather

import com.batumove.geogo.domain.model.AirQuality
import com.batumove.geogo.domain.model.Marine
import com.batumove.geogo.domain.model.Weather
import com.batumove.geogo.presentation.home.UiState

data class WeatherUiState(
    val weather: UiState<Weather> = UiState.Loading,
    val airQuality: UiState<AirQuality> = UiState.Loading,
    val marine: UiState<Marine> = UiState.Loading
)