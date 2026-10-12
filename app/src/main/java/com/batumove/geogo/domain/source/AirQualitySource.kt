package com.batumove.geogo.domain.source

import com.batumove.geogo.data.dto.AirQualityDto

interface AirQualitySource {

    suspend fun getAirQuality(
        latitude: Double,
        longitude: Double,
    ): AirQualityDto

}