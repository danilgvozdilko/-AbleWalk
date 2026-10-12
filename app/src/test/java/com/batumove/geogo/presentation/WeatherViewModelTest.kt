package com.batumove.geogo.presentation

import com.batumove.geogo.MainDispatcherRule
import com.batumove.geogo.data.dto.CurrentWeatherDto
import com.batumove.geogo.data.dto.DailyWeatherDto
import com.batumove.geogo.data.dto.HourlyWeatherDto
import com.batumove.geogo.data.dto.WeatherDto
import com.batumove.geogo.domain.FakeWeatherRepository
import com.batumove.geogo.domain.usecase.WeatherUserCase
import com.batumove.geogo.presentation.home.HomeUiState
import com.batumove.geogo.presentation.home.HomeViewModel
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