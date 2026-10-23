package com.geo.ablemove.data.remote.source

import com.geo.ablemove.data.mapper.toDomain
import com.geo.ablemove.data.remote.api.MarineApi
import com.geo.ablemove.data.util.safeApiCall
import com.geo.ablemove.domain.model.Marine
import com.geo.ablemove.domain.source.MarineSource
import javax.inject.Inject

class MarineSourceImpl @Inject constructor(private val marineApi: MarineApi) : MarineSource {

    override suspend fun getMarine(
        latitude: Double,
        longitude: Double,
    ): Result<Marine> {
        return safeApiCall {
            marineApi.getMarine(latitude, longitude).toDomain()
        }
    }

}