package com.geo.ablemove.domain.source

import com.geo.ablemove.domain.model.AirQuality

interface AirQualitySource {
    suspend fun getAirQuality(
        latitude: Double,
        longitude: Double,
    ): Result<AirQuality>
}
