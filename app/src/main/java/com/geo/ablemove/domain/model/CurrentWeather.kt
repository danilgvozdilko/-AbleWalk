package com.geo.ablemove.domain.model

data class CurrentWeather(
    val time: String,
    val temperature: Double?,
    val feelsLike: Double?,
    val humidity: Int?,
    val precipitation: Double?,
    val weatherCode: WeatherCode?,
    val windSpeed: Double?
)
