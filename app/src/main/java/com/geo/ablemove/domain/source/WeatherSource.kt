package com.geo.ablemove.domain.source

import com.geo.ablemove.domain.model.Weather

interface WeatherSource {
    suspend fun getWeather(
        latitude: Double,
        longitude: Double,
        temperatureUnit: String = "celsius",
        windSpeedUnit: String = "kmh"
    ): Result<Weather>
}
