package com.geo.ablemove.domain.usecase

import com.geo.ablemove.domain.model.Weather
import com.geo.ablemove.domain.source.WeatherSource
import javax.inject.Inject

class WeatherUserCase @Inject constructor(private val weatherSource: WeatherSource) {
    suspend fun getWeather(temperatureUnit: String): Result<Weather> =
        weatherSource.getWeather(41.64, 41.63, temperatureUnit)
}
