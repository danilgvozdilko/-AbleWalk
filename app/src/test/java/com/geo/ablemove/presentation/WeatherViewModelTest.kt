package com.geo.ablemove.presentation

import com.geo.ablemove.MainDispatcherRule
import com.geo.ablemove.data.dto.CurrentWeatherDto
import com.geo.ablemove.data.dto.DailyWeatherDto
import com.geo.ablemove.data.dto.HourlyWeatherDto
import com.geo.ablemove.data.dto.WeatherDto
import com.geo.ablemove.domain.FakeWeatherRepository
import com.geo.ablemove.domain.usecase.WeatherUserCase
import com.geo.ablemove.presentation.home.HomeUiState
import com.geo.ablemove.presentation.home.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    @get:Rule
    val mainDispatcherRule =
        MainDispatcherRule()

    private lateinit var repository: FakeWeatherRepository
    private lateinit var useCase: WeatherUserCase


    @Before
    fun setUp() {
        repository = FakeWeatherRepository()
        useCase = WeatherUserCase(repository)
    }

    @Test
    fun `succesfull getWeather should update weather state`() =
        runTest {
            repository.weather = WeatherDto(
                latitude = 1.0,
                longitude = 1.0,
                timezone = "test",
                current = CurrentWeatherDto(time = "test", temperature = 1.0),
                hourly = HourlyWeatherDto(
                    time = emptyList(),
                    temperature = emptyList(),
                    feelsLike = emptyList(),
                    humidity = emptyList(),
                    precipitationProbability = emptyList(),
                    precipitation = emptyList(),
                    weatherCode = emptyList(),
                    windSpeed = emptyList(),
                    windGusts = emptyList(),
                    uvIndex = emptyList(),
                    isDay = emptyList()
                ),
                daily = DailyWeatherDto(
                    time = emptyList(),
                    maxTemperature = emptyList(),
                    minTemperature = emptyList(),
                    maxRainProbability = emptyList(),
                    weatherCode = emptyList(),
                    sunrise = emptyList(),
                    sunset = emptyList(),
                    maxUvIndex = emptyList()
                )
            )
            val viewModel = HomeViewModel(useCase)

            advanceUntilIdle()

            val state = viewModel.uiState.value

            require(state is HomeUiState.Content)

            assertEquals(
                repository.weather!!.current.temperature,
                state.weather.currentWeather.temperature
            )
        }

    @Test
    fun `error state should update state to Error`() = runTest {

        repository.weatherException =
            IllegalStateException(
                "Server unavailable"
            )

        val viewModel = HomeViewModel(useCase)

        advanceUntilIdle()

        val state = viewModel.uiState.value

        require(state is HomeUiState.Error)

        assertEquals(
            "Server unavailable",
            state.error,
        )

    }


}