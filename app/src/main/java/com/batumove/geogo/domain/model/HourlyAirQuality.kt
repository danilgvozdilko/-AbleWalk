package com.batumove.geogo.domain.model

data class HourlyAirQuality(
    val time: List<String>,
    val europeanAqi: List<Int?>,
    val pm25: List<Double?>,
    val pm10: List<Double?>
)
