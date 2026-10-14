package com.batumove.geogo.data.remote.source

import com.batumove.geogo.data.mapper.toDomain
import com.batumove.geogo.data.remote.api.WeatherApi
import com.batumove.geogo.data.util.safeApiCall
import com.batumove.geogo.domain.model.Weather
import com.batumove.geogo.domain.source.WeatherSource
import javax.inject.Inject

class WeatherSourceImpl @Inject constructor(private val weatherApi: WeatherApi) : WeatherSource {

    override suspend fun getWeather(
        latitude: Double,
        longitude: Double
    ): Result<Weather> {
        return safeApiCall {
            weatherApi.getForecast(latitude, longitude).toDomain()
        }
    }

}