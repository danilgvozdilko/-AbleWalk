package com.batumove.geogo.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.batumove.geogo.domain.usecase.AirQualityUseCase
import com.batumove.geogo.domain.usecase.MarineUseCase
import com.batumove.geogo.domain.usecase.WeatherUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherUserCase: WeatherUserCase,
    private val airQualityUseCase: AirQualityUseCase,
    private val marineUseCase: MarineUseCase
) : ViewModel() {
    private val _uiState =
        MutableStateFlow<HomeUiState>(
            HomeUiState.Loading
        )

    val uiState: StateFlow<HomeUiState> =
        _uiState.asStateFlow()

    init {
        loadWeather()
    }

    fun loadWeather() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                val weather = weatherUserCase.getWeather()
                Log.v("123123", "weather: $weather")
                _uiState.value = HomeUiState.Content(weather)
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.message ?: "Unknown error")
            }
        }

        viewModelScope.launch {
            try {
                val airQuality =
                    airQualityUseCase.getAirQuality(41.64, 41.63)
                Log.v("123123", "airQuality: $airQuality")
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.message ?: "Unknown error")
            }

        }

        viewModelScope.launch {
            try {
                val marine =
                    marineUseCase.getMarine(41.64, 41.63)
                Log.v("123123", "marine: $marine")
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.message ?: "Unknown error")
            }

        }
    }

}