package com.geo.ablemove.domain.model

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

data class HourlyWeather(
    val time: List<String>,
    val temperature: List<Double?>,
    val feelsLike: List<Double?>,
    val humidity: List<Int?>,
    val precipitationProbability: List<Int?>,
    val precipitation: List<Double?>,
    val weatherCode: List<WeatherCode?>,
    val windSpeed: List<Double?>,
    val windGusts: List<Double?>,
    val uvIndex: List<Double?>,
    val isDay: List<Int?>
)

fun HourlyWeather.filterNext24Hours(
    now: LocalDateTime = LocalDateTime.now()
): HourlyWeather {
    val start = now.truncatedTo(ChronoUnit.HOURS)
    val end = start.plusHours(24)

    val indices = time.indices.filter { index ->
        val dateTime = LocalDateTime.parse(time[index])
        !dateTime.isBefore(start) && dateTime.isBefore(end)
    }

    return copy(
        time = indices.map {
            LocalDateTime.parse(time[it]).hour.toString()
        },
        temperature = indices.map { temperature[it] },
        weatherCode = indices.map { weatherCode[it] },
        precipitationProbability = indices.map {
            precipitationProbability[it]
        },
        isDay = indices.map { isDay[it] },
        windSpeed = indices.map { windSpeed[it] },
        humidity = indices.map { humidity[it] },
        precipitation = indices.map { precipitation[it] },
        feelsLike = indices.map { feelsLike[it] },
        uvIndex = indices.map { uvIndex[it] },
        windGusts = indices.map { windGusts[it] }
    )
}