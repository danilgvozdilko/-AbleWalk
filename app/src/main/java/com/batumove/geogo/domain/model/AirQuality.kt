package com.batumove.geogo.domain.model

import com.batumove.geogo.data.dto.CurrentAirQualityDto
import com.batumove.geogo.data.dto.HourlyAirQualityDto

data class AirQuality(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val current: CurrentAirQualityDto? = null,
    val hourly: HourlyAirQualityDto? = null
)