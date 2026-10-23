package com.geo.ablemove.domain.model

data class DailyWeather(
    val time: List<String>,
    val maxTemperature: List<Double?>,
    val minTemperature: List<Double?>,
    val maxRainProbability: List<Int?>,
    val weatherCode: List<String?>,
    val sunrise: List<String?>,
    val sunset: List<String?>,
    val maxUvIndex: List<Double?>
)