package com.geo.ablemove.domain.model

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