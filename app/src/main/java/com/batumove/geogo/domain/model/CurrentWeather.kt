package com.batumove.geogo.domain.model

import java.time.LocalDateTime

data class CurrentWeather(
    val time: String,
    val temperature: Double?,
    val feelsLike: Double?,
    val humidity: Int?,
    val precipitation: Double?,
    val weatherCode: WeatherCode?,
    val windSpeed: Double?
)

internal fun CurrentWeather.isDay(): Boolean {
    val hour = LocalDateTime.parse(time).hour
    return hour in 6 until 18
}