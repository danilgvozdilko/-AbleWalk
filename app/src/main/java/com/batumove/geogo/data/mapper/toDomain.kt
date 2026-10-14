package com.batumove.geogo.data.mapper

import com.batumove.geogo.data.dto.AirQualityDto
import com.batumove.geogo.data.dto.CurrentAirQualityDto
import com.batumove.geogo.data.dto.CurrentMarineDto
import com.batumove.geogo.data.dto.CurrentWeatherDto
import com.batumove.geogo.data.dto.DailyWeatherDto
import com.batumove.geogo.data.dto.HourlyAirQualityDto
import com.batumove.geogo.data.dto.HourlyMarineDto
import com.batumove.geogo.data.dto.HourlyWeatherDto
import com.batumove.geogo.data.dto.MarineDto
import com.batumove.geogo.data.dto.WeatherDto
import com.batumove.geogo.domain.model.AirQuality
import com.batumove.geogo.domain.model.CurrentAirQuality
import com.batumove.geogo.domain.model.CurrentMarine
import com.batumove.geogo.domain.model.CurrentWeather
import com.batumove.geogo.domain.model.DailyWeather
import com.batumove.geogo.domain.model.HourlyAirQuality
import com.batumove.geogo.domain.model.HourlyMarine
import com.batumove.geogo.domain.model.HourlyWeather
import com.batumove.geogo.domain.model.Marine
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

internal fun MarineDto.toDomain(): Marine {
    return Marine(
        latitude,
        longitude,
        timezone,
        current,
        hourly
    )
}

internal fun CurrentMarineDto.toDomain(): CurrentMarine {
    return CurrentMarine(time, waveHeight, seaTemperature, waveDirection)
}

internal fun HourlyMarineDto.toDomain(): HourlyMarine {
    return HourlyMarine(time, waveHeight, seaTemperature, waveDirection, wavePeriod)
}

internal fun AirQualityDto.toDomain(): AirQuality {
    return AirQuality(latitude, longitude, timezone, current, hourly)
}

internal fun CurrentAirQualityDto.toDomain(): CurrentAirQuality {
    return CurrentAirQuality(time, europeanAqi, pm25, pm10)
}

internal fun HourlyAirQualityDto.toDomain(): HourlyAirQuality {
    return HourlyAirQuality(time, europeanAqi, pm25, pm10)
}