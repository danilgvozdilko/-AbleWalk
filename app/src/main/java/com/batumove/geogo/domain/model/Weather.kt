package com.batumove.geogo.domain.model

data class Weather(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val time: String,
    val temperature: Double,
    val feelsLike: Double,
    val humidity: Int,
    val precipitation: Double,
    val weatherCode: String,
    val windSpeed: Double
)