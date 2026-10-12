package com.batumove.geogo.domain.source

import com.batumove.geogo.data.dto.WeatherDto

interface WeatherSource {

    suspend fun getWeather(
        latitude: Double,
        longitude: Double,
    ): WeatherDto

}