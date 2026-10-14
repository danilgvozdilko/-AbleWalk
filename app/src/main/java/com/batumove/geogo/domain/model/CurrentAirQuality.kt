package com.batumove.geogo.domain.model

data class CurrentAirQuality(
    val time: String,
    val europeanAqi: Int? = null,
    val pm25: Double? = null,
    val pm10: Double? = null
)
