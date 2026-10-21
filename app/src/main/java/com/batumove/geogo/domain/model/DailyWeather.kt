package com.batumove.geogo.domain.model

data class DailyWeather(
    val time: List<String>,
    val maxTemperature: List<Double?>,
    val minTemperature: List<Double?>,
    val maxRainProbability: List<Int?>,
    val weatherCode: List<WeatherCode?>,
    val sunrise: List<String?>,
    val sunset: List<String?>,
    val maxUvIndex: List<Double?>
)