package com.geo.ablemove.domain

import com.geo.ablemove.data.dto.WeatherDto
import com.geo.ablemove.domain.source.WeatherSource

class FakeWeatherRepository : WeatherSource {

    var weather: WeatherDto? = null
    var weatherException: Throwable? = null


    override suspend fun getWeather(latitude: Double, longitude: Double): WeatherDto {
        weatherException?.let { throw it }
        return weather!!
    }

}