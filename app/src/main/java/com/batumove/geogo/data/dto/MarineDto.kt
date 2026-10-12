package com.batumove.geogo.data.dto

import com.google.gson.annotations.SerializedName

data class MarineDto(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val current: CurrentMarineDto?,
    val hourly: HourlyMarineDto
)

data class CurrentMarineDto(
    val time: String,

    @SerializedName("wave_height")
    val waveHeight: Double?,

    @SerializedName("sea_surface_temperature")
    val seaTemperature: Double?,

    @SerializedName("wave_direction")
    val waveDirection: Double?
)

data class HourlyMarineDto(
    val time: List<String>,

    @SerializedName("wave_height")
    val waveHeight: List<Double?>,

    @SerializedName("sea_surface_temperature")
    val seaTemperature: List<Double?>,

    @SerializedName("wave_direction")
    val waveDirection: List<Double?>,

    @SerializedName("wave_period")
    val wavePeriod: List<Double?>
)
