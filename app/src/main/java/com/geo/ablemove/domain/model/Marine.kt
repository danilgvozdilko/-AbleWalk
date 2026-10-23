package com.geo.ablemove.domain.model

import com.geo.ablemove.data.dto.CurrentMarineDto
import com.geo.ablemove.data.dto.HourlyMarineDto

data class Marine(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val current: CurrentMarineDto? = null,
    val hourly: HourlyMarineDto
)