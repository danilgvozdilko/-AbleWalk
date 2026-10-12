package com.batumove.geogo.data.mapper

import com.batumove.geogo.data.dto.WeatherDto
import com.batumove.geogo.domain.model.Weather

internal fun WeatherDto.toDomain(): Weather {
    return Weather(
        latitude = latitude,
        longitude = longitude,
        timezone = timezone,
        time = current.time,
        temperature = current.temperature ?: 0.0,
        feelsLike = current.feelsLike ?: 0.0,
        humidity = current.humidity ?: 0,
        precipitation = current.precipitation ?: 0.0,
        weatherCode = current.weatherCode.toString(),
        windSpeed = current.windSpeed ?: 0.0
    )
}