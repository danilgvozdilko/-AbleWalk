package com.geo.ablemove.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AirQualityDto(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val current: CurrentAirQualityDto? = null,
    val hourly: HourlyAirQualityDto
)

@Serializable
data class CurrentAirQualityDto(
    val time: String,

    @SerialName("european_aqi")
    val europeanAqi: Int? = null,

    @SerialName("pm2_5")
    val pm25: Double? = null,

    @SerialName("pm10")
    val pm10: Double? = null
)

@Serializable
data class HourlyAirQualityDto(
    val time: List<String>,

    @SerialName("european_aqi")
    val europeanAqi: List<Int?>,

    @SerialName("pm2_5")
    val pm25: List<Double?>,

    @SerialName("pm10")
    val pm10: List<Double?>
)
