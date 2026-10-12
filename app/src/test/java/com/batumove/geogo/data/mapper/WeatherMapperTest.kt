package com.batumove.geogo.data.mapper

import com.batumove.geogo.data.dto.CurrentWeatherDto
import junit.framework.TestCase.assertEquals
import org.junit.Test

class WeatherMapperTest {

    @Test
    fun `weatherDto should be mapped to weather`() {
        val weatherDto = CurrentWeatherDto(
            time = "time",
            temperature = 36.6,
            feelsLike = 36.6,
            humidity = 36,
            precipitation = 36.6,
            weatherCode = 36,
            windSpeed = 36.6
        )
        val weather = weatherDto.toDomain()
        assertEquals(weatherDto.time, weather.time)
    }

    @Test
    fun `weatherCode should me mapped to String`() {
        val weatherDto = CurrentWeatherDto(
            time = "time",
            temperature = 36.6,
            feelsLike = 36.6,
            humidity = 36,
            precipitation = 36.6,
            weatherCode = 1,
            windSpeed = 36.6
        )
        val weather = weatherDto.toDomain()
        assertEquals(weather.weatherCode, "Mainly clear")
    }
}