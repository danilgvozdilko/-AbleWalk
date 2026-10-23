package com.geo.ablemove.domain.model

data class Weather(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val currentWeather: CurrentWeather,
    val hourlyWeather: HourlyWeather,
    val dailyWeather: DailyWeather
)