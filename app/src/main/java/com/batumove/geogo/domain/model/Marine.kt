package com.batumove.geogo.domain.model

import com.batumove.geogo.data.dto.CurrentMarineDto
import com.batumove.geogo.data.dto.HourlyMarineDto

data class Marine(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val current: CurrentMarineDto? = null,
    val hourly: HourlyMarineDto
)