package com.batumove.geogo.domain.model

data class HourlyMarine(
    val time: List<String>,
    val waveHeight: List<Double?>,
    val seaTemperature: List<Double?>,
    val waveDirection: List<Double?>,
    val wavePeriod: List<Double?>
)