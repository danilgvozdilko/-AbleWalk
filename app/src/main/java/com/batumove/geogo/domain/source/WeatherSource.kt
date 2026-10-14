package com.batumove.geogo.domain.source

import com.batumove.geogo.domain.model.Weather

interface WeatherSource {
    suspend fun getWeather(
        latitude: Double,
        longitude: Double,
    ): Result<Weather>
}
