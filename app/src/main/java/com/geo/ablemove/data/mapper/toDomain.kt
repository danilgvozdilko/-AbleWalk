package com.geo.ablemove.data.mapper

import com.geo.ablemove.domain.model.WeatherCode
import com.geo.ablemove.data.dto.AirQualityDto
import com.geo.ablemove.data.dto.CurrentAirQualityDto
import com.geo.ablemove.data.dto.CurrentMarineDto
import com.geo.ablemove.data.dto.CurrentWeatherDto
import com.geo.ablemove.data.dto.DailyWeatherDto
import com.geo.ablemove.data.dto.HourlyAirQualityDto
import com.geo.ablemove.data.dto.HourlyMarineDto
import com.geo.ablemove.data.dto.HourlyWeatherDto
import com.geo.ablemove.data.dto.MarineDto
import com.geo.ablemove.data.dto.WeatherDto
import com.geo.ablemove.domain.model.AirQuality
import com.geo.ablemove.domain.model.CurrentAirQuality
import com.geo.ablemove.domain.model.CurrentMarine
import com.geo.ablemove.domain.model.CurrentWeather
import com.geo.ablemove.domain.model.DailyWeather
import com.geo.ablemove.domain.model.HourlyAirQuality
import com.geo.ablemove.domain.model.HourlyMarine
import com.geo.ablemove.domain.model.HourlyWeather
import com.geo.ablemove.domain.model.Marine
import com.geo.ablemove.domain.model.Weather

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
        weatherCode = weatherCode.map { it?.toDomainWeatherCode() ?: WeatherCode.UNKNOWN },
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
        weatherCode.map { it?.toDomainWeatherCode() ?: WeatherCode.UNKNOWN },
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