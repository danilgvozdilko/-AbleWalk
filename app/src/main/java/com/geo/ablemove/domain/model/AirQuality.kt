package com.geo.ablemove.domain.model

import com.geo.ablemove.data.dto.CurrentAirQualityDto
import com.geo.ablemove.data.dto.HourlyAirQualityDto

data class AirQuality(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val current: CurrentAirQualityDto? = null,
    val hourly: HourlyAirQualityDto? = null
)