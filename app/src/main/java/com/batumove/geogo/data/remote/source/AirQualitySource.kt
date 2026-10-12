package com.batumove.geogo.data.remote.source

import com.batumove.geogo.data.dto.AirQualityDto

interface AirQualitySource {

    suspend fun getAirQuality(
        latitude: Double,
        longitude: Double,
    ): AirQualityDto

}