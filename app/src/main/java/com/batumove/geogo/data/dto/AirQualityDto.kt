package com.batumove.geogo.data.dto

import com.google.gson.annotations.SerializedName

data class AirQualityDto(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val current: CurrentAirQualityDto?,
    val hourly: HourlyAirQualityDto
)

data class CurrentAirQualityDto(
    val time: String,

    @SerializedName("european_aqi")
    val europeanAqi: Int?,

    @SerializedName("pm2_5")
    val pm25: Double?,

    @SerializedName("pm10")
    val pm10: Double?
)

data class HourlyAirQualityDto(
    val time: List<String>,

    @SerializedName("european_aqi")
    val europeanAqi: List<Int?>,

    @SerializedName("pm2_5")
    val pm25: List<Double?>,

    @SerializedName("pm10")
    val pm10: List<Double?>
)
