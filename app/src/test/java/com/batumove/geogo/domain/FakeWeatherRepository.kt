package com.batumove.geogo.domain

import com.batumove.geogo.data.dto.WeatherDto
import com.batumove.geogo.domain.source.WeatherSource

class FakeWeatherRepository : WeatherSource {

    var weather: WeatherDto? = null
    var weatherException: Throwable? = null


    override suspend fun getWeather(latitude: Double, longitude: Double): WeatherDto {
        weatherException?.let { throw it }
        return weather!!
    }

}