package com.batumove.geogo.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MarineDto(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val current: CurrentMarineDto? = null,
    val hourly: HourlyMarineDto
)

@Serializable
data class CurrentMarineDto(
    val time: String,

    @SerialName("wave_height")
    val waveHeight: Double? = null,

    @SerialName("sea_surface_temperature")
    val seaTemperature: Double? = null,

    @SerialName("wave_direction")
    val waveDirection: Double? = null
)

@Serializable
data class HourlyMarineDto(
    val time: List<String>,

    @SerialName("wave_height")
    val waveHeight: List<Double?>,

    @SerialName("sea_surface_temperature")
    val seaTemperature: List<Double?>,

    @SerialName("wave_direction")
    val waveDirection: List<Double?>,

    @SerialName("wave_period")
    val wavePeriod: List<Double?>
)
