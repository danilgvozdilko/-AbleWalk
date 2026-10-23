package com.geo.ablemove.domain.model

data class CurrentMarine(
    val time: String,
    val waveHeight: Double? = null,
    val seaTemperature: Double? = null,
    val waveDirection: Double? = null
)