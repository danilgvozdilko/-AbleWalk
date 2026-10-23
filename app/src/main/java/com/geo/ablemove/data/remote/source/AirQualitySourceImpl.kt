package com.geo.ablemove.data.remote.source

import com.geo.ablemove.data.mapper.toDomain
import com.geo.ablemove.data.remote.api.AirQualityApi
import com.geo.ablemove.data.util.safeApiCall
import com.geo.ablemove.domain.model.AirQuality
import com.geo.ablemove.domain.source.AirQualitySource
import javax.inject.Inject

class AirQualitySourceImpl @Inject constructor(
    private val airQualityApi: AirQualityApi
) : AirQualitySource {

    override suspend fun getAirQuality(
        latitude: Double,
        longitude: Double,
    ): Result<AirQuality> {
        return safeApiCall {
            airQualityApi.getAirQuality(latitude, longitude).toDomain()
        }

    }
}
