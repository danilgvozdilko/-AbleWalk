package com.geo.ablemove.presentation.weather

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geo.ablemove.domain.datastore.UnitFormattingPreferences
import com.geo.ablemove.domain.model.TemperatureUnit
import com.geo.ablemove.domain.model.UnitFormatting
import com.geo.ablemove.domain.model.filterNext24Hours
import com.geo.ablemove.domain.source.LocationRepository
import com.geo.ablemove.domain.usecase.AirQualityUseCase
import com.geo.ablemove.domain.usecase.MarineUseCase
import com.geo.ablemove.domain.usecase.WeatherUserCase
import com.geo.ablemove.presentation.home.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherUserCase: WeatherUserCase,
    private val airQualityUseCase: AirQualityUseCase,
    private val marineUseCase: MarineUseCase,
    private val locationRepository: LocationRepository,
    private val unitFormattingPreferences: UnitFormattingPreferences
) : ViewModel() {

    val unitFormatting: StateFlow<UnitFormatting> =
        unitFormattingPreferences.unitFormatting.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UnitFormatting()
        )

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState = _uiState.asStateFlow()

    private val _locationName = MutableStateFlow("")
    val locationName = _locationName.asStateFlow()

    init {
        observeTemperatureUnit()
        loadAirQuality()
        loadMarine()
    }

    private fun observeTemperatureUnit() {
        viewModelScope.launch {
            unitFormattingPreferences.unitFormatting
                .map { it.temperature }
                .distinctUntilChanged()
                .collectLatest { temperatureUnit ->
                    loadWeather(temperatureUnit)
                }
        }
    }

    private suspend fun loadWeather(
        temperatureUnit: TemperatureUnit
    ) {
        _uiState.update {
            it.copy(weather = UiState.Loading)
        }

        val result = weatherUserCase.getWeather(
            temperatureUnit = temperatureUnit.name.lowercase()
        )

        result.onSuccess { weather ->
            val locationName = locationRepository.getLocationName(
                latitude = weather.latitude,
                longitude = weather.longitude
            ).orEmpty()

            val parsedWeather = weather.copy(
                hourlyWeather = weather.hourlyWeather.filterNext24Hours()
            )

            _locationName.value = locationName

            _uiState.update {
                it.copy(weather = UiState.Success(parsedWeather))
            }

            Log.v("123123", "Success: $weather")

        }.onFailure { error ->
            if (error is CancellationException) throw error

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

    fun retryWeather() {
        viewModelScope.launch {
            val temperatureUnit = unitFormattingPreferences
                .unitFormatting
                .first()
                .temperature

            loadWeather(temperatureUnit)
        }
    }

    fun retryAirQuality() = loadAirQuality()
    fun retryMarine() = loadMarine()
}