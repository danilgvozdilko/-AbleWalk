package com.geo.ablemove.data.remote.source

import com.geo.ablemove.data.mapper.toDomain
import com.geo.ablemove.data.remote.api.WeatherApi
import com.geo.ablemove.data.util.safeApiCall
import com.geo.ablemove.domain.model.Weather
import com.geo.ablemove.domain.source.WeatherSource
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