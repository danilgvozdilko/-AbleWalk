package com.batumove.geogo.data.remote.source

import com.batumove.geogo.data.dto.AirQualityDto
import com.batumove.geogo.data.remote.api.AirQualityApi
import com.batumove.geogo.domain.source.AirQualitySource
import javax.inject.Inject

class AirQualitySourceImpl @Inject constructor(private val airQualityApi: AirQualityApi) :
    AirQualitySource {

    override suspend fun getAirQuality(
        latitude: Double,
        longitude: Double,
    ): AirQualityDto {
        return airQualityApi.getAirQuality(latitude, longitude)
    }

}