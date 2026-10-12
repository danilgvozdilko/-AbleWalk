package com.batumove.geogo.data.remote.source

import com.batumove.geogo.data.dto.WeatherDto
import com.batumove.geogo.data.remote.api.WeatherApi
import com.batumove.geogo.domain.source.WeatherSource
import javax.inject.Inject

class WeatherSourceImpl @Inject constructor(private val weatherApi: WeatherApi) : WeatherSource {

    override suspend fun getWeather(
        latitude: Double,
        longitude: Double
    ): WeatherDto {
        return weatherApi.getForecast(latitude, longitude)
    }

}