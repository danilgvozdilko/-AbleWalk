package com.batumove.geogo.data.mapper

import com.batumove.geogo.data.dto.CurrentWeatherDto
import com.batumove.geogo.data.dto.DailyWeatherDto
import com.batumove.geogo.data.dto.HourlyWeatherDto
import com.batumove.geogo.data.dto.WeatherDto
import com.batumove.geogo.domain.model.CurrentWeather
import com.batumove.geogo.domain.model.DailyWeather
import com.batumove.geogo.domain.model.HourlyWeather
import com.batumove.geogo.domain.model.Weather

internal fun WeatherDto.toDomain(): Weather {
    return Weather(
        latitude = latitude,
        longitude = longitude,
        timezone = timezone,
        currentWeather = current.toDomain(),
        hourlyWeather = hourly.toDomain(),
        dailyWeather = daily.toDomain()
    )
}

internal fun CurrentWeatherDto.toDomain(): CurrentWeather {
    return CurrentWeather(
        time = time,
        temperature = temperature,
        feelsLike = feelsLike,
        humidity = humidity,
        precipitation = precipitation,
        weatherCode = weatherCode?.toDomainWeatherCode(),
        windSpeed = windSpeed
    )
}

internal fun HourlyWeatherDto.toDomain(): HourlyWeather {
    return HourlyWeather(
        time = time,
        temperature = temperature,
        feelsLike = feelsLike,
        humidity = humidity,
        precipitationProbability = precipitationProbability,
        precipitation = precipitation,
        weatherCode = weatherCode.map { it?.toDomainWeatherCode() ?: "Unknown Weather" },
        windSpeed = windSpeed,
        windGusts = windGusts,
        uvIndex = uvIndex,
        isDay = isDay
    )
}

internal fun DailyWeatherDto.toDomain(): DailyWeather {
    return DailyWeather(
        time,
        maxTemperature,
        minTemperature,
        maxRainProbability,
        weatherCode.map { it?.toDomainWeatherCode() ?: "Unknown Weather" },
        sunrise,
        sunset,
        maxUvIndex
    )

}