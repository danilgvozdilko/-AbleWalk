package com.geo.ablemove.presentation.weather

import com.geo.ablemove.domain.model.AirQuality
import com.geo.ablemove.domain.model.Marine
import com.geo.ablemove.domain.model.Weather
import com.geo.ablemove.presentation.home.UiState

data class WeatherUiState(
    val weather: UiState<Weather> = UiState.Loading,
    val airQuality: UiState<AirQuality> = UiState.Loading,
    val marine: UiState<Marine> = UiState.Loading
)