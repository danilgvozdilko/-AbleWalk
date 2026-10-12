package com.batumove.geogo.domain.usecase

import com.batumove.geogo.data.mapper.toDomain
import com.batumove.geogo.data.remote.source.WeatherSource
import com.batumove.geogo.domain.model.Weather
import javax.inject.Inject

class WeatherUserCase @Inject constructor(private val weatherSource: WeatherSource) {
    suspend fun getWeather(): Weather {
        return weatherSource.getWeather(41.64, 41.63).toDomain()
    }
}