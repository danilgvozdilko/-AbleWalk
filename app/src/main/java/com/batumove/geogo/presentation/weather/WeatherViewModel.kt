package com.batumove.geogo.presentation.weather

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.batumove.geogo.domain.usecase.AirQualityUseCase
import com.batumove.geogo.domain.usecase.MarineUseCase
import com.batumove.geogo.domain.usecase.WeatherUserCase
import com.batumove.geogo.presentation.home.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherUserCase: WeatherUserCase,
    private val airQualityUseCase: AirQualityUseCase,
    private val marineUseCase: MarineUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadWeather()
        loadAirQuality()
        loadMarine()
    }

    private fun loadWeather() {
        viewModelScope.launch {
            _uiState.update { it.copy(weather = UiState.Loading) }

            val result = weatherUserCase.getWeather()

            result.onSuccess { weather ->
                _uiState.update {
                    it.copy(weather = UiState.Success(weather))
                }
                Log.v("123123", "Success: $weather")
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        weather = UiState.Error(
                            error.message ?: "Something went wrong"
                        )
                    )
                }
                Log.v("123123", "Error", error)
            }
        }

    }

    private fun loadAirQuality() {
        viewModelScope.launch {
            _uiState.update { it.copy(airQuality = UiState.Loading) }

            val result = airQualityUseCase.getAirQuality(41.64, 41.63)

            result.onSuccess { airQuality ->
                _uiState.update {
                    it.copy(airQuality = UiState.Success(airQuality))
                }
                Log.v("123123", "Success: $airQuality")
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        airQuality = UiState.Error(
                            error.message ?: "Something went wrong"
                        )
                    )
                }
                Log.v("123123", "Error", error)
            }
        }
    }

    private fun loadMarine() {
        viewModelScope.launch {
            _uiState.update { it.copy(marine = UiState.Loading) }

            val result = marineUseCase.getMarine(41.64, 41.63)

            result.onSuccess { marine ->
                _uiState.update {
                    it.copy(marine = UiState.Success(marine))
                }
                Log.v("123123", "Success: $marine")
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        marine = UiState.Error(
                            error.message ?: "Something went wrong"
                        )
                    )
                }
                Log.v("123123", "Error", error)
            }
        }
    }

    fun retryWeather() = loadWeather()
    fun retryAirQuality() = loadAirQuality()
    fun retryMarine() = loadMarine()
}