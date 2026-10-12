package com.batumove.geogo.data.remote.source

import com.batumove.geogo.data.dto.WeatherDto

interface WeatherSource {

    suspend fun getWeather(
        latitude: Double,
        longitude: Double,
    ): WeatherDto

}