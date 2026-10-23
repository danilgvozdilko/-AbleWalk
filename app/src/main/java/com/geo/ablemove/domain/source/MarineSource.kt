package com.geo.ablemove.domain.source

import com.geo.ablemove.domain.model.Marine

interface MarineSource {
    suspend fun getMarine(
        latitude: Double,
        longitude: Double,
    ): Result<Marine>
}
