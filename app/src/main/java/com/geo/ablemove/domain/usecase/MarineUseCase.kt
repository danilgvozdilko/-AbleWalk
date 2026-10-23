package com.geo.ablemove.domain.usecase

import com.geo.ablemove.domain.model.Marine
import com.geo.ablemove.domain.source.MarineSource
import javax.inject.Inject

class MarineUseCase @Inject constructor(private val marineSource: MarineSource) {
    suspend fun getMarine(latitude: Double, longitude: Double): Result<Marine> =
        marineSource.getMarine(latitude, longitude)
}
